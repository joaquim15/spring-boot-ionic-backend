package br.com.cursoudemy.domain.enums;

public enum Perfil {

	ADMIN(1, "ROLE_ADMIN"), 
	CLIENTE(2, "ROLE_CLIENTE");

	private int cod;
	private String descriacao;

	public int getCod() {
		return cod;
	}

	public String getDescriacao() {
		return descriacao;
	}

	private Perfil(int cod, String descriacao) {
		this.cod = cod;
		this.descriacao = descriacao;
	}

	public static Perfil toEnum(Integer cod) {

		if (cod == null) {
			return null;
		}

		for (Perfil x : Perfil.values()) {
			if (cod.equals(x.cod)) {
				return x;
			}
		}

		throw new IllegalArgumentException("Id Inválido: " + cod);
	}
}
