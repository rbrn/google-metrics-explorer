package com.db.pwcc.tre.metrics.service.impl;

import com.db.pwcc.tre.metrics.service.GoogleMetricService;
import com.db.pwcc.tre.metrics.domain.GoogleMetric;
import com.db.pwcc.tre.metrics.repository.GoogleMetricRepository;
import com.db.pwcc.tre.metrics.service.dto.GoogleMetricDTO;
import com.db.pwcc.tre.metrics.service.mapper.GoogleMetricMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link GoogleMetric}.
 */
@Service
public class GoogleMetricServiceImpl implements GoogleMetricService {

    private final Logger log = LoggerFactory.getLogger(GoogleMetricServiceImpl.class);

    private final GoogleMetricRepository googleMetricRepository;

    private final GoogleMetricMapper googleMetricMapper;

    public GoogleMetricServiceImpl(GoogleMetricRepository googleMetricRepository, GoogleMetricMapper googleMetricMapper) {
        this.googleMetricRepository = googleMetricRepository;
        this.googleMetricMapper = googleMetricMapper;
    }

    /**
     * Save a googleMetric.
     *
     * @param googleMetricDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public GoogleMetricDTO save(GoogleMetricDTO googleMetricDTO) {
        log.debug("Request to save GoogleMetric : {}", googleMetricDTO);
        GoogleMetric googleMetric = googleMetricMapper.toEntity(googleMetricDTO);
        googleMetric = googleMetricRepository.save(googleMetric);
        return googleMetricMapper.toDto(googleMetric);
    }

    /**
     * Get all the googleMetrics.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    public Page<GoogleMetricDTO> findAll(Pageable pageable) {
        log.debug("Request to get all GoogleMetrics");
        return googleMetricRepository.findAll(pageable)
            .map(googleMetricMapper::toDto);
    }

    /**
     * Get one googleMetric by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<GoogleMetricDTO> findOne(String id) {
        log.debug("Request to get GoogleMetric : {}", id);
        return googleMetricRepository.findById(id)
            .map(googleMetricMapper::toDto);
    }

    /**
     * Delete the googleMetric by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete GoogleMetric : {}", id);
        googleMetricRepository.deleteById(id);
    }

    @Override
    public List<GoogleMetricDTO> findByName(String groupName) {
        return googleMetricRepository.findAllByMetricGroupId(groupName)
            .stream()
            .map(googleMetricMapper::toDto).collect(Collectors.toList());
    }
}
