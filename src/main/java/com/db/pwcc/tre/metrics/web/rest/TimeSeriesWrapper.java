package com.db.pwcc.tre.metrics.web.rest;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public class TimeSeriesWrapper {
    public Map<String, String> getLabelsMap() {
        return labelsMap;
    }

    public List<TimeSeriesPoint> getTimeSeriesPoints() {
        return timeSeriesPoints;
    }

    public void setTimeSeriesPoints(List<TimeSeriesPoint> timeSeriesPoints) {
        this.timeSeriesPoints = timeSeriesPoints;
    }

    private final Map<String, String> labelsMap;

    public TimeSeriesWrapper(List<TimeSeriesPoint> timeSeriesPoints, Map<String, String> labelsMap) {
        this.timeSeriesPoints = timeSeriesPoints;
        this.labelsMap = labelsMap;
    }

    public List<TimeSeriesPoint> timeSeriesPoints;

}
