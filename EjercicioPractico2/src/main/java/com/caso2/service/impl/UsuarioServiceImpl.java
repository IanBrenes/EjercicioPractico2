package com.caso2.service.impl;

import com.caso2.dao.UsuarioDao;
import com.caso2.domain.Rol;
import com.caso2.domain.Usuario;
import com.caso2.service.UsuarioService;
import java.util.List;
import java.util.Objects;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioDao usuarioDao;

    public UsuarioServiceImpl(UsuarioDao usuarioDao) {
        this.usuarioDao = usuarioDao;
    }

    // -------- LISTAR ----------
    @Override
    @Transactional(readOnly = true)
    public List<Usuario> getUsuarios() {
        return usuarioDao.findAll();
    }

    // -------- GET por entidad ----------
    @Override
    @Transactional(readOnly = true)
    public Usuario getUsuario(Usuario usuario) {
        if (usuario == null || usuario.getIdUsuario() == null) return null;
        return usuarioDao.findById(usuario.getIdUsuario()).orElse(null);
    }

    // -------- GET por ID (firma usa Long; repo también) ----------
    @Override
    @Transactional(readOnly = true)
    public Usuario getUsuario(Long idUsuario) {
        if (idUsuario == null) return null;
        return usuarioDao.findById(idUsuario).orElse(null);
    }

    // -------- Buscar por username ----------
    @Override
    @Transactional(readOnly = true)
    public Usuario getUsuarioPorUsername(String username) {
        if (username == null || username.isBlank()) return null;
        return usuarioDao.findByUsername(username);
    }

    // -------- Buscar por username + password (solo si usas auth propia) ----------
    @Override
    @Transactional(readOnly = true)
    public Usuario getUsuarioPorUsernameYPassword(String username, String password) {
        if (username == null || password == null) return null;
        return usuarioDao.findByUsernameAndPassword(username, password);
    }

    // -------- Buscar por username O correo ----------
    @Override
    @Transactional(readOnly = true)
    public Usuario getUsuarioPorUsernameOCorreo(String username, String correo) {
        if ((username == null || username.isBlank()) && (correo == null || correo.isBlank())) return null;
        return usuarioDao.findByUsernameOrCorreo(username, correo);
    }

    // -------- Existe por username O correo ----------
    @Override
    @Transactional(readOnly = true)
    public boolean existeUsuarioPorUsernameOCorreo(String username, String correo) {
        if ((username == null || username.isBlank()) && (correo == null || correo.isBlank())) return false;
        return usuarioDao.existsByUsernameOrCorreo(username, correo);
    }

    // -------- Guardar ----------
    @Override
    @Transactional
    public Usuario save(Usuario usuario) {
        Objects.requireNonNull(usuario, "usuario no puede ser null");
        Objects.requireNonNull(usuario.getUsername(), "username es requerido");
        Objects.requireNonNull(usuario.getPassword(), "password es requerido");

        // Normaliza prefijo ROLE_
        if (usuario.getRoles() != null) {
            for (Rol r : usuario.getRoles()) {
                if (r != null && r.getNombre() != null && !r.getNombre().startsWith("ROLE_")) {
                    r.setNombre("ROLE_" + r.getNombre());
                }
            }
        }
        return usuarioDao.save(usuario);
    }

    // -------- Guardar con bandera para crear rol USER (compat) ----------
    @Override
    @Transactional
    public void save(Usuario usuario, boolean crearRolUser) {
        if (crearRolUser) {
            boolean tieneUser = false;
            if (usuario.getRoles() != null) {
                for (Rol r : usuario.getRoles()) {
                    if (r != null && r.getNombre() != null && r.getNombre().equalsIgnoreCase("ROLE_USER")) {
                        tieneUser = true;
                        break;
                    }
                }
            }
            if (!tieneUser) {
                Rol nuevo = new Rol();
                nuevo.setNombre("ROLE_USER");
                usuario.getRoles().add(nuevo);
            }
        }

        if (usuario.getRoles() != null) {
            for (Rol r : usuario.getRoles()) {
                if (r != null && r.getNombre() != null && !r.getNombre().startsWith("ROLE_")) {
                    r.setNombre("ROLE_" + r.getNombre());
                }
            }
        }

        usuarioDao.save(usuario);
    }

    // -------- Eliminar por entidad ----------
    @Override
    @Transactional
    public void delete(Usuario usuario) {
        if (usuario == null || usuario.getIdUsuario() == null) return;
        usuarioDao.deleteById(usuario.getIdUsuario());
    }

    // -------- Eliminar por ID ----------
    @Override
    @Transactional
    public void delete(Long idUsuario) {
        if (idUsuario == null) return;
        usuarioDao.deleteById(idUsuario);
    }

    // -------- Usuario autenticado (para reservas) ----------
    @Override
    @Transactional(readOnly = true)
    public Usuario getUsuarioActual() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal())) {
            throw new IllegalStateException("No hay usuario autenticado");
        }
        String username = auth.getName();
        Usuario u = usuarioDao.findByUsername(username);
        if (u == null) {
            throw new IllegalStateException("Usuario autenticado no existe en BD: " + username);
        }
        return u;
    }
}
