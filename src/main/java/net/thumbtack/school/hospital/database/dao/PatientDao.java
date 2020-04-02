package net.thumbtack.school.hospital.database.dao;

import java.util.List;

import net.thumbtack.school.hospital.database.model.Patient;
import net.thumbtack.school.hospital.database.model.User;

public interface PatientDao {
    /**
     * Add new patient
     *
     * @param patient - new patient
     * @return inserted patient
     */
    Patient insert(Patient patient);

    /**
     * Get patient by Patient ID
     *
     * @param id - patient DB id
     * @return patient
     */
    Patient getByPatientId(int id);

    /**
     * Get patient by User ID
     *
     * @param id - patient DB id
     * @return patient
     */
    Patient getByUserId(int id);

    /**
     * Get patient by token
     *
     * @return patient
     */
    Patient getByToken(User.Session token);

    /**
     * Get ALL patients by Doctor ID
     *
     * @param id - patient DB id
     * @return patient
     */
    List<Patient> getAllPatientByDoctorId(int id);

    /**
     * Get Patients count in DB
     *
     * @return int
     */
    int getCount();
}
