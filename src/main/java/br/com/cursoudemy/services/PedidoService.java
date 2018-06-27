package br.com.cursoudemy.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cursoudemy.domain.ItemPedido;
import br.com.cursoudemy.domain.PagamentoComBoleto;
import br.com.cursoudemy.domain.Pedido;
import br.com.cursoudemy.domain.enums.EstadoPagamento;
import br.com.cursoudemy.repositories.ItemPedidoRepository;
import br.com.cursoudemy.repositories.PagamentoRepository;
import br.com.cursoudemy.repositories.PedidoRepository;
import br.com.cursoudemy.services.exceptions.ObjectNotFoundExeption;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repository;

	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ItemPedidoRepository pedidoRepository;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired 
	private EmailService emailService;

	public Pedido find(Integer id) {

		Optional<Pedido> obj = repository.findById(id);

		return obj.orElseThrow(() -> new ObjectNotFoundExeption(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}

	@Transactional
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstance(new Date());
		obj.setCliente(this.clienteService.find(obj.getCliente().getId()));
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);

		if (obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			this.boletoService.preencherPagamentoComBoleto(pagto, obj.getInstance());
		}

		obj = this.repository.save(obj);
		this.pagamentoRepository.save(obj.getPagamento());
		for (ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
			ip.setProduto(produtoService.find(ip.getProduto().getId()));
			ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(obj);
		}
		this.pedidoRepository.saveAll(obj.getItens());
		this.emailService.sendOrderConfirmationEmail(obj);
		return obj;
	}
}
