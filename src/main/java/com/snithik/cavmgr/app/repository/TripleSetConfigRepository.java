package com.snithik.cavmgr.app.repository;

import com.snithik.cavmgr.app.domain.TripleSetConfig;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TripleSetConfig entity.
 */
@SuppressWarnings("unused")
public interface TripleSetConfigRepository extends JpaRepository<TripleSetConfig,Long> {

}
