package br.com.fiap.projeto.controller;

import br.com.fiap.projeto.entity.Cidadao;
import br.com.fiap.projeto.service.CidadaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cidadaos")
@RequiredArgsConstructor
public class CidadaoController {
    private final CidadaoService cidadaoService;

    @PostMapping
    public ResponseEntity<Cidadao> criar(@Valid @RequestBody Cidadao cidadao) {
        Cidadao novoCidadao = cidadaoService.criar(cidadao);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoCidadao);
    }

    @GetMapping
    public ResponseEntity<List<Cidadao>> listarTodos() {
        List<Cidadao> cidadaos = cidadaoService.listarTodos();
        return ResponseEntity.ok(cidadaos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cidadao> buscarPorId(@PathVariable Long id) {
        return cidadaoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cidadao> atualizar(@PathVariable Long id, @Valid @RequestBody Cidadao cidadao) {
        return cidadaoService.atualizar(id, cidadao)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (!cidadaoService.excluir(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
