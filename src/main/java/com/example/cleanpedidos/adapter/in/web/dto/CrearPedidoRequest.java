package com.example.cleanpedidos.adapter.in.web.dto;

import java.util.List;

public record CrearPedidoRequest(
        String clienteNombre,
        List<LineaPedidoDto> lineas
) {
    public CrearPedidoRequest {
        if (clienteNombre == null || clienteNombre.isBlank()) {
            throw new IllegalArgumentException("El cliente es obligatorio");
        }
        if (lineas == null || lineas.isEmpty()) {
            throw new IllegalArgumentException("Se requiere al menos una línea de pedido");
        }
    }
}
