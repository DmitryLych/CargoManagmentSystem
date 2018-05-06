package com.lych.cargomanagementsystem.service;

import com.lych.cargomanagementsystem.domain.Company;
import com.lych.cargomanagementsystem.domain.Driver;
import com.lych.cargomanagementsystem.domain.User;
import com.lych.cargomanagementsystem.repository.CompanyRepository;
import com.lych.cargomanagementsystem.repository.DriverRepository;
import com.lych.cargomanagementsystem.security.AuthoritiesConstants;
import com.lych.cargomanagementsystem.service.dto.CommonCompanyDTO;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;


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
    private final DriverRepository driverRepository;

    private final DozerBeanMapper dozerBeanMapper;

    /**
     * Save a company.
     *
     * @param companyDTO the entity to save
     * @return the persisted entity
     */
    public CommonCompanyDTO save(CompanyDTO companyDTO) {
        log.debug("Request to save Company : {}", companyDTO);
        final User currentUser = userProvider.getCurrentUser();
        Company company = dozerBeanMapper.map(companyDTO, Company.class);
        company.setUser(currentUser);
        company = companyRepository.save(company);
        return dozerBeanMapper.map(company, CommonCompanyDTO.class);
    }

    public CommonCompanyDTO update(CompanyDTO companyDTO) {
        log.debug("Request to save Company : {}", companyDTO);
        final User currentUser = userProvider.getCurrentUser();
        final Company found = companyRepository.findOne(companyDTO.getId());
        Company company = companyMapper.toEntity(companyDTO);
        if (currentUser.getAuthorities().stream()
            .noneMatch(authority -> authority.getName().equals(AuthoritiesConstants.ADMIN))
            && !found.getUser().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException("Access denied.");
        }

        company.setUser(found.getUser());
        company = companyRepository.save(company);
        return dozerBeanMapper.map(company, CommonCompanyDTO.class);
    }

    /**
     * Get all the companies.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CommonCompanyDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Companies");
        final User user = userProvider.getCurrentUser();
        if (user.getAuthorities().stream()
            .anyMatch(authority -> authority.getName().equals(AuthoritiesConstants.ADMIN))) {
            final Page<Company> companies = companyRepository.findAll(pageable);
            final List<CommonCompanyDTO> commonCompanyDTOS = companies.getContent().stream()
                .map(company -> dozerBeanMapper.map(company, CommonCompanyDTO.class))
                .collect(toList());
            return new PageImpl<>(commonCompanyDTOS, pageable, companies.getTotalElements());
        }

        final Page<Company> companies = companyRepository.findByUserId(user.getId(), pageable);
        final List<CommonCompanyDTO> companyList = companies.getContent().stream()
            .map(company -> dozerBeanMapper.map(company, CommonCompanyDTO.class))
            .collect(toList());
        return new PageImpl<>(companyList, pageable, companies.getTotalElements());
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
            }).collect(toList());
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
        driverRepository.deleteAllByCompanyId(id);
        companyRepository.delete(id);
    }
}
