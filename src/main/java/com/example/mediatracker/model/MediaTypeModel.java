package com.example.mediatracker.model;

import jakarta.persistence.*;

@Entity
@Table(name = "media_type")
public class MediaTypeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    public MediaTypeModel() {}

    public MediaTypeModel(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
