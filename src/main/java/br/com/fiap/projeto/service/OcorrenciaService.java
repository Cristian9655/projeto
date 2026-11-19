package br.com.fiap.projeto.service;


import br.com.fiap.projeto.entity.Categoria;
import br.com.fiap.projeto.entity.Cidadao;
import br.com.fiap.projeto.entity.Ocorrencia;
import br.com.fiap.projeto.repository.CategoriaRepository;
import br.com.fiap.projeto.repository.CidadaoRepository;
import br.com.fiap.projeto.repository.OcorrenciaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OcorrenciaService {
    private final OcorrenciaRepository ocorrenciaRepository;
    private final CidadaoRepository cidadaoRepository;
    private final CategoriaRepository categoriaRepository;

    public Ocorrencia criar(Ocorrencia ocorrencia) {
        Long idCidadao = ocorrencia.getCidadao().getIdCidadao();
        Long idCategoria = ocorrencia.getCategoria().getIdCategoria();

        Cidadao cidadao = cidadaoRepository.findById(idCidadao)
                .orElseThrow(() -> new RuntimeException("Cidadão não encontrado"));

        Categoria categoria = categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        ocorrencia.setCidadao(cidadao);
        ocorrencia.setCategoria(categoria);

        return ocorrenciaRepository.save(ocorrencia);
    }

    public List<Ocorrencia> listarTodos() {
        return ocorrenciaRepository.findAll();
    }

    public Optional<Ocorrencia> buscarPorId(Long id) {
        return ocorrenciaRepository.findById(id);
    }

    public Optional<Ocorrencia> atualizar(Long id, Ocorrencia ocorrenciaAtualizada) {
        return ocorrenciaRepository.findById(id)
                .map(ocorrencia -> {
                    Long idCidadao = ocorrenciaAtualizada.getCidadao().getIdCidadao();
                    Long idCategoria = ocorrenciaAtualizada.getCategoria().getIdCategoria();

                    Cidadao cidadao = cidadaoRepository.findById(idCidadao)
                            .orElseThrow(() -> new RuntimeException("Cidadão não encontrado"));

                    Categoria categoria = categoriaRepository.findById(idCategoria)
                            .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

                    ocorrencia.setTitulo(ocorrenciaAtualizada.getTitulo());
                    ocorrencia.setDescricao(ocorrenciaAtualizada.getDescricao());
                    ocorrencia.setCidadao(cidadao);
                    ocorrencia.setCategoria(categoria);

                    return ocorrenciaRepository.save(ocorrencia);
                });
    }

    public boolean excluir(Long id) {
        if (!ocorrenciaRepository.existsById(id)) {
            return false;
        }
        ocorrenciaRepository.deleteById(id);
        return true;
    }
}
