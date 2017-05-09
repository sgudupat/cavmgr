package com.snithik.cavmgr.app.web.rest;

import com.snithik.cavmgr.app.CavmgrApp;

import com.snithik.cavmgr.app.domain.TripleSetConfig;
import com.snithik.cavmgr.app.repository.TripleSetConfigRepository;
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
 * Test class for the TripleSetConfigResource REST controller.
 *
 * @see TripleSetConfigResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CavmgrApp.class)
public class TripleSetConfigResourceIntTest {

    private static final String DEFAULT_CONTROL = "AAAAAAAAAA";
    private static final String UPDATED_CONTROL = "BBBBBBBBBB";

    private static final String DEFAULT_PARENT = "AAAAAAAAAA";
    private static final String UPDATED_PARENT = "BBBBBBBBBB";

    private static final String DEFAULT_CHILD = "AAAAAAAAAA";
    private static final String UPDATED_CHILD = "BBBBBBBBBB";

    private static final String DEFAULT_CONFIG = "AAAAAAAAAA";
    private static final String UPDATED_CONFIG = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATION_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATION_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_LAST_UPDATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_UPDATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_LAST_UPATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_UPATED_BY = "BBBBBBBBBB";

    @Autowired
    private TripleSetConfigRepository tripleSetConfigRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTripleSetConfigMockMvc;

