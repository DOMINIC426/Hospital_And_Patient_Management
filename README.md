

## Health Management System — System Specifications

---

### 👤 STEP 1 — Registration and Authentication

Anyone wishing to use the system must **register first**. During registration, they provide:

* Full name, date of birth, gender, phone number, email, and password.
* **System Role** — Are they a Patient, Clinician, Nutritionist, or Administrator?

The system encrypts and saves the password as a **BCrypt hash** (never plain text for security compliance). It then returns a **JWT token** that serves as their "digital ID card" valid for 300 minutes. Every subsequent request sent to the backend must include this token in the header—if it is missing, the system rejects it immediately and returns a **401 Unauthorized** status code.

---

### 🏥 STEP 2 — Patient Onboarding & Medical History Completion

After registration, the **Patient** fills out their **Medical History** once—this is a strict One-to-One relationship per patient. They record:

* Chronic illnesses (e.g., diabetes, hypertension)
* Current medications
* Allergies
* Past surgical history
* Family medical history

This step is critical because when a clinician reviews the patient later, they will have full visibility into this history to make better clinical decisions.

---

### 📅 STEP 3 — Appointment Scheduling

The patient can **book an appointment** with a healthcare provider. During booking:

* They select their preferred doctor (Clinician).
* The system allocates the date and time.
* The system automatically assigns a **Queue Number** (e.g., "Number 7").
* The appointment status initializes as **BOOKED**.

Clinicians can view their individual schedules, while Administrators have full visibility over the entire clinic's appointments.

---

### 🩺 STEP 4 — New Patient Visit (The Core Engine)

When the patient arrives at the clinic, the **Clinician** opens a **New Visit**. The Visit entity acts as the main entry point for everything else—every test, assessment, and laboratory result is directly mapped to a single Visit ID.

Within the Visit session, the clinician records:

* The **Chief Complaint** (e.g., "Severe abdominal pain for 3 days").
* Duration, **severity**, and associated secondary symptoms.
* **Onset** (when the symptoms explicitly started).

---

### 🤖 STEP 5 — AI-Powered Triage & Risk Assessment

Once the clinician inputs the complaints, the **AI engine (Google Gemini)** analyzes all parameters to recommend a **Triage Level**:

| Color Code | Meaning | Clinical Urgency |
| --- | --- | --- |
| 🟢 **GREEN** | Routine | Non-urgent case |
| 🟡 **YELLOW** | Priority | Requires close attention and prompt review |
| 🟠 **ORANGE** | Urgent | High risk, prioritize immediately |
| 🔴 **RED** | Emergency | Immediate critical care required NOW |

**Crucial Note:** The AI only provides a recommendation—the clinician holds final authority to confirm or override it. The AI does not make autonomous clinical decisions.

---

### 📏 STEP 6 — Anthropometric Measurements

The clinician or triage nurse inputs the patient's **vital statistics and body metrics**:

* Weight, height, blood pressure, hemoglobin levels, and blood glucose.
* Mid-Upper Arm Circumference (MUAC) to screen for acute malnutrition.
* Waist-to-Hip Ratio (WHR) inputs.

The system **automatically calculates the BMI and WHR** on the fly—no manual math required. You input the weight and height, and the backend handles the formula logic.

---

### 🥗 STEP 7 — Nutrition Assessment

The **Nutritionist** logs in to conduct a comprehensive dietary evaluation, capturing:

* Meal frequencies and types of food consumed daily.
* Dietary restrictions (e.g., diabetic management, religious requirements like Halal, food allergies).
* Daily fluid/water intake.
* Physical Activity Level (PAL).
* Sleep hours, alcohol consumption habits, and smoking history.

---

### 🧠 STEP 8 — Mental Health Screening

During the same visit, the clinician conducts a brief psychiatric screening:

* Current stress levels and sleep quality.
* **PHQ-2 Score** (Patient Health Questionnaire-2 for depression screening).
* **GAD-2 Score** (Generalized Anxiety Disorder-2 for anxiety screening).

