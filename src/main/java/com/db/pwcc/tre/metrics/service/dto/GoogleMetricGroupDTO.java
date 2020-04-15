package com.db.pwcc.tre.metrics.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.db.pwcc.tre.metrics.domain.GoogleMetricGroup} entity.
 */
public class GoogleMetricGroupDTO implements Serializable {
    
    private String id;

    @NotNull
    private String name;

    @NotNull
    private String googleId;

    
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GoogleMetricGroupDTO googleMetricGroupDTO = (GoogleMetricGroupDTO) o;
        if (googleMetricGroupDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), googleMetricGroupDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GoogleMetricGroupDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", googleId='" + getGoogleId() + "'" +
            "}";
    }
}
