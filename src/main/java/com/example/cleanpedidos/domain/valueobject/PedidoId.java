package com.example.cleanpedidos.domain.valueobject;

import java.util.Objects;
import java.util.UUID;

public record PedidoId(UUID valor) {
    public PedidoId {
        Objects.requireNonNull(valor, "PedidoId no puede ser nulo");
    }

    public static PedidoId nuevo() {
        return new PedidoId(UUID.randomUUID());
    }

    @Override
    public String toString() {
        return valor.toString();
    }
}
