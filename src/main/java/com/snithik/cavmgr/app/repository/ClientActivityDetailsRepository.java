package com.snithik.cavmgr.app.repository;

import com.snithik.cavmgr.app.domain.ClientActivityDetails;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ClientActivityDetails entity.
 */
@SuppressWarnings("unused")
public interface ClientActivityDetailsRepository extends JpaRepository<ClientActivityDetails,Long> {

}
