package net.thumbtack.school.hospital.dto.request;

/*{
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
}*/

import net.thumbtack.school.hospital.dto.validation.Date;

public class EditScheduleDtoRequest {
    @Date
    private String dateStart, dateEnd;

    private WeekScheduleDtoRequest weekSchedule;
    private WeekDaysScheduleDtoRequest weekDaysSchedules;

    private int duration;


    public EditScheduleDtoRequest() {
    }

    public EditScheduleDtoRequest(String dateStart, String dateEnd, WeekScheduleDtoRequest weekSchedule, int duration) {
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.weekSchedule = weekSchedule;
        this.duration = duration;
    }

    public EditScheduleDtoRequest(String dateStart, String dateEnd, WeekDaysScheduleDtoRequest weekDaysSchedules, int duration) {
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.weekDaysSchedules = weekDaysSchedules;
        this.duration = duration;
    }


    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public WeekScheduleDtoRequest getWeekSchedule() {
        return weekSchedule;
    }

    public void setWeekSchedule(WeekScheduleDtoRequest weekSchedule) {
        this.weekSchedule = weekSchedule;
    }

    public WeekDaysScheduleDtoRequest getWeekDaysSchedules() {
        return weekDaysSchedules;
    }

    public void setWeekDaysSchedules(WeekDaysScheduleDtoRequest weekDaysSchedules) {
        this.weekDaysSchedules = weekDaysSchedules;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

}
