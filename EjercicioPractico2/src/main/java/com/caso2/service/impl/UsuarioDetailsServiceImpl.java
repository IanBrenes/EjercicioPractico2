package com.caso2.service.impl;

import com.caso2.dao.UsuarioDao;
import com.caso2.domain.Rol;
import com.caso2.domain.Usuario;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
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
    private HttpSession session;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioDao.findByUsername(username);
        if (usuario == null) {
            throw new UsernameNotFoundException(username);
        }

        // Si usas una imagen en sesión
        //session.removeAttribute("usuarioImagen");
        //session.setAttribute("usuarioImagen", usuario.getRutaImagen());

        List<GrantedAuthority> roles = new ArrayList<>();
        for (Rol rol : usuario.getRoles()) {
            String nombre = rol.getNombre();
            // Asegurar prefijo ROLE_
            if (nombre != null && !nombre.startsWith("ROLE_")) {
                nombre = "ROLE_" + nombre;
            }
            roles.add(new SimpleGrantedAuthority(nombre));
        }

        // Importante: la password que regresa viene encriptada en BD (BCrypt),
        // Spring compara usando el PasswordEncoder @Bean.
        return new User(usuario.getUsername(), usuario.getPassword(), usuario.isActivo(), true, true, true, roles);
    }
}