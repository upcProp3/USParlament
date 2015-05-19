CREATE TABLE mp (
    id BIGINT PRIMARY KEY generated always as identity, 
    fullname VARCHAR(255),
    state VARCHAR(2),
    district INT );

CREATE TABLE attrDefinition (
  id BIGINT PRIMARY KEY generated always as identity,
  name VARCHAR(255),
  importance INT );

CREATE TABLE attribute (
  value VARCHAR(255),
  mp BIGINT,
  attrDefinition BIGINT,
  FOREIGN KEY (mp) REFERENCES mp(id),
  FOREIGN KEY (attrDefinition) REFERENCES attrDefinition(id) );