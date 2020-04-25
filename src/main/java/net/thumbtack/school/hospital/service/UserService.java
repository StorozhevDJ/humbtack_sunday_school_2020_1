package net.thumbtack.school.hospital.service;

import net.thumbtack.school.hospital.database.dao.UserDao;
import net.thumbtack.school.hospital.database.model.Session;
import net.thumbtack.school.hospital.database.model.User;
import net.thumbtack.school.hospital.dto.request.LoginDtoRequest;
import net.thumbtack.school.hospital.dto.response.LoginDtoResponse;
import net.thumbtack.school.hospital.serverexception.ServerError;
import net.thumbtack.school.hospital.serverexception.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = DataAccessException.class)
public class UserService {

    private UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public LoginDtoResponse loginUser (LoginDtoRequest loginDtoRequest, String cookie) throws ServerException {
        LoginDtoResponse dto = new LoginDtoResponse();
        /*User user = userDao.getByLogin(loginDtoRequest.getLogin(), loginDtoRequest.getPassword());
        if (user == null) {
            throw new ServerException(ServerError.LOGIN_OR_PASSWORD_INVALID);
        }*/
        // REVU для того, чтолбы сделать login, создавать самому User незачем
        // надо его через dao попросить по логину и проверить пароль
        // или дажк запросить по логину и паролю
        User user = new User(loginDtoRequest.getLogin(), loginDtoRequest.getPassword(), new Session(cookie));
        userDao.logIn(user);
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        return dto;
    }

    public String logoutUser (String cookie) throws ServerException {
        userDao.logOut(new Session(cookie));
        // REVU верните DTO. Скажем, EmptyResponse
        return "{}";
    }

    public LoginDtoResponse infoUser (String cookie) throws ServerException {
        User user = userDao.getByToken(new Session(cookie));
        LoginDtoResponse dto = new LoginDtoResponse();

        return dto;
    }
}
