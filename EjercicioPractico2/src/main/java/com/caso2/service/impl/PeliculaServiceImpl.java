package com.caso2.service.impl;

import com.caso2.dao.PeliculaDao;
import com.caso2.domain.Pelicula;
import com.caso2.service.PeliculaService;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class PeliculaServiceImpl implements PeliculaService {
    private final PeliculaDao dao;
    public PeliculaServiceImpl(PeliculaDao dao){ this.dao = dao; }
    @Override public List<Pelicula> listar(){ return dao.findAll(); }
    @Override public Optional<Pelicula> porId(Integer id){ return dao.findById(id); }
    @Override public Pelicula guardar(Pelicula p){ return dao.save(p); }
    @Override public void eliminar(Integer id){ dao.deleteById(id); }
}
