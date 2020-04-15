package com.db.pwcc.tre.metrics.repository;

import com.db.pwcc.tre.metrics.domain.GoogleMetricGroup;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the GoogleMetricGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GoogleMetricGroupRepository extends MongoRepository<GoogleMetricGroup, String> {
}
