package net.thumbtack.school.hospital.controller;

import net.thumbtack.school.hospital.dto.response.EmptyDtoResponse;
import net.thumbtack.school.hospital.serverexception.ServerException;
import net.thumbtack.school.hospital.service.DebugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/debug")
public class DebugController {

    private final DebugService debugService;

    @Autowired
    public DebugController(DebugService debugService) {
        this.debugService = debugService;
    }

    /**
     * 4.1. Clear server
     * POST /api/debug/clear
     */
    @PostMapping(path = "/clear")
    public EmptyDtoResponse clearDatabase() throws ServerException {
        return debugService.clearDatabase();
    }

}
