DROP SCHEMA IF EXISTS smack;

CREATE SCHEMA smack;

USE smack;

CREATE TABLE user(
  user_email char(128) not null,
  displayname char(64) not null,
  timestamp_created int not null DEFAULT(UNIX_TIMESTAMP()),

  PRIMARY KEY(user_email)
);

CREATE TABLE auth (
  auth_id char(32) not null,
  auth_email char(128) not null,
  auth_password BINARY(60) not null,
  timestamp_created int not null DEFAULT(UNIX_TIMESTAMP()),

  PRIMARY KEY(auth_id),
  CONSTRAINT fk_user_auth_email
    FOREIGN KEY(auth_email)
    REFERENCES user(user_email)
);

CREATE TABLE chat (
  chat_id char(32) not null,
  chat_name char(32) not null,
  timestamp_created int not null DEFAULT(UNIX_TIMESTAMP()),

  PRIMARY KEY(chat_id)
);

CREATE TABLE subscription (
  subscription_id char(32) not null,
  user_email char(32) not null,
  chat_id char(32) not null,
  timestamp_joined int not null DEFAULT(UNIX_TIMESTAMP()),
  timestamp_left int DEFAULT(null),

  PRIMARY KEY(subscription_id),
  CONSTRAINT fk_user_user_email
    FOREIGN KEY(user_email)
    REFERENCES user(user_email),
  CONSTRAINT fk_chat_chat_id
    FOREIGN KEY(chat_id)
    REFERENCES chat(chat_id)
);

