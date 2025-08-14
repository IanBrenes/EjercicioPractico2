/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.caso2.domain;


import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Item extends Pelicula {
    private int cantidad; // Cantidad de entradas / items

    public Item() {
    }

    public Item(Pelicula pelicula) {
        super.setIdPelicula(pelicula.getIdPelicula());
        super.setTitulo(pelicula.getTitulo());
        super.setCategoria(pelicula.getCategoria());
        super.setPrecio(pelicula.getPrecio());
        super.setActivo(pelicula.isActivo());
        this.cantidad = 0;
    }
}