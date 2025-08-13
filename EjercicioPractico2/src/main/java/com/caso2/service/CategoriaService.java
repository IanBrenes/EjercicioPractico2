/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.caso2.service;

import com.caso2.domain.Categoria;
import java.util.List;

public interface CategoriaService {

    // Lista todas las categorías
    List<Categoria> getCategorias();

    // Obtiene una categoría por su ID
    Categoria getCategoria(Long idCategoria);
    
    // Guarda o actualiza una categoría
    Categoria save(Categoria categoria);
    
    // Elimina una categoría por su ID
    void delete(Long idCategoria);
}

