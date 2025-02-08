package com.example.VideoClubApp.model;

import jakarta.persistence.*;
import lombok.Builder;

@Entity
@Builder
@Table(name="staff")
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name="name")
    private String name;
    @Column(name="phone")
    private String phone;
    @ManyToOne
    @JoinColumn(name = "videoClub_key")
    private VideoClub videoClub;

    public Staff(){}

    public Staff(Long id,String name, String phone, VideoClub videoClub){
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.videoClub = videoClub;
    }

    public Long getId() {
        return id;
    }

    public String getPhone() {
        return phone;
    }

    public String getName() {
        return name;
    }

    public VideoClub getVideoClub() {
        return videoClub;
    }

    public void setId(Long id){
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }
    public void setPhone(String phone){
        this.phone = phone;
    }
    public void setVideoClub(VideoClub videoClub) {
        this.videoClub = videoClub;
    }
}
