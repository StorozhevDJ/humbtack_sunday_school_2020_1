package net.thumbtack.school.hospital.service;

import net.thumbtack.school.hospital.ApplicationProperties;
import net.thumbtack.school.hospital.database.dao.AdminDao;
import net.thumbtack.school.hospital.dto.request.*;
import net.thumbtack.school.hospital.dto.response.DeleteDoctorDtoResponse;
import net.thumbtack.school.hospital.dto.response.EditAdminDtoResponse;
import net.thumbtack.school.hospital.dto.response.GetServerSettingsDtoResponse;
import net.thumbtack.school.hospital.dto.response.LoginDtoResponse;
import net.thumbtack.school.hospital.serverexception.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = DataAccessException.class)
public class AdminService {

    private AdminDao adminDao;

    @Autowired
    public AdminService(AdminDao adminDao) {
        this.adminDao = adminDao;
    }

    public LoginDtoResponse registerAdmin (RegisterAdminDtoRequest registerAdminDtoRequest, String cookie) throws ServerException {
        LoginDtoResponse loginAdminDtoResponse = null;

        return loginAdminDtoResponse;
    }

    public EditAdminDtoResponse editAdmin (EditAdminDtoRequest editAdminDtoRequest, String cookie) throws ServerException {
        EditAdminDtoResponse editAdminDtoResponse = null;

        return editAdminDtoResponse;
    }

    public LoginDtoResponse registerDoctor (RegisterDoctorDtoRequest registerDtoRequest, String cookie) throws ServerException {
        LoginDtoResponse dto = null;

        return dto;
    }

    public LoginDtoResponse editScheduleDoctor(EditScheduleDtoRequest editDto, long doctorId, String cookie) throws ServerException {
        LoginDtoResponse dto = null;

        return dto;
    }

    public DeleteDoctorDtoResponse deleteDoctor (DeleteDoctorDtoRequest deleteDoctorDtoRequest, long doctorId, String cookie) throws ServerException {
        DeleteDoctorDtoResponse dto = null;

        return dto;
    }

    public GetServerSettingsDtoResponse getServerSettings (String cookie) throws ServerException {
        GetServerSettingsDtoResponse dto = new GetServerSettingsDtoResponse();
        ApplicationProperties prop = new ApplicationProperties();
        dto.setMaxNameLength(prop.getMaxNameLength());
        dto.setCookie(cookie);
        return dto;
    }
}
