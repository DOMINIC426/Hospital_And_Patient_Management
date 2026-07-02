Sawa bro, nakueleza kwa Kiswahili kabisa! 🇹🇿

---

## Mfumo wa Health Management System — Maelezo ya Kiswahili

---

### 👤 HATUA YA 1 — Usajili na Kuingia Mfumo

Mtu yeyote anayetaka kutumia mfumo lazima **ajisajili kwanza**. Wakati wa usajili, anaweka:
- Jina lake, tarehe ya kuzaliwa, jinsia, namba ya simu, email, na nenosiri
- **Role yake** — ni mgonjwa (Patient), daktari (Clinician), mtaalamu wa lishe (Nutritionist), au msimamizi (Administrator)?

Mfumo unasave nenosiri kama **BCrypt hash** (sio plain text — hii ni usalama). Kisha unamrudishia **JWT token** ambayo itatumika kama "kitambulisho cha kidijitali" kwa muda wa dakika 300. Kila ombi (request) analotuma baadaye lazima liwe na token hiyo kwenye header — kama hana, mfumo unakataa moja kwa moja na kurudisha **401 Unauthorized**.

---

### 🏥 HATUA YA 2 — Mgonjwa Anaingia na Kujaza Historia ya Afya

Baada ya kujisajili, **mgonjwa (Patient)** anajaza **Medical History** yake mara moja — hii ni rekodi moja tu kwa kila mgonjwa (One-to-One). Anaweka:
- Magonjwa ya kudumu (k.m. kisukari, shinikizo la damu)
- Dawa anazotumia sasa hivi
- Mzio wake (allergies)
- Upasuaji aliopitia zamani
- Historia ya magonjwa ya familia

Hii ni muhimu sana kwa sababu wakati daktari ataangalia mgonjwa, ataona historia hii yote na kufanya maamuzi bora zaidi.

---

### 📅 HATUA YA 3 — Mgonjwa Anaweka Miadi (Appointment)

Mgonjwa anaweza **kuweka miadi** na daktari. Wakati anaweka miadi:
- Anachagua daktari (Clinician) anayetaka kumwona
- Mfumo unaweka tarehe na saa
- Mfumo unampa **namba ya foleni (Queue Number)** automatically — k.m. "Namba 7"
- Hali ya miadi inaanza kama **BOOKED**

Daktari anaweza kuona orodha ya miadi yake yote. Admin anaweza kuona miadi za kliniki nzima.

---

### 🩺 HATUA YA 4 — Ziara Mpya (New Visit) — Hapa Ndipo Kazi Kubwa Inaanza

Mgonjwa akifika kliniki, **daktari (Clinician)** anafungua **Visit mpya** kwa mgonjwa huyo. Visit ndiyo "mlango" wa kila kitu kingine — kila kipimo, kila tathmini, kila matokeo ya maabara — vyote vinaunganishwa na Visit moja.

Katika Visit, daktari anaweka:
- **Malalamiko makuu** ya mgonjwa (Chief Complaint) — k.m. "Maumivu ya tumbo siku 3"
- **Muda** malalamiko yamekuwepo, **ukali** wake, **dalili nyingine** zinazohusiana
- **Wakati** dalili zilianza (Onset)

---

### 🤖 HATUA YA 5 — AI Inachunguza na Kupendekeza Kiwango cha Hatari (Triage)

Baada ya daktari kuweka malalamiko, **AI (Google Gemini)** inachunguza taarifa zote na kupendekeza **Triage Level**:

| Rangi | Maana |
|---|---|
| 🟢 **GREEN** | Hali ya kawaida, si ya haraka |
| 🟡 **YELLOW** | Inahitaji uangalifu, angalia haraka |
| 🟠 **ORANGE** | Hali mbaya, mhudumie haraka |
| 🔴 **RED** | Dharura kamili — mhudumie SASA HIVI |

**Muhimu:** AI inapendekeza tu — daktari ndiye anayethibitisha au kubadilisha. AI haifanyi maamuzi peke yake.

---

### 📏 HATUA YA 6 — Vipimo vya Mwili (Anthropometrics)

Daktari au muuguzi anaweka **vipimo vya mwili** vya mgonjwa:
- Uzito, urefu, shinikizo la damu, hemoglobini, sukari ya damu
- MUAC (kipimo cha mkono — kinaonyesha utapiamlo)
- Kipimo cha kiuno na nyonga (WHR)

Mfumo **unakokotoa BMI na WHR automatically** — huhitaji kufanya hesabu wewe mwenyewe, unaweka uzito na urefu tu, mfumo unafanya kazi.

---

### 🥗 HATUA YA 7 — Tathmini ya Lishe (Nutrition Assessment)

**Mtaalamu wa lishe (Nutritionist)** anaingia na kufanya tathmini ya lishe. Anauliza mgonjwa na kuweka:
- Anakula mara ngapi kwa siku, chakula gani
- Vikwazo vya chakula (k.m. hali ya kisukari, uislamu — haramu, allergies)
- Maji anayokunywa kwa siku
- Shughuli za mwili (Physical Activity Level)
- Masaa ya usingizi, matumizi ya pombe, uvutaji sigara

---

### 🧠 HATUA YA 8 — Uchunguzi wa Afya ya Akili (Mental Health Screening)

Katika ziara hiyo hiyo, daktari anafanya **uchunguzi mfupi wa afya ya akili**:
- Kiwango cha msongo wa mawazo (Stress Level)
- Ubora wa usingizi
- **PHQ-2 Score** — maswali 2 ya unyogovu (depression)
- **GAD-2 Score** — maswali 2 ya wasiwasi (anxiety)

