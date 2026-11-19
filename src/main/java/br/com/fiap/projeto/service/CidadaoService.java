package br.com.fiap.projeto.service;


import br.com.fiap.projeto.entity.Cidadao;
import br.com.fiap.projeto.repository.CidadaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CidadaoService {
    private final CidadaoRepository cidadaoRepository;

    public Cidadao criar(Cidadao cidadao) {
        return cidadaoRepository.save(cidadao);
    }

    public List<Cidadao> listarTodos() {
        return cidadaoRepository.findAll();
    }

    public Optional<Cidadao> buscarPorId(Long id) {
        return cidadaoRepository.findById(id);
    }

    public Optional<Cidadao> atualizar(Long id, Cidadao cidadaoAtualizado) {
        return cidadaoRepository.findById(id)
                .map(cidadao -> {
                    cidadao.setNome(cidadaoAtualizado.getNome());
                    cidadao.setEmail(cidadaoAtualizado.getEmail());
                    return cidadaoRepository.save(cidadao);
                });
    }

    public boolean excluir(Long id) {
        if (!cidadaoRepository.existsById(id)) {
            return false;
        }
        cidadaoRepository.deleteById(id);
        return true;
    }
}
