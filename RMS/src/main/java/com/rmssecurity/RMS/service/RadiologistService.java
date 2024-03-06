package com.rmssecurity.RMS.service;

import com.rmssecurity.RMS.dto.RadiologistDto;
import com.rmssecurity.RMS.dto.RadiologistResponse;

import java.util.List;

public interface RadiologistService {

    RadiologistDto createRadiologist(RadiologistDto radiologist);
    RadiologistDto updateRadiologist(RadiologistDto radiologist, Long radiologistId);
    RadiologistDto getRadiologistById(Long radiologistId);
    RadiologistResponse getAllRadiologists(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
    void deleteRadiologist(Long radiologistId);

    List<RadiologistDto> searchRadiologists(String searchBy, String keyword);
}
