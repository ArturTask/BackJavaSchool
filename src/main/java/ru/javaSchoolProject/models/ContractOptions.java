package ru.javaSchoolProject.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.javaSchoolProject.enums.OptionType;

import javax.persistence.*;

@Entity
@Table(name = "contract_options")
@Data
@NoArgsConstructor
public class ContractOptions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "contract_id")
    private Contract contract;

    @Column(name = "option_id")
    private int optionId;

    @Column(name = "name")
    private String name;

    @Column(name = "option_type")
    @Enumerated(EnumType.STRING)
    private OptionType optionType;

    @Column(name = "cost")
    private Double cost;


}
