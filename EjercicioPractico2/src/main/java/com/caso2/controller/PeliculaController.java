/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.caso2.controller;


import com.caso2.domain.Pelicula;
import com.caso2.service.CategoriaService;
import com.caso2.service.PeliculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/pelicula")
public class PeliculaController {
    
    @Autowired
    private PeliculaService peliculaService;

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/listado")
    public String listado(Model model) {
        var peliculas = peliculaService.getPeliculas(false);
        model.addAttribute("peliculas", peliculas);
        model.addAttribute("totalPeliculas", peliculas.size());
        model.addAttribute("pelicula", new Pelicula());
        model.addAttribute("categorias", categoriaService.getCategorias(true));
        return "pelicula/listado";
    }

    @GetMapping("/nuevo")
    public String nuevaPelicula(Pelicula pelicula, Model model) {
        var categorias = categoriaService.getCategorias(false);
        model.addAttribute("categorias", categorias);
        return "/pelicula/modificar";
    }

    @PostMapping("/guardar")
    public String guardarPelicula(Pelicula pelicula, Model model) {
        if (pelicula.getCategoria() == null || pelicula.getCategoria().getIdCategoria() == null) {
            model.addAttribute("pelicula", pelicula);
            model.addAttribute("categorias", categoriaService.getCategorias(true));
            model.addAttribute("error", "Debe seleccionar una categoría.");
            return "pelicula/modificar";
        }
        peliculaService.save(pelicula);
        return "redirect:/pelicula/listado";
    }

    @GetMapping("/eliminar/{idPelicula}")
    public String eliminarPelicula(Pelicula pelicula) {
        peliculaService.delete(pelicula);
        return "redirect:/pelicula/listado";
    }

    @GetMapping("/modificar/{idPelicula}")
    public String modificarPelicula(Pelicula pelicula, Model model) {
        pelicula = peliculaService.getPelicula(pelicula);
        model.addAttribute("pelicula", pelicula);
        var categorias = categoriaService.getCategorias(false);
        model.addAttribute("categorias", categorias);
        return "/pelicula/modificar";
    }
}
