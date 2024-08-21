-- liquibase formated sql

--changeset Igor:1
CREATE TABLE volunteers(
id SERIAL,
name TEXT,
nickName TEXT,
year INTEGER,
workingFirstTime TIMESTAMP,
workingLastTime TIMESTAMP
);
CREATE TABLE answer(
id SERIAL PRIMARY KEY,
command TEXT,
message TEXT UNIQUE
);
CREATE TABLE BotTalkClient(
id SERIAL,
answerId INTEGER PRIMARY KEY,
name TEXT,
chatId INTEGER,
timeSendMessage TIMESTAMP,
initialSend TEXT,
message TEXT REFERENCES answer(message),
successOfSending BOOLEAN
)