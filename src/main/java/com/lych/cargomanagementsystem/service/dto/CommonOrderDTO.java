package com.lych.cargomanagementsystem.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CommonOrderDTO implements Serializable {

    private static final long serialVersionUID = -9127790671904419752L;

    private Long id;
    private String addresses;
}
