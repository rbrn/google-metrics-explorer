package com.db.pwcc.tre.metrics.service;

import com.db.pwcc.tre.metrics.service.dto.GoogleMetricGroupDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.db.pwcc.tre.metrics.domain.GoogleMetricGroup}.
 */
public interface GoogleMetricGroupService {

    /**
     * Save a googleMetricGroup.
     *
     * @param googleMetricGroupDTO the entity to save.
     * @return the persisted entity.
     */
    GoogleMetricGroupDTO save(GoogleMetricGroupDTO googleMetricGroupDTO);

    /**
     * Get all the googleMetricGroups.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<GoogleMetricGroupDTO> findAll(Pageable pageable);

    /**
     * Get the "id" googleMetricGroup.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GoogleMetricGroupDTO> findOne(String id);

    /**
     * Delete the "id" googleMetricGroup.
     *
     * @param id the id of the entity.
     */
    void delete(String id);

    /**
     * Search for the googleMetricGroup corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<GoogleMetricGroupDTO> search(String query, Pageable pageable);
}
