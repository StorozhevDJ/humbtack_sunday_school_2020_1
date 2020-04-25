-- -------------------------------------------------------------------
-- Demo date

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