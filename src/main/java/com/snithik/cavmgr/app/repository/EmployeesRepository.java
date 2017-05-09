package com.snithik.cavmgr.app.repository;

import com.snithik.cavmgr.app.domain.Employees;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Employees entity.
 */
@SuppressWarnings("unused")
public interface EmployeesRepository extends JpaRepository<Employees,Long> {

}
