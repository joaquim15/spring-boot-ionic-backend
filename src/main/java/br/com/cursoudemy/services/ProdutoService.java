package br.com.cursoudemy.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.cursoudemy.domain.Categoria;
import br.com.cursoudemy.domain.Produto;
import br.com.cursoudemy.repositories.CategoriaRepository;
import br.com.cursoudemy.repositories.ProdutoRepository;
import br.com.cursoudemy.services.exceptions.ObjectNotFoundExeption;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository repository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;

	public Produto find(Integer id) {

		Optional<Produto> obj = this.repository.findById(id);

		return obj.orElseThrow(() -> new ObjectNotFoundExeption("Objeto não encontrado! Id: " + id + ", Tipo: " + Produto.class.getName()));
	}
	
	
	public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPorPage, String orderBy, String direction) {

		PageRequest pageRequest = new PageRequest(page, linesPorPage, Direction.valueOf(direction), orderBy);
		List<Categoria> categorias = this.categoriaRepository.findAllById(ids);
		return this.repository.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
	}
}
