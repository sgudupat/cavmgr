package com.snithik.cavmgr.app.web.rest;

import com.snithik.cavmgr.app.CavmgrApp;

import com.snithik.cavmgr.app.domain.ClientActivityDetails;
import com.snithik.cavmgr.app.repository.ClientActivityDetailsRepository;
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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.snithik.cavmgr.app.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ClientActivityDetailsResource REST controller.
 *
 * @see ClientActivityDetailsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CavmgrApp.class)
public class ClientActivityDetailsResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DUE_DATE = "AAAAAAAAAA";
    private static final String UPDATED_DUE_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_INTERNAL_DUE_DATE = "AAAAAAAAAA";
    private static final String UPDATED_INTERNAL_DUE_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    private static final String DEFAULT_WORK_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_WORK_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_MONTH_YEAR = "AAAAAAAAAA";
    private static final String UPDATED_MONTH_YEAR = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATION_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATION_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_LAST_UPDATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_UPDATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_LAST_UPATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_UPATED_BY = "BBBBBBBBBB";

    private static final Long DEFAULT_ORG_ID = 1L;
    private static final Long UPDATED_ORG_ID = 2L;

    @Autowired
    private ClientActivityDetailsRepository clientActivityDetailsRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restClientActivityDetailsMockMvc;

    private ClientActivityDetails clientActivityDetails;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ClientActivityDetailsResource clientActivityDetailsResource = new ClientActivityDetailsResource(clientActivityDetailsRepository);
        this.restClientActivityDetailsMockMvc = MockMvcBuilders.standaloneSetup(clientActivityDetailsResource)
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
    public static ClientActivityDetails createEntity(EntityManager em) {
        ClientActivityDetails clientActivityDetails = new ClientActivityDetails()
            .name(DEFAULT_NAME)
            .dueDate(DEFAULT_DUE_DATE)
            .internalDueDate(DEFAULT_INTERNAL_DUE_DATE)
            .status(DEFAULT_STATUS)
            .comments(DEFAULT_COMMENTS)
            .workStatus(DEFAULT_WORK_STATUS)
            .monthYear(DEFAULT_MONTH_YEAR)
            .creationDate(DEFAULT_CREATION_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .lastUpdateDate(DEFAULT_LAST_UPDATE_DATE)
            .lastUpatedBy(DEFAULT_LAST_UPATED_BY)
            .orgId(DEFAULT_ORG_ID);
        return clientActivityDetails;
    }

    @Before
    public void initTest() {
        clientActivityDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createClientActivityDetails() throws Exception {
        int databaseSizeBeforeCreate = clientActivityDetailsRepository.findAll().size();

        // Create the ClientActivityDetails
        restClientActivityDetailsMockMvc.perform(post("/api/client-activity-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientActivityDetails)))
            .andExpect(status().isCreated());

        // Validate the ClientActivityDetails in the database
        List<ClientActivityDetails> clientActivityDetailsList = clientActivityDetailsRepository.findAll();
        assertThat(clientActivityDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        ClientActivityDetails testClientActivityDetails = clientActivityDetailsList.get(clientActivityDetailsList.size() - 1);
        assertThat(testClientActivityDetails.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testClientActivityDetails.getDueDate()).isEqualTo(DEFAULT_DUE_DATE);
        assertThat(testClientActivityDetails.getInternalDueDate()).isEqualTo(DEFAULT_INTERNAL_DUE_DATE);
        assertThat(testClientActivityDetails.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testClientActivityDetails.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testClientActivityDetails.getWorkStatus()).isEqualTo(DEFAULT_WORK_STATUS);
        assertThat(testClientActivityDetails.getMonthYear()).isEqualTo(DEFAULT_MONTH_YEAR);
        assertThat(testClientActivityDetails.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testClientActivityDetails.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testClientActivityDetails.getLastUpdateDate()).isEqualTo(DEFAULT_LAST_UPDATE_DATE);
        assertThat(testClientActivityDetails.getLastUpatedBy()).isEqualTo(DEFAULT_LAST_UPATED_BY);
        assertThat(testClientActivityDetails.getOrgId()).isEqualTo(DEFAULT_ORG_ID);
    }

    @Test
    @Transactional
    public void createClientActivityDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clientActivityDetailsRepository.findAll().size();

        // Create the ClientActivityDetails with an existing ID
        clientActivityDetails.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClientActivityDetailsMockMvc.perform(post("/api/client-activity-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientActivityDetails)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ClientActivityDetails> clientActivityDetailsList = clientActivityDetailsRepository.findAll();
        assertThat(clientActivityDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllClientActivityDetails() throws Exception {
        // Initialize the database
        clientActivityDetailsRepository.saveAndFlush(clientActivityDetails);

        // Get all the clientActivityDetailsList
        restClientActivityDetailsMockMvc.perform(get("/api/client-activity-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clientActivityDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].dueDate").value(hasItem(DEFAULT_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].internalDueDate").value(hasItem(DEFAULT_INTERNAL_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())))
            .andExpect(jsonPath("$.[*].workStatus").value(hasItem(DEFAULT_WORK_STATUS.toString())))
            .andExpect(jsonPath("$.[*].monthYear").value(hasItem(DEFAULT_MONTH_YEAR.toString())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(sameInstant(DEFAULT_CREATION_DATE))))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].lastUpdateDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATE_DATE))))
            .andExpect(jsonPath("$.[*].lastUpatedBy").value(hasItem(DEFAULT_LAST_UPATED_BY.toString())))
            .andExpect(jsonPath("$.[*].orgId").value(hasItem(DEFAULT_ORG_ID.intValue())));
    }

    @Test
    @Transactional
    public void getClientActivityDetails() throws Exception {
        // Initialize the database
        clientActivityDetailsRepository.saveAndFlush(clientActivityDetails);

        // Get the clientActivityDetails
        restClientActivityDetailsMockMvc.perform(get("/api/client-activity-details/{id}", clientActivityDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(clientActivityDetails.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.dueDate").value(DEFAULT_DUE_DATE.toString()))
            .andExpect(jsonPath("$.internalDueDate").value(DEFAULT_INTERNAL_DUE_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS.toString()))
            .andExpect(jsonPath("$.workStatus").value(DEFAULT_WORK_STATUS.toString()))
            .andExpect(jsonPath("$.monthYear").value(DEFAULT_MONTH_YEAR.toString()))
            .andExpect(jsonPath("$.creationDate").value(sameInstant(DEFAULT_CREATION_DATE)))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.lastUpdateDate").value(sameInstant(DEFAULT_LAST_UPDATE_DATE)))
            .andExpect(jsonPath("$.lastUpatedBy").value(DEFAULT_LAST_UPATED_BY.toString()))
            .andExpect(jsonPath("$.orgId").value(DEFAULT_ORG_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingClientActivityDetails() throws Exception {
        // Get the clientActivityDetails
        restClientActivityDetailsMockMvc.perform(get("/api/client-activity-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClientActivityDetails() throws Exception {
        // Initialize the database
        clientActivityDetailsRepository.saveAndFlush(clientActivityDetails);
        int databaseSizeBeforeUpdate = clientActivityDetailsRepository.findAll().size();

        // Update the clientActivityDetails
        ClientActivityDetails updatedClientActivityDetails = clientActivityDetailsRepository.findOne(clientActivityDetails.getId());
        updatedClientActivityDetails
            .name(UPDATED_NAME)
            .dueDate(UPDATED_DUE_DATE)
            .internalDueDate(UPDATED_INTERNAL_DUE_DATE)
            .status(UPDATED_STATUS)
            .comments(UPDATED_COMMENTS)
            .workStatus(UPDATED_WORK_STATUS)
            .monthYear(UPDATED_MONTH_YEAR)
            .creationDate(UPDATED_CREATION_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .lastUpdateDate(UPDATED_LAST_UPDATE_DATE)
            .lastUpatedBy(UPDATED_LAST_UPATED_BY)
            .orgId(UPDATED_ORG_ID);

        restClientActivityDetailsMockMvc.perform(put("/api/client-activity-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedClientActivityDetails)))
            .andExpect(status().isOk());

        // Validate the ClientActivityDetails in the database
        List<ClientActivityDetails> clientActivityDetailsList = clientActivityDetailsRepository.findAll();
        assertThat(clientActivityDetailsList).hasSize(databaseSizeBeforeUpdate);
        ClientActivityDetails testClientActivityDetails = clientActivityDetailsList.get(clientActivityDetailsList.size() - 1);
        assertThat(testClientActivityDetails.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testClientActivityDetails.getDueDate()).isEqualTo(UPDATED_DUE_DATE);
        assertThat(testClientActivityDetails.getInternalDueDate()).isEqualTo(UPDATED_INTERNAL_DUE_DATE);
        assertThat(testClientActivityDetails.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testClientActivityDetails.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testClientActivityDetails.getWorkStatus()).isEqualTo(UPDATED_WORK_STATUS);
        assertThat(testClientActivityDetails.getMonthYear()).isEqualTo(UPDATED_MONTH_YEAR);
        assertThat(testClientActivityDetails.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testClientActivityDetails.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testClientActivityDetails.getLastUpdateDate()).isEqualTo(UPDATED_LAST_UPDATE_DATE);
        assertThat(testClientActivityDetails.getLastUpatedBy()).isEqualTo(UPDATED_LAST_UPATED_BY);
        assertThat(testClientActivityDetails.getOrgId()).isEqualTo(UPDATED_ORG_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingClientActivityDetails() throws Exception {
        int databaseSizeBeforeUpdate = clientActivityDetailsRepository.findAll().size();

        // Create the ClientActivityDetails

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restClientActivityDetailsMockMvc.perform(put("/api/client-activity-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientActivityDetails)))
            .andExpect(status().isCreated());

        // Validate the ClientActivityDetails in the database
        List<ClientActivityDetails> clientActivityDetailsList = clientActivityDetailsRepository.findAll();
        assertThat(clientActivityDetailsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteClientActivityDetails() throws Exception {
        // Initialize the database
        clientActivityDetailsRepository.saveAndFlush(clientActivityDetails);
        int databaseSizeBeforeDelete = clientActivityDetailsRepository.findAll().size();

        // Get the clientActivityDetails
        restClientActivityDetailsMockMvc.perform(delete("/api/client-activity-details/{id}", clientActivityDetails.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ClientActivityDetails> clientActivityDetailsList = clientActivityDetailsRepository.findAll();
        assertThat(clientActivityDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClientActivityDetails.class);
    }
}
