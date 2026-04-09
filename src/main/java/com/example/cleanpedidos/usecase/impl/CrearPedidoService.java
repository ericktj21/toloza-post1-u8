package com.example.cleanpedidos.usecase.impl;

import com.example.cleanpedidos.adapter.in.web.dto.LineaPedidoDto;
import com.example.cleanpedidos.domain.entity.Pedido;
import com.example.cleanpedidos.domain.valueobject.Dinero;
import com.example.cleanpedidos.domain.valueobject.PedidoId;
import com.example.cleanpedidos.usecase.CrearPedidoUseCase;
import com.example.cleanpedidos.usecase.port.PedidoRepositoryPort;

import java.util.List;

public class CrearPedidoService implements CrearPedidoUseCase {
    private final PedidoRepositoryPort repo;

    public CrearPedidoService(PedidoRepositoryPort repo) {
        this.repo = repo;
    }

    @Override
    public PedidoId ejecutar(String clienteNombre, List<LineaPedidoDto> lineas) {
        Pedido pedido = new Pedido(PedidoId.nuevo(), clienteNombre);
        lineas.forEach(l -> pedido.agregarLinea(
                l.productoNombre(),
                l.cantidad(),
                new Dinero(l.precioUnitario())
        ));
        pedido.confirmar();
        repo.guardar(pedido);
        return pedido.getId();
    }
}
