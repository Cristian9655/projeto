package br.com.fiap.projeto.repository;

import br.com.fiap.projeto.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
