package br.com.soc.sistema.action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.soc.sistema.business.AgendaBusiness;
import br.com.soc.sistema.filter.AgendaFilter;
import br.com.soc.sistema.helper.FunctionsHelper;
import br.com.soc.sistema.infra.Action;
import br.com.soc.sistema.infra.OpcoesComboBuscarAgenda;
import br.com.soc.sistema.infra.PeriodoDisponivelEnum;
import br.com.soc.sistema.vo.AgendaVo;

@SuppressWarnings("serial")
public class AgendaAction extends Action {
    private List<AgendaVo> agendas = new ArrayList<>();
    private AgendaBusiness business = new AgendaBusiness();
    private AgendaFilter filtrar = new AgendaFilter();
    private AgendaVo agendaVo = new AgendaVo();
    private final String proximaAction = "todosAgendas";

    public String todos() {
        try {
            agendas.addAll(business.trazerTodasAsAgendas());
        } catch (SQLException e) {
            addActionError("Erro ao listar agendas: " + e.getMessage());
            return ERROR;
        }
        return SUCCESS;
    }
    
    private boolean validaFiltro() {
    	// return filtrar.isNullOpcoesCombo() || filtrar.isNullOpcoesPeriodoDisponivel() || filtrar.isNullValorBusca();
    	return filtrar.isNullOpcoesCombo() && (filtrar.isNullOpcoesPeriodoDisponivel() || filtrar.isNullValorBusca());
    }
    
    public String filtrar() {
        if (validaFiltro())
            return REDIRECT;

        try {
            agendas = business.filtrarAgendas(filtrar);
        } catch (Exception e) {
            addActionError("Erro ao filtrar agendas: " + e.getMessage());
            return ERROR;
        }

        return SUCCESS;
    }

    public String input() {
        if (FunctionsHelper.isNuloOuVazioString(agendaVo.getRowid() != null ? agendaVo.getRowid().toString() : null))
            return INPUT;
        
        try {
            agendaVo = business.buscarAgendaPor(agendaVo.getRowid());
        } catch (Exception e) {
            addActionError("Erro ao buscar agenda: " + e.getMessage());
            return ERROR;
        }
        return INPUT;
    }

    public String atualizar() {
        if (FunctionsHelper.isNuloOuVazioString(agendaVo.getNome())) {
            addActionError("O nome não pode ser vazio.");
            return INPUT;
        }
        
        if (FunctionsHelper.isNuloOuVazioString(agendaVo.getCodigoPeriodoDisponivel())) {
            addActionError("O período disponível deve ser selecionado.");
            return INPUT;
        }
        
        // Validação adicional para caracteres especiais
        if (agendaVo.getNome().length() > 100) {
            addActionError("O nome da agenda não pode ter mais de 100 caracteres.");
            return INPUT;
        }
        
        try {
            if (agendaVo.getRowid() == null)
                business.salvarAgenda(agendaVo);
            else
                business.atualizarAgenda(agendaVo);
        } catch (Exception e) {
            addActionError(e.getMessage());
            return INPUT;
        }
        
        return REDIRECT;
    }

    public String excluir() {
        if (agendaVo.getRowid() == null)
            return ERROR;

        try {
            business.excluirAgenda(agendaVo.getRowid());
        } catch (Exception e) {
            // Verificar se o erro é devido a compromissos vinculados
            String mensagem = e.getMessage().toLowerCase();
            if (mensagem.contains("constraint") || mensagem.contains("foreign key") || 
                mensagem.contains("compromisso") || mensagem.contains("referencial")) {
                addActionError("Não é possível excluir esta agenda pois existem compromissos cadastrados para ela.");
            } else {
                addActionError("Erro ao excluir agenda: " + e.getMessage());
            }
            return ERROR;
        }
        return REDIRECT;
    }

    public List<OpcoesComboBuscarAgenda> getListaOpcoesCombo() {
        return Arrays.asList(OpcoesComboBuscarAgenda.values());
    }

    public List<PeriodoDisponivelEnum> getListaOpcoesPeriodoDisponivel() {
        return Arrays.asList(PeriodoDisponivelEnum.values());
    }

    public List<AgendaVo> getAgendas() {
        return agendas;
    }

    public void setAgendas(List<AgendaVo> agendas) {
        this.agendas = agendas;
    }

    public AgendaFilter getFiltrar() {
        return filtrar;
    }

    public void setFiltrar(AgendaFilter filtrar) {
        this.filtrar = filtrar;
    }

    public AgendaVo getAgendaVo() {
        return agendaVo;
    }

    public void setAgendaVo(AgendaVo agendaVo) {
        this.agendaVo = agendaVo;
    }

    public String getProximaAction() {
        return proximaAction;
    }
}