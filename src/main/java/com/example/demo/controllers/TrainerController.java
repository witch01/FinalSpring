package com.example.demo.controllers;

import com.example.demo.models.*;
import com.example.demo.repo.PostsRepository;
import com.example.demo.repo.ShopRepository;
import com.example.demo.repo.TrainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/treni")
public class TrainerController {

    @Autowired
    private TrainerRepository trainerRepository;
    @Autowired
    private ShopRepository shopRepository;

    @GetMapping("/main")
    public String blogMain(Model model)
    {
        Iterable<Trainer> trainer = trainerRepository.findAll();
        model.addAttribute("trainer", trainer);
        return "trainer-main";
    }

    @GetMapping("/add")
    public String TrainerAdd(@ModelAttribute("trainer") Trainer trainer, Model model)
    {

        Iterable<Shop> shops = shopRepository.findAll();
        model.addAttribute("shop", shops);
        return "trainer-add";
    }


    @PostMapping("/add")
    public String TrainersAdd(@ModelAttribute("trainer") @Valid Trainer trainer, @RequestParam String adress, BindingResult bindingResult, Model model)
    {
        if(bindingResult.hasErrors())
        {
            return "trainer-add";
        }
        trainer.setShop(shopRepository.findByAdress(adress));
        trainerRepository.save(trainer);
        return "trainer-main";
    }


    @GetMapping("/{id}/edit")
    public String trainerEdit(@PathVariable("id")long id,
                               Model model)
    {
        Iterable<Shop> shops = shopRepository.findAll();
        Trainer trainer = trainerRepository.findById(id).orElseThrow(()
                ->new IllegalArgumentException("Invalid trainer Id" + id));
        model.addAttribute("shop", shops);
        model.addAttribute("trainer",trainer);

        return "trainer-view";
    }

    @PostMapping("/{id}/edit")
    public String trainerUpdate(@ModelAttribute("trainer") @Valid Trainer trainer, @RequestParam String adress, BindingResult bindingResult,
                                 @PathVariable("id") long id) {

        trainer.setId(id);
        if(bindingResult.hasErrors())
        {
            return "trainer-view";
        }
        trainer.setShop(shopRepository.findByAdress(adress));
        trainerRepository.save(trainer);
        return "redirect:/treni/main";
    }

    @PostMapping("/{id}/remove")
    public String trainerDelete(@PathVariable("id") long id, Model model){
        Trainer trainer = trainerRepository.findById(id).orElseThrow();
        trainerRepository.delete(trainer);
        return "redirect:/treni/main";
    }
}
