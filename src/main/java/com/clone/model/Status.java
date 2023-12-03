package com.clone.model;

import lombok.Data;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String content;

    @Column(name = "local_date_time")
    private LocalDateTime localDateTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
