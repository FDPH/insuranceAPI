-- MySQL Script generated by MySQL Workbench
-- Thu Oct 17 10:20:47 2024
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema insuranceDB
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `insuranceDB` ;

-- -----------------------------------------------------
-- Schema insuranceDB
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `insuranceDB` DEFAULT CHARACTER SET utf8 ;
USE `insuranceDB` ;

-- -----------------------------------------------------
-- Table `insuranceDB`.`insuranceType`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `insuranceDB`.`insuranceType` ;

CREATE TABLE IF NOT EXISTS `insuranceDB`.`insuranceType` (
                                                             `id_insurance_type` INT NOT NULL AUTO_INCREMENT,
                                                             `insurance_name` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id_insurance_type`),
    UNIQUE INDEX `idProtectionType_UNIQUE` (`id_insurance_type` ASC) VISIBLE)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `insuranceDB`.`documentTypes`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `insuranceDB`.`documentTypes` ;

CREATE TABLE IF NOT EXISTS `insuranceDB`.`documentTypes` (
                                                             `id_document` INT NOT NULL AUTO_INCREMENT,
                                                             `document_name` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id_document`),
    UNIQUE INDEX `idDocumentTypes_UNIQUE` (`id_document` ASC) VISIBLE)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `insuranceDB`.`gender`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `insuranceDB`.`gender` ;

CREATE TABLE IF NOT EXISTS `insuranceDB`.`gender` (
                                                      `id_gender` INT NOT NULL AUTO_INCREMENT,
                                                      `gender_type` VARCHAR(45) NULL,
    PRIMARY KEY (`id_gender`),
    UNIQUE INDEX `idgender_UNIQUE` (`id_gender` ASC) VISIBLE)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `insuranceDB`.`insuranceDetailRange`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `insuranceDB`.`insuranceDetailRange` ;

