package com.example.demo.controllers;


import com.example.demo.models.*;
import com.example.demo.repo.EmployeeRepository;
import com.example.demo.repo.PostsRepository;
import com.example.demo.repo.ShopRepository;
import com.example.demo.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/employee")
@PreAuthorize("hasAnyAuthority('USER')")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private PostsRepository postsRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ShopRepository shopRepository;

    @GetMapping("/emp")
    public String blogMain(Model model)
    {
        Iterable<Employee> employee = employeeRepository.findAll();
        model.addAttribute("employee", employee);
        return "employee-main";
    }

    @GetMapping("/add")
    public String EmployeeAdd(@ModelAttribute("employee") Employee employee, Model model)
    {

        Iterable<Posts> posts = postsRepository.findAll();
        Iterable<Shop> shop = shopRepository.findAll();
        Iterable<User> user=userRepository.findAll();
        ArrayList<User> userArrayList=new ArrayList<>();
        for (User sub: user){
            if (sub.getEmployee()==null){
                userArrayList.add(sub);
            }
        }
        model.addAttribute("user", user);
        model.addAttribute("shop", shop);
        model.addAttribute("posts", posts);
        return "employee-add";
    }


    @PostMapping("/add")
    public String employeePostAdd(@ModelAttribute("employee") @Valid  Employee employee, BindingResult bindingResult, @RequestParam String names,@RequestParam String username,
                                  @RequestParam String adress, Model model)
    {
        if(bindingResult.hasErrors())
        {
            Iterable<Posts> posts = postsRepository.findAll();
            Iterable<Shop> shop = shopRepository.findAll();
            Iterable<User> user=userRepository.findAll();
            model.addAttribute("user", user);
            model.addAttribute("shop", shop);
            model.addAttribute("posts", posts);
            return "employee-add";
        }
        employee.setPosts(postsRepository.findByNames(names));
        employee.setUser(userRepository.findByUsername(username));
        employee.setShop(shopRepository.findByAdress(adress));
        employeeRepository.save(employee);
        return "redirect:/employee/emp";
    }

    @GetMapping("/filter")
    public String employeeFilter(Model model)
    {
        return "employee-filter";
    }

    @PostMapping("/filter/result")
    public String blogResult(@RequestParam String surname, Model model)
    {
        List<Employee> result = employeeRepository.findBySurnameContains(surname);
        List<Employee> result1 = employeeRepository.findBySurname(surname);
//        List<Post> result = postRepository.findLikeTitle(title);
        model.addAttribute("result", result);
        return "employee-filter";
    }



    @GetMapping("/{id}/edit")
    public String employeeEdit(@PathVariable("id")long id,
                                Model model)
    {
        Iterable<Posts> posts = postsRepository.findAll();
        Iterable<Shop> shop = shopRepository.findAll();
        Iterable<User> user=userRepository.findAll();
        Employee employee = employeeRepository.findById(id).orElseThrow(()
                ->new IllegalArgumentException("Invalid students Id" + id));
        model.addAttribute("user", user);
        model.addAttribute("shop", shop);
        model.addAttribute("posts", posts);
        model.addAttribute("employee",employee);

        return "employee-view";
    }

    @PostMapping("/{id}/edit")
    public String employeeUpdate(@ModelAttribute("employee")@Valid Employee employee,BindingResult bindingResult, @RequestParam String names,@RequestParam String username,
                                 @RequestParam String adress,
                                 @PathVariable("id") long id) {

        employee.setId(id);
        if (bindingResult.hasErrors()) {
            Iterable<Posts> posts = postsRepository.findAll();
            Iterable<Shop> shop = shopRepository.findAll();
            Iterable<User> user=userRepository.findAll();
            return "employee-view";
        }
        employee.setPosts(postsRepository.findByNames(names));
        employee.setUser(userRepository.findByUsername(username));
        employee.setShop(shopRepository.findByAdress(adress));
        employeeRepository.save(employee);

        return "redirect:/employee/emp";
    }

    @PostMapping("/{id}/remove")
    public String employeeDelete(@PathVariable("id") long id, Model model){
        Employee employee = employeeRepository.findById(id).orElseThrow();
        employeeRepository.delete(employee);
        return "redirect:/employee/emp";
    }

    @GetMapping("/posts")
    public String postsMain(Model model)
    {
        Iterable<Posts> posts = postsRepository.findAll();
        model.addAttribute("posts", posts);
        return "posts-main";
    }

    @GetMapping("/addposts")
    public String PostsAdds( Posts posts, Model model)
    {
        return "posts-add";
    }

    @PostMapping("/addposts")
    public String PostAdd(@ModelAttribute("posts") @Valid  Posts posts,
                                  BindingResult bindingResult, Model model)
    {
        if(bindingResult.hasErrors())
        {
            return "posts-add";
        }
        postsRepository.save(posts);
        return "redirect:/employee/posts";
    }
    @GetMapping("/{id}/posts")
    public String postsView(@PathVariable(value = "id") long id, Model model)
    {
        Optional<Posts> posts = postsRepository.findById(id);
        ArrayList<Posts> res = new ArrayList<>();
        posts.ifPresent(res::add);
        model.addAttribute("posts", res);
        if(!postsRepository.existsById(id))
        {
            return "redirect:/";
        }
        return "posts-view";

    }

    @GetMapping("/{id}/editposts")
    public String postsEdit(@PathVariable("id")long id,
                               Model model)
    {
        Posts posts = postsRepository.findById(id).orElseThrow(()
                ->new IllegalArgumentException("Invalid posts Id" + id));
        model.addAttribute("posts",posts);

        return "posts-view";
    }

    @PostMapping("/{id}/editposts")
    public String postsUpdate(@ModelAttribute("posts")@Valid Posts posts, BindingResult bindingResult,
                                 @PathVariable("id") long id) {

        posts.setId(id);
        if (bindingResult.hasErrors()) {
            return "posts-view";
        }
        postsRepository.save(posts);
        return "redirect:/employee/posts";
    }
    @PostMapping("/{id}/removeposts")
    public String postsDelete(@PathVariable("id") long id, Model model){
        Posts posts = postsRepository.findById(id).orElseThrow();
        postsRepository.delete(posts);
        return "redirect:/employee/posts";
    }
}
