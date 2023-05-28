package com.javeriana.userManagment.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import com.javeriana.userManagment.model.Client;

public interface ClientRepository extends CrudRepository<Client, Long>{
    
    Optional<Client> findByEmail(String email);
    
}
