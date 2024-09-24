CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS platform_users
(
    id            uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    user_name     VARCHAR NOT NULL,
    original_name VARCHAR,
    user_email    VARCHAR NOT NULL,
    password      VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS otp_sessions
(
    id               uuid PRIMARY KEY     DEFAULT gen_random_uuid(),
    session_name     VARCHAR     NOT NULL,
    session_start    TIMESTAMPTZ NOT NULL DEFAULT now(),
    session_expiry   TIMESTAMPTZ NOT NULL,
    session_complete BOOLEAN              DEFAULT FALSE,
    session_otp      VARCHAR     NOT NULL,
    owner            UUID        NOT NULL REFERENCES platform_users (id)
);

CREATE TABLE IF NOT EXISTS otp_attendance
(
    id            uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    platform_user uuid NOT NULL REFERENCES platform_users (id),
    session       uuid NOT NULL REFERENCES otp_sessions (id)
);