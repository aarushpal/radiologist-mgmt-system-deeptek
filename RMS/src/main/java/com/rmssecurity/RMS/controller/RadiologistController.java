package com.rmssecurity.RMS.controller;


import com.rmssecurity.RMS.config.AppConstants;
import com.rmssecurity.RMS.dto.RadiologistDto;
import com.rmssecurity.RMS.dto.RadiologistResponse;
import com.rmssecurity.RMS.entity.Radiologist;
import com.rmssecurity.RMS.exception.ResourceNotFoundException;
import com.rmssecurity.RMS.repository.RadiologistRepo;
import com.rmssecurity.RMS.service.RadiologistService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000/")
@RequestMapping("/api/radiologists")
public class RadiologistController {

    @Autowired
    private RadiologistService radiologistService;

//    @PostMapping("/")
//    Radiologist newRadiologist(@Valid @RequestBody Radiologist newRadiologist){
//        return radiologistRepository.save(newRadiologist);
//    }

    @PostMapping("/")
    public ResponseEntity<RadiologistDto> createRadiologist(@Valid @RequestBody RadiologistDto radiologistDto)
    {
        RadiologistDto createRadiologistDto = this.radiologistService.createRadiologist(radiologistDto);
        return new ResponseEntity<>(createRadiologistDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RadiologistDto> updateRadiologist(@Valid @RequestBody RadiologistDto radiologistDto, @PathVariable long id)
    {
        RadiologistDto updatedRadiologistDto = this.radiologistService.updateRadiologist(radiologistDto, id);
        return new ResponseEntity<>(updatedRadiologistDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRadiologist(@PathVariable long id) {
        try {
            this.radiologistService.deleteRadiologist(id);
            return ResponseEntity.status(HttpStatus.OK).body("Radiologist deleted with ID: " + id);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Radiologist not found with ID: " + id);
        }
    }



    @GetMapping("/")
    public ResponseEntity<RadiologistResponse> getAllRadiologists(
            @RequestParam(value="pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value="pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value="sortBy", defaultValue = AppConstants.SORT_BY, required=false) String sortBy,
            @RequestParam(value="sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir

    ){
        RadiologistResponse radiologistResponse = this.radiologistService.getAllRadiologists(pageNumber,pageSize,sortBy,sortDir);
        return new ResponseEntity<RadiologistResponse>(radiologistResponse, HttpStatus.OK);
    }


    //search
    @GetMapping("/search/")
    public ResponseEntity<List<RadiologistDto>> searchRadiologistByName(
            @RequestParam(value = "searchBy", defaultValue = "name", required = true) String searchBy,
            @RequestParam(value = "keyword", required = false) String keyword
    ) {
        if (keyword == null || keyword.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<RadiologistDto> result;

        switch (searchBy) {
            case "name":
                result = this.radiologistService.searchRadiologists("name", keyword);
                break;
            case "email":
                result = this.radiologistService.searchRadiologists("email", keyword);
                break;
            case "username":
                result = this.radiologistService.searchRadiologists("username", keyword);
                break;
            case "type":
                result = this.radiologistService.searchRadiologists("type", keyword);
                break;
            case "contactNumber":
                result = this.radiologistService.searchRadiologists("contactNumber", keyword);
                break;
            default:
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }




}