DROP DATABASE IF EXISTS `hospital`;
CREATE DATABASE `hospital`;
USE `hospital`;


-- -----------------------------------------------------
-- Table doctor `speciality` list
-- -----------------------------------------------------
CREATE TABLE `speciality` (`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `speciality` VARCHAR(150) NOT NULL,
    UNIQUE KEY (`speciality`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

-- -----------------------------------------------------
-- Table `room` list for reception
-- -----------------------------------------------------
CREATE TABLE `room` (`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `room` VARCHAR(50) NOT NULL,
    UNIQUE KEY (`room`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;


-- -----------------------------------------------------
-- Table `user`
-- -----------------------------------------------------
CREATE TABLE `user` (`id` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `firstName` VARCHAR(50) NOT NULL,
    `lastName` VARCHAR(50) NOT NULL,
    `patronymic` VARCHAR(50) NULL,
    `type` ENUM('admin', 'doctor', 'patient') NOT NULL,
    `login` VARCHAR(50) NOT NULL,
    `password` CHAR(32) NOT NULL,
    `token` VARCHAR(50) NULL,
    UNIQUE KEY (`login`),
    UNIQUE KEY (`token`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

-- -----------------------------------------------------
-- Table `admin` account
-- -----------------------------------------------------
CREATE TABLE `admin` (`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `userId` INT UNSIGNED NOT NULL,
    `position` VARCHAR(50) NOT NULL,
    UNIQUE (`userId`),
    FOREIGN KEY (`userId`) REFERENCES `user`(id)  ON DELETE CASCADE
) ENGINE=INNODB DEFAULT CHARSET=utf8;

-- -----------------------------------------------------
-- Table `patient` account
-- -----------------------------------------------------
CREATE TABLE `patient` (`id` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
	`userId` INT UNSIGNED NOT NULL,
    `email` VARCHAR(50) NOT NULL,
    `address` VARCHAR(250) NOT NULL,
    `phone` CHAR(12) NOT NULL,
    FOREIGN KEY (`userId`) REFERENCES `user`(id)  ON DELETE CASCADE,
    UNIQUE (`email`),
    UNIQUE (`phone`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

-- -----------------------------------------------------
-- Table `doctor` account
-- -----------------------------------------------------
CREATE TABLE doctor (`id` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `userId` INT UNSIGNED NOT NULL,
    `speciality` INT NOT NULL,
    FOREIGN KEY (`userId`) REFERENCES `user`(id)  ON DELETE CASCADE,
    FOREIGN KEY (`speciality`) REFERENCES `speciality`(id)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

-- -----------------------------------------------------
-- Table `schedule` reception
-- -----------------------------------------------------
CREATE TABLE `schedule` (`id` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
	`doctorId` INT UNSIGNED NOT NULL,
    `patientId` INT UNSIGNED NULL,
    `date` DATE NOT NULL,
    `time` TIME NOT NULL,
    `roomId` INT NOT NULL,
    FOREIGN KEY (`doctorId`) REFERENCES `doctor`(id) ON DELETE CASCADE,
    FOREIGN KEY (`roomId`) REFERENCES `room`(id),
    FOREIGN KEY (`patientId`) REFERENCES `patient`(id) ON DELETE SET NULL,
    UNIQUE (`doctorId`, `date`, `time`),
    UNIQUE (`patientId`, `date`, `doctorId`), -- Пациент может записаться к нескольким врачам, но не может записаться более одного раза к одному и тому же врачу на один и тот же день
    UNIQUE (`roomId`, `date`, `time`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;



-- -----------------------------------------------------
-- Insert default admin account
-- -----------------------------------------------------
INSERT INTO `user` (`firstName`, `lastName`, `type`, `login`, `password`)
	VALUES ('firstNameAdmin', 'lastNameAdmin', 'admin', 'admin', MD5('admin'));
    
INSERT INTO `admin` (`userId`, `position`)
	VALUES (LAST_INSERT_ID(), 'Superadmin');
