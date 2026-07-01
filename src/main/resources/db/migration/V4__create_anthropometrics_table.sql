CREATE TABLE IF NOT EXISTS anthropometrics (
                                               anthro_id BIGSERIAL PRIMARY KEY,
                                               visit_id BIGINT NOT NULL,
                                               weight DOUBLE PRECISION,
                                               height DOUBLE PRECISION,
                                               muac DOUBLE PRECISION,
                                               waist_circumference DOUBLE PRECISION,
                                               hip_circumference DOUBLE PRECISION,
                                               bmi DOUBLE PRECISION,
                                               bmi_for_age_z_score DOUBLE PRECISION,
                                               whr DOUBLE PRECISION,
                                               blood_pressure VARCHAR(255),
                                               hemoglobin DOUBLE PRECISION,
                                               blood_glucose DOUBLE PRECISION,
                                               CONSTRAINT fk_anthropometrics_visit FOREIGN KEY (visit_id) REFERENCES visit(visit_id)
);