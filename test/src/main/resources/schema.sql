CREATE TABLE `people` (
  `id` SMALLINT NOT NULL AUTO_INCREMENT,
  `name` TEXT NULL,
  `age` TINYINT NULL,
  PRIMARY KEY (`id`)
) COLLATE = 'utf8_general_ci' ENGINE = InnoDB;