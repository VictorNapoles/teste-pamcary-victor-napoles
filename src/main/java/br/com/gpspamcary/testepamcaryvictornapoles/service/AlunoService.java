package br.com.gpspamcary.testepamcaryvictornapoles.service;

import java.util.List;
import java.util.Optional;

import br.com.gpspamcary.testepamcaryvictornapoles.model.Aluno;

public interface AlunoService {
	
	Optional<List<Aluno>> obterTodos();
	
	Optional<Aluno> obterPorId(Long id);
	
	Aluno inserir(Aluno cliente);
	
	Aluno alterar(Aluno cliente);
	
	void remover(Long id);

}
