package com.example.VideoClubApp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Entity
@Builder
@Table(name="movie")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotBlank(message="title must not be empty")
    @Column(name="title")
    private String title;
    @NotBlank(message="year must not be empty")
    @Column(name="year")
    private String year;
    @ManyToOne
    @JoinColumn(name="videoClub_key")
    private VideoClub videoClub;
    @ManyToOne
    @JoinColumn(name="customer_key")
    private Customer customer;

    public Movie(){}
    public Movie(Long id,String title,String year,VideoClub videoClub,Customer customer){
        this.id = id;
        this.title = title;
        this.year = year;
        this.customer = customer;
        this.videoClub = videoClub;
    }

    public Long getId(){
        return this.id;
    }
    public String getTitle(){
        return this.title;
    }

    public String getYear(){
        return this.year;
    }
    public Customer getCustomer(){
        return this.customer;
    }
    public VideoClub getVideoClub(){
        return this.videoClub;
    }

    public void setId(Long id){
        this.id = id;
    }
    public void setTitle(String title){
        this.title = title;
    }

    public void setYear(String year){
        this.year=year;
    }

    public void setVideoClub(VideoClub videoClub){
        this.videoClub = videoClub;
    }
    public void setCustomer(Customer customer){
        this.customer = customer;
    }
}