    private TripleSetConfig tripleSetConfig;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TripleSetConfigResource tripleSetConfigResource = new TripleSetConfigResource(tripleSetConfigRepository);
        this.restTripleSetConfigMockMvc = MockMvcBuilders.standaloneSetup(tripleSetConfigResource)
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
    public static TripleSetConfig createEntity(EntityManager em) {
        TripleSetConfig tripleSetConfig = new TripleSetConfig()
            .control(DEFAULT_CONTROL)
            .parent(DEFAULT_PARENT)
            .child(DEFAULT_CHILD)
            .config(DEFAULT_CONFIG)
            .creationDate(DEFAULT_CREATION_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .lastUpdateDate(DEFAULT_LAST_UPDATE_DATE)
            .lastUpatedBy(DEFAULT_LAST_UPATED_BY);
        return tripleSetConfig;
    }

    @Before
    public void initTest() {
        tripleSetConfig = createEntity(em);
    }

    @Test
    @Transactional
    public void createTripleSetConfig() throws Exception {
        int databaseSizeBeforeCreate = tripleSetConfigRepository.findAll().size();

        // Create the TripleSetConfig
        restTripleSetConfigMockMvc.perform(post("/api/triple-set-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tripleSetConfig)))
            .andExpect(status().isCreated());

        // Validate the TripleSetConfig in the database
        List<TripleSetConfig> tripleSetConfigList = tripleSetConfigRepository.findAll();
        assertThat(tripleSetConfigList).hasSize(databaseSizeBeforeCreate + 1);
        TripleSetConfig testTripleSetConfig = tripleSetConfigList.get(tripleSetConfigList.size() - 1);
        assertThat(testTripleSetConfig.getControl()).isEqualTo(DEFAULT_CONTROL);
        assertThat(testTripleSetConfig.getParent()).isEqualTo(DEFAULT_PARENT);
        assertThat(testTripleSetConfig.getChild()).isEqualTo(DEFAULT_CHILD);
        assertThat(testTripleSetConfig.getConfig()).isEqualTo(DEFAULT_CONFIG);
        assertThat(testTripleSetConfig.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testTripleSetConfig.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testTripleSetConfig.getLastUpdateDate()).isEqualTo(DEFAULT_LAST_UPDATE_DATE);
        assertThat(testTripleSetConfig.getLastUpatedBy()).isEqualTo(DEFAULT_LAST_UPATED_BY);
    }

    @Test
    @Transactional
    public void createTripleSetConfigWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tripleSetConfigRepository.findAll().size();

        // Create the TripleSetConfig with an existing ID
        tripleSetConfig.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTripleSetConfigMockMvc.perform(post("/api/triple-set-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tripleSetConfig)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TripleSetConfig> tripleSetConfigList = tripleSetConfigRepository.findAll();
        assertThat(tripleSetConfigList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTripleSetConfigs() throws Exception {
        // Initialize the database
        tripleSetConfigRepository.saveAndFlush(tripleSetConfig);

        // Get all the tripleSetConfigList
        restTripleSetConfigMockMvc.perform(get("/api/triple-set-configs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tripleSetConfig.getId().intValue())))
            .andExpect(jsonPath("$.[*].control").value(hasItem(DEFAULT_CONTROL.toString())))
            .andExpect(jsonPath("$.[*].parent").value(hasItem(DEFAULT_PARENT.toString())))
            .andExpect(jsonPath("$.[*].child").value(hasItem(DEFAULT_CHILD.toString())))
            .andExpect(jsonPath("$.[*].config").value(hasItem(DEFAULT_CONFIG.toString())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(sameInstant(DEFAULT_CREATION_DATE))))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].lastUpdateDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATE_DATE))))
            .andExpect(jsonPath("$.[*].lastUpatedBy").value(hasItem(DEFAULT_LAST_UPATED_BY.toString())));
    }

    @Test
    @Transactional
    public void getTripleSetConfig() throws Exception {
        // Initialize the database
        tripleSetConfigRepository.saveAndFlush(tripleSetConfig);

        // Get the tripleSetConfig
        restTripleSetConfigMockMvc.perform(get("/api/triple-set-configs/{id}", tripleSetConfig.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tripleSetConfig.getId().intValue()))
            .andExpect(jsonPath("$.control").value(DEFAULT_CONTROL.toString()))
            .andExpect(jsonPath("$.parent").value(DEFAULT_PARENT.toString()))
            .andExpect(jsonPath("$.child").value(DEFAULT_CHILD.toString()))
            .andExpect(jsonPath("$.config").value(DEFAULT_CONFIG.toString()))
            .andExpect(jsonPath("$.creationDate").value(sameInstant(DEFAULT_CREATION_DATE)))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.lastUpdateDate").value(sameInstant(DEFAULT_LAST_UPDATE_DATE)))
            .andExpect(jsonPath("$.lastUpatedBy").value(DEFAULT_LAST_UPATED_BY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTripleSetConfig() throws Exception {
        // Get the tripleSetConfig
        restTripleSetConfigMockMvc.perform(get("/api/triple-set-configs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTripleSetConfig() throws Exception {
        // Initialize the database
        tripleSetConfigRepository.saveAndFlush(tripleSetConfig);
        int databaseSizeBeforeUpdate = tripleSetConfigRepository.findAll().size();

        // Update the tripleSetConfig
        TripleSetConfig updatedTripleSetConfig = tripleSetConfigRepository.findOne(tripleSetConfig.getId());
        updatedTripleSetConfig
            .control(UPDATED_CONTROL)
            .parent(UPDATED_PARENT)
            .child(UPDATED_CHILD)
            .config(UPDATED_CONFIG)
            .creationDate(UPDATED_CREATION_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .lastUpdateDate(UPDATED_LAST_UPDATE_DATE)
            .lastUpatedBy(UPDATED_LAST_UPATED_BY);

        restTripleSetConfigMockMvc.perform(put("/api/triple-set-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTripleSetConfig)))
            .andExpect(status().isOk());

        // Validate the TripleSetConfig in the database
        List<TripleSetConfig> tripleSetConfigList = tripleSetConfigRepository.findAll();
        assertThat(tripleSetConfigList).hasSize(databaseSizeBeforeUpdate);
        TripleSetConfig testTripleSetConfig = tripleSetConfigList.get(tripleSetConfigList.size() - 1);
        assertThat(testTripleSetConfig.getControl()).isEqualTo(UPDATED_CONTROL);
        assertThat(testTripleSetConfig.getParent()).isEqualTo(UPDATED_PARENT);
        assertThat(testTripleSetConfig.getChild()).isEqualTo(UPDATED_CHILD);
        assertThat(testTripleSetConfig.getConfig()).isEqualTo(UPDATED_CONFIG);
        assertThat(testTripleSetConfig.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testTripleSetConfig.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testTripleSetConfig.getLastUpdateDate()).isEqualTo(UPDATED_LAST_UPDATE_DATE);
        assertThat(testTripleSetConfig.getLastUpatedBy()).isEqualTo(UPDATED_LAST_UPATED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingTripleSetConfig() throws Exception {
        int databaseSizeBeforeUpdate = tripleSetConfigRepository.findAll().size();

        // Create the TripleSetConfig

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTripleSetConfigMockMvc.perform(put("/api/triple-set-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tripleSetConfig)))
            .andExpect(status().isCreated());

        // Validate the TripleSetConfig in the database
        List<TripleSetConfig> tripleSetConfigList = tripleSetConfigRepository.findAll();
        assertThat(tripleSetConfigList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTripleSetConfig() throws Exception {
        // Initialize the database
        tripleSetConfigRepository.saveAndFlush(tripleSetConfig);
        int databaseSizeBeforeDelete = tripleSetConfigRepository.findAll().size();

        // Get the tripleSetConfig
        restTripleSetConfigMockMvc.perform(delete("/api/triple-set-configs/{id}", tripleSetConfig.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TripleSetConfig> tripleSetConfigList = tripleSetConfigRepository.findAll();
        assertThat(tripleSetConfigList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TripleSetConfig.class);
    }
}
