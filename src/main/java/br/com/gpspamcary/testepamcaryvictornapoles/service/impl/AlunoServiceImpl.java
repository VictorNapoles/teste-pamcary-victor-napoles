package br.com.gpspamcary.testepamcaryvictornapoles.service.impl;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gpspamcary.testepamcaryvictornapoles.model.Aluno;
import br.com.gpspamcary.testepamcaryvictornapoles.repository.AlunoRepository;
import br.com.gpspamcary.testepamcaryvictornapoles.service.AlunoService;

@Service
public class AlunoServiceImpl implements AlunoService {

	@Autowired
	private AlunoRepository repository;

	@Override
	public Optional<List<Aluno>> obterTodos() {
		return Optional.ofNullable(repository.findAll());
	}

	@Override
	public Aluno inserir(Aluno aluno) {
		return repository.save(aluno);
	}

	@Override
	public Aluno alterar(Aluno aluno) {
		Optional<Aluno> optAluno = Optional.ofNullable(aluno);
		
		Long id = optAluno.map(Aluno::getId).orElseThrow(() -> new EntityNotFoundException("Favor informar o id do aluno"));
		
		if (!repository.existsById(id)) {
			throw new EntityNotFoundException("Aluno inexistente!");
		}
		
		return repository.save(aluno);
	}

	@Override
	public Optional<Aluno> obterPorId(Long id) {
		return repository.findById(id);
	}

	@Override
	public void remover(Long id) {
		repository.deleteById(id);

	}

}
