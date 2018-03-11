package com.lych.cargomanagementsystem.web.rest;

import com.lych.cargomanagementsystem.CargoManagmentSystemApp;

import com.lych.cargomanagementsystem.domain.DriverLicense;
import com.lych.cargomanagementsystem.repository.DriverLicenseRepository;
import com.lych.cargomanagementsystem.service.DriverLicenseService;
import com.lych.cargomanagementsystem.service.dto.DriverLicenseDTO;
import com.lych.cargomanagementsystem.service.mapper.DriverLicenseMapper;
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
 * Test class for the DriverLicenseResource REST controller.
 *
 * @see DriverLicenseResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CargoManagmentSystemApp.class)
public class DriverLicenseResourceIntTest {

    private static final String DEFAULT_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_VALIDATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALIDATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_SPECIAL_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_SPECIAL_NOTES = "BBBBBBBBBB";

    @Autowired
    private DriverLicenseRepository driverLicenseRepository;

    @Autowired
    private DriverLicenseMapper driverLicenseMapper;

    @Autowired
    private DriverLicenseService driverLicenseService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDriverLicenseMockMvc;

    private DriverLicense driverLicense;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DriverLicenseResource driverLicenseResource = new DriverLicenseResource(driverLicenseService);
        this.restDriverLicenseMockMvc = MockMvcBuilders.standaloneSetup(driverLicenseResource)
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
    public static DriverLicense createEntity(EntityManager em) {
        DriverLicense driverLicense = new DriverLicense()
            .category(DEFAULT_CATEGORY)
            .validate(DEFAULT_VALIDATE)
            .specialNotes(DEFAULT_SPECIAL_NOTES);
        return driverLicense;
    }

    @Before
    public void initTest() {
        driverLicense = createEntity(em);
    }

