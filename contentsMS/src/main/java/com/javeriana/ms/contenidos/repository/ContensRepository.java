package com.javeriana.ms.contenidos.repository;


import com.javeriana.ms.contenidos.model.Contents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContensRepository extends JpaRepository<Contents, Integer> {

    Contents findContentsById(Integer id);
    List<Contents> findContentsByServiceID(Integer id);
    List<Contents> findContentsByProviderID(Integer id);

    Contents findContentsByProviderIDAndId(Integer pId,Integer id);

    Contents findContentsByServiceIDAndId(Integer pId,Integer id);

}
