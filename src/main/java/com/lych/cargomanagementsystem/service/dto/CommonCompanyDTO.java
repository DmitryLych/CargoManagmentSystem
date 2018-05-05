package com.lych.cargomanagementsystem.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CommonCompanyDTO implements Serializable {

    private static final long serialVersionUID = -7388197540233347322L;

    private Long id;
    private String companyName;
    private String address;
    private String email;
    private String telephoneNumber;
}
