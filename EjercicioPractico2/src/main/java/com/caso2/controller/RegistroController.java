/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.caso2.controller;

import com.caso2.domain.Usuario;
import com.caso2.service.RegistroService;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@Slf4j
@RequestMapping("/registro")
public class RegistroController {

    @Autowired
    private RegistroService registroService;

    // Formulario: nuevo registro
    @GetMapping("/nuevo")
    public String nuevo(Model model, Usuario usuario) {
        return "/registro/nuevo";
    }

    // Formulario: recordar cuenta
    @GetMapping("/recordar")
    public String recordar(Model model, Usuario usuario) {
        return "/registro/recordar";
    }

    // Crear usuario
    @PostMapping("/crearUsuario")
    public String crearUsuario(Model model, Usuario usuario) throws MessagingException {
        model = registroService.crearUsuario(model, usuario);
        return "/registro/salida";
    }

    // Mostrar pantalla de activación desde enlace
    @GetMapping("/activar/{usuario}/{id}")
    public String activar(Model model,
                          @PathVariable("usuario") String usuario,
                          @PathVariable("id") String id) {
        model = registroService.activar(model, usuario, id);
        return model.containsAttribute("usuario") ? "/registro/salida" : "/registro/activar";
    }

    // Procesar activación (con imagen)
    @PostMapping("/activar")
    public String activar(Usuario usuario,
                          @RequestParam("imagenFile") MultipartFile imagenFile)
            throws MessagingException {
        registroService.activar(usuario, imagenFile);
        return "redirect:/";
    }

    // Recordar usuario (recuperación)
    @PostMapping("/recordarUsuario")
    public String recordarUsuario(Model model, Usuario usuario) throws MessagingException {
        model = registroService.recordarUsuario(model, usuario);
        return "/registro/salida";
    }
}
