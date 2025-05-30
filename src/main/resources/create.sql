-- 1. Create the user
CREATE USER library_user WITH PASSWORD 'library_password';

-- 2. Create the database
CREATE DATABASE library_db;

-- Step 3 onward must be run inside the `library_db` database:
\c library_db

-- 3. Grant connect privilege to the user (already implicit if you GRANT schema/table access)
GRANT CONNECT ON DATABASE library_db TO library_user;

-- 4. Grant usage on the public schema
GRANT USAGE ON SCHEMA public TO library_user;

-- 5. Grant CRUD privileges on all existing tables
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA public TO library_user;

-- 6. Grant privileges on future tables in this schema
ALTER DEFAULT PRIVILEGES IN SCHEMA public
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO library_user;

-- 7. Grant privileges on all sequences (existing)
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA public TO library_user;

-- 8. Ensure the `books_id_seq` sequence is synced with table data (do this after you have inserted or imported existing books)
SELECT setval('books_id_seq', (SELECT MAX(id) FROM books));
