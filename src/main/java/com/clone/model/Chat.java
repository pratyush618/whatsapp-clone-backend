package com.clone.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String chat_name;
    private String chat_image;

    @ManyToMany
    private Set<User> admins;

    @Column(name = "is_group")
    private Boolean isGroup;

    @Column(name = "created_by")
    @ManyToOne
    private User createdBy;

    @ManyToMany
    private Set<User> users = new HashSet<>();

    @OneToMany(mappedBy = "chat")
    private List<Message> messages = new ArrayList<>();

}
