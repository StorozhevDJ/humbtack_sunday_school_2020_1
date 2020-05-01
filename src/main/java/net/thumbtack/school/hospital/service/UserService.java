package net.thumbtack.school.hospital.service;

import net.thumbtack.school.hospital.database.dao.AdminDao;
import net.thumbtack.school.hospital.database.dao.DoctorDao;
import net.thumbtack.school.hospital.database.dao.PatientDao;
import net.thumbtack.school.hospital.database.dao.UserDao;
import net.thumbtack.school.hospital.database.daoimpl.AdminDaoImpl;
import net.thumbtack.school.hospital.database.model.*;
import net.thumbtack.school.hospital.dto.request.LoginDtoRequest;
import net.thumbtack.school.hospital.dto.response.EmptyResponse;
import net.thumbtack.school.hospital.dto.response.LoginDtoResponse;
import net.thumbtack.school.hospital.mapper.AdminMapper;
import net.thumbtack.school.hospital.mapper.DoctorMapper;
import net.thumbtack.school.hospital.mapper.PatientMapper;
import net.thumbtack.school.hospital.serverexception.ServerError;
import net.thumbtack.school.hospital.serverexception.ServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = DataAccessException.class)
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private UserDao userDao;
    private AdminDao adminDao;
    private DoctorDao doctorDao;
    private PatientDao patientDao;

    @Autowired
    public UserService(UserDao userDao, AdminDao adminDao, DoctorDao doctorDao, PatientDao patientDao) {
        this.userDao = userDao;
        this.adminDao = adminDao;
        this.doctorDao = doctorDao;
        this.patientDao = patientDao;
    }

    public LoginDtoResponse loginUser(LoginDtoRequest loginDtoRequest, String cookie) throws ServerException {
        User user = userDao.getByLogin(loginDtoRequest.getLogin(), loginDtoRequest.getPassword());
        if (user == null) {
            LOGGER.debug("Login user failed, user \"{}\" with login/password not found", user.getLogin());
            throw new ServerException(ServerError.LOGIN_OR_PASSWORD_INVALID);
        }
        user.setSession(new Session(user.getId(), cookie));
        userDao.logIn(user);
        switch (user.getType()) {
            case DOCTOR: {
                return DoctorMapper.convertToDto(doctorDao.getByUserId(user.getId()));
            }
            case PATIENT: {
                return PatientMapper.convertToDto(patientDao.getByUserId(user.getId()));
            }
            case ADMINISTRATOR: {
                return AdminMapper.convertToDto(adminDao.getByUserId(user.getId()));
            }
        }
        throw new ServerException(ServerError.OTHER_ERROR);
    }

    public String logoutUser(String cookie) throws ServerException {
        userDao.logOut(new Session(cookie));
        return new EmptyResponse().getEmptyResponse();
    }

    public LoginDtoResponse infoUser(String cookie) throws ServerException {
        User user = userDao.getByToken(new Session(cookie));
        if (user == null) {
            throw new ServerException(ServerError.TOKEN_INVALID);
        }
        switch (user.getType()) {
            case DOCTOR: {
                return DoctorMapper.convertToDto(doctorDao.getByUserId(user.getId()));
            }
            case PATIENT: {
                return PatientMapper.convertToDto(patientDao.getByUserId(user.getId()));
            }
            case ADMINISTRATOR: {
                return AdminMapper.convertToDto(adminDao.getByUserId(user.getId()));
            }
        }
        throw new ServerException(ServerError.OTHER_ERROR);
    }

}
