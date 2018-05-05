package com.lych.cargomanagementsystem.service;

import com.lych.cargomanagementsystem.domain.Company;
import com.lych.cargomanagementsystem.domain.Driver;
import com.lych.cargomanagementsystem.domain.User;
import com.lych.cargomanagementsystem.repository.CompanyRepository;
import com.lych.cargomanagementsystem.security.AuthoritiesConstants;
import com.lych.cargomanagementsystem.service.dto.CompanyDTO;
import com.lych.cargomanagementsystem.service.dto.DetailCompanyDTO;
import com.lych.cargomanagementsystem.service.dto.SearchDriverDTO;
import com.lych.cargomanagementsystem.service.mapper.CompanyMapper;
import lombok.RequiredArgsConstructor;
import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * Service Implementation for managing Company.
 */
@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CompanyService {

    private final Logger log = LoggerFactory.getLogger(CompanyService.class);
    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;
    private final UserProvider userProvider;

    private final DozerBeanMapper dozerBeanMapper;

    /**
     * Save a company.
     *
     * @param companyDTO the entity to save
     * @return the persisted entity
     */
    public CompanyDTO save(CompanyDTO companyDTO) {
        log.debug("Request to save Company : {}", companyDTO);
        final User currentUser = userProvider.getCurrentUser();
        Company company = dozerBeanMapper.map(companyDTO, Company.class);
        company.setUser(currentUser);
        company = companyRepository.save(company);
        return dozerBeanMapper.map(company, CompanyDTO.class);
    }

    public CompanyDTO update(CompanyDTO companyDTO) {
        log.debug("Request to save Company : {}", companyDTO);
        final User currentUser = userProvider.getCurrentUser();
        Company company = companyMapper.toEntity(companyDTO);
        if (currentUser.getAuthorities().stream()
            .noneMatch(authority -> authority.getName().equals(AuthoritiesConstants.ADMIN))
            && !company.getUser().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException("Access denied.");
        }

        company = companyRepository.save(company);
        return companyMapper.toDto(company);
    }

    /**
     * Get all the companies.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CompanyDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Companies");
        final User user = userProvider.getCurrentUser();
        if (user.getAuthorities().stream()
            .anyMatch(authority -> authority.getName().equals(AuthoritiesConstants.ADMIN))) {
            return companyRepository.findAll(pageable)
                .map(companyMapper::toDto);
        }

        return companyRepository.findByUserId(user.getId(), pageable).map(companyMapper::toDto);
    }

    /**
     * Get one company by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public DetailCompanyDTO findOne(Long id) {
        log.debug("Request to get Company : {}", id);
        Company company = companyRepository.findOne(id);
        final Set<Driver> drivers = company.getDrivers();
        final List<SearchDriverDTO> searchDriverDTOS = drivers.stream()
            .map(driver -> {
                final SearchDriverDTO searchDriverDTO = new SearchDriverDTO();
                searchDriverDTO.setFullName(driver.getLastName().concat(" ").concat(driver.getFirstName()));
                searchDriverDTO.setId(driver.getId());
                return searchDriverDTO;
            }).collect(Collectors.toList());
        final DetailCompanyDTO detailCompanyDTO = dozerBeanMapper.map(company, DetailCompanyDTO.class);
        detailCompanyDTO.setDrivers(searchDriverDTOS);
        return detailCompanyDTO;
    }

    /**
     * Delete the company by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Company : {}", id);
        companyRepository.delete(id);
    }
}
