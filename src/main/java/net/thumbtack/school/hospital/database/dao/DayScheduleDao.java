package net.thumbtack.school.hospital.database.dao;

import java.util.List;

import net.thumbtack.school.hospital.database.model.DaySchedule;
import net.thumbtack.school.hospital.database.model.TicketSchedule;

public interface DayScheduleDao {

	/*
	{
    "dateStart": "дата, начиная с которой вносятся изменения в расписание",
    "dateEnd": "дата, до которой вносятся изменения в расписание",
    "weekSchedule":
       {
            “timeStart” :"время начала приема",
            “timeEnd” : "время окончания приема",
            “weekDays” :
            [
               день недели 1.. день недели N
            ]
       },
   "weekDaysSchedule":
       [
         daySchedule :{
            “weekDay” : “день недели”
            “timeStart” :"время начала приема",
            “timeEnd” : "время окончания приема",
         },
       ...
      ],
  "duration": время на прием одного пациента в минутах
}
	 */

	/*{
     "ticket": “номер талона”,
     "doctorId": идентификатор врача,
     "firstName": "имя врача",
     "lastName": "фамилия врача",
     "patronymic": "отчество врача",
     "speciality": "специальность врача",
     "room": ”номер кабинета”,
     "date": "дата",
     "time": "время",
}*/

	/**
	 * Insert new Schedule in to DB
	 *
	 * @param daySchedule list
	 * @return count of schedule record
	 */
	int createSchedule(List<DaySchedule> daySchedule);

	/**
	 * Get Doctor schedule
	 *
	 * @param id Doctor
	 * @return Schedule list
	 */
	List<DaySchedule> getByDoctorId(int id);

	/**
	 * Get schedule for all doctors with speciality
	 *
	 * @param speciality for find doctor
	 * @return Schedule list
	 */
	List<DaySchedule> getByDoctorSpeciality(String speciality);

	/**
	 * Get All schedule
	 *
	 * @return
	 */
	List<DaySchedule> getAllSchedule();

	/**
	 * Add ticket to schedule
	 *
	 * @param schedule
	 * @return
	 */
	boolean addTicket(TicketSchedule schedule);

}
