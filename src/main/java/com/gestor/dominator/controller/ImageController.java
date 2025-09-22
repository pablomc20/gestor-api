package com.gestor.dominator.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/images")
public class ImageController {
    
    @GetMapping("/test")
    public String test() {
        return "Images endpoint works!";
    }

}
