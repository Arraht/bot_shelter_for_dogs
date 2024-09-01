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
CREATE TABLE BotTalkClient(
id SERIAL,
chat_id INTEGER,
name TEXT,
command TEXT,
timeSendMessage TIMESTAMP,
successOfSending BOOLEAN
)
-- changeset Igor:3
CREATE TABLE VolunteersTalkClient(
id SERIAL,
nameVolunteers TEXT,
nameClient TEXT,
chatId INTEGER,
appointmentTime TIMESTAMP
)
-- changeset Sergei:4
CREATE TABLE shelter(
id SERIAL,
name TEXT,
address TEXT,
workSchedule TEXT,
directionsMap BYTEA,
securityContact TEXT,
generalRecommendationsOnSafety TEXT
)