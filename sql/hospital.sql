DROP DATABASE IF EXISTS `hospital`;
CREATE DATABASE `hospital`;
USE `hospital`;

-- -----------------------------------------------------
-- Table doctor `speciality` list
-- -----------------------------------------------------
CREATE TABLE speciality (`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `speciality` VARCHAR(150) NOT NULL,
    UNIQUE KEY (`speciality`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

-- -----------------------------------------------------
-- Table `room` for reception
-- -----------------------------------------------------
CREATE TABLE room (`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `room` VARCHAR(50) NOT NULL,
    UNIQUE KEY (`room`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

-- -----------------------------------------------------
-- Table `admin` account
-- -----------------------------------------------------
CREATE TABLE admin (`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `firstName` VARCHAR(50) NOT NULL,
    `lastName` VARCHAR(50) NOT NULL,
    `patronymic` VARCHAR(50) NULL,
    `position` VARCHAR(50) NOT NULL,
    `login` VARCHAR(50) NOT NULL,
    `password` CHAR(32) NOT NULL,
    `tocken` VARCHAR(50) NULL,
    UNIQUE KEY (`login`),
    UNIQUE KEY (`tocken`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

-- -----------------------------------------------------
-- Table `patient` account
-- -----------------------------------------------------
CREATE TABLE patient (`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `firstName` VARCHAR(50) NOT NULL,
    `lastName` VARCHAR(50) NOT NULL,
    `patronymic` VARCHAR(50) NULL,
    `email` VARCHAR(50) NOT NULL,
    `address` VARCHAR(250) NOT NULL,
    `phone` CHAR(12) NOT NULL,
    `login` VARCHAR(50) NOT NULL,
    `password` CHAR(32) NOT NULL,
    `tocken` VARCHAR(50) NULL,
    UNIQUE KEY (`login`),
    UNIQUE (`email`),
    UNIQUE (`phone`),
    UNIQUE KEY (`tocken`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

-- -----------------------------------------------------
-- Table `doctor` account
-- -----------------------------------------------------
CREATE TABLE doctor (`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `firstName` VARCHAR(50) NOT NULL,
    `lastName` VARCHAR(50) NOT NULL,
    `patronymic` VARCHAR(50) NULL,
    `speciality` INT NOT NULL,
    `login` VARCHAR(50) NOT NULL,
    `password` CHAR(32) NOT NULL,
    `tocken` VARCHAR(50) NULL,
    UNIQUE KEY (`login`),
    UNIQUE KEY (`tocken`),
    FOREIGN KEY (`speciality`) REFERENCES `speciality`(id)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

-- -----------------------------------------------------
-- Table `schedule` reception
-- -----------------------------------------------------
CREATE TABLE schedule (`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	`doctorId` INT NOT NULL,
    `roomId` INT NOT NULL,
    `date` DATE NOT NULL,
    `time` TIME NOT NULL,
    `patientId` INT NULL,
    FOREIGN KEY (`doctorId`) REFERENCES `doctor`(id) ON DELETE CASCADE,
    FOREIGN KEY (`roomId`) REFERENCES `room`(id),
    FOREIGN KEY (`patientId`) REFERENCES `patient`(id) ON DELETE SET NULL
) ENGINE=INNODB DEFAULT CHARSET=utf8;



-- -----------------------------------------------------
-- Insert default admin account
-- -----------------------------------------------------
INSERT INTO `admin` (`firstName`, `lastName`, `position`, `login`, `password`)
	VALUES ('firstNameAdmin', 'lastNameAdmin', 'Superadmin', 'admin', MD5('admin'))
