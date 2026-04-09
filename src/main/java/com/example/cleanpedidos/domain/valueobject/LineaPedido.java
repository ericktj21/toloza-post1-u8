package com.example.cleanpedidos.domain.valueobject;

import java.math.BigDecimal;

public record LineaPedido(String productoNombre, int cantidad, Dinero precioUnitario) {
    
    public LineaPedido {
        if (productoNombre == null || productoNombre.isBlank()) {
            throw new IllegalArgumentException("Nombre de producto no puede ser vacío");
        }
        if (cantidad <= 0) {
            throw new IllegalArgumentException("Cantidad debe ser mayor a cero");
        }
        if (precioUnitario == null) {
            throw new IllegalArgumentException("Precio unitario no puede ser nulo");
        }
    }

    public Dinero subtotal() {
        return new Dinero(precioUnitario.cantidad()
                .multiply(BigDecimal.valueOf(cantidad)));
    }
}
