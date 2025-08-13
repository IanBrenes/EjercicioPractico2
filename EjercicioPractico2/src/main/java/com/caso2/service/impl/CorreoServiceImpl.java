/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.caso2.service.impl;

import com.caso2.service.CorreoService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service // Indica que esta clase es un servicio de Spring
public class CorreoServiceImpl implements CorreoService {

    @Autowired // Inyección automática de JavaMailSender
    private JavaMailSender mailSender;

    @Override
    public void enviarCorreoHtml(
            String para,        // Dirección del destinatario
            String asunto,      // Asunto del correo
            String contenidoHtml // Contenido en formato HTML
    ) throws MessagingException {

        // Crear un mensaje de tipo MIME
        MimeMessage message = mailSender.createMimeMessage();

        // Helper para configurar el correo (texto HTML, destinatario, asunto, etc.)
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        // Configurar destinatario, asunto y contenido
        helper.setTo(para);
        helper.setSubject(asunto);
        helper.setText(contenidoHtml, true); // "true" para indicar que es HTML

        // Enviar el mensaje
        mailSender.send(message);
    }
}

