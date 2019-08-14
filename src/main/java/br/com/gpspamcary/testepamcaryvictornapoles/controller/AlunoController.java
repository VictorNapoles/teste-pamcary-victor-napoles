package br.com.gpspamcary.testepamcaryvictornapoles.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.gpspamcary.testepamcaryvictornapoles.model.Aluno;
import br.com.gpspamcary.testepamcaryvictornapoles.service.AlunoService;

public class AlunoController {
	
	@Autowired
	private AlunoService service;
	
	@SuppressWarnings("rawtypes")
	@GetMapping
	public ResponseEntity obterTodos() {
		
		ResponseEntity<List<Aluno>> noContent = ResponseEntity.noContent().build();
		return service.obterTodos().map((l)-> {
			
			if(l.isEmpty())
				return noContent;
			
			return ResponseEntity.ok().body(l);
		}).orElse(noContent);
	}
	
	@SuppressWarnings("rawtypes")
	@GetMapping("{id}")
	public ResponseEntity obterPorId(@PathVariable("id")Long id) {
		
		return service.obterPorId(id).map((c)-> {
			return ResponseEntity.ok().body(c);
		}).orElse(ResponseEntity.noContent().build());
	}
	
	@SuppressWarnings("rawtypes")
	@PostMapping
	public ResponseEntity inserir(@RequestBody Aluno aluno) {
		
		try {
			aluno = service.inserir(aluno);
			return new ResponseEntity<Aluno>(aluno, HttpStatus.CREATED);

		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);

		}
	}
	
	@SuppressWarnings("rawtypes")
	@PutMapping
	public ResponseEntity alterar(@RequestBody Aluno aluno) {
		
		try {
			aluno = service.alterar(aluno);
			return new ResponseEntity<Aluno>(aluno, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);

		}
	}
	
	@SuppressWarnings("rawtypes")
	@DeleteMapping("{id}")
	public ResponseEntity remover(@PathVariable("id")Long id) {
		
		try {
			service.remover(id);
			return new ResponseEntity<Aluno>(HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);

		}
	}

}
