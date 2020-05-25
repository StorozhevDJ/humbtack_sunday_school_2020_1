package net.thumbtack.school.hospital.dto.request;

public class WeekDaysScheduleDtoRequest {

    private DayScheduleDtoRequest[] daySchedule;

    public WeekDaysScheduleDtoRequest() {
    }

    public WeekDaysScheduleDtoRequest(DayScheduleDtoRequest[] daySchedule) {
        this.daySchedule = daySchedule;
    }

    public DayScheduleDtoRequest[] getDaySchedule() {
        return daySchedule;
    }

    public void setDaySchedule(DayScheduleDtoRequest[] daySchedule) {
        this.daySchedule = daySchedule;
    }

}
