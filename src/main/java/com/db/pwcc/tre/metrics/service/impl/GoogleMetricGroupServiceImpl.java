package com.db.pwcc.tre.metrics.service.impl;

import com.db.pwcc.tre.metrics.service.GoogleMetricGroupService;
import com.db.pwcc.tre.metrics.domain.GoogleMetricGroup;
import com.db.pwcc.tre.metrics.repository.GoogleMetricGroupRepository;
import com.db.pwcc.tre.metrics.repository.search.GoogleMetricGroupSearchRepository;
import com.db.pwcc.tre.metrics.service.dto.GoogleMetricGroupDTO;
import com.db.pwcc.tre.metrics.service.mapper.GoogleMetricGroupMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link GoogleMetricGroup}.
 */
@Service
public class GoogleMetricGroupServiceImpl implements GoogleMetricGroupService {

    private final Logger log = LoggerFactory.getLogger(GoogleMetricGroupServiceImpl.class);

    private final GoogleMetricGroupRepository googleMetricGroupRepository;

    private final GoogleMetricGroupMapper googleMetricGroupMapper;

    private final GoogleMetricGroupSearchRepository googleMetricGroupSearchRepository;

    public GoogleMetricGroupServiceImpl(GoogleMetricGroupRepository googleMetricGroupRepository, GoogleMetricGroupMapper googleMetricGroupMapper, GoogleMetricGroupSearchRepository googleMetricGroupSearchRepository) {
        this.googleMetricGroupRepository = googleMetricGroupRepository;
        this.googleMetricGroupMapper = googleMetricGroupMapper;
        this.googleMetricGroupSearchRepository = googleMetricGroupSearchRepository;
    }

    /**
     * Save a googleMetricGroup.
     *
     * @param googleMetricGroupDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public GoogleMetricGroupDTO save(GoogleMetricGroupDTO googleMetricGroupDTO) {
        log.debug("Request to save GoogleMetricGroup : {}", googleMetricGroupDTO);
        GoogleMetricGroup googleMetricGroup = googleMetricGroupMapper.toEntity(googleMetricGroupDTO);
        googleMetricGroup = googleMetricGroupRepository.save(googleMetricGroup);
        GoogleMetricGroupDTO result = googleMetricGroupMapper.toDto(googleMetricGroup);
        googleMetricGroupSearchRepository.save(googleMetricGroup);
        return result;
    }

    /**
     * Get all the googleMetricGroups.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    public Page<GoogleMetricGroupDTO> findAll(Pageable pageable) {
        log.debug("Request to get all GoogleMetricGroups");
        return googleMetricGroupRepository.findAll(pageable)
            .map(googleMetricGroupMapper::toDto);
    }

    /**
     * Get one googleMetricGroup by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<GoogleMetricGroupDTO> findOne(String id) {
        log.debug("Request to get GoogleMetricGroup : {}", id);
        return googleMetricGroupRepository.findById(id)
            .map(googleMetricGroupMapper::toDto);
    }

    /**
     * Delete the googleMetricGroup by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete GoogleMetricGroup : {}", id);
        googleMetricGroupRepository.deleteById(id);
        googleMetricGroupSearchRepository.deleteById(id);
    }

    /**
     * Search for the googleMetricGroup corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    public Page<GoogleMetricGroupDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of GoogleMetricGroups for query {}", query);
        return googleMetricGroupSearchRepository.search(queryStringQuery(query), pageable)
            .map(googleMetricGroupMapper::toDto);
    }
}
