package net.thumbtack.school.hospital.database.dao;

import java.util.List;

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
	 * @return
	 */
	int createSchedule(List<Schedule> schedule);

	List<Schedule> getAllShedule();

}
