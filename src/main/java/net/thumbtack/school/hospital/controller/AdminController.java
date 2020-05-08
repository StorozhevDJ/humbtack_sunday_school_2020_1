package net.thumbtack.school.hospital.controller;

import net.thumbtack.school.hospital.dto.request.*;
import net.thumbtack.school.hospital.dto.response.EditScheduleDtoResponse;
import net.thumbtack.school.hospital.dto.response.EmptyDtoResponse;
import net.thumbtack.school.hospital.dto.response.LoginDtoResponse;
import net.thumbtack.school.hospital.serverexception.ServerException;
import net.thumbtack.school.hospital.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController
@RequestMapping("/api")
@Validated
public class AdminController {

    private AdminService adminService;

    private static final String COOKIE_NAME = "JAVASESSIONID";

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    /**
     * 3.2. Administrator - registering new administrator
     * POST /api/admins
     */
    @PostMapping(path = "/admins", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public LoginDtoResponse registerAdmin(
            @RequestBody @Valid RegisterAdminDtoRequest dtoRequest,
            @CookieValue(COOKIE_NAME) String cookie,
            HttpServletResponse response
    ) throws ServerException {
        LoginDtoResponse dto = adminService.registerAdmin(dtoRequest, cookie);
        response.addCookie(new Cookie(COOKIE_NAME, cookie));
        return dto;
    }

    /**
     * 3.11. Administrator - edit administrator account information
     * PUT /api/admins
     */
    @PutMapping(path = "/admins", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public LoginDtoResponse editAdminAccount(
            @CookieValue(COOKIE_NAME) String cookie,
            @RequestBody @Valid EditAdminDtoRequest dtoRequest
    ) throws ServerException {
        return adminService.editAdmin(dtoRequest, cookie);
    }

    /**
     * 3.3. Administrator - registering new doctor and set schedule
     * POST /api/doctors
     */
    @PostMapping(path = "/doctors", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public EditScheduleDtoResponse registerDoctor(
            @CookieValue(COOKIE_NAME) String cookie,
            @RequestBody @Valid RegisterDoctorDtoRequest dtoRequest
    ) throws ServerException {
        return adminService.registerDoctor(dtoRequest, cookie);
    }

    /**
     * 3.12. Administrator - change doctor schedule
     * PUT /api/doctors/идентификатор_врача
     */
    @PutMapping(path = "/doctors/{doctorId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public EditScheduleDtoResponse editScheduleDoctor(
            @CookieValue(COOKIE_NAME) String cookie,
            @RequestBody @Valid EditScheduleDtoRequest dtoRequest,
            @PathVariable(name = "doctorId") int doctorId
    ) throws ServerException {
        return adminService.editScheduleDoctor(dtoRequest, doctorId, cookie);
    }

    /**
     * 3.14. Administrator - delete doctor
     * DELETE /api/doctors/идентификатор_врача
     */
    @DeleteMapping(path = "/doctors/{doctorId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public EmptyDtoResponse deleteDoctor(
            @CookieValue(COOKIE_NAME) String cookie,
            @RequestBody @Valid DeleteDoctorDtoRequest dtoRequest,
            @PathVariable(name = "doctorId") @Min(1) int doctorId
    ) throws ServerException {
        return adminService.deleteDoctor(dtoRequest, doctorId, cookie);
    }


}
