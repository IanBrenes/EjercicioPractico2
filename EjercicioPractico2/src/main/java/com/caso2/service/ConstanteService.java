/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.caso2.service;

import com.caso2.domain.Constante;
import java.util.List;

public interface ConstanteService {
    
    // Lista todas las constantes
    List<Constante> getConstantes();
    
    // Obtiene una constante por su ID
    Constante getConstante(Long idConstante);
    
    // Obtiene una constante por su atributo
    Constante getConstantePorAtributo(String atributo);
    
    // Guarda o actualiza una constante
    Constante save(Constante constante);
    
    // Elimina una constante por su ID
    void delete(Long idConstante);
}

