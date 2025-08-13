/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.caso2.service;

import jakarta.mail.MessagingException;

public interface CorreoService {

    // Enviar correo con contenido HTML
    void enviarCorreoHtml(
            String para,
            String asunto,
            String contenidoHtml
    ) throws MessagingException;
}

