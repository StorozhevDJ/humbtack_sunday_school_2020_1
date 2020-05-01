package net.thumbtack.school.hospital.controller;

import net.thumbtack.school.hospital.dto.response.EmptyResponse;
import net.thumbtack.school.hospital.serverexception.ServerException;
import net.thumbtack.school.hospital.service.DebugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/debug")
public class DebugController {

    private DebugService debugService;

    @Autowired
    public DebugController(DebugService debugService) {
        this.debugService = debugService;
    }


    @PostMapping(path = "/clear")
    public EmptyResponse clearDatabase() throws ServerException {
        return debugService.clearDatabase();
    }

}
