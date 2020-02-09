package com.assignemnt.demo.service;

import com.assignemnt.demo.dto.ApartmentDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ApartmentService {

    void save(MultipartFile[] photos, ApartmentDto dto) throws Exception;

    List<ApartmentDto> getApartments();

    ApartmentDto getApartmentById(Long id);

    void deleteApartmentById(Long id);
}
