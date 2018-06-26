package br.com.cursoudemy.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import br.com.cursoudemy.domain.PagamentoComBoleto;

@Service
public class BoletoService {

	public void preencherPagamentoComBoleto(PagamentoComBoleto pagto, Date instanteDoPedido) {

		// Crio uma instância do Calendar.
		Calendar calendar = Calendar.getInstance();

		// defino a data do calendario com a que veio por parametro.
		calendar.setTime(instanteDoPedido);

		// vamos acrecentar a esta data 7 dias para o pagamento.
		calendar.add(Calendar.DAY_OF_MONTH, 7);

		// e definimos a data de vencimento
		pagto.setDataVencimento(calendar.getTime());

	}

}
