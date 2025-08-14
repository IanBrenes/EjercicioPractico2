package com.caso2.dao;
import com.caso2.domain.Reserva;
import com.caso2.domain.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservaDao extends JpaRepository<Reserva, Integer> {
    List<Reserva> findByUsuarioOrderByFechaDesc(Usuario usuario);
}
