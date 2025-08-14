package com.caso2.service.impl;

import com.caso2.dao.UsuarioDao;
import com.caso2.domain.Rol;
import com.caso2.domain.Usuario;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.ObjectProvider; // <-- IMPORTANTE
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userDetailsService")
public class UsuarioDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private ObjectProvider<jakarta.servlet.http.HttpSession> sessionProvider; // <-- OPCIONAL

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioDao.findByUsername(username);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado: " + username);
        }

        // Sesión opcional (en tests puede no existir)
        var session = sessionProvider.getIfAvailable();
        if (session != null) {
            session.setAttribute("usuarioImagen", usuario.getRutaImagen());
        }

        Collection<Rol> roles = usuario.getRoles() == null ? Set.of() : usuario.getRoles();
        var authorities = roles.stream()
                .map(Rol::getNombre)      // p.ej. ROLE_USER
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());

        return new User(usuario.getUsername(), usuario.getPassword(), authorities);
    }
}
