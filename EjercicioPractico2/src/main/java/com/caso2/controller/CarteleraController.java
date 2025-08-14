package com.caso2.controller;

import com.caso2.service.FuncionService;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CarteleraController {
    private final FuncionService funcionService;
    public CarteleraController(FuncionService funcionService){ this.funcionService = funcionService; }

    @GetMapping({"/","/cartelera"})
    public String cartelera(Model model){
        model.addAttribute("funciones", funcionService.proximas());
        return "cine/cartelera";
    }
}
