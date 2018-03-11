package com.lych.cargomanagementsystem.web.rest;

import com.lych.cargomanagementsystem.CargoManagmentSystemApp;

import com.lych.cargomanagementsystem.domain.Trailer;
import com.lych.cargomanagementsystem.repository.TrailerRepository;
import com.lych.cargomanagementsystem.service.TrailerService;
import com.lych.cargomanagementsystem.service.dto.TrailerDTO;
import com.lych.cargomanagementsystem.service.mapper.TrailerMapper;
import com.lych.cargomanagementsystem.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.lych.cargomanagementsystem.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TrailerResource REST controller.
 *
 * @see TrailerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CargoManagmentSystemApp.class)
public class TrailerResourceIntTest {

    private static final String DEFAULT_REGISTER_SIGN = "AAAAAAAAAA";
    private static final String UPDATED_REGISTER_SIGN = "BBBBBBBBBB";

    private static final String DEFAULT_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_COLOR = "BBBBBBBBBB";

    private static final String DEFAULT_TRAILER_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TRAILER_TYPE = "BBBBBBBBBB";

    private static final Double DEFAULT_WEIGHT = 1D;
    private static final Double UPDATED_WEIGHT = 2D;

    private static final Double DEFAULT_HEIGHT = 1D;
    private static final Double UPDATED_HEIGHT = 2D;

    private static final Double DEFAULT_LONGEST = 1D;
    private static final Double UPDATED_LONGEST = 2D;

    private static final Integer DEFAULT_VOLUME = 1;
    private static final Integer UPDATED_VOLUME = 2;

    private static final LocalDate DEFAULT_YEAR_OF_ISSUE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_YEAR_OF_ISSUE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private TrailerRepository trailerRepository;

    @Autowired
    private TrailerMapper trailerMapper;

    @Autowired
    private TrailerService trailerService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTrailerMockMvc;

