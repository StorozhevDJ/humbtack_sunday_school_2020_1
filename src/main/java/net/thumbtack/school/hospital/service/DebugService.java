package net.thumbtack.school.hospital.service;


import net.thumbtack.school.hospital.database.dao.CommonDao;
import net.thumbtack.school.hospital.dto.response.EmptyDtoResponse;
import net.thumbtack.school.hospital.serverexception.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = DataAccessException.class)
public class DebugService {

    private CommonDao commonDao;

    @Autowired
    public DebugService(CommonDao commonDao) {
        this.commonDao = commonDao;
    }

    /**
     * Clear server database
     *
     * @return EmptyDtoResponse
     * @throws ServerException
     */
    public EmptyDtoResponse clearDatabase() throws ServerException {
        commonDao.clear();
        return new EmptyDtoResponse();
    }

}
