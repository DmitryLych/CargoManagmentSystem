package com.lych.cargomanagementsystem.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class SearchOrderDTO implements Serializable {

    private static final long serialVersionUID = 2838991456456773562L;

    private Long id;
    private String addresses;
}
