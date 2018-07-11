package br.com.cursoudemy.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.cursoudemy.domain.Cliente;
import br.com.cursoudemy.repositories.ClienteRepository;
import br.com.cursoudemy.services.exceptions.ObjectNotFoundExeption;

@Service
public class AuthService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private BCryptPasswordEncoder pe;

	@Autowired
	private EmailService emailService;

	private Random random = new Random();

	public void sendNewPassword(String email) {

		Cliente cliente = clienteRepository.findByEmail(email);

		if (cliente == null) {
			throw new ObjectNotFoundExeption("E-mail não encontrado.");
		}

		String newPass = newPassword();

		cliente.setSenha(pe.encode(newPass));

		this.clienteRepository.save(cliente);
		this.emailService.sendNewPasswordEmail(cliente, newPass);
	}

	private String newPassword() {

		char[] vet = new char[10];
		for (int i = 0; i < 10; i++) {
			vet[i] = randomChar();
		}

		return new String(vet);
	}

	private char randomChar() {

		int opt = this.random.nextInt(3);

		if (opt == 0) { // gera um digito
			return (char) (this.random.nextInt(10) + 48);
		} else if (opt == 1) { // gero uma Letra Maiuscula
			return (char) (this.random.nextInt(26) + 65);
		} else { // gera letra minuscula
			return (char) (this.random.nextInt(26) + 97);
		}
	}
}
