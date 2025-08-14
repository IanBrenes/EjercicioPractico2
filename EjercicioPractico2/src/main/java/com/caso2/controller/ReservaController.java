package com.caso2.controller;

import com.caso2.domain.Usuario;
import com.caso2.service.ReservaService;
import com.caso2.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/reservas")
public class ReservaController {

    private final ReservaService reservaService;
    private final UsuarioService usuarioService;

    public ReservaController(ReservaService reservaService, UsuarioService usuarioService){
        this.reservaService = reservaService; this.usuarioService = usuarioService;
    }

    @GetMapping("/nueva/{idFuncion}")
    public String nueva(@PathVariable Integer idFuncion, Model model){
        model.addAttribute("idFuncion", idFuncion);
        return "reserva/nueva";
    }

    @PostMapping("/confirmar")
    public String confirmar(@RequestParam Integer idFuncion,
                            @RequestParam Integer cantidad, Model model){
        Usuario u = usuarioService.getUsuarioActual(); // <-- tu método
        reservaService.reservar(idFuncion, u, cantidad);
        return "redirect:/reservas/mis";
    }

    @GetMapping("/mis")
    public String mis(Model model){
        Usuario u = usuarioService.getUsuarioActual();
        model.addAttribute("reservas", reservaService.misReservas(u));
        return "reserva/mis";
    }

    @GetMapping("/cancelar/{id}")
    public String cancelar(@PathVariable Integer id){
        Usuario u = usuarioService.getUsuarioActual();
        reservaService.cancelar(id, u);
        return "redirect:/reservas/mis";
    }
}
