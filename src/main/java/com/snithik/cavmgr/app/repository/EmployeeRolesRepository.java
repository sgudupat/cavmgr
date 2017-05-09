package com.snithik.cavmgr.app.repository;

import com.snithik.cavmgr.app.domain.EmployeeRoles;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the EmployeeRoles entity.
 */
@SuppressWarnings("unused")
public interface EmployeeRolesRepository extends JpaRepository<EmployeeRoles,Long> {

}
