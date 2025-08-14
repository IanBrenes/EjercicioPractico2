/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.caso2.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name="categoria")
public class Categoria implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_categoria")
    private Long idCategoria;

    private String descripcion;
    private boolean activo;

    // Antes: List<Carro> carros;
    @OneToMany
    @JoinColumn(name="id_categoria")
    private List<Pelicula> peliculas;

    public Categoria() {
    }

    public Categoria(String categoria, boolean activo) {
        this.descripcion = categoria;
        this.activo = activo;
    }
}