package net.thumbtack.school.hospital.database.model;

import java.util.Objects;

public class Statistic {
    private ScheduleType type;
    private int count;

    public ScheduleType getType() {
        return type;
    }

    public void setType(ScheduleType type) {
        this.type = type;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Statistic)) return false;
        Statistic statistic = (Statistic) o;
        return getCount() == statistic.getCount() &&
                getType() == statistic.getType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getType(), getCount());
    }
}
