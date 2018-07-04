package br.com.cursoudemy.services;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;

import br.com.cursoudemy.domain.Pedido;

public class MockEmailService extends AbstractEmailService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MockEmailService.class);

	@Override
	public void sendEmail(SimpleMailMessage msg) {
		LOGGER.info("Simulando envio de email...");
		LOGGER.info(msg.toString());
		LOGGER.info("Email enviado.");

	}

	@Override
	public void sendOrderConfirmationHtmlEmail(Pedido obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendHtmlEmail(MimeMessage msg) {
		// TODO Auto-generated method stub
		
	}

}
