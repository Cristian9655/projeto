package br.com.fiap.projeto.repository;


import br.com.fiap.projeto.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}