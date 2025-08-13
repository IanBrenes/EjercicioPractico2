/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.caso2.service.impl;

import com.caso2.dao.CarroDao;
import com.caso2.dao.FacturaDao;
import com.caso2.dao.UsuarioDao;
import com.caso2.dao.VentaDao;
import com.caso2.domain.Factura;
import com.caso2.domain.Item;
import com.caso2.domain.Usuario;
import com.caso2.domain.Venta;
import com.caso2.service.ItemService;
import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private HttpSession session;

    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private CarroDao carroDao;

    @Autowired
    private FacturaDao facturaDao;

    @Autowired
    private VentaDao ventaDao;

    @Override
    @SuppressWarnings("unchecked")
    public List<Item> gets() {
        List<Item> listaItems = (List<Item>) session.getAttribute("listaItems");
        return listaItems;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Item get(Item item) {
        List<Item> listaItems = (List<Item>) session.getAttribute("listaItems");
        if (listaItems != null) {
            for (Item i : listaItems) {
                if (Objects.equals(i.getIdCarro(), item.getIdCarro())) {
                    return i;
                }
            }
        }
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void delete(Item item) {
        List<Item> listaItems = (List<Item>) session.getAttribute("listaItems");
        if (listaItems != null) {
            int posicion = -1;
            boolean existe = false;
            for (Item i : listaItems) {
                posicion++;
                if (Objects.equals(i.getIdCarro(), item.getIdCarro())) {
                    existe = true;
                    break;
                }
            }
            if (existe) {
                listaItems.remove(posicion);
                session.setAttribute("listaItems", listaItems);
            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void save(Item item) {
        List<Item> listaItems = (List<Item>) session.getAttribute("listaItems");
        if (listaItems == null) {
            listaItems = new ArrayList<>();
        }
        boolean existe = false;
        for (Item i : listaItems) {
            if (Objects.equals(i.getIdCarro(), item.getIdCarro())) {
                existe = true;
                // No hay control de existencias en tabla 'carro', solo incrementar
                i.setCantidad(i.getCantidad() + 1);
                break;
            }
        }
        if (!existe) {
            item.setCantidad(1);
            listaItems.add(item);
        }
        session.setAttribute("listaItems", listaItems);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void update(Item item) {
        List<Item> listaItems = (List<Item>) session.getAttribute("listaItems");
        if (listaItems != null) {
            for (Item i : listaItems) {
                if (Objects.equals(i.getIdCarro(), item.getIdCarro())) {
                    i.setCantidad(item.getCantidad());
                    session.setAttribute("listaItems", listaItems);
                    break;
                }
            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void facturar() {
        // 1) Usuario autenticado
        String username = "";
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails userDetails) {
            username = userDetails.getUsername();
        } else if (principal != null) {
            username = principal.toString();
        }
        if (username.isBlank()) {
            System.out.println("username en blanco...");
            return;
        }

        Usuario usuario = usuarioDao.findByUsername(username);
        if (usuario == null) {
            System.out.println("Usuario no existe en usuarios...");
            return;
        }

        // 2) Crear factura
        Factura factura = new Factura(usuario.getIdUsuario());
        factura = facturaDao.save(factura);

        // 3) Registrar ventas (no hay existencias que actualizar en 'carro')
        List<Item> listaItems = (List<Item>) session.getAttribute("listaItems");
        if (listaItems != null && !listaItems.isEmpty()) {
            double total = 0.0;
            for (Item i : listaItems) {
                // Opcional: validar que el carro exista
                if (i.getIdCarro() != null && carroDao.existsById(i.getIdCarro())) {
                    Venta venta = new Venta(
                            factura.getIdFactura(),
                            i.getIdCarro(),
                            i.getPrecio() != null ? i.getPrecio().doubleValue() : 0.0,
                            i.getCantidad()
                    );
                    ventaDao.save(venta);
                    total += i.getCantidad() * (i.getPrecio() != null ? i.getPrecio().doubleValue() : 0.0);
                }
            }

            // 4) Actualizar total en la factura
            factura.setTotal(total);
            facturaDao.save(factura);

            // 5) Limpiar carrito
            listaItems.clear();
            session.setAttribute("listaItems", listaItems);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public double getTotal() {
        List<Item> listaItems = (List<Item>) session.getAttribute("listaItems");
        double total = 0.0;
        if (listaItems != null) {
            for (Item i : listaItems) {
                BigDecimal p = i.getPrecio(); // en Carro es BigDecimal
                total += i.getCantidad() * (p != null ? p.doubleValue() : 0.0);
            }
        }
        return total;
    }
}

