package com.example.cleanpedidos.adapter.in.web.dto;

import java.math.BigDecimal;

public record LineaPedidoDto(
        String productoNombre,
        int cantidad,
        BigDecimal precioUnitario
) {
    public LineaPedidoDto {
        if (productoNombre == null || productoNombre.isBlank()) {
            throw new IllegalArgumentException("Nombre de producto no puede ser vacío");
        }
        if (cantidad <= 0) {
            throw new IllegalArgumentException("Cantidad debe ser mayor a cero");
        }
        if (precioUnitario == null || precioUnitario.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Precio debe ser mayor a cero");
        }
    }
}
