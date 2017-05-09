package com.snithik.cavmgr.app.web.rest;

import com.snithik.cavmgr.app.CavmgrApp;

import com.snithik.cavmgr.app.domain.Clients;
import com.snithik.cavmgr.app.repository.ClientsRepository;
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
 * Test class for the ClientsResource REST controller.
 *
 * @see ClientsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CavmgrApp.class)
public class ClientsResourceIntTest {

    private static final String DEFAULT_ENTITY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ENTITY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_PERSON = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_PERSON = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_ADDRESS = "BBBBBBBBBB";

    private static final Long DEFAULT_OFFICE_PHONE = 1L;
    private static final Long UPDATED_OFFICE_PHONE = 2L;

    private static final Long DEFAULT_MOBILE_PHONE = 1L;
    private static final Long UPDATED_MOBILE_PHONE = 2L;

    private static final String DEFAULT_BILLING_PERIOD = "AAAAAAAAAA";
    private static final String UPDATED_BILLING_PERIOD = "BBBBBBBBBB";

    private static final String DEFAULT_PAN_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PAN_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_TAN_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_TAN_NUMBER = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE_OF_INCORP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_OF_INCORP = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_GRADE = "AAAAAAAAAA";
    private static final String UPDATED_GRADE = "BBBBBBBBBB";

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
    private ClientsRepository clientsRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restClientsMockMvc;

    private Clients clients;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ClientsResource clientsResource = new ClientsResource(clientsRepository);
        this.restClientsMockMvc = MockMvcBuilders.standaloneSetup(clientsResource)
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
    public static Clients createEntity(EntityManager em) {
        Clients clients = new Clients()
            .entityName(DEFAULT_ENTITY_NAME)
            .contactPerson(DEFAULT_CONTACT_PERSON)
            .emailAddress(DEFAULT_EMAIL_ADDRESS)
            .officePhone(DEFAULT_OFFICE_PHONE)
            .mobilePhone(DEFAULT_MOBILE_PHONE)
            .billingPeriod(DEFAULT_BILLING_PERIOD)
            .panNumber(DEFAULT_PAN_NUMBER)
            .tanNumber(DEFAULT_TAN_NUMBER)
            .dateOfIncorp(DEFAULT_DATE_OF_INCORP)
            .grade(DEFAULT_GRADE)
            .creationDate(DEFAULT_CREATION_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .lastUpdateDate(DEFAULT_LAST_UPDATE_DATE)
            .lastUpatedBy(DEFAULT_LAST_UPATED_BY)
            .orgId(DEFAULT_ORG_ID);
        return clients;
    }

    @Before
    public void initTest() {
        clients = createEntity(em);
    }

    @Test
    @Transactional
    public void createClients() throws Exception {
        int databaseSizeBeforeCreate = clientsRepository.findAll().size();

        // Create the Clients
        restClientsMockMvc.perform(post("/api/clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clients)))
            .andExpect(status().isCreated());

        // Validate the Clients in the database
        List<Clients> clientsList = clientsRepository.findAll();
        assertThat(clientsList).hasSize(databaseSizeBeforeCreate + 1);
        Clients testClients = clientsList.get(clientsList.size() - 1);
        assertThat(testClients.getEntityName()).isEqualTo(DEFAULT_ENTITY_NAME);
        assertThat(testClients.getContactPerson()).isEqualTo(DEFAULT_CONTACT_PERSON);
        assertThat(testClients.getEmailAddress()).isEqualTo(DEFAULT_EMAIL_ADDRESS);
        assertThat(testClients.getOfficePhone()).isEqualTo(DEFAULT_OFFICE_PHONE);
        assertThat(testClients.getMobilePhone()).isEqualTo(DEFAULT_MOBILE_PHONE);
        assertThat(testClients.getBillingPeriod()).isEqualTo(DEFAULT_BILLING_PERIOD);
        assertThat(testClients.getPanNumber()).isEqualTo(DEFAULT_PAN_NUMBER);
        assertThat(testClients.getTanNumber()).isEqualTo(DEFAULT_TAN_NUMBER);
        assertThat(testClients.getDateOfIncorp()).isEqualTo(DEFAULT_DATE_OF_INCORP);
        assertThat(testClients.getGrade()).isEqualTo(DEFAULT_GRADE);
        assertThat(testClients.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testClients.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testClients.getLastUpdateDate()).isEqualTo(DEFAULT_LAST_UPDATE_DATE);
        assertThat(testClients.getLastUpatedBy()).isEqualTo(DEFAULT_LAST_UPATED_BY);
        assertThat(testClients.getOrgId()).isEqualTo(DEFAULT_ORG_ID);
    }

    @Test
    @Transactional
    public void createClientsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clientsRepository.findAll().size();

        // Create the Clients with an existing ID
        clients.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClientsMockMvc.perform(post("/api/clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clients)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Clients> clientsList = clientsRepository.findAll();
        assertThat(clientsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllClients() throws Exception {
        // Initialize the database
        clientsRepository.saveAndFlush(clients);

        // Get all the clientsList
        restClientsMockMvc.perform(get("/api/clients?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clients.getId().intValue())))
            .andExpect(jsonPath("$.[*].entityName").value(hasItem(DEFAULT_ENTITY_NAME.toString())))
            .andExpect(jsonPath("$.[*].contactPerson").value(hasItem(DEFAULT_CONTACT_PERSON.toString())))
            .andExpect(jsonPath("$.[*].emailAddress").value(hasItem(DEFAULT_EMAIL_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].officePhone").value(hasItem(DEFAULT_OFFICE_PHONE.intValue())))
            .andExpect(jsonPath("$.[*].mobilePhone").value(hasItem(DEFAULT_MOBILE_PHONE.intValue())))
            .andExpect(jsonPath("$.[*].billingPeriod").value(hasItem(DEFAULT_BILLING_PERIOD.toString())))
            .andExpect(jsonPath("$.[*].panNumber").value(hasItem(DEFAULT_PAN_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].tanNumber").value(hasItem(DEFAULT_TAN_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].dateOfIncorp").value(hasItem(sameInstant(DEFAULT_DATE_OF_INCORP))))
            .andExpect(jsonPath("$.[*].grade").value(hasItem(DEFAULT_GRADE.toString())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(sameInstant(DEFAULT_CREATION_DATE))))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].lastUpdateDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATE_DATE))))
            .andExpect(jsonPath("$.[*].lastUpatedBy").value(hasItem(DEFAULT_LAST_UPATED_BY.toString())))
            .andExpect(jsonPath("$.[*].orgId").value(hasItem(DEFAULT_ORG_ID.intValue())));
    }

    @Test
    @Transactional
    public void getClients() throws Exception {
        // Initialize the database
        clientsRepository.saveAndFlush(clients);

        // Get the clients
        restClientsMockMvc.perform(get("/api/clients/{id}", clients.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(clients.getId().intValue()))
            .andExpect(jsonPath("$.entityName").value(DEFAULT_ENTITY_NAME.toString()))
            .andExpect(jsonPath("$.contactPerson").value(DEFAULT_CONTACT_PERSON.toString()))
            .andExpect(jsonPath("$.emailAddress").value(DEFAULT_EMAIL_ADDRESS.toString()))
            .andExpect(jsonPath("$.officePhone").value(DEFAULT_OFFICE_PHONE.intValue()))
            .andExpect(jsonPath("$.mobilePhone").value(DEFAULT_MOBILE_PHONE.intValue()))
            .andExpect(jsonPath("$.billingPeriod").value(DEFAULT_BILLING_PERIOD.toString()))
            .andExpect(jsonPath("$.panNumber").value(DEFAULT_PAN_NUMBER.toString()))
            .andExpect(jsonPath("$.tanNumber").value(DEFAULT_TAN_NUMBER.toString()))
            .andExpect(jsonPath("$.dateOfIncorp").value(sameInstant(DEFAULT_DATE_OF_INCORP)))
            .andExpect(jsonPath("$.grade").value(DEFAULT_GRADE.toString()))
            .andExpect(jsonPath("$.creationDate").value(sameInstant(DEFAULT_CREATION_DATE)))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.lastUpdateDate").value(sameInstant(DEFAULT_LAST_UPDATE_DATE)))
            .andExpect(jsonPath("$.lastUpatedBy").value(DEFAULT_LAST_UPATED_BY.toString()))
            .andExpect(jsonPath("$.orgId").value(DEFAULT_ORG_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingClients() throws Exception {
        // Get the clients
        restClientsMockMvc.perform(get("/api/clients/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClients() throws Exception {
        // Initialize the database
        clientsRepository.saveAndFlush(clients);
        int databaseSizeBeforeUpdate = clientsRepository.findAll().size();

        // Update the clients
        Clients updatedClients = clientsRepository.findOne(clients.getId());
        updatedClients
            .entityName(UPDATED_ENTITY_NAME)
            .contactPerson(UPDATED_CONTACT_PERSON)
            .emailAddress(UPDATED_EMAIL_ADDRESS)
            .officePhone(UPDATED_OFFICE_PHONE)
            .mobilePhone(UPDATED_MOBILE_PHONE)
            .billingPeriod(UPDATED_BILLING_PERIOD)
            .panNumber(UPDATED_PAN_NUMBER)
            .tanNumber(UPDATED_TAN_NUMBER)
            .dateOfIncorp(UPDATED_DATE_OF_INCORP)
            .grade(UPDATED_GRADE)
            .creationDate(UPDATED_CREATION_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .lastUpdateDate(UPDATED_LAST_UPDATE_DATE)
            .lastUpatedBy(UPDATED_LAST_UPATED_BY)
            .orgId(UPDATED_ORG_ID);

        restClientsMockMvc.perform(put("/api/clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedClients)))
            .andExpect(status().isOk());

        // Validate the Clients in the database
        List<Clients> clientsList = clientsRepository.findAll();
        assertThat(clientsList).hasSize(databaseSizeBeforeUpdate);
        Clients testClients = clientsList.get(clientsList.size() - 1);
        assertThat(testClients.getEntityName()).isEqualTo(UPDATED_ENTITY_NAME);
        assertThat(testClients.getContactPerson()).isEqualTo(UPDATED_CONTACT_PERSON);
        assertThat(testClients.getEmailAddress()).isEqualTo(UPDATED_EMAIL_ADDRESS);
        assertThat(testClients.getOfficePhone()).isEqualTo(UPDATED_OFFICE_PHONE);
        assertThat(testClients.getMobilePhone()).isEqualTo(UPDATED_MOBILE_PHONE);
        assertThat(testClients.getBillingPeriod()).isEqualTo(UPDATED_BILLING_PERIOD);
        assertThat(testClients.getPanNumber()).isEqualTo(UPDATED_PAN_NUMBER);
        assertThat(testClients.getTanNumber()).isEqualTo(UPDATED_TAN_NUMBER);
        assertThat(testClients.getDateOfIncorp()).isEqualTo(UPDATED_DATE_OF_INCORP);
        assertThat(testClients.getGrade()).isEqualTo(UPDATED_GRADE);
        assertThat(testClients.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testClients.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testClients.getLastUpdateDate()).isEqualTo(UPDATED_LAST_UPDATE_DATE);
        assertThat(testClients.getLastUpatedBy()).isEqualTo(UPDATED_LAST_UPATED_BY);
        assertThat(testClients.getOrgId()).isEqualTo(UPDATED_ORG_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingClients() throws Exception {
        int databaseSizeBeforeUpdate = clientsRepository.findAll().size();

        // Create the Clients

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restClientsMockMvc.perform(put("/api/clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clients)))
            .andExpect(status().isCreated());

        // Validate the Clients in the database
        List<Clients> clientsList = clientsRepository.findAll();
        assertThat(clientsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteClients() throws Exception {
        // Initialize the database
        clientsRepository.saveAndFlush(clients);
        int databaseSizeBeforeDelete = clientsRepository.findAll().size();

        // Get the clients
        restClientsMockMvc.perform(delete("/api/clients/{id}", clients.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Clients> clientsList = clientsRepository.findAll();
        assertThat(clientsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Clients.class);
    }
}
