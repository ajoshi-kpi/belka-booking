package com.example.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalTime;

@Entity
@NoArgsConstructor
@Data
public class Room {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String title;
    private LocalTime openTime;
    private LocalTime closeTime;
}
