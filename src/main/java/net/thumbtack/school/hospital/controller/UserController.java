package net.thumbtack.school.hospital.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import net.thumbtack.school.hospital.dto.request.LoginDtoRequest;
import net.thumbtack.school.hospital.dto.response.EmptyResponse;
import net.thumbtack.school.hospital.dto.response.LoginDtoResponse;
import net.thumbtack.school.hospital.serverexception.ServerException;
import net.thumbtack.school.hospital.service.UserService;
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
public class UserController {

    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/sessions", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public LoginDtoResponse loginUser(
            @RequestBody LoginDtoRequest dtoRequest,
            HttpServletResponse response
    ) throws ServerException {
        String cookie = UUID.randomUUID().toString();
        LoginDtoResponse dto = userService.loginUser(dtoRequest, cookie);
        response.addCookie(new Cookie("JAVASESSIONID", cookie));
        return dto;
    }

    @DeleteMapping(path = "/sessions", produces = MediaType.APPLICATION_JSON_VALUE)
    public String logoutUser(
            @CookieValue("JAVASESSIONID") String cookie,
            HttpServletResponse response
    ) throws ServerException {
        String dto = userService.logoutUser(cookie);
        response.addCookie(new Cookie("JAVASESSIONID", null));
        return dto;
    }

    @GetMapping(path = "/sessions", produces = MediaType.APPLICATION_JSON_VALUE)
    public LoginDtoResponse infoUser(
            @CookieValue("JAVASESSIONID") String cookie,
            HttpServletResponse response
    ) throws ServerException {
        LoginDtoResponse dto = userService.infoUser(cookie);
        return dto;
    }

}
