/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.caso2.service.impl;

import com.caso2.domain.Usuario;
import com.caso2.service.CorreoService;
import com.caso2.service.RegistroService;
import com.caso2.service.UsuarioService;
import jakarta.mail.MessagingException;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

@Service
public class RegistroServiceImpl implements RegistroService {

    @Autowired
    private CorreoService correoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private MessageSource messageSource;

    @Value("${servidor.http}")
    private String servidor;

    @Override
    public Model activar(Model model, String username, String clave) {
        // Buscar usuario por username y clave (según tu contrato actual)
        Usuario usuario = usuarioService.getUsuarioPorUsernameYPassword(username, clave);

        if (usuario != null) {
            model.addAttribute("usuario", usuario);
        } else {
            model.addAttribute("titulo",
                    messageSource.getMessage("registro.activar", null, Locale.getDefault()));
            model.addAttribute("mensaje",
                    messageSource.getMessage("registro.activar.error", null, Locale.getDefault()));
        }
        return model;
    }

    @Override
    public void activar(Usuario usuario, MultipartFile imagenFile) {
        // Encripta la contraseña DEFINITIVA ingresada en el formulario de activación
        var encoder = new BCryptPasswordEncoder();
        usuario.setPassword(encoder.encode(usuario.getPassword()));

        // Si algún día quieres guardar imagen en otra plataforma, este es el lugar.
        // if (!imagenFile.isEmpty()) { ... }

        // Guarda usuario y asigna roles según tu flag (compat con tu servicio)
        usuarioService.save(usuario, true);
    }

    @Override
    public Model crearUsuario(Model model, Usuario usuario) throws MessagingException {
        String mensaje;

        // Verifica si (username OR correo) ya existen
        if (!usuarioService.existeUsuarioPorUsernameOCorreo(usuario.getUsername(), usuario.getCorreo())) {

            // Genera clave temporal (texto plano para el enlace)
            String clave = demeClave();

            // Se guarda la clave temporal en password y se deja inactivo
            usuario.setPassword(clave);
            usuario.setActivo(false);

            // Guarda y asigna rol por defecto (según tu implementación)
            usuarioService.save(usuario, true);

            // Envía correo de activación
            enviaCorreoActivar(usuario, clave);

            mensaje = String.format(
                    messageSource.getMessage("registro.mensaje.activacion.ok", null, Locale.getDefault()),
                    usuario.getCorreo());
        } else {
            mensaje = String.format(
                    messageSource.getMessage("registro.mensaje.usuario.o.correo", null, Locale.getDefault()),
                    usuario.getUsername(), usuario.getCorreo());
        }

        model.addAttribute("titulo",
                messageSource.getMessage("registro.activar", null, Locale.getDefault()));
        model.addAttribute("mensaje", mensaje);

        return model;
    }

    @Override
    public Model recordarUsuario(Model model, Usuario usuario) throws MessagingException {
        String mensaje;

        // Buscar por username O correo
        Usuario usuario2 = usuarioService.getUsuarioPorUsernameOCorreo(
                usuario.getUsername(), usuario.getCorreo());

        if (usuario2 != null) {
            // Genera nueva clave temporal
            String clave = demeClave();

            // Desactiva y setea nueva clave temporal
            usuario2.setPassword(clave);
            usuario2.setActivo(false);

            // Guarda sin tocar roles
            usuarioService.save(usuario2, false);

            // Envía correo de recuperación
            enviaCorreoRecordar(usuario2, clave);

            mensaje = String.format(
                    messageSource.getMessage("registro.mensaje.recordar.ok", null, Locale.getDefault()),
                    usuario.getCorreo());
        } else {
            mensaje = String.format(
                    messageSource.getMessage("registro.mensaje.usuario.o.correo", null, Locale.getDefault()),
                    usuario.getUsername(), usuario.getCorreo());
        }

        model.addAttribute("titulo",
                messageSource.getMessage("registro.activar", null, Locale.getDefault()));
        model.addAttribute("mensaje", mensaje);

        return model;
    }

    /* ===================== Helpers ===================== */

    private String demeClave() {
        String tira = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_*+";
        String clave = "";
        for (int i = 0; i < 40; i++) {
            clave += tira.charAt((int) (Math.random() * tira.length()));
        }
        return clave;
    }

    private void enviaCorreoActivar(Usuario usuario, String clave) throws MessagingException {
        String mensaje = messageSource.getMessage("registro.correo.activar", null, Locale.getDefault());
        mensaje = String.format(mensaje, usuario.getNombre(), usuario.getApellidos(),
                servidor, usuario.getUsername(), clave);

        String asunto = messageSource.getMessage("registro.mensaje.activacion", null, Locale.getDefault());
        correoService.enviarCorreoHtml(usuario.getCorreo(), asunto, mensaje);
    }

    private void enviaCorreoRecordar(Usuario usuario, String clave) throws MessagingException {
        String mensaje = messageSource.getMessage("registro.correo.recordar", null, Locale.getDefault());
        mensaje = String.format(mensaje, usuario.getNombre(), usuario.getApellidos(),
                servidor, usuario.getUsername(), clave);

        String asunto = messageSource.getMessage("registro.mensaje.recordar", null, Locale.getDefault());
        correoService.enviarCorreoHtml(usuario.getCorreo(), asunto, mensaje);
    }
}

