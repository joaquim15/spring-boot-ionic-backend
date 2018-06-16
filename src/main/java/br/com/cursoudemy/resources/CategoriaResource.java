package br.com.cursoudemy.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cursoudemy.domain.Categoria;

import org.springframework.web.bind.annotation.RequestMethod;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {

	@RequestMapping(method = RequestMethod.GET)
	public List<Categoria> Listar() {

		Categoria cat1 = new Categoria(1L, "Informatica");
		Categoria cat2 = new Categoria(2L, "Escritório");

		List<Categoria> listCategorias = new ArrayList<>();

		listCategorias.add(cat1);
		listCategorias.add(cat2);

		return listCategorias;
	}

}
