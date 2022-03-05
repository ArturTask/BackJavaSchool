package ru.javaSchoolProject.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tariff")
@Data
public class Tariff {
    public Tariff() {
        this.setActive(true);
    }

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

//    @OnDelete(action = OnDeleteAction.NO_ACTION)
//    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @OneToMany(mappedBy = "tariff",cascade =CascadeType.ALL, fetch = FetchType.EAGER) //orphanRemoval - if we deside to delete something from List it will be deleted from db
    private List<Options> options;

}
