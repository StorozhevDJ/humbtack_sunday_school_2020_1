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

public class EditScheduleDtoRequest {

    private String dateStart;
    private String dateEnd;

    public WeekScheduleDtoRequest weekSchedule;
    public WeekDaysScheduleDtoRequest[] weekDaysSchedule;

    private int duration;


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

    public WeekDaysScheduleDtoRequest[] getWeekDaysSchedule() {
        return weekDaysSchedule;
    }

    public void setWeekDaysSchedule(WeekDaysScheduleDtoRequest[] weekDaysSchedule) {
        this.weekDaysSchedule = weekDaysSchedule;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

}
