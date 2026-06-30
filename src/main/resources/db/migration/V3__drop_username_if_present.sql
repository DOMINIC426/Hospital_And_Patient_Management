DO $$
BEGIN
    IF EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_name = 'users' AND column_name = 'username'
    ) THEN
        ALTER TABLE users DROP CONSTRAINT IF EXISTS users_username_key;
        ALTER TABLE users DROP COLUMN username;
    END IF;
END $$;
