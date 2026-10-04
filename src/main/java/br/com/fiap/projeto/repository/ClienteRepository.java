package br.com.fiap.projeto.repository;


import br.com.fiap.projeto.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}