package com.lych.cargomanagementsystem.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.lych.cargomanagementsystem.service.TrailerService;
import com.lych.cargomanagementsystem.web.rest.errors.BadRequestAlertException;
import com.lych.cargomanagementsystem.web.rest.util.HeaderUtil;
import com.lych.cargomanagementsystem.web.rest.util.PaginationUtil;
import com.lych.cargomanagementsystem.service.dto.TrailerDTO;
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
 * REST controller for managing Trailer.
 */
@RestController
@RequestMapping("/api")
public class TrailerResource {

    private final Logger log = LoggerFactory.getLogger(TrailerResource.class);

    private static final String ENTITY_NAME = "trailer";

    private final TrailerService trailerService;

    public TrailerResource(TrailerService trailerService) {
        this.trailerService = trailerService;
    }

    /**
     * POST  /trailers : Create a new trailer.
     *
     * @param trailerDTO the trailerDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new trailerDTO, or with status 400 (Bad Request) if the trailer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/trailers")
    @Timed
    public ResponseEntity<TrailerDTO> createTrailer(@Valid @RequestBody TrailerDTO trailerDTO) throws URISyntaxException {
        log.debug("REST request to save Trailer : {}", trailerDTO);
        if (trailerDTO.getId() != null) {
            throw new BadRequestAlertException("A new trailer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TrailerDTO result = trailerService.save(trailerDTO);
        return ResponseEntity.created(new URI("/api/trailers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /trailers : Updates an existing trailer.
     *
     * @param trailerDTO the trailerDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated trailerDTO,
     * or with status 400 (Bad Request) if the trailerDTO is not valid,
     * or with status 500 (Internal Server Error) if the trailerDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/trailers")
    @Timed
    public ResponseEntity<TrailerDTO> updateTrailer(@Valid @RequestBody TrailerDTO trailerDTO) throws URISyntaxException {
        log.debug("REST request to update Trailer : {}", trailerDTO);
        if (trailerDTO.getId() == null) {
            return createTrailer(trailerDTO);
        }
        TrailerDTO result = trailerService.save(trailerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, trailerDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /trailers : get all the trailers.
     *
     * @param pageable the pagination information
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of trailers in body
     */
    @GetMapping("/trailers")
    @Timed
    public ResponseEntity<List<TrailerDTO>> getAllTrailers(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("truck-is-null".equals(filter)) {
            log.debug("REST request to get all Trailers where truck is null");
            return new ResponseEntity<>(trailerService.findAllWhereTruckIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of Trailers");
        Page<TrailerDTO> page = trailerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/trailers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /trailers/:id : get the "id" trailer.
     *
     * @param id the id of the trailerDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the trailerDTO, or with status 404 (Not Found)
     */
    @GetMapping("/trailers/{id}")
    @Timed
    public ResponseEntity<TrailerDTO> getTrailer(@PathVariable Long id) {
        log.debug("REST request to get Trailer : {}", id);
        TrailerDTO trailerDTO = trailerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(trailerDTO));
    }

    /**
     * DELETE  /trailers/:id : delete the "id" trailer.
     *
     * @param id the id of the trailerDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/trailers/{id}")
    @Timed
    public ResponseEntity<Void> deleteTrailer(@PathVariable Long id) {
        log.debug("REST request to delete Trailer : {}", id);
        trailerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
