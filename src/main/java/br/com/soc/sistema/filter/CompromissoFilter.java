package br.com.soc.sistema.filter;

import br.com.soc.sistema.exception.BusinessException;
import br.com.soc.sistema.infra.OpcoesComboBuscarCompromisso;

public class CompromissoFilter {
    private OpcoesComboBuscarCompromisso opcoesCombo;
    private String valorBusca;
    private String dataInicial;
	private String dataFinal;
	private String tipoImpressao;

    public String getValorBusca() {
        return valorBusca;
    }

    public CompromissoFilter setValorBusca(String valorBusca) {
        this.valorBusca = valorBusca;
        return this;
    }

    public OpcoesComboBuscarCompromisso getOpcoesCombo() {
        return opcoesCombo;
    }

    public CompromissoFilter setOpcoesCombo(String codigo) throws BusinessException {
        if (codigo == null || codigo.trim().isEmpty()) {
            this.opcoesCombo = null;
            return this;
        }
        this.opcoesCombo = OpcoesComboBuscarCompromisso.buscarPor(codigo);
        return this;
    }

    public boolean isNullOpcoesCombo() {
        return this.opcoesCombo == null;
    }

    public static CompromissoFilter builder() {
        return new CompromissoFilter();
    }
    
    public String getDataInicial() {
		return dataInicial;
	}
	public void setDataInicial(String dataInicial) {
		this.dataInicial = dataInicial;
	}
	public String getDataFinal() {
		return dataFinal;
	}
	public void setDataFinal(String dataFinal) {
		this.dataFinal = dataFinal;
	}
	public String getTipoImpressao() {
		return tipoImpressao;
	}
	public void setTipoImpressao(String tipoImpressao) {
		this.tipoImpressao = tipoImpressao;
	}
}