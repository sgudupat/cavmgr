package com.snithik.cavmgr.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.snithik.cavmgr.app.domain.TripleSetConfig;

import com.snithik.cavmgr.app.repository.TripleSetConfigRepository;
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
 * REST controller for managing TripleSetConfig.
 */
@RestController
@RequestMapping("/api")
public class TripleSetConfigResource {

    private final Logger log = LoggerFactory.getLogger(TripleSetConfigResource.class);

    private static final String ENTITY_NAME = "tripleSetConfig";
        
    private final TripleSetConfigRepository tripleSetConfigRepository;

    public TripleSetConfigResource(TripleSetConfigRepository tripleSetConfigRepository) {
        this.tripleSetConfigRepository = tripleSetConfigRepository;
    }

    /**
     * POST  /triple-set-configs : Create a new tripleSetConfig.
     *
     * @param tripleSetConfig the tripleSetConfig to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tripleSetConfig, or with status 400 (Bad Request) if the tripleSetConfig has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/triple-set-configs")
    @Timed
    public ResponseEntity<TripleSetConfig> createTripleSetConfig(@RequestBody TripleSetConfig tripleSetConfig) throws URISyntaxException {
        log.debug("REST request to save TripleSetConfig : {}", tripleSetConfig);
        if (tripleSetConfig.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new tripleSetConfig cannot already have an ID")).body(null);
        }
        TripleSetConfig result = tripleSetConfigRepository.save(tripleSetConfig);
        return ResponseEntity.created(new URI("/api/triple-set-configs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /triple-set-configs : Updates an existing tripleSetConfig.
     *
     * @param tripleSetConfig the tripleSetConfig to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tripleSetConfig,
     * or with status 400 (Bad Request) if the tripleSetConfig is not valid,
     * or with status 500 (Internal Server Error) if the tripleSetConfig couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/triple-set-configs")
    @Timed
    public ResponseEntity<TripleSetConfig> updateTripleSetConfig(@RequestBody TripleSetConfig tripleSetConfig) throws URISyntaxException {
        log.debug("REST request to update TripleSetConfig : {}", tripleSetConfig);
        if (tripleSetConfig.getId() == null) {
            return createTripleSetConfig(tripleSetConfig);
        }
        TripleSetConfig result = tripleSetConfigRepository.save(tripleSetConfig);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tripleSetConfig.getId().toString()))
            .body(result);
    }

    /**
     * GET  /triple-set-configs : get all the tripleSetConfigs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tripleSetConfigs in body
     */
    @GetMapping("/triple-set-configs")
    @Timed
    public List<TripleSetConfig> getAllTripleSetConfigs() {
        log.debug("REST request to get all TripleSetConfigs");
        List<TripleSetConfig> tripleSetConfigs = tripleSetConfigRepository.findAll();
        return tripleSetConfigs;
    }

    /**
     * GET  /triple-set-configs/:id : get the "id" tripleSetConfig.
     *
     * @param id the id of the tripleSetConfig to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tripleSetConfig, or with status 404 (Not Found)
     */
    @GetMapping("/triple-set-configs/{id}")
    @Timed
    public ResponseEntity<TripleSetConfig> getTripleSetConfig(@PathVariable Long id) {
        log.debug("REST request to get TripleSetConfig : {}", id);
        TripleSetConfig tripleSetConfig = tripleSetConfigRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tripleSetConfig));
    }

    /**
     * DELETE  /triple-set-configs/:id : delete the "id" tripleSetConfig.
     *
     * @param id the id of the tripleSetConfig to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/triple-set-configs/{id}")
    @Timed
    public ResponseEntity<Void> deleteTripleSetConfig(@PathVariable Long id) {
        log.debug("REST request to delete TripleSetConfig : {}", id);
        tripleSetConfigRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
