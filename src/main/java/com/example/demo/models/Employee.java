package com.example.demo.models;


import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.*;
import java.util.Date;

@Entity
public class Employee {

    public Employee() {
    }

    public Employee(String surname, Date dr, Boolean gender, Integer children, Posts posts, User user, Shop shop) {
        this.surname = surname;
        this.dr = dr;
        this.gender = gender;
        this.children = children;
        this.posts = posts;
        this.user = user;
        this.shop = shop;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotEmpty(message = "Поле не может быть пустым")
    @Size(min=2, max = 200, message = "ФИО не может быть короче двух и длиннее двухсот символов.")
    private String surname;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Past
    private Date dr;
    @NotNull
    @Column(nullable = false)
    private Boolean gender = false;
    @Min(value = 0, message = "Количество детей не может быть отрицательным")
    private Integer children;

    @ManyToOne(optional = true, cascade = CascadeType.ALL)
    private Posts posts;

    @OneToOne(optional = true, cascade = CascadeType.ALL)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(optional = true, cascade = CascadeType.ALL)
    private Shop shop;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }


    public Date getDr() {
        return dr;
    }

    public void setDr(Date dr) {
        this.dr = dr;
    }

    public Boolean getGender() {
        return gender;
    }
    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public Integer getChildren() {
        return children;
    }

    public void setChildren(Integer children) {
        this.children = children;
    }

    public Posts getPosts() {
        return posts;
    }

    public void setPosts(Posts posts) {
        this.posts = posts;
    }
}


