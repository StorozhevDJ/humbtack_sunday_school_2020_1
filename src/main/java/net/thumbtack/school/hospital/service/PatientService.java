package net.thumbtack.school.hospital.service;

import net.thumbtack.school.hospital.database.dao.PatientDao;
import net.thumbtack.school.hospital.dto.request.AddTicketDtoRequest;
import net.thumbtack.school.hospital.dto.request.EditPatientDtoRequest;
import net.thumbtack.school.hospital.dto.request.RegisterPatientDtoRequest;
import net.thumbtack.school.hospital.dto.response.AddTicketDtoResponse;
import net.thumbtack.school.hospital.dto.response.EmptyResponse;
import net.thumbtack.school.hospital.dto.response.GetTicketDtoResponse;
import net.thumbtack.school.hospital.dto.response.LoginDtoResponse;
import net.thumbtack.school.hospital.serverexception.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = DataAccessException.class)
public class PatientService {

    private PatientDao patientDao;

    @Autowired
    public PatientService(PatientDao patientDao) {
        this.patientDao = patientDao;
    }

    public LoginDtoResponse registerPatient(String cookie, RegisterPatientDtoRequest registerPatientDtoRequest) throws ServerException {
        LoginDtoResponse loginPatientDtoResponse = null;

        return loginPatientDtoResponse;
    }

    public LoginDtoResponse infoPatient (String cookie, long patientId) throws ServerException {
        LoginDtoResponse loginPatientDtoResponse = null;

        return loginPatientDtoResponse;
    }

    public LoginDtoResponse editPatient (String cookie, EditPatientDtoRequest editPatientDtoRequest) throws ServerException {
        LoginDtoResponse loginPatientDtoResponse = null;

        return loginPatientDtoResponse;
    }

    public AddTicketDtoResponse addTicket (String cookie, AddTicketDtoRequest addTicketDtoRequest) throws ServerException {
        AddTicketDtoResponse addTicketDtoResponse = null;

        return addTicketDtoResponse;
    }

    public EmptyResponse cancelTicket (String cookie, String ticket) throws ServerException {

        return new EmptyResponse();
    }

    public EmptyResponse cancelCommission (String cookie, String ticket) throws ServerException {

        return new EmptyResponse();
    }

    public List<GetTicketDtoResponse> getTicketsList (String cookie) throws ServerException {
        List<GetTicketDtoResponse> getTicketDtoResponseList = null;

        return getTicketDtoResponseList;
    }
}
