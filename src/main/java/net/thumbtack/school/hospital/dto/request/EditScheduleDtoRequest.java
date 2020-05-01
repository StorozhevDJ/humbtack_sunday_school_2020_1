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

    public class WeekSchedule {
        private String timeStart, timeEnd;
        private String[] weekDays;

        public WeekSchedule(String timeStart, String timeEnd, String[] weekDays) {
            super();
            this.timeStart = timeStart;
            this.timeEnd = timeEnd;
            this.weekDays = weekDays;
        }

        public String getTimeStart() {
            return timeStart;
        }

        public void setTimeStart(String timeStart) {
            this.timeStart = timeStart;
        }

        public String getTimeEnd() {
            return timeEnd;
        }

        public void setTimeEnd(String timeEnd) {
            this.timeEnd = timeEnd;
        }

        public String[] getWeekDays() {
            return weekDays;
        }

        public void setWeekDays(String[] weekDays) {
            this.weekDays = weekDays;
        }
    }

    private WeekSchedule weekSchedule;

    public class WeekDaysSchedule {
        private String daySchedule;
        private String timeStart;
        private String timeEnd;

        public WeekDaysSchedule(String daySchedule, String timeStart, String timeEnd) {
            super();
            this.daySchedule = daySchedule;
            this.timeStart = timeStart;
            this.timeEnd = timeEnd;
        }

        public String getDaySchedule() {
            return daySchedule;
        }

        public void setDaySchedule(String daySchedule) {
            this.daySchedule = daySchedule;
        }

        public String getTimeStart() {
            return timeStart;
        }

        public void setTimeStart(String timeStart) {
            this.timeStart = timeStart;
        }

        public String getTimeEnd() {
            return timeEnd;
        }

        public void setTimeEnd(String timeEnd) {
            this.timeEnd = timeEnd;
        }
    }

    private WeekDaysSchedule[] weekDaysSchedule;
    private int duration;

    public EditScheduleDtoRequest(String dateStart, String dateEnd, WeekSchedule weekSchedule,
                                  WeekDaysSchedule[] weekDaysSchedule, int duration) {
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.weekSchedule = weekSchedule;
        this.weekDaysSchedule = weekDaysSchedule;
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

    public WeekSchedule getWeekSchedule() {
        return weekSchedule;
    }

    public void setWeekSchedule(WeekSchedule weekSchedule) {
        this.weekSchedule = weekSchedule;
    }

    public WeekDaysSchedule[] getWeekDaysSchedule() {
        return weekDaysSchedule;
    }

    public void setWeekDaysSchedule(WeekDaysSchedule[] weekDaysSchedule) {
        this.weekDaysSchedule = weekDaysSchedule;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

}