**Automated System Rule:** If the PHQ-2 score is $\ge 3$ **OR** the GAD-2 score is $\ge 3$, the backend automatically flags **Referral Flag = TRUE**, signaling that this patient requires an immediate referral to a mental health professional.

---

### 🔬 STEP 9 — Laboratory Results

The clinician logs laboratory profiles from blood and urine tests:

* CBC (Complete Blood Count).
* HbA1c (Long-term glycemic control for diabetes).
* Lipid Profile (Cholesterol tracking).
* Creatinine (Kidney function).
* Liver Function Tests (LFT).
* Electrolytes and Urinalysis.

These metrics are synchronized with the nutritionist's dashboard to ensure precise dietary diagnoses.

---

### 📋 STEP 10 — PES Nutrition Diagnosis

This is the primary workspace for the **Nutritionist**, who formulates a diagnosis using the standardized PES structure:

* **P — Problem:** What is the core issue? (e.g., "Protein-Energy Malnutrition")
* **E — Etiology:** What is the root cause? (e.g., "Inadequate intake of protein-dense foods")
* **S — Signs/Symptoms:** What evidence supports this? (e.g., "MUAC below 21cm, low hemoglobin numbers")

Following the PES statement, they document:

* **Intervention** — Action plan to correct the problem.
* **Prescription** — Specific dietary items or supplements assigned.
* **Monitoring Indicators** — Metrics used to evaluate patient improvement.

**AI Support Integration:** The AI scans all patient data (anthropometrics + nutrition assessment + labs) and generates a **draft PES statement**. The nutritionist reviews, modifies if necessary, and saves it.

---

### 💊 STEP 11 — Drug-Nutrition Interaction Alerts

The system cross-references all **active medications** in the patient's medical history against a backend **Drug-Nutrition Interactions table**. If a medication conflicts with a dietary path, the system triggers a **warning flag** for the clinician and nutritionist—for example:

> "Warfarin + Vitamin K (Green Leafy Vegetables) Interaction. Recommendation: Maintain consistent daily intake of vitamin K to avoid altering drug efficacy."

---

### 🍎 STEP 12 — Localized Food Recommendations

Instead of generic advice like "increase protein intake," the database includes a pre-mapped **indigenous and local Tanzanian food database**. The AI suggests exact items tailored to the patient's context:

* **Increase:** Beans, fish, milk (for protein deficiencies).
* **Avoid:** High sodium/salt, refined sugars (for hypertension or diabetes management).

This ensures nutritional counseling remains highly practical, affordable, and culturally relevant.

---

### 📊 STEP 13 — Population Analytics Dashboard

This interface is strictly restricted to the **Administrator**. The system aggregates anonymized data across all Visits (completely scrubbing names and personal identifiers) to compile monthly trends on:

* Top presenting clinical symptoms.
* Overall population nutritional statuses.
* Mental health trends across demographics.
* Early warning signs for disease outbreaks.

This allows the Admin to plan clinic resources effectively (e.g., "Diabetic cases rose by 15% this month; we need to requisition more insulin and testing supplies").

---

### 🔐 STEP 14 — Security Audit Logs

**Every single action** performed by a user within the system is tracked in an immutable log—detailing who did what, and exactly when. This is visible only to the Admin. These logs **cannot be updated or deleted by anyone**, ensuring complete data integrity, security compliance, and accountability.

---

## Complete Workflow Diagram



```
Register Account → Log In (Receive JWT Token)
       ↓
Complete Medical History (Patient - One-Time Setup)
       ↓
Schedule Appointment with Clinician
       ↓
Arrive at Clinic → Clinician Open New Visit
       ↓
AI Core Engine → Assign Triage Level (Green/Yellow/Orange/Red)
       ↓
Collect Anthropometrics + Nutrition Vitals + Mental Health Vitals + Lab Work
       ↓
AI Core Engine → Suggest Draft PES Statement
       ↓
Nutritionist → Review, Adjust, and Verify PES Diagnosis
       ↓
Verify Drug-Nutrition Interactions + Generate Localized Food Plans
       ↓
Conclude Visit Session → Schedule Follow-up (If Required)
       ↓
Data Pipeline → Population Analytics Dashboard (Admin View Only)

```

---



