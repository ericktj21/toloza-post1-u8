package com.example.cleanpedidos.usecase;

import com.example.cleanpedidos.adapter.in.web.dto.PedidoResponse;
import com.example.cleanpedidos.domain.valueobject.PedidoId;

import java.util.List;

public interface ConsultarPedidoUseCase {
    PedidoResponse buscarPorId(PedidoId id);
    List<PedidoResponse> listarTodos();
}
