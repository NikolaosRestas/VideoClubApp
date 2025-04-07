package com.example.VideoClubApp.controller;

import com.example.VideoClubApp.model.Customer;
import com.example.VideoClubApp.model.VideoClub;
import com.example.VideoClubApp.model.dto.CustomerRequestDto;
import com.example.VideoClubApp.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {
    @InjectMocks
    CustomerController customerController;

    @Mock
    CustomerService customerService;

    @Test
    void getCustomers(){

        List<Customer> customers = new ArrayList<>();
        VideoClub videoClub = new VideoClub (1L,"BroadCast","aaa");
        Customer customer = new Customer(1L,"NikosRestas","6943850517","paok@gmail.com",videoClub);

        when(customerService.getAllCustomers()).thenReturn(customers);
        customers.add(customer);

        ResponseEntity<List<Customer>> response = customerController.getCustomers();
        assertThat(Objects.requireNonNull(response.getBody()).get(0).getId()).isNotNull();
        assertThat(response.getBody().get(0).getId()).isEqualTo(1L);
        assertThat(response.getBody().get(0).getName()).isEqualTo("NikosRestas");
        assertThat(response.getBody().get(0).getPhone()).isEqualTo("6943850517");
        assertThat(response.getBody().get(0).getEmail()).isEqualTo("paok@gmail.com");
        assertThat(response.getBody().get(0).getVideoClub()).isEqualTo(videoClub);

    }

    @Test
    void getCustomerById(){
        Long id=1L;
        VideoClub videoClub = new VideoClub (1L,"BroadCast","aaa");
        Customer customer = new Customer(1L,"NikosRestas","6943850517","paok@gmail.com",videoClub);

        when(customerService.findCustomerById(1L)).thenReturn(customer);

        ResponseEntity<Customer> response = customerController.getCustomerById(1L);

        assertThat(Objects.requireNonNull(response.getBody()).getId()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("NikosRestas");
        assertThat(response.getBody().getPhone()).isEqualTo("6943850517");
        assertThat(response.getBody().getEmail()).isEqualTo("paok@gmail.com");
        assertThat(response.getBody().getVideoClub()).isEqualTo(videoClub);
    }

    @Test
    void insertCustomer(){
        VideoClub videoClub = new VideoClub (1L,"BroadCast","aaa");
        Customer customer = new Customer(1L,"NikosRestas","aa","@gmail.com",videoClub);
        CustomerRequestDto customerRequestDto = new CustomerRequestDto("NikosRestas","aa","@gmail.com",videoClub.getId());

        when(customerService.insertCustomer(customerRequestDto)).thenReturn(customer);

        ResponseEntity<Customer> response = customerController.addCustomer(customerRequestDto);

        assertThat(Objects.requireNonNull(response.getBody()).getId()).isEqualTo(1L);
        assertThat(response.getBody().getId()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("NikosRestas");
        assertThat(response.getBody().getEmail()).isEqualTo("@gmail.com");
        assertThat(response.getBody().getPhone()).isEqualTo("aa");
        assertThat(response.getBody().getVideoClub()).isEqualTo(videoClub);
    }

    @Test
    void updateCustomer(){
        Long id=1L;
        VideoClub videoClub = new VideoClub (1L,"BroadCast","aaa");
        Customer customer = new Customer(1L,"NikosRestas","aa","@gmail.com",videoClub);
        CustomerRequestDto customerRequestDto = new CustomerRequestDto("NikosRestas","aa","@gmail.com",videoClub.getId());

        when(customerService.updateCustomer(customerRequestDto,1L)).thenReturn(customer);

        Customer response = customerController.updateCustomer(customerRequestDto,1L);

        assertThat(response.getId()).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getName()).isEqualTo("NikosRestas");
        assertThat(response.getEmail()).isEqualTo("@gmail.com");
        assertThat(response.getPhone()).isEqualTo("aa");
        assertThat(response.getVideoClub()).isEqualTo(videoClub);

    }

    @Test
    void deleteCustomerById(){
        Long id = 1L;
        VideoClub videoClub = new VideoClub(1L,"BroadCast","aaa");
        Customer customer = new Customer(1L,"Nikos","bbb","@gmail.com",videoClub);

        when(customerService.deleteCustomerById(id)).thenReturn(true);

        Boolean response = customerController.deleteCustomerById(id);

        assertThat(response).isTrue();

    }

}
