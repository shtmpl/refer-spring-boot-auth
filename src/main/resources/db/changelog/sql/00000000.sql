--liquibase formatted sql

--changeset ibardych:0000000000042-1

CREATE SEQUENCE account_id_seq
  START WITH 1
  INCREMENT BY 1
  NO MINVALUE
  NO MAXVALUE
  CACHE 1;

CREATE TABLE account
(
  id         BIGINT PRIMARY KEY DEFAULT nextval('account_id_seq'),
  created_at TIMESTAMP WITH TIME ZONE,
  username   VARCHAR(255),
  password   VARCHAR(255)
);

ALTER TABLE account
  ADD CONSTRAINT account_username_key UNIQUE (username);
