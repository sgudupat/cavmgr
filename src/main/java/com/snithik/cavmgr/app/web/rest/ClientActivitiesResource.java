package com.snithik.cavmgr.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.snithik.cavmgr.app.domain.ClientActivities;

import com.snithik.cavmgr.app.repository.ClientActivitiesRepository;
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
 * REST controller for managing ClientActivities.
 */
@RestController
@RequestMapping("/api")
public class ClientActivitiesResource {

    private final Logger log = LoggerFactory.getLogger(ClientActivitiesResource.class);

    private static final String ENTITY_NAME = "clientActivities";
        
    private final ClientActivitiesRepository clientActivitiesRepository;

    public ClientActivitiesResource(ClientActivitiesRepository clientActivitiesRepository) {
        this.clientActivitiesRepository = clientActivitiesRepository;
    }

    /**
     * POST  /client-activities : Create a new clientActivities.
     *
     * @param clientActivities the clientActivities to create
     * @return the ResponseEntity with status 201 (Created) and with body the new clientActivities, or with status 400 (Bad Request) if the clientActivities has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @ApiOperation(hidden = true, value = "")
    @PostMapping("/client-activities")
    @Timed
    public ResponseEntity<ClientActivities> createClientActivities(@RequestBody ClientActivities clientActivities) throws URISyntaxException {
        log.debug("REST request to save ClientActivities : {}", clientActivities);
        if (clientActivities.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new clientActivities cannot already have an ID")).body(null);
        }
        ClientActivities result = clientActivitiesRepository.save(clientActivities);
        return ResponseEntity.created(new URI("/api/client-activities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /client-activities : Updates an existing clientActivities.
     *
     * @param clientActivities the clientActivities to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated clientActivities,
     * or with status 400 (Bad Request) if the clientActivities is not valid,
     * or with status 500 (Internal Server Error) if the clientActivities couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @ApiOperation(hidden = true, value = "")
    @PutMapping("/client-activities")
    @Timed
    public ResponseEntity<ClientActivities> updateClientActivities(@RequestBody ClientActivities clientActivities) throws URISyntaxException {
        log.debug("REST request to update ClientActivities : {}", clientActivities);
        if (clientActivities.getId() == null) {
            return createClientActivities(clientActivities);
        }
        ClientActivities result = clientActivitiesRepository.save(clientActivities);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, clientActivities.getId().toString()))
            .body(result);
    }

    /**
     * GET  /client-activities : get all the clientActivities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of clientActivities in body
     */
    @ApiOperation(hidden = true, value = "")
    @GetMapping("/client-activities")
    @Timed
    public List<ClientActivities> getAllClientActivities() {
        log.debug("REST request to get all ClientActivities");
        List<ClientActivities> clientActivities = clientActivitiesRepository.findAll();
        return clientActivities;
    }

    /**
     * GET  /client-activities/:id : get the "id" clientActivities.
     *
     * @param id the id of the clientActivities to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the clientActivities, or with status 404 (Not Found)
     */
    @ApiOperation(hidden = true, value = "")
    @GetMapping("/client-activities/{id}")
    @Timed
    public ResponseEntity<ClientActivities> getClientActivities(@PathVariable Long id) {
        log.debug("REST request to get ClientActivities : {}", id);
        ClientActivities clientActivities = clientActivitiesRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(clientActivities));
    }

    /**
     * DELETE  /client-activities/:id : delete the "id" clientActivities.
     *
     * @param id the id of the clientActivities to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @ApiOperation(hidden = true, value = "")
    @DeleteMapping("/client-activities/{id}")
    @Timed
    public ResponseEntity<Void> deleteClientActivities(@PathVariable Long id) {
        log.debug("REST request to delete ClientActivities : {}", id);
        clientActivitiesRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
