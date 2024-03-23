package com.rmssecurity.RMS.service;


import com.rmssecurity.RMS.dto.RadiologistDto;
import com.rmssecurity.RMS.dto.RadiologistResponse;
import com.rmssecurity.RMS.entity.Dicom;
import com.rmssecurity.RMS.entity.Radiologist;
import com.rmssecurity.RMS.exception.ResourceNotFoundException;
import com.rmssecurity.RMS.repository.DicomRepo;
import com.rmssecurity.RMS.repository.RadiologistRepo;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RadiologistServiceImpl implements RadiologistService {
    @Autowired
    private RadiologistRepo radiologistRepo;
    @Autowired
    private DicomRepo dicomRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public RadiologistDto createRadiologist(RadiologistDto radiologistDto) {
        Radiologist radiologist = this.dtoToRadiologist(radiologistDto);
        Radiologist savedRadiologist = this.radiologistRepo.save(radiologist);
        return this.radiologistToDto(savedRadiologist);
    }

    @Override
    public RadiologistDto updateRadiologist(RadiologistDto radiologistDto, Long radiologistId) {
        Radiologist radiologist = this.radiologistRepo.findById(radiologistId).orElseThrow(()-> new ResourceNotFoundException("Radiologist", " id ", radiologistId));
        radiologist.setName(radiologistDto.getName());
        radiologist.setUsername((radiologistDto.getUsername()));
        radiologist.setEmail(radiologistDto.getEmail());
        radiologist.setType(radiologistDto.getType());
        radiologist.setContactNumber(radiologistDto.getContactNumber());

        Radiologist updatedRadiologist = this.radiologistRepo.save(radiologist);

        return this.radiologistToDto(updatedRadiologist);
    }

    @Override
    public Radiologist getRadiologistById(Long radiologistId) {
        Radiologist radiologist = this.radiologistRepo.findById(radiologistId).orElseThrow(()-> new ResourceNotFoundException("Radiologist", " id ", radiologistId));
//        return this.radiologistToDto(radiologist);
        return radiologist;
    }

    @Override
    public RadiologistResponse getAllRadiologists(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

        Sort sort = null;
        if(sortDir.equalsIgnoreCase("asc"))
        {
            sort = Sort.by(sortBy).ascending();
        }
        else{
            sort = Sort.by(sortBy).descending();
        }

        Pageable p = PageRequest.of(pageNumber, pageSize, sort);
        Page<Radiologist> pageRadiologist = this.radiologistRepo.findAll(p);
        List<Radiologist> allRadiologists = pageRadiologist.getContent();

//        List<Radiologist> radiologistDtos =  this.radiologistRepo.findAll();
//        return radiologists.stream().map(this::radiologistToDto).toList();
        List<RadiologistDto> radiologistDtos =  allRadiologists.stream().map((radiologist) -> this.modelMapper.map(radiologist,RadiologistDto.class)).collect(Collectors.toList());
        RadiologistResponse radiologistResponse = new RadiologistResponse();
        radiologistResponse.setContent(radiologistDtos);
        radiologistResponse.setPageNumber(pageRadiologist.getNumber());
        radiologistResponse.setPageSize(pageRadiologist.getSize());
        radiologistResponse.setTotalElements(pageRadiologist.getTotalElements());
        radiologistResponse.setTotalPages(pageRadiologist.getTotalPages());
        radiologistResponse.setLastPage(pageRadiologist.isLast());

        return radiologistResponse;
    }

    @Override
    public void deleteRadiologistById(Long radiologistId) {
//        Radiologist radiologist = this.radiologistRepo.findById(radiologistId).orElseThrow(()-> new ResourceNotFoundException("Radiologist", " id ", radiologistId));
//        this.radiologistRepo.delete(radiologist);
//        if(dicomRepo.existsById(radiologistId.intValue()))
//        {
//            dicomRepo.deleteById(radiologistId.intValue());
//        }
//        if (dicomRepo.existsByRadiologistId(radiologistId)) {
//            // Delete associated DICOM images
//            dicomRepo.deleteByRadiologistId(radiologistId);
//        }
        // Check if DICOM images are associated with this radiologist
//        boolean hasDicomImages = dicomRepo.existsByRadiologist_RadiologistId(radiologistId);
//
//        // If DICOM images exist, delete them
//        if (hasDicomImages) {
            List<Dicom> dicomImages = dicomRepo.findByRadiologistId(radiologistId.intValue());
            dicomRepo.deleteAll(dicomImages);
//        }
        radiologistRepo.deleteById(radiologistId);
    }

//    @Override
//    public List<RadiologistDto> searchRadiologists(Map<String, String> searchCriteria){
//
//        List<Radiologist> radiologists =  null;
//        switch(searchBy){
//            case "name":
//                radiologists = this.radiologistRepo.searchByName("%"+keyword+"%");
//                break;
//            case "username":
//                radiologists = this.radiologistRepo.searchByUsername("%"+keyword+"%");
//                break;
//            case "type":
//                radiologists = this.radiologistRepo.searchByType("%"+keyword+"%");
//                break;
//            case "contactNumber":
//                radiologists = this.radiologistRepo.searchByContactNumber("%"+keyword+"%");
//                break;
//            case "email":
//                radiologists = this.radiologistRepo.searchByEmail("%"+keyword+"%");
//                break;
//
//        }
//
//        List<RadiologistDto> radiologistDtos = radiologists.stream().map((radiologist) -> this.modelMapper.map(radiologist, RadiologistDto.class)).collect(Collectors.toList());
//
//        return radiologistDtos;
//    }


    @Override
    public RadiologistResponse searchRadiologists(Integer pageNumber, Integer pageSize, String sortBy, String sortDir, Map<String, String> searchCriteria) {
        Sort sort = null;
        if (sortDir.equalsIgnoreCase("asc")) {
            sort = Sort.by(sortBy).ascending();
        } else {
            sort = Sort.by(sortBy).descending();
        }

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        List<Specification<Radiologist>> specifications = new ArrayList<>();

        for (Map.Entry<String, String> entry : searchCriteria.entrySet()) {
            String searchBy = entry.getKey();
            String keyword = "%" + entry.getValue() + "%";

            switch (searchBy) {
                case "name":
                    specifications.add((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), keyword));
                    break;
                case "email":
                    specifications.add((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("email"), keyword));
                    break;
                case "username":
                    specifications.add((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("username"), keyword));
                    break;
                case "type":
                    specifications.add((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("type"), keyword));
                    break;
                case "contactNumber":
                    specifications.add((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("contactNumber"), keyword));
                    break;
                default:
                    // Handle unknown search criteria
                    break;
            }
        }

        Specification<Radiologist> combinedSpecification = specifications.stream().reduce(Specification::and).orElse(null);

        Page<Radiologist> pageRadiologist;
        if (combinedSpecification != null) {
            pageRadiologist = radiologistRepo.findAll(combinedSpecification, pageable);
        } else {
            pageRadiologist = radiologistRepo.findAll(pageable);
        }

        List<Radiologist> allRadiologists = pageRadiologist.getContent();

        List<RadiologistDto> radiologistDtos = allRadiologists.stream()
                .map(radiologist -> modelMapper.map(radiologist, RadiologistDto.class))
                .collect(Collectors.toList());

        RadiologistResponse radiologistResponse = new RadiologistResponse();
        radiologistResponse.setContent(radiologistDtos);
        radiologistResponse.setPageNumber(pageRadiologist.getNumber());
        radiologistResponse.setPageSize(pageRadiologist.getSize());
        radiologistResponse.setTotalElements(pageRadiologist.getTotalElements());
        radiologistResponse.setTotalPages(pageRadiologist.getTotalPages());
        radiologistResponse.setLastPage(pageRadiologist.isLast());

        return radiologistResponse;
    }




    private Radiologist dtoToRadiologist(RadiologistDto radiologistDto)
    {
        return this.modelMapper.map(radiologistDto, Radiologist.class);
    }

    public RadiologistDto radiologistToDto(Radiologist radiologist)
    {
        return this.modelMapper.map(radiologist, RadiologistDto.class);
    }
}