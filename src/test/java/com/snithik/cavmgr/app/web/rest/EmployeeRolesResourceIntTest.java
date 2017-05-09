package com.snithik.cavmgr.app.web.rest;

import com.snithik.cavmgr.app.CavmgrApp;

import com.snithik.cavmgr.app.domain.EmployeeRoles;
import com.snithik.cavmgr.app.repository.EmployeeRolesRepository;
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
 * Test class for the EmployeeRolesResource REST controller.
 *
 * @see EmployeeRolesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CavmgrApp.class)
public class EmployeeRolesResourceIntTest {

    private static final String DEFAULT_ROLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ROLE_NAME = "BBBBBBBBBB";

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
    private EmployeeRolesRepository employeeRolesRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEmployeeRolesMockMvc;

    private EmployeeRoles employeeRoles;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EmployeeRolesResource employeeRolesResource = new EmployeeRolesResource(employeeRolesRepository);
        this.restEmployeeRolesMockMvc = MockMvcBuilders.standaloneSetup(employeeRolesResource)
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
    public static EmployeeRoles createEntity(EntityManager em) {
        EmployeeRoles employeeRoles = new EmployeeRoles()
            .roleName(DEFAULT_ROLE_NAME)
            .creationDate(DEFAULT_CREATION_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .lastUpdateDate(DEFAULT_LAST_UPDATE_DATE)
            .lastUpatedBy(DEFAULT_LAST_UPATED_BY)
            .orgId(DEFAULT_ORG_ID);
        return employeeRoles;
    }

    @Before
    public void initTest() {
        employeeRoles = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmployeeRoles() throws Exception {
        int databaseSizeBeforeCreate = employeeRolesRepository.findAll().size();

        // Create the EmployeeRoles
        restEmployeeRolesMockMvc.perform(post("/api/employee-roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeRoles)))
            .andExpect(status().isCreated());

        // Validate the EmployeeRoles in the database
        List<EmployeeRoles> employeeRolesList = employeeRolesRepository.findAll();
        assertThat(employeeRolesList).hasSize(databaseSizeBeforeCreate + 1);
        EmployeeRoles testEmployeeRoles = employeeRolesList.get(employeeRolesList.size() - 1);
        assertThat(testEmployeeRoles.getRoleName()).isEqualTo(DEFAULT_ROLE_NAME);
        assertThat(testEmployeeRoles.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testEmployeeRoles.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testEmployeeRoles.getLastUpdateDate()).isEqualTo(DEFAULT_LAST_UPDATE_DATE);
        assertThat(testEmployeeRoles.getLastUpatedBy()).isEqualTo(DEFAULT_LAST_UPATED_BY);
        assertThat(testEmployeeRoles.getOrgId()).isEqualTo(DEFAULT_ORG_ID);
    }

    @Test
    @Transactional
    public void createEmployeeRolesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = employeeRolesRepository.findAll().size();

        // Create the EmployeeRoles with an existing ID
        employeeRoles.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeRolesMockMvc.perform(post("/api/employee-roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeRoles)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<EmployeeRoles> employeeRolesList = employeeRolesRepository.findAll();
        assertThat(employeeRolesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEmployeeRoles() throws Exception {
        // Initialize the database
        employeeRolesRepository.saveAndFlush(employeeRoles);

        // Get all the employeeRolesList
        restEmployeeRolesMockMvc.perform(get("/api/employee-roles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeRoles.getId().intValue())))
            .andExpect(jsonPath("$.[*].roleName").value(hasItem(DEFAULT_ROLE_NAME.toString())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(sameInstant(DEFAULT_CREATION_DATE))))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].lastUpdateDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATE_DATE))))
            .andExpect(jsonPath("$.[*].lastUpatedBy").value(hasItem(DEFAULT_LAST_UPATED_BY.toString())))
            .andExpect(jsonPath("$.[*].orgId").value(hasItem(DEFAULT_ORG_ID.intValue())));
    }

    @Test
    @Transactional
    public void getEmployeeRoles() throws Exception {
        // Initialize the database
        employeeRolesRepository.saveAndFlush(employeeRoles);

        // Get the employeeRoles
        restEmployeeRolesMockMvc.perform(get("/api/employee-roles/{id}", employeeRoles.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(employeeRoles.getId().intValue()))
            .andExpect(jsonPath("$.roleName").value(DEFAULT_ROLE_NAME.toString()))
            .andExpect(jsonPath("$.creationDate").value(sameInstant(DEFAULT_CREATION_DATE)))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.lastUpdateDate").value(sameInstant(DEFAULT_LAST_UPDATE_DATE)))
            .andExpect(jsonPath("$.lastUpatedBy").value(DEFAULT_LAST_UPATED_BY.toString()))
            .andExpect(jsonPath("$.orgId").value(DEFAULT_ORG_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingEmployeeRoles() throws Exception {
        // Get the employeeRoles
        restEmployeeRolesMockMvc.perform(get("/api/employee-roles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmployeeRoles() throws Exception {
        // Initialize the database
        employeeRolesRepository.saveAndFlush(employeeRoles);
        int databaseSizeBeforeUpdate = employeeRolesRepository.findAll().size();

        // Update the employeeRoles
        EmployeeRoles updatedEmployeeRoles = employeeRolesRepository.findOne(employeeRoles.getId());
        updatedEmployeeRoles
            .roleName(UPDATED_ROLE_NAME)
            .creationDate(UPDATED_CREATION_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .lastUpdateDate(UPDATED_LAST_UPDATE_DATE)
            .lastUpatedBy(UPDATED_LAST_UPATED_BY)
            .orgId(UPDATED_ORG_ID);

        restEmployeeRolesMockMvc.perform(put("/api/employee-roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEmployeeRoles)))
            .andExpect(status().isOk());

        // Validate the EmployeeRoles in the database
        List<EmployeeRoles> employeeRolesList = employeeRolesRepository.findAll();
        assertThat(employeeRolesList).hasSize(databaseSizeBeforeUpdate);
        EmployeeRoles testEmployeeRoles = employeeRolesList.get(employeeRolesList.size() - 1);
        assertThat(testEmployeeRoles.getRoleName()).isEqualTo(UPDATED_ROLE_NAME);
        assertThat(testEmployeeRoles.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testEmployeeRoles.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testEmployeeRoles.getLastUpdateDate()).isEqualTo(UPDATED_LAST_UPDATE_DATE);
        assertThat(testEmployeeRoles.getLastUpatedBy()).isEqualTo(UPDATED_LAST_UPATED_BY);
        assertThat(testEmployeeRoles.getOrgId()).isEqualTo(UPDATED_ORG_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingEmployeeRoles() throws Exception {
        int databaseSizeBeforeUpdate = employeeRolesRepository.findAll().size();

        // Create the EmployeeRoles

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEmployeeRolesMockMvc.perform(put("/api/employee-roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeRoles)))
            .andExpect(status().isCreated());

        // Validate the EmployeeRoles in the database
        List<EmployeeRoles> employeeRolesList = employeeRolesRepository.findAll();
        assertThat(employeeRolesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEmployeeRoles() throws Exception {
        // Initialize the database
        employeeRolesRepository.saveAndFlush(employeeRoles);
        int databaseSizeBeforeDelete = employeeRolesRepository.findAll().size();

        // Get the employeeRoles
        restEmployeeRolesMockMvc.perform(delete("/api/employee-roles/{id}", employeeRoles.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EmployeeRoles> employeeRolesList = employeeRolesRepository.findAll();
        assertThat(employeeRolesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeRoles.class);
    }
}
