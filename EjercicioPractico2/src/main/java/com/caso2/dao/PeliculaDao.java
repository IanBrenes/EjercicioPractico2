/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.caso2.dao;

/**
 *
 * @author erick
 */

import com.caso2.domain.Pelicula;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PeliculaDao extends JpaRepository<Pelicula, Long> {

    List<Pelicula> findByPrecioBetweenOrderByTitulo(double precioInf, double precioSup);

    @Query("SELECT p FROM Pelicula p WHERE p.precio BETWEEN :precioInf AND :precioSup ORDER BY p.titulo ASC")
    List<Pelicula> metodoJPQL(@Param("precioInf") double precioInf, @Param("precioSup") double precioSup);

    @Query(
        value = "SELECT * FROM pelicula WHERE precio BETWEEN :precioInf AND :precioSup ORDER BY titulo ASC",
        nativeQuery = true
    )
    List<Pelicula> metodoNativo(@Param("precioInf") double precioInf, @Param("precioSup") double precioSup);
}

