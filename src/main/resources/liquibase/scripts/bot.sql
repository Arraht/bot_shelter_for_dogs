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
-- changeset Sergei:7
CREATE TABLE picture(
id SERIAL,
filePath TEXT,
fileSize SERIAL,
MediaType TEXT,
data BYTEA
)
-- changeset Sergei:8
CREATE TABLE direction_to_shelter_picture(
shelter SERIAL
) INHERITS (picture);
-- changeset Sergei:9
ALTER TABLE shelter RENAME COLUMN directionsmap TO directionspicture;
-- changeset Sergei:10
ALTER TABLE shelter ALTER COLUMN directionspicture TYPE TEXT;
-- changeset Sergei:11
ALTER TABLE picture add primary key(id)
-- changeset Sergei:12
ALTER TABLE shelter ADD CONSTRAINT directionspicture_constraint foreign key (id) references picture
