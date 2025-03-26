package com.example.VideoClubApp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Entity
@Builder
@Table(name="ps_games")
public class PsGames {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotBlank(message="title should not be empty")
    @Column(name="title")
    private String title;
    @NotBlank(message="console should not be empty")
    @Column(name="console")
    private String console;
    @NotBlank(message="company should not be empty")
    @Column(name="company")
    private String company;

    @ManyToOne
    @JoinColumn(name = "videoClub_key")
    private VideoClub videoClub;
    @ManyToOne
    @JoinColumn(name="customer_key")
    private Customer customer;

    public PsGames(){}

    public PsGames(Long id,String title,String company, String console,VideoClub videoClub,Customer customer){
        this.id=id;
        this.company = company;
        this.title=title;
        this.console=console;
        this.videoClub=videoClub;
        this.customer=customer;
    }

    public Long getId(){
        return this.id;
    }
    public String getTitle(){
        return this.title;
    }

    public String getCompany() {
        return this.company;
    }
    public String getConsole(){
        return this.console;
    }

    public VideoClub getVideoClub() {
        return this.videoClub;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public void setId(Long id){
        this.id=id;
    }
    public void setTitle(String title){
        this.title=title;
    }
    public void setCompany(String company){
        this.company=company;
    }
    public void setConsole(String console){
        this.console=console;
    }
    public void setVideoClub(VideoClub videoClub){
        this.videoClub = videoClub;
    }
    public void setCustomer(Customer customer){
        this.customer=customer;
    }
}
