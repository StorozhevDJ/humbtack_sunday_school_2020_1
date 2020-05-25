package net.thumbtack.school.hospital.integration;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.thumbtack.school.hospital.ApplicationProperties;
import net.thumbtack.school.hospital.database.dao.AdminDao;
import net.thumbtack.school.hospital.database.dao.CommonDao;
import net.thumbtack.school.hospital.database.dao.DoctorDao;
import net.thumbtack.school.hospital.database.dao.PatientDao;
import net.thumbtack.school.hospital.database.model.Admin;
import net.thumbtack.school.hospital.database.model.Doctor;
import net.thumbtack.school.hospital.database.model.Session;
import net.thumbtack.school.hospital.database.model.User;
import net.thumbtack.school.hospital.dto.request.*;
import net.thumbtack.school.hospital.dto.response.*;
import net.thumbtack.school.hospital.dto.response.getticket.GetTicketDto;
import net.thumbtack.school.hospital.serverexception.ServerException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class IntegrationTest {

    private final CommonDao commonDao;
    private final AdminDao adminDao;
    private final DoctorDao doctorDao;
    private final PatientDao patientDao;

    @Autowired
    private ApplicationProperties applicationProperties;

    @Autowired
    private TestRestTemplate restTemplate;
    private final String restUrl = "http://localhost:8888/api/";

    @Autowired
    public IntegrationTest(CommonDao commonDao, AdminDao adminDao, DoctorDao doctorDao, PatientDao patientDao) {
        this.commonDao = commonDao;
        this.adminDao = adminDao;
        this.doctorDao = doctorDao;
        this.patientDao = patientDao;
    }

    @BeforeEach
    public void clearDB() throws ServerException {
        commonDao.clear();
        adminDao.insert(new Admin(new User("FirstNameAdmin", "lastNameAdmin", null, "admin", "admin", new Session()), "Superadmin"));
    }


    //3.2
    @Test
    public void testAdminRegister() {
        HttpHeaders headers = loginSuperadmin();
        // Good register
        HttpEntity<String> entity = new HttpEntity<>(new Gson().toJson(new RegisterAdminDtoRequest("Имя", "Фамилия", "Отчество", "Должность", "admLogin", "admPwd")), headers);
        LoginDtoResponse responseOk = restTemplate.postForObject(restUrl + "admins", entity, LoginDtoResponse.class);
        assertAll(
                () -> assertNotEquals(responseOk.getId(), 0),
                () -> assertEquals(responseOk.getFirstName(), "Имя"),
                () -> assertEquals(responseOk.getLastName(), "Фамилия"),
                () -> assertEquals(responseOk.getPatronymic(), "Отчество"),
                () -> assertEquals(responseOk.getPosition(), "Должность")
        );
        // Double register
        entity = new HttpEntity<>(new Gson().toJson(new RegisterAdminDtoRequest("Имя", "Фамилия", "Отчество", "Должность", "admLogin", "admPwd")), headers);
        try {
            restTemplate.postForEntity(restUrl + "admins", entity, ExceptionDtoResponse.class);
            fail();
        } catch (Exception ex) {
        }
        // Bad register
        entity = new HttpEntity<>(new Gson().toJson(new RegisterAdminDtoRequest("fName", "lName", "patronimic", "Position232", "lo", "pwd")), headers);
        try {
            restTemplate.postForEntity(restUrl + "admins", entity, ExceptionDtoResponse.class);
            fail();
        } catch (Exception ex) {
        }
    }

    //3.3
    @Test
    public void testDoctorRegister() {
        HttpHeaders headers = loginSuperadmin();
        RegisterDoctorDtoRequest dtoRequest = new RegisterDoctorDtoRequest(
                "Имя",
                "Фамилия",
                "Отчество",
                "spec",
                "1",
                "admLogin",
                "admPwd",
                "01-05-2020",
                "10-05-2020",
                new WeekScheduleDtoRequest("08:00", "14:00", new String[]{"Mon", "Tue"}),
                15
        );
        HttpEntity<String> entity = new HttpEntity<>(new Gson().toJson(dtoRequest), headers);
        String responseOk = restTemplate.postForObject(restUrl + "doctors", entity, String.class);
        EditScheduleDtoResponse dtoResponse = new Gson().fromJson(responseOk, EditScheduleDtoResponse.class);
        assertAll(
                () -> assertNotEquals(dtoResponse.getId(), 0),
                () -> assertEquals(dtoResponse.getFirstName(), "Имя"),
                () -> assertEquals(dtoResponse.getLastName(), "Фамилия"),
                () -> assertEquals(dtoResponse.getPatronymic(), "Отчество"),
                () -> assertEquals(dtoResponse.getSpeciality(), "spec"),
                () -> assertEquals(dtoResponse.getRoom(), "1"),
                () -> assertEquals(dtoResponse.getSchedule().get(0).getDate(), "04-05-2020"),
                () -> assertEquals(dtoResponse.getSchedule().get(1).getDate(), "05-05-2020"),
                () -> assertEquals(dtoResponse.getSchedule().size(), 2),
                () -> assertEquals(dtoResponse.getSchedule().get(0).getTicketScheduleDto().get(0).getTime(), "08:00"),
                () -> assertEquals(dtoResponse.getSchedule().get(0).getTicketScheduleDto().get(1).getTime(), "08:15")
        );
    }

    //3.4
    @Test
    public void testPatientRegister() {
        // Good register
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(new Gson().toJson(new RegisterPatientDtoRequest("Имя", "Фамилия", "Отчество", "e@mail.net", "Адрес", "89012345678", "Login", "password")), headers);
        LoginDtoResponse responseOk = restTemplate.postForObject(restUrl + "patients", entity, LoginDtoResponse.class);
        assertAll(
                () -> assertNotEquals(responseOk.getId(), 0),
                () -> assertEquals(responseOk.getFirstName(), "Имя"),
                () -> assertEquals(responseOk.getLastName(), "Фамилия"),
                () -> assertEquals(responseOk.getPatronymic(), "Отчество"),
                () -> assertEquals(responseOk.getEmail(), "e@mail.net"),
                () -> assertEquals(responseOk.getAddress(), "Адрес"),
                () -> assertEquals(responseOk.getPhone(), "89012345678")
        );
        // Double register
        entity = new HttpEntity<>(new Gson().toJson(new RegisterPatientDtoRequest("Имя", "Фамилия", "Отчество", "e@mail.net", "Адрес", "89012345678", "Login", "password")), headers);
        try {
            restTemplate.postForObject(restUrl + "patients", entity, ExceptionDtoResponse.class);
            fail();
        } catch (Exception ex) {
        }
        // Bad register
        entity = new HttpEntity<>(new Gson().toJson(new RegisterPatientDtoRequest("fName", "lname", null, "e@mail", "Адрес", "S89012345A678", "Login", "pwd")), headers);
        try {
            restTemplate.postForObject(restUrl + "patients", entity, ExceptionDtoResponse.class);
            fail();
        } catch (Exception ex) {
        }
    }

    //3.5
    @Test
    public void testLoginUser() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(new Gson().toJson(new LoginDtoRequest("admin", "admin")), headers);
        LoginDtoResponse response = restTemplate.postForObject(restUrl + "sessions", entity, LoginDtoResponse.class);
        assertAll(
                () -> assertNotEquals(response.getId(), 0),
                () -> assertNotNull(response.getFirstName())
        );
    }

    //3.6
    @Test
    public void testLogOutUser() throws ServerException {
        // Login
        HttpHeaders headers = loginSuperadmin();
        // LogOut
        HttpEntity<String> entity = new HttpEntity<>(new Gson().toJson(new LoginDtoRequest("admin", "admin")), headers);
        ResponseEntity<String> response = restTemplate.exchange(restUrl + "sessions", HttpMethod.DELETE, entity, String.class);
        // Check
        Admin admin = adminDao.getByToken(headers.get("Cookie").get(0).substring(14));
        assertNull(admin);
    }

    //3.7
    @Test
    public void testInfoUser() {
        HttpHeaders headers = loginSuperadmin();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<LoginDtoResponse> response = restTemplate.exchange(restUrl + "account", HttpMethod.GET, entity, LoginDtoResponse.class);
        assertAll(
                () -> assertNotEquals(response.getBody().getId(), 0),
                () -> assertEquals(response.getBody().getFirstName(), "FirstNameAdmin"),
                () -> assertEquals(response.getBody().getLastName(), "lastNameAdmin"),
                () -> assertNull(response.getBody().getPatronymic()),
                () -> assertEquals(response.getBody().getPosition(), "Superadmin")
        );
    }

    //3.8
    @Test
    public void testInfoDoctor() throws ServerException {
        addDoctors();
        Doctor doctor = doctorDao.getBySpeciality("spec").get(0);
        HttpHeaders headers = loginSuperadmin();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(restUrl + "doctors/" + doctor.getId() + "?schedule=yes&startDate=01-05-2020&endDate=30-05-2020", HttpMethod.GET, entity, String.class);
        EditScheduleDtoResponse dtoResponse = new Gson().fromJson(response.getBody(), EditScheduleDtoResponse.class);
        assertAll(
                () -> assertEquals(doctor.getId(), dtoResponse.getId()),
                () -> assertEquals(doctor.getUser().getFirstName(), dtoResponse.getFirstName()),
                () -> assertEquals(doctor.getUser().getLastName(), dtoResponse.getLastName()),
                () -> assertEquals(doctor.getUser().getPatronymic(), dtoResponse.getPatronymic()),
                () -> assertEquals(doctor.getSpeciality().getName(), dtoResponse.getSpeciality()),
                () -> assertEquals(doctor.getRoom().getNumber(), dtoResponse.getRoom()),
                () -> assertNotNull(dtoResponse.getSchedule()),
                () -> assertEquals(2, dtoResponse.getSchedule().size()),
                () -> assertEquals("05-05-2020", dtoResponse.getSchedule().get(1).getDate()),
                () -> assertEquals(24, dtoResponse.getSchedule().get(1).getTicketScheduleDto().size()),
                () -> assertEquals("08:00", dtoResponse.getSchedule().get(1).getTicketScheduleDto().get(0).getTime())
        );

        response = restTemplate.exchange(restUrl + "doctors/" + doctor.getId() + "?schedule=no", HttpMethod.GET, entity, String.class);
        EditScheduleDtoResponse dtoResponse2 = new Gson().fromJson(response.getBody(), EditScheduleDtoResponse.class);
        assertNull(dtoResponse2.getSchedule());
    }

    //3.9
    @Test
    public void testInfoDoctors() {
        addDoctors();
        HttpHeaders headers = loginSuperadmin();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(restUrl + "doctors?speciality=Айболит&schedule=yes&startDate=01-05-2020&endDate=30-05-2020", HttpMethod.GET, entity, String.class);
        List<EditScheduleDtoResponse> dtoResponse = new Gson().fromJson(response.getBody(), List.class);
        assertEquals(3, dtoResponse.size());
        response = restTemplate.exchange(restUrl + "doctors?schedule=yes&startDate=01-05-2020&endDate=30-05-2020", HttpMethod.GET, entity, String.class);
        dtoResponse = new Gson().fromJson(response.getBody(), List.class);
        assertEquals(5, dtoResponse.size());
        response = restTemplate.exchange(restUrl + "doctors?schedule=yes&startDate=01-05-2020&endDate=02-05-2020", HttpMethod.GET, entity, String.class);
        Type listType = new TypeToken<ArrayList<LoginDtoResponse>>() {
        }.getType();
        List<LoginDtoResponse> responseInfo = new Gson().fromJson(response.getBody(), listType);
        assertAll(
                () -> assertEquals(1, responseInfo.size()),
                () -> assertEquals("Имя", responseInfo.get(0).getFirstName()),
                () -> assertEquals("Фамилияа", responseInfo.get(0).getLastName()),
                () -> assertEquals("Отчество", responseInfo.get(0).getPatronymic()),
                () -> assertEquals("13", responseInfo.get(0).getRoom()),
                () -> assertEquals("Айболит", responseInfo.get(0).getSpeciality())
        );
    }

    //3.10 GET /api/patients/идентификатор_пациента
    @Test
    public void testInfoPatients() {
        // Add new patient
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entityPatient = new HttpEntity<>(new Gson().toJson(new RegisterPatientDtoRequest("Имя", "Фамилия", "Отчество", "e@mail.net", "Адрес", "89012345678", "Login", "password")), headers);
        LoginDtoResponse patient = restTemplate.postForObject(restUrl + "patients", entityPatient, LoginDtoResponse.class);
        //Login Admin
        headers = loginSuperadmin();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(restUrl + "patients/" + patient.getId(), HttpMethod.GET, entity, String.class);
        LoginDtoResponse dtoResponse = new Gson().fromJson(response.getBody(), LoginDtoResponse.class);
        assertAll(
                () -> assertEquals(patient.getId(), dtoResponse.getId()),
                () -> assertEquals(patient.getFirstName(), dtoResponse.getFirstName()),
                () -> assertEquals(patient.getLastName(), dtoResponse.getLastName()),
                () -> assertEquals(patient.getPatronymic(), dtoResponse.getPatronymic()),
                () -> assertEquals(patient.getPhone(), dtoResponse.getPhone()),
                () -> assertEquals(patient.getEmail(), dtoResponse.getEmail()),
                () -> assertEquals(patient.getAddress(), dtoResponse.getAddress())
        );
    }

    //3.11 PUT /api/admins
    @Test
    public void testEditAdmin() {
        HttpHeaders headers = loginSuperadmin();
        HttpEntity<String> entity = new HttpEntity<>(new Gson().toJson(new EditAdminDtoRequest("Имя", "Фамилия", "Отчество", "Должность", "admin", "admPwd")), headers);
        LoginDtoResponse dtoResponse = restTemplate.exchange(restUrl + "admins", HttpMethod.PUT, entity, LoginDtoResponse.class).getBody();
        assertAll(
                () -> assertNotEquals(dtoResponse.getId(), 0),
                () -> assertEquals(dtoResponse.getFirstName(), "Имя"),
                () -> assertEquals(dtoResponse.getLastName(), "Фамилия"),
                () -> assertEquals(dtoResponse.getPatronymic(), "Отчество"),
                () -> assertEquals(dtoResponse.getPosition(), "Должность")
        );
    }

    //3.12 PUT /api/doctors/идентификатор_врача
    @Test
    public void testEditSchedule() throws ServerException {
        addDoctors();
        Doctor doctor = doctorDao.getBySpeciality("spec").get(0);
        HttpHeaders headers = loginSuperadmin();
        EditScheduleDtoRequest newSchedule = new EditScheduleDtoRequest(
                "01-05-2020",
                "30-05-2020",
                new WeekScheduleDtoRequest(
                        "08:00",
                        "15:00",
                        new String[]{"Mon", "Tue", "Fri"}
                ),
                15
        );
        HttpEntity<String> entity = new HttpEntity<>(new Gson().toJson(newSchedule), headers);
        ResponseEntity<String> response = restTemplate.exchange(restUrl + "doctors/" + doctor.getId(), HttpMethod.PUT, entity, String.class);
        EditScheduleDtoResponse dtoResponse = new Gson().fromJson(response.getBody(), EditScheduleDtoResponse.class);
        assertAll(
                () -> assertNotEquals(dtoResponse.getId(), 0),
                () -> assertEquals(dtoResponse.getFirstName(), "Имя"),
                () -> assertEquals(dtoResponse.getLastName(), "Фамилия"),
                () -> assertEquals(dtoResponse.getPatronymic(), "Отчество"),
                () -> assertEquals(dtoResponse.getSpeciality(), "spec"),
                () -> assertEquals(dtoResponse.getRoom(), "1"),
                () -> assertEquals(dtoResponse.getSchedule().size(), 13),
                () -> assertEquals(dtoResponse.getSchedule().get(0).getDate(), "01-05-2020"),
                () -> assertNotEquals(dtoResponse.getSchedule().get(1).getDate(), "02-05-2020"),
                () -> assertEquals(dtoResponse.getSchedule().get(1).getDate(), "04-05-2020"),
                () -> assertEquals(dtoResponse.getSchedule().get(0).getTicketScheduleDto().get(0).getTime(), "08:00"),
                () -> assertEquals(dtoResponse.getSchedule().get(0).getTicketScheduleDto().get(1).getTime(), "08:15"),
                () -> assertNull(dtoResponse.getSchedule().get(0).getTicketScheduleDto().get(0).getPatient())
        );
    }

    //3.13 PUT /api/patients
    @Test
    public void testEditPatient() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(new Gson().toJson(new RegisterPatientDtoRequest("Имя", "Фамилия", "Отчество", "e@mail.net", "Адрес", "89012345678", "Login", "password")), headers);
        ResponseEntity<LoginDtoResponse> responseRegister = restTemplate.postForEntity(restUrl + "patients", entity, LoginDtoResponse.class);
        headers.add("Cookie", responseRegister.getHeaders().get("Set-Cookie").get(0));
        entity = new HttpEntity<>(new Gson().toJson(new EditPatientDtoRequest("Новоеимя", "Новаяфамилия", "Новоеотчество", "newe@mail.net", "Адрес2", "+79012345678", "password", "newPassword")), headers);
        ResponseEntity<String> response = restTemplate.exchange(restUrl + "patients", HttpMethod.PUT, entity, String.class);
        LoginDtoResponse dtoResponse = new Gson().fromJson(response.getBody(), LoginDtoResponse.class);
        assertAll(
                () -> assertNotEquals(dtoResponse.getId(), 0),
                () -> assertEquals(dtoResponse.getFirstName(), "Новоеимя"),
                () -> assertEquals(dtoResponse.getLastName(), "Новаяфамилия"),
                () -> assertEquals(dtoResponse.getPatronymic(), "Новоеотчество"),
                () -> assertEquals(dtoResponse.getEmail(), "newe@mail.net"),
                () -> assertEquals(dtoResponse.getAddress(), "Адрес2"),
                () -> assertEquals(dtoResponse.getPhone(), "+79012345678")
        );
    }

    //3.14 DELETE /api/doctors/идентификатор_врача
    @Test
    public void testDeleteDoctor() throws ServerException {
        addDoctors();
        Doctor doctor = doctorDao.getBySpeciality("spec").get(0);
        // Register patients
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(new Gson().toJson(new RegisterPatientDtoRequest("Имя", "Фамилия", "Отчество", "e@mail.net", "Адрес", "89012345678", "Login", "password")), headers);
        ResponseEntity<LoginDtoResponse> response = restTemplate.postForEntity(restUrl + "patients", entity, LoginDtoResponse.class);
        headers.add("Cookie", response.getHeaders().get("Set-Cookie").get(0));
        // Add ticket
        entity = new HttpEntity<>(new Gson().toJson(new AddTicketDtoRequest(doctor.getId() + "", doctor.getSpeciality().getName(), "05-05-2020", "08:00")), headers);
        String responseAddTicket = restTemplate.postForObject(restUrl + "tickets", entity, String.class);
        new Gson().fromJson(responseAddTicket, AddTicketDtoResponse.class);
        // Delete doctor
        HttpHeaders headersAdmin = loginSuperadmin();
        entity = new HttpEntity<>(new Gson().toJson(new DeleteDoctorDtoRequest("05-05-2020")), headersAdmin);
        String dtoResponse = restTemplate.exchange(restUrl + "doctors/" + doctor.getId(), HttpMethod.DELETE, entity, String.class).getBody();
        assertEquals(dtoResponse, "\"{}\"");
    }

    //3.15 POST /api/tickets
    @Test
    public void testAddTicket() throws ServerException {
        addDoctors();
        Doctor doctor = doctorDao.getBySpeciality("spec").get(0);
        // Register patients
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(new Gson().toJson(new RegisterPatientDtoRequest("Имя", "Фамилия", "Отчество", "e@mail.net", "Адрес", "89012345678", "Login", "password")), headers);
        ResponseEntity<LoginDtoResponse> response = restTemplate.postForEntity(restUrl + "patients", entity, LoginDtoResponse.class);
        headers.add("Cookie", response.getHeaders().get("Set-Cookie").get(0));
        // Add ticket
        entity = new HttpEntity<>(new Gson().toJson(new AddTicketDtoRequest(doctor.getId() + "", doctor.getSpeciality().getName(), "04-05-2020", "08:00")), headers);
        String responseAddTicket = restTemplate.postForObject(restUrl + "tickets", entity, String.class);
        AddTicketDtoResponse dtoResponse = new Gson().fromJson(responseAddTicket, AddTicketDtoResponse.class);
        assertAll(
                () -> assertNotEquals(dtoResponse.getDoctorId(), 0),
                () -> assertEquals(dtoResponse.getTicket(), "D" + dtoResponse.getDoctorId() + "040520200800"),
                () -> assertEquals(dtoResponse.getFirstName(), "Имя"),
                () -> assertEquals(dtoResponse.getLastName(), "Фамилия"),
                () -> assertEquals(dtoResponse.getPatronymic(), "Отчество"),
                () -> assertEquals(dtoResponse.getRoom(), "1"),
                () -> assertEquals(dtoResponse.getSpeciality(), "spec"),
                () -> assertEquals(dtoResponse.getDate(), "04-05-2020"),
                () -> assertEquals(dtoResponse.getTime(), "08:00")
        );
    }

    //3.16 DELETE /api/tickets/номер_талона
    @Test
    public void testCancelTicket() throws ServerException {
        addDoctors();
        Doctor doctor = doctorDao.getBySpeciality("spec").get(0);
        // Register patient
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(new Gson().toJson(new RegisterPatientDtoRequest("Имя", "Фамилия", "Отчество", "e@mail.net", "Адрес", "89012345678", "Login", "password")), headers);
        ResponseEntity<LoginDtoResponse> response = restTemplate.postForEntity(restUrl + "patients", entity, LoginDtoResponse.class);
        headers.add("Cookie", response.getHeaders().get("Set-Cookie").get(0));
        // Add ticket
        entity = new HttpEntity<>(new Gson().toJson(new AddTicketDtoRequest(doctor.getId() + "", doctor.getSpeciality().getName(), "04-05-2020", "08:00")), headers);
        String responseAddTicket = restTemplate.postForObject(restUrl + "tickets", entity, String.class);
        AddTicketDtoResponse dtoAddResponse = new Gson().fromJson(responseAddTicket, AddTicketDtoResponse.class);
        // Cancel ticket
        String responseCancelTicket = restTemplate.exchange(restUrl + "tickets/" + dtoAddResponse.getTicket(), HttpMethod.DELETE, entity, String.class).getBody();
        assertEquals(responseCancelTicket, "\"{}\"");
        String responseCancelTicket2 = restTemplate.exchange(restUrl + "tickets/" + dtoAddResponse.getTicket(), HttpMethod.DELETE, entity, String.class).getBody();
        ExceptionDtoResponse dtoCancelResponse2 = new Gson().fromJson(responseCancelTicket2, ExceptionDtoResponse.class);
        assertEquals(dtoCancelResponse2.getErrors().get(0).getErrorCode(), "CANCEL_TICKET_FAIL");
    }

    //3.17 POST /api/commissions
    @Test
    public void testAddCommission() {
        addDoctors();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(new Gson().toJson(new LoginDtoRequest("doc1Login", "doc1Pwd")), headers);
        ResponseEntity<LoginDtoResponse> responseDoctor = restTemplate.postForEntity(restUrl + "sessions", entity, LoginDtoResponse.class);
        // Register patient
        HttpHeaders headerPatient = new HttpHeaders();
        headerPatient.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entityPatient = new HttpEntity<>(new Gson().toJson(new RegisterPatientDtoRequest("Имя", "Фамилия", "Отчество", "e@mail.net", "Адрес", "89012345678", "Login", "password")), headerPatient);
        ResponseEntity<LoginDtoResponse> responsePatient = restTemplate.postForEntity(restUrl + "patients", entityPatient, LoginDtoResponse.class);
        // Add ticket to commission
        headers.add("Cookie", responseDoctor.getHeaders().get("Set-Cookie").get(0));
        HttpEntity<String> entityCommission = new HttpEntity<>(new Gson().toJson(new AddCommissionDtoRequest(
                responsePatient.getBody().getId(),
                new Integer[]{responseDoctor.getBody().getId()},
                "1",
                "05-05-2020",
                "09:00",
                25
        )), headers);
        String responseAddTicket = restTemplate.postForObject(restUrl + "commissions", entityCommission, String.class);
        AddCommissionDtoResponse dtoAddResponse = new Gson().fromJson(responseAddTicket, AddCommissionDtoResponse.class);
        assertAll(
                () -> assertEquals("CD" + responseDoctor.getBody().getId() + "D050520200900", dtoAddResponse.getTicket()),
                () -> assertEquals(responsePatient.getBody().getId(), dtoAddResponse.getPatientId()),
                () -> assertEquals(responseDoctor.getBody().getId(), dtoAddResponse.getDoctorIds()[0]),
                () -> assertEquals("1", dtoAddResponse.getRoom()),
                () -> assertEquals("05-05-2020", dtoAddResponse.getDate()),
                () -> assertEquals("09:00", dtoAddResponse.getTime()),
                () -> assertEquals(25, dtoAddResponse.getDuration())
        );
    }

    //3.18 DELETE /api/commissions/номер_талона_на_комиссию
    @Test
    public void testCancelCommission() {
        addDoctors();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(new Gson().toJson(new LoginDtoRequest("doc1Login", "doc1Pwd")), headers);
        ResponseEntity<LoginDtoResponse> responseDoctor = restTemplate.postForEntity(restUrl + "sessions", entity, LoginDtoResponse.class);
        // Register patient
        HttpHeaders headerPatient = new HttpHeaders();
        headerPatient.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entityPatient = new HttpEntity<>(new Gson().toJson(new RegisterPatientDtoRequest("Имя", "Фамилия", "Отчество", "e@mail.net", "Адрес", "89012345678", "Login", "password")), headerPatient);
        ResponseEntity<LoginDtoResponse> responsePatient = restTemplate.postForEntity(restUrl + "patients", entityPatient, LoginDtoResponse.class);
        // Add ticket to commission
        headers.add("Cookie", responseDoctor.getHeaders().get("Set-Cookie").get(0));
        HttpEntity<String> entityCommission = new HttpEntity<>(new Gson().toJson(new AddCommissionDtoRequest(
                responsePatient.getBody().getId(),
                new Integer[]{responseDoctor.getBody().getId()},
                "1",
                "05-05-2020",
                "09:00",
                25
        )), headers);
        String responseAddTicket = restTemplate.postForObject(restUrl + "commissions", entityCommission, String.class);
        AddCommissionDtoResponse dtoAddResponse = new Gson().fromJson(responseAddTicket, AddCommissionDtoResponse.class);
        //Cancel commission
        headerPatient.add("Cookie", responsePatient.getHeaders().get("Set-Cookie").get(0));
        entityPatient = new HttpEntity<>(headerPatient);
        String responseCancelCommission = restTemplate.exchange(restUrl + "commissions/" + dtoAddResponse.getTicket(), HttpMethod.DELETE, entityPatient, String.class).getBody();
        assertEquals(responseCancelCommission, "\"{}\"");
        responseCancelCommission = restTemplate.exchange(restUrl + "commissions/" + dtoAddResponse.getTicket(), HttpMethod.DELETE, entityPatient, String.class).getBody();
        ExceptionDtoResponse dtoCancelResponse = new Gson().fromJson(responseCancelCommission, ExceptionDtoResponse.class);
        assertEquals(dtoCancelResponse.getErrors().get(0).getErrorCode(), "CANCEL_TICKET_FAIL");
    }

    //3.19 GET /api/tickets
    @Test
    public void testGetTickets() throws ServerException {
        addDoctors();
        Doctor doctor = doctorDao.getBySpeciality("spec").get(0);
        // Register patients
        HttpHeaders headersPatient = new HttpHeaders();
        headersPatient.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(new Gson().toJson(new RegisterPatientDtoRequest("Имя", "Фамилия", "Отчество", "e@mail.net", "Адрес", "89012345678", "Login", "password")), headersPatient);
        ResponseEntity<LoginDtoResponse> responsePatient = restTemplate.postForEntity(restUrl + "patients", entity, LoginDtoResponse.class);
        headersPatient.add("Cookie", responsePatient.getHeaders().get("Set-Cookie").get(0));
        // Add ticket
        entity = new HttpEntity<>(new Gson().toJson(new AddTicketDtoRequest(doctor.getId() + "", doctor.getSpeciality().getName(), "04-05-2020", "08:00")), headersPatient);
        String responseAddTicket = restTemplate.postForObject(restUrl + "tickets", entity, String.class);
        AddTicketDtoResponse dtoResponse = new Gson().fromJson(responseAddTicket, AddTicketDtoResponse.class);
        // login doctor
        HttpHeaders headersDoctor = new HttpHeaders();
        headersDoctor.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entityDoctor = new HttpEntity<>(new Gson().toJson(new LoginDtoRequest("doc1Login", "doc1Pwd")), headersDoctor);
        ResponseEntity<LoginDtoResponse> responseDoctor = restTemplate.postForEntity(restUrl + "sessions", entityDoctor, LoginDtoResponse.class);
        // Add ticket to commission
        headersDoctor.add("Cookie", responseDoctor.getHeaders().get("Set-Cookie").get(0));
        HttpEntity<String> entityCommission = new HttpEntity<>(new Gson().toJson(new AddCommissionDtoRequest(
                responsePatient.getBody().getId(),
                new Integer[]{responseDoctor.getBody().getId()},
                "1",
                "05-05-2020",
                "09:00",
                25
        )), headersDoctor);
        String responseAddCommission = restTemplate.postForObject(restUrl + "commissions", entityCommission, String.class);
        AddCommissionDtoResponse dtoAddCommission = new Gson().fromJson(responseAddCommission, AddCommissionDtoResponse.class);
        // Get tickets
        entity = new HttpEntity<>(headersPatient);
        String responseGetTicketsString = restTemplate.exchange(restUrl + "tickets/", HttpMethod.GET, entity, String.class).getBody();
        Type listType = new TypeToken<ArrayList<GetTicketDto>>() {
        }.getType();
        List<GetTicketDto> responseGetTickets = new Gson().fromJson(responseGetTicketsString, listType);
        assertAll(
                // Reception
                () -> assertEquals(2, responseGetTickets.size()),
                () -> assertEquals(dtoResponse.getTicket(), responseGetTickets.get(0).getTicket()),
                () -> assertEquals(dtoResponse.getDoctorId(), responseGetTickets.get(0).getDoctorId()),
                () -> assertEquals(dtoResponse.getRoom(), responseGetTickets.get(0).getRoom()),
                () -> assertEquals(dtoResponse.getSpeciality(), responseGetTickets.get(0).getSpeciality()),
                () -> assertEquals(dtoResponse.getDate(), responseGetTickets.get(0).getDate()),
                () -> assertEquals(dtoResponse.getTime(), responseGetTickets.get(0).getTime()),
                () -> assertEquals(dtoResponse.getFirstName(), responseGetTickets.get(0).getFirstName()),
                () -> assertEquals(dtoResponse.getLastName(), responseGetTickets.get(0).getLastName()),
                () -> assertEquals(dtoResponse.getPatronymic(), responseGetTickets.get(0).getPatronymic()),
                // Commission
                () -> assertEquals(dtoAddCommission.getTicket(), responseGetTickets.get(1).getTicket()),
                () -> assertEquals(dtoAddCommission.getRoom(), responseGetTickets.get(1).getRoom()),
                () -> assertEquals(dtoAddCommission.getDate(), responseGetTickets.get(1).getDate()),
                () -> assertEquals(dtoAddCommission.getTime(), responseGetTickets.get(1).getTime()),
                () -> assertEquals(1, responseGetTickets.get(1).getDoctorsCommissionList().size())

        );
    }

    //3.20 GET /api/statistic
    @Test
    public void testGetStatistic() throws ServerException {
        addDoctors();
        Doctor doctor = doctorDao.getBySpeciality("spec").get(0);
        HttpHeaders headersAdmin = loginSuperadmin();
        // Register patient
        HttpHeaders headersPatient = new HttpHeaders();
        headersPatient.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(new Gson().toJson(new RegisterPatientDtoRequest("Имя", "Фамилия", "Отчество", "e@mail.net", "Адрес", "89012345678", "Login", "password")), headersPatient);
        ResponseEntity<LoginDtoResponse> responsePatient = restTemplate.postForEntity(restUrl + "patients", entity, LoginDtoResponse.class);
        headersPatient.add("Cookie", responsePatient.getHeaders().get("Set-Cookie").get(0));
        // Add ticket
        entity = new HttpEntity<>(new Gson().toJson(new AddTicketDtoRequest(doctor.getId() + "", doctor.getSpeciality().getName(), "04-05-2020", "08:00")), headersPatient);
        restTemplate.postForObject(restUrl + "tickets", entity, String.class);
        // Get doctor statistic
        entity = new HttpEntity<>(headersAdmin);
        ResponseEntity<String> responseGetTicketsString = restTemplate.exchange(restUrl + "statistic?startDate=01-05-2020&endDate=05-05-2020&doctor=" + doctor.getId(), HttpMethod.GET, entity, String.class);
        StatisticDtoResponse dtoResponse = new Gson().fromJson(responseGetTicketsString.getBody(), StatisticDtoResponse.class);
        assertAll(
                () -> assertEquals(dtoResponse.getDoctorId(), doctor.getId()),
                () -> assertNull(dtoResponse.getPatientId()),
                () -> assertEquals(dtoResponse.getFreeTicketsCount(), 23),
                () -> assertEquals(dtoResponse.getReceptionTicketsCount(), 1),
                () -> assertEquals(dtoResponse.getCommissionTicketsCount(), 0)
        );
        responseGetTicketsString = restTemplate.exchange(restUrl + "statistic?startDate=01-05-2020&endDate=05-05-2020&patient=" + responsePatient.getBody().getId(), HttpMethod.GET, entity, String.class);
        StatisticDtoResponse dtoResponsePatient = new Gson().fromJson(responseGetTicketsString.getBody(), StatisticDtoResponse.class);
        assertAll(
                () -> assertEquals(dtoResponsePatient.getPatientId(), responsePatient.getBody().getId()),
                () -> assertNull(dtoResponsePatient.getDoctorId()),
                () -> assertEquals(dtoResponsePatient.getFreeTicketsCount(), 0),
                () -> assertEquals(dtoResponsePatient.getReceptionTicketsCount(), 1),
                () -> assertEquals(dtoResponsePatient.getCommissionTicketsCount(), 0)
        );
    }

    //3.21
    @Test
    public void testGetServerSettings() {
        final ResponseEntity<GetServerSettingsDtoResponse> response = restTemplate.getForEntity(restUrl + "settings", GetServerSettingsDtoResponse.class);
        assertAll(
                () -> assertEquals(response.getBody().getMaxNameLength(), applicationProperties.getMaxNameLength()),
                () -> assertEquals(response.getBody().getMinPasswordLength(), applicationProperties.getMinPasswordLength())
        );
    }


    private HttpHeaders loginSuperadmin() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(new Gson().toJson(new LoginDtoRequest("admin", "admin")), headers);
        ResponseEntity<LoginDtoResponse> response = restTemplate.postForEntity(restUrl + "sessions", entity, LoginDtoResponse.class);
        headers.add("Cookie", response.getHeaders().get("Set-Cookie").get(0));
        return headers;
    }

    private void addDoctors() {
        HttpHeaders headers = loginSuperadmin();
        RegisterDoctorDtoRequest dtoRequest = new RegisterDoctorDtoRequest(
                "Имя",
                "Фамилия",
                "Отчество",
                "spec",
                "1",
                "doc1Login",
                "doc1Pwd",
                "01-05-2020",
                "10-05-2020",
                new WeekScheduleDtoRequest("08:00", "14:00", new String[]{"Mon", "Tue"}),
                15
        );
        HttpEntity<String> entity = new HttpEntity<>(new Gson().toJson(dtoRequest), headers);
        restTemplate.postForObject(restUrl + "doctors", entity, String.class);

        DayScheduleDtoRequest dayScheduleDtoRequest1 = new DayScheduleDtoRequest("Mon", "09:00", "15:00");
        DayScheduleDtoRequest dayScheduleDtoRequest2 = new DayScheduleDtoRequest("Fri", "09:00", "15:00");
        WeekDaysScheduleDtoRequest weekDaysScheduleDtoRequest = new WeekDaysScheduleDtoRequest(new DayScheduleDtoRequest[]{dayScheduleDtoRequest1, dayScheduleDtoRequest2});

        dtoRequest = new RegisterDoctorDtoRequest(
                "Имя",
                "Фамилияа",
                "Отчество",
                "Айболит",
                "13",
                "doc2Login",
                "doc2Pwd",
                "01-05-2020",
                "10-05-2020",
                weekDaysScheduleDtoRequest,
                15
        );
        entity = new HttpEntity<>(new Gson().toJson(dtoRequest), headers);
        restTemplate.postForObject(restUrl + "doctors", entity, String.class);
    }

}
