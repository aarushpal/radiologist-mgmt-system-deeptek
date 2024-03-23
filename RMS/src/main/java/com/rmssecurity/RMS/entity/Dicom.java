package com.rmssecurity.RMS.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "dicoms")
public class Dicom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dicom_id")
    private Long dicomId;

    @Column(name = "file_url")
    private String fileUrl;

    @Column(name = "patient_id")
    private String patientId;

    @Column(name = "patient_name")
    private String patientName;

    @Column(name = "study_date")
    private Date studyDate;

    private String patientSex;
    private String age;
    private Date birthDate;

    private String comment;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id")
    private Radiologist radiologist;

    public Long getDicomId() {
        return dicomId;
    }

    public void setDicomId(Long dicomId) {
        this.dicomId = dicomId;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public Date getStudyDate() {
        return studyDate;
    }

    public void setStudyDate(Date studyDate) {
        this.studyDate = studyDate;
    }

    public String getModality() {
        return modality;
    }

    public void setModality(String modality) {
        this.modality = modality;
    }

    public Radiologist getRadiologist() {
        return radiologist;
    }

    public void setRadiologist(Radiologist radiologist) {
        this.radiologist = radiologist;
    }

    @Column(name = "modality")
    private String modality;

    // other attributes...

    public String getPatientSex() {
        return patientSex;
    }

    public void setPatientSex(String patientSex) {
        this.patientSex = patientSex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
}