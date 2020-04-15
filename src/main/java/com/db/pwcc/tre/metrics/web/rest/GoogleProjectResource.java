package com.db.pwcc.tre.metrics.web.rest;

import com.db.pwcc.tre.metrics.service.GoogleProjectService;
import com.db.pwcc.tre.metrics.web.rest.errors.BadRequestAlertException;
import com.db.pwcc.tre.metrics.service.dto.GoogleProjectDTO;

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

/**
 * REST controller for managing {@link com.db.pwcc.tre.metrics.domain.GoogleProject}.
 */
@RestController
@RequestMapping("/api")
public class GoogleProjectResource {

    private final Logger log = LoggerFactory.getLogger(GoogleProjectResource.class);

    private static final String ENTITY_NAME = "googleProject";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GoogleProjectService googleProjectService;

    public GoogleProjectResource(GoogleProjectService googleProjectService) {
        this.googleProjectService = googleProjectService;
    }

    /**
     * {@code POST  /google-projects} : Create a new googleProject.
     *
     * @param googleProjectDTO the googleProjectDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new googleProjectDTO, or with status {@code 400 (Bad Request)} if the googleProject has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/google-projects")
    public ResponseEntity<GoogleProjectDTO> createGoogleProject(@Valid @RequestBody GoogleProjectDTO googleProjectDTO) throws URISyntaxException {
        log.debug("REST request to save GoogleProject : {}", googleProjectDTO);
        if (googleProjectDTO.getId() != null) {
            throw new BadRequestAlertException("A new googleProject cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GoogleProjectDTO result = googleProjectService.save(googleProjectDTO);
        return ResponseEntity.created(new URI("/api/google-projects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /google-projects} : Updates an existing googleProject.
     *
     * @param googleProjectDTO the googleProjectDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated googleProjectDTO,
     * or with status {@code 400 (Bad Request)} if the googleProjectDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the googleProjectDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/google-projects")
    public ResponseEntity<GoogleProjectDTO> updateGoogleProject(@Valid @RequestBody GoogleProjectDTO googleProjectDTO) throws URISyntaxException {
        log.debug("REST request to update GoogleProject : {}", googleProjectDTO);
        if (googleProjectDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GoogleProjectDTO result = googleProjectService.save(googleProjectDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, googleProjectDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /google-projects} : get all the googleProjects.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of googleProjects in body.
     */
    @GetMapping("/google-projects")
    public ResponseEntity<List<GoogleProjectDTO>> getAllGoogleProjects(Pageable pageable) {
        log.debug("REST request to get a page of GoogleProjects");
        Page<GoogleProjectDTO> page = googleProjectService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /google-projects/:id} : get the "id" googleProject.
     *
     * @param id the id of the googleProjectDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the googleProjectDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/google-projects/{id}")
    public ResponseEntity<GoogleProjectDTO> getGoogleProject(@PathVariable String id) {
        log.debug("REST request to get GoogleProject : {}", id);
        Optional<GoogleProjectDTO> googleProjectDTO = googleProjectService.findOne(id);
        return ResponseUtil.wrapOrNotFound(googleProjectDTO);
    }

    /**
     * {@code DELETE  /google-projects/:id} : delete the "id" googleProject.
     *
     * @param id the id of the googleProjectDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/google-projects/{id}")
    public ResponseEntity<Void> deleteGoogleProject(@PathVariable String id) {
        log.debug("REST request to delete GoogleProject : {}", id);
        googleProjectService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
