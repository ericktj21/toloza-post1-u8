package com.example.cleanpedidos.adapter.in.web;

import com.example.cleanpedidos.adapter.in.web.dto.CrearPedidoRequest;
import com.example.cleanpedidos.adapter.in.web.dto.PedidoResponse;
import com.example.cleanpedidos.domain.valueobject.PedidoId;
import com.example.cleanpedidos.usecase.ConsultarPedidoUseCase;
import com.example.cleanpedidos.usecase.CrearPedidoUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {
    private static final Logger logger = LoggerFactory.getLogger(PedidoController.class);
    
    private final CrearPedidoUseCase crearUseCase;
    private final ConsultarPedidoUseCase consultarUseCase;

    public PedidoController(CrearPedidoUseCase crearUseCase, ConsultarPedidoUseCase consultarUseCase) {
        this.crearUseCase = crearUseCase;
        this.consultarUseCase = consultarUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, String> crear(@RequestBody CrearPedidoRequest req) {
        logger.info("Recibiendo solicitud de crear pedido para cliente: {}", req.clienteNombre());
        PedidoId id = crearUseCase.ejecutar(req.clienteNombre(), req.lineas());
        return Map.of("pedidoId", id.toString());
    }

    @GetMapping("/{id}")
    public PedidoResponse buscar(@PathVariable String id) {
        logger.info("Buscando pedido con ID: {}", id);
        return consultarUseCase.buscarPorId(new PedidoId(UUID.fromString(id)));
    }

    @GetMapping
    public List<PedidoResponse> listar() {
        logger.info("Listando todos los pedidos");
        return consultarUseCase.listarTodos();
    }
}
