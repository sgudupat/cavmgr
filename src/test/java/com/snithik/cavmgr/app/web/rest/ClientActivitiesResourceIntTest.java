package com.snithik.cavmgr.app.web.rest;

import com.snithik.cavmgr.app.CavmgrApp;

import com.snithik.cavmgr.app.domain.ClientActivities;
import com.snithik.cavmgr.app.repository.ClientActivitiesRepository;
import com.snithik.cavmgr.app.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ClientActivitiesResource REST controller.
 *
 * @see ClientActivitiesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CavmgrApp.class)
public class ClientActivitiesResourceIntTest {

    private static final String DEFAULT_ACTIVITY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVITY_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_PRIORITY = 1;
    private static final Integer UPDATED_PRIORITY = 2;

    private static final Boolean DEFAULT_APPLICABILITY = false;
    private static final Boolean UPDATED_APPLICABILITY = true;

    private static final Long DEFAULT_ORG_ID = 1L;
    private static final Long UPDATED_ORG_ID = 2L;

    @Autowired
    private ClientActivitiesRepository clientActivitiesRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restClientActivitiesMockMvc;

    private ClientActivities clientActivities;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ClientActivitiesResource clientActivitiesResource = new ClientActivitiesResource(clientActivitiesRepository);
        this.restClientActivitiesMockMvc = MockMvcBuilders.standaloneSetup(clientActivitiesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClientActivities createEntity(EntityManager em) {
        ClientActivities clientActivities = new ClientActivities()
            .activityName(DEFAULT_ACTIVITY_NAME)
            .priority(DEFAULT_PRIORITY)
            .applicability(DEFAULT_APPLICABILITY)
            .orgId(DEFAULT_ORG_ID);
        return clientActivities;
    }

    @Before
    public void initTest() {
        clientActivities = createEntity(em);
    }

    @Test
    @Transactional
    public void createClientActivities() throws Exception {
        int databaseSizeBeforeCreate = clientActivitiesRepository.findAll().size();

        // Create the ClientActivities
        restClientActivitiesMockMvc.perform(post("/api/client-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientActivities)))
            .andExpect(status().isCreated());

        // Validate the ClientActivities in the database
        List<ClientActivities> clientActivitiesList = clientActivitiesRepository.findAll();
        assertThat(clientActivitiesList).hasSize(databaseSizeBeforeCreate + 1);
        ClientActivities testClientActivities = clientActivitiesList.get(clientActivitiesList.size() - 1);
        assertThat(testClientActivities.getActivityName()).isEqualTo(DEFAULT_ACTIVITY_NAME);
        assertThat(testClientActivities.getPriority()).isEqualTo(DEFAULT_PRIORITY);
        assertThat(testClientActivities.isApplicability()).isEqualTo(DEFAULT_APPLICABILITY);
        assertThat(testClientActivities.getOrgId()).isEqualTo(DEFAULT_ORG_ID);
    }

    @Test
    @Transactional
    public void createClientActivitiesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clientActivitiesRepository.findAll().size();

        // Create the ClientActivities with an existing ID
        clientActivities.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClientActivitiesMockMvc.perform(post("/api/client-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientActivities)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ClientActivities> clientActivitiesList = clientActivitiesRepository.findAll();
        assertThat(clientActivitiesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllClientActivities() throws Exception {
        // Initialize the database
        clientActivitiesRepository.saveAndFlush(clientActivities);

        // Get all the clientActivitiesList
        restClientActivitiesMockMvc.perform(get("/api/client-activities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clientActivities.getId().intValue())))
            .andExpect(jsonPath("$.[*].activityName").value(hasItem(DEFAULT_ACTIVITY_NAME.toString())))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY)))
            .andExpect(jsonPath("$.[*].applicability").value(hasItem(DEFAULT_APPLICABILITY.booleanValue())))
            .andExpect(jsonPath("$.[*].orgId").value(hasItem(DEFAULT_ORG_ID.intValue())));
    }

    @Test
    @Transactional
    public void getClientActivities() throws Exception {
        // Initialize the database
        clientActivitiesRepository.saveAndFlush(clientActivities);

        // Get the clientActivities
        restClientActivitiesMockMvc.perform(get("/api/client-activities/{id}", clientActivities.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(clientActivities.getId().intValue()))
            .andExpect(jsonPath("$.activityName").value(DEFAULT_ACTIVITY_NAME.toString()))
            .andExpect(jsonPath("$.priority").value(DEFAULT_PRIORITY))
            .andExpect(jsonPath("$.applicability").value(DEFAULT_APPLICABILITY.booleanValue()))
            .andExpect(jsonPath("$.orgId").value(DEFAULT_ORG_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingClientActivities() throws Exception {
        // Get the clientActivities
        restClientActivitiesMockMvc.perform(get("/api/client-activities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClientActivities() throws Exception {
        // Initialize the database
        clientActivitiesRepository.saveAndFlush(clientActivities);
        int databaseSizeBeforeUpdate = clientActivitiesRepository.findAll().size();

        // Update the clientActivities
        ClientActivities updatedClientActivities = clientActivitiesRepository.findOne(clientActivities.getId());
        updatedClientActivities
            .activityName(UPDATED_ACTIVITY_NAME)
            .priority(UPDATED_PRIORITY)
            .applicability(UPDATED_APPLICABILITY)
            .orgId(UPDATED_ORG_ID);

        restClientActivitiesMockMvc.perform(put("/api/client-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedClientActivities)))
            .andExpect(status().isOk());

        // Validate the ClientActivities in the database
        List<ClientActivities> clientActivitiesList = clientActivitiesRepository.findAll();
        assertThat(clientActivitiesList).hasSize(databaseSizeBeforeUpdate);
        ClientActivities testClientActivities = clientActivitiesList.get(clientActivitiesList.size() - 1);
        assertThat(testClientActivities.getActivityName()).isEqualTo(UPDATED_ACTIVITY_NAME);
        assertThat(testClientActivities.getPriority()).isEqualTo(UPDATED_PRIORITY);
        assertThat(testClientActivities.isApplicability()).isEqualTo(UPDATED_APPLICABILITY);
        assertThat(testClientActivities.getOrgId()).isEqualTo(UPDATED_ORG_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingClientActivities() throws Exception {
        int databaseSizeBeforeUpdate = clientActivitiesRepository.findAll().size();

        // Create the ClientActivities

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restClientActivitiesMockMvc.perform(put("/api/client-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientActivities)))
            .andExpect(status().isCreated());

        // Validate the ClientActivities in the database
        List<ClientActivities> clientActivitiesList = clientActivitiesRepository.findAll();
        assertThat(clientActivitiesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteClientActivities() throws Exception {
        // Initialize the database
        clientActivitiesRepository.saveAndFlush(clientActivities);
        int databaseSizeBeforeDelete = clientActivitiesRepository.findAll().size();

        // Get the clientActivities
        restClientActivitiesMockMvc.perform(delete("/api/client-activities/{id}", clientActivities.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ClientActivities> clientActivitiesList = clientActivitiesRepository.findAll();
        assertThat(clientActivitiesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClientActivities.class);
    }
}
