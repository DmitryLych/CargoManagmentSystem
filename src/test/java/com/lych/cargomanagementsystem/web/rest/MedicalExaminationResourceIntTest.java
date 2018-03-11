package com.lych.cargomanagementsystem.web.rest;

import com.lych.cargomanagementsystem.CargoManagmentSystemApp;

import com.lych.cargomanagementsystem.domain.MedicalExamination;
import com.lych.cargomanagementsystem.repository.MedicalExaminationRepository;
import com.lych.cargomanagementsystem.service.MedicalExaminationService;
import com.lych.cargomanagementsystem.service.dto.MedicalExaminationDTO;
import com.lych.cargomanagementsystem.service.mapper.MedicalExaminationMapper;
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
 * Test class for the MedicalExaminationResource REST controller.
 *
 * @see MedicalExaminationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CargoManagmentSystemApp.class)
public class MedicalExaminationResourceIntTest {

    private static final LocalDate DEFAULT_VALIDATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALIDATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private MedicalExaminationRepository medicalExaminationRepository;

    @Autowired
    private MedicalExaminationMapper medicalExaminationMapper;

    @Autowired
    private MedicalExaminationService medicalExaminationService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMedicalExaminationMockMvc;

    private MedicalExamination medicalExamination;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MedicalExaminationResource medicalExaminationResource = new MedicalExaminationResource(medicalExaminationService);
        this.restMedicalExaminationMockMvc = MockMvcBuilders.standaloneSetup(medicalExaminationResource)
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
    public static MedicalExamination createEntity(EntityManager em) {
        MedicalExamination medicalExamination = new MedicalExamination()
            .validate(DEFAULT_VALIDATE);
        return medicalExamination;
    }

    @Before
    public void initTest() {
        medicalExamination = createEntity(em);
    }

