package net.thumbtack.school.hospital.controller;

import net.thumbtack.school.hospital.dto.request.AddCommissionDtoRequest;
import net.thumbtack.school.hospital.dto.response.AddCommissionDtoResponse;
import net.thumbtack.school.hospital.dto.response.LoginDtoResponse;
import net.thumbtack.school.hospital.serverexception.ServerException;
import net.thumbtack.school.hospital.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@Validated
public class DoctorController {

    private DoctorService doctorService;

    @Autowired
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }


    @GetMapping(path = "/doctors/{docId}")
    public LoginDtoResponse infoDoctor(
            @CookieValue("JAVASESSIONID") String cookie,
            @PathVariable("docId") long docId,
            @RequestParam(name = "schedule") String schedule,
            @RequestParam(name = "startDate") String startDate,
            @RequestParam(name = "endDate") String endDate
    ) throws ServerException {
        LoginDtoResponse dto = doctorService.doctorInfo(cookie, docId, schedule, startDate, endDate);
        return dto;
    }

    @GetMapping(path = "/doctors")
    public List<LoginDtoResponse> infoDoctors(
            @CookieValue("JAVASESSIONID") String cookie,
            @RequestParam(name = "speciality") String speciality,
            @RequestParam(name = "schedule") String schedule,
            @RequestParam(name = "startDate") String startDate,
            @RequestParam(name = "endDate") String endDate
    ) throws ServerException {
        List<LoginDtoResponse> dto = doctorService.doctorsInfo(cookie, speciality, schedule, startDate, endDate);
        return dto;
    }

    @GetMapping(path = "/commissions")
    public AddCommissionDtoResponse addCommission(
            @CookieValue("JAVASESSIONID") String cookie,
            @RequestBody @Valid AddCommissionDtoRequest dtoRequest
    ) throws ServerException {
        AddCommissionDtoResponse dto = doctorService.addCommission(cookie, dtoRequest);
        return dto;
    }

}
