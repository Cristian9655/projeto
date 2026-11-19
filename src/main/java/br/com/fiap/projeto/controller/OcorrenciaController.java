package br.com.fiap.projeto.controller;

import br.com.fiap.projeto.entity.Ocorrencia;
import br.com.fiap.projeto.service.OcorrenciaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ocorrencias")
@RequiredArgsConstructor
public class OcorrenciaController {
    private final OcorrenciaService ocorrenciaService;

    @PostMapping
    public ResponseEntity<Ocorrencia> criar(@Valid @RequestBody Ocorrencia ocorrencia) {
        Ocorrencia novaOcorrencia = ocorrenciaService.criar(ocorrencia);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaOcorrencia);
    }

    @GetMapping
    public ResponseEntity<List<Ocorrencia>> listarTodos() {
        List<Ocorrencia> ocorrencias = ocorrenciaService.listarTodos();
        return ResponseEntity.ok(ocorrencias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ocorrencia> buscarPorId(@PathVariable Long id) {
        return ocorrenciaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ocorrencia> atualizar(@PathVariable Long id, @Valid @RequestBody Ocorrencia ocorrencia) {
        return ocorrenciaService.atualizar(id, ocorrencia)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (!ocorrenciaService.excluir(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
