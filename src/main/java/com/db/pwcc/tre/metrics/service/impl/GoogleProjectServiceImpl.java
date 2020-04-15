package com.db.pwcc.tre.metrics.service.impl;

import com.db.pwcc.tre.metrics.service.GoogleProjectService;
import com.db.pwcc.tre.metrics.domain.GoogleProject;
import com.db.pwcc.tre.metrics.repository.GoogleProjectRepository;
import com.db.pwcc.tre.metrics.service.dto.GoogleProjectDTO;
import com.db.pwcc.tre.metrics.service.mapper.GoogleProjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Implementation for managing {@link GoogleProject}.
 */
@Service
public class GoogleProjectServiceImpl implements GoogleProjectService {

    private final Logger log = LoggerFactory.getLogger(GoogleProjectServiceImpl.class);

    private final GoogleProjectRepository googleProjectRepository;

    private final GoogleProjectMapper googleProjectMapper;

    public GoogleProjectServiceImpl(GoogleProjectRepository googleProjectRepository, GoogleProjectMapper googleProjectMapper) {
        this.googleProjectRepository = googleProjectRepository;
        this.googleProjectMapper = googleProjectMapper;
    }

    /**
     * Save a googleProject.
     *
     * @param googleProjectDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public GoogleProjectDTO save(GoogleProjectDTO googleProjectDTO) {
        log.debug("Request to save GoogleProject : {}", googleProjectDTO);
        GoogleProject googleProject = googleProjectMapper.toEntity(googleProjectDTO);
        googleProject = googleProjectRepository.save(googleProject);
        return googleProjectMapper.toDto(googleProject);
    }

    /**
     * Get all the googleProjects.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    public Page<GoogleProjectDTO> findAll(Pageable pageable) {
        log.debug("Request to get all GoogleProjects");
        return googleProjectRepository.findAll(pageable)
            .map(googleProjectMapper::toDto);
    }

    /**
     * Get one googleProject by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<GoogleProjectDTO> findOne(String id) {
        log.debug("Request to get GoogleProject : {}", id);
        return googleProjectRepository.findById(id)
            .map(googleProjectMapper::toDto);
    }

    /**
     * Delete the googleProject by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete GoogleProject : {}", id);
        googleProjectRepository.deleteById(id);
    }
}
