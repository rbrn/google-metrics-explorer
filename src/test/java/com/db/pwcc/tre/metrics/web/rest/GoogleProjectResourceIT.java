package com.db.pwcc.tre.metrics.web.rest;

import com.db.pwcc.tre.metrics.GcpMetricsExplorerApp;
import com.db.pwcc.tre.metrics.domain.GoogleProject;
import com.db.pwcc.tre.metrics.repository.GoogleProjectRepository;
import com.db.pwcc.tre.metrics.service.GoogleProjectService;
import com.db.pwcc.tre.metrics.service.dto.GoogleProjectDTO;
import com.db.pwcc.tre.metrics.service.mapper.GoogleProjectMapper;

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
 * Integration tests for the {@link GoogleProjectResource} REST controller.
 */
@SpringBootTest(classes = GcpMetricsExplorerApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class GoogleProjectResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_GOOGLE_ID = "AAAAAAAAAA";
    private static final String UPDATED_GOOGLE_ID = "BBBBBBBBBB";

    @Autowired
    private GoogleProjectRepository googleProjectRepository;

    @Autowired
    private GoogleProjectMapper googleProjectMapper;

    @Autowired
    private GoogleProjectService googleProjectService;

    @Autowired
    private MockMvc restGoogleProjectMockMvc;

    private GoogleProject googleProject;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GoogleProject createEntity() {
        GoogleProject googleProject = new GoogleProject()
            .name(DEFAULT_NAME)
            .googleId(DEFAULT_GOOGLE_ID);
        return googleProject;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GoogleProject createUpdatedEntity() {
        GoogleProject googleProject = new GoogleProject()
            .name(UPDATED_NAME)
            .googleId(UPDATED_GOOGLE_ID);
        return googleProject;
    }

    @BeforeEach
    public void initTest() {
        googleProjectRepository.deleteAll();
        googleProject = createEntity();
    }

    @Test
    public void createGoogleProject() throws Exception {
        int databaseSizeBeforeCreate = googleProjectRepository.findAll().size();

        // Create the GoogleProject
        GoogleProjectDTO googleProjectDTO = googleProjectMapper.toDto(googleProject);
        restGoogleProjectMockMvc.perform(post("/api/google-projects")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(googleProjectDTO)))
            .andExpect(status().isCreated());

        // Validate the GoogleProject in the database
        List<GoogleProject> googleProjectList = googleProjectRepository.findAll();
        assertThat(googleProjectList).hasSize(databaseSizeBeforeCreate + 1);
        GoogleProject testGoogleProject = googleProjectList.get(googleProjectList.size() - 1);
        assertThat(testGoogleProject.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGoogleProject.getGoogleId()).isEqualTo(DEFAULT_GOOGLE_ID);
    }

    @Test
    public void createGoogleProjectWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = googleProjectRepository.findAll().size();

        // Create the GoogleProject with an existing ID
        googleProject.setId("existing_id");
        GoogleProjectDTO googleProjectDTO = googleProjectMapper.toDto(googleProject);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGoogleProjectMockMvc.perform(post("/api/google-projects")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(googleProjectDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GoogleProject in the database
        List<GoogleProject> googleProjectList = googleProjectRepository.findAll();
        assertThat(googleProjectList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = googleProjectRepository.findAll().size();
        // set the field null
        googleProject.setName(null);

        // Create the GoogleProject, which fails.
        GoogleProjectDTO googleProjectDTO = googleProjectMapper.toDto(googleProject);

        restGoogleProjectMockMvc.perform(post("/api/google-projects")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(googleProjectDTO)))
            .andExpect(status().isBadRequest());

        List<GoogleProject> googleProjectList = googleProjectRepository.findAll();
        assertThat(googleProjectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkGoogleIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = googleProjectRepository.findAll().size();
        // set the field null
        googleProject.setGoogleId(null);

        // Create the GoogleProject, which fails.
        GoogleProjectDTO googleProjectDTO = googleProjectMapper.toDto(googleProject);

        restGoogleProjectMockMvc.perform(post("/api/google-projects")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(googleProjectDTO)))
            .andExpect(status().isBadRequest());

        List<GoogleProject> googleProjectList = googleProjectRepository.findAll();
        assertThat(googleProjectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllGoogleProjects() throws Exception {
        // Initialize the database
        googleProjectRepository.save(googleProject);

        // Get all the googleProjectList
        restGoogleProjectMockMvc.perform(get("/api/google-projects?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(googleProject.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].googleId").value(hasItem(DEFAULT_GOOGLE_ID)));
    }
    
    @Test
    public void getGoogleProject() throws Exception {
        // Initialize the database
        googleProjectRepository.save(googleProject);

        // Get the googleProject
        restGoogleProjectMockMvc.perform(get("/api/google-projects/{id}", googleProject.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(googleProject.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.googleId").value(DEFAULT_GOOGLE_ID));
    }

    @Test
    public void getNonExistingGoogleProject() throws Exception {
        // Get the googleProject
        restGoogleProjectMockMvc.perform(get("/api/google-projects/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateGoogleProject() throws Exception {
        // Initialize the database
        googleProjectRepository.save(googleProject);

        int databaseSizeBeforeUpdate = googleProjectRepository.findAll().size();

        // Update the googleProject
        GoogleProject updatedGoogleProject = googleProjectRepository.findById(googleProject.getId()).get();
        updatedGoogleProject
            .name(UPDATED_NAME)
            .googleId(UPDATED_GOOGLE_ID);
        GoogleProjectDTO googleProjectDTO = googleProjectMapper.toDto(updatedGoogleProject);

        restGoogleProjectMockMvc.perform(put("/api/google-projects")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(googleProjectDTO)))
            .andExpect(status().isOk());

        // Validate the GoogleProject in the database
        List<GoogleProject> googleProjectList = googleProjectRepository.findAll();
        assertThat(googleProjectList).hasSize(databaseSizeBeforeUpdate);
        GoogleProject testGoogleProject = googleProjectList.get(googleProjectList.size() - 1);
        assertThat(testGoogleProject.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGoogleProject.getGoogleId()).isEqualTo(UPDATED_GOOGLE_ID);
    }

    @Test
    public void updateNonExistingGoogleProject() throws Exception {
        int databaseSizeBeforeUpdate = googleProjectRepository.findAll().size();

        // Create the GoogleProject
        GoogleProjectDTO googleProjectDTO = googleProjectMapper.toDto(googleProject);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGoogleProjectMockMvc.perform(put("/api/google-projects")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(googleProjectDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GoogleProject in the database
        List<GoogleProject> googleProjectList = googleProjectRepository.findAll();
        assertThat(googleProjectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteGoogleProject() throws Exception {
        // Initialize the database
        googleProjectRepository.save(googleProject);

        int databaseSizeBeforeDelete = googleProjectRepository.findAll().size();

        // Delete the googleProject
        restGoogleProjectMockMvc.perform(delete("/api/google-projects/{id}", googleProject.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GoogleProject> googleProjectList = googleProjectRepository.findAll();
        assertThat(googleProjectList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
