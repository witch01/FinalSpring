package com.example.demo.repo;

import com.example.demo.models.Price;
import com.example.demo.models.Trainer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PriceRepository extends CrudRepository<Price, Long> {

    Price findByNames(String names);

    List<Price> findByNamesContains(String names);

    //List<Trainer> findByNames(String names);



}
