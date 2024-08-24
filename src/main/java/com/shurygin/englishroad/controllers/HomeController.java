package com.shurygin.englishroad.controllers;


import com.shurygin.englishroad.model.Level;
import com.shurygin.englishroad.repositories.LevelRepository;
import com.shurygin.englishroad.util.SecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
public class HomeController {

    private final LevelRepository levelRepository;
    private final SecurityManager securityManager;

    @Autowired
    public HomeController(LevelRepository levelRepository, SecurityManager securityManager) {
        this.levelRepository = levelRepository;
        this.securityManager = securityManager;
    }

    @ModelAttribute(name = "levels")
    public List<Level> levels() {
        return levelRepository.findAll(Sort.by("id"));
    }

    @ModelAttribute(name = "securityManager")
    public SecurityManager securityManager() {
        return securityManager;
    }

    @GetMapping
    public String showHomePage(){
        return "home";
    }

}
