package br.com.cursoudemy.domain;

public enum TipoCliente {

	PESSOAFISICA(1, "Pessoa Fisíca"), PESSOAJURIDICA(2, "Pessoa Jurídica");

	private int cod;
	private String descriacao;

	public int getCod() {
		return cod;
	}

	public String getDescriacao() {
		return descriacao;
	}

	private TipoCliente(int cod, String descriacao) {
		this.cod = cod;
		this.descriacao = descriacao;
	}

	public static TipoCliente toEnum(Integer cod) {

		if (cod == null) {
			return null;
		}

		for (TipoCliente x : TipoCliente.values()) {
			if (cod.equals(x.cod)) {
				return x;
			}
		}

		throw new IllegalArgumentException("Id Inválido: " + cod);
	}
}
