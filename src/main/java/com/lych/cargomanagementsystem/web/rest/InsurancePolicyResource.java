package com.lych.cargomanagementsystem.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.lych.cargomanagementsystem.service.InsurancePolicyService;
import com.lych.cargomanagementsystem.service.dto.InsurancePolicyDTO;
import com.lych.cargomanagementsystem.web.rest.errors.BadRequestAlertException;
import com.lych.cargomanagementsystem.web.rest.util.HeaderUtil;
import com.lych.cargomanagementsystem.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing InsurancePolicy.
 */
@RestController
@RequestMapping("/api")
public class InsurancePolicyResource {

    private final Logger log = LoggerFactory.getLogger(InsurancePolicyResource.class);

    private static final String ENTITY_NAME = "insurancePolicy";

    private final InsurancePolicyService insurancePolicyService;

    public InsurancePolicyResource(InsurancePolicyService insurancePolicyService) {
        this.insurancePolicyService = insurancePolicyService;
    }

    /**
     * POST  /insurance-policies : Create a new insurancePolicy.
     *
     * @param insurancePolicyDTO the insurancePolicyDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new insurancePolicyDTO, or with status 400 (Bad Request) if the insurancePolicy has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/insurance-policies")
    @Timed
    public ResponseEntity<InsurancePolicyDTO> createInsurancePolicy(@Valid @RequestBody InsurancePolicyDTO insurancePolicyDTO) throws URISyntaxException {
        log.debug("REST request to save InsurancePolicy : {}", insurancePolicyDTO);
        if (insurancePolicyDTO.getId() != null) {
            throw new BadRequestAlertException("A new insurancePolicy cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InsurancePolicyDTO result = insurancePolicyService.save(insurancePolicyDTO);
        return ResponseEntity.created(new URI("/api/insurance-policies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /insurance-policies : Updates an existing insurancePolicy.
     *
     * @param insurancePolicyDTO the insurancePolicyDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated insurancePolicyDTO,
     * or with status 400 (Bad Request) if the insurancePolicyDTO is not valid,
     * or with status 500 (Internal Server Error) if the insurancePolicyDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/insurance-policies")
    @Timed
    public ResponseEntity<InsurancePolicyDTO> updateInsurancePolicy(@Valid @RequestBody InsurancePolicyDTO insurancePolicyDTO) throws URISyntaxException {
        log.debug("REST request to update InsurancePolicy : {}", insurancePolicyDTO);
        if (insurancePolicyDTO.getId() == null) {
            return createInsurancePolicy(insurancePolicyDTO);
        }
        InsurancePolicyDTO result = insurancePolicyService.save(insurancePolicyDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, insurancePolicyDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /insurance-policies : get all the insurancePolicies.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of insurancePolicies in body
     */
    @GetMapping("/insurance-policies")
    @Timed
    public ResponseEntity<List<InsurancePolicyDTO>> getAllInsurancePolicies(Pageable pageable) {
        log.debug("REST request to get a page of InsurancePolicies");
        Page<InsurancePolicyDTO> page = insurancePolicyService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/insurance-policies");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

//    /**
//     * GET  /insurance-policies : get all the insurancePolicies.
//     *
//     * @param pageable the pagination information
//     * @return the ResponseEntity with status 200 (OK) and the list of insurancePolicies in body
//     */
//    @GetMapping("/insurance-policies/drivers/{driverId}")
//    @Timed
//    public ResponseEntity<List<InsurancePolicyDTO>> getAllInsurancePolicies(Pageable pageable, @PathVariable Long driverId) {
//        log.debug("REST request to get a page of InsurancePolicies");
//        Page<InsurancePolicyDTO> page = insurancePolicyService.findAllByDriver(pageable, driverId);
//        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/insurance-policies/drivers/"
//            + driverId);
//        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
//    }

    /**
     * GET  /insurance-policies/:id : get the "id" insurancePolicy.
     *
     * @param id the id of the insurancePolicyDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the insurancePolicyDTO, or with status 404 (Not Found)
     */
    @GetMapping("/insurance-policies/{id}")
    @Timed
    public ResponseEntity<InsurancePolicyDTO> getInsurancePolicy(@PathVariable Long id) {
        log.debug("REST request to get InsurancePolicy : {}", id);
        InsurancePolicyDTO insurancePolicyDTO = insurancePolicyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(insurancePolicyDTO));
    }

    /**
     * DELETE  /insurance-policies/:id : delete the "id" insurancePolicy.
     *
     * @param id the id of the insurancePolicyDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/insurance-policies/{id}")
    @Timed
    public ResponseEntity<Void> deleteInsurancePolicy(@PathVariable Long id) {
        log.debug("REST request to delete InsurancePolicy : {}", id);
        insurancePolicyService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
