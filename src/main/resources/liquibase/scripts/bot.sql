-- liquibase formatted sql

-- changeset Igor:1
CREATE TABLE volunteers(
id SERIAL,
name TEXT,
nick_name TEXT,
working_first_time TIMESTAMP,
working_last_time TIMESTAMP
)
-- changeset Igor:2
CREATE TABLE answer(
id SERIAL,
command TEXT,
chat_id INTEGER
)
-- changeset Igor:3
CREATE TABLE Client(
id SERIAL,
name TEXT,
chat_id INTEGER
)
-- changeset Igor:4
CREATE TABLE bot_talk_client(
id SERIAL,
answer_id INTEGER,
name TEXT,
chat_id INTEGER,
time_send_message TIMESTAMP,
initial_send TEXT,
success_of_sending BOOLEAN
)
-- changeset Igor:5
CREATE TABLE volunteers_talk_client(
id SERIAL,
name_volunteers TEXT,
name_client TEXT,
chat_id INTEGER,
appointment_time TIMESTAMP
)
-- changeset Sergei:6
CREATE TABLE shelter(
id SERIAL,
name TEXT,
address TEXT,
work_schedule TEXT,
security_contact TEXT,
general_recommendations_on_safety TEXT
)
-- changeset Sergei:7
CREATE TABLE picture(
id SERIAL,
file_path TEXT,
file_size SERIAL,
media_type TEXT,
data BYTEA
)
-- changeset Sergei:8
CREATE TABLE direction_to_shelter_picture(
shelter_id SERIAL
) INHERITS (picture);
-- changeset Sergei:9
ALTER TABLE picture add primary key(id)
-- changeset Sergei:10
ALTER TABLE shelter add primary key(id)
-- changeset Sergei:11
CREATE TABLE pet (
     id          BIGSERIAL    primary key
    ,class_pet   TEXT
    ,alias       TEXT
    ,age         SMALLINT     check(age >= 0)
    ,sex         BOOLEAN
    ,weight      NUMERIC(9,3) check(weight >= 0.0)
    ,diet        TEXT
    ,description TEXT
);
-- changeset Sergei:12
CREATE TABLE report(
     id                     BIGSERIAL    primary key
    ,time_created           TIMESTAMP
    ,time_received_text     TIMESTAMP
    ,text_from_client       TEXT
    ,time_received_photo    TIMESTAMP
    ,accepted               BOOLEAN
    ,volunteer_id           BIGSERIAL
    ,client_id              BIGSERIAL
    ,pet_id                 BIGSERIAL
);
-- changeset Sergei:13
CREATE TABLE pet_photo(
    pet_id      BIGSERIAL
)   INHERITS (picture);
-- changeset Sergei:14
CREATE TABLE photo_for_report(
    report_id      BIGSERIAL
)   INHERITS (picture);

-- changeset Igor:14
CREATE TABLE volunteers_chat_id(
id SERIAL,
volunteer_id INTEGER,
chat_id INTEGER
)
-- changeset Igor:15
ALTER TABLE Client
ADD COLUMN contact BIGINT

-- changeset Igor:16
ALTER TABLE Client
ADD COLUMN user_name TEXT

-- changeset Igor:17
CREATE TABLE report_from_bot(
id SERIAL,
file_size BIGINT,
file_id TEXT,
chat_id BIGINT,
user_name TEXT,
data_report_bot BYTEA
)
-- changeset Igor:18
ALTER TABLE report_from_bot
ADD COLUMN message_report_from_bot TEXT

-- changeset Igor:19
ALTER TABLE report_from_bot
ADD COLUMN time_send_report TIMESTAMP

-- changeset Igor:20
ALTER TABLE Client
ADD COLUMN parent BOOLEAN

-- changeset Igor:21
ALTER TABLE Client
ALTER COLUMN parent SET DEFAULT FALSE

-- changeset Igor:22
ALTER TABLE report_from_bot
ADD COLUMN time_complete TIMESTAMP