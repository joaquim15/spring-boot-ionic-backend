package br.com.cursoudemy.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.ec2.model.Address;

import br.com.cursoudemy.domain.Pedido;

@Service
public class ServiceCheckoutTransparente {/*

	private Item item;

	private Checkout checkout = new Checkout();

	private Sender cliente = new Sender();

	private Phone telefone = new Phone();

	private SenderDocument documento = new SenderDocument();

	private Address endereco;

	private Shipping shipping = new Shipping();

	private String checkoutURL;

	@Autowired
	private ProdutoService produtoService;

	public String CheckoutTransparente(Pedido obj) {

		for (ItemPedido ip : obj.getItens()) {

			ip.setProduto(this.produtoService.find(ip.getProduto().getId()));

			this.item = new Item();
			this.item.setId(String.valueOf(ip.getProduto().getId()));
			this.item.setDescription(ip.getProduto().getNome());
			this.item.setQuantity(ip.getQuantidade());
			this.item.setAmount(BigDecimal.valueOf(ip.getProduto().getPreco()).setScale(2));
			this.checkout.addItem(item);

		}

		this.cliente.setName(obj.getCliente().getNome());
		this.cliente.setEmail(obj.getCliente().getEmail());

		for (String tel : obj.getCliente().getTelefones()) {
			this.telefone = new Phone();
			this.telefone.setFullPhone(tel);
		}

		this.documento.setValue(obj.getCliente().getCpfOrCnpj());
		this.cliente.addDocument(this.documento);
		this.checkout.setSender(this.cliente);

		for (Endereco end : obj.getCliente().getEnderecos()) {

			this.shipping = new Shipping();
			this.endereco = new Address();

			this.endereco.setStreet(end.getLogradouro());
			this.endereco.setNumber(end.getNumero());
			this.endereco.setComplement(end.getComplemento());
			this.endereco.setDistrict(end.getBairro());
			this.endereco.setCity(end.getCidade().getNome());
			this.endereco.setState("SP");
			this.endereco.setCountry("BRA");
			this.endereco.setPostalCode(end.getCep());
			this.shipping.setAddress(this.endereco);
			this.checkout.setShippingAddress(this.endereco);
		}
		this.checkout.setShippingType(ShippingType.NOT_SPECIFIED);

		this.checkout.setCurrency(Currency.BRL);

		try {

			Boolean onlyCheckoutCode = false;

			this.checkoutURL = this.checkout.register(PagSeguroConfig.getAccountCredentials(), onlyCheckoutCode);

			System.out.println(this.checkoutURL);

			this.checkout = new Checkout();

		} catch (PagSeguroServiceException e) {
			this.checkout = new Checkout();
			System.err.println(e.getMessage());
		}
		return this.checkoutURL;
	}
*/}