package com.snithik.cavmgr.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.snithik.cavmgr.app.domain.ClientActivityDetails;

import com.snithik.cavmgr.app.repository.ClientActivityDetailsRepository;
import com.snithik.cavmgr.app.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ClientActivityDetails.
 */
@RestController
@RequestMapping("/api")
public class ClientActivityDetailsResource {

    private final Logger log = LoggerFactory.getLogger(ClientActivityDetailsResource.class);

    private static final String ENTITY_NAME = "clientActivityDetails";
        
    private final ClientActivityDetailsRepository clientActivityDetailsRepository;

    public ClientActivityDetailsResource(ClientActivityDetailsRepository clientActivityDetailsRepository) {
        this.clientActivityDetailsRepository = clientActivityDetailsRepository;
    }

    /**
     * POST  /client-activity-details : Create a new clientActivityDetails.
     *
     * @param clientActivityDetails the clientActivityDetails to create
     * @return the ResponseEntity with status 201 (Created) and with body the new clientActivityDetails, or with status 400 (Bad Request) if the clientActivityDetails has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/client-activity-details")
    @Timed
    public ResponseEntity<ClientActivityDetails> createClientActivityDetails(@RequestBody ClientActivityDetails clientActivityDetails) throws URISyntaxException {
        log.debug("REST request to save ClientActivityDetails : {}", clientActivityDetails);
        if (clientActivityDetails.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new clientActivityDetails cannot already have an ID")).body(null);
        }
        ClientActivityDetails result = clientActivityDetailsRepository.save(clientActivityDetails);
        return ResponseEntity.created(new URI("/api/client-activity-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /client-activity-details : Updates an existing clientActivityDetails.
     *
     * @param clientActivityDetails the clientActivityDetails to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated clientActivityDetails,
     * or with status 400 (Bad Request) if the clientActivityDetails is not valid,
     * or with status 500 (Internal Server Error) if the clientActivityDetails couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/client-activity-details")
    @Timed
    public ResponseEntity<ClientActivityDetails> updateClientActivityDetails(@RequestBody ClientActivityDetails clientActivityDetails) throws URISyntaxException {
        log.debug("REST request to update ClientActivityDetails : {}", clientActivityDetails);
        if (clientActivityDetails.getId() == null) {
            return createClientActivityDetails(clientActivityDetails);
        }
        ClientActivityDetails result = clientActivityDetailsRepository.save(clientActivityDetails);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, clientActivityDetails.getId().toString()))
            .body(result);
    }

    /**
     * GET  /client-activity-details : get all the clientActivityDetails.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of clientActivityDetails in body
     */
    @GetMapping("/client-activity-details")
    @Timed
    public List<ClientActivityDetails> getAllClientActivityDetails() {
        log.debug("REST request to get all ClientActivityDetails");
        List<ClientActivityDetails> clientActivityDetails = clientActivityDetailsRepository.findAll();
        return clientActivityDetails;
    }

    /**
     * GET  /client-activity-details/:id : get the "id" clientActivityDetails.
     *
     * @param id the id of the clientActivityDetails to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the clientActivityDetails, or with status 404 (Not Found)
     */
    @GetMapping("/client-activity-details/{id}")
    @Timed
    public ResponseEntity<ClientActivityDetails> getClientActivityDetails(@PathVariable Long id) {
        log.debug("REST request to get ClientActivityDetails : {}", id);
        ClientActivityDetails clientActivityDetails = clientActivityDetailsRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(clientActivityDetails));
    }

    /**
     * DELETE  /client-activity-details/:id : delete the "id" clientActivityDetails.
     *
     * @param id the id of the clientActivityDetails to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/client-activity-details/{id}")
    @Timed
    public ResponseEntity<Void> deleteClientActivityDetails(@PathVariable Long id) {
        log.debug("REST request to delete ClientActivityDetails : {}", id);
        clientActivityDetailsRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
