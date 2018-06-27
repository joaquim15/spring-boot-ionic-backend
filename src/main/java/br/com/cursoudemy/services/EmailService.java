package br.com.cursoudemy.services;

import org.springframework.mail.SimpleMailMessage;

import br.com.cursoudemy.domain.Pedido;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido pedido);

	void sendEmail(SimpleMailMessage msg);
}
