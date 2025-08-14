package com.caso2.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data
@Entity
@Table(name = "pelicula")
public class Pelicula implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pelicula")
    private Integer idPelicula;

    @Column(nullable = false, length = 150)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String sinopsis;

    @Column(name = "duracion_min", nullable = false)
    private Integer duracionMin;

    private String clasificacion;
    private String director;

    @Column(name = "ruta_imagen", length = 1024)
    private String rutaImagen;

    @Column(nullable = false)
    private boolean activo = true;

    @OneToMany(mappedBy = "pelicula", fetch = FetchType.LAZY)
    private List<Funcion> funciones;
}
