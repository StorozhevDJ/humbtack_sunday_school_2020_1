package net.thumbtack.school.hospital.controller;

import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.thumbtack.school.hospital.dto.request.AddAdminDtoRequest;
import net.thumbtack.school.hospital.dto.response.AddAdminDtoResponse;
import net.thumbtack.school.hospital.serverException.ServerException;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public AddAdminDtoResponse registerUser(
			@RequestBody @Valid AddAdminDtoRequest registerDto,
			HttpServletResponse response
	) throws ServerException {
		String cookie = UUID.randomUUID().toString();
		AddAdminDtoResponse dto = null;// = adminService.registerAdmin(registerDto, cookie);
		response.addCookie(new Cookie("JAVASESSIONID", cookie));
		return dto;
	}

}
