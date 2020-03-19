package net.thumbtack.school.hospital.database.dao;

import java.util.List;

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
	 * Get Doctor info by User ID
	 *
	 * @param id
	 * @return
	 */
	Doctor getByUserId(int id);

	/**
	 * Get Doctor info by Doctor ID
	 *
	 * @param id
	 * @return
	 */
	Doctor getByDoctorId(int id);

	/**
	 * Get Doctor list by Speciality
	 *
	 * @param speciality
	 * @return
	 */
	List<Doctor> getBySpeciality(String speciality);

	/**
	 * @return int
	 */
	int getCount();

}
