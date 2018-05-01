package com.lych.cargomanagementsystem.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CommonGoodsDTO implements Serializable {

    private static final long serialVersionUID = 5344255038908466430L;

    private Long id;
    private String name;
}
