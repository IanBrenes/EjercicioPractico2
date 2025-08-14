package com.caso2.service;
import com.caso2.domain.Funcion;
import com.caso2.domain.Pelicula;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface FuncionService {
    List<Funcion> proximas();
    List<Funcion> porPelicula(Pelicula p);
    Optional<Funcion> porId(Integer id);
    Funcion guardar(Funcion f);
    void eliminar(Integer id);
}
