-- V1__create_enums.sql

-- Gender Enum
CREATE TYPE gender AS ENUM (
  'MALE',
  'FEMALE',
  'OTHER'
);

-- Role Type Enum
CREATE TYPE role_type AS ENUM (
  'ADMIN',
  'EMPLOYEE',
  'CUSTOMER'
);

-- Payment Method Enum
CREATE TYPE payment_method AS ENUM (
  'CARD',
  'PIX',
  'CASH'
);

-- Payment Status Enum
CREATE TYPE payment_status AS ENUM (
  'PAID',
  'PENDING',
  'FAILED',
  'CANCELED'
);

-- Subscription Status Enum
CREATE TYPE subscription_status AS ENUM (
  'ACTIVE',
  'EXPIRED',
  'CANCELED'
);

-- Booking Status Enum
CREATE TYPE booking_status AS ENUM (
  'CONFIRMED',
  'CANCELED'
);

-- Check-in Status Enum
CREATE TYPE checkin_status AS ENUM (
  'PRESENT',
  'ABSENT',
  'LATE'
);

-- Group Type Enum
CREATE TYPE group_type AS ENUM (
  'PUBLIC',
  'PRIVATE',
  'COMPETITIVE'
);
