package com.db.pwcc.tre.metrics.service;

import com.db.pwcc.tre.metrics.service.dto.GoogleProjectDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.db.pwcc.tre.metrics.domain.GoogleProject}.
 */
public interface GoogleProjectService {

    /**
     * Save a googleProject.
     *
     * @param googleProjectDTO the entity to save.
     * @return the persisted entity.
     */
    GoogleProjectDTO save(GoogleProjectDTO googleProjectDTO);

    /**
     * Get all the googleProjects.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<GoogleProjectDTO> findAll(Pageable pageable);

    /**
     * Get the "id" googleProject.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GoogleProjectDTO> findOne(String id);

    /**
     * Delete the "id" googleProject.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
