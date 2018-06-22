package br.com.cursoudemy.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cursoudemy.domain.Categoria;
import br.com.cursoudemy.repositories.CategoriaRepository;
import br.com.cursoudemy.services.exceptions.ObjectNotFoundExeption;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repository;

	public Categoria find(Integer id) {

		Optional<Categoria> obj = repository.findById(id);

		return obj.orElseThrow(() -> new ObjectNotFoundExeption(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}

	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return this.repository.save(obj);
	}

	public Categoria update(Categoria obj) {
		find(obj.getId());
		return this.repository.save(obj);
	}
}
