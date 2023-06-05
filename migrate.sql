-- Database: point_earned

-- DROP DATABASE IF EXISTS point_earned;

CREATE DATABASE point_earned
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'C'
    LC_CTYPE = 'C'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;


-- CREATE users table

CREATE TABLE users (
	id serial primary key,
	phone_number varchar(20),
	email varchar(100),
	username varchar(100) not null,
	user_type varchar(10) not null,
	qr_url text,
	created_at timestamp,
	updated_at timestamp,
	deleted_at timestamp
);

-- CREATE point count table
-- to count the purchases that the user made from the specific store

CREATE TABLE point_count (
	user_id integer not null,
	store_id integer not null,
	purchased_count integer default 0,
	user_rank varchar(20),
	discount_remain integer default 0,
	created_at timestamp,
	updated_at timestamp,
	deleted_at timestamp,
	PRIMARY KEY(user_id, store_id)
);

-- CREATE point count table
-- store custom discount and rank set by each store

CREATE TABLE discount_setting(
	store_id integer not null,
	user_rank varchar(20) not null,
	least_number integer,
	discount float,
	discount_limit integer,
	created_at timestamp,
	updated_at timestamp,
	deleted_at timestamp,
	primary key (store_id, user_rank)
);

-- add foreign key for discount id