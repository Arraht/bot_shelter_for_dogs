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
workSchedule TEXT,
directionsMap BYTEA,
securityContact TEXT,
generalRecommendationsOnSafety TEXT
)