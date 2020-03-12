package net.thumbtack.school.hospital.database.dao;

import net.thumbtack.school.hospital.database.model.Patient;

public interface PatientDao {
    /**
     * Add new patient
     *
     * @param patient - new patient
     * @return inserted patient
     */
    Patient insert(Patient patient);

    /**
     * Get patient by ID
     *
     * @param id - patient DB id
     * @return patient
     */
    Patient getById(int id);
}
