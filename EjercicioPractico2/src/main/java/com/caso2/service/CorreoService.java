/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.caso2.service;


import jakarta.mail.MessagingException;

public interface CorreoService {

    // Método para enviar un correo con contenido HTML
    public void enviarCorreoHtml(
            String para, // Dirección del destinatario
            String asunto, // Asunto del correo
            String contenidoHtml) // Contenido en formato HTML
            throws MessagingException; // Puede lanzar excepción si falla el envío
}