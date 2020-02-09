package com.assignemnt.demo.service;

import com.assignemnt.demo.dto.ApartmentDto;
import com.assignemnt.demo.exception.RequestValidationException;
import com.assignemnt.demo.model.Apartment;
import com.assignemnt.demo.model.Photo;
import com.assignemnt.demo.repository.ApartmentRepository;
import com.assignemnt.demo.repository.PhotoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ApartmentServiceImpl implements ApartmentService {

    private Logger LOGGER = LoggerFactory.getLogger(ApartmentServiceImpl.class);

    private PhotoRepository photoRepository;
    private final ApartmentRepository apartmentRepository;

    @Autowired
    public ApartmentServiceImpl(ApartmentRepository apartmentRepository, PhotoRepository photoRepository) {
        this.apartmentRepository = apartmentRepository;
        this.photoRepository = photoRepository;
    }

    @Override
    public void save(MultipartFile[] files, ApartmentDto dto) throws IOException {
        if ((files == null || files.length < 1) && (dto.getPreviousIds() == null || dto.getPreviousIds().isEmpty())) {
            throw new RequestValidationException("Apartment Photos are required");
        }

        Apartment apartment = null;
        //Checking update case
        if (dto.getId() != null) {
            //Update case
            Optional<Apartment> byId = apartmentRepository.findById(dto.getId());
            apartment = byId.orElse(null);
            apartment.setAddress(dto.getAddress());
            apartment.setDescription(dto.getDescription());
            apartment.setNoOfRooms(dto.getNoOfRooms());
            //Deleting all removed photos
            photoRepository.deleteAllByIdNotIn(dto.getPreviousIds());
        } else {
            //Create new case
            //Converting dto to entity
            ObjectMapper mapper = new ObjectMapper();
            apartment = mapper.convertValue(dto, Apartment.class);
        }

        List<Photo> photos = new ArrayList<>();
        for (MultipartFile file : files) {
            Photo photo = new Photo();
            photo.setFileBytes(file.getBytes());
            photo.setMimeType(file.getContentType());
            photo.setName(file.getOriginalFilename());
            photo.setApartment(apartment); // bidirectional mapping
            photos.add(photo);
        }

        apartment.setPhotos(photos);

        apartmentRepository.save(apartment);

        LOGGER.info("Apartment saved successfully");

    }

    @Override
    public List<ApartmentDto> getApartments() {
        Page<Apartment> apartments = apartmentRepository.findAll(PageRequest.of(0, 10));
        List<ApartmentDto> apartmentDtos = apartments.get().map(apartment -> {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.convertValue(apartment, ApartmentDto.class);
        }).collect(Collectors.toList());
        return apartmentDtos;
    }

    @Override
    public ApartmentDto getApartmentById(Long id) {
        Optional<Apartment> byId = apartmentRepository.findById(id);
        Apartment apartment = byId.orElse(null);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(apartment, ApartmentDto.class);
    }

    @Override
    public void deleteApartmentById(Long id) {
        apartmentRepository.deleteById(id);
    }

}
