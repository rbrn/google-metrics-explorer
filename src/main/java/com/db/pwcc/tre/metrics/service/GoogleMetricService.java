package com.db.pwcc.tre.metrics.service;

import com.db.pwcc.tre.metrics.service.dto.GoogleMetricDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.db.pwcc.tre.metrics.domain.GoogleMetric}.
 */
public interface GoogleMetricService {

    /**
     * Save a googleMetric.
     *
     * @param googleMetricDTO the entity to save.
     * @return the persisted entity.
     */
    GoogleMetricDTO save(GoogleMetricDTO googleMetricDTO);

    /**
     * Get all the googleMetrics.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<GoogleMetricDTO> findAll(Pageable pageable);

    /**
     * Get the "id" googleMetric.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GoogleMetricDTO> findOne(String id);

    /**
     * Delete the "id" googleMetric.
     *
     * @param id the id of the entity.
     */
    void delete(String id);

    /**
     * Search for the googleMetric corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<GoogleMetricDTO> search(String query, Pageable pageable);
}
