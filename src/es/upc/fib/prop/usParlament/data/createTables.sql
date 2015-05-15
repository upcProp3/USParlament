CREATE TABLE lesson (
    id BIGINT PRIMARY KEY generated always as identity, 
    name VARCHAR(255), 
    date DATE );

CREATE TABLE student (
    id BIGINT PRIMARY KEY generated always as identity,
    name VARCHAR(255),
    surname VARCHAR(255),
    birthdate DATE );

CREATE TABLE attendance (
    id BIGINT PRIMARY KEY generated always as identity,
    lesson BIGINT,
    student BIGINT,
    FOREIGN KEY (lesson) REFERENCES LESSON(id),
    FOREIGN KEY (student) REFERENCES STUDENT(id) );

