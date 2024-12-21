package com.example.VideoClubApp.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name="videoClub")
public class VideoClub {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name="name")
    private String name;
    @Column(name="phone")
    private String phone;

    public VideoClub(){}

    public VideoClub(Long id,String name,String phone){
        this.id=id;
        this.name=name;
        this.phone=phone;
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

    public void setId(Long id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj){
            return true;
        }
        if(!(obj instanceof VideoClub)){
            return false;
        }
        VideoClub videoClub = (VideoClub) obj;
        return Objects.equals(this.id,videoClub.id) && Objects.equals(this.name,videoClub.name) && Objects.equals(this.phone,videoClub.phone);
    }
}
