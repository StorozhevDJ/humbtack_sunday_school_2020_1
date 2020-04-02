package net.thumbtack.school.hospital.database.dao;

import java.util.List;

import net.thumbtack.school.hospital.database.model.Commission;
import net.thumbtack.school.hospital.database.model.Schedule;

public interface ScheduleDao {

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
	 * @param schedule list
	 * @return count of schedule record
	 */
	int createSchedule(List<Schedule> schedule);

	/**
	 * Add commission in to schedule
	 *
	 * @param commissions
	 * @return
	 */
	int addCommission(List<Commission> commissions);

	/**
	 * Get Doctor schedule
	 *
	 * @param id Doctor
	 * @return Schedule list
	 */
	List<Schedule> getByDoctorId(int id);

	/**
	 * Get schedule for all doctors with speciality
	 *
	 * @param speciality for find doctor
	 * @return Schedule list
	 */
	List<Schedule> getByDoctorSpeciality(String speciality);

	/**
	 * Get All schedule
	 *
	 * @return
	 */
	List<Schedule> getAllShedule();

	/**
	 * Add ticket to schedule
	 *
	 * @param schedule
	 * @return
	 */
	boolean addTicket(Schedule schedule);

}
