/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.caso2.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data
@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;
    
    @Column(nullable = false, length = 20)
    private String username;
    
    @Column(nullable = false, length = 512)
    private String password;
    
    @Column(nullable = false, length = 20)
    private String nombre;
    
    @Column(nullable = false, length = 30)
    private String apellidos;
    
    @Column(length = 75)
    private String correo;
    
    @Column(length = 15)
    private String telefono;
    
    @Column(name = "ruta_imagen", length = 1024)
    private String rutaImagen;
    
    @Column(nullable = false)
    private boolean activo;
    
    @OneToMany
    @JoinColumn(name = "id_usuario")
    private List<Rol> roles;
}