    @Test
    @Transactional
    public void createDriverLicense() throws Exception {
        int databaseSizeBeforeCreate = driverLicenseRepository.findAll().size();

        // Create the DriverLicense
        DriverLicenseDTO driverLicenseDTO = driverLicenseMapper.toDto(driverLicense);
        restDriverLicenseMockMvc.perform(post("/api/driver-licenses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(driverLicenseDTO)))
            .andExpect(status().isCreated());

        // Validate the DriverLicense in the database
        List<DriverLicense> driverLicenseList = driverLicenseRepository.findAll();
        assertThat(driverLicenseList).hasSize(databaseSizeBeforeCreate + 1);
        DriverLicense testDriverLicense = driverLicenseList.get(driverLicenseList.size() - 1);
        assertThat(testDriverLicense.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testDriverLicense.getValidate()).isEqualTo(DEFAULT_VALIDATE);
        assertThat(testDriverLicense.getSpecialNotes()).isEqualTo(DEFAULT_SPECIAL_NOTES);
    }

    @Test
    @Transactional
    public void createDriverLicenseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = driverLicenseRepository.findAll().size();

        // Create the DriverLicense with an existing ID
        driverLicense.setId(1L);
        DriverLicenseDTO driverLicenseDTO = driverLicenseMapper.toDto(driverLicense);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDriverLicenseMockMvc.perform(post("/api/driver-licenses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(driverLicenseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DriverLicense in the database
        List<DriverLicense> driverLicenseList = driverLicenseRepository.findAll();
        assertThat(driverLicenseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCategoryIsRequired() throws Exception {
        int databaseSizeBeforeTest = driverLicenseRepository.findAll().size();
        // set the field null
        driverLicense.setCategory(null);

        // Create the DriverLicense, which fails.
        DriverLicenseDTO driverLicenseDTO = driverLicenseMapper.toDto(driverLicense);

        restDriverLicenseMockMvc.perform(post("/api/driver-licenses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(driverLicenseDTO)))
            .andExpect(status().isBadRequest());

        List<DriverLicense> driverLicenseList = driverLicenseRepository.findAll();
        assertThat(driverLicenseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDriverLicenses() throws Exception {
        // Initialize the database
        driverLicenseRepository.saveAndFlush(driverLicense);

        // Get all the driverLicenseList
        restDriverLicenseMockMvc.perform(get("/api/driver-licenses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(driverLicense.getId().intValue())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].validate").value(hasItem(DEFAULT_VALIDATE.toString())))
            .andExpect(jsonPath("$.[*].specialNotes").value(hasItem(DEFAULT_SPECIAL_NOTES.toString())));
    }

    @Test
    @Transactional
    public void getDriverLicense() throws Exception {
        // Initialize the database
        driverLicenseRepository.saveAndFlush(driverLicense);

        // Get the driverLicense
        restDriverLicenseMockMvc.perform(get("/api/driver-licenses/{id}", driverLicense.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(driverLicense.getId().intValue()))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY.toString()))
            .andExpect(jsonPath("$.validate").value(DEFAULT_VALIDATE.toString()))
            .andExpect(jsonPath("$.specialNotes").value(DEFAULT_SPECIAL_NOTES.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDriverLicense() throws Exception {
        // Get the driverLicense
        restDriverLicenseMockMvc.perform(get("/api/driver-licenses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDriverLicense() throws Exception {
        // Initialize the database
        driverLicenseRepository.saveAndFlush(driverLicense);
        int databaseSizeBeforeUpdate = driverLicenseRepository.findAll().size();

        // Update the driverLicense
        DriverLicense updatedDriverLicense = driverLicenseRepository.findOne(driverLicense.getId());
        // Disconnect from session so that the updates on updatedDriverLicense are not directly saved in db
        em.detach(updatedDriverLicense);
        updatedDriverLicense
            .category(UPDATED_CATEGORY)
            .validate(UPDATED_VALIDATE)
            .specialNotes(UPDATED_SPECIAL_NOTES);
        DriverLicenseDTO driverLicenseDTO = driverLicenseMapper.toDto(updatedDriverLicense);

        restDriverLicenseMockMvc.perform(put("/api/driver-licenses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(driverLicenseDTO)))
            .andExpect(status().isOk());

        // Validate the DriverLicense in the database
        List<DriverLicense> driverLicenseList = driverLicenseRepository.findAll();
        assertThat(driverLicenseList).hasSize(databaseSizeBeforeUpdate);
        DriverLicense testDriverLicense = driverLicenseList.get(driverLicenseList.size() - 1);
        assertThat(testDriverLicense.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testDriverLicense.getValidate()).isEqualTo(UPDATED_VALIDATE);
        assertThat(testDriverLicense.getSpecialNotes()).isEqualTo(UPDATED_SPECIAL_NOTES);
    }

    @Test
    @Transactional
    public void updateNonExistingDriverLicense() throws Exception {
        int databaseSizeBeforeUpdate = driverLicenseRepository.findAll().size();

        // Create the DriverLicense
        DriverLicenseDTO driverLicenseDTO = driverLicenseMapper.toDto(driverLicense);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDriverLicenseMockMvc.perform(put("/api/driver-licenses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(driverLicenseDTO)))
            .andExpect(status().isCreated());

        // Validate the DriverLicense in the database
        List<DriverLicense> driverLicenseList = driverLicenseRepository.findAll();
        assertThat(driverLicenseList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDriverLicense() throws Exception {
        // Initialize the database
        driverLicenseRepository.saveAndFlush(driverLicense);
        int databaseSizeBeforeDelete = driverLicenseRepository.findAll().size();

        // Get the driverLicense
        restDriverLicenseMockMvc.perform(delete("/api/driver-licenses/{id}", driverLicense.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DriverLicense> driverLicenseList = driverLicenseRepository.findAll();
        assertThat(driverLicenseList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DriverLicense.class);
        DriverLicense driverLicense1 = new DriverLicense();
        driverLicense1.setId(1L);
        DriverLicense driverLicense2 = new DriverLicense();
        driverLicense2.setId(driverLicense1.getId());
        assertThat(driverLicense1).isEqualTo(driverLicense2);
        driverLicense2.setId(2L);
        assertThat(driverLicense1).isNotEqualTo(driverLicense2);
        driverLicense1.setId(null);
        assertThat(driverLicense1).isNotEqualTo(driverLicense2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DriverLicenseDTO.class);
        DriverLicenseDTO driverLicenseDTO1 = new DriverLicenseDTO();
        driverLicenseDTO1.setId(1L);
        DriverLicenseDTO driverLicenseDTO2 = new DriverLicenseDTO();
        assertThat(driverLicenseDTO1).isNotEqualTo(driverLicenseDTO2);
        driverLicenseDTO2.setId(driverLicenseDTO1.getId());
        assertThat(driverLicenseDTO1).isEqualTo(driverLicenseDTO2);
        driverLicenseDTO2.setId(2L);
        assertThat(driverLicenseDTO1).isNotEqualTo(driverLicenseDTO2);
        driverLicenseDTO1.setId(null);
        assertThat(driverLicenseDTO1).isNotEqualTo(driverLicenseDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(driverLicenseMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(driverLicenseMapper.fromId(null)).isNull();
    }
}
