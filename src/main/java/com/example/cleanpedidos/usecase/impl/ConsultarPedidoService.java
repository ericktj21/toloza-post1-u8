package com.example.cleanpedidos.usecase.impl;

import com.example.cleanpedidos.adapter.in.web.dto.PedidoResponse;
import com.example.cleanpedidos.domain.entity.Pedido;
import com.example.cleanpedidos.domain.valueobject.PedidoId;
import com.example.cleanpedidos.usecase.ConsultarPedidoUseCase;
import com.example.cleanpedidos.usecase.port.PedidoRepositoryPort;

import java.util.List;

public class ConsultarPedidoService implements ConsultarPedidoUseCase {
    private final PedidoRepositoryPort repo;

    public ConsultarPedidoService(PedidoRepositoryPort repo) {
        this.repo = repo;
    }

    @Override
    public PedidoResponse buscarPorId(PedidoId id) {
        Pedido pedido = repo.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Pedido no encontrado: " + id));
        return toDtoResponse(pedido);
    }

    @Override
    public List<PedidoResponse> listarTodos() {
        return repo.buscarTodos().stream()
                .map(this::toDtoResponse)
                .toList();
    }

    private PedidoResponse toDtoResponse(Pedido p) {
        return new PedidoResponse(
                p.getId().toString(),
                p.getClienteNombre(),
                p.getEstado().name(),
                p.getLineas().stream()
                        .map(l -> new PedidoResponse.LineaResponse(
                                l.productoNombre(),
                                l.cantidad(),
                                l.precioUnitario().cantidad()
                        ))
                        .toList(),
                p.calcularTotal().cantidad()
        );
    }
}
