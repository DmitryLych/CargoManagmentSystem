package com.lych.cargomanagementsystem.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.lych.cargomanagementsystem.service.MedicalExaminationService;
import com.lych.cargomanagementsystem.web.rest.errors.BadRequestAlertException;
import com.lych.cargomanagementsystem.web.rest.util.HeaderUtil;
import com.lych.cargomanagementsystem.web.rest.util.PaginationUtil;
import com.lych.cargomanagementsystem.service.dto.MedicalExaminationDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing MedicalExamination.
 */
@RestController
@RequestMapping("/api")
public class MedicalExaminationResource {

    private final Logger log = LoggerFactory.getLogger(MedicalExaminationResource.class);

    private static final String ENTITY_NAME = "medicalExamination";

    private final MedicalExaminationService medicalExaminationService;

    public MedicalExaminationResource(MedicalExaminationService medicalExaminationService) {
        this.medicalExaminationService = medicalExaminationService;
    }

    /**
     * POST  /medical-examinations : Create a new medicalExamination.
     *
     * @param medicalExaminationDTO the medicalExaminationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new medicalExaminationDTO, or with status 400 (Bad Request) if the medicalExamination has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/medical-examinations")
    @Timed
    public ResponseEntity<MedicalExaminationDTO> createMedicalExamination(@Valid @RequestBody MedicalExaminationDTO medicalExaminationDTO) throws URISyntaxException {
        log.debug("REST request to save MedicalExamination : {}", medicalExaminationDTO);
        if (medicalExaminationDTO.getId() != null) {
            throw new BadRequestAlertException("A new medicalExamination cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MedicalExaminationDTO result = medicalExaminationService.save(medicalExaminationDTO);
        return ResponseEntity.created(new URI("/api/medical-examinations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /medical-examinations : Updates an existing medicalExamination.
     *
     * @param medicalExaminationDTO the medicalExaminationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated medicalExaminationDTO,
     * or with status 400 (Bad Request) if the medicalExaminationDTO is not valid,
     * or with status 500 (Internal Server Error) if the medicalExaminationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/medical-examinations")
    @Timed
    public ResponseEntity<MedicalExaminationDTO> updateMedicalExamination(@Valid @RequestBody MedicalExaminationDTO medicalExaminationDTO) throws URISyntaxException {
        log.debug("REST request to update MedicalExamination : {}", medicalExaminationDTO);
        if (medicalExaminationDTO.getId() == null) {
            return createMedicalExamination(medicalExaminationDTO);
        }
        MedicalExaminationDTO result = medicalExaminationService.save(medicalExaminationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, medicalExaminationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /medical-examinations : get all the medicalExaminations.
     *
     * @param pageable the pagination information
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of medicalExaminations in body
     */
    @GetMapping("/medical-examinations")
    @Timed
    public ResponseEntity<List<MedicalExaminationDTO>> getAllMedicalExaminations(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("driver-is-null".equals(filter)) {
            log.debug("REST request to get all MedicalExaminations where driver is null");
            return new ResponseEntity<>(medicalExaminationService.findAllWhereDriverIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of MedicalExaminations");
        Page<MedicalExaminationDTO> page = medicalExaminationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/medical-examinations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /medical-examinations/:id : get the "id" medicalExamination.
     *
     * @param id the id of the medicalExaminationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the medicalExaminationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/medical-examinations/{id}")
    @Timed
    public ResponseEntity<MedicalExaminationDTO> getMedicalExamination(@PathVariable Long id) {
        log.debug("REST request to get MedicalExamination : {}", id);
        MedicalExaminationDTO medicalExaminationDTO = medicalExaminationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(medicalExaminationDTO));
    }

    /**
     * DELETE  /medical-examinations/:id : delete the "id" medicalExamination.
     *
     * @param id the id of the medicalExaminationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/medical-examinations/{id}")
    @Timed
    public ResponseEntity<Void> deleteMedicalExamination(@PathVariable Long id) {
        log.debug("REST request to delete MedicalExamination : {}", id);
        medicalExaminationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
