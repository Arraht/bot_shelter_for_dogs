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
message TEXT
)
-- changeset Igor:3
CREATE TABLE Client(
id SERIAL,
name TEXT,
chatId INTEGER
)
-- changeset Igor:4
CREATE TABLE BotTalkClient(
id SERIAL,
answerId INTEGER,
name TEXT,
chatId INTEGER,
timeSendMessage TIMESTAMP,
initialSend TEXT,
successOfSending BOOLEAN
)
-- changeset Igor:5
CREATE TABLE VolunteersTalkClient(
id SERIAL,
nameVolunteers TEXT,
nameClient TEXT,
chatId INTEGER,
appointmentTime TIMESTAMP
)