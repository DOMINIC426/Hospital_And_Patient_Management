

-- Alter the column type from TIMESTAMP to DATE.
-- The USING clause handles casting existing date-time strings or records down to a date value cleanly.
ALTER TABLE appointments
    ALTER COLUMN date_time TYPE DATE
        USING date_time::DATE;