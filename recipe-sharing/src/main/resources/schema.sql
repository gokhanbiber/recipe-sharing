  DROP TABLE ingredients IF EXISTS;

  CREATE TABLE ingredients (
    id BIGINT NOT NULL PRIMARY KEY,
    unit VARCHAR(20),
    type VARCHAR(50),
    amount FLOAT,
    calorie INT
  );




