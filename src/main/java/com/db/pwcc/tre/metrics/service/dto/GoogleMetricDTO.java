package com.db.pwcc.tre.metrics.service.dto;

import com.db.pwcc.tre.metrics.domain.GoogleMetric;
import com.db.pwcc.tre.metrics.web.rest.GoogleMetricCustomService;
import com.db.pwcc.tre.metrics.web.rest.TimeSeriesPoint;
import com.google.monitoring.v3.Point;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the {@link GoogleMetric} entity.
 */
public class GoogleMetricDTO implements Serializable {

    private String id;

    @NotNull
    private String name;

    private String description;


    private String metricGroupId;

    private String metricGroupName;
    private List<TimeSeriesPoint> data;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMetricGroupId() {
        return metricGroupId;
    }

    public void setMetricGroupId(String googleMetricGroupId) {
        this.metricGroupId = googleMetricGroupId;
    }

    public String getMetricGroupName() {
        return metricGroupName;
    }

    public void setMetricGroupName(String googleMetricGroupName) {
        this.metricGroupName = googleMetricGroupName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GoogleMetricDTO googleMetricDTO = (GoogleMetricDTO) o;
        if (googleMetricDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), googleMetricDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GoogleMetricDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", metricGroupId=" + getMetricGroupId() +
            ", metricGroupName='" + getMetricGroupName() + "'" +
            "}";
    }

    public void setData(List<TimeSeriesPoint> data) {
        this.data = data;
    }

    public List<TimeSeriesPoint> getData() {
        return data;
    }
}
