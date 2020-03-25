package net.thumbtack.school.hospital.controller;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.thumbtack.school.hospital.serverException.ServerException;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    @GetMapping
    public String getUsersInfo(@CookieValue("JAVASESSIONID") String cookie) throws ServerException {
        return "userService.getUsersInfo(cookie)";
    }
}
