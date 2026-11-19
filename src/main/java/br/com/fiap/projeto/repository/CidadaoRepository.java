package br.com.fiap.projeto.repository;

import br.com.fiap.projeto.entity.Cidadao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CidadaoRepository extends JpaRepository<Cidadao, Long> {
}
