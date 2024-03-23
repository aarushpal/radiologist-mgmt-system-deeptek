package com.rmssecurity.RMS.controller;

import java.io.IOException;
import com.rmssecurity.RMS.config.AppConstants;
import com.rmssecurity.RMS.dto.RadiologistDto;
import com.rmssecurity.RMS.dto.RadiologistResponse;
import com.rmssecurity.RMS.entity.Dicom;
import com.rmssecurity.RMS.service.DicomService;
import com.rmssecurity.RMS.entity.Radiologist;
import com.rmssecurity.RMS.exception.ResourceNotFoundException;
import com.rmssecurity.RMS.repository.RadiologistRepo;
import com.rmssecurity.RMS.service.RadiologistService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000/")
@RequestMapping("/api/radiologists")
public class RadiologistController {

    @Autowired
    private RadiologistService radiologistService;

    @Autowired
    private DicomService dicom_service;

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

//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> deleteRadiologist(@PathVariable long id) {
//        try {
//            this.radiologistService.deleteRadiologist(id);
//            return new ResponseEntity<>(HttpStatus.OK);
//        } catch (ResourceNotFoundException ex) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                    .body("Radiologist not found with ID: " + id);
//        }
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRadiologist(@PathVariable Long id) {
        radiologistService.deleteRadiologistById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Radiologist with ID: " + id + " deleted successfully.");
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
//    @GetMapping("/search/")
//    public ResponseEntity<List<RadiologistDto>> searchRadiologistByName(
//            @RequestParam(value = "searchBy", defaultValue = "name", required = true) String searchBy,
//            @RequestParam(value = "keyword", required = false) String keyword
//    ) {
//        if (keyword == null || keyword.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//
//        List<RadiologistDto> result;
//
//        switch (searchBy) {
//            case "name":
//                result = this.radiologistService.searchRadiologists("name", keyword);
//                break;
//            case "email":
//                result = this.radiologistService.searchRadiologists("email", keyword);
//                break;
//            case "username":
//                result = this.radiologistService.searchRadiologists("username", keyword);
//                break;
//            case "type":
//                result = this.radiologistService.searchRadiologists("type", keyword);
//                break;
//            case "contactNumber":
//                result = this.radiologistService.searchRadiologists("contactNumber", keyword);
//                break;
//            default:
//                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }

    @GetMapping("/search")
    public ResponseEntity<RadiologistResponse> searchRadiologists(
            @RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir,
            @RequestParam Map<String, String> searchCriteria
    ) {
        RadiologistResponse response = radiologistService.searchRadiologists(pageNumber, pageSize, sortBy, sortDir, searchCriteria);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



    // DICOM APIs


    @PostMapping(value = "/upload/{radiologist_id}/",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadImageToFileSystem(@RequestPart("file") MultipartFile file, @PathVariable("radiologist_id") long radiologist_id) throws IOException {

        try
        {
            String file_url = dicom_service.uploadImageToFileSystem(file, radiologist_id);
            return new ResponseEntity<String>(file_url, HttpStatus.OK);
        }
        catch(ResourceNotFoundException ex)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Radiologist not found with ID: " + radiologist_id);

        }


    }



    @GetMapping("/viewDicom/{dicomId}/")
    public ResponseEntity<?> downloadImage (@PathVariable("dicomId") int dicomId, HttpServletRequest request) throws IOException{
        Resource resource= dicom_service.downloadImage(dicomId);
        String mimeType;

        try{
            mimeType= request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        }catch(IOException e){
            mimeType=  MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }

        mimeType= (mimeType==null)? MediaType.APPLICATION_OCTET_STREAM_VALUE : mimeType;

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(mimeType))
                .body(resource);
    }


    @GetMapping("/view/{radiologist_id}")
    public List<Dicom> getAllDicomsByRadiologistId(@PathVariable("radiologist_id") int radiologist_id){

        return (List<Dicom>)dicom_service.getAllDicomsByRadiologistId(radiologist_id);

    }

    @GetMapping("/viewPatientDetails/{dicomId}")
    public List<String> getPatientDicomDetails(@PathVariable("dicomId") int dicomId){

        return dicom_service.getPatientDicomDetails(dicomId);

    }
    @PutMapping("/update/{dicomId}")
    public String updateDicom(@PathVariable("dicomId")int dicomId, MultipartFile file) throws IOException {
        return dicom_service.updateDicom(dicomId,file);
    }




//    @PutMapping("/saveComment/{dicomId}")
//    public void CommentOnDicom (@PathVariable("dicomId")int dicomId,@RequestParam("comment") String comment){
//        dicom_service.CommentOnDicom(dicomId,comment);
//    }
//
//    @GetMapping("/getComment/{dicomId}")
//    public String getComment(@PathVariable("dicomId")int dicomId){
//        return dicom_service.getComment(dicomId);
//    }





}