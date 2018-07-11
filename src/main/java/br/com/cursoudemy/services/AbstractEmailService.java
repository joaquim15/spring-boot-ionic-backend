package br.com.cursoudemy.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.thymeleaf.context.Context;

import br.com.cursoudemy.domain.Cliente;
import br.com.cursoudemy.domain.Pedido;

public abstract class AbstractEmailService implements EmailService {

	@Value("${default.sender}")
	private String sender;

	@Override
	public void sendOrderConfirmationEmail(Pedido obj) {

		SimpleMailMessage sm = prepareSimpleMailMessageFromPedido(obj);
		sendEmail(sm);
	}

	protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido obj) {

		SimpleMailMessage sm = new SimpleMailMessage();
		// Configuração destinatario.
		sm.setTo(obj.getCliente().getEmail());
		// Configuração rementente
		sm.setFrom(this.sender);
		// Configuração Assunto do Email.
		sm.setSubject("Pedido Confirmado! Codigo: " + obj.getId());
		// Configuração de data
		sm.setSentDate(new Date(System.currentTimeMillis()));
		// Configuração corpo do Email.
		sm.setText(obj.toString());

		return sm;
	}

	protected String htmlFromTemplatePedido(Pedido obj) {

		Context context = new Context();
		context.setVariable("pedido", obj);
		return sender;

	}

	@Override
	public void sendNewPasswordEmail(Cliente cliente, String newPass) {

		SimpleMailMessage sm = prepareNewPasswordEmail(cliente, newPass);
		sendEmail(sm);

	}

	protected SimpleMailMessage prepareNewPasswordEmail(Cliente cliente, String newPass) {

		SimpleMailMessage sm = new SimpleMailMessage();
		// Configuração destinatario.
		sm.setTo(cliente.getEmail());
		// Configuração rementente
		sm.setFrom(this.sender);
		// Configuração Assunto do Email.
		sm.setSubject("Solitação de nova Senha");
		// Configuração de data
		sm.setSentDate(new Date(System.currentTimeMillis()));
		// Configuração corpo do Email.
		sm.setText("Nova senha: " + newPass);

		return sm;
	}

}
