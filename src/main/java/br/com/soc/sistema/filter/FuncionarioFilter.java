package br.com.soc.sistema.filter;

import br.com.soc.sistema.exception.BusinessException;
import br.com.soc.sistema.infra.OpcoesComboBuscar;

public class FuncionarioFilter {
	private OpcoesComboBuscar opcoesCombo;
	private String valorBusca;

	public String getValorBusca() {
		return valorBusca;
	}

	public FuncionarioFilter setValorBusca(String valorBusca) {
		this.valorBusca = valorBusca;
		return this;
	}

	public OpcoesComboBuscar getOpcoesCombo() {
		return opcoesCombo;
	}

	public FuncionarioFilter setOpcoesCombo(String codigo) throws IllegalArgumentException, BusinessException {
	    if (codigo == null || codigo.trim().isEmpty()) {
	        this.opcoesCombo = null;
	        return this;
	    }
	    this.opcoesCombo = OpcoesComboBuscar.buscarPor(codigo);
	    return this;
	}
	
	public boolean isNullOpcoesCombo() {
		return this.getOpcoesCombo() == null;
	}
	
	public static FuncionarioFilter builder() {
		return new FuncionarioFilter();
	}
}
