package net.thumbtack.school.hospital.database.dao;

import java.time.LocalDate;
import java.util.List;

import net.thumbtack.school.hospital.database.model.Patient;
import net.thumbtack.school.hospital.database.model.Statistic;
import net.thumbtack.school.hospital.serverexception.ServerException;

public interface PatientDao {
    /**
     * Add new patient
     *
     * @param patient - new patient
     * @return inserted patient
     */
    Patient insert(Patient patient) throws ServerException;

    /**
     * Change patient account information
     *
     * @param patient
     * @throws ServerException
     */
    void update(Patient patient) throws ServerException;

    /**
     * Get patient by Patient ID
     *
     * @param id - patient DB id
     * @return patient
     */
    Patient getByPatientId(int id) throws ServerException;

    /**
     * Get patient by User ID
     *
     * @param id - patient DB id
     * @return patient
     */
    Patient getByUserId(int id) throws ServerException;

    /**
     * Get patient by token
     *
     * @return patient
     */
    Patient getByToken(String token) throws ServerException;

    /**
     * @return int
     */
    List<Statistic> getTicketCount(int id, LocalDate dateStart, LocalDate dateEnd) throws ServerException;

    /**
     * Get Patients count in DB
     *
     * @return int
     */
    int getCount() throws ServerException;
}
