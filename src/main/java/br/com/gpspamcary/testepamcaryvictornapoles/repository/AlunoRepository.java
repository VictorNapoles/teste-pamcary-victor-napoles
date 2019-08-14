package br.com.gpspamcary.testepamcaryvictornapoles.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gpspamcary.testepamcaryvictornapoles.model.Aluno;

public interface AlunoRepository extends JpaRepository<Aluno,Long> {}