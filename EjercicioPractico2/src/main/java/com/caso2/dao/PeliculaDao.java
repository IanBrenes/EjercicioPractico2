package com.caso2.dao;
import com.caso2.domain.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeliculaDao extends JpaRepository<Pelicula, Integer> { }

