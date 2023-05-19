CREATE TABLE customer (
  customer_id BIGINT NOT NULL AUTO_INCREMENT,
  first_name VARCHAR(255) NOT NULL,
  last_name VARCHAR(255) NOT NULL,
  ssn VARCHAR(255) NOT NULL UNIQUE,
  PRIMARY KEY (customer_id)
);
