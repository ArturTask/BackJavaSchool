package ru.javaSchoolProject.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.javaSchoolProject.enums.OptionType;

import javax.persistence.*;

@Entity
@Table(name = "options")
@Data
@NoArgsConstructor
public class Options {

    public Options(int id, String name, OptionType optionType, Tariff tariff) {
        this.id = id;
        this.name = name;
        this.optionType = optionType;
        this.tariff = tariff;
    }

    public Options(String name, OptionType optionType, Tariff tariff) {
        this.name = name;
        this.optionType = optionType;
        this.tariff = tariff;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "option_type")
    @Enumerated(EnumType.STRING)
    private OptionType optionType;

    @ManyToOne(fetch = FetchType.LAZY) //change LAZY if not works
    @JoinColumn(name = "tariff_id")
    private Tariff tariff;
}