**Mfumo una sheria moja ya kiotomatiki:** Kama PHQ-2 ≥ 3 **au** GAD-2 ≥ 3, mfumo unaweka **Referral Flag = TRUE** automatically — inamaanisha mgonjwa huyu anahitaji kupelekwa kwa mtaalamu wa afya ya akili.

---

### 🔬 HATUA YA 9 — Matokeo ya Maabara (Laboratory Results)

Daktari anaweza kuweka matokeo ya vipimo vya damu na mkojo:
- CBC (Complete Blood Count — hesabu ya chembe za damu)
- HbA1c (kiashiria cha kisukari cha muda mrefu)
- Lipid Profile (mafuta ya damu)
- Creatinine (hali ya figo)
- Liver Function (hali ya ini)
- Electrolytes na Urinalysis (mkojo)

Hizi zinaenda kwa mtaalamu wa lishe pia ili aweze kufanya diagnosis sahihi.

---

### 📋 HATUA YA 10 — Utambuzi wa Lishe (PES Diagnosis)

Hii ndiyo hatua kuu ya **mtaalamu wa lishe**. Anafanya utambuzi kwa muundo wa PES:

- **P — Problem:** Tatizo ni nini? (k.m. "Utapiamlo wa protini")
- **E — Etiology:** Chanzo ni nini? (k.m. "Ulaji mbaya wa chakula chenye protini")
- **S — Signs/Symptoms:** Dalili zipi zinaonyesha? (k.m. "MUAC chini ya 21cm, hemoglobini chini ya kawaida")

Kisha anaweka:
- **Intervention** — atafanya nini kurekebisha?
- **Prescription** — mgonjwa apewa nini?
- **Monitoring Indicators** — tutajua vipi kama mgonjwa anaboreka?

**AI inasaidia hapa pia** — inachunguza vipimo vyote vya mgonjwa (anthropometrics + lishe + maabara) na inapendekeza **rasimu ya PES statement**. Mtaalamu wa lishe anaangalia, anarekebisha kama inahitajika, kisha anathibitisha.

---

### 💊 HATUA YA 11 — Kuangalia Madhara ya Dawa na Lishe

Mfumo unachunguza **dawa zote** zilizo kwenye historia ya mgonjwa na kuzilinganisha na **jedwali la Drug-Nutrition Interactions**. Kama kuna dawa inayoathiri lishe, mfumo unatoa **onyo** kwa daktari na mtaalamu wa lishe — k.m.:

> "Warfarin + Vitamin K (mboga za kijani) — zinaathiriana. Pendekezo: Simamia ulaji wa mboga za kijani."

---

### 🍎 HATUA YA 12 — Mapendekezo ya Chakula cha Kienyeji

Badala ya kumwambia mgonjwa "kula protini zaidi" bila mfano, mfumo una **jedwali la vyakula vya kienyeji** tayari. AI inapendekeza chakula kinachofaa kulingana na hali ya mgonjwa:

- **Ongeza:** Maharage, samaki, maziwa (kwa tatizo la protini)
- **Epuka:** Chumvi nyingi, sukari (kwa shinikizo la damu au kisukari)

Hii inafanya ushauri wa lishe uwe wa vitendo na wa karibu na mazingira ya mgonjwa.

---

### 📊 HATUA YA 13 — Dashboard ya Takwimu za Watu (Population Analytics)

**Admin** peke yake anaona dashboard hii. Mfumo unakusanya data ya Visit zote (bila majina ya wagonjwa — anonymized) na kuonyesha kila mwezi:
- Dalili zinazojitokeza zaidi kliniki
- Hali ya lishe ya watu wanaokuja
- Mwenendo wa afya ya akili
- Dalili za mlipuko wa magonjwa (disease outbreaks)

Hii inasaidia Admin kupanga rasilimali za kliniki — k.m. "Mwezi huu watu wengi wana tatizo la sukari, tunahitaji dawa zaidi za kisukari."

---

### 🔐 HATUA YA 14 — Kumbukumbu za Usalama (Security Logs)

**Kila tendo** ambalo mtumiaji anafanya kwenye mfumo linawekwa kumbukumbu — nani alifanya nini na saa ngapi. Hii inaonekana na Admin tu. Kumbukumbu hizi **haziwezi kufutwa au kubadilishwa na mtu yeyote** — zinabaki daima kwa usalama na uwajibikaji.

---

## Muhtasari wa Mtiririko Wote

```
Jisajili → Ingia (pata JWT token)
    ↓
Jaza Historia ya Afya (Patient mara moja)
    ↓
Weka Miadi na Daktari
    ↓
Fika Kliniki → Daktari afungue Visit
    ↓
AI → Triage Level (Green/Yellow/Orange/Red)
    ↓
Vipimo vya Mwili + Lishe + Akili + Maabara
    ↓
AI → Pendekeza PES Diagnosis
    ↓
Mtaalamu wa Lishe → Thibitisha/Rekebisha PES
    ↓
Angalia Madhara ya Dawa + Pendekeza Chakula
    ↓
Ziara Imalizika → Miadi ifuatayo (kama inahitajika)
    ↓
Data → Population Analytics (Admin anaona kila mwezi)
```

---

Sasa unaelewa vizuri flow nzima bro. **Unaanza na Module ipi?** Ninapendekeza uanze na **Medical History** (Phase 2, hatua ya kwanza) kwa sababu ni rahisi — One-to-One na User, logic ndogo, utapata confidence kabla ya kuingia Visit ambayo ina sub-modules nyingi.