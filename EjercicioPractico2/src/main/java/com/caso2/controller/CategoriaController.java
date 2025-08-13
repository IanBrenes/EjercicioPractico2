/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.caso2.controller;

import com.caso2.domain.Categoria;
import com.caso2.service.CategoriaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@Slf4j
@RequestMapping("/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/listado")
    public String listado(Model model) {
        var categorias = categoriaService.getCategorias(); // sin boolean en caso2
        model.addAttribute("categorias", categorias);
        model.addAttribute("totalCategorias", categorias.size());
        return "/categoria/listado";
    }

    @GetMapping("/nuevo")
    public String categoriaNuevo(Model model) {
        model.addAttribute("categoria", new Categoria());
        return "/categoria/modifica";
    }

    @PostMapping("/guardar")
    public String categoriaGuardar(@ModelAttribute Categoria categoria,
                                   @RequestParam(value = "imagenFile", required = false) MultipartFile imagenFile) {
        // En caso2 no subimos imagen; si luego agregas subida, procesala aquí.
        categoriaService.save(categoria);
        return "redirect:/categoria/listado";
    }

    @GetMapping("/eliminar/{idCategoria}")
    public String categoriaEliminar(@PathVariable("idCategoria") Long idCategoria) {
        categoriaService.delete(idCategoria);
        return "redirect:/categoria/listado";
    }

    @GetMapping("/modificar/{idCategoria}")
    public String categoriaModificar(@PathVariable("idCategoria") Long idCategoria, Model model) {
        Categoria categoria = categoriaService.getCategoria(idCategoria);
        model.addAttribute("categoria", categoria);
        return "/categoria/modifica";
    }
}
