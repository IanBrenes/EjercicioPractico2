package com.caso2.dao;

import com.caso2.domain.Rol;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RolDao extends JpaRepository<Rol, Integer> {

    // Busca los roles a partir del id del usuario, sin requerir Rol.usuario
    @Query("select r from Usuario u join u.roles r where u.idUsuario = :idUsuario")
    List<Rol> findByUsuarioId(@Param("idUsuario") Long idUsuario);
}
