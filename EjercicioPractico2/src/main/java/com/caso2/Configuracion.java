package com.caso2;

import java.util.Locale;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
@EnableMethodSecurity // para @PreAuthorize en controladores/servicios
public class Configuracion implements WebMvcConfigurer {

    // ---- i18n ----
    @Bean
    public LocaleResolver localeResolver() {
        var slr = new SessionLocaleResolver();
        slr.setDefaultLocale(new Locale("es"));
        return slr;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        var lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registro) {
        registro.addInterceptor(localeChangeInterceptor());
    }

    // ---- Vistas simples ----
    @Override
    public void addViewControllers(ViewControllerRegistry registro) {
        // Si tu CarteleraController ya mapea "/", podés dejar esto igual.
        registro.addViewController("/").setViewName("cine/cartelera");
        registro.addViewController("/cartelera").setViewName("cine/cartelera");
        registro.addViewController("/login");
        registro.addViewController("/errores/403").setViewName("/errores/403");
    }

    // ---- Usuarios en memoria para pruebas ----
    @Bean
    public UserDetailsService users() {
        UserDetails admin = User.builder()
                .username("juan")
                .password("{noop}123")
                .roles("USER", "ADMIN")
                .build();
        UserDetails vendedor = User.builder()
                .username("rebeca")
                .password("{noop}456")
                .roles("USER") // si querés usar VENDEDOR, agregalo aquí y en las reglas
                .build();
        UserDetails user = User.builder()
                .username("pedro")
                .password("{noop}789")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user, vendedor, admin);
    }

    // ---- Seguridad HTTP ----
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                // Público
                .requestMatchers("/", "/index", "/cartelera").permitAll()
                .requestMatchers("/webjars/**", "/css/**", "/js/**", "/images/**", "/static/**").permitAll()
                // Solo ADMIN
                .requestMatchers("/admin/**").hasRole("ADMIN")
                // Autenticado (USER o ADMIN)
                .requestMatchers("/reservas/**").hasAnyRole("USER","ADMIN")
                // Lo demás, autenticado
                .anyRequest().authenticated()
            )
            .formLogin(login -> login
                .loginPage("/login").permitAll()
                .defaultSuccessUrl("/cartelera", true)
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .permitAll()
            )
            .exceptionHandling(ex -> ex
                .accessDeniedPage("/errores/403")
            )
            // Si aún no estás usando el token CSRF en formularios, desactívalo para evitar 403.
            .csrf(csrf -> csrf.disable());

        return http.build();
    }
}
