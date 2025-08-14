package com.caso2.dao;

import com.caso2.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UsuarioDao extends JpaRepository<Usuario, Long> {
    Usuario findByUsername(String username);
    Usuario findByUsernameAndPassword(String username, String password);
    Usuario findByUsernameOrCorreo(String username, String correo);

    boolean existsByUsernameOrCorreo(String username, String correo);

    @Query("SELECT u FROM Usuario u WHERE LOWER(u.username)=LOWER(?1)")
    Usuario findByUsernameIgnoreCase(String username);
}
