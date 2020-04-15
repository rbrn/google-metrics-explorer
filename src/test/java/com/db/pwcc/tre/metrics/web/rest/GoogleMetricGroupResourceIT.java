package com.db.pwcc.tre.metrics.web.rest;

import com.db.pwcc.tre.metrics.GcpMetricsExplorerApp;
import com.db.pwcc.tre.metrics.domain.GoogleMetricGroup;
import com.db.pwcc.tre.metrics.repository.GoogleMetricGroupRepository;
import com.db.pwcc.tre.metrics.service.GoogleMetricGroupService;
import com.db.pwcc.tre.metrics.service.dto.GoogleMetricGroupDTO;
import com.db.pwcc.tre.metrics.service.mapper.GoogleMetricGroupMapper;

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
 * Integration tests for the {@link GoogleMetricGroupResource} REST controller.
 */
@SpringBootTest(classes = GcpMetricsExplorerApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class GoogleMetricGroupResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_GOOGLE_ID = "AAAAAAAAAA";
    private static final String UPDATED_GOOGLE_ID = "BBBBBBBBBB";

    @Autowired
    private GoogleMetricGroupRepository googleMetricGroupRepository;

    @Autowired
    private GoogleMetricGroupMapper googleMetricGroupMapper;

    @Autowired
    private GoogleMetricGroupService googleMetricGroupService;

    @Autowired
    private MockMvc restGoogleMetricGroupMockMvc;

    private GoogleMetricGroup googleMetricGroup;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GoogleMetricGroup createEntity() {
        GoogleMetricGroup googleMetricGroup = new GoogleMetricGroup()
            .name(DEFAULT_NAME)
            .googleId(DEFAULT_GOOGLE_ID);
        return googleMetricGroup;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GoogleMetricGroup createUpdatedEntity() {
        GoogleMetricGroup googleMetricGroup = new GoogleMetricGroup()
            .name(UPDATED_NAME)
            .googleId(UPDATED_GOOGLE_ID);
        return googleMetricGroup;
    }

    @BeforeEach
    public void initTest() {
        googleMetricGroupRepository.deleteAll();
        googleMetricGroup = createEntity();
    }

    @Test
    public void createGoogleMetricGroup() throws Exception {
        int databaseSizeBeforeCreate = googleMetricGroupRepository.findAll().size();

        // Create the GoogleMetricGroup
        GoogleMetricGroupDTO googleMetricGroupDTO = googleMetricGroupMapper.toDto(googleMetricGroup);
        restGoogleMetricGroupMockMvc.perform(post("/api/google-metric-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(googleMetricGroupDTO)))
            .andExpect(status().isCreated());

        // Validate the GoogleMetricGroup in the database
        List<GoogleMetricGroup> googleMetricGroupList = googleMetricGroupRepository.findAll();
        assertThat(googleMetricGroupList).hasSize(databaseSizeBeforeCreate + 1);
        GoogleMetricGroup testGoogleMetricGroup = googleMetricGroupList.get(googleMetricGroupList.size() - 1);
        assertThat(testGoogleMetricGroup.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGoogleMetricGroup.getGoogleId()).isEqualTo(DEFAULT_GOOGLE_ID);
    }

    @Test
    public void createGoogleMetricGroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = googleMetricGroupRepository.findAll().size();

        // Create the GoogleMetricGroup with an existing ID
        googleMetricGroup.setId("existing_id");
        GoogleMetricGroupDTO googleMetricGroupDTO = googleMetricGroupMapper.toDto(googleMetricGroup);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGoogleMetricGroupMockMvc.perform(post("/api/google-metric-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(googleMetricGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GoogleMetricGroup in the database
        List<GoogleMetricGroup> googleMetricGroupList = googleMetricGroupRepository.findAll();
        assertThat(googleMetricGroupList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = googleMetricGroupRepository.findAll().size();
        // set the field null
        googleMetricGroup.setName(null);

        // Create the GoogleMetricGroup, which fails.
        GoogleMetricGroupDTO googleMetricGroupDTO = googleMetricGroupMapper.toDto(googleMetricGroup);

        restGoogleMetricGroupMockMvc.perform(post("/api/google-metric-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(googleMetricGroupDTO)))
            .andExpect(status().isBadRequest());

        List<GoogleMetricGroup> googleMetricGroupList = googleMetricGroupRepository.findAll();
        assertThat(googleMetricGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkGoogleIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = googleMetricGroupRepository.findAll().size();
        // set the field null
        googleMetricGroup.setGoogleId(null);

        // Create the GoogleMetricGroup, which fails.
        GoogleMetricGroupDTO googleMetricGroupDTO = googleMetricGroupMapper.toDto(googleMetricGroup);

        restGoogleMetricGroupMockMvc.perform(post("/api/google-metric-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(googleMetricGroupDTO)))
            .andExpect(status().isBadRequest());

        List<GoogleMetricGroup> googleMetricGroupList = googleMetricGroupRepository.findAll();
        assertThat(googleMetricGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllGoogleMetricGroups() throws Exception {
        // Initialize the database
        googleMetricGroupRepository.save(googleMetricGroup);

        // Get all the googleMetricGroupList
        restGoogleMetricGroupMockMvc.perform(get("/api/google-metric-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(googleMetricGroup.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].googleId").value(hasItem(DEFAULT_GOOGLE_ID)));
    }
    
    @Test
    public void getGoogleMetricGroup() throws Exception {
        // Initialize the database
        googleMetricGroupRepository.save(googleMetricGroup);

        // Get the googleMetricGroup
        restGoogleMetricGroupMockMvc.perform(get("/api/google-metric-groups/{id}", googleMetricGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(googleMetricGroup.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.googleId").value(DEFAULT_GOOGLE_ID));
    }

    @Test
    public void getNonExistingGoogleMetricGroup() throws Exception {
        // Get the googleMetricGroup
        restGoogleMetricGroupMockMvc.perform(get("/api/google-metric-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateGoogleMetricGroup() throws Exception {
        // Initialize the database
        googleMetricGroupRepository.save(googleMetricGroup);

        int databaseSizeBeforeUpdate = googleMetricGroupRepository.findAll().size();

        // Update the googleMetricGroup
        GoogleMetricGroup updatedGoogleMetricGroup = googleMetricGroupRepository.findById(googleMetricGroup.getId()).get();
        updatedGoogleMetricGroup
            .name(UPDATED_NAME)
            .googleId(UPDATED_GOOGLE_ID);
        GoogleMetricGroupDTO googleMetricGroupDTO = googleMetricGroupMapper.toDto(updatedGoogleMetricGroup);

        restGoogleMetricGroupMockMvc.perform(put("/api/google-metric-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(googleMetricGroupDTO)))
            .andExpect(status().isOk());

        // Validate the GoogleMetricGroup in the database
        List<GoogleMetricGroup> googleMetricGroupList = googleMetricGroupRepository.findAll();
        assertThat(googleMetricGroupList).hasSize(databaseSizeBeforeUpdate);
        GoogleMetricGroup testGoogleMetricGroup = googleMetricGroupList.get(googleMetricGroupList.size() - 1);
        assertThat(testGoogleMetricGroup.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGoogleMetricGroup.getGoogleId()).isEqualTo(UPDATED_GOOGLE_ID);
    }

    @Test
    public void updateNonExistingGoogleMetricGroup() throws Exception {
        int databaseSizeBeforeUpdate = googleMetricGroupRepository.findAll().size();

        // Create the GoogleMetricGroup
        GoogleMetricGroupDTO googleMetricGroupDTO = googleMetricGroupMapper.toDto(googleMetricGroup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGoogleMetricGroupMockMvc.perform(put("/api/google-metric-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(googleMetricGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GoogleMetricGroup in the database
        List<GoogleMetricGroup> googleMetricGroupList = googleMetricGroupRepository.findAll();
        assertThat(googleMetricGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteGoogleMetricGroup() throws Exception {
        // Initialize the database
        googleMetricGroupRepository.save(googleMetricGroup);

        int databaseSizeBeforeDelete = googleMetricGroupRepository.findAll().size();

        // Delete the googleMetricGroup
        restGoogleMetricGroupMockMvc.perform(delete("/api/google-metric-groups/{id}", googleMetricGroup.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GoogleMetricGroup> googleMetricGroupList = googleMetricGroupRepository.findAll();
        assertThat(googleMetricGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
