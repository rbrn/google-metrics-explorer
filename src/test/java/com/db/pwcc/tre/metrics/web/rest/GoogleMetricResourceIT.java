package com.db.pwcc.tre.metrics.web.rest;

import com.db.pwcc.tre.metrics.GcpMetricsExplorerApp;
import com.db.pwcc.tre.metrics.domain.GoogleMetric;
import com.db.pwcc.tre.metrics.repository.GoogleMetricRepository;
import com.db.pwcc.tre.metrics.service.GoogleMetricService;
import com.db.pwcc.tre.metrics.service.dto.GoogleMetricDTO;
import com.db.pwcc.tre.metrics.service.mapper.GoogleMetricMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link GoogleMetricResource} REST controller.
 */
@SpringBootTest(classes = GcpMetricsExplorerApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class GoogleMetricResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private GoogleMetricRepository googleMetricRepository;

    @Autowired
    private GoogleMetricMapper googleMetricMapper;

    @Autowired
    private GoogleMetricService googleMetricService;

    @Autowired
    private MockMvc restGoogleMetricMockMvc;

    private GoogleMetric googleMetric;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GoogleMetric createEntity() {
        GoogleMetric googleMetric = new GoogleMetric()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return googleMetric;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GoogleMetric createUpdatedEntity() {
        GoogleMetric googleMetric = new GoogleMetric()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return googleMetric;
    }

    @BeforeEach
    public void initTest() {
        googleMetricRepository.deleteAll();
        googleMetric = createEntity();
    }

    @Test
    public void createGoogleMetric() throws Exception {
        int databaseSizeBeforeCreate = googleMetricRepository.findAll().size();

        // Create the GoogleMetric
        GoogleMetricDTO googleMetricDTO = googleMetricMapper.toDto(googleMetric);
        restGoogleMetricMockMvc.perform(post("/api/google-metrics")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(googleMetricDTO)))
            .andExpect(status().isCreated());

        // Validate the GoogleMetric in the database
        List<GoogleMetric> googleMetricList = googleMetricRepository.findAll();
        assertThat(googleMetricList).hasSize(databaseSizeBeforeCreate + 1);
        GoogleMetric testGoogleMetric = googleMetricList.get(googleMetricList.size() - 1);
        assertThat(testGoogleMetric.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGoogleMetric.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    public void createGoogleMetricWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = googleMetricRepository.findAll().size();

        // Create the GoogleMetric with an existing ID
        googleMetric.setId("existing_id");
        GoogleMetricDTO googleMetricDTO = googleMetricMapper.toDto(googleMetric);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGoogleMetricMockMvc.perform(post("/api/google-metrics")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(googleMetricDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GoogleMetric in the database
        List<GoogleMetric> googleMetricList = googleMetricRepository.findAll();
        assertThat(googleMetricList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = googleMetricRepository.findAll().size();
        // set the field null
        googleMetric.setName(null);

        // Create the GoogleMetric, which fails.
        GoogleMetricDTO googleMetricDTO = googleMetricMapper.toDto(googleMetric);

        restGoogleMetricMockMvc.perform(post("/api/google-metrics")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(googleMetricDTO)))
            .andExpect(status().isBadRequest());

        List<GoogleMetric> googleMetricList = googleMetricRepository.findAll();
        assertThat(googleMetricList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllGoogleMetrics() throws Exception {
        // Initialize the database
        googleMetricRepository.save(googleMetric);

        // Get all the googleMetricList
        restGoogleMetricMockMvc.perform(get("/api/google-metrics?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(googleMetric.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    public void getGoogleMetric() throws Exception {
        // Initialize the database
        googleMetricRepository.save(googleMetric);

        // Get the googleMetric
        restGoogleMetricMockMvc.perform(get("/api/google-metrics/{id}", googleMetric.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(googleMetric.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    public void getNonExistingGoogleMetric() throws Exception {
        // Get the googleMetric
        restGoogleMetricMockMvc.perform(get("/api/google-metrics/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateGoogleMetric() throws Exception {
        // Initialize the database
        googleMetricRepository.save(googleMetric);

        int databaseSizeBeforeUpdate = googleMetricRepository.findAll().size();

        // Update the googleMetric
        GoogleMetric updatedGoogleMetric = googleMetricRepository.findById(googleMetric.getId()).get();
        updatedGoogleMetric
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        GoogleMetricDTO googleMetricDTO = googleMetricMapper.toDto(updatedGoogleMetric);

        restGoogleMetricMockMvc.perform(put("/api/google-metrics")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(googleMetricDTO)))
            .andExpect(status().isOk());

        // Validate the GoogleMetric in the database
        List<GoogleMetric> googleMetricList = googleMetricRepository.findAll();
        assertThat(googleMetricList).hasSize(databaseSizeBeforeUpdate);
        GoogleMetric testGoogleMetric = googleMetricList.get(googleMetricList.size() - 1);
        assertThat(testGoogleMetric.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGoogleMetric.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    public void updateNonExistingGoogleMetric() throws Exception {
        int databaseSizeBeforeUpdate = googleMetricRepository.findAll().size();

        // Create the GoogleMetric
        GoogleMetricDTO googleMetricDTO = googleMetricMapper.toDto(googleMetric);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGoogleMetricMockMvc.perform(put("/api/google-metrics")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(googleMetricDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GoogleMetric in the database
        List<GoogleMetric> googleMetricList = googleMetricRepository.findAll();
        assertThat(googleMetricList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteGoogleMetric() throws Exception {
        // Initialize the database
        googleMetricRepository.save(googleMetric);

        int databaseSizeBeforeDelete = googleMetricRepository.findAll().size();

        // Delete the googleMetric
        restGoogleMetricMockMvc.perform(delete("/api/google-metrics/{id}", googleMetric.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GoogleMetric> googleMetricList = googleMetricRepository.findAll();
        assertThat(googleMetricList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
