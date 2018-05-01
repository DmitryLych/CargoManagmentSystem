package com.lych.cargomanagementsystem.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CommonInsurancePolicyDTO implements Serializable {

    private static final long serialVersionUID = -6888043886555753816L;

    private Long id;
    private String type;
}
