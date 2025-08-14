package com.caso2.service.impl;

import com.caso2.dao.FuncionDao;
import com.caso2.domain.Funcion;
import com.caso2.domain.Pelicula;
import com.caso2.service.FuncionService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class FuncionServiceImpl implements FuncionService {
    private final FuncionDao dao;
    public FuncionServiceImpl(FuncionDao dao){ this.dao = dao; }

    @Override public List<Funcion> proximas(){
        return dao.findByFechaHoraAfterAndActivoTrue(LocalDateTime.now());
    }
    @Override public List<Funcion> porPelicula(Pelicula p){ return dao.findByPeliculaAndActivoTrueOrderByFechaHoraAsc(p); }
    @Override public Optional<Funcion> porId(Integer id){ return dao.findById(id); }
    @Override public Funcion guardar(Funcion f){ 
        if (f.getDisponibles() == null) f.setDisponibles(f.getCapacidad());
        return dao.save(f); 
    }
    @Override public void eliminar(Integer id){ dao.deleteById(id); }
}
