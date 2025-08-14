package com.caso2.controller;

import com.caso2.domain.Funcion;
import com.caso2.domain.Pelicula;
import com.caso2.service.FuncionService;
import com.caso2.service.PeliculaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

@Controller
@RequestMapping("/admin/funciones")
@PreAuthorize("hasRole('ADMIN')")
public class FuncionController {
    private final FuncionService funcionService;
    private final PeliculaService peliculaService;

    public FuncionController(FuncionService funcionService, PeliculaService peliculaService){
        this.funcionService = funcionService; this.peliculaService = peliculaService;
    }

    @GetMapping
    public String listado(Model model){
        model.addAttribute("funciones", funcionService.proximas());
        return "funcion/listado";
    }

    @GetMapping("/nueva")
    public String nueva(Model model){
        model.addAttribute("funcion", new Funcion());
        model.addAttribute("peliculas", peliculaService.listar());
        return "funcion/editar";
    }

    @PostMapping("/guardar")
    public String guardar(Funcion f, @RequestParam Integer idPelicula){
        Pelicula p = peliculaService.porId(idPelicula).orElseThrow();
        f.setPelicula(p);
        if (f.getDisponibles()==null) f.setDisponibles(f.getCapacidad());
        funcionService.guardar(f);
        return "redirect:/admin/funciones";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model){
        model.addAttribute("funcion", funcionService.porId(id).orElseThrow());
        model.addAttribute("peliculas", peliculaService.listar());
        return "funcion/editar";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id){
        funcionService.eliminar(id);
        return "redirect:/admin/funciones";
    }
}
