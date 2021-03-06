package br.com.cursoudemy.services;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import br.com.cursoudemy.domain.Cidade;
import br.com.cursoudemy.domain.Cliente;
import br.com.cursoudemy.domain.Endereco;
import br.com.cursoudemy.domain.enums.Perfil;
import br.com.cursoudemy.domain.enums.TipoCliente;
import br.com.cursoudemy.dto.ClienteDTO;
import br.com.cursoudemy.dto.ClienteNewDTO;
import br.com.cursoudemy.repositories.ClienteRepository;
import br.com.cursoudemy.repositories.EnderecoRepository;
import br.com.cursoudemy.security.UserSS;
import br.com.cursoudemy.services.exceptions.AuthorizationExeception;
import br.com.cursoudemy.services.exceptions.DataIntegrityExeception;
import br.com.cursoudemy.services.exceptions.ObjectNotFoundExeption;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repository;

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private BCryptPasswordEncoder pe;

	@Autowired
	private S3Service s3Service;

	@Autowired
	private ImageService imageService;

	@Value("${img.prefix.client.profile}")
	private String prefix;

	@Value("${img.profile.size}")
	private Integer size;

	public Cliente find(Integer id) {

		Optional<Cliente> obj = repository.findById(id);

		UserSS user = UserService.authenticated();

		if (user == null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationServiceException("Acesso negado.");
		}

		return obj.orElseThrow(() -> new ObjectNotFoundExeption(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}

	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = repository.save(obj);
		this.enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
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
			throw new DataIntegrityExeception("Não é possivél excluir por que há pedidos relacionadas.");
		}
	}

	public List<Cliente> findAll() {
		// TODO Auto-generated method stub
		return this.repository.findAll();
	}

	public Cliente findByEmail(String email) {

		UserSS user = UserService.authenticated();
		
		if (user == null || !user.hasRole(Perfil.ADMIN) && !email.equals(user.getUsername())) {
			throw new AuthorizationExeception("Acesso Negado.");
		}

		Cliente obj = repository.findByEmail(email);
		
		if (obj == null) {
			throw new ObjectNotFoundExeption(
					"Objeto não encontrado! Id: " + user.getId() + ", Tipo: " + Cliente.class.getName());
		}

		return obj;
	}

	public Page<Cliente> findPage(Integer page, Integer linesPorPage, String orderBy, String direction) {

		PageRequest pageRequest = PageRequest.of(page, linesPorPage, Direction.valueOf(direction), orderBy);

		return repository.findAll(pageRequest);

	}

	public Cliente fromDTO(ClienteDTO objDto) {
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null, null);
	}

	public Cliente fromDTO(ClienteNewDTO objDto) {

		Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOrCnpj(),
				TipoCliente.toEnum(objDto.getTipoCliente()), pe.encode(objDto.getSenha()));
		Cidade cid = new Cidade(objDto.getCidadeId(), null, null);
		Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getLogradouro(),
				objDto.getBairro(), objDto.getCep(), cli, cid);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDto.getTelefone1());

		if (objDto.getTelefone2() != null) {
			cli.getTelefones().add(objDto.getTelefone2());
		}

		if (objDto.getTelefone2() != null) {
			cli.getTelefones().add(objDto.getTelefone3());
		}

		return cli;

	}

	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}

	public URI uploadProfilePicture(MultipartFile objMult) {

		UserSS user = UserService.authenticated();

		if (user == null) {
			throw new AuthorizationExeception("Acesso | Negado");
		}

		BufferedImage jpgImage = imageService.getJpgImageFromFile(objMult);
		jpgImage = imageService.cropSquare(jpgImage);
		jpgImage = imageService.resize(jpgImage, size);

		String fileName = prefix + user.getId() + ".jpg";

		return s3Service.uploafFile(imageService.getInputStream(jpgImage, "jpg"), fileName, "image");

	}
}
