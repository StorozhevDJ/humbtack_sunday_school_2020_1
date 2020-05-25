package net.thumbtack.school.hospital.controller;

import net.thumbtack.school.hospital.dto.request.LoginDtoRequest;
import net.thumbtack.school.hospital.dto.response.EmptyDtoResponse;
import net.thumbtack.school.hospital.dto.response.GetServerSettingsDtoResponse;
import net.thumbtack.school.hospital.dto.response.LoginDtoResponse;
import net.thumbtack.school.hospital.serverexception.ServerException;
import net.thumbtack.school.hospital.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@Validated
public class UserController {

    private final UserService userService;

    private static final String COOKIE_NAME = "JAVASESSIONID";

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 3.5. Login.
     * POST /api/sessions
     */
    @PostMapping(path = "/sessions", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public LoginDtoResponse loginUser(
            @RequestBody LoginDtoRequest dtoRequest,
            HttpServletResponse response
    ) throws ServerException {
        String cookie = UUID.randomUUID().toString();
        LoginDtoResponse dto = userService.loginUser(dtoRequest, cookie);
        response.addCookie(new Cookie(COOKIE_NAME, cookie));
        return dto;
    }

    /**
     * 3.6. Logout.
     * DELETE /api/sessions
     */
    @DeleteMapping(path = "/sessions", produces = MediaType.APPLICATION_JSON_VALUE)
    public EmptyDtoResponse logoutUser(
            @CookieValue(COOKIE_NAME) String cookie,
            HttpServletResponse response
    ) throws ServerException {
        EmptyDtoResponse dto = userService.logoutUser(cookie);
        response.addCookie(new Cookie(COOKIE_NAME, null));
        return dto;
    }

    /**
     * 3.7. Get current user information
     * GET /api/account
     */
    @GetMapping(path = "/account", produces = MediaType.APPLICATION_JSON_VALUE)
    public LoginDtoResponse infoUser(
            @CookieValue(COOKIE_NAME) String cookie,
            HttpServletResponse response
    ) throws ServerException {
        return userService.infoUser(cookie);
    }

    /**
     * 3.21. Get server settings
     * GET /api/settings
     */
    @GetMapping(path = "/settings", produces = MediaType.APPLICATION_JSON_VALUE)
    public GetServerSettingsDtoResponse getServerSettings(@CookieValue(value = COOKIE_NAME, defaultValue = "") String cookie) {
        return userService.getServerSettings(cookie);
    }
}
