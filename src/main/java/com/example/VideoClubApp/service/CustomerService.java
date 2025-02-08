package com.example.VideoClubApp.service;

import com.example.VideoClubApp.model.Customer;
import com.example.VideoClubApp.model.VideoClub;
import com.example.VideoClubApp.model.dto.CustomerRequestDto;
import com.example.VideoClubApp.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final VideoClubService videoClubService;

    public CustomerService(CustomerRepository customerRepository, VideoClubService videoClubService) {
        this.customerRepository = customerRepository;
        this.videoClubService = videoClubService;
    }

    public List<Customer> getAllCustomers() {
        return this.customerRepository.findAll();
    }

    public Customer findCustomerById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    public Customer insertCustomer(CustomerRequestDto customerRequestDto){
        final VideoClub videoClub = videoClubService.findVideoClubById(customerRequestDto.getVideoClubId());
        final Customer customer = Customer.builder()
                .id(null)
                .name(customerRequestDto.getName())
                .email(customerRequestDto.getEmail())
                .phone(customerRequestDto.getPhone())
                .videoClub(videoClub).build();
        return customerRepository.save(customer);
    }

    public boolean deleteAllCustomers(){
        customerRepository.deleteAll();
        System.out.println("All Customers have been deleted");
        return true;
    }

    public boolean deleteCustomerById(Long id){
        customerRepository.deleteById(id);
        return true;
    }

    public Customer updateCustomer(CustomerRequestDto customerRequestDto, Long id){
        final Customer savedCustomer = findCustomerById(id);
        savedCustomer.setName(customerRequestDto.getName());
        savedCustomer.setEmail(customerRequestDto.getEmail());
        savedCustomer.setPhone(customerRequestDto.getPhone());
        final VideoClub videoClub = videoClubService.findVideoClubById(customerRequestDto.getVideoClubId());

        savedCustomer.setVideoClub(videoClub);
        return customerRepository.save(savedCustomer);

    }

}
