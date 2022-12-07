package com.example.demo.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;

@Entity
public class Posts {
    public  Posts(){}
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotEmpty(message = "Поле не может быть пустым")
    @Size(min=2, max = 50, message = "Наименование не может быть короче 2 и длиннее 50 символов.")
    private String names;

    @NotNull(message = "Поле не может быть пустым")
    private Double salary;

    @OneToMany(mappedBy = "posts", fetch = FetchType.EAGER)
    private Collection<Employee> tenants;

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

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Posts(String names, Double salary, Collection<Employee> tenants) {
        this.names = names;
        this.salary = salary;
        this.tenants = tenants;
    }

    public Collection<Employee> getTenants() {
        return tenants;
    }

    public void setTenants(Collection<Employee> tenants) {
        this.tenants = tenants;
    }
}
