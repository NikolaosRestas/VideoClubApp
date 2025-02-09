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

}
