package com.example.demo.controllers;

import com.example.demo.models.Posts;
import com.example.demo.models.Price;
import com.example.demo.models.Shop;
import com.example.demo.models.Trainer;
import com.example.demo.repo.PriceRepository;
import com.example.demo.repo.ShopRepository;
import com.example.demo.repo.TrainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/price")
public class PriceController {
    @Autowired
    private TrainerRepository trainerRepository;
    @Autowired
    private PriceRepository priceRepository;

    @GetMapping("/main")
    public String blogMain(Model model)
    {
        Iterable<Price> price = priceRepository.findAll();
        model.addAttribute("price", price);
        return "price-main";
    }

    @GetMapping("/add")
    public String TrainerAdd(@ModelAttribute("price") Price price, Model model)
    {

        Iterable<Trainer> trainer = trainerRepository.findAll();
        model.addAttribute("trainer", trainer);
        return "price-add";
    }


    @PostMapping("/add")
    public String TrainersAdd(@ModelAttribute("price") @Valid Price price, @RequestParam String names, BindingResult bindingResult, Model model)
    {
        if(bindingResult.hasErrors())
        {
            return "price-add";
        }
        price.setTrainer(trainerRepository.findByNames(names));
        priceRepository.save(price);
        return "price-main";
    }


    @GetMapping("/{id}/edit")
    public String trainerEdit(@PathVariable("id")long id,
                              Model model)
    {
        Iterable<Trainer> trainer = trainerRepository.findAll();
        Price price = priceRepository.findById(id).orElseThrow(()
                ->new IllegalArgumentException("Invalid trainer Id" + id));
        model.addAttribute("price", price);
        model.addAttribute("trainer",trainer);

        return "price-view";
    }

    @PostMapping("/{id}/edit")
    public String trainerUpdate(@ModelAttribute("price") @Valid Price price, @RequestParam String names, BindingResult bindingResult,
                                @PathVariable("id") long id) {

        price.setId(id);
        if(bindingResult.hasErrors())
        {
            Iterable<Trainer> trainer = trainerRepository.findAll();
            return "price-view";
        }
        price.setTrainer(trainerRepository.findByNames(names));
        priceRepository.save(price);
        return "redirect:/price/main";
    }

    @PostMapping("/{id}/remove")
    public String trainerDelete(@PathVariable("id") long id, Model model){
        Price price = priceRepository.findById(id).orElseThrow();
        priceRepository.delete(price);
        return "redirect:/treni/main";
    }
}


