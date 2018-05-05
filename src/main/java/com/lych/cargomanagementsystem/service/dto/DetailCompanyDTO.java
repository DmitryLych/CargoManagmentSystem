package com.lych.cargomanagementsystem.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class DetailCompanyDTO implements Serializable {

    private static final long serialVersionUID = -615984514913126157L;

    private Long id;
    private String companyName;
    private String address;
    private String email;
    private String telephoneNumber;
    private List<SearchDriverDTO> drivers;
}
