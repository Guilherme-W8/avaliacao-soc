package br.com.soc.sistema.action;

import java.util.ArrayList;
import java.util.List;

import br.com.soc.sistema.business.CompromissoBusiness;
import br.com.soc.sistema.filter.CompromissoFilter;
import br.com.soc.sistema.helper.FunctionsHelper;
import br.com.soc.sistema.infra.Action;
import br.com.soc.sistema.vo.CompromissoVo;

@SuppressWarnings("serial")
public class RelatorioCompromissoAction extends Action {
	private List<CompromissoVo> compromissosList = new ArrayList<>();
	private CompromissoFilter filter = new CompromissoFilter();
	private final String proximaAction = "filtrarRelatorios";

	public String filtrar() {
		return INPUT;
	}

	public String gerarHTML() {
		if (!validarPedidoDeProcessamento()) {
			return INPUT;
		}
		
		try {
			CompromissoBusiness compromissoBusiness = new CompromissoBusiness();
			compromissosList = compromissoBusiness.buscarCompromissosPorRangeData(filter);
		} catch (Exception e) {
			addActionError(e.getLocalizedMessage());
			return INPUT;
		}
		
        return SUCCESS;
	}

	private boolean validarPedidoDeProcessamento() {
		boolean valido = true;

		if (FunctionsHelper.isNuloOuVazioString(filter.getTipoImpressao())) {
			addActionError("Tipo de impressão inválido.");
			valido = false;
		}

		if (FunctionsHelper.isNuloOuVazioString(filter.getDataInicial())) {
			addActionError("Não foi definido uma data inicial.");
			valido = false;
		}

		return valido;
	}

	public String getProximaAction() {
		return proximaAction;
	}

	public List<CompromissoVo> getCompromissosList() {
		return compromissosList;
	}

	public void setCompromissosList(List<CompromissoVo> compromissosList) {
		this.compromissosList = compromissosList;
	}

	public CompromissoFilter getFilter() {
		return filter;
	}

	public void setFilter(CompromissoFilter filter) {
		this.filter = filter;
	}
}
