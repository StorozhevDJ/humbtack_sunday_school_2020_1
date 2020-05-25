package net.thumbtack.school.hospital.service;

import net.thumbtack.school.hospital.ApplicationProperties;
import net.thumbtack.school.hospital.database.dao.AdminDao;
import net.thumbtack.school.hospital.database.dao.DoctorDao;
import net.thumbtack.school.hospital.database.dao.PatientDao;
import net.thumbtack.school.hospital.database.dao.UserDao;
import net.thumbtack.school.hospital.database.model.Session;
import net.thumbtack.school.hospital.database.model.User;
import net.thumbtack.school.hospital.dto.request.LoginDtoRequest;
import net.thumbtack.school.hospital.dto.response.EmptyDtoResponse;
import net.thumbtack.school.hospital.dto.response.GetServerSettingsDtoResponse;
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

    private final UserDao userDao;
    private final AdminDao adminDao;
    private final DoctorDao doctorDao;
    private final PatientDao patientDao;

    @Autowired
    ApplicationProperties prop;


    @Autowired
    public UserService(UserDao userDao, AdminDao adminDao, DoctorDao doctorDao, PatientDao patientDao) {
        this.userDao = userDao;
        this.adminDao = adminDao;
        this.doctorDao = doctorDao;
        this.patientDao = patientDao;
    }

    /**
     * User login
     *
     * @param loginDtoRequest
     * @param cookie
     * @return LoginDtoResponse
     * @throws ServerException
     */
    public LoginDtoResponse loginUser(LoginDtoRequest loginDtoRequest, String cookie) throws ServerException {
        User user = userDao.getByLogin(loginDtoRequest.getLogin(), loginDtoRequest.getPassword());
        if (user == null) {
            LOGGER.debug("Login user failed, user \"{}\" with login/password not found", loginDtoRequest.getLogin());
            throw new ServerException(ServerError.LOGIN_OR_PASSWORD_INVALID);
        }
        user.setSession(new Session(user.getId(), cookie));
        userDao.logIn(user);
        switch (user.getUserType()) {
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

    /**
     * User LogOut
     *
     * @param cookie
     * @return EmptyDtoResponse
     * @throws ServerException
     */
    public EmptyDtoResponse logoutUser(String cookie) throws ServerException {
        userDao.logOut(new Session(cookie));
        return new EmptyDtoResponse();
    }

    /**
     * Get current User account information
     *
     * @param cookie
     * @return LoginDtoResponse
     * @throws ServerException
     */
    public LoginDtoResponse infoUser(String cookie) throws ServerException {
        User user = userDao.getByToken(cookie);
        if (user == null) {
            throw new ServerException(ServerError.TOKEN_INVALID);
        }
        switch (user.getUserType()) {
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


    /**
     * Get server settings for current user
     *
     * @param cookie
     */
    public GetServerSettingsDtoResponse getServerSettings(String cookie) {
        GetServerSettingsDtoResponse dto = new GetServerSettingsDtoResponse();
        dto.setMaxNameLength(prop.getMaxNameLength());
        dto.setMinPasswordLength(prop.getMinPasswordLength());
        return dto;
    }

}
