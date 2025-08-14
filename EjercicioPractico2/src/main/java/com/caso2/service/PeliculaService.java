package com.caso2.service;
import com.caso2.domain.Pelicula;
import java.util.List;
import java.util.Optional;

public interface PeliculaService {
    List<Pelicula> listar();
    Optional<Pelicula> porId(Integer id);
    Pelicula guardar(Pelicula p);
    void eliminar(Integer id);
}
