package com.javeriana.userManagment.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import com.javeriana.userManagment.model.Provider;

public interface ProviderRepository extends CrudRepository<Provider, Long>{
    
    Optional<Provider> findByEmail(String email);
    
}
