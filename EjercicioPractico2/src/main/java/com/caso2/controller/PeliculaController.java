package com.caso2.controller;

import com.caso2.domain.Pelicula;
import com.caso2.service.PeliculaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

@Controller
@RequestMapping("/admin/peliculas")
@PreAuthorize("hasRole('ADMIN')")
public class PeliculaController {
    private final PeliculaService service;
    public PeliculaController(PeliculaService service){ this.service = service; }

    @GetMapping
    public String listado(Model model){
        model.addAttribute("peliculas", service.listar());
        return "pelicula/listado";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model){
        model.addAttribute("pelicula", new Pelicula());
        return "pelicula/editar";
    }

    @PostMapping("/guardar")
    public String guardar(Pelicula p){
        service.guardar(p);
        return "redirect:/admin/peliculas";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model){
        model.addAttribute("pelicula", service.porId(id).orElseThrow());
        return "pelicula/editar";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id){
        service.eliminar(id);
        return "redirect:/admin/peliculas";
    }
}
