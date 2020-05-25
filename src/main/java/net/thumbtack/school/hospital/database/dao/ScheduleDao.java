package net.thumbtack.school.hospital.database.dao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import net.thumbtack.school.hospital.database.model.*;
import net.thumbtack.school.hospital.serverexception.ServerException;


public interface ScheduleDao {

	/**
	 * Insert new Schedule in to DB
	 *
	 * @param daySchedule list
	 * @return count of schedule record
	 */
	int createSchedule(List<DaySchedule> daySchedule) throws ServerException;

	/**
	 * Add ticket to schedule
	 *
	 * @param schedule
	 * @return
	 */
	boolean addTicket(TicketSchedule schedule) throws ServerException;

	/**
	 * Add commission ticket
	 *
	 * @param schedule
	 * @return
	 * @throws ServerException
	 */
	boolean addCommissionTicket(Commission schedule, List<Integer> ticketScheduleList) throws ServerException;

	/**
	 * Get Doctor schedule
	 *
	 * @param id Doctor
	 * @return Schedule list
	 */
	List<DaySchedule> getByDoctorId(int id, LocalDate dateStart, LocalDate dateEnd) throws ServerException;

	/**
	 * Get schedule for all doctors with speciality
	 *
	 * @param speciality for find doctor
	 * @return Schedule list
	 */
	List<DaySchedule> getByDoctorSpeciality(String speciality, LocalDate dateStart, LocalDate dateEnd) throws ServerException;

	/**
	 * Get day schedule by parameters
	 *
	 * @param scheduleId
	 * @return
	 */
	List<TicketSchedule> getTicketSchedule(int scheduleId, ScheduleType type, LocalTime timeStart, LocalTime timeEnd) throws ServerException;

	/**
	 * Get patient tickets list by patient ID
	 *
	 * @param patientId
	 * @return
	 * @throws ServerException
	 */
	List<DaySchedule> getTicketsListByPatientId(int patientId) throws ServerException;

	/**
	 * Get Schedule by parameters
	 *
	 * @param speciality
	 * @param dateStart
	 * @param dateEnd
	 * @return List<DaySchedule>
	 * @throws ServerException
	 */
	List<DaySchedule> getDaySchedule(int doctorId, String speciality, LocalDate dateStart, LocalDate dateEnd) throws ServerException;

	/**
	 *
	 * @param doctorIds
	 * @param date
	 * @param timeStart
	 * @param timeEnd
	 * @return
	 * @throws ServerException
	 */
	List<Integer> getCountFreeSchedule(List<Integer> doctorIds, LocalDate date, LocalTime timeStart, LocalTime timeEnd) throws ServerException;


	List<Commission> getCommission(int patientId) throws ServerException;

	/**
	 * Cancel an existing doctor ticket
	 *
	 * @param ticket
	 * @return boolean
	 * @throws ServerException
	 */
	void cancelTicket(String ticket, int patientId) throws ServerException;

	/**
	 * Cancel an existing commission
	 * @param ticket
	 * @throws ServerException
	 */
	void cancelCommission(String ticket, int patientId) throws ServerException;

	/**
	 * Update and add new doctor schedule
	 * @param doctor
	 * @throws ServerException
	 */
	void updateSchedule(Doctor doctor) throws ServerException;

	/**
	 * Delete doctor schedule from date
	 *
	 * @param doctorId
	 * @param date
	 */
	void deleteSchedule(int doctorId, LocalDate date) throws ServerException;

}
