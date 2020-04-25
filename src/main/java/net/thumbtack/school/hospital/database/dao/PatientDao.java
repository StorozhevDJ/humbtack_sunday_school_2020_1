package net.thumbtack.school.hospital.database.dao;

import java.util.List;

import net.thumbtack.school.hospital.database.model.Patient;
import net.thumbtack.school.hospital.database.model.Session;
import net.thumbtack.school.hospital.database.model.User;
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
    Patient getByToken(Session token) throws ServerException;

    /**
     * Get ALL patients by Doctor ID
     *
     * @param id - patient DB id
     * @return patient
     */
    List<Patient> getAllPatientByDoctorId(int id) throws ServerException;

    /**
     * Get Patients count in DB
     *
     * @return int
     */
    int getCount() throws ServerException;
}
