package com.snithik.cavmgr.app.repository;

import com.snithik.cavmgr.app.domain.Clients;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Clients entity.
 */
@SuppressWarnings("unused")
public interface ClientsRepository extends JpaRepository<Clients,Long> {

}
