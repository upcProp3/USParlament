CREATE TABLE mp (
    id BIGINT PRIMARY KEY generated always as identity, 
    fullname VARCHAR(255),
    state VARCHAR(2),
    district INT );
