package com.caso2.dao;

import com.caso2.domain.Carro;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CarroDao extends JpaRepository<Carro, Long> {

    /* ===== Derivadas por nombre ===== */
    List<Carro> findByActivoTrue();

    List<Carro> findByIdCategoriaAndActivoTrue(Long idCategoria);

    List<Carro> findByPrecioBetweenOrderByModeloAsc(BigDecimal precioInf, BigDecimal precioSup);

    List<Carro> findByModeloContainingIgnoreCaseAndActivoTrue(String termino);

    /* ===== JPQL ===== */
    @Query("SELECT c FROM Carro c " +
           "WHERE c.precio BETWEEN :inf AND :sup " +
           "ORDER BY c.modelo ASC")
    List<Carro> buscarPorRangoPrecio(
            @Param("inf") BigDecimal precioInf,
            @Param("sup") BigDecimal precioSup);

    /* ===== SQL nativo ===== */
    @Query(value = "SELECT * FROM carro " +
                   "WHERE precio BETWEEN :inf AND :sup " +
                   "ORDER BY modelo ASC",
           nativeQuery = true)
    List<Carro> buscarPorRangoPrecioNativo(
            @Param("inf") BigDecimal precioInf,
            @Param("sup") BigDecimal precioSup);
}
