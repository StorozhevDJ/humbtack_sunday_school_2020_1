DROP DATABASE IF EXISTS `hospital`;
create DATABASE `hospital`;
USE `hospital`;


-- -----------------------------------------------------
-- Table doctor `speciality` list
-- -----------------------------------------------------
create TABLE `speciality` (
    `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(150) NOT NULL,
UNIQUE KEY(`name`)
)ENGINE = INNODB DEFAULT CHARSET = utf8;


-- -----------------------------------------------------
-- Table `room` list for reception
-- -----------------------------------------------------
create TABLE `room` (
    `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `room` VARCHAR(50) NOT NULL,
    UNIQUE KEY (`room`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;


-- -----------------------------------------------------
-- Table `user`
-- -----------------------------------------------------
create TABLE `user` (
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `firstName` VARCHAR(50) NOT NULL,
    `lastName` VARCHAR(50) NOT NULL,
    `patronymic` VARCHAR(50) NULL,
`type` ENUM('ADMINISTRATOR', 'DOCTOR', 'PATIENT')NOT NULL,
`login` VARCHAR(50)NOT NULL,
`password` CHAR(32)NOT NULL,
UNIQUE KEY(`login`)
)ENGINE = INNODB DEFAULT CHARSET = utf8 COLLATE utf8_general_ci;


-- -----------------------------------------------------
-- Table session
-- -----------------------------------------------------
create TABLE `session` (
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `userId` INT UNSIGNED NOT NULL,
    `token` VARCHAR(32) NOT NULL,
FOREIGN KEY(`userId`)REFERENCES `user`(id)ON
delete CASCADE,
UNIQUE KEY(`userId`),
KEY(`token`)
)ENGINE = INNODB DEFAULT CHARSET = utf8 COLLATE utf8_general_ci
;


-- -----------------------------------------------------
-- Table `admin` account
-- -----------------------------------------------------
create TABLE `admin` (
    `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `userId` INT UNSIGNED NOT NULL,
    `position` VARCHAR(50) NOT NULL,
    UNIQUE KEY (`userId`),
    FOREIGN KEY (`userId`) REFERENCES `user`(id)  ON delete CASCADE
) ENGINE=INNODB DEFAULT CHARSET=utf8;


-- -----------------------------------------------------
-- Table `patient` account
-- -----------------------------------------------------
create TABLE `patient` (
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `userId` INT UNSIGNED NOT NULL,
    `email` VARCHAR(50) NOT NULL,
    `address` VARCHAR(250) NOT NULL,
    `phone` CHAR(12) NOT NULL,
    FOREIGN KEY (`userId`) REFERENCES `user`(id)  ON delete CASCADE,
    UNIQUE (`email`),
    UNIQUE (`phone`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;


-- -----------------------------------------------------
-- Table `doctor` account
-- -----------------------------------------------------
create TABLE `doctor` (
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `userId` INT UNSIGNED NOT NULL,
    `specialityId` INT NOT NULL,
    `roomId` INT NOT NULL,
UNIQUE KEY(`userId`),
FOREIGN KEY(`userId`)REFERENCES `user`(id)ON
delete CASCADE,
FOREIGN KEY(`roomId`)REFERENCES `room`(id),
FOREIGN KEY(`specialityId`)REFERENCES `speciality`(id)
)ENGINE = INNODB
;


-- -----------------------------------------------------
-- Table Doctor `schedule`
-- -----------------------------------------------------
create TABLE `schedule` (
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `doctorId` INT UNSIGNED NOT NULL,
    `date` DATE NOT NULL,    -- Date of schedule
FOREIGN KEY(`doctorId`)REFERENCES `doctor`(id)ON
delete CASCADE,
UNIQUE(`doctorId`, `date`)
)ENGINE = INNODB DEFAULT CHARSET = utf8
;


-- -----------------------------------------------------
-- Table `day_schedule` reception
-- -----------------------------------------------------
create TABLE `day_schedule` (
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `scheduleId` INT UNSIGNED NULL,
    `ticket` VARCHAR(50) NULL,	-- NULL if schedule free
    `patientId` INT UNSIGNED NULL,
`timeStart` TIME NOT NULL,  -- Start reception time
`timeEnd` TIME NOT NULL,  -- End reception time
`type` ENUM('FREE', 'RECEPTION', 'COMMISSION', 'OTHER')NOT NULL DEFAULT 'FREE',
FOREIGN KEY(`scheduleId`)REFERENCES `schedule`(id)ON
delete CASCADE,
FOREIGN KEY(`patientId`)REFERENCES `patient`(id)ON
delete
SET NULL,
UNIQUE(`ticket`),
UNIQUE(`scheduleId`, `timeStart`),
UNIQUE(`scheduleId`, `patientId`)
-- Пациент может записаться к нескольким врачам, но не может записаться более одного раза к одному и тому же врачу на один и тот же день
)ENGINE= INNODB DEFAULT CHARSET=utf8;


-- -----------------------------------------------------
-- Table `commission`
-- -----------------------------------------------------
create TABLE `commission` (
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `ticket` VARCHAR(50) NULL,	-- NULL if schedule free
    `patientId` INT UNSIGNED NULL,
    `room` INT NULL,
    `date` DATE NOT NULL,    -- Date of schedule
`timeStart` TIME NOT NULL,  -- Start reception time
`timeEnd` TIME NOT NULL,  -- End reception time
FOREIGN KEY(`patientId`)REFERENCES `patient`(id)ON
delete CASCADE,
UNIQUE(`ticket`)
)ENGINE = INNODB DEFAULT CHARSET = utf8
;


-- -----------------------------------------------------
-- Doctor list for commission
-- -----------------------------------------------------
create TABLE `commission_doctor` (
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `commissionId` INT UNSIGNED NOT NULL,
    `doctorId` INT UNSIGNED NOT NULL,
FOREIGN KEY(`commissionId`)REFERENCES `commission`(id)ON
delete CASCADE,
FOREIGN KEY(`doctorId`)REFERENCES `doctor`(id)ON
delete CASCADE,
UNIQUE(`doctorId`, `commissionId`)
)ENGINE = INNODB DEFAULT CHARSET = utf8
;



-- -----------------------------------------------------
-- Insert default data
-- -----------------------------------------------------

-- Admin account
insert into `user`(`firstName`, `lastName`, `type`, `login`, `password`)
values ('firstNameAdmin', 'lastNameAdmin', 'Administrator', 'admin', md5('admin'));

insert into `admin`(`userId`, `position`)
values (last_insert_id(), 'Superadmin');


-- Speciality
insert into `speciality`(`id`, `name`)
	values (1, 'spec'), (2, 'Айболит'), (3, 'Оториноларинголог'), (4, 'Вирусолог'), (5, 'Травматолог');

-- Rooms
insert into `room` (`id`, `room`)
	values (1, '1'), (2, '6'), (3, '13'), (4, '13a'), (5, '777');


-- -------------------------------------------------------------------
-- Временные insert для проверки на этапе проектирования структуры БД.
-- После окончания работы над структурой БД будут удалены.

-- Admins
insert into `user` (`firstName`, `lastName`, `patronymic`, `type`, `login`, `password`)
	values ('firstNameAdmin2', 'lastNameAdmin2', `patronymic`, 'Administrator', 'admin2', md5('admin'));
insert into `admin` (`userId`, `position`)
	values (last_insert_id(), 'SecondAdmin');
    
 -- Patients
insert into `user` (`firstName`, `lastName`, `patronymic`, `type`, `login`, `password`)
	values ('firstNamePatient', 'lastNamePatient', 'patronymic', 'Patient', 'patient1', md5('patient'));
insert into `patient` (`userId`, `email`, `address`, `phone`)
	values (last_insert_id(), 'm@il', 'addr', '79012345678');

insert into `user` (`firstName`, `lastName`, `patronymic`, `type`, `login`, `password`)
	values ('firstNamePatient2', 'lastNamePatient2', 'patronymic2', 'Patient', 'patient2', md5('patient'));
insert into `patient` (`userId`, `email`, `address`, `phone`)
	values (last_insert_id(), 'm@il2', 'addr2', '79012345679');

-- Doctors
insert into `user` (`firstName`, `lastName`, `patronymic`, `type`, `login`, `password`)
	values ('firstNameDoc', 'lastNameDco', 'patronymicDco', 'Doctor', 'doctor', md5('doctor'));
insert into `doctor` (`userId`, `specialityId`, `roomId`) values (last_insert_id(), 1, 1);

insert into `user` (`firstName`, `lastName`, `patronymic`, `type`, `login`, `password`)
	values ('firstNameDoc2', 'lastNameDoc2', 'patronymicDoc2', 'Doctor', 'doctor2', md5('doctor'));
insert into `doctor` (`userId`, `specialityId`, `roomId`) values (last_insert_id(), 2, 2);
