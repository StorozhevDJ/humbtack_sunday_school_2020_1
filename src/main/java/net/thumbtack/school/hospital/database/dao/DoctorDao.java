package net.thumbtack.school.hospital.database.dao;

import java.util.List;

import net.thumbtack.school.hospital.database.model.Doctor;
import net.thumbtack.school.hospital.database.model.Session;
import net.thumbtack.school.hospital.database.model.User;
import net.thumbtack.school.hospital.serverexception.ServerException;

public interface DoctorDao {
	/**
	 * Add new doctor into DB
	 *
	 * @param doctor - new doctor
	 * @return inserted doctor
	 */
	Doctor insert(Doctor doctor) throws ServerException;

	/**
	 * Get doctor info by token from JAVASESSIONID cookie
	 *
	 * @param token
	 * @return doctor account
	 */
	Doctor getByToken(Session token) throws ServerException;

	/**
	 * Get Doctor info by User ID
	 *
	 * @param id
	 * @return
	 */
	Doctor getByUserId(int id) throws ServerException;

	/**
	 * Get Doctor info by Doctor ID
	 *
	 * @param id
	 * @return
	 */
	Doctor getByDoctorId(int id) throws ServerException;

	/**
	 * Get Doctor list by Speciality
	 *
	 * @param speciality
	 * @return
	 */
	List<Doctor> getBySpeciality(String speciality) throws ServerException;

	/**
	 * @return int
	 */
	int getCount() throws ServerException;

	/**
	 * Delete Doctor by doctor ID
	 *
	 * @param id
	 * @throws ServerException
	 */
	void deleteById(int id) throws ServerException;

}
