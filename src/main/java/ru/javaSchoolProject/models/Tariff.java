package ru.javaSchoolProject.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tariff")
@Data
@NoArgsConstructor
public class Tariff {

    public Tariff(String title, int cost, String description, List<Options> options) {
        this.title = title;
        this.cost = cost;
        this.description = description;
        this.options = options;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "cost")
    private double cost;

    @Column(name = "description")
    private String description;

    @Column(name = "is_active")// default true
    private boolean isActive;

    @OneToMany(mappedBy = "tariff",cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true) //orphanRemoval - if we deside to delete something from List it will be deleted from db
    private List<Options> options;

}
