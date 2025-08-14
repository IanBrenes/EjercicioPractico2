package com.caso2.dao;
import com.caso2.domain.Funcion;
import com.caso2.domain.Pelicula;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncionDao extends JpaRepository<Funcion, Integer> {
    List<Funcion> findByActivoTrueOrderByFechaHoraAsc();
    List<Funcion> findByPeliculaAndActivoTrueOrderByFechaHoraAsc(Pelicula pelicula);
    List<Funcion> findByFechaHoraAfterAndActivoTrue(LocalDateTime now);
}

