/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.caso2.service.impl;

import com.caso2.dao.CategoriaDao;
import com.caso2.domain.Categoria;
import com.caso2.service.CategoriaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    @Autowired
    private CategoriaDao categoriaDao;

    /* ======== API nueva (por ID) ======== */

    @Override
    @Transactional(readOnly = true)
    public List<Categoria> getCategorias() {
        return categoriaDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Categoria getCategoria(Long idCategoria) {
        return categoriaDao.findById(idCategoria).orElse(null);
    }

    @Override
    @Transactional
    public Categoria save(Categoria categoria) {
        return categoriaDao.save(categoria);
    }

    @Override
    @Transactional
    public void delete(Long idCategoria) {
        categoriaDao.deleteById(idCategoria);
    }

    /* ======== Métodos de compatibilidad (legacy) ======== */
    // Estos permiten seguir usando el código viejo sin cambiar llamadas.

    @Transactional(readOnly = true)
    public List<Categoria> getCategorias(boolean activos) {
        var lista = categoriaDao.findAll();
        if (activos) {
            lista.removeIf(c -> !c.isActivo());
        }
        return lista;
    }

    @Transactional(readOnly = true)
    public Categoria getCategoria(Categoria categoria) {
        if (categoria == null || categoria.getIdCategoria() == null) return null;
        return getCategoria(categoria.getIdCategoria());
    }

    @Transactional
    public void delete(Categoria categoria) {
        if (categoria != null && categoria.getIdCategoria() != null) {
            delete(categoria.getIdCategoria());
        }
    }
}

