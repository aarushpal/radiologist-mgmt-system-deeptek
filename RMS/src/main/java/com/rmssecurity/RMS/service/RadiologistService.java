package com.rmssecurity.RMS.service;

import com.rmssecurity.RMS.dto.RadiologistDto;
import com.rmssecurity.RMS.dto.RadiologistResponse;
import com.rmssecurity.RMS.entity.Radiologist;

import java.util.List;
import java.util.Map;

public interface RadiologistService {

    RadiologistDto createRadiologist(RadiologistDto radiologist);
    RadiologistDto updateRadiologist(RadiologistDto radiologist, Long radiologistId);
    Radiologist getRadiologistById(Long radiologistId);
    RadiologistResponse getAllRadiologists(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
    void deleteRadiologistById(Long radiologistId);

    RadiologistResponse searchRadiologists(Integer pageNumber, Integer pageSize, String sortBy, String sortDir, Map<String, String> searchCriteria);
}
