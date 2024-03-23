package com.rmssecurity.RMS.repository;

import com.rmssecurity.RMS.entity.Radiologist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RadiologistRepo extends JpaRepository<Radiologist, Long> , JpaSpecificationExecutor<Radiologist> {

//    public List<Radiologist> findByRadiologistName(String name);

    @Query("select r from Radiologist r where r.name like :key")
    List<Radiologist> searchByName(@Param("key") String name);

    @Query("select r from Radiologist r where r.contactNumber like :key")
    List<Radiologist> searchByContactNumber(@Param("key") String contactNumber);

    @Query("select r from Radiologist r where r.username like :key")
    List<Radiologist> searchByUsername(@Param("key") String username);

    @Query("select r from Radiologist r where r.email like :key")
    List<Radiologist> searchByEmail(@Param("key") String email);

    @Query("select r from Radiologist r where r.type like :key")
    List<Radiologist> searchByType(@Param("key") String type);

//    @Query("select r from Radiologist r where r.id like :key")
//    List<Radiologist> searchById(@Param("key") Long id);

}


