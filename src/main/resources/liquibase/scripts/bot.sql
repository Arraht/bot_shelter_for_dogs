-- liquibase formatted sql

-- changeset Igor:1
CREATE TABLE volunteers(
id SERIAL,
name TEXT,
nickName TEXT,
year INTEGER,
workingFirstTime TIMESTAMP,
workingLastTime TIMESTAMP
);
-- changeset Igor:2
CREATE TABLE answer(
id SERIAL PRIMARY KEY,
command TEXT,
message TEXT
);
-- changeset Igor:3
CREATE TABLE BotTalkClient(
id SERIAL,
answerId INTEGER PRIMARY KEY REFERENCES answer(id),
name TEXT,
chatId INTEGER,
timeSendMessage TIMESTAMP,
initialSend TEXT,
message TEXT,
successOfSending BOOLEAN
)