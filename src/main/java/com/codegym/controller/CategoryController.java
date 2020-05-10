package com.codegym.controller;

import com.codegym.model.Blog;
import com.codegym.model.Category;
import com.codegym.service.BlogService;
import com.codegym.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ModelAndView showFormCategory(@RequestParam("search")Optional<String> search, Pageable pageable){
        Page<Category> categories;
        if (search.isPresent()){
            categories = categoryService.findAllByCategoryName(search.get(), pageable);
        }else {
            categories = categoryService.findAll(pageable);
        }
        ModelAndView modelAndView = new ModelAndView("/category/list");
        modelAndView.addObject("category", categories);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView showCreateFormCategory(){
        ModelAndView modelAndView = new ModelAndView("/category/create");
        modelAndView.addObject("category", new Category());
        return modelAndView;
    }

    @PostMapping("/create")
    public String createCategory(@ModelAttribute("category") Category category, RedirectAttributes redirect){
        categoryService.save(category);
        redirect.addFlashAttribute("message", "create category successfully !");
        return "redirect:/category";
    }

}
