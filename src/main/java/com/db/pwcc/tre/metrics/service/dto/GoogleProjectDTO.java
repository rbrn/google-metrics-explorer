package com.db.pwcc.tre.metrics.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.db.pwcc.tre.metrics.domain.GoogleProject} entity.
 */
public class GoogleProjectDTO implements Serializable {
    
    private String id;

    @NotNull
    private String name;

    @NotNull
    private String googleId;


    private String googleMetricId;
    
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

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public String getGoogleMetricId() {
        return googleMetricId;
    }

    public void setGoogleMetricId(String googleMetricId) {
        this.googleMetricId = googleMetricId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GoogleProjectDTO googleProjectDTO = (GoogleProjectDTO) o;
        if (googleProjectDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), googleProjectDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GoogleProjectDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", googleId='" + getGoogleId() + "'" +
            ", googleMetricId=" + getGoogleMetricId() +
            "}";
    }
}
