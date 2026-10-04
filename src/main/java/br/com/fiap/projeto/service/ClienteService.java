package br.com.fiap.projeto.service;

import br.com.fiap.projeto.entity.Cliente;
import br.com.fiap.projeto.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService {
    private final ClienteRepository clienteRepository;

    public Cliente criar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> buscarPorId(Long id) {
        return clienteRepository.findById(id);
    }

    public Optional<Cliente> atualizar(Long id, Cliente clienteAtualizado) {

        return clienteRepository.findById(id)
                .map(cliente -> {

                    cliente.setNome(clienteAtualizado.getNome());
                    cliente.setEmail(clienteAtualizado.getEmail());

                    return clienteRepository.save(cliente);
                });
    }

    public boolean excluir(Long id) {

        if (!clienteRepository.existsById(id)) {
            return false;
        }

        clienteRepository.deleteById(id);
        return true;
    }
}