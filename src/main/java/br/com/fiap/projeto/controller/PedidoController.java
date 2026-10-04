package br.com.fiap.projeto.controller;

import br.com.fiap.projeto.entity.Pedido;
import br.com.fiap.projeto.service.PedidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {
    private final PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<Pedido> criar(
            @Valid @RequestBody Pedido pedido) {

        Pedido novoPedido = pedidoService.criar(pedido);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(novoPedido);
    }

    @GetMapping
    public ResponseEntity<List<Pedido>> listarTodos() {

        List<Pedido> pedidos = pedidoService.listarTodos();

        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPorId(
            @PathVariable Long id) {

        return pedidoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pedido> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody Pedido pedido) {

        return pedidoService.atualizar(id, pedido)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @PathVariable Long id) {

        if (!pedidoService.excluir(id)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

}