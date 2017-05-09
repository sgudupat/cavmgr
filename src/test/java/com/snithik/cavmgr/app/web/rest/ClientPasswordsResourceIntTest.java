package com.snithik.cavmgr.app.web.rest;

import com.snithik.cavmgr.app.CavmgrApp;

import com.snithik.cavmgr.app.domain.ClientPasswords;
import com.snithik.cavmgr.app.repository.ClientPasswordsRepository;
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
 * Test class for the ClientPasswordsResource REST controller.
 *
 * @see ClientPasswordsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CavmgrApp.class)
public class ClientPasswordsResourceIntTest {

    private static final String DEFAULT_ACCOUNT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_USER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_USER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

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
    private ClientPasswordsRepository clientPasswordsRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restClientPasswordsMockMvc;

    private ClientPasswords clientPasswords;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ClientPasswordsResource clientPasswordsResource = new ClientPasswordsResource(clientPasswordsRepository);
        this.restClientPasswordsMockMvc = MockMvcBuilders.standaloneSetup(clientPasswordsResource)
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
    public static ClientPasswords createEntity(EntityManager em) {
        ClientPasswords clientPasswords = new ClientPasswords()
            .accountType(DEFAULT_ACCOUNT_TYPE)
            .userName(DEFAULT_USER_NAME)
            .password(DEFAULT_PASSWORD)
            .creationDate(DEFAULT_CREATION_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .lastUpdateDate(DEFAULT_LAST_UPDATE_DATE)
            .lastUpatedBy(DEFAULT_LAST_UPATED_BY)
            .orgId(DEFAULT_ORG_ID);
        return clientPasswords;
    }

    @Before
    public void initTest() {
        clientPasswords = createEntity(em);
    }

    @Test
    @Transactional
    public void createClientPasswords() throws Exception {
        int databaseSizeBeforeCreate = clientPasswordsRepository.findAll().size();

        // Create the ClientPasswords
        restClientPasswordsMockMvc.perform(post("/api/client-passwords")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientPasswords)))
            .andExpect(status().isCreated());

        // Validate the ClientPasswords in the database
        List<ClientPasswords> clientPasswordsList = clientPasswordsRepository.findAll();
        assertThat(clientPasswordsList).hasSize(databaseSizeBeforeCreate + 1);
        ClientPasswords testClientPasswords = clientPasswordsList.get(clientPasswordsList.size() - 1);
        assertThat(testClientPasswords.getAccountType()).isEqualTo(DEFAULT_ACCOUNT_TYPE);
        assertThat(testClientPasswords.getUserName()).isEqualTo(DEFAULT_USER_NAME);
        assertThat(testClientPasswords.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testClientPasswords.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testClientPasswords.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testClientPasswords.getLastUpdateDate()).isEqualTo(DEFAULT_LAST_UPDATE_DATE);
        assertThat(testClientPasswords.getLastUpatedBy()).isEqualTo(DEFAULT_LAST_UPATED_BY);
        assertThat(testClientPasswords.getOrgId()).isEqualTo(DEFAULT_ORG_ID);
    }

    @Test
    @Transactional
    public void createClientPasswordsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clientPasswordsRepository.findAll().size();

        // Create the ClientPasswords with an existing ID
        clientPasswords.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClientPasswordsMockMvc.perform(post("/api/client-passwords")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientPasswords)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ClientPasswords> clientPasswordsList = clientPasswordsRepository.findAll();
        assertThat(clientPasswordsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllClientPasswords() throws Exception {
        // Initialize the database
        clientPasswordsRepository.saveAndFlush(clientPasswords);

        // Get all the clientPasswordsList
        restClientPasswordsMockMvc.perform(get("/api/client-passwords?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clientPasswords.getId().intValue())))
            .andExpect(jsonPath("$.[*].accountType").value(hasItem(DEFAULT_ACCOUNT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME.toString())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(sameInstant(DEFAULT_CREATION_DATE))))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].lastUpdateDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATE_DATE))))
            .andExpect(jsonPath("$.[*].lastUpatedBy").value(hasItem(DEFAULT_LAST_UPATED_BY.toString())))
            .andExpect(jsonPath("$.[*].orgId").value(hasItem(DEFAULT_ORG_ID.intValue())));
    }

    @Test
    @Transactional
    public void getClientPasswords() throws Exception {
        // Initialize the database
        clientPasswordsRepository.saveAndFlush(clientPasswords);

        // Get the clientPasswords
        restClientPasswordsMockMvc.perform(get("/api/client-passwords/{id}", clientPasswords.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(clientPasswords.getId().intValue()))
            .andExpect(jsonPath("$.accountType").value(DEFAULT_ACCOUNT_TYPE.toString()))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME.toString()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD.toString()))
            .andExpect(jsonPath("$.creationDate").value(sameInstant(DEFAULT_CREATION_DATE)))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.lastUpdateDate").value(sameInstant(DEFAULT_LAST_UPDATE_DATE)))
            .andExpect(jsonPath("$.lastUpatedBy").value(DEFAULT_LAST_UPATED_BY.toString()))
            .andExpect(jsonPath("$.orgId").value(DEFAULT_ORG_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingClientPasswords() throws Exception {
        // Get the clientPasswords
        restClientPasswordsMockMvc.perform(get("/api/client-passwords/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClientPasswords() throws Exception {
        // Initialize the database
        clientPasswordsRepository.saveAndFlush(clientPasswords);
        int databaseSizeBeforeUpdate = clientPasswordsRepository.findAll().size();

        // Update the clientPasswords
        ClientPasswords updatedClientPasswords = clientPasswordsRepository.findOne(clientPasswords.getId());
        updatedClientPasswords
            .accountType(UPDATED_ACCOUNT_TYPE)
            .userName(UPDATED_USER_NAME)
            .password(UPDATED_PASSWORD)
            .creationDate(UPDATED_CREATION_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .lastUpdateDate(UPDATED_LAST_UPDATE_DATE)
            .lastUpatedBy(UPDATED_LAST_UPATED_BY)
            .orgId(UPDATED_ORG_ID);

        restClientPasswordsMockMvc.perform(put("/api/client-passwords")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedClientPasswords)))
            .andExpect(status().isOk());

        // Validate the ClientPasswords in the database
        List<ClientPasswords> clientPasswordsList = clientPasswordsRepository.findAll();
        assertThat(clientPasswordsList).hasSize(databaseSizeBeforeUpdate);
        ClientPasswords testClientPasswords = clientPasswordsList.get(clientPasswordsList.size() - 1);
        assertThat(testClientPasswords.getAccountType()).isEqualTo(UPDATED_ACCOUNT_TYPE);
        assertThat(testClientPasswords.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testClientPasswords.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testClientPasswords.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testClientPasswords.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testClientPasswords.getLastUpdateDate()).isEqualTo(UPDATED_LAST_UPDATE_DATE);
        assertThat(testClientPasswords.getLastUpatedBy()).isEqualTo(UPDATED_LAST_UPATED_BY);
        assertThat(testClientPasswords.getOrgId()).isEqualTo(UPDATED_ORG_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingClientPasswords() throws Exception {
        int databaseSizeBeforeUpdate = clientPasswordsRepository.findAll().size();

        // Create the ClientPasswords

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restClientPasswordsMockMvc.perform(put("/api/client-passwords")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientPasswords)))
            .andExpect(status().isCreated());

        // Validate the ClientPasswords in the database
        List<ClientPasswords> clientPasswordsList = clientPasswordsRepository.findAll();
        assertThat(clientPasswordsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteClientPasswords() throws Exception {
        // Initialize the database
        clientPasswordsRepository.saveAndFlush(clientPasswords);
        int databaseSizeBeforeDelete = clientPasswordsRepository.findAll().size();

        // Get the clientPasswords
        restClientPasswordsMockMvc.perform(delete("/api/client-passwords/{id}", clientPasswords.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ClientPasswords> clientPasswordsList = clientPasswordsRepository.findAll();
        assertThat(clientPasswordsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClientPasswords.class);
    }
}
