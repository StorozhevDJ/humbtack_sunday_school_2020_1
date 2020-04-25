package net.thumbtack.school.hospital.database.dao;

import java.util.List;

import net.thumbtack.school.hospital.database.model.DaySchedule;
import net.thumbtack.school.hospital.database.model.TicketSchedule;
import net.thumbtack.school.hospital.serverexception.ServerException;


public interface DayScheduleDao {

	/**
	 * Insert new Schedule in to DB
	 *
	 * @param daySchedule list
	 * @return count of schedule record
	 */
	int createSchedule(List<DaySchedule> daySchedule) throws ServerException;

	/**
	 * Get Doctor schedule
	 *
	 * @param id Doctor
	 * @return Schedule list
	 */
	List<DaySchedule> getByDoctorId(int id) throws ServerException;

	/**
	 * Get schedule for all doctors with speciality
	 *
	 * @param speciality for find doctor
	 * @return Schedule list
	 */
	List<DaySchedule> getByDoctorSpeciality(String speciality) throws ServerException;

	/**
	 * Get All schedule
	 *
	 * @return
	 */
	List<DaySchedule> getAllSchedule() throws ServerException;

	/**
	 * Add ticket to schedule
	 *
	 * @param schedule
	 * @return
	 */
	boolean addTicket(TicketSchedule schedule) throws ServerException;

}
