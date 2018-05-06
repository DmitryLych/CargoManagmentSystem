package com.lych.cargomanagementsystem.config;

import lombok.RequiredArgsConstructor;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DozerMapping {

    private final DozerBeanMapper mapper;

    /**
     * Init.
     */
    @PostConstruct
    public void init() {
        mapper.addMapping(new Driver2DriverDTO());
    }
}
