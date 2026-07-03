ALTER TABLE appointments
    ALTER COLUMN date_time TYPE DATE
        USING date_time::DATE;