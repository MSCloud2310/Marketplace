package com.javeriana.ms.contenidos.controller;

import com.javeriana.ms.contenidos.model.Contents;
import com.javeriana.ms.contenidos.service.ContentService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/contents")
public class ContentController {

    @Autowired
    ContentService contentService;

    @GetMapping()
    public ResponseEntity<List<Contents>> getAllContents() {
        List<Contents> transports = contentService.getContents();
        return new ResponseEntity<>(transports, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contents> getContentById(@PathVariable("id") Integer id){
        Contents content = this.contentService.getContentById(id);
        if( content== null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(content, HttpStatus.OK);
    }

    @GetMapping("/service/{id}")
    public ResponseEntity<List<Contents>> getAllContentsByService(@PathVariable("id") Integer id) {
        List<Contents> transports = contentService.getContentByServiceId(id);
        return new ResponseEntity<>(transports, HttpStatus.OK);
    }

    @GetMapping("/provider/{id}")
    public ResponseEntity<List<Contents>> getAllContentsByProvider(@PathVariable("Id") Integer id) {
        List<Contents> transports = contentService.getContentByProviderId(id);
        return new ResponseEntity<>(transports, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Contents> createContent(@RequestBody Contents content) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        content.setCreatio_date(date);
        contentService.saveContent(content);
        return new ResponseEntity<>(content, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Contents> changeStatus(@PathVariable("id") Integer id) {
        Contents content = contentService.invalidateContent(id);
        return new ResponseEntity<>(content, HttpStatus.CREATED);
    }
}
