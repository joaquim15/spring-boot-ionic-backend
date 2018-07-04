package br.com.cursoudemy.services;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class SmtpEmailService extends AbstractEmailService {

	@Autowired
	private MailSender mailSender;

	@Autowired
	private JavaMailSender javaMailSender;

	private static final Logger LOGGER = LoggerFactory.getLogger(SmtpEmailService.class);

	@Override
	public void sendEmail(SimpleMailMessage msg) {
		LOGGER.info("Enviando de e-mail...");
		mailSender.send(msg);
		LOGGER.info("E-mail enviado.");
	}

	@Override
	public void sendHtmlEmail(MimeMessage msg) {

		LOGGER.info("Enviando de e-mail...");
		javaMailSender.send(msg);
		LOGGER.info("E-mail enviado.");

	}
}