CREATE TABLE IF NOT EXISTS `insuranceDB`.`insuranceDetailRange` (
                                                                    `id_insurance` INT NOT NULL AUTO_INCREMENT,
                                                                    `min_age` INT NOT NULL,
                                                                    `max_age` INT NOT NULL,
                                                                    `prima_porcentage` DECIMAL(6,5) NOT NULL,
    `insuranceType_id_insurance_type` INT NOT NULL,
    UNIQUE INDEX `idInsurance_UNIQUE` (`id_insurance` ASC) VISIBLE,
    PRIMARY KEY (`id_insurance`),
    INDEX `fk_insuranceDetailRange_insuranceType_idx` (`insuranceType_id_insurance_type` ASC) VISIBLE,
    CONSTRAINT `fk_insuranceDetailRange_insuranceType`
    FOREIGN KEY (`insuranceType_id_insurance_type`)
    REFERENCES `insuranceDB`.`insuranceType` (`id_insurance_type`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `insuranceDB`.`insuredUser`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `insuranceDB`.`insuredUser` ;

CREATE TABLE IF NOT EXISTS `insuranceDB`.`insuredUser` (
                                                           `id_insured_user` INT NOT NULL AUTO_INCREMENT,
                                                           `document_number` VARCHAR(45) NOT NULL,
    `first_name` VARCHAR(45) NOT NULL,
    `last_name` VARCHAR(45) NOT NULL,
    `date_of_birth` DATE NOT NULL,
    `documentTypes_id_document` INT NOT NULL,
    `gender_id_gender` INT NOT NULL,
    PRIMARY KEY (`id_insured_user`),
    UNIQUE INDEX `idinsuredUser_UNIQUE` (`id_insured_user` ASC) VISIBLE,
    INDEX `fk_insuredUser_documentTypes1_idx` (`documentTypes_id_document` ASC) VISIBLE,
    INDEX `fk_insuredUser_gender1_idx` (`gender_id_gender` ASC) VISIBLE,
    CONSTRAINT `fk_insuredUser_documentTypes1`
    FOREIGN KEY (`documentTypes_id_document`)
    REFERENCES `insuranceDB`.`documentTypes` (`id_document`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `fk_insuredUser_gender1`
    FOREIGN KEY (`gender_id_gender`)
    REFERENCES `insuranceDB`.`gender` (`id_gender`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `insuranceDB`.`insuredUser_has_insuranceType`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `insuranceDB`.`insuredUser_has_insuranceType` ;

CREATE TABLE IF NOT EXISTS `insuranceDB`.`insuredUser_has_insuranceType` (
                                                                             `init_date` DATE NULL,
                                                                             `end_date` DATE NULL,
                                                                             `insuranceType_id_insurance_type` INT NOT NULL,
                                                                             `insuredUser_id_insured_user` INT NOT NULL,
                                                                             PRIMARY KEY (`insuranceType_id_insurance_type`, `insuredUser_id_insured_user`),
    INDEX `fk_insuredUser_has_insuranceType_insuredUser1_idx` (`insuredUser_id_insured_user` ASC) VISIBLE,
    CONSTRAINT `fk_insuredUser_has_insuranceType_insuranceType1`
    FOREIGN KEY (`insuranceType_id_insurance_type`)
    REFERENCES `insuranceDB`.`insuranceType` (`id_insurance_type`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `fk_insuredUser_has_insuranceType_insuredUser1`
    FOREIGN KEY (`insuredUser_id_insured_user`)
    REFERENCES `insuranceDB`.`insuredUser` (`id_insured_user`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `insuranceDB`.`insuranceType`
-- -----------------------------------------------------
START TRANSACTION;
USE `insuranceDB`;
INSERT INTO `insuranceDB`.`insuranceType` (`id_insurance_type`, `insurance_name`) VALUES (1, 'Muerte accidental');
INSERT INTO `insuranceDB`.`insuranceType` (`id_insurance_type`, `insurance_name`) VALUES (2, 'Desmembración');
INSERT INTO `insuranceDB`.`insuranceType` (`id_insurance_type`, `insurance_name`) VALUES (3, 'Auxilio funerario');
INSERT INTO `insuranceDB`.`insuranceType` (`id_insurance_type`, `insurance_name`) VALUES (4, 'Renta vitalicia');

COMMIT;


-- -----------------------------------------------------
-- Data for table `insuranceDB`.`documentTypes`
-- -----------------------------------------------------
START TRANSACTION;
USE `insuranceDB`;
INSERT INTO `insuranceDB`.`documentTypes` (`id_document`, `document_name`) VALUES (1, 'CC');
INSERT INTO `insuranceDB`.`documentTypes` (`id_document`, `document_name`) VALUES (2, 'CE');

COMMIT;


-- -----------------------------------------------------
-- Data for table `insuranceDB`.`gender`
-- -----------------------------------------------------
START TRANSACTION;
USE `insuranceDB`;
INSERT INTO `insuranceDB`.`gender` (`id_gender`, `gender_type`) VALUES (1, 'Masculino');
INSERT INTO `insuranceDB`.`gender` (`id_gender`, `gender_type`) VALUES (2, 'Femenino');

COMMIT;


-- -----------------------------------------------------
-- Data for table `insuranceDB`.`insuranceDetailRange`
-- -----------------------------------------------------
START TRANSACTION;
USE `insuranceDB`;
INSERT INTO `insuranceDB`.`insuranceDetailRange` (`id_insurance`, `min_age`, `max_age`, `prima_porcentage`, `insuranceType_id_insurance_type`) VALUES (DEFAULT, 18, 45, 0.02304, 1);
INSERT INTO `insuranceDB`.`insuranceDetailRange` (`id_insurance`, `min_age`, `max_age`, `prima_porcentage`, `insuranceType_id_insurance_type`) VALUES (DEFAULT, 46, 75, 0.02012, 1);
INSERT INTO `insuranceDB`.`insuranceDetailRange` (`id_insurance`, `min_age`, `max_age`, `prima_porcentage`, `insuranceType_id_insurance_type`) VALUES (DEFAULT, 18, 50, 0.1809, 2);
INSERT INTO `insuranceDB`.`insuranceDetailRange` (`id_insurance`, `min_age`, `max_age`, `prima_porcentage`, `insuranceType_id_insurance_type`) VALUES (DEFAULT, 51, 70, 0.16043, 2);
INSERT INTO `insuranceDB`.`insuranceDetailRange` (`id_insurance`, `min_age`, `max_age`, `prima_porcentage`, `insuranceType_id_insurance_type`) VALUES (DEFAULT, 18, 60, 0.14123, 3);
INSERT INTO `insuranceDB`.`insuranceDetailRange` (`id_insurance`, `min_age`, `max_age`, `prima_porcentage`, `insuranceType_id_insurance_type`) VALUES (DEFAULT, 61, 70, 0.1545, 3);
INSERT INTO `insuranceDB`.`insuranceDetailRange` (`id_insurance`, `min_age`, `max_age`, `prima_porcentage`, `insuranceType_id_insurance_type`) VALUES (DEFAULT, 18, 50, 0.12123, 4);
INSERT INTO `insuranceDB`.`insuranceDetailRange` (`id_insurance`, `min_age`, `max_age`, `prima_porcentage`, `insuranceType_id_insurance_type`) VALUES (DEFAULT, 51, 70, 0.1345, 4);

COMMIT;


-- -----------------------------------------------------
-- Data for table `insuranceDB`.`insuredUser`
-- -----------------------------------------------------
START TRANSACTION;
USE `insuranceDB`;
INSERT INTO `insuranceDB`.`insuredUser` (`id_insured_user`, `document_number`, `first_name`, `last_name`, `date_of_birth`, `documentTypes_id_document`, `gender_id_gender`) VALUES (DEFAULT, '79000001', 'NOMBRE1', 'APELLIDO1', '1945-01-10', 1, 1);
INSERT INTO `insuranceDB`.`insuredUser` (`id_insured_user`, `document_number`, `first_name`, `last_name`, `date_of_birth`, `documentTypes_id_document`, `gender_id_gender`) VALUES (DEFAULT, '79000002', 'NOMBRE2', 'APELLIDO2', '1950-01-10', 1, 1);
INSERT INTO `insuranceDB`.`insuredUser` (`id_insured_user`, `document_number`, `first_name`, `last_name`, `date_of_birth`, `documentTypes_id_document`, `gender_id_gender`) VALUES (DEFAULT, '79000003', 'NOMBRE3', 'APELLIDO3', '1955-01-10', 1, 1);
INSERT INTO `insuranceDB`.`insuredUser` (`id_insured_user`, `document_number`, `first_name`, `last_name`, `date_of_birth`, `documentTypes_id_document`, `gender_id_gender`) VALUES (DEFAULT, '51000001', 'NOMBRE4', 'APELLIDO4', '1960-01-10', 2, 2);
INSERT INTO `insuranceDB`.`insuredUser` (`id_insured_user`, `document_number`, `first_name`, `last_name`, `date_of_birth`, `documentTypes_id_document`, `gender_id_gender`) VALUES (DEFAULT, '51000002', 'NOMBRE5', 'APELLIDO5', '1965-01-10', 2, 2);
INSERT INTO `insuranceDB`.`insuredUser` (`id_insured_user`, `document_number`, `first_name`, `last_name`, `date_of_birth`, `documentTypes_id_document`, `gender_id_gender`) VALUES (DEFAULT, '51000003', 'NOMBRE6', 'APELLIDO6', '1970-01-10', 2, 2);

COMMIT;

