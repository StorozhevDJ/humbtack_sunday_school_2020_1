// REVU слишком много контроллеров. Думаю, 3-4 вполне хватит
package net.thumbtack.school.hospital.controller;

import net.thumbtack.school.hospital.dto.request.AddTicketDtoRequest;
import net.thumbtack.school.hospital.dto.request.EditPatientDtoRequest;
import net.thumbtack.school.hospital.dto.request.RegisterPatientDtoRequest;
import net.thumbtack.school.hospital.dto.response.AddTicketDtoResponse;
import net.thumbtack.school.hospital.dto.response.EmptyDtoResponse;
import net.thumbtack.school.hospital.dto.response.getticket.GetTicketListDtoResponse;
import net.thumbtack.school.hospital.dto.response.LoginDtoResponse;
import net.thumbtack.school.hospital.serverexception.ServerException;
import net.thumbtack.school.hospital.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@Validated
public class PatientController {

    private final PatientService patientService;

    private static final String COOKIE_NAME = "JAVASESSIONID";


    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    /**
     * 3.4. Patients - registering new patients
     * POST /api/patients
     */
    @PostMapping(path = "/patients", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public LoginDtoResponse registerPatients(
            @RequestBody @Valid RegisterPatientDtoRequest dtoRequest,
            HttpServletResponse response
    ) throws ServerException {
        String cookie = UUID.randomUUID().toString();
        LoginDtoResponse responseDto = patientService.registerPatient(cookie, dtoRequest);
        response.addCookie(new Cookie(COOKIE_NAME, cookie));
        return responseDto;
    }

    /**
     * 3.10. Administrator or doctor - get patient information
     * GET /api/patients/идентификатор_пациента
     */
    @GetMapping(path = "/patients/{patientId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public LoginDtoResponse infoPatient(
            @PathVariable("patientId") int patientId,
            @CookieValue(COOKIE_NAME) String cookie
    ) throws ServerException {
        return patientService.infoPatient(cookie, patientId);
    }

    /**
     * 3.13. Patient - change current user account
     * PUT /api/patients
     */
    @PutMapping(path = "/patients", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public LoginDtoResponse editPatient(
            @CookieValue(COOKIE_NAME) String cookie,
            @RequestBody @Valid EditPatientDtoRequest dtoRequest
    ) throws ServerException {
        return patientService.editPatient(cookie, dtoRequest);
    }

    /**
     * 3.15. Patient - record to the doctor
     * POST /api/tickets
     */
    @PostMapping(path = "/tickets", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public AddTicketDtoResponse addTicket(
            @RequestBody @Valid AddTicketDtoRequest dtoRequest,
            @CookieValue(COOKIE_NAME) String cookie
    ) throws ServerException {
        return patientService.addTicket(cookie, dtoRequest);
    }

    /**
     * 3.16. Cancel ticket to the doctor
     * DELETE /api/tickets/номер_талона
     */
    @DeleteMapping(path = "/tickets/{ticket}", produces = MediaType.APPLICATION_JSON_VALUE)
    public EmptyDtoResponse cancelTicket(
            @PathVariable("ticket") String ticket,
            @CookieValue(COOKIE_NAME) String cookie
    ) throws ServerException {
        return patientService.cancelTicket(cookie, ticket);
    }

    /**
     * 3.18. Patient - cancel the commission
     * DELETE /api/commissions/номер_талона_на_комиссию
     */
    @DeleteMapping(path = "/commissions/{ticket}", produces = MediaType.APPLICATION_JSON_VALUE)
    public EmptyDtoResponse cancelCommission(
            @PathVariable("ticket") String ticket,
            @CookieValue(COOKIE_NAME) String cookie
    ) throws ServerException {
        return patientService.cancelCommission(cookie, ticket);
    }

    /**
     * 3.19. Patient - get tickets list
     * GET /api/tickets
     */
    @GetMapping(path = "/tickets", produces = MediaType.APPLICATION_JSON_VALUE)
    public GetTicketListDtoResponse getTicketsList(
            @CookieValue(COOKIE_NAME) String cookie
    ) throws ServerException {
        return patientService.getTicketsList(cookie);
    }
}
