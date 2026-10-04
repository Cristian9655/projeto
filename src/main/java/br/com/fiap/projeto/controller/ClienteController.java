package br.com.fiap.projeto.controller;


import br.com.fiap.projeto.entity.Cliente;
import br.com.fiap.projeto.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {
    private final ClienteService clienteService;

    @PostMapping
    public ResponseEntity<Cliente> criar(
            @Valid @RequestBody Cliente cliente) {

        Cliente novoCliente = clienteService.criar(cliente);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(novoCliente);
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> listarTodos() {

        List<Cliente> clientes = clienteService.listarTodos();

        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarPorId(
            @PathVariable Long id) {

        return clienteService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody Cliente cliente) {

        return clienteService.atualizar(id, cliente)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @PathVariable Long id) {

        if (!clienteService.excluir(id)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}