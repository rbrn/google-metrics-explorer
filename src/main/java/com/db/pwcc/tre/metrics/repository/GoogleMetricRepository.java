package com.db.pwcc.tre.metrics.repository;

import com.db.pwcc.tre.metrics.domain.GoogleMetric;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the GoogleMetric entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GoogleMetricRepository extends MongoRepository<GoogleMetric, String> {
}
