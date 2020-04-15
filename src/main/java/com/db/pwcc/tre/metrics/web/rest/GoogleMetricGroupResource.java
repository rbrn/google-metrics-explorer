package com.db.pwcc.tre.metrics.web.rest;

import com.db.pwcc.tre.metrics.service.GoogleMetricGroupService;
import com.db.pwcc.tre.metrics.web.rest.errors.BadRequestAlertException;
import com.db.pwcc.tre.metrics.service.dto.GoogleMetricGroupDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.db.pwcc.tre.metrics.domain.GoogleMetricGroup}.
 */
@RestController
@RequestMapping("/api")
public class GoogleMetricGroupResource {

    private final Logger log = LoggerFactory.getLogger(GoogleMetricGroupResource.class);

    private static final String ENTITY_NAME = "googleMetricGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GoogleMetricGroupService googleMetricGroupService;

    public GoogleMetricGroupResource(GoogleMetricGroupService googleMetricGroupService) {
        this.googleMetricGroupService = googleMetricGroupService;
    }

    /**
     * {@code POST  /google-metric-groups} : Create a new googleMetricGroup.
     *
     * @param googleMetricGroupDTO the googleMetricGroupDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new googleMetricGroupDTO, or with status {@code 400 (Bad Request)} if the googleMetricGroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/google-metric-groups")
    public ResponseEntity<GoogleMetricGroupDTO> createGoogleMetricGroup(@Valid @RequestBody GoogleMetricGroupDTO googleMetricGroupDTO) throws URISyntaxException {
        log.debug("REST request to save GoogleMetricGroup : {}", googleMetricGroupDTO);
        if (googleMetricGroupDTO.getId() != null) {
            throw new BadRequestAlertException("A new googleMetricGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GoogleMetricGroupDTO result = googleMetricGroupService.save(googleMetricGroupDTO);
        return ResponseEntity.created(new URI("/api/google-metric-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /google-metric-groups} : Updates an existing googleMetricGroup.
     *
     * @param googleMetricGroupDTO the googleMetricGroupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated googleMetricGroupDTO,
     * or with status {@code 400 (Bad Request)} if the googleMetricGroupDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the googleMetricGroupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/google-metric-groups")
    public ResponseEntity<GoogleMetricGroupDTO> updateGoogleMetricGroup(@Valid @RequestBody GoogleMetricGroupDTO googleMetricGroupDTO) throws URISyntaxException {
        log.debug("REST request to update GoogleMetricGroup : {}", googleMetricGroupDTO);
        if (googleMetricGroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GoogleMetricGroupDTO result = googleMetricGroupService.save(googleMetricGroupDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, googleMetricGroupDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /google-metric-groups} : get all the googleMetricGroups.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of googleMetricGroups in body.
     */
    @GetMapping("/google-metric-groups")
    public ResponseEntity<List<GoogleMetricGroupDTO>> getAllGoogleMetricGroups(Pageable pageable) {
        log.debug("REST request to get a page of GoogleMetricGroups");
        Page<GoogleMetricGroupDTO> page = googleMetricGroupService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /google-metric-groups/:id} : get the "id" googleMetricGroup.
     *
     * @param id the id of the googleMetricGroupDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the googleMetricGroupDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/google-metric-groups/{id}")
    public ResponseEntity<GoogleMetricGroupDTO> getGoogleMetricGroup(@PathVariable String id) {
        log.debug("REST request to get GoogleMetricGroup : {}", id);
        Optional<GoogleMetricGroupDTO> googleMetricGroupDTO = googleMetricGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(googleMetricGroupDTO);
    }

    /**
     * {@code DELETE  /google-metric-groups/:id} : delete the "id" googleMetricGroup.
     *
     * @param id the id of the googleMetricGroupDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/google-metric-groups/{id}")
    public ResponseEntity<Void> deleteGoogleMetricGroup(@PathVariable String id) {
        log.debug("REST request to delete GoogleMetricGroup : {}", id);
        googleMetricGroupService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }

    /**
     * {@code SEARCH  /_search/google-metric-groups?query=:query} : search for the googleMetricGroup corresponding
     * to the query.
     *
     * @param query the query of the googleMetricGroup search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/google-metric-groups")
    public ResponseEntity<List<GoogleMetricGroupDTO>> searchGoogleMetricGroups(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of GoogleMetricGroups for query {}", query);
        Page<GoogleMetricGroupDTO> page = googleMetricGroupService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
