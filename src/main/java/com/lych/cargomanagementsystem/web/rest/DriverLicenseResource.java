package com.lych.cargomanagementsystem.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.lych.cargomanagementsystem.service.DriverLicenseService;
import com.lych.cargomanagementsystem.web.rest.errors.BadRequestAlertException;
import com.lych.cargomanagementsystem.web.rest.util.HeaderUtil;
import com.lych.cargomanagementsystem.web.rest.util.PaginationUtil;
import com.lych.cargomanagementsystem.service.dto.DriverLicenseDTO;
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
 * REST controller for managing DriverLicense.
 */
@RestController
@RequestMapping("/api")
public class DriverLicenseResource {

    private final Logger log = LoggerFactory.getLogger(DriverLicenseResource.class);

    private static final String ENTITY_NAME = "driverLicense";

    private final DriverLicenseService driverLicenseService;

    public DriverLicenseResource(DriverLicenseService driverLicenseService) {
        this.driverLicenseService = driverLicenseService;
    }

    /**
     * POST  /driver-licenses : Create a new driverLicense.
     *
     * @param driverLicenseDTO the driverLicenseDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new driverLicenseDTO, or with status 400 (Bad Request) if the driverLicense has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/driver-licenses")
    @Timed
    public ResponseEntity<DriverLicenseDTO> createDriverLicense(@Valid @RequestBody DriverLicenseDTO driverLicenseDTO) throws URISyntaxException {
        log.debug("REST request to save DriverLicense : {}", driverLicenseDTO);
        if (driverLicenseDTO.getId() != null) {
            throw new BadRequestAlertException("A new driverLicense cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DriverLicenseDTO result = driverLicenseService.save(driverLicenseDTO);
        return ResponseEntity.created(new URI("/api/driver-licenses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /driver-licenses : Updates an existing driverLicense.
     *
     * @param driverLicenseDTO the driverLicenseDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated driverLicenseDTO,
     * or with status 400 (Bad Request) if the driverLicenseDTO is not valid,
     * or with status 500 (Internal Server Error) if the driverLicenseDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/driver-licenses")
    @Timed
    public ResponseEntity<DriverLicenseDTO> updateDriverLicense(@Valid @RequestBody DriverLicenseDTO driverLicenseDTO) throws URISyntaxException {
        log.debug("REST request to update DriverLicense : {}", driverLicenseDTO);
        if (driverLicenseDTO.getId() == null) {
            return createDriverLicense(driverLicenseDTO);
        }
        DriverLicenseDTO result = driverLicenseService.save(driverLicenseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, driverLicenseDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /driver-licenses : get all the driverLicenses.
     *
     * @param pageable the pagination information
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of driverLicenses in body
     */
    @GetMapping("/driver-licenses")
    @Timed
    public ResponseEntity<List<DriverLicenseDTO>> getAllDriverLicenses(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("driver-is-null".equals(filter)) {
            log.debug("REST request to get all DriverLicenses where driver is null");
            return new ResponseEntity<>(driverLicenseService.findAllWhereDriverIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of DriverLicenses");
        Page<DriverLicenseDTO> page = driverLicenseService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/driver-licenses");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /driver-licenses/:id : get the "id" driverLicense.
     *
     * @param id the id of the driverLicenseDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the driverLicenseDTO, or with status 404 (Not Found)
     */
    @GetMapping("/driver-licenses/{id}")
    @Timed
    public ResponseEntity<DriverLicenseDTO> getDriverLicense(@PathVariable Long id) {
        log.debug("REST request to get DriverLicense : {}", id);
        DriverLicenseDTO driverLicenseDTO = driverLicenseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(driverLicenseDTO));
    }

    /**
     * DELETE  /driver-licenses/:id : delete the "id" driverLicense.
     *
     * @param id the id of the driverLicenseDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/driver-licenses/{id}")
    @Timed
    public ResponseEntity<Void> deleteDriverLicense(@PathVariable Long id) {
        log.debug("REST request to delete DriverLicense : {}", id);
        driverLicenseService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
