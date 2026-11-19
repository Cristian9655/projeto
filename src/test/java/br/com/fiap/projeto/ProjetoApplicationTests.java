package br.com.fiap.projeto;

import br.com.fiap.projeto.entity.Categoria;
import br.com.fiap.projeto.entity.Cidadao;
import br.com.fiap.projeto.entity.Ocorrencia;
import br.com.fiap.projeto.service.CategoriaService;
import br.com.fiap.projeto.service.CidadaoService;
import br.com.fiap.projeto.service.OcorrenciaService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProjetoApplicationTests {

	@Autowired
	private CidadaoService cidadaoService;

	@Autowired
	private CategoriaService categoriaService;

	@Autowired
	private OcorrenciaService ocorrenciaService;

	@Test
	void contextLoads() {
		assertNotNull(cidadaoService);
		assertNotNull(categoriaService);
		assertNotNull(ocorrenciaService);
	}

	@Test
	void deveRealizarFluxoCompletoDeZeladoria() {
		// 1. Criar Cidadão e Categoria
		Cidadao cidadao = new Cidadao(null, "Carlos Souza", "carlos@email.com");
		Cidadao cidadaoSalvo = cidadaoService.criar(cidadao);
		assertNotNull(cidadaoSalvo.getIdCidadao());

		Categoria categoria = new Categoria(null, "Iluminação", "Poste apagado");
		Categoria categoriaSalva = categoriaService.criar(categoria);
		assertNotNull(categoriaSalva.getIdCategoria());

		// 2. Criar Ocorrência vinculada
		Ocorrencia ocorrencia = new Ocorrencia(
				null,
				"Poste sem luz na Rua A",
				"Lâmpada queimada há dois dias",
				cidadaoSalvo,
				categoriaSalva
		);
		Ocorrencia ocorrenciaSalva = ocorrenciaService.criar(ocorrencia);
		assertNotNull(ocorrenciaSalva.getIdOcorrencia());

		// 3. Listar Ocorrências
		List<Ocorrencia> lista = ocorrenciaService.listarTodos();
		assertFalse(lista.isEmpty());

		// 4. Atualizar Ocorrência
		ocorrenciaSalva.setTitulo("Poste sem luz - Urgente");
		Optional<Ocorrencia> atualizada = ocorrenciaService.atualizar(
				ocorrenciaSalva.getIdOcorrencia(),
				ocorrenciaSalva
		);
		assertTrue(atualizada.isPresent());
		assertEquals("Poste sem luz - Urgente", atualizada.get().getTitulo());

		// 5. Excluir registros (respeitando a ordem das chaves estrangeiras)
		assertTrue(ocorrenciaService.excluir(ocorrenciaSalva.getIdOcorrencia()));
		assertTrue(cidadaoService.excluir(cidadaoSalvo.getIdCidadao()));
		assertTrue(categoriaService.excluir(categoriaSalva.getIdCategoria()));
	}

}
