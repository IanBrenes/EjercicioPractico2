/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.caso2.service.impl;

import com.caso2.dao.ConstanteDao;
import com.caso2.domain.Constante;
import com.caso2.service.ConstanteService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConstanteServiceImpl implements ConstanteService {

    @Autowired
    private ConstanteDao constanteDao;

    @Override
    @Transactional(readOnly = true)
    public List<Constante> getConstantes() {
        return constanteDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Constante getConstante(Long idConstante) {
        return constanteDao.findById(idConstante).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Constante getConstantePorAtributo(String atributo) {
        return constanteDao.findByAtributo(atributo);
    }

    @Override
    @Transactional
    public Constante save(Constante constante) {
        return constanteDao.save(constante);
    }

    @Override
    @Transactional
    public void delete(Long idConstante) {
        constanteDao.deleteById(idConstante);
    }

    /* ====== Compatibilidad (firmas antiguas) ====== */

    @Transactional(readOnly = true)
    public Constante getConstante(Constante constante) {
        if (constante == null || constante.getIdConstante() == null) return null;
        return getConstante(constante.getIdConstante());
    }

    @Transactional
    public void delete(Constante constante) {
        if (constante != null && constante.getIdConstante() != null) {
            delete(constante.getIdConstante());
        }
    }
}

