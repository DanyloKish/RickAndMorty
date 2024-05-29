--liquibase formatted sql
--changeset <DanilKi>:<create-characters-table> splitStatements:true endDelimiter:;
CREATE TABLE IF NOT EXISTS characters
(
  id BIGINT AUTO_INCREMENT NOT NULL,
  external_id BIGINT NOT NULL,
  name VARCHAR(100) NOT NULL,
  status TINYINT NOT NULL,
  gender TINYINT NOT NULL,
  CONSTRAINT pk_characters PRIMARY KEY (id)
);

--rollback DROP TABLE characters;
