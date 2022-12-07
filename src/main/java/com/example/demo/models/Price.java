package com.example.demo.models;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Price {
    public Price(){}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(message = "Поле не может быть пустым")
    @Size(min=2, max = 200, message = "Название не может быть короче двух и длиннее двухсот символов.")
    private String names;

    @NotEmpty(message = "Поле не может быть пустым")
    @Size(min=2, max = 500, message = "Название не может быть короче двух и длиннее пятисот символов.")
    private String description;

    @Min(value = 0, message = "Цена не может быть отрицательной")
    @NotNull(message = "Поле не может быть пустым")
    private Double cent;

    @ManyToOne(optional = true, cascade = CascadeType.ALL)
    private Trainer trainer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getCent() {
        return cent;
    }

    public void setCent(Double cent) {
        this.cent = cent;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }
}
