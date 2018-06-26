package br.com.cursoudemy.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cursoudemy.domain.Pedido;
import br.com.cursoudemy.repositories.PedidoRepository;
import br.com.cursoudemy.services.exceptions.ObjectNotFoundExeption;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repository;

	public Pedido find(Integer id) {

		Optional<Pedido> obj = repository.findById(id);

		return obj.orElseThrow(() -> new ObjectNotFoundExeption("Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}
}
