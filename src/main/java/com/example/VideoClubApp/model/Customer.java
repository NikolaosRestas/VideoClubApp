package com.example.VideoClubApp.model;

import jakarta.persistence.*;
import lombok.Builder;

@Entity
@Builder
@Table(name="customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name="name")
    private String name;
    @Column(name="phone")
    private String phone;
    @Column(name="email")
    private String email;

    @ManyToOne
    @JoinColumn(name = "videoClub_key")
    private VideoClub videoClub;

    public Customer(){}

    public Customer(Long id, String name, String phone, String email, VideoClub videoClub){
        this.id = id;
        this.name=name;
        this.phone = phone;
        this.email = email;
        this.videoClub = videoClub;
    }
    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public VideoClub getVideoClub() {
        return videoClub;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(Long id){
        this.id = id;
    }

    public void setVideoClub(VideoClub videoClub) {
        this.videoClub = videoClub;
    }

    @Override
    public boolean equals(Object customer) {
        if(this == customer){
            return true;
        }
        if(! (customer instanceof Customer)){
            return false;
        }
        return super.equals(customer);
    }
}
