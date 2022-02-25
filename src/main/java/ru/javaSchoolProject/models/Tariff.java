package ru.javaSchoolProject.models;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tariff")
@Data
public class Tariff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "cost")
    private int cost;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "tariff",cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true) //orphanRemoval - if we deside to delete something from List it will be deleted from db
    private List<Options> options;
}
