package net.thumbtack.school.hospital.database;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import net.thumbtack.school.hospital.database.model.Session;
import net.thumbtack.school.hospital.database.model.UserType;
import org.junit.jupiter.api.Test;

import net.thumbtack.school.hospital.database.model.Patient;
import net.thumbtack.school.hospital.database.model.User;

public class PatientDaoTest extends DatabaseTest {

    @Test
    public void testGetCount() {
        assertEquals(1, patientDao.getCount());
    }

    @Test
    public void testAddPatient() {
        User user = new User("Вася", "Петечкин", "Васильевич", "testPatient", "password", null);
        Patient patient = new Patient(user, "e@mail", "Addr", "79012345678");
        patientDao.insert(patient);
        assertThrows(RuntimeException.class, () -> patientDao.insert(patient));// Check double add
        assertThrows(RuntimeException.class, () -> patientDao.insert(null));
        assertAll(
                () -> assertNotEquals(0, patient.getId()),
                () -> assertEquals(patient.getUser().getId(), user.getId()),
                () -> assertEquals(UserType.PATIENT, patient.getUser().getType()),
                () -> assertEquals(2, patientDao.getCount())
        );
    }

    @Test
    public void testGetByToken() {
        assertTrue(userDao.logIn(new User("patientLogin", "passwordPatient", new Session("token"))));

        Patient patient = patientDao.getByToken(new Session("token"));
        assertAll(
                () -> assertEquals("patient@mail", patient.getEmail()),
                () -> assertEquals("addrPatient", patient.getAddress()),
                () -> assertEquals("+79001112233", patient.getPhone()),
                () -> assertEquals(UserType.PATIENT, patient.getUser().getType(), "User type is not patient"),
                () -> assertEquals("FirstNamePatient", patient.getUser().getFirstName(), "User firstName is not FirstNamePatient"),
                () -> assertEquals("lastNamePatient", patient.getUser().getLastName(), "User lastname is not lastNamePatient"),
                () -> assertEquals("partronymicPatient", patient.getUser().getPatronymic(), "User patronymic is not partronymicPatient"),
                () -> assertEquals("patientLogin", patient.getUser().getLogin(), "User login is not patientLogin"),
                () -> assertEquals("token", patient.getUser().getSession().getToken(), "User token is not token"),
                () -> assertNull(patient.getUser().getPassword(), "User password is not null"),
                () -> assertNull(patientDao.getByToken(new Session("errorToken"))),
                () -> assertNull(patientDao.getByToken(new Session(""))),
                () -> assertNull(patientDao.getByToken(new Session("   "))),
                () -> assertNull(patientDao.getByToken(new Session(" token "))),
                () -> assertNull(patientDao.getByToken(null))
        );
    }

    @Test
    public void testGetByUserId() {
        Patient patient = patientDao.getByUserId(userDao.getByLogin("patientLogin", "passwordPatient").getId());

        assertAll(
                () -> assertEquals("patient@mail", patient.getEmail()),
                () -> assertEquals("addrPatient", patient.getAddress()),
                () -> assertEquals("+79001112233", patient.getPhone()),
                () -> assertEquals(UserType.PATIENT, patient.getUser().getType(), "User type is not patient"),
                () -> assertEquals("FirstNamePatient", patient.getUser().getFirstName(), "User firstName is not FirstNamePatient"),
                () -> assertEquals("lastNamePatient", patient.getUser().getLastName(), "User lastname is not lastNamePatient"),
                () -> assertEquals("partronymicPatient", patient.getUser().getPatronymic(), "User patronymic is not partronymicPatient"),
                () -> assertEquals("patientLogin", patient.getUser().getLogin(), "User login is not patientLogin"),
                () -> assertNull(patient.getUser().getSession().getToken(), "User token is not null"),
                () -> assertNull(patient.getUser().getPassword(), "User password is not null"),
                () -> assertNull(patientDao.getByPatientId(0)),
                () -> assertNull(patientDao.getByPatientId(123456))
        );
    }

    @Test
    public void testGetByPatientId() {
        int id = patientDao.getByUserId(userDao.getByLogin("patientLogin", "passwordPatient").getId()).getId();

        Patient patient = patientDao.getByPatientId(id);

        assertAll(
                () -> assertEquals("patient@mail", patient.getEmail()),
                () -> assertEquals("addrPatient", patient.getAddress()),
                () -> assertEquals("+79001112233", patient.getPhone()),
                () -> assertEquals(UserType.PATIENT, patient.getUser().getType(), "User type is not patient"),
                () -> assertEquals("FirstNamePatient", patient.getUser().getFirstName(), "User firstName is not FirstNamePatient"),
                () -> assertEquals("lastNamePatient", patient.getUser().getLastName(), "User lastname is not lastNamePatient"),
                () -> assertEquals("partronymicPatient", patient.getUser().getPatronymic(), "User patronymic is not partronymicPatient"),
                () -> assertEquals("patientLogin", patient.getUser().getLogin(), "User login is not patientLogin"),
                () -> assertNull(patient.getUser().getSession().getToken(), "User token is not null"),
                () -> assertNull(patient.getUser().getPassword(), "User password is not null"),
                () -> assertNull(patientDao.getByPatientId(0)),
                () -> assertNull(patientDao.getByPatientId(123456))
        );
    }

}
