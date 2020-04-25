package net.thumbtack.school.hospital.service;

import net.thumbtack.school.hospital.database.dao.DoctorDao;
import net.thumbtack.school.hospital.dto.request.AddComissionDtoRequest;
import net.thumbtack.school.hospital.dto.response.AddComissionDtoResponse;
import net.thumbtack.school.hospital.dto.response.LoginDtoResponse;
import net.thumbtack.school.hospital.serverexception.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = DataAccessException.class)
public class DoctorService {

    private DoctorDao doctorDao;

    @Autowired
    public DoctorService(DoctorDao doctorDao) {
        this.doctorDao = doctorDao;
    }

    public LoginDtoResponse doctorInfo (String cookie, long docId, String schedule, String startDate, String endDate) throws ServerException {
        LoginDtoResponse dto = null;

        return dto;
    }

    public List<LoginDtoResponse> doctorsInfo (String cookie, String speciality, String schedule, String startDate, String endDate) throws ServerException {
        List<LoginDtoResponse> dto = null;

        return dto;
    }

    public AddComissionDtoResponse addCommission (String cookie, AddComissionDtoRequest addComissionDtoRequest) throws ServerException {
        AddComissionDtoResponse dto = null;

        return dto;
    }

}
