package com.example.demo.repo;

import com.example.demo.models.Posts;
import com.example.demo.models.Product;
import com.example.demo.models.Subcategories;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostsRepository extends CrudRepository<Posts, Long> {

    Posts findByNames(String names);

    List<Posts> findByNamesContains(String names);

    //List<Posts> findByNames(String names);



}
