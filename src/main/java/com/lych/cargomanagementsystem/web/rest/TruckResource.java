package com.lych.cargomanagementsystem.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.lych.cargomanagementsystem.service.TruckService;
import com.lych.cargomanagementsystem.web.rest.errors.BadRequestAlertException;
import com.lych.cargomanagementsystem.web.rest.util.HeaderUtil;
import com.lych.cargomanagementsystem.web.rest.util.PaginationUtil;
import com.lych.cargomanagementsystem.service.dto.TruckDTO;
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

/**
 * REST controller for managing Truck.
 */
@RestController
@RequestMapping("/api")
public class TruckResource {

    private final Logger log = LoggerFactory.getLogger(TruckResource.class);

    private static final String ENTITY_NAME = "truck";

    private final TruckService truckService;

    public TruckResource(TruckService truckService) {
        this.truckService = truckService;
    }

    /**
     * POST  /trucks : Create a new truck.
     *
     * @param truckDTO the truckDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new truckDTO, or with status 400 (Bad Request) if the truck has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/trucks")
    @Timed
    public ResponseEntity<TruckDTO> createTruck(@Valid @RequestBody TruckDTO truckDTO) throws URISyntaxException {
        log.debug("REST request to save Truck : {}", truckDTO);
        if (truckDTO.getId() != null) {
            throw new BadRequestAlertException("A new truck cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TruckDTO result = truckService.save(truckDTO);
        return ResponseEntity.created(new URI("/api/trucks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /trucks : Updates an existing truck.
     *
     * @param truckDTO the truckDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated truckDTO,
     * or with status 400 (Bad Request) if the truckDTO is not valid,
     * or with status 500 (Internal Server Error) if the truckDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/trucks")
    @Timed
    public ResponseEntity<TruckDTO> updateTruck(@Valid @RequestBody TruckDTO truckDTO) throws URISyntaxException {
        log.debug("REST request to update Truck : {}", truckDTO);
        if (truckDTO.getId() == null) {
            return createTruck(truckDTO);
        }
        TruckDTO result = truckService.save(truckDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, truckDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /trucks : get all the trucks.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of trucks in body
     */
    @GetMapping("/trucks")
    @Timed
    public ResponseEntity<List<TruckDTO>> getAllTrucks(Pageable pageable) {
        log.debug("REST request to get a page of Trucks");
        Page<TruckDTO> page = truckService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/trucks");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /trucks/:id : get the "id" truck.
     *
     * @param id the id of the truckDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the truckDTO, or with status 404 (Not Found)
     */
    @GetMapping("/trucks/{id}")
    @Timed
    public ResponseEntity<TruckDTO> getTruck(@PathVariable Long id) {
        log.debug("REST request to get Truck : {}", id);
        TruckDTO truckDTO = truckService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(truckDTO));
    }

    /**
     * DELETE  /trucks/:id : delete the "id" truck.
     *
     * @param id the id of the truckDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/trucks/{id}")
    @Timed
    public ResponseEntity<Void> deleteTruck(@PathVariable Long id) {
        log.debug("REST request to delete Truck : {}", id);
        truckService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
