package com.lych.cargomanagementsystem.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class DetailCustomerDTO implements Serializable {

    private static final long serialVersionUID = -3721679782080342710L;

    private Long id;
    private String address;
    private String customerName;
    private String email;
    private String companyTelephoneNumber;
    private String mobileTelephoneNumber;
    private List<SearchOrderDTO> orders;
}
