package br.com.cursoudemy.services;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import br.com.cursoudemy.domain.Pedido;

public abstract class AbstractEmailService implements EmailService {

	@Value("${default.sender}")
	private String sender;

	@Autowired
	private TemplateEngine templateEngine;

	@Autowired
	private JavaMailSender javaMailSender;

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
		// Apelido usado para mapear o na tela html no caso aqui "pedido".
		context.setVariable("pedido", obj);
		// para realizar o processamento dos dados do html.
		return this.templateEngine.process("email/confirmacaoPedido", context);

	}

	@Override
	public void sendOrderConfirmationHtmlEmail(Pedido obj) {
		try {
			MimeMessage mm = prepareMimeMessageFromPedido(obj);
			sendHtmlEmail(mm);
		} catch (MessagingException e) {
			sendOrderConfirmationEmail(obj);
		}
	}

	protected MimeMessage prepareMimeMessageFromPedido(Pedido obj) throws MessagingException {

		MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
		MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true);
		// E-mail do Cliente Associado ao Pedido.
		mmh.setTo(obj.getCliente().getEmail());
		// Remetente do Email
		mmh.setFrom(this.sender);
		// Assunto do Email.
		mmh.setSubject("Pedido confirmado! Código: " + obj.getId());
		// Instante do Pedido
		mmh.setSentDate(new Date(System.currentTimeMillis()));
		// Corpo do email
		mmh.setText(htmlFromTemplatePedido(obj), true);

		return mimeMessage;
	}

}
