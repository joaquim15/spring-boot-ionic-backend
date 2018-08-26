package br.com.cursoudemy.services;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cursoudemy.domain.Cliente;
import br.com.cursoudemy.domain.ItemPedido;
import br.com.cursoudemy.domain.PagamentoComBoleto;
import br.com.cursoudemy.domain.Pedido;
import br.com.cursoudemy.domain.enums.EstadoPagamento;
import br.com.cursoudemy.repositories.ItemPedidoRepository;
import br.com.cursoudemy.repositories.PagamentoRepository;
import br.com.cursoudemy.repositories.PedidoRepository;
import br.com.cursoudemy.security.UserSS;
import br.com.cursoudemy.services.exceptions.AuthorizationExeception;
import br.com.cursoudemy.services.exceptions.ObjectNotFoundExeption;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repo;

	@Autowired
	private BoletoService boletoService;

	@Autowired
	private PagamentoRepository pagamentoRepository;

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private EmailService emailService;

	@Autowired
	private ServiceCheckoutTransparente checkoutTransparente;

	private URL url;

	public Pedido find(Integer id) {

		Optional<Pedido> obj = this.repo.findById(id);

		return obj.orElseThrow(() -> new ObjectNotFoundExeption(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}

	@Transactional
	public Pedido insert(Pedido obj) throws IOException {

		String URL_CHEKOUT = null;

		obj.setId(null);
		obj.setInstance(new Date());
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.setCliente(this.clienteService.find(obj.getCliente().getId()));
		obj.getPagamento().setPedido(obj);

		if (obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			this.boletoService.preencherPagamentoComBoleto(pagto, obj.getInstance());
		} else {
			//URL_CHEKOUT = this.checkoutTransparente.CheckoutTransparente(obj);
		}

		obj = this.repo.save(obj);
		this.pagamentoRepository.save(obj.getPagamento());
		for (ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
			ip.setProduto(this.produtoService.find(ip.getProduto().getId()));
			ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(obj);
		}
		this.itemPedidoRepository.saveAll(obj.getItens());

		this.emailService.sendOrderConfirmationHtmlEmail(obj);

		try {
			this.url = new URL(URL_CHEKOUT);
			Desktop.getDesktop().browse(url.toURI());
		} catch (

		URISyntaxException e) {
			e.printStackTrace();
		}

		return obj;
	}
	
	public Page<Pedido> findPage(Integer page, Integer linesPorPage, String orderBy, String direction) {
		
		UserSS user = UserService.authenticated();
		
		if(user == null ) {
			throw new AuthorizationExeception("Acesso negado");
		}

		PageRequest pageRequest = PageRequest.of(page, linesPorPage, Direction.valueOf(direction), orderBy);
		Cliente cliente = this.clienteService.find(user.getId());
		return this.repo.findByCliente(cliente, pageRequest);
	}
}
