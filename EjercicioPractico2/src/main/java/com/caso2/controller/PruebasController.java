/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.caso2.controller;

import com.caso2.domain.Pelicula;
import com.caso2.service.PeliculaService;
import com.caso2.domain.Categoria;
import com.caso2.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/pruebas")
public class PruebasController {

    @Autowired
    private PeliculaService peliculaService;
    
    @Autowired
    private CategoriaService categoriaService; // Elimina si no usas categorías

    @GetMapping("/listado")
    public String listado(Model model) {
        var peliculas = peliculaService.getPeliculas(false);
        var categorias = categoriaService.getCategorias(false); // Elimina si no hay categorías
        model.addAttribute("peliculas", peliculas);
        model.addAttribute("totalPeliculas", peliculas.size());
        model.addAttribute("categorias", categorias);
        return "/pruebas/listado";
    }

    @GetMapping("/listado/{idCategoria}")
    public String listado(Model model, Categoria categoria) {
        var peliculas = categoriaService.getCategoria(categoria).getPeliculas();
        var categorias = categoriaService.getCategorias(false);
        model.addAttribute("peliculas", peliculas);
        model.addAttribute("totalPeliculas", peliculas.size());
        model.addAttribute("categorias", categorias);
        return "/pruebas/listado";
    }
    
    @PostMapping("/query1")
    public String consultaQuery1(@RequestParam(value = "precioInf") double precioInf,
                                 @RequestParam(value = "precioSup") double precioSup,
                                 Model model) {
        var peliculas = peliculaService.findByPrecioBetweenOrderByTitulo(precioInf, precioSup);
        model.addAttribute("peliculas", peliculas);
        model.addAttribute("precioInf", precioInf);
        model.addAttribute("precioSup", precioSup);
        return "/pruebas/listado";
    }
    
    @PostMapping("/query2")
    public String consultaQuery2(@RequestParam(value = "precioInf") double precioInf,
                                 @RequestParam(value = "precioSup") double precioSup,
                                 Model model) {
        var peliculas = peliculaService.metodoJPQL(precioInf, precioSup);
        model.addAttribute("peliculas", peliculas);
        model.addAttribute("totalPeliculas", peliculas.size());
        model.addAttribute("precioInf", precioInf);
        model.addAttribute("precioSup", precioSup);
        return "/pruebas/listado2";
    }

    @PostMapping("/query3")
    public String consultaQuery3(@RequestParam(value = "precioInf") double precioInf,
                                 @RequestParam(value = "precioSup") double precioSup,
                                 Model model) {
        var peliculas = peliculaService.metodoNativo(precioInf, precioSup);
        model.addAttribute("peliculas", peliculas);
        model.addAttribute("totalPeliculas", peliculas.size());
        model.addAttribute("precioInf", precioInf);
        model.addAttribute("precioSup", precioSup);
        return "/pruebas/listado2";
    }
}