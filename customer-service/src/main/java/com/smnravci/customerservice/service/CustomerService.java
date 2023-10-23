package com.smnravci.customerservice.service;

import com.smnravci.customerservice.dto.CustomerRequest;
import com.smnravci.customerservice.dto.CustomerResponse;
import com.smnravci.customerservice.model.Customer;
import com.smnravci.customerservice.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public void createCustomer(CustomerRequest customerRequest){
        Customer customer = Customer.builder()
                .fullName(customerRequest.getFullName())
                .email(customerRequest.getEmail())
                .phone(customerRequest.getPhone())
                .build();
        customerRepository.save(customer);
        log.info("Customer {} is created.", customer.getFullName());
    }
    public List<CustomerResponse> getAllCustomers(){
        List<Customer> customers = customerRepository.findAll();
        return customers.stream().map(this::mapToCustomerResponse).collect(Collectors.toList());
    }

    private CustomerResponse mapToCustomerResponse(Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .fullName(customer.getFullName())
                .phone(customer.getPhone())
                .email(customer.getEmail())
                .build();
    }
    public CustomerResponse getCustomerById(int id){
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
        return mapToCustomerResponse(customer);
    }
    public CustomerResponse updateCustomer(int id, CustomerRequest customerRequest){
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        customer.setFullName(customerRequest.getFullName());
        customer.setEmail(customerRequest.getEmail());
        customer.setPhone(customerRequest.getPhone());
        Customer updatedCustomer = customerRepository.save(customer);
        log.info("Customer {} is updated.", updatedCustomer.getFullName());
        return mapToCustomerResponse(updatedCustomer);
    }
    public void deleteCustomer(int id){
        customerRepository.deleteById(id);
    }

}
