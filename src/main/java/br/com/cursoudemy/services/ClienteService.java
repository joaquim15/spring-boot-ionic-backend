package br.com.cursoudemy.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.cursoudemy.domain.Cliente;
import br.com.cursoudemy.dto.ClienteDTO;
import br.com.cursoudemy.repositories.ClienteRepository;
import br.com.cursoudemy.services.exceptions.DataIntegrityExeception;
import br.com.cursoudemy.services.exceptions.ObjectNotFoundExeption;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repository;

	public Cliente find(Integer id) {

		Optional<Cliente> obj = repository.findById(id);

		return obj.orElseThrow(() -> new ObjectNotFoundExeption(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}

	public Cliente update(Cliente obj) {

		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		return this.repository.save(newObj);
	}

	public void delete(Integer id) {

		find(id);

		try {

			this.repository.deleteById(id);

		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityExeception("Não possivél excluir por que há entidades relacionadas.");
		}
	}

	public List<Cliente> findAll() {
		// TODO Auto-generated method stub
		return this.repository.findAll();
	}

	@SuppressWarnings("deprecation")
	public Page<Cliente> findPage(Integer page, Integer linesPorPage, String orderBy, String direction) {

		PageRequest pageRequest = new PageRequest(page, linesPorPage, Direction.valueOf(direction), orderBy);

		return repository.findAll(pageRequest);

	}

	public Cliente fromDTO(ClienteDTO objDto) {
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null);
	}

	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
}
