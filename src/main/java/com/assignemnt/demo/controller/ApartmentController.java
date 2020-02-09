package com.assignemnt.demo.controller;

import com.assignemnt.demo.dto.ApartmentDto;
import com.assignemnt.demo.service.ApartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "apartment")
public class ApartmentController {

    private final Logger LOG = LoggerFactory.getLogger(ApartmentController.class);

    @Autowired
    private ApartmentService apartmentService;

    @PostMapping(value = "save")
    public ResponseEntity<?> save(@RequestPart(value = "photos") MultipartFile[] photos,
                                  @Valid @RequestPart(value = "metaData") ApartmentDto apartmentDto) throws Exception {
        LOG.info("Save apartment endpoint called! ");
        apartmentService.save(photos, apartmentDto);
        if (apartmentDto.getId() == null) {
            return ResponseEntity.status(HttpStatus.CREATED).build();// In case of create, status code will be 201
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // In case of update, status code will be 204
        }
    }

    @GetMapping(value = "list")
    public ResponseEntity<?> getApartments() throws Exception {
        LOG.info("Get apartment endpoint called! ");
        List<ApartmentDto> apartments = apartmentService.getApartments();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(apartments);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<?> getApartmentById(@PathVariable Long id) {
        LOG.info("Get apartment By ID endpoint called! ");
        ApartmentDto apartment = apartmentService.getApartmentById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(apartment);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<?> deleteApartmentById(@PathVariable Long id) {
        LOG.info("Get apartment By ID endpoint called! ");
        apartmentService.deleteApartmentById(id);
        return ResponseEntity
                .status(HttpStatus.OK).build();
    }
}
