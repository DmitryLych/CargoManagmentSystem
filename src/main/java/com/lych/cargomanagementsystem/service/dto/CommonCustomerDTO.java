package com.lych.cargomanagementsystem.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CommonCustomerDTO implements Serializable {

    private static final long serialVersionUID = 6191463619736647616L;

    private Long id;
    private String address;
    private String customerName;
    private String email;
    private String companyTelephoneNumber;
    private String mobileTelephoneNumber;
}
