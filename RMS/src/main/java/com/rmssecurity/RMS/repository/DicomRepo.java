package com.rmssecurity.RMS.repository;

import java.util.List;

import com.rmssecurity.RMS.entity.Dicom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



public interface DicomRepo extends JpaRepository<Dicom, Integer> {

    @Query
    List<Dicom> findByRadiologistId(@Param("radiologist_id") int radiologist_id);
}