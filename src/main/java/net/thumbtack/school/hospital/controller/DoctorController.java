package net.thumbtack.school.hospital.controller;

import net.thumbtack.school.hospital.dto.request.AddCommissionDtoRequest;
import net.thumbtack.school.hospital.dto.response.AddCommissionDtoResponse;
import net.thumbtack.school.hospital.dto.response.EditScheduleDtoResponse;
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

    private static final String COOKIE_NAME = "JAVASESSIONID";

    @Autowired
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    /**
     * 3.8. Get doctor information
     * GET /api/doctors/идентификатор_врача
     */
    @GetMapping(path = "/doctors/{docId}")
    public EditScheduleDtoResponse infoDoctor(
            @CookieValue(COOKIE_NAME) String cookie,
            @PathVariable("docId") int docId,
            @RequestParam(name = "schedule") String schedule,
            @RequestParam(name = "startDate", required = false) String startDate,
            @RequestParam(name = "endDate", required = false) String endDate
    ) throws ServerException {
        EditScheduleDtoResponse dto = doctorService.doctorInfo(cookie, docId, schedule, startDate, endDate);
        return dto;
    }

    /**
     * 3.9. Get doctors information
     * GET /api/doctors
     */
    @GetMapping(path = "/doctors")
    public List<EditScheduleDtoResponse> infoDoctors(
            @CookieValue(COOKIE_NAME) String cookie,
            @RequestParam(name = "speciality") String speciality,
            @RequestParam(name = "schedule", defaultValue = "no") String schedule,
            @RequestParam(name = "startDate", required = false) String startDate,
            @RequestParam(name = "endDate", required = false) String endDate
    ) throws ServerException {
        List<EditScheduleDtoResponse> dto = doctorService.doctorsInfo(cookie, speciality, schedule, startDate, endDate);
        return dto;
    }

    /**
     * 3.17. Doctor - appointment of medical commission
     * POST /api/commissions
     */
    @PostMapping(path = "/commissions")
    public AddCommissionDtoResponse addCommission(
            @CookieValue(COOKIE_NAME) String cookie,
            @RequestBody @Valid AddCommissionDtoRequest dtoRequest
    ) throws ServerException {
        AddCommissionDtoResponse dto = doctorService.addCommission(cookie, dtoRequest);
        return dto;
    }

}
