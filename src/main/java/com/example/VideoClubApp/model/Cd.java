package com.example.VideoClubApp.model;

import jakarta.persistence.*;
import lombok.Builder;

@Entity
@Builder
@Table(name="cd")
public class Cd {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name="name")
    private String name;
    @Column(name="artist")
    private String artist;

    @ManyToOne
    @JoinColumn(name = "videoClub_key")
    private VideoClub videoClub;

    @ManyToOne
    @JoinColumn(name = "customer_key")
    private Customer customer;

    public Cd(){}

    public Cd(Long id, String name, String artist, VideoClub videoClub, Customer customer){
        this.id = id;
        this.name = name;
        this.artist = artist;
        this.videoClub = videoClub;
        this.customer = customer;
    }

    public Long getId(){
        return this.id;
    }
    public String getName(){
        return this.name;
    }

    public String getArtist(){
        return this.artist;
    }
    public VideoClub getVideoClub(){
        return this.videoClub;
    }

    public Customer getCustomer(){
        return this.customer;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setArtist(String artist){
        this.artist = artist;
    }

    public void setVideoClub(VideoClub videoClub){
        this.videoClub = videoClub;
    }

    public void setCustomer(Customer customer){
        this.customer = customer;
    }

}
