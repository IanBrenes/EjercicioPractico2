package com.caso2;

import java.util.Locale;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class Configuracion implements WebMvcConfigurer {

    @Bean
    public LocaleResolver localeResolver() {
        var slr = new SessionLocaleResolver();
        slr.setDefaultLocale(new Locale("es"));
        return slr;
    }

    @Bean
    public LocaleChangeInterceptor localeChanceInterceptor() {
        var lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registro) {
        registro.addInterceptor(localeChanceInterceptor());
    }

    // Messages.properties
    @Bean("messageSource")
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource= new ResourceBundleMessageSource();
        messageSource.setBasenames("messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registro) {
        registro.addViewController("/").setViewName("index");
        registro.addViewController("/login");
        registro.addViewController("/errores/403").setViewName("/errores/403");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                // ===== Público =====
                .requestMatchers(
                    "/", "/index",
                    "/registro/**",
                    "/carrito/**",
                    "/webjars/**",
                    "/css/**", "/js/**", "/images/**", "/fonts/**",
                    // cartelera pública
                    "/pelicula/listado"
                ).permitAll()

                // ===== ADMIN: gestiona catálogos =====
                .requestMatchers(
                    "/pelicula/nuevo","/pelicula/guardar",
                    "/pelicula/modificar/**","/pelicula/eliminar/**",
                    "/categoria/nuevo","/categoria/guardar",
                    "/categoria/modificar/**","/categoria/eliminar/**",
                    "/usuario/nuevo","/usuario/guardar",
                    "/usuario/modificar/**","/usuario/eliminar/**",
                    "/reportes/**"
                ).hasRole("ADMIN")

                // ===== Listados internos (si los usas) =====
                .requestMatchers("/categoria/listado","/usuario/listado")
                    .hasAnyRole("ADMIN","USER")

                // ===== Facturación (USER y también ADMIN) =====
                .requestMatchers("/factura/**","/venta/**")
                    .hasAnyRole("USER","ADMIN")

                // ===== Checkout del carrito =====
                .requestMatchers("/facturar/carrito")
                    .hasRole("USER")

                // Cualquier otra ruta requiere autenticación
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .permitAll()
            )
            .logout(logout -> logout.permitAll());

        return http.build();
    }

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    public void configurerGlobal(AuthenticationManagerBuilder build) throws Exception {
        build.userDetailsService(userDetailsService)
             .passwordEncoder(new BCryptPasswordEncoder());
    }
}