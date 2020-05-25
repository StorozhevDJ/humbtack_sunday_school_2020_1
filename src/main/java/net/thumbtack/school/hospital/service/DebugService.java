package net.thumbtack.school.hospital.service;


import net.thumbtack.school.hospital.database.dao.AdminDao;
import net.thumbtack.school.hospital.database.dao.CommonDao;
import net.thumbtack.school.hospital.database.model.Admin;
import net.thumbtack.school.hospital.database.model.Session;
import net.thumbtack.school.hospital.database.model.User;
import net.thumbtack.school.hospital.dto.response.EmptyDtoResponse;
import net.thumbtack.school.hospital.serverexception.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = DataAccessException.class)
public class DebugService {

    private final CommonDao commonDao;
    private final AdminDao adminDao;

    @Autowired
    public DebugService(CommonDao commonDao, AdminDao adminDao) {
        this.commonDao = commonDao;
        this.adminDao = adminDao;
    }

    /**
     * Clear server database
     *
     * @return EmptyDtoResponse
     * @throws ServerException
     */
    public EmptyDtoResponse clearDatabase() throws ServerException {
        commonDao.clear();
        User user = new User("FirstNameAdmin", "lastNameAdmin", null, "admin", "admin", new Session());
        adminDao.insert(new Admin(user, "Superadmin"));
        return new EmptyDtoResponse();
    }

}
