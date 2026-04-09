package com.example.cleanpedidos.adapter.in.web.dto;

import java.math.BigDecimal;
import java.util.List;

public record PedidoResponse(
        String id,
        String clienteNombre,
        String estado,
        List<LineaResponse> lineas,
        BigDecimal total
) {
    public record LineaResponse(
            String productoNombre,
            int cantidad,
            BigDecimal precioUnitario
    ) {}
}
