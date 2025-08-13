/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.caso2.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.Data;

@Data
@Entity
@Table(name = "venta")
public class Venta implements Serializable {    
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_venta")
    private Long idVenta;
    
    @Column(name = "id_factura", nullable = false)
    private Long idFactura;
    
    @Column(name = "id_carro", nullable = false)
    private Long idCarro;
    
    @Column(precision = 12, scale = 2, nullable = false)
    private double precio;
    
    @Column(nullable = false)
    private int cantidad;    
    
    public Venta() {
    }

    public Venta(Long idFactura, Long idCarro, double precio, int cantidad) {
        this.idFactura = idFactura;
        this.idCarro = idCarro;
        this.precio = precio;
        this.cantidad = cantidad;
    }
}

