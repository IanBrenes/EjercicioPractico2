/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.caso2.controller;

import com.caso2.domain.Categoria;
import com.caso2.service.CarroService;
import com.caso2.service.CategoriaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/pruebas")
public class PruebasController {

    @Autowired
    private CarroService carroService;

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/listado")
    public String listado(Model model) {
        var carros = carroService.getCarros();
        var categorias = categoriaService.getCategorias();

        model.addAttribute("productos", carros);           // reutilizo el nombre "productos" de tus vistas
        model.addAttribute("totalProductos", carros.size());
        model.addAttribute("categorias", categorias);
        return "/pruebas/listado";
    }

    @GetMapping("/listado/{idCategoria}")
    public String listadoPorCategoria(Model model, @PathVariable Long idCategoria) {
        var carros = carroService.getCarrosByCategoria(idCategoria);
        var categorias = categoriaService.getCategorias();

        model.addAttribute("productos", carros);
        model.addAttribute("totalProductos", carros.size());
        model.addAttribute("categorias", categorias);
        return "/pruebas/listado";
    }

    // Consultas ampliadas (por rango de precio)
    @GetMapping("/listado2")
    public String listado2(Model model) {
        var carros = carroService.getCarros();
        model.addAttribute("productos", carros);
        model.addAttribute("totalProductos", carros.size());
        return "/pruebas/listado2";
    }

    @PostMapping("/query1")
    public String consultaQuery1(@RequestParam("precioInf") double precioInf,
                                 @RequestParam("precioSup") double precioSup,
                                 Model model) {
        var carros = carroService.findByPrecioBetweenOrderByModelo(precioInf, precioSup);
        model.addAttribute("productos", carros);
        model.addAttribute("totalProductos", carros.size());
        model.addAttribute("precioInf", precioInf);
        model.addAttribute("precioSup", precioSup);
        return "/pruebas/listado";
    }

    @PostMapping("/query2")
    public String consultaQuery2(@RequestParam("precioInf") double precioInf,
                                 @RequestParam("precioSup") double precioSup,
                                 Model model) {
        var carros = carroService.metodoJPQL(precioInf, precioSup);
        model.addAttribute("productos", carros);
        model.addAttribute("totalProductos", carros.size());
        model.addAttribute("precioInf", precioInf);
        model.addAttribute("precioSup", precioSup);
        return "/pruebas/listado2";
    }

    @PostMapping("/query3")
    public String consultaQuery3(@RequestParam("precioInf") double precioInf,
                                 @RequestParam("precioSup") double precioSup,
                                 Model model) {
        var carros = carroService.metodoNativo(precioInf, precioSup);
        model.addAttribute("productos", carros);
        model.addAttribute("totalProductos", carros.size());
        model.addAttribute("precioInf", precioInf);
        model.addAttribute("precioSup", precioSup);
        return "/pruebas/listado2";
    }
}