    @Test
    @Transactional
    public void createMedicalExamination() throws Exception {
        int databaseSizeBeforeCreate = medicalExaminationRepository.findAll().size();

        // Create the MedicalExamination
        MedicalExaminationDTO medicalExaminationDTO = medicalExaminationMapper.toDto(medicalExamination);
        restMedicalExaminationMockMvc.perform(post("/api/medical-examinations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicalExaminationDTO)))
            .andExpect(status().isCreated());

        // Validate the MedicalExamination in the database
        List<MedicalExamination> medicalExaminationList = medicalExaminationRepository.findAll();
        assertThat(medicalExaminationList).hasSize(databaseSizeBeforeCreate + 1);
        MedicalExamination testMedicalExamination = medicalExaminationList.get(medicalExaminationList.size() - 1);
        assertThat(testMedicalExamination.getValidate()).isEqualTo(DEFAULT_VALIDATE);
    }

    @Test
    @Transactional
    public void createMedicalExaminationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = medicalExaminationRepository.findAll().size();

        // Create the MedicalExamination with an existing ID
        medicalExamination.setId(1L);
        MedicalExaminationDTO medicalExaminationDTO = medicalExaminationMapper.toDto(medicalExamination);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMedicalExaminationMockMvc.perform(post("/api/medical-examinations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicalExaminationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MedicalExamination in the database
        List<MedicalExamination> medicalExaminationList = medicalExaminationRepository.findAll();
        assertThat(medicalExaminationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkValidateIsRequired() throws Exception {
        int databaseSizeBeforeTest = medicalExaminationRepository.findAll().size();
        // set the field null
        medicalExamination.setValidate(null);

        // Create the MedicalExamination, which fails.
        MedicalExaminationDTO medicalExaminationDTO = medicalExaminationMapper.toDto(medicalExamination);

        restMedicalExaminationMockMvc.perform(post("/api/medical-examinations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicalExaminationDTO)))
            .andExpect(status().isBadRequest());

        List<MedicalExamination> medicalExaminationList = medicalExaminationRepository.findAll();
        assertThat(medicalExaminationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMedicalExaminations() throws Exception {
        // Initialize the database
        medicalExaminationRepository.saveAndFlush(medicalExamination);

        // Get all the medicalExaminationList
        restMedicalExaminationMockMvc.perform(get("/api/medical-examinations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medicalExamination.getId().intValue())))
            .andExpect(jsonPath("$.[*].validate").value(hasItem(DEFAULT_VALIDATE.toString())));
    }

    @Test
    @Transactional
    public void getMedicalExamination() throws Exception {
        // Initialize the database
        medicalExaminationRepository.saveAndFlush(medicalExamination);

        // Get the medicalExamination
        restMedicalExaminationMockMvc.perform(get("/api/medical-examinations/{id}", medicalExamination.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(medicalExamination.getId().intValue()))
            .andExpect(jsonPath("$.validate").value(DEFAULT_VALIDATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMedicalExamination() throws Exception {
        // Get the medicalExamination
        restMedicalExaminationMockMvc.perform(get("/api/medical-examinations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMedicalExamination() throws Exception {
        // Initialize the database
        medicalExaminationRepository.saveAndFlush(medicalExamination);
        int databaseSizeBeforeUpdate = medicalExaminationRepository.findAll().size();

        // Update the medicalExamination
        MedicalExamination updatedMedicalExamination = medicalExaminationRepository.findOne(medicalExamination.getId());
        // Disconnect from session so that the updates on updatedMedicalExamination are not directly saved in db
        em.detach(updatedMedicalExamination);
        updatedMedicalExamination
            .validate(UPDATED_VALIDATE);
        MedicalExaminationDTO medicalExaminationDTO = medicalExaminationMapper.toDto(updatedMedicalExamination);

        restMedicalExaminationMockMvc.perform(put("/api/medical-examinations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicalExaminationDTO)))
            .andExpect(status().isOk());

        // Validate the MedicalExamination in the database
        List<MedicalExamination> medicalExaminationList = medicalExaminationRepository.findAll();
        assertThat(medicalExaminationList).hasSize(databaseSizeBeforeUpdate);
        MedicalExamination testMedicalExamination = medicalExaminationList.get(medicalExaminationList.size() - 1);
        assertThat(testMedicalExamination.getValidate()).isEqualTo(UPDATED_VALIDATE);
    }

    @Test
    @Transactional
    public void updateNonExistingMedicalExamination() throws Exception {
        int databaseSizeBeforeUpdate = medicalExaminationRepository.findAll().size();

        // Create the MedicalExamination
        MedicalExaminationDTO medicalExaminationDTO = medicalExaminationMapper.toDto(medicalExamination);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMedicalExaminationMockMvc.perform(put("/api/medical-examinations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicalExaminationDTO)))
            .andExpect(status().isCreated());

        // Validate the MedicalExamination in the database
        List<MedicalExamination> medicalExaminationList = medicalExaminationRepository.findAll();
        assertThat(medicalExaminationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMedicalExamination() throws Exception {
        // Initialize the database
        medicalExaminationRepository.saveAndFlush(medicalExamination);
        int databaseSizeBeforeDelete = medicalExaminationRepository.findAll().size();

        // Get the medicalExamination
        restMedicalExaminationMockMvc.perform(delete("/api/medical-examinations/{id}", medicalExamination.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MedicalExamination> medicalExaminationList = medicalExaminationRepository.findAll();
        assertThat(medicalExaminationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MedicalExamination.class);
        MedicalExamination medicalExamination1 = new MedicalExamination();
        medicalExamination1.setId(1L);
        MedicalExamination medicalExamination2 = new MedicalExamination();
        medicalExamination2.setId(medicalExamination1.getId());
        assertThat(medicalExamination1).isEqualTo(medicalExamination2);
        medicalExamination2.setId(2L);
        assertThat(medicalExamination1).isNotEqualTo(medicalExamination2);
        medicalExamination1.setId(null);
        assertThat(medicalExamination1).isNotEqualTo(medicalExamination2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MedicalExaminationDTO.class);
        MedicalExaminationDTO medicalExaminationDTO1 = new MedicalExaminationDTO();
        medicalExaminationDTO1.setId(1L);
        MedicalExaminationDTO medicalExaminationDTO2 = new MedicalExaminationDTO();
        assertThat(medicalExaminationDTO1).isNotEqualTo(medicalExaminationDTO2);
        medicalExaminationDTO2.setId(medicalExaminationDTO1.getId());
        assertThat(medicalExaminationDTO1).isEqualTo(medicalExaminationDTO2);
        medicalExaminationDTO2.setId(2L);
        assertThat(medicalExaminationDTO1).isNotEqualTo(medicalExaminationDTO2);
        medicalExaminationDTO1.setId(null);
        assertThat(medicalExaminationDTO1).isNotEqualTo(medicalExaminationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(medicalExaminationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(medicalExaminationMapper.fromId(null)).isNull();
    }
}
