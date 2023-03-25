package com.javeriana.ms.contenidos.service;

import com.javeriana.ms.contenidos.model.Contents;
import com.javeriana.ms.contenidos.repository.ContensRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContentService {

    @Autowired
    ContensRepository contensRepository;

    public List<Contents> getContents(){
        return this.contensRepository.findAll();
    }

    public Contents getContentById(Integer id){
        return this.contensRepository.findContentsById(id);
    }
    public List<Contents> getContentByServiceId(Integer id){
        return this.contensRepository.findContentsByServiceID(id);
    }

    public List<Contents> getContentByProviderId(Integer id){
        return this.contensRepository.findContentsByProviderID(id);
    }

    public Contents saveContent( Contents content){
        return this.contensRepository.save(content);
    }

    public Contents invalidateContent( Integer id){
        Contents content = getContentById(id);
        content.setStatus(!content.isStatus());
        return this.contensRepository.save(content);
    }
}
