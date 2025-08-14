package com.caso2.service.impl;

import com.caso2.dao.FuncionDao;
import com.caso2.dao.ReservaDao;
import com.caso2.domain.Funcion;
import com.caso2.domain.Reserva;
import com.caso2.domain.Usuario;
import com.caso2.service.ReservaService;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ReservaServiceImpl implements ReservaService {
    private final ReservaDao reservaDao;
    private final FuncionDao funcionDao;

    public ReservaServiceImpl(ReservaDao reservaDao, FuncionDao funcionDao){
        this.reservaDao = reservaDao;
        this.funcionDao = funcionDao;
    }

    @Override
    @Transactional
    public Reserva reservar(Integer idFuncion, Usuario usuario, int cantidad){
        Funcion f = funcionDao.findById(idFuncion)
                .orElseThrow(() -> new IllegalArgumentException("Función no existe"));
        if (!f.isActivo()) throw new IllegalStateException("Función inactiva");
        if (cantidad <= 0) throw new IllegalArgumentException("Cantidad inválida");
        if (f.getDisponibles() < cantidad) throw new IllegalStateException("No hay asientos suficientes");

        f.setDisponibles(f.getDisponibles() - cantidad);
        funcionDao.save(f);

        Reserva r = new Reserva();
        r.setUsuario(usuario);
        r.setFuncion(f);
        r.setCantidad(cantidad);
        r.setTotal(f.getPrecio().multiply(new BigDecimal(cantidad)));
        // estado=1 (activa) por defecto
        return reservaDao.save(r);
    }

    @Override
    @Transactional
    public void cancelar(Integer idReserva, Usuario usuario){
        Reserva r = reservaDao.findById(idReserva)
                .orElseThrow(() -> new IllegalArgumentException("Reserva no existe"));
        if (!r.getUsuario().getIdUsuario().equals(usuario.getIdUsuario()))
            throw new SecurityException("No puede cancelar reservas de otros usuarios");
        if (r.getEstado() == 2) return; // ya cancelada

        r.setEstado(2);
        reservaDao.save(r);

        Funcion f = r.getFuncion();
        f.setDisponibles(f.getDisponibles() + r.getCantidad());
        funcionDao.save(f);
    }

    @Override
    public List<Reserva> misReservas(Usuario usuario){
        return reservaDao.findByUsuarioOrderByFechaDesc(usuario);
    }
}
