package com.db.pwcc.tre.metrics.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.Objects;

/**
 * A GoogleMetric.
 */
@Document(collection = "google_metric")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "googlemetric")
public class GoogleMetric implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("name")
    private String name;

    @Field("description")
    private String description;

    @DBRef
    @Field("metricGroup")
    @JsonIgnoreProperties("googleMetrics")
    private GoogleMetricGroup metricGroup;

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

    public GoogleMetric name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public GoogleMetric description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public GoogleMetricGroup getMetricGroup() {
        return metricGroup;
    }

    public GoogleMetric metricGroup(GoogleMetricGroup googleMetricGroup) {
        this.metricGroup = googleMetricGroup;
        return this;
    }

    public void setMetricGroup(GoogleMetricGroup googleMetricGroup) {
        this.metricGroup = googleMetricGroup;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GoogleMetric)) {
            return false;
        }
        return id != null && id.equals(((GoogleMetric) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "GoogleMetric{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
