package br.com.soc.sistema.filter;

import br.com.soc.sistema.exception.BusinessException;
import br.com.soc.sistema.helper.FunctionsHelper;
import br.com.soc.sistema.infra.OpcoesComboBuscarAgenda;
import br.com.soc.sistema.infra.PeriodoDisponivelEnum;

public class AgendaFilter {
    private OpcoesComboBuscarAgenda opcoesCombo;
    private PeriodoDisponivelEnum opcoesPeriodoDisponivel;
    private String valorBusca;

    public String getValorBusca() {
        return valorBusca;
    }

    public AgendaFilter setValorBusca(String valorBusca) {
        this.valorBusca = valorBusca;
        return this;
    }

    public OpcoesComboBuscarAgenda getOpcoesCombo() {
        return opcoesCombo;
    }

    public AgendaFilter setOpcoesCombo(String codigo) throws BusinessException {
        if (codigo == null || codigo.trim().isEmpty()) {
            this.opcoesCombo = null;
            return this;
        }
        this.opcoesCombo = OpcoesComboBuscarAgenda.buscarPor(codigo);
        return this;
    }
    
    public PeriodoDisponivelEnum getOpcoesPeriodoDisponivel() {
        return opcoesPeriodoDisponivel;
    }

    public AgendaFilter setOpcoesPeriodoDisponivel(String codigo) throws IllegalArgumentException, BusinessException {
        if (codigo == null || codigo.trim().isEmpty()) {
            this.opcoesPeriodoDisponivel = null;
            return this;
        }
        this.opcoesPeriodoDisponivel = PeriodoDisponivelEnum.buscarPor(codigo);
        return this;
    }

    public boolean isNullOpcoesCombo() {
        return this.opcoesCombo == null;
    }

    public boolean isNullOpcoesPeriodoDisponivel() {
        return this.opcoesPeriodoDisponivel == null;
    }
    
    public boolean isNullValorBusca() {
        return FunctionsHelper.isNuloOuVazioString(this.valorBusca);
    }

    public static AgendaFilter builder() {
        return new AgendaFilter();
    }
}