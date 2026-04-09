package com.example.cleanpedidos.usecase;

import com.example.cleanpedidos.adapter.in.web.dto.LineaPedidoDto;
import com.example.cleanpedidos.domain.valueobject.PedidoId;

import java.util.List;

public interface CrearPedidoUseCase {
    PedidoId ejecutar(String clienteNombre, List<LineaPedidoDto> lineas);
}
