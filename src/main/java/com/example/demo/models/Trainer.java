package com.example.demo.models;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Collection;
import java.util.Date;

@Entity
public class Trainer {
    public Trainer(){}
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(message = "Поле не может быть пустым")
    @Size(min=2, max = 200, message = "ФИО не может быть короче 2 и длиннее 200 символов.")
    private String names;

    @Min(value = 0, message = "Стаж работы не может быть отрицательным")
    private Integer dr;

    @ManyToOne(optional = true, cascade = CascadeType.ALL)
    private Shop shop;

    @OneToMany(mappedBy = "trainer", fetch = FetchType.EAGER)
    private Collection<Price> tr;

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

    public Integer getDr() {
        return dr;
    }

    public void setDr(Integer dr) {
        this.dr = dr;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public Collection<Price> getTr() {
        return tr;
    }

    public void setTr(Collection<Price> tr) {
        this.tr = tr;
    }
}
