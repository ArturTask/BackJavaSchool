package ru.javaSchoolProject.models;

import ru.javaSchoolProject.enums.OptionType;

import javax.persistence.*;

@Entity
@Table(name = "contract_options")
public class ContractOptions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "contract_id")
    private Contract contract;

    @Column(name = "options")
    private String option;

    @Column(name = "option_type")
    @Enumerated(value = EnumType.STRING)
    private OptionType optionType;

}
