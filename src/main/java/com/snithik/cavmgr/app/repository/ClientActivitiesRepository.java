package com.snithik.cavmgr.app.repository;

import com.snithik.cavmgr.app.domain.ClientActivities;
import com.snithik.cavmgr.app.domain.Employees;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ClientActivities entity.
 */
@SuppressWarnings("unused")
public interface ClientActivitiesRepository extends JpaRepository<ClientActivities,Long> {

	List<ClientActivities> findByResponsibleMgr(Employees employee);


}
