package com.snithik.cavmgr.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.snithik.cavmgr.app.domain.ClientActivities;
import com.snithik.cavmgr.app.domain.Clients;
import com.snithik.cavmgr.app.domain.Employees;
import com.snithik.cavmgr.app.domain.User;
import com.snithik.cavmgr.app.repository.ClientActivitiesRepository;
import com.snithik.cavmgr.app.repository.ClientsRepository;
import com.snithik.cavmgr.app.repository.EmployeesRepository;
import com.snithik.cavmgr.app.repository.UserRepository;
import com.snithik.cavmgr.app.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiOperation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * REST controller for managing Clients.
 */
@RestController
@RequestMapping("/api")
public class ClientsResource {

    private final Logger log = LoggerFactory.getLogger(ClientsResource.class);

    private static final String ENTITY_NAME = "clients";
        
    private final ClientsRepository clientsRepository;
    @Autowired
    private EmployeesRepository employeesRepository;
    @Autowired
    private ClientActivitiesRepository clientActivitiesRepository;
    @Autowired
    private UserResource userResource;

    public ClientsResource(ClientsRepository clientsRepository) {
        this.clientsRepository = clientsRepository;
    }

    /**
     * POST  /clients : Create a new clients.
     *
     * @param clients the clients to create
     * @return the ResponseEntity with status 201 (Created) and with body the new clients, or with status 400 (Bad Request) if the clients has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @ApiOperation(hidden = true, value = "")
    @PostMapping("/clients")
    @Timed
    public ResponseEntity<Clients> createClients(@RequestBody Clients clients) throws URISyntaxException {
        log.debug("REST request to save Clients : {}", clients);
        if (clients.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new clients cannot already have an ID")).body(null);
        }
        Clients result = clientsRepository.save(clients);
        return ResponseEntity.created(new URI("/api/clients/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /clients : Updates an existing clients.
     *
     * @param clients the clients to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated clients,
     * or with status 400 (Bad Request) if the clients is not valid,
     * or with status 500 (Internal Server Error) if the clients couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @ApiOperation(hidden = true, value = "")
    @PutMapping("/clients")
    @Timed
    public ResponseEntity<Clients> updateClients(@RequestBody Clients clients) throws URISyntaxException {
        log.debug("REST request to update Clients : {}", clients);
        if (clients.getId() == null) {
            return createClients(clients);
        }
        Clients result = clientsRepository.save(clients);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, clients.getId().toString()))
            .body(result);
    }

    /**
     * GET  /clients : get all the clients.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of clients in body
     */
    @GetMapping("/clients")
    @Timed
    public List<Clients> getAllClients() {
        log.debug("REST request to get all Clients");
        List<Clients> clients = clientsRepository.findAll();
        return clients;
    }

    /**
     * GET  /clients/:id : get the "id" clients.
     *
     * @param id the id of the clients to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the clients, or with status 404 (Not Found)
     */
    @ApiOperation(hidden = true, value = "")
    @GetMapping("/clients/{id}")
    @Timed
    public ResponseEntity<Clients> getClients(@PathVariable Long id) {
        log.debug("REST request to get Clients : {}", id);
        Clients clients = clientsRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(clients));
    }

    /**
     * DELETE  /clients/:id : delete the "id" clients.
     *
     * @param id the id of the clients to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @ApiOperation(hidden = true, value = "")
    @DeleteMapping("/clients/{id}")
    @Timed
    public ResponseEntity<Void> deleteClients(@PathVariable Long id) {
        log.debug("REST request to delete Clients : {}", id);
        clientsRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
   @SuppressWarnings({ "unused", "unchecked" })
	@GetMapping("/clientsByManger")
    @Timed
    public ResponseEntity<Set<Clients>> getAllClientsByMgr(@RequestParam Long managerId) {
        log.debug("REST request to get all Clients by Mgr");
        Set<Clients> clients = new HashSet<>();
        Employees employee =employeesRepository.findOne(managerId);
        List<ClientActivities> client_activities = clientActivitiesRepository.findByResponsibleMgr(employee);
        
        log.debug("Following Client Activities were found");
        client_activities.forEach(clientActivity ->{
        	log.debug(client_activities.toString());
        	 clients.add(clientActivity.getClient());
        });
       
        //return (ArrayList<Clients>)clients;
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable((Set<Clients>)clients));
    }
    
    @SuppressWarnings("unchecked")
	@GetMapping("/getClients")
    @Timed
    public ResponseEntity<ArrayList<Clients>> getClients() {
    	
        log.debug("REST request to get all Clients by OrgId");
        User user= userResource.getUser();
        log.debug("User is " + user.toString());
        List<Clients> clients = clientsRepository.findByOrgId(user.getId());
  
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable((ArrayList<Clients>)clients));
    }
}


