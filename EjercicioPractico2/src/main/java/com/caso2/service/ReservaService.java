package com.caso2.service;
import com.caso2.domain.Reserva;
import com.caso2.domain.Usuario;
import java.util.List;

public interface ReservaService {
    Reserva reservar(Integer idFuncion, Usuario usuario, int cantidad);
    void cancelar(Integer idReserva, Usuario usuario);
    List<Reserva> misReservas(Usuario usuario);
}
