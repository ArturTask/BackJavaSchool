package ru.javaSchoolProject.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "contract")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "phone_number", unique = true)
    private Long phoneNumber;

    @ManyToOne(cascade = {CascadeType.REFRESH},fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contract")
    private List<ContractOptions> contractOptions; // current options of user

    @OneToOne(cascade = {CascadeType.REFRESH},fetch = FetchType.EAGER)
    @JoinColumn(name = "tariff_id")
    private Tariff tariff;

}
