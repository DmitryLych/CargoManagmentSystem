package com.lych.cargomanagementsystem.web.rest;

import com.lych.cargomanagementsystem.CargoManagmentSystemApp;

import com.lych.cargomanagementsystem.domain.InsurancePolicy;
import com.lych.cargomanagementsystem.repository.InsurancePolicyRepository;
import com.lych.cargomanagementsystem.service.InsurancePolicyService;
import com.lych.cargomanagementsystem.service.dto.InsurancePolicyDTO;
import com.lych.cargomanagementsystem.service.mapper.InsurancePolicyMapper;
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
 * Test class for the InsurancePolicyResource REST controller.
 *
 * @see InsurancePolicyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CargoManagmentSystemApp.class)
public class InsurancePolicyResourceIntTest {

    private static final LocalDate DEFAULT_VALIDATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALIDATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final Double DEFAULT_COST = 1D;
    private static final Double UPDATED_COST = 2D;

    @Autowired
    private InsurancePolicyRepository insurancePolicyRepository;

    @Autowired
    private InsurancePolicyMapper insurancePolicyMapper;

    @Autowired
    private InsurancePolicyService insurancePolicyService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restInsurancePolicyMockMvc;

    private InsurancePolicy insurancePolicy;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InsurancePolicyResource insurancePolicyResource = new InsurancePolicyResource(insurancePolicyService);
        this.restInsurancePolicyMockMvc = MockMvcBuilders.standaloneSetup(insurancePolicyResource)
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
    public static InsurancePolicy createEntity(EntityManager em) {
        InsurancePolicy insurancePolicy = new InsurancePolicy()
            .validate(DEFAULT_VALIDATE)
            .type(DEFAULT_TYPE)
            .cost(DEFAULT_COST);
        return insurancePolicy;
    }

    @Before
    public void initTest() {
        insurancePolicy = createEntity(em);
    }

