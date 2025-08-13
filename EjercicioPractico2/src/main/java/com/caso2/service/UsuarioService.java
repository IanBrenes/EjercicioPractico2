/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.caso2.service;

import com.caso2.domain.Usuario;
import java.util.List;

public interface UsuarioService {
    
    /** Listado de usuarios */
    List<Usuario> getUsuarios();
    
    /** Obtener usuario por entidad (compat con tu código previo) */
    Usuario getUsuario(Usuario usuario);

    /** Obtener usuario por ID (usado en los nuevos controladores) */
    Usuario getUsuario(Long idUsuario);
    
    /** Buscar por username */
    Usuario getUsuarioPorUsername(String username);

    /** Buscar por username + password (si usas autenticación propia) */
    Usuario getUsuarioPorUsernameYPassword(String username, String password);
    
    /** Buscar por username O correo */
    Usuario getUsuarioPorUsernameOCorreo(String username, String correo);
    
    /** Validar existencia por username O correo */
    boolean existeUsuarioPorUsernameOCorreo(String username, String correo);
    
    /** Guardar/actualizar (devuelve la entidad persistida) */
    Usuario save(Usuario usuario);

    /** Guardar con bandera para crear rol USER (compat con tu firma previa) */
    void save(Usuario usuario, boolean crearRolUser);
    
    /** Eliminar por entidad (compat) */
    void delete(Usuario usuario);

    /** Eliminar por ID (usado en los nuevos controladores) */
    void delete(Long idUsuario);
}

