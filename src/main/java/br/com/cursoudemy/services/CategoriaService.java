package br.com.cursoudemy.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cursoudemy.domain.Categoria;
import br.com.cursoudemy.repositories.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repository;

	public Optional<Categoria> burcar(Integer id) {

		Optional<Categoria> obj = this.repository.findById(id);

		return obj;

	}
}
