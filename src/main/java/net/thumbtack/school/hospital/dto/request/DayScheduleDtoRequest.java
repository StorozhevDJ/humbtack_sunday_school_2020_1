package net.thumbtack.school.hospital.dto.request;

import javax.validation.constraints.NotNull;

public class DayScheduleDtoRequest {
    @NotNull
    private String weekDay;
    @NotNull
    private String timeStart, timeEnd;

    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
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

    public DayScheduleDtoRequest() {
    }

    public DayScheduleDtoRequest(String weekDay, String timeStart, String timeEnd) {
        setWeekDay(weekDay);
        setTimeStart(timeStart);
        setTimeEnd(timeEnd);
    }
}
