package com.db.pwcc.tre.metrics.web.rest;

import com.db.pwcc.tre.metrics.service.GoogleMetricService;
import com.db.pwcc.tre.metrics.web.rest.errors.BadRequestAlertException;
import com.db.pwcc.tre.metrics.service.dto.GoogleMetricDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.db.pwcc.tre.metrics.domain.GoogleMetric}.
 */
@RestController
@RequestMapping("/api")
public class GoogleMetricResource {

    private final Logger log = LoggerFactory.getLogger(GoogleMetricResource.class);

    private static final String ENTITY_NAME = "googleMetric";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GoogleMetricService googleMetricService;

    public GoogleMetricResource(GoogleMetricService googleMetricService) {
        this.googleMetricService = googleMetricService;
    }

    /**
     * {@code POST  /google-metrics} : Create a new googleMetric.
     *
     * @param googleMetricDTO the googleMetricDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new googleMetricDTO, or with status {@code 400 (Bad Request)} if the googleMetric has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/google-metrics")
    public ResponseEntity<GoogleMetricDTO> createGoogleMetric(@Valid @RequestBody GoogleMetricDTO googleMetricDTO) throws URISyntaxException {
        log.debug("REST request to save GoogleMetric : {}", googleMetricDTO);
        if (googleMetricDTO.getId() != null) {
            throw new BadRequestAlertException("A new googleMetric cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GoogleMetricDTO result = googleMetricService.save(googleMetricDTO);
        return ResponseEntity.created(new URI("/api/google-metrics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /google-metrics} : Updates an existing googleMetric.
     *
     * @param googleMetricDTO the googleMetricDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated googleMetricDTO,
     * or with status {@code 400 (Bad Request)} if the googleMetricDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the googleMetricDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/google-metrics")
    public ResponseEntity<GoogleMetricDTO> updateGoogleMetric(@Valid @RequestBody GoogleMetricDTO googleMetricDTO) throws URISyntaxException {
        log.debug("REST request to update GoogleMetric : {}", googleMetricDTO);
        if (googleMetricDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GoogleMetricDTO result = googleMetricService.save(googleMetricDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, googleMetricDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /google-metrics} : get all the googleMetrics.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of googleMetrics in body.
     */
    @GetMapping("/google-metrics")
    public ResponseEntity<List<GoogleMetricDTO>> getAllGoogleMetrics(Pageable pageable) {
        log.debug("REST request to get a page of GoogleMetrics");
        Page<GoogleMetricDTO> page = googleMetricService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /google-metrics} : get all the googleMetrics.
     *
     * @param groupName the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of googleMetrics in body.
     */
    @GetMapping("/google-metrics/by-group/{groupName}")
    public ResponseEntity<List<GoogleMetricDTO>> getAllGoogleMetrics(@PathVariable String  groupName) {
        log.debug("REST request to get a page of GoogleMetrics");
        List<GoogleMetricDTO> page = googleMetricService.findByName(groupName);
        return ResponseEntity.ok().body(page);
    }


    @Autowired
    private GoogleMetricCustomService googleMetricCustomService;
    /**
     * {@code GET  /google-metrics/:id} : get the "id" googleMetric.
     *
     * @param id the id of the googleMetricDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the googleMetricDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/google-metrics/{id}")
    public ResponseEntity<GoogleMetricDTO> getGoogleMetric(@PathVariable String id) throws IOException {
        log.debug("REST request to get GoogleMetric : {}", id);
        Optional<GoogleMetricDTO> googleMetricDTO = googleMetricService.findOne(id);
        googleMetricDTO.get().setData( googleMetricCustomService.listTimeSeriesAggregate(googleMetricDTO));
        return ResponseUtil.wrapOrNotFound(googleMetricDTO);
    }

    /**
     * {@code DELETE  /google-metrics/:id} : delete the "id" googleMetric.
     *
     * @param id the id of the googleMetricDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/google-metrics/{id}")
    public ResponseEntity<Void> deleteGoogleMetric(@PathVariable String id) {
        log.debug("REST request to delete GoogleMetric : {}", id);
        googleMetricService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
