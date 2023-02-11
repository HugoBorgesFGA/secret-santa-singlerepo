CREATE DATABASE "write";
\connect "write";
CREATE SCHEMA commands;
CREATE TABLE commands.store (
	id varchar(36) NOT NULL,
	created_at timestamp NOT NULL,
	id_entity varchar(36) NOT NULL,
	id_command varchar(256) NOT NULL,
	"data" jsonb NULL,
	error varchar(128) NOT NULL
);

\disconnect;

CREATE DATABASE "read";
\connect "read"
CREATE SCHEMA events;
CREATE TABLE events.store (
	id varchar(36) NOT NULL,
	created_at timestamp NOT NULL,
	id_entity varchar(36) NOT NULL,
	id_event varchar(256) NOT NULL,
	"data" jsonb NULL
);
