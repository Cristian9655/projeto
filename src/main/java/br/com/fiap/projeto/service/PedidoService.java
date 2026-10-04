package br.com.fiap.projeto.service;

import br.com.fiap.projeto.entity.Cliente;
import br.com.fiap.projeto.entity.Pedido;
import br.com.fiap.projeto.repository.ClienteRepository;
import br.com.fiap.projeto.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PedidoService {
    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;

    public Pedido criar(Pedido pedido) {

        Long idCliente = pedido.getCliente().getIdCliente();

        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() ->
                        new RuntimeException("Cliente não encontrado"));

        pedido.setCliente(cliente);

        return pedidoRepository.save(pedido);
    }

    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    public Optional<Pedido> buscarPorId(Long id) {
        return pedidoRepository.findById(id);
    }

    public Optional<Pedido> atualizar(Long id, Pedido pedidoAtualizado) {

        return pedidoRepository.findById(id)
                .map(pedido -> {

                    Long idCliente = pedidoAtualizado
                            .getCliente()
                            .getIdCliente();

                    Cliente cliente = clienteRepository.findById(idCliente)
                            .orElseThrow(() ->
                                    new RuntimeException("Cliente não encontrado"));

                    pedido.setDataPedido(pedidoAtualizado.getDataPedido());
                    pedido.setValorTotal(pedidoAtualizado.getValorTotal());
                    pedido.setCliente(cliente);

                    return pedidoRepository.save(pedido);
                });
    }

    public boolean excluir(Long id) {

        if (!pedidoRepository.existsById(id)) {
            return false;
        }

        pedidoRepository.deleteById(id);

        return true;
    }

}