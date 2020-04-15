package com.db.pwcc.tre.metrics.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A GoogleProject.
 */
@Document(collection = "google_project")
public class GoogleProject implements Serializable {

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
    @JsonIgnoreProperties("projects")
    private GoogleMetric googleMetric;

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

    public GoogleProject name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGoogleId() {
        return googleId;
    }

    public GoogleProject googleId(String googleId) {
        this.googleId = googleId;
        return this;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public GoogleMetric getGoogleMetric() {
        return googleMetric;
    }

    public GoogleProject googleMetric(GoogleMetric googleMetric) {
        this.googleMetric = googleMetric;
        return this;
    }

    public void setGoogleMetric(GoogleMetric googleMetric) {
        this.googleMetric = googleMetric;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GoogleProject)) {
            return false;
        }
        return id != null && id.equals(((GoogleProject) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "GoogleProject{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", googleId='" + getGoogleId() + "'" +
            "}";
    }
}
