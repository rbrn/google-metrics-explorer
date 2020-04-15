package com.db.pwcc.tre.metrics.web.rest;

import java.time.Instant;

public class TimeSeriesPoint {
    public TimeSeriesPoint(Instant startTime, Double value, int startTimeHoursMinutes) {
        this.startTime = startTime;
        this.value = value;
        this.startTimeHoursMinutes = startTimeHoursMinutes;
    }

    public Instant startTime;
    public int startTimeHoursMinutes;
    public Double value;
}
