package com.db.pwcc.tre.metrics.repository;

import com.db.pwcc.tre.metrics.domain.GoogleProject;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the GoogleProject entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GoogleProjectRepository extends MongoRepository<GoogleProject, String> {
}
