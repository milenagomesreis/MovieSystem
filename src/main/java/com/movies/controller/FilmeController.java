package com.movies.controller;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.movies.model.Filme;
import com.movies.persistence.FilmeDAO;

@RestController
public class FilmeController {
	
	FilmeDAO filmeDAO;
	
	@GetMapping("/filmes")
	public ResponseEntity<ArrayList<Filme>>listarFilmes(){
		filmeDAO = new FilmeDAO();
		ArrayList<Filme> filmes = filmeDAO.listarFilmes();
		
		if(filmes.isEmpty()){
			
			return new ResponseEntity<ArrayList<Filme>>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<ArrayList<Filme>>(filmes, HttpStatus.OK);
	}
	
	@GetMapping("/filme/{id}")
	public ResponseEntity<Filme> getFilme(@PathVariable("id") int id){
		filmeDAO = new FilmeDAO();
		Filme filme = filmeDAO.getFilmePorId(id);
		
		if (filme == null){
			return new ResponseEntity<Filme>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Filme>(filme,HttpStatus.OK);
		
	}
	
	@PostMapping("/filme")
	public ResponseEntity<Filme> adicionaFilme(@RequestBody Filme filme){
		filmeDAO = new FilmeDAO();
		
		if(filmeDAO.hasFilme(filme)){
			return new ResponseEntity<Filme>(HttpStatus.CONFLICT);
		}
		
		filme = filmeDAO.incluir(filme);
		
		if(filme == null){
			return new ResponseEntity<Filme>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Filme>(filme, HttpStatus.OK);
		
	}
	
	@PutMapping("/filme/{id}")
	public ResponseEntity<Void> alteraFilme(@PathVariable("id") int id, @RequestBody Filme filme){
		filmeDAO = new FilmeDAO();
		
		if (!filmeDAO.alterar(filme)){
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@DeleteMapping("/filme/{id}")
	public ResponseEntity<Void> deletarFilme(@PathVariable("id") int id){
		filmeDAO = new FilmeDAO();
		
		if (!filmeDAO.excluir(id)){
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
