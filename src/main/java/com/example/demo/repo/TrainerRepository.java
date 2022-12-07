package com.example.demo.repo;

import com.example.demo.models.Posts;
import com.example.demo.models.Trainer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TrainerRepository extends CrudRepository<Trainer, Long> {

    Trainer findByNames(String names);

    List<Trainer> findByNamesContains(String names);

    //List<Trainer> findByNames(String names);



}

