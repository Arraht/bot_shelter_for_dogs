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
id SERIAL,
command TEXT,
message TEXT
);
CREATE TABLE BotTalkClient(
id SERIAL,
name TEXT,
chatId INTEGER,
timeSendMessage TIMESTAMP,
initialSend TEXT,
answerMessage TEXT,
successOfSending BOOLEAN
)