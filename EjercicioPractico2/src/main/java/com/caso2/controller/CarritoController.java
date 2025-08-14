/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.caso2.controller;

import com.caso2.domain.Item;
import com.caso2.domain.Pelicula;
import com.caso2.service.ItemService;
import com.caso2.service.PeliculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CarritoController {

   @Autowired
   private ItemService itemService;

   @Autowired
   private PeliculaService peliculaService;

   // Para ver el carrito
   @GetMapping("/carrito/listado")
   public String inicio(Model model) {
       var items = itemService.gets();
       if (items == null) items = java.util.Collections.emptyList(); // guard rápido
       model.addAttribute("items", items);
       var carritoTotalVenta = 0.0;
       for (Item i : items) {
           carritoTotalVenta += (i.getCantidad() * i.getPrecio());
       }
       model.addAttribute("carritoTotal", carritoTotalVenta);
       return "/carrito/listado";
   }

   @GetMapping("/carrito/agregar/{idPelicula}")
   public ModelAndView agregarItem(Model model, Item item) {
       Item item2 = itemService.get(item);
       if (item2 == null) {
           // Item extiende Pelicula; el binder pone idPelicula desde el path
           Pelicula pelicula = peliculaService.getPelicula(item);
           item2 = new Item(pelicula);
       }
       itemService.save(item2);

       var lista = itemService.gets();
       if (lista == null) lista = java.util.Collections.emptyList();
       var totalCarritos = 0;
       var carritoTotalVenta = 0.0;
       for (Item i : lista) {
           totalCarritos += i.getCantidad();
           carritoTotalVenta += (i.getCantidad() * i.getPrecio());
       }
       model.addAttribute("listaItems", lista);
       model.addAttribute("listaTotal", totalCarritos);
       model.addAttribute("carritoTotal", carritoTotalVenta);
       return new ModelAndView("/carrito/fragmentos :: verCarrito");
   }

   // Para modificar un producto del carrito
   @GetMapping("/carrito/modificar/{idPelicula}")
   public String modificarItem(Item item, Model model) {
       item = itemService.get(item);
       model.addAttribute("item", item);
       return "/carrito/modifica";
   }

   // Para eliminar un elemento del carrito
   @GetMapping("/carrito/eliminar/{idPelicula}")
   public String eliminarItem(Item item) {
       itemService.delete(item);
       return "redirect:/carrito/listado";
   }

   @PostMapping("/carrito/guardar")
   public String guardarItem(Item item) {
       itemService.update(item);
       return "redirect:/carrito/listado";
   }

   @GetMapping("/facturar/carrito")
   public String facturarCarrito() {
       itemService.facturar();
       return "redirect:/";
   }
}