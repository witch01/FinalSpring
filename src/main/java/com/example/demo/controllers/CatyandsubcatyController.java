package com.example.demo.controllers;

import com.example.demo.models.*;
import com.example.demo.repo.CategoriesRepository;
import com.example.demo.repo.SubcategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@PreAuthorize("hasAnyAuthority('ADMIN', 'PRODAVEC')")
public class CatyandsubcatyController {
    @Autowired
    public CategoriesRepository categoriesRepository;
    @Autowired
    public SubcategoriesRepository subcategoriesRepository;

    @GetMapping("/categories")
    public String Main(Model model){
        Iterable<Categories> categories = categoriesRepository.findAll();
        model.addAttribute("categories",categories);
        return "categories-add";
    }

    @PostMapping("/categories/add")
    public String blogPostAdd(@RequestParam String names, @RequestParam String name, Model model)
    {
        Categories categories = categoriesRepository.findByName(name);
        Subcategories subcategories = new Subcategories(names, categories);
        subcategoriesRepository.save(subcategories);
        return "redirect:/";
    }

    @GetMapping("sub/{id}/edit")
    public String trainerEdit(@PathVariable("id")long id,
                              Model model)
    {
        Iterable<Categories> categories = categoriesRepository.findAll();
        Subcategories subcategories = subcategoriesRepository.findById(id).orElseThrow(()
                ->new IllegalArgumentException("Invalid trainer Id" + id));
        model.addAttribute("categories", categories);
        model.addAttribute("subcategories",subcategories);

        return "categories-view";
    }

    @PostMapping("sub/{id}/edit")
    public String trainerUpdate(@ModelAttribute("subcategories") @Valid Subcategories subcategories, BindingResult bindingResult, @RequestParam String name,
                                @PathVariable("id") long id) {

        subcategories.setId(id);
        if(bindingResult.hasErrors())
        {
            return "categories-view";
        }
        subcategories.setCategories(categoriesRepository.findByName(name));
        subcategoriesRepository.save(subcategories);
        return "redirect:/sub/main";
    }
    @PostMapping("sub/{id}/remove")
    public String trainerDelete(@PathVariable("id") long id, Model model){
        Subcategories subcategories = subcategoriesRepository.findById(id).orElseThrow();
        subcategoriesRepository.delete(subcategories);
        return "redirect:/sub/main";
    }

    @GetMapping("sub/main")
    public String blogMain(Model model)
    {
        Iterable<Subcategories> subcategories = subcategoriesRepository.findAll();
        model.addAttribute("subcategories", subcategories);
        return "categories-main";
    }

    @GetMapping("/newcategories")
    public String newShop(Categories categories, Model model)
    {
        return "subcategories-add";
    }

    @PostMapping("/newcategories")
    public String shopPostAdd(@ModelAttribute("categories") @Valid Categories categories, BindingResult bindingResult,
                              Model model)
    {
        if(bindingResult.hasErrors())
        {
            return "categories-add";
        }
        categoriesRepository.save(categories);
        return "redirect:/categoriesmain";
    }
    @GetMapping("/categoriesmain")
    public String shopMain(Model model)
    {
        Iterable<Categories> categories = categoriesRepository.findAll();
        model.addAttribute("categories", categories);
        return "subcategories-main";
    }
    @GetMapping("cate/{id}/edit")
    public String postsEdit(@PathVariable("id")long id,
                            Model model)
    {
        Categories categories = categoriesRepository.findById(id).orElseThrow(()
                ->new IllegalArgumentException("Invalid posts Id" + id));
        model.addAttribute("categories",categories);

        return "subcategories-view";
    }

    @PostMapping("cate/{id}/edit")
    public String postsUpdate(@ModelAttribute("categories")@Valid Categories categories, BindingResult bindingResult,
                              @PathVariable("id") long id) {

        categories.setId(id);
        if (bindingResult.hasErrors()) {
            return "subcategories-view";
        }
        categoriesRepository.save(categories);
        return "redirect:/categoriesmain";
    }
    @PostMapping("cate/{id}/remove")
    public String postsDelete(@PathVariable("id") long id, Model model){
        Categories categories = categoriesRepository.findById(id).orElseThrow();
        categoriesRepository.delete(categories);
        return "redirect:/categoriesmain";
    }
}
