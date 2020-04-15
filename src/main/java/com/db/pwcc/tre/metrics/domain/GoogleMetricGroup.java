package com.db.pwcc.tre.metrics.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A GoogleMetricGroup.
 */
@Document(collection = "google_metric_group")
public class GoogleMetricGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("name")
    private String name;

    @NotNull
    @Field("google_id")
    private String googleId;

    @DBRef
    @Field("googleMetric")
    private Set<GoogleMetric> googleMetrics = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public GoogleMetricGroup name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGoogleId() {
        return googleId;
    }

    public GoogleMetricGroup googleId(String googleId) {
        this.googleId = googleId;
        return this;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public Set<GoogleMetric> getGoogleMetrics() {
        return googleMetrics;
    }

    public GoogleMetricGroup googleMetrics(Set<GoogleMetric> googleMetrics) {
        this.googleMetrics = googleMetrics;
        return this;
    }

    public GoogleMetricGroup addGoogleMetric(GoogleMetric googleMetric) {
        this.googleMetrics.add(googleMetric);
        googleMetric.setMetricGroup(this);
        return this;
    }

    public GoogleMetricGroup removeGoogleMetric(GoogleMetric googleMetric) {
        this.googleMetrics.remove(googleMetric);
        googleMetric.setMetricGroup(null);
        return this;
    }

    public void setGoogleMetrics(Set<GoogleMetric> googleMetrics) {
        this.googleMetrics = googleMetrics;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GoogleMetricGroup)) {
            return false;
        }
        return id != null && id.equals(((GoogleMetricGroup) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "GoogleMetricGroup{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", googleId='" + getGoogleId() + "'" +
            "}";
    }
}
