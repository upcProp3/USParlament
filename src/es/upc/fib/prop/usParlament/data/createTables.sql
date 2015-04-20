CREATE TABLE mp (
    id BIGINT PRIMARY KEY generated always as identity,
    name VARCHAR(255),
    state VARCHAR(2),
    district INT);

CREATE TABLE attrDefinition (
    id BIGINT PRIMARY KEY generated always as identity,
    name VARCHAR(255),
    importance INT );

CREATE TABLE attribute (
  id BIGINT PRIMARY KEY generated always as identity,
  attrDefinition BIGINT,
  mp BIGINT,
  value VARCHAR(255),
  FOREIGN KEY (attrDefinition) REFERENCES attrDefinition(id),
  FOREIGN KEY (mp) REFERENCES mp(id) );
