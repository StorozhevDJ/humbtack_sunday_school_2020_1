package net.thumbtack.school.hospital.database.dao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import net.thumbtack.school.hospital.database.model.Commission;
import net.thumbtack.school.hospital.database.model.DaySchedule;
import net.thumbtack.school.hospital.database.model.ScheduleType;
import net.thumbtack.school.hospital.database.model.TicketSchedule;
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
	 * Get All schedule
	 *
	 * @return
	 */
	List<DaySchedule> getAllSchedule() throws ServerException;

	/**
	 * Get day schedule by schedule id
	 *
	 * @param scheduleId
	 * @return
	 */
	List<TicketSchedule> getTicketScheduleById(int scheduleId) throws ServerException;


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


	List<DaySchedule> checkFreeScheduleToCommission(List<Integer> doctorIds, LocalDate date, LocalTime timeStart, LocalTime timeEnd) throws ServerException;

	/**
	 * Cancel an existing doctor ticket
	 *
	 * @param ticket
	 * @return
	 * @throws ServerException
	 */
	boolean cancelTicket(String ticket) throws ServerException;

	/**
	 * Delete doctor schedule from date
	 *
	 * @param doctorId
	 * @param date
	 */
	void deleteSchedule(int doctorId, LocalDate date) throws ServerException;

	/**
	 * Add commission ticket
	 *
	 * @param schedule
	 * @return
	 * @throws ServerException
	 */
	public boolean addCommissionTicket(Commission schedule, TicketSchedule ticketSchedule) throws ServerException;
}
