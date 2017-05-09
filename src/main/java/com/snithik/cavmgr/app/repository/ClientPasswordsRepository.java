package com.snithik.cavmgr.app.repository;

import com.snithik.cavmgr.app.domain.ClientPasswords;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ClientPasswords entity.
 */
@SuppressWarnings("unused")
public interface ClientPasswordsRepository extends JpaRepository<ClientPasswords,Long> {

}
