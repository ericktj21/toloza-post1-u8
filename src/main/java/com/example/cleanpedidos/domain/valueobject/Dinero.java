package com.example.cleanpedidos.domain.valueobject;

import java.math.BigDecimal;

public record Dinero(BigDecimal cantidad) {
    public static final Dinero CERO = new Dinero(BigDecimal.ZERO);

    public Dinero {
        if (cantidad.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Dinero no puede ser negativo");
        }
    }

    public Dinero sumar(Dinero otro) {
        return new Dinero(this.cantidad.add(otro.cantidad));
    }

    @Override
    public String toString() {
        return cantidad.toString();
    }
}
