package com.shurygin.englishroad.controllers;


import com.shurygin.englishroad.model.Level;
import com.shurygin.englishroad.repositories.LevelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    LevelRepository levelRepository;

    @ModelAttribute(name = "levels")
    public List<Level> levels() {
        return levelRepository.findAll(Sort.by("id"));
    }

    @GetMapping
    public String showHomePage(){
        return "home";
    }

}
