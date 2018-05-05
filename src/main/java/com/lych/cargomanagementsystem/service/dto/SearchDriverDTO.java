package com.lych.cargomanagementsystem.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class SearchDriverDTO implements Serializable {

    private static final long serialVersionUID = -6150238362527712415L;

    private Long id;
    private String fullName;
}
