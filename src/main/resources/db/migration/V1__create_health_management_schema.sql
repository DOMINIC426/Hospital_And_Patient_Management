CREATE TABLE users (
    user_id BIGSERIAL PRIMARY KEY,
    role VARCHAR(50),
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    dob DATE,
    sex VARCHAR(20),
    contact_phone VARCHAR(50),
    insurance_status VARCHAR(255),
    username VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255)
);

CREATE TABLE medical_history (
    history_id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE,
    chronic_conditions TEXT,
    current_medications TEXT,
    allergies TEXT,
    past_surgeries TEXT,
    family_history TEXT,
    CONSTRAINT fk_medical_history_user FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

CREATE TABLE visits (
    visit_id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    visit_date DATE,
    chief_complaint VARCHAR(255),
    duration VARCHAR(255),
    severity VARCHAR(255),
    associated_symptoms TEXT,
    onset VARCHAR(255),
    triage_level VARCHAR(20),
    clinician_notes TEXT,
    CONSTRAINT fk_visits_user FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

CREATE TABLE nutrition_assessment (
    nutrition_id BIGSERIAL PRIMARY KEY,
    visit_id BIGINT NOT NULL UNIQUE,
    daily_meals TEXT,
    dietary_restrictions TEXT,
    water_intake VARCHAR(255),
    physical_activity_level VARCHAR(255),
    sleep_hours DOUBLE PRECISION,
    alcohol_use VARCHAR(255),
    tobacco_use VARCHAR(255),
    CONSTRAINT fk_nutrition_assessment_visit FOREIGN KEY (visit_id) REFERENCES visits(visit_id) ON DELETE CASCADE
);

CREATE TABLE anthropometrics (
    anthro_id BIGSERIAL PRIMARY KEY,
    visit_id BIGINT NOT NULL UNIQUE,
    weight DOUBLE PRECISION,
    height DOUBLE PRECISION,
    muac DOUBLE PRECISION,
    waist_circumference DOUBLE PRECISION,
    hip_circumference DOUBLE PRECISION,
    bmi DOUBLE PRECISION,
    bmi_for_age_z_score DOUBLE PRECISION,
    whr DOUBLE PRECISION,
    blood_pressure VARCHAR(50),
    hemoglobin DOUBLE PRECISION,
    blood_glucose DOUBLE PRECISION,
    CONSTRAINT fk_anthropometrics_visit FOREIGN KEY (visit_id) REFERENCES visits(visit_id) ON DELETE CASCADE
);

CREATE TABLE mental_health_screen (
    mental_id BIGSERIAL PRIMARY KEY,
    visit_id BIGINT NOT NULL UNIQUE,
    stress_level VARCHAR(255),
    sleep_quality VARCHAR(255),
    phq2_score INTEGER,
    gad2_score INTEGER,
    referral_flag BOOLEAN,
    CONSTRAINT fk_mental_health_screen_visit FOREIGN KEY (visit_id) REFERENCES visits(visit_id) ON DELETE CASCADE
);

CREATE TABLE laboratory_results (
    lab_id BIGSERIAL PRIMARY KEY,
    visit_id BIGINT NOT NULL UNIQUE,
    cbc TEXT,
    hba1c DOUBLE PRECISION,
    lipid_profile TEXT,
    creatinine DOUBLE PRECISION,
    liver_function TEXT,
    electrolytes TEXT,
    urinalysis TEXT,
    CONSTRAINT fk_laboratory_results_visit FOREIGN KEY (visit_id) REFERENCES visits(visit_id) ON DELETE CASCADE
);

CREATE TABLE nutrition_diagnosis (
    diagnosis_id BIGSERIAL PRIMARY KEY,
    visit_id BIGINT NOT NULL UNIQUE,
    problem TEXT,
    etiology TEXT,
    signs_symptoms TEXT,
    intervention TEXT,
    prescription TEXT,
    monitoring_indicators TEXT,
    CONSTRAINT fk_nutrition_diagnosis_visit FOREIGN KEY (visit_id) REFERENCES visits(visit_id) ON DELETE CASCADE
);

CREATE TABLE appointments (
    appointment_id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    clinician_id BIGINT NOT NULL,
    date_time TIMESTAMP,
    queue_number INTEGER,
    status VARCHAR(50),
    CONSTRAINT fk_appointments_patient FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    CONSTRAINT fk_appointments_clinician FOREIGN KEY (clinician_id) REFERENCES users(user_id) ON DELETE CASCADE
);

CREATE TABLE drug_nutrition_interactions (
    interaction_id BIGSERIAL PRIMARY KEY,
    medication_name VARCHAR(255),
    interaction_description TEXT,
    recommendation TEXT
);

CREATE TABLE food_recommendations (
    food_id BIGSERIAL PRIMARY KEY,
    category VARCHAR(50),
    local_food_name VARCHAR(255),
    recommendation_type VARCHAR(50)
);

CREATE TABLE population_analytics (
    analytics_id BIGSERIAL PRIMARY KEY,
    month VARCHAR(255),
    top_symptoms TEXT,
    nutrition_status TEXT,
    mental_health_trends TEXT,
    disease_outbreaks TEXT
);

CREATE TABLE security_logs (
    log_id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    action VARCHAR(255),
    timestamp TIMESTAMP,
    CONSTRAINT fk_security_logs_user FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);
