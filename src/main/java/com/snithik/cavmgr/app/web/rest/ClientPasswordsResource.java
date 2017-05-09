package com.snithik.cavmgr.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.snithik.cavmgr.app.domain.ClientPasswords;

import com.snithik.cavmgr.app.repository.ClientPasswordsRepository;
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
 * REST controller for managing ClientPasswords.
 */
@RestController
@RequestMapping("/api")
public class ClientPasswordsResource {

    private final Logger log = LoggerFactory.getLogger(ClientPasswordsResource.class);

    private static final String ENTITY_NAME = "clientPasswords";
        
    private final ClientPasswordsRepository clientPasswordsRepository;

    public ClientPasswordsResource(ClientPasswordsRepository clientPasswordsRepository) {
        this.clientPasswordsRepository = clientPasswordsRepository;
    }

    /**
     * POST  /client-passwords : Create a new clientPasswords.
     *
     * @param clientPasswords the clientPasswords to create
     * @return the ResponseEntity with status 201 (Created) and with body the new clientPasswords, or with status 400 (Bad Request) if the clientPasswords has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/client-passwords")
    @Timed
    public ResponseEntity<ClientPasswords> createClientPasswords(@RequestBody ClientPasswords clientPasswords) throws URISyntaxException {
        log.debug("REST request to save ClientPasswords : {}", clientPasswords);
        if (clientPasswords.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new clientPasswords cannot already have an ID")).body(null);
        }
        ClientPasswords result = clientPasswordsRepository.save(clientPasswords);
        return ResponseEntity.created(new URI("/api/client-passwords/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /client-passwords : Updates an existing clientPasswords.
     *
     * @param clientPasswords the clientPasswords to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated clientPasswords,
     * or with status 400 (Bad Request) if the clientPasswords is not valid,
     * or with status 500 (Internal Server Error) if the clientPasswords couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/client-passwords")
    @Timed
    public ResponseEntity<ClientPasswords> updateClientPasswords(@RequestBody ClientPasswords clientPasswords) throws URISyntaxException {
        log.debug("REST request to update ClientPasswords : {}", clientPasswords);
        if (clientPasswords.getId() == null) {
            return createClientPasswords(clientPasswords);
        }
        ClientPasswords result = clientPasswordsRepository.save(clientPasswords);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, clientPasswords.getId().toString()))
            .body(result);
    }

    /**
     * GET  /client-passwords : get all the clientPasswords.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of clientPasswords in body
     */
    @GetMapping("/client-passwords")
    @Timed
    public List<ClientPasswords> getAllClientPasswords() {
        log.debug("REST request to get all ClientPasswords");
        List<ClientPasswords> clientPasswords = clientPasswordsRepository.findAll();
        return clientPasswords;
    }

    /**
     * GET  /client-passwords/:id : get the "id" clientPasswords.
     *
     * @param id the id of the clientPasswords to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the clientPasswords, or with status 404 (Not Found)
     */
    @GetMapping("/client-passwords/{id}")
    @Timed
    public ResponseEntity<ClientPasswords> getClientPasswords(@PathVariable Long id) {
        log.debug("REST request to get ClientPasswords : {}", id);
        ClientPasswords clientPasswords = clientPasswordsRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(clientPasswords));
    }

    /**
     * DELETE  /client-passwords/:id : delete the "id" clientPasswords.
     *
     * @param id the id of the clientPasswords to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/client-passwords/{id}")
    @Timed
    public ResponseEntity<Void> deleteClientPasswords(@PathVariable Long id) {
        log.debug("REST request to delete ClientPasswords : {}", id);
        clientPasswordsRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
