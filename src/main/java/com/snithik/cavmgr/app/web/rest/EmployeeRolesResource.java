package com.snithik.cavmgr.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.snithik.cavmgr.app.domain.EmployeeRoles;

import com.snithik.cavmgr.app.repository.EmployeeRolesRepository;
import com.snithik.cavmgr.app.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiOperation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing EmployeeRoles.
 */
@RestController
@RequestMapping("/api")
public class EmployeeRolesResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeRolesResource.class);

    private static final String ENTITY_NAME = "employeeRoles";
        
    private final EmployeeRolesRepository employeeRolesRepository;

    public EmployeeRolesResource(EmployeeRolesRepository employeeRolesRepository) {
        this.employeeRolesRepository = employeeRolesRepository;
    }

    /**
     * POST  /employee-roles : Create a new employeeRoles.
     *
     * @param employeeRoles the employeeRoles to create
     * @return the ResponseEntity with status 201 (Created) and with body the new employeeRoles, or with status 400 (Bad Request) if the employeeRoles has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @ApiOperation(hidden = true, value = "")
    @PostMapping("/employee-roles")
    @Timed
    public ResponseEntity<EmployeeRoles> createEmployeeRoles(@RequestBody EmployeeRoles employeeRoles) throws URISyntaxException {
        log.debug("REST request to save EmployeeRoles : {}", employeeRoles);
        if (employeeRoles.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new employeeRoles cannot already have an ID")).body(null);
        }
        EmployeeRoles result = employeeRolesRepository.save(employeeRoles);
        return ResponseEntity.created(new URI("/api/employee-roles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /employee-roles : Updates an existing employeeRoles.
     *
     * @param employeeRoles the employeeRoles to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated employeeRoles,
     * or with status 400 (Bad Request) if the employeeRoles is not valid,
     * or with status 500 (Internal Server Error) if the employeeRoles couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @ApiOperation(hidden = true, value = "")
    @PutMapping("/employee-roles")
    @Timed
    public ResponseEntity<EmployeeRoles> updateEmployeeRoles(@RequestBody EmployeeRoles employeeRoles) throws URISyntaxException {
        log.debug("REST request to update EmployeeRoles : {}", employeeRoles);
        if (employeeRoles.getId() == null) {
            return createEmployeeRoles(employeeRoles);
        }
        EmployeeRoles result = employeeRolesRepository.save(employeeRoles);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, employeeRoles.getId().toString()))
            .body(result);
    }

    /**
     * GET  /employee-roles : get all the employeeRoles.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of employeeRoles in body
     */
    @ApiOperation(hidden = true, value = "")
    @GetMapping("/employee-roles")
    @Timed
    public List<EmployeeRoles> getAllEmployeeRoles() {
        log.debug("REST request to get all EmployeeRoles");
        List<EmployeeRoles> employeeRoles = employeeRolesRepository.findAll();
        return employeeRoles;
    }

    /**
     * GET  /employee-roles/:id : get the "id" employeeRoles.
     *
     * @param id the id of the employeeRoles to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the employeeRoles, or with status 404 (Not Found)
     */
    @ApiOperation(hidden = true, value = "")
    @GetMapping("/employee-roles/{id}")
    @Timed
    public ResponseEntity<EmployeeRoles> getEmployeeRoles(@PathVariable Long id) {
        log.debug("REST request to get EmployeeRoles : {}", id);
        EmployeeRoles employeeRoles = employeeRolesRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(employeeRoles));
    }

    /**
     * DELETE  /employee-roles/:id : delete the "id" employeeRoles.
     *
     * @param id the id of the employeeRoles to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @ApiOperation(hidden = true, value = "")
    @DeleteMapping("/employee-roles/{id}")
    @Timed
    public ResponseEntity<Void> deleteEmployeeRoles(@PathVariable Long id) {
        log.debug("REST request to delete EmployeeRoles : {}", id);
        employeeRolesRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
