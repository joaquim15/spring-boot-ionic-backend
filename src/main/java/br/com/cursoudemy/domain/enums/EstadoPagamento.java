package br.com.cursoudemy.domain.enums;

public enum EstadoPagamento {

	PENDENTE(1, "pendente"), 
	QUITADO(2, "quitado"), 
	CANCELADO(3, "cancelado");

	private int cod;
	private String descriacao;

	public int getCod() {
		return cod;
	}

	public String getDescriacao() {
		return descriacao;
	}

	private EstadoPagamento(int cod, String descriacao) {
		this.cod = cod;
		this.descriacao = descriacao;
	}

	public static EstadoPagamento toEnum(Integer cod) {

		if (cod == null) {
			return null;
		}

		for (EstadoPagamento x : EstadoPagamento.values()) {
			if (cod.equals(x.cod)) {
				return x;
			}
		}

		throw new IllegalArgumentException("Id Inválido: " + cod);
	}
}
