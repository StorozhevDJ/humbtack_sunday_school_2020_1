package net.thumbtack.school.hospital.database.dao;

import net.thumbtack.school.hospital.database.model.Doctor;

public interface DoctorDao {
    /**
     * Add new doctor into DB
     *
     * @param doctor - new doctor
     * @return inserted doctor
     */
    Doctor insert(Doctor doctor);

    /**
     * Get doctor info by token from JAVASESSIONID cookie
     *
     * @param token
     * @return doctor account
     */
    Doctor getByToken(String token);

    /**
     * Get doctor info by DB ID
     *
     * @param id
     * @return
     */
    Doctor getById(String id);
}
