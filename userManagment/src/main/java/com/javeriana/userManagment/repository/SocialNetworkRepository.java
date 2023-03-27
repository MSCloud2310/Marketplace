package com.javeriana.userManagment.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import com.javeriana.userManagment.model.SocialNetwork;

public interface SocialNetworkRepository extends CrudRepository<SocialNetwork ,Long>{
    
    Optional<SocialNetwork> findByUrl(String url);
}
