/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.caso2.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Item extends Carro {
    
    private int cantidad; // Cantidad de unidades de un carro en el carrito

    public Item() {
    }

    public Item(Carro carro) {
        super.setIdCarro(carro.getIdCarro());
        super.setCategoria(carro.getCategoria());
        super.setDescripcion(carro.getDescripcion());
        super.setCilindros(carro.getCilindros());
        super.setModelo(carro.getModelo());
        super.setPrecio(carro.getPrecio());
        super.setActivo(carro.isActivo());
        this.cantidad = 0;
    }
}

