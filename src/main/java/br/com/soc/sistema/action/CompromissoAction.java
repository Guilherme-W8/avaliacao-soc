package br.com.soc.sistema.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.soc.sistema.business.AgendaBusiness;
import br.com.soc.sistema.business.CompromissoBusiness;
import br.com.soc.sistema.business.FuncionarioBusiness;
import br.com.soc.sistema.filter.CompromissoFilter;
import br.com.soc.sistema.helper.FunctionsHelper;
import br.com.soc.sistema.infra.Action;
import br.com.soc.sistema.infra.OpcoesComboBuscarCompromisso;
import br.com.soc.sistema.vo.AgendaVo;
import br.com.soc.sistema.vo.CompromissoVo;
import br.com.soc.sistema.vo.FuncionarioVo;

@SuppressWarnings("serial")
public class CompromissoAction extends Action {
    private List<CompromissoVo> compromissos = new ArrayList<>();
    private CompromissoBusiness compromissoBusiness = new CompromissoBusiness();
    private CompromissoFilter filtrar = new CompromissoFilter();
    private CompromissoVo compromissoVo = new CompromissoVo();
    private final String proximaAction = "todosCompromissos";


    public String todos() {
        try {
            compromissos.addAll(compromissoBusiness.trazerTodosOsCompromissos());
        } catch (Exception e) {
            addActionError("Erro ao listar compromissos: " + e.getMessage());
            return ERROR;
        }
        return SUCCESS;
    }

    public String filtrar() { 
        if (filtrar.isNullOpcoesCombo()) return REDIRECT;

        try {
            compromissos = compromissoBusiness.filtrarCompromissos(filtrar);
        } catch (Exception e) {
            addActionError("Erro ao filtrar compromissos: " + e.getMessage());
            return ERROR;
        }

        return SUCCESS;
    }

    public String input() {
        if (FunctionsHelper.isNuloOuVazioString(compromissoVo.getRowid() != null ? compromissoVo.getRowid().toString() : null))
            return INPUT;
        
        try {
        	filtrar.setValorBusca(String.valueOf(compromissoVo.getRowid()));
            compromissoVo = compromissoBusiness.buscarCompromissoPor(compromissoVo.getRowid());
        } catch (Exception e) {
            addActionError("Erro ao buscar compromisso: " + e.getMessage());
            return ERROR;
        }
        return INPUT;
    }

    public String atualizar() {
    	if(!validarCamposCompromisso())
    		return INPUT;
    	
        try {
            if (compromissoVo.getRowid() == null)
                compromissoBusiness.salvarCompromisso(compromissoVo);
            else
                compromissoBusiness.atualizarCompromisso(compromissoVo);
        } catch (Exception e) {
            addActionError(e.getMessage());
            return INPUT;
        }
        
        return REDIRECT;
    }
    
    private boolean validarCamposCompromisso() {
    	boolean isValido = true;
    	
        if (compromissoVo.getIdFuncionario() == null || compromissoVo.getIdFuncionario() <= 0) {
            addActionError("Por favor, selecione um funcionário válido.");
            isValido = false;
        }
        
        if (compromissoVo.getIdAgenda() == null || compromissoVo.getIdAgenda() <= 0) {
            addActionError("Por favor, selecione uma agenda válida.");
            isValido = false;
        }
        
        if (FunctionsHelper.isNuloOuVazioString(compromissoVo.getData())) {
            addActionError("A data é obrigatória.");
            isValido = false;
        }
        
        if (FunctionsHelper.isNuloOuVazioString(compromissoVo.getHora())) {
            addActionError("O horário é obrigatório.");
            isValido = false;
        }
        
        if (!compromissoVo.getData().matches("\\d{4}-\\d{2}-\\d{2}")) {
            addActionError("Formato de data inválido.");
            isValido = false;
        }
        
        if (!compromissoVo.getHora().matches("\\d{2}:\\d{2}")) {
            addActionError("Formato de horário inválido.");
            isValido = false;
        }
        
        return isValido;
    }

    public String excluir() {
        if (compromissoVo.getRowid() == null)
            return ERROR;

        try {
            compromissoBusiness.excluirCompromisso(compromissoVo.getRowid());
        } catch (Exception e) {
            addActionError("Erro ao excluir compromisso: " + e.getMessage());
            return ERROR;
        }
        return REDIRECT;
    }

    public List<OpcoesComboBuscarCompromisso> getListaOpcoesCombo() {
        return Arrays.asList(OpcoesComboBuscarCompromisso.values());
    }

    public List<FuncionarioVo> getListaFuncionarios() {
        try {
            return new FuncionarioBusiness().trazerTodosOsFuncionarios();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<AgendaVo> getListaAgendas() {
        try {
            return new AgendaBusiness().trazerTodasAsAgendas();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public CompromissoVo getCompromissoVo() {
        return compromissoVo;
    }

    public void setCompromissoVo(CompromissoVo compromissoVo) {
        this.compromissoVo = compromissoVo;
    }

    public List<CompromissoVo> getCompromissos() {
        return compromissos;
    }

    public void setCompromissos(List<CompromissoVo> compromissos) {
        this.compromissos = compromissos;
    }

    public CompromissoFilter getFiltrar() {
        return filtrar;
    }

    public void setFiltrar(CompromissoFilter filtrar) {
        this.filtrar = filtrar;
    }

    public String getProximaAction() {
        return proximaAction;
    }
}