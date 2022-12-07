package com.example.demo.models;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.List;

@Table(name = "shop")
@Entity
public class Shop {

    public Shop() {
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotEmpty(message = "Поле не может быть пустым")
    @Size(min=2, max = 500, message = "Адрес не может быть короче двух и длиннее пятисот символов.")
    private String adress;
    @ManyToMany
    @JoinTable (name="shop_product",
            joinColumns=@JoinColumn (name="shop_id"),
            inverseJoinColumns=@JoinColumn(name="product_id"))
    private List<Product> products;

    @OneToMany(mappedBy = "shop", fetch = FetchType.EAGER)
    private Collection<Trainer> tenants;


    @OneToMany(mappedBy = "shop", fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private Collection<Employee> tr;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Collection<Employee> getTr() {
        return tr;
    }

    public void setTr(Collection<Employee> tr) {
        this.tr = tr;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Collection<Trainer> getTenants() {
        return tenants;
    }

    public void setTenants(Collection<Trainer> tenants) {
        this.tenants = tenants;
    }
}
