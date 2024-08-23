package com.example.Class_Helper.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @GetMapping("/home")
    public String getHome(){
        return "home";
    }
    @GetMapping("/whiteBoard")
    public String whiteBoard(){
        return "whiteBoard";
    }
    @GetMapping("/clock")
    public String clock(){
        return "clock";
    }

}