    private Trailer trailer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TrailerResource trailerResource = new TrailerResource(trailerService);
        this.restTrailerMockMvc = MockMvcBuilders.standaloneSetup(trailerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Trailer createEntity(EntityManager em) {
        Trailer trailer = new Trailer()
            .registerSign(DEFAULT_REGISTER_SIGN)
            .color(DEFAULT_COLOR)
            .trailerType(DEFAULT_TRAILER_TYPE)
            .weight(DEFAULT_WEIGHT)
            .height(DEFAULT_HEIGHT)
            .longest(DEFAULT_LONGEST)
            .volume(DEFAULT_VOLUME)
            .yearOfIssue(DEFAULT_YEAR_OF_ISSUE);
        return trailer;
    }

    @Before
    public void initTest() {
        trailer = createEntity(em);
    }

    @Test
    @Transactional
    public void createTrailer() throws Exception {
        int databaseSizeBeforeCreate = trailerRepository.findAll().size();

        // Create the Trailer
        TrailerDTO trailerDTO = trailerMapper.toDto(trailer);
        restTrailerMockMvc.perform(post("/api/trailers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trailerDTO)))
            .andExpect(status().isCreated());

        // Validate the Trailer in the database
        List<Trailer> trailerList = trailerRepository.findAll();
        assertThat(trailerList).hasSize(databaseSizeBeforeCreate + 1);
        Trailer testTrailer = trailerList.get(trailerList.size() - 1);
        assertThat(testTrailer.getRegisterSign()).isEqualTo(DEFAULT_REGISTER_SIGN);
        assertThat(testTrailer.getColor()).isEqualTo(DEFAULT_COLOR);
        assertThat(testTrailer.getTrailerType()).isEqualTo(DEFAULT_TRAILER_TYPE);
        assertThat(testTrailer.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testTrailer.getHeight()).isEqualTo(DEFAULT_HEIGHT);
        assertThat(testTrailer.getLongest()).isEqualTo(DEFAULT_LONGEST);
        assertThat(testTrailer.getVolume()).isEqualTo(DEFAULT_VOLUME);
        assertThat(testTrailer.getYearOfIssue()).isEqualTo(DEFAULT_YEAR_OF_ISSUE);
    }

    @Test
    @Transactional
    public void createTrailerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = trailerRepository.findAll().size();

        // Create the Trailer with an existing ID
        trailer.setId(1L);
        TrailerDTO trailerDTO = trailerMapper.toDto(trailer);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTrailerMockMvc.perform(post("/api/trailers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trailerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Trailer in the database
        List<Trailer> trailerList = trailerRepository.findAll();
        assertThat(trailerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkRegisterSignIsRequired() throws Exception {
        int databaseSizeBeforeTest = trailerRepository.findAll().size();
        // set the field null
        trailer.setRegisterSign(null);

        // Create the Trailer, which fails.
        TrailerDTO trailerDTO = trailerMapper.toDto(trailer);

        restTrailerMockMvc.perform(post("/api/trailers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trailerDTO)))
            .andExpect(status().isBadRequest());

        List<Trailer> trailerList = trailerRepository.findAll();
        assertThat(trailerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkColorIsRequired() throws Exception {
        int databaseSizeBeforeTest = trailerRepository.findAll().size();
        // set the field null
        trailer.setColor(null);

        // Create the Trailer, which fails.
        TrailerDTO trailerDTO = trailerMapper.toDto(trailer);

        restTrailerMockMvc.perform(post("/api/trailers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trailerDTO)))
            .andExpect(status().isBadRequest());

        List<Trailer> trailerList = trailerRepository.findAll();
        assertThat(trailerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTrailerTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = trailerRepository.findAll().size();
        // set the field null
        trailer.setTrailerType(null);

        // Create the Trailer, which fails.
        TrailerDTO trailerDTO = trailerMapper.toDto(trailer);

        restTrailerMockMvc.perform(post("/api/trailers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trailerDTO)))
            .andExpect(status().isBadRequest());

        List<Trailer> trailerList = trailerRepository.findAll();
        assertThat(trailerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkWeightIsRequired() throws Exception {
        int databaseSizeBeforeTest = trailerRepository.findAll().size();
        // set the field null
        trailer.setWeight(null);

        // Create the Trailer, which fails.
        TrailerDTO trailerDTO = trailerMapper.toDto(trailer);

        restTrailerMockMvc.perform(post("/api/trailers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trailerDTO)))
            .andExpect(status().isBadRequest());

        List<Trailer> trailerList = trailerRepository.findAll();
        assertThat(trailerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHeightIsRequired() throws Exception {
        int databaseSizeBeforeTest = trailerRepository.findAll().size();
        // set the field null
        trailer.setHeight(null);

        // Create the Trailer, which fails.
        TrailerDTO trailerDTO = trailerMapper.toDto(trailer);

        restTrailerMockMvc.perform(post("/api/trailers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trailerDTO)))
            .andExpect(status().isBadRequest());

        List<Trailer> trailerList = trailerRepository.findAll();
        assertThat(trailerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLongestIsRequired() throws Exception {
        int databaseSizeBeforeTest = trailerRepository.findAll().size();
        // set the field null
        trailer.setLongest(null);

        // Create the Trailer, which fails.
        TrailerDTO trailerDTO = trailerMapper.toDto(trailer);

        restTrailerMockMvc.perform(post("/api/trailers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trailerDTO)))
            .andExpect(status().isBadRequest());

        List<Trailer> trailerList = trailerRepository.findAll();
        assertThat(trailerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkYearOfIssueIsRequired() throws Exception {
        int databaseSizeBeforeTest = trailerRepository.findAll().size();
        // set the field null
        trailer.setYearOfIssue(null);

        // Create the Trailer, which fails.
        TrailerDTO trailerDTO = trailerMapper.toDto(trailer);

        restTrailerMockMvc.perform(post("/api/trailers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trailerDTO)))
            .andExpect(status().isBadRequest());

        List<Trailer> trailerList = trailerRepository.findAll();
        assertThat(trailerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTrailers() throws Exception {
        // Initialize the database
        trailerRepository.saveAndFlush(trailer);

        // Get all the trailerList
        restTrailerMockMvc.perform(get("/api/trailers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trailer.getId().intValue())))
            .andExpect(jsonPath("$.[*].registerSign").value(hasItem(DEFAULT_REGISTER_SIGN.toString())))
            .andExpect(jsonPath("$.[*].color").value(hasItem(DEFAULT_COLOR.toString())))
            .andExpect(jsonPath("$.[*].trailerType").value(hasItem(DEFAULT_TRAILER_TYPE.toString())))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].height").value(hasItem(DEFAULT_HEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].longest").value(hasItem(DEFAULT_LONGEST.doubleValue())))
            .andExpect(jsonPath("$.[*].volume").value(hasItem(DEFAULT_VOLUME)))
            .andExpect(jsonPath("$.[*].yearOfIssue").value(hasItem(DEFAULT_YEAR_OF_ISSUE.toString())));
    }

    @Test
    @Transactional
    public void getTrailer() throws Exception {
        // Initialize the database
        trailerRepository.saveAndFlush(trailer);

        // Get the trailer
        restTrailerMockMvc.perform(get("/api/trailers/{id}", trailer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(trailer.getId().intValue()))
            .andExpect(jsonPath("$.registerSign").value(DEFAULT_REGISTER_SIGN.toString()))
            .andExpect(jsonPath("$.color").value(DEFAULT_COLOR.toString()))
            .andExpect(jsonPath("$.trailerType").value(DEFAULT_TRAILER_TYPE.toString()))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT.doubleValue()))
            .andExpect(jsonPath("$.height").value(DEFAULT_HEIGHT.doubleValue()))
            .andExpect(jsonPath("$.longest").value(DEFAULT_LONGEST.doubleValue()))
            .andExpect(jsonPath("$.volume").value(DEFAULT_VOLUME))
            .andExpect(jsonPath("$.yearOfIssue").value(DEFAULT_YEAR_OF_ISSUE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTrailer() throws Exception {
        // Get the trailer
        restTrailerMockMvc.perform(get("/api/trailers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTrailer() throws Exception {
        // Initialize the database
        trailerRepository.saveAndFlush(trailer);
        int databaseSizeBeforeUpdate = trailerRepository.findAll().size();

        // Update the trailer
        Trailer updatedTrailer = trailerRepository.findOne(trailer.getId());
        // Disconnect from session so that the updates on updatedTrailer are not directly saved in db
        em.detach(updatedTrailer);
        updatedTrailer
            .registerSign(UPDATED_REGISTER_SIGN)
            .color(UPDATED_COLOR)
            .trailerType(UPDATED_TRAILER_TYPE)
            .weight(UPDATED_WEIGHT)
            .height(UPDATED_HEIGHT)
            .longest(UPDATED_LONGEST)
            .volume(UPDATED_VOLUME)
            .yearOfIssue(UPDATED_YEAR_OF_ISSUE);
        TrailerDTO trailerDTO = trailerMapper.toDto(updatedTrailer);

        restTrailerMockMvc.perform(put("/api/trailers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trailerDTO)))
            .andExpect(status().isOk());

        // Validate the Trailer in the database
        List<Trailer> trailerList = trailerRepository.findAll();
        assertThat(trailerList).hasSize(databaseSizeBeforeUpdate);
        Trailer testTrailer = trailerList.get(trailerList.size() - 1);
        assertThat(testTrailer.getRegisterSign()).isEqualTo(UPDATED_REGISTER_SIGN);
        assertThat(testTrailer.getColor()).isEqualTo(UPDATED_COLOR);
        assertThat(testTrailer.getTrailerType()).isEqualTo(UPDATED_TRAILER_TYPE);
        assertThat(testTrailer.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testTrailer.getHeight()).isEqualTo(UPDATED_HEIGHT);
        assertThat(testTrailer.getLongest()).isEqualTo(UPDATED_LONGEST);
        assertThat(testTrailer.getVolume()).isEqualTo(UPDATED_VOLUME);
        assertThat(testTrailer.getYearOfIssue()).isEqualTo(UPDATED_YEAR_OF_ISSUE);
    }

    @Test
    @Transactional
    public void updateNonExistingTrailer() throws Exception {
        int databaseSizeBeforeUpdate = trailerRepository.findAll().size();

        // Create the Trailer
        TrailerDTO trailerDTO = trailerMapper.toDto(trailer);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTrailerMockMvc.perform(put("/api/trailers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trailerDTO)))
            .andExpect(status().isCreated());

        // Validate the Trailer in the database
        List<Trailer> trailerList = trailerRepository.findAll();
        assertThat(trailerList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTrailer() throws Exception {
        // Initialize the database
        trailerRepository.saveAndFlush(trailer);
        int databaseSizeBeforeDelete = trailerRepository.findAll().size();

        // Get the trailer
        restTrailerMockMvc.perform(delete("/api/trailers/{id}", trailer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Trailer> trailerList = trailerRepository.findAll();
        assertThat(trailerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Trailer.class);
        Trailer trailer1 = new Trailer();
        trailer1.setId(1L);
        Trailer trailer2 = new Trailer();
        trailer2.setId(trailer1.getId());
        assertThat(trailer1).isEqualTo(trailer2);
        trailer2.setId(2L);
        assertThat(trailer1).isNotEqualTo(trailer2);
        trailer1.setId(null);
        assertThat(trailer1).isNotEqualTo(trailer2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TrailerDTO.class);
        TrailerDTO trailerDTO1 = new TrailerDTO();
        trailerDTO1.setId(1L);
        TrailerDTO trailerDTO2 = new TrailerDTO();
        assertThat(trailerDTO1).isNotEqualTo(trailerDTO2);
        trailerDTO2.setId(trailerDTO1.getId());
        assertThat(trailerDTO1).isEqualTo(trailerDTO2);
        trailerDTO2.setId(2L);
        assertThat(trailerDTO1).isNotEqualTo(trailerDTO2);
        trailerDTO1.setId(null);
        assertThat(trailerDTO1).isNotEqualTo(trailerDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(trailerMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(trailerMapper.fromId(null)).isNull();
    }
}
