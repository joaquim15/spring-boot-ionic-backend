package br.com.cursoudemy.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import br.com.cursoudemy.domain.Pedido;

public class AbstractEmailService implements EmailService {

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

	@Override
	public void sendEmail(SimpleMailMessage msg) {
		// TODO Auto-generated method stub
		
	}
	

}
