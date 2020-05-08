package net.thumbtack.school.hospital.dto.request;

import javax.validation.constraints.NotNull;

public class WeekScheduleDtoRequest {
    @NotNull
    private String timeStart, timeEnd;
    @NotNull
    private String[] weekDays;

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

    public WeekScheduleDtoRequest() {
    }

    public WeekScheduleDtoRequest(String timeStart, String timeEnd, String[] weekDays) {
        setTimeStart(timeStart);
        setTimeEnd(timeEnd);
        setWeekDays(weekDays);
    }
}
