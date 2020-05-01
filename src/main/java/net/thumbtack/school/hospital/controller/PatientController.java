// REVU слишком много контроллеров. Думаю, 3-4 вполне хватит
package net.thumbtack.school.hospital.controller;

import net.thumbtack.school.hospital.dto.request.AddTicketDtoRequest;
import net.thumbtack.school.hospital.dto.request.EditPatientDtoRequest;
import net.thumbtack.school.hospital.dto.request.RegisterPatientDtoRequest;
import net.thumbtack.school.hospital.dto.response.AddTicketDtoResponse;
import net.thumbtack.school.hospital.dto.response.EmptyResponse;
import net.thumbtack.school.hospital.dto.response.GetTicketDtoResponse;
import net.thumbtack.school.hospital.dto.response.LoginDtoResponse;
import net.thumbtack.school.hospital.serverexception.ServerException;
import net.thumbtack.school.hospital.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@Validated
public class PatientController {

    private PatientService patientService;


    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }


    @PostMapping(path = "/patients", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public LoginDtoResponse registerPatients(
            @RequestBody @Valid RegisterPatientDtoRequest dtoRequest,
            // REVU private static final COOKIE_NAME = "JAVASESSIONID" и везде его 
            @CookieValue("JAVASESSIONID") String cookie
    ) throws ServerException {
        LoginDtoResponse dto = patientService.registerPatient(cookie, dtoRequest);
        return dto;
    }

    @GetMapping(path = "/patients/{patientId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public LoginDtoResponse infoPatient(
            @PathVariable("patientId") long patientId,
            @CookieValue("JAVASESSIONID") String cookie
    ) throws ServerException {
        LoginDtoResponse dto = patientService.infoPatient(cookie, patientId);
        return dto;
    }

    @PutMapping(path = "/patients", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public LoginDtoResponse editPatient(
            @CookieValue("JAVASESSIONID") String cookie,
            @RequestBody @Valid EditPatientDtoRequest dtoRequest
    ) throws ServerException {
        LoginDtoResponse dto = patientService.editPatient(cookie, dtoRequest);
        return dto;
    }

    @PostMapping(path = "/tickets", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public AddTicketDtoResponse addTicket(
            @RequestBody @Valid AddTicketDtoRequest dtoRequest,
            @CookieValue("JAVASESSIONID") String cookie
    ) throws ServerException {
        AddTicketDtoResponse dto = patientService.addTicket(cookie, dtoRequest);
        return dto;
    }

    @DeleteMapping(path = "/tickets/{ticket}", produces = MediaType.APPLICATION_JSON_VALUE)
    public EmptyResponse cancelTicket(
            @PathVariable("ticket") String ticket,
            @CookieValue("JAVASESSIONID") String cookie
    ) throws ServerException {
        EmptyResponse dto = patientService.cancelTicket(cookie, ticket);
        return dto;
    }

    @DeleteMapping(path = "/commissions/{ticket}", produces = MediaType.APPLICATION_JSON_VALUE)
    public EmptyResponse cancelCommission(
            @PathVariable("ticket") String ticket,
            @CookieValue("JAVASESSIONID") String cookie
    ) throws ServerException {
        EmptyResponse dto = patientService.cancelCommission(cookie, ticket);
        return dto;
    }

    @GetMapping(path = "/tickets", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<GetTicketDtoResponse> infoPatient(
            @CookieValue("JAVASESSIONID") String cookie
    ) throws ServerException {
        List<GetTicketDtoResponse> dto = patientService.getTicketsList(cookie);
        return dto;
    }
}
