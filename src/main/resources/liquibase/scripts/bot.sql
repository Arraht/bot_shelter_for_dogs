-- liquibase formatted sql

-- changeset Igor:1
CREATE TABLE volunteers(
id SERIAL,
name TEXT,
nickName TEXT,
workingFirstTime TIMESTAMP,
workingLastTime TIMESTAMP
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
CREATE TABLE BotTalkClient(
id SERIAL,
answerId INTEGER,
name TEXT,
chat_id INTEGER,
timeSendMessage TIMESTAMP,
initialSend TEXT,
successOfSending BOOLEAN
)
-- changeset Igor:5
CREATE TABLE VolunteersTalkClient(
id SERIAL,
nameVolunteers TEXT,
nameClient TEXT,
chat_id INTEGER,
appointmentTime TIMESTAMP
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

