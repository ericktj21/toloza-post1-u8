package com.example.cleanpedidos.usecase.port;

import com.example.cleanpedidos.domain.entity.Pedido;
import com.example.cleanpedidos.domain.valueobject.PedidoId;

import java.util.List;
import java.util.Optional;

public interface PedidoRepositoryPort {
    void guardar(Pedido pedido);
    Optional<Pedido> buscarPorId(PedidoId id);
    List<Pedido> buscarTodos();
}
