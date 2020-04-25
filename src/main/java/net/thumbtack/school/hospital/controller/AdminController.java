package net.thumbtack.school.hospital.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


import net.thumbtack.school.hospital.dto.request.*;
import net.thumbtack.school.hospital.dto.response.DeleteDoctorDtoResponse;
import net.thumbtack.school.hospital.dto.response.EditAdminDtoResponse;
import net.thumbtack.school.hospital.dto.response.GetServerSettingsDtoResponse;
import net.thumbtack.school.hospital.dto.response.LoginDtoResponse;
import net.thumbtack.school.hospital.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import net.thumbtack.school.hospital.serverexception.ServerException;

@RestController
@RequestMapping("/api")
@Validated
public class AdminController {

	private AdminService adminService;

	@Autowired
	public AdminController(AdminService adminService) {
		this.adminService = adminService;
	}


	// REVU совершенно чудовищное форматирование, читать невозможно
	// Ctrl-Alt-L
	@PostMapping(
			path = "/admins",
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE
	)
	public LoginDtoResponse registerAdmin(
			@RequestBody @Valid RegisterAdminDtoRequest dtoRequest,
			@CookieValue("JAVASESSIONID") String cookie,
			HttpServletResponse response
	) throws ServerException {
		LoginDtoResponse dto = adminService.registerAdmin(dtoRequest, cookie);
		response.addCookie(new Cookie("JAVASESSIONID", cookie));
		return dto;
	}

	@PutMapping(
			path = "/admins",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public EditAdminDtoResponse editAdminAccount(
			@CookieValue("JAVASESSIONID") String cookie,
			@RequestBody @Valid EditAdminDtoRequest dtoRequest
	) throws ServerException {
		EditAdminDtoResponse dto = adminService.editAdmin(dtoRequest, cookie);
		return dto;
	}

	@PostMapping(
			path = "/doctors",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public LoginDtoResponse registerDoctor(
			@CookieValue("JAVASESSIONID") String cookie,
			@RequestBody @Valid RegisterDoctorDtoRequest dtoRequest
	) throws ServerException {
		LoginDtoResponse dto = adminService.registerDoctor(dtoRequest, cookie);
		return dto;
	}

	@PutMapping(
			path = "/doctors/{doctorId}",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public LoginDtoResponse editScheduleDoctor(
			@CookieValue("JAVASESSIONID") String cookie,
			@RequestBody @Valid EditScheduleDtoRequest dtoRequest,
			@RequestParam(name = "doctorId") long doctorId
	) throws ServerException {
		LoginDtoResponse dto = adminService.editScheduleDoctor(dtoRequest, doctorId, cookie);
		return dto;
	}

	@DeleteMapping(
			path = "/doctors/{doctorId}",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public DeleteDoctorDtoResponse deleteDoctor(
			@CookieValue("JAVASESSIONID") String cookie,
			@RequestBody @Valid DeleteDoctorDtoRequest dtoRequest,
			@RequestParam(name = "doctorId") long doctorId
	) throws ServerException {
		DeleteDoctorDtoResponse  dto = adminService.deleteDoctor(dtoRequest, doctorId, cookie);
		return dto;
	}

	@GetMapping(
			path = "/settings",
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public GetServerSettingsDtoResponse getServerSettings(
			@CookieValue(value = "JAVASESSIONID", defaultValue = "") String cookie
	) throws ServerException {
		GetServerSettingsDtoResponse dto = adminService.getServerSettings(cookie);
		return dto;
	}

}
