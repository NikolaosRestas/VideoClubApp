package com.example.VideoClubApp.service;

import com.example.VideoClubApp.model.Customer;
import com.example.VideoClubApp.model.VideoClub;
import com.example.VideoClubApp.model.dto.CustomerRequestDto;
import com.example.VideoClubApp.repository.CustomerRepository;
import com.example.VideoClubApp.repository.VideoClubRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {
    @InjectMocks
    VideoClubService videoClubService;

    @InjectMocks
    CustomerService customerService;

    @Mock
    CustomerRepository customerRepository;
    @Mock
    VideoClubRepository videoClubRepository;

    @BeforeEach
    void setUp(){
        videoClubService = new VideoClubService(videoClubRepository);
        customerService = new CustomerService(customerRepository,videoClubService);
    }

    @Test
    void getCustomers(){
        List<Customer> customers = new ArrayList<>();
        VideoClub videoClub = new VideoClub (1L,"BroadCast","aaa");
        Customer customer = new Customer(1L,"NikosRestas","6943850517","paok@gmail.com",videoClub);

        when(customerRepository.findAll()).thenReturn(customers);
        customers.add(customer);

        List<Customer> response = customerService.getAllCustomers();

        assertThat(response.get(0).getId()).isNotNull();
        assertThat(response.get(0).getId()).isEqualTo(1L);
        assertThat(response.get(0).getName()).isEqualTo("NikosRestas");
        assertThat(response.get(0).getPhone()).isEqualTo("6943850517");
        assertThat(response.get(0).getEmail()).isEqualTo("paok@gmail.com");
        assertThat(response.get(0).getVideoClub()).isEqualTo(videoClub);
    }

    @Test
    void getCustomerById(){
        Long id = 1L;
        VideoClub videoClub = new VideoClub (1L,"BroadCast","aaa");
        Customer customer = new Customer(1L,"NikosRestas","6943850517","paok@gmail.com",videoClub);

        when(customerRepository.findById(id)).thenReturn(Optional.of(customer));

        Customer response = customerService.findCustomerById(id);

        assertThat(response.getId()).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getName()).isEqualTo("NikosRestas");
        assertThat(response.getPhone()).isEqualTo("6943850517");
        assertThat(response.getEmail()).isEqualTo("paok@gmail.com");
        assertThat(response.getVideoClub()).isEqualTo(videoClub);

    }

    @Test
    void addCustomer(){
        VideoClub videoClub = new VideoClub (1L,"BroadCast","aaa");
        CustomerRequestDto customerRequestDto = new CustomerRequestDto("NikosRestas","aa","@gmail.com",videoClub.getId());


        Customer customer = Customer.builder()
                .id(null)
                .name(customerRequestDto.getName())
                .phone(customerRequestDto.getPhone())
                .videoClub(videoClub).build();

        Customer response = new Customer(1L,"NikosRestas","aa","@gmail.com",customer.getVideoClub());

        when(customerRepository.save(any())).thenReturn(response);
        when(videoClubRepository.findById(customerRequestDto.getVideoClubId())).thenReturn(Optional.of(videoClub));

        customerService.insertCustomer(customerRequestDto);

        assertThat(response.getId()).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getName()).isEqualTo("NikosRestas");
        assertThat(response.getPhone()).isEqualTo("aa");
        assertThat(response.getEmail()).isEqualTo("@gmail.com");
        assertThat(response.getVideoClub()).isEqualTo(videoClub);

    }

    @Test
    void updateCustomer(){
        Long id =1L;

        VideoClub videoClub = new VideoClub (1L,"BroadCast","aaa");
        CustomerRequestDto customerRequestDto = new CustomerRequestDto("NikosRestas","bb","@gmail.com",videoClub.getId());

        Customer customer = Customer.builder()
                .id(null)
                .name(customerRequestDto.getName())
                .phone(customerRequestDto.getPhone())
                .videoClub(videoClub).build();

        Customer response = new Customer(1L,"NikosRestas","bb","@gmail.com",customer.getVideoClub());

        when(customerRepository.findById(1L)).thenReturn(Optional.of(response));
        when(videoClubRepository.findById(customerRequestDto.getVideoClubId())).thenReturn(Optional.of(videoClub));

        customerService.updateCustomer(customerRequestDto,1L);

        assertThat(response.getId()).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getName()).isEqualTo("NikosRestas");
        assertThat(response.getPhone()).isEqualTo("bb");
        assertThat(response.getEmail()).isEqualTo("@gmail.com");
        assertThat(response.getVideoClub()).isEqualTo(videoClub);

    }

}
