package com.rmssecurity.RMS.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.rmssecurity.RMS.entity.OurUsers;
import com.rmssecurity.RMS.entity.Product;
import com.rmssecurity.RMS.entity.Radiologist;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReqRes {

    private int statusCode;
    private String error;
    private String message;
    private String token;
    private String refreshToken;
    private String expirationTime;
    private String name;
    private String email;
    private String role;
    private String password;
    private List<Product> products;
    private List<Radiologist> radiologists;
    private OurUsers ourUsers;
}