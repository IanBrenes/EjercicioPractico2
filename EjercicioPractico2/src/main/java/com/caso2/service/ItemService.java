/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.caso2.service;

import com.caso2.domain.Item;
import java.util.List;

public interface ItemService {
    // Se usa para tener en una sesión de memoria la información del carrito de compras

    List<Item> gets();

    // Recupera el item por su identidad (según los datos dentro de Item)
    Item get(Item item);

    // Elimina el item indicado
    void delete(Item item);

    // Crea o actualiza el item (si ya existe, actualiza; si no, lo agrega)
    void save(Item item);

    // Actualiza cantidades/datos del item existente
    void update(Item item);

    // Genera la factura a partir del carrito actual
    void facturar();

    // Retorna el total del carrito
    double getTotal();
}