    @Test
    @Transactional
    public void createInsurancePolicy() throws Exception {
        int databaseSizeBeforeCreate = insurancePolicyRepository.findAll().size();

        // Create the InsurancePolicy
        InsurancePolicyDTO insurancePolicyDTO = insurancePolicyMapper.toDto(insurancePolicy);
        restInsurancePolicyMockMvc.perform(post("/api/insurance-policies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(insurancePolicyDTO)))
            .andExpect(status().isCreated());

        // Validate the InsurancePolicy in the database
        List<InsurancePolicy> insurancePolicyList = insurancePolicyRepository.findAll();
        assertThat(insurancePolicyList).hasSize(databaseSizeBeforeCreate + 1);
        InsurancePolicy testInsurancePolicy = insurancePolicyList.get(insurancePolicyList.size() - 1);
        assertThat(testInsurancePolicy.getValidate()).isEqualTo(DEFAULT_VALIDATE);
        assertThat(testInsurancePolicy.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testInsurancePolicy.getCost()).isEqualTo(DEFAULT_COST);
    }

    @Test
    @Transactional
    public void createInsurancePolicyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = insurancePolicyRepository.findAll().size();

        // Create the InsurancePolicy with an existing ID
        insurancePolicy.setId(1L);
        InsurancePolicyDTO insurancePolicyDTO = insurancePolicyMapper.toDto(insurancePolicy);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInsurancePolicyMockMvc.perform(post("/api/insurance-policies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(insurancePolicyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the InsurancePolicy in the database
        List<InsurancePolicy> insurancePolicyList = insurancePolicyRepository.findAll();
        assertThat(insurancePolicyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkValidateIsRequired() throws Exception {
        int databaseSizeBeforeTest = insurancePolicyRepository.findAll().size();
        // set the field null
        insurancePolicy.setValidate(null);

        // Create the InsurancePolicy, which fails.
        InsurancePolicyDTO insurancePolicyDTO = insurancePolicyMapper.toDto(insurancePolicy);

        restInsurancePolicyMockMvc.perform(post("/api/insurance-policies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(insurancePolicyDTO)))
            .andExpect(status().isBadRequest());

        List<InsurancePolicy> insurancePolicyList = insurancePolicyRepository.findAll();
        assertThat(insurancePolicyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = insurancePolicyRepository.findAll().size();
        // set the field null
        insurancePolicy.setType(null);

        // Create the InsurancePolicy, which fails.
        InsurancePolicyDTO insurancePolicyDTO = insurancePolicyMapper.toDto(insurancePolicy);

        restInsurancePolicyMockMvc.perform(post("/api/insurance-policies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(insurancePolicyDTO)))
            .andExpect(status().isBadRequest());

        List<InsurancePolicy> insurancePolicyList = insurancePolicyRepository.findAll();
        assertThat(insurancePolicyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCostIsRequired() throws Exception {
        int databaseSizeBeforeTest = insurancePolicyRepository.findAll().size();
        // set the field null
        insurancePolicy.setCost(null);

        // Create the InsurancePolicy, which fails.
        InsurancePolicyDTO insurancePolicyDTO = insurancePolicyMapper.toDto(insurancePolicy);

        restInsurancePolicyMockMvc.perform(post("/api/insurance-policies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(insurancePolicyDTO)))
            .andExpect(status().isBadRequest());

        List<InsurancePolicy> insurancePolicyList = insurancePolicyRepository.findAll();
        assertThat(insurancePolicyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInsurancePolicies() throws Exception {
        // Initialize the database
        insurancePolicyRepository.saveAndFlush(insurancePolicy);

        // Get all the insurancePolicyList
        restInsurancePolicyMockMvc.perform(get("/api/insurance-policies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(insurancePolicy.getId().intValue())))
            .andExpect(jsonPath("$.[*].validate").value(hasItem(DEFAULT_VALIDATE.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].cost").value(hasItem(DEFAULT_COST.doubleValue())));
    }

    @Test
    @Transactional
    public void getInsurancePolicy() throws Exception {
        // Initialize the database
        insurancePolicyRepository.saveAndFlush(insurancePolicy);

        // Get the insurancePolicy
        restInsurancePolicyMockMvc.perform(get("/api/insurance-policies/{id}", insurancePolicy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(insurancePolicy.getId().intValue()))
            .andExpect(jsonPath("$.validate").value(DEFAULT_VALIDATE.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.cost").value(DEFAULT_COST.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingInsurancePolicy() throws Exception {
        // Get the insurancePolicy
        restInsurancePolicyMockMvc.perform(get("/api/insurance-policies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInsurancePolicy() throws Exception {
        // Initialize the database
        insurancePolicyRepository.saveAndFlush(insurancePolicy);
        int databaseSizeBeforeUpdate = insurancePolicyRepository.findAll().size();

        // Update the insurancePolicy
        InsurancePolicy updatedInsurancePolicy = insurancePolicyRepository.findOne(insurancePolicy.getId());
        // Disconnect from session so that the updates on updatedInsurancePolicy are not directly saved in db
        em.detach(updatedInsurancePolicy);
        updatedInsurancePolicy
            .validate(UPDATED_VALIDATE)
            .type(UPDATED_TYPE)
            .cost(UPDATED_COST);
        InsurancePolicyDTO insurancePolicyDTO = insurancePolicyMapper.toDto(updatedInsurancePolicy);

        restInsurancePolicyMockMvc.perform(put("/api/insurance-policies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(insurancePolicyDTO)))
            .andExpect(status().isOk());

        // Validate the InsurancePolicy in the database
        List<InsurancePolicy> insurancePolicyList = insurancePolicyRepository.findAll();
        assertThat(insurancePolicyList).hasSize(databaseSizeBeforeUpdate);
        InsurancePolicy testInsurancePolicy = insurancePolicyList.get(insurancePolicyList.size() - 1);
        assertThat(testInsurancePolicy.getValidate()).isEqualTo(UPDATED_VALIDATE);
        assertThat(testInsurancePolicy.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testInsurancePolicy.getCost()).isEqualTo(UPDATED_COST);
    }

    @Test
    @Transactional
    public void updateNonExistingInsurancePolicy() throws Exception {
        int databaseSizeBeforeUpdate = insurancePolicyRepository.findAll().size();

        // Create the InsurancePolicy
        InsurancePolicyDTO insurancePolicyDTO = insurancePolicyMapper.toDto(insurancePolicy);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restInsurancePolicyMockMvc.perform(put("/api/insurance-policies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(insurancePolicyDTO)))
            .andExpect(status().isCreated());

        // Validate the InsurancePolicy in the database
        List<InsurancePolicy> insurancePolicyList = insurancePolicyRepository.findAll();
        assertThat(insurancePolicyList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteInsurancePolicy() throws Exception {
        // Initialize the database
        insurancePolicyRepository.saveAndFlush(insurancePolicy);
        int databaseSizeBeforeDelete = insurancePolicyRepository.findAll().size();

        // Get the insurancePolicy
        restInsurancePolicyMockMvc.perform(delete("/api/insurance-policies/{id}", insurancePolicy.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<InsurancePolicy> insurancePolicyList = insurancePolicyRepository.findAll();
        assertThat(insurancePolicyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InsurancePolicy.class);
        InsurancePolicy insurancePolicy1 = new InsurancePolicy();
        insurancePolicy1.setId(1L);
        InsurancePolicy insurancePolicy2 = new InsurancePolicy();
        insurancePolicy2.setId(insurancePolicy1.getId());
        assertThat(insurancePolicy1).isEqualTo(insurancePolicy2);
        insurancePolicy2.setId(2L);
        assertThat(insurancePolicy1).isNotEqualTo(insurancePolicy2);
        insurancePolicy1.setId(null);
        assertThat(insurancePolicy1).isNotEqualTo(insurancePolicy2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InsurancePolicyDTO.class);
        InsurancePolicyDTO insurancePolicyDTO1 = new InsurancePolicyDTO();
        insurancePolicyDTO1.setId(1L);
        InsurancePolicyDTO insurancePolicyDTO2 = new InsurancePolicyDTO();
        assertThat(insurancePolicyDTO1).isNotEqualTo(insurancePolicyDTO2);
        insurancePolicyDTO2.setId(insurancePolicyDTO1.getId());
        assertThat(insurancePolicyDTO1).isEqualTo(insurancePolicyDTO2);
        insurancePolicyDTO2.setId(2L);
        assertThat(insurancePolicyDTO1).isNotEqualTo(insurancePolicyDTO2);
        insurancePolicyDTO1.setId(null);
        assertThat(insurancePolicyDTO1).isNotEqualTo(insurancePolicyDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(insurancePolicyMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(insurancePolicyMapper.fromId(null)).isNull();
    }
}
