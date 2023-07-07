package com.estudos.demo.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.estudos.demo.service.I18nService;

@Controller
public class HomeController {
    
    @Autowired
    private I18nService i18nService;

    @RequestMapping("/")
    public String index(Model model, Locale locale) {
        String msg = i18nService.getMsg("index.welcome", locale);
        model.addAttribute("mensagem", msg);
        return "publica-index"; //"index.html"
    }

    @RequestMapping("/login")
    public String login(Model model) {
        return "login";
    }
}
