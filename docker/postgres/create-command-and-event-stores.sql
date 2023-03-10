CREATE DATABASE "secret-santa";

\connect "secret-santa";
CREATE SCHEMA cqrs;

CREATE TABLE cqrs.commands (
	id varchar(36) NOT NULL,
    created_at timestamp NOT NULL,
    hash varchar(64) NOT NULL,
	name varchar(128) NOT NULL,
	id_entity varchar(36) NOT NULL,
	"data" jsonb NULL,
	error varchar(512),
	CONSTRAINT commands_hash_unique UNIQUE (hash)
);

CREATE TABLE cqrs.events (
	id varchar(36) NOT NULL,
    created_at timestamp NOT NULL,
    hash varchar(64) NOT NULL,
    name varchar(128) NOT NULL,
    id_entity varchar(36) NOT NULL,
    "data" jsonb NULL,
    CONSTRAINT events_hash_unique UNIQUE (hash)
);
