package com.lych.cargomanagementsystem.service;

import com.lych.cargomanagementsystem.domain.Customer;
import com.lych.cargomanagementsystem.domain.User;
import com.lych.cargomanagementsystem.repository.CustomerRepository;
import com.lych.cargomanagementsystem.repository.OrderRepository;
import com.lych.cargomanagementsystem.security.AuthoritiesConstants;
import com.lych.cargomanagementsystem.service.dto.CommonCustomerDTO;
import com.lych.cargomanagementsystem.service.dto.CustomerDTO;
import com.lych.cargomanagementsystem.service.dto.DetailCustomerDTO;
import com.lych.cargomanagementsystem.service.dto.SearchOrderDTO;
import com.lych.cargomanagementsystem.service.mapper.CustomerMapper;
import lombok.RequiredArgsConstructor;
import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;


/**
 * Service Implementation for managing Customer.
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Transactional
public class CustomerService {

    private final Logger log = LoggerFactory.getLogger(CustomerService.class);

    private final CustomerRepository customerRepository;
    private final DozerBeanMapper dozerBeanMapper;
    private final UserProvider userProvider;
    private final OrderRepository orderRepository;

    /**
     * Save a customer.
     *
     * @param customerDTO the entity to save
     * @return the persisted entity
     */
    public CommonCustomerDTO save(CustomerDTO customerDTO) {
        log.debug("Request to save Customer : {}", customerDTO);
        final User user = userProvider.getCurrentUser();
        Customer customer = dozerBeanMapper.map(customerDTO, Customer.class);
        customer.setUser(user);
        customer = customerRepository.save(customer);
        return dozerBeanMapper.map(customer, CommonCustomerDTO.class);
    }

    /**
     * Save a customer.
     *
     * @param customerDTO the entity to save
     * @return the persisted entity
     */
    public CommonCustomerDTO update(CustomerDTO customerDTO) {
        log.debug("Request to save Customer : {}", customerDTO);
        final Customer found = customerRepository.findOne(customerDTO.getId());
        Customer customer = dozerBeanMapper.map(customerDTO, Customer.class);
        customer.setUser(found.getUser());
        customer = customerRepository.save(customer);
        return dozerBeanMapper.map(customer, CommonCustomerDTO.class);
    }

    /**
     * Get all the customers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CommonCustomerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Customers");
        final User currentUser = userProvider.getCurrentUser();
        if (currentUser.getAuthorities().stream()
            .anyMatch(authority -> authority.getName().equals(AuthoritiesConstants.ADMIN))) {
            final Page<Customer> customers = customerRepository.findAll(pageable);
            return new PageImpl<>(customers.getContent().stream()
                .map(customer -> dozerBeanMapper.map(customer, CommonCustomerDTO.class))
                .collect(toList()), pageable, customers.getTotalElements());
        }
        final Page<Customer> customers = customerRepository.findByUserId(currentUser.getId(), pageable);

        return new PageImpl<>(customers.getContent().stream().
            map(customer -> dozerBeanMapper.map(customer, CommonCustomerDTO.class)).collect(toList()),
            pageable, customers.getTotalElements());

    }

    /**
     * Get one customer by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public DetailCustomerDTO findOne(Long id) {
        log.debug("Request to get Customer : {}", id);
        Customer customer = customerRepository.findOne(id);
        final List<SearchOrderDTO> orders = customer.getOrders().stream()
            .map(order -> {
                final SearchOrderDTO searchOrderDTO = new SearchOrderDTO();
                searchOrderDTO.setId(order.getId());
                searchOrderDTO.setAddresses(order.getDownloadAddress().concat(" - ")
                    .concat(order.getUnloadingAddress()));
                return searchOrderDTO;
            }).collect(Collectors.toList());
        final DetailCustomerDTO detailCustomerDTO = dozerBeanMapper.map(customer, DetailCustomerDTO.class);
        detailCustomerDTO.setOrders(orders);
        return detailCustomerDTO;
    }

    /**
     * Delete the customer by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Customer : {}", id);
        orderRepository.deleteAllByCustomerId(id);
        customerRepository.delete(id);
    }
}
