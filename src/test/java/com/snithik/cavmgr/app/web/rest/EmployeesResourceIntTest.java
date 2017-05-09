package com.snithik.cavmgr.app.web.rest;

import com.snithik.cavmgr.app.CavmgrApp;

import com.snithik.cavmgr.app.domain.Employees;
import com.snithik.cavmgr.app.repository.EmployeesRepository;
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
 * Test class for the EmployeesResource REST controller.
 *
 * @see EmployeesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CavmgrApp.class)
public class EmployeesResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_ADDRESS = "BBBBBBBBBB";

    private static final Long DEFAULT_MOBILE_PHONE = 1L;
    private static final Long UPDATED_MOBILE_PHONE = 2L;

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

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
    private EmployeesRepository employeesRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEmployeesMockMvc;

    private Employees employees;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EmployeesResource employeesResource = new EmployeesResource(employeesRepository);
        this.restEmployeesMockMvc = MockMvcBuilders.standaloneSetup(employeesResource)
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
    public static Employees createEntity(EntityManager em) {
        Employees employees = new Employees()
            .name(DEFAULT_NAME)
            .password(DEFAULT_PASSWORD)
            .emailAddress(DEFAULT_EMAIL_ADDRESS)
            .mobilePhone(DEFAULT_MOBILE_PHONE)
            .status(DEFAULT_STATUS)
            .creationDate(DEFAULT_CREATION_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .lastUpdateDate(DEFAULT_LAST_UPDATE_DATE)
            .lastUpatedBy(DEFAULT_LAST_UPATED_BY)
            .orgId(DEFAULT_ORG_ID);
        return employees;
    }

    @Before
    public void initTest() {
        employees = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmployees() throws Exception {
        int databaseSizeBeforeCreate = employeesRepository.findAll().size();

        // Create the Employees
        restEmployeesMockMvc.perform(post("/api/employees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employees)))
            .andExpect(status().isCreated());

        // Validate the Employees in the database
        List<Employees> employeesList = employeesRepository.findAll();
        assertThat(employeesList).hasSize(databaseSizeBeforeCreate + 1);
        Employees testEmployees = employeesList.get(employeesList.size() - 1);
        assertThat(testEmployees.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEmployees.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testEmployees.getEmailAddress()).isEqualTo(DEFAULT_EMAIL_ADDRESS);
        assertThat(testEmployees.getMobilePhone()).isEqualTo(DEFAULT_MOBILE_PHONE);
        assertThat(testEmployees.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testEmployees.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testEmployees.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testEmployees.getLastUpdateDate()).isEqualTo(DEFAULT_LAST_UPDATE_DATE);
        assertThat(testEmployees.getLastUpatedBy()).isEqualTo(DEFAULT_LAST_UPATED_BY);
        assertThat(testEmployees.getOrgId()).isEqualTo(DEFAULT_ORG_ID);
    }

    @Test
    @Transactional
    public void createEmployeesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = employeesRepository.findAll().size();

        // Create the Employees with an existing ID
        employees.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeesMockMvc.perform(post("/api/employees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employees)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Employees> employeesList = employeesRepository.findAll();
        assertThat(employeesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEmployees() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList
        restEmployeesMockMvc.perform(get("/api/employees?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employees.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())))
            .andExpect(jsonPath("$.[*].emailAddress").value(hasItem(DEFAULT_EMAIL_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].mobilePhone").value(hasItem(DEFAULT_MOBILE_PHONE.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(sameInstant(DEFAULT_CREATION_DATE))))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].lastUpdateDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATE_DATE))))
            .andExpect(jsonPath("$.[*].lastUpatedBy").value(hasItem(DEFAULT_LAST_UPATED_BY.toString())))
            .andExpect(jsonPath("$.[*].orgId").value(hasItem(DEFAULT_ORG_ID.intValue())));
    }

    @Test
    @Transactional
    public void getEmployees() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get the employees
        restEmployeesMockMvc.perform(get("/api/employees/{id}", employees.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(employees.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD.toString()))
            .andExpect(jsonPath("$.emailAddress").value(DEFAULT_EMAIL_ADDRESS.toString()))
            .andExpect(jsonPath("$.mobilePhone").value(DEFAULT_MOBILE_PHONE.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.creationDate").value(sameInstant(DEFAULT_CREATION_DATE)))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.lastUpdateDate").value(sameInstant(DEFAULT_LAST_UPDATE_DATE)))
            .andExpect(jsonPath("$.lastUpatedBy").value(DEFAULT_LAST_UPATED_BY.toString()))
            .andExpect(jsonPath("$.orgId").value(DEFAULT_ORG_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingEmployees() throws Exception {
        // Get the employees
        restEmployeesMockMvc.perform(get("/api/employees/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmployees() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);
        int databaseSizeBeforeUpdate = employeesRepository.findAll().size();

        // Update the employees
        Employees updatedEmployees = employeesRepository.findOne(employees.getId());
        updatedEmployees
            .name(UPDATED_NAME)
            .password(UPDATED_PASSWORD)
            .emailAddress(UPDATED_EMAIL_ADDRESS)
            .mobilePhone(UPDATED_MOBILE_PHONE)
            .status(UPDATED_STATUS)
            .creationDate(UPDATED_CREATION_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .lastUpdateDate(UPDATED_LAST_UPDATE_DATE)
            .lastUpatedBy(UPDATED_LAST_UPATED_BY)
            .orgId(UPDATED_ORG_ID);

        restEmployeesMockMvc.perform(put("/api/employees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEmployees)))
            .andExpect(status().isOk());

        // Validate the Employees in the database
        List<Employees> employeesList = employeesRepository.findAll();
        assertThat(employeesList).hasSize(databaseSizeBeforeUpdate);
        Employees testEmployees = employeesList.get(employeesList.size() - 1);
        assertThat(testEmployees.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEmployees.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testEmployees.getEmailAddress()).isEqualTo(UPDATED_EMAIL_ADDRESS);
        assertThat(testEmployees.getMobilePhone()).isEqualTo(UPDATED_MOBILE_PHONE);
        assertThat(testEmployees.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testEmployees.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testEmployees.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testEmployees.getLastUpdateDate()).isEqualTo(UPDATED_LAST_UPDATE_DATE);
        assertThat(testEmployees.getLastUpatedBy()).isEqualTo(UPDATED_LAST_UPATED_BY);
        assertThat(testEmployees.getOrgId()).isEqualTo(UPDATED_ORG_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingEmployees() throws Exception {
        int databaseSizeBeforeUpdate = employeesRepository.findAll().size();

        // Create the Employees

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEmployeesMockMvc.perform(put("/api/employees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employees)))
            .andExpect(status().isCreated());

        // Validate the Employees in the database
        List<Employees> employeesList = employeesRepository.findAll();
        assertThat(employeesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEmployees() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);
        int databaseSizeBeforeDelete = employeesRepository.findAll().size();

        // Get the employees
        restEmployeesMockMvc.perform(delete("/api/employees/{id}", employees.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Employees> employeesList = employeesRepository.findAll();
        assertThat(employeesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Employees.class);
    }
}
