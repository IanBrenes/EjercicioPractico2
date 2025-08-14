/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.caso2.service;

import com.caso2.domain.Contacto;
import java.util.List;

public interface ContactoService {
    void guardarContacto(Contacto contacto);
    List<Contacto> listarContactos();
}