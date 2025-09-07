package br.com.soc.sistema.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.soc.sistema.business.FuncionarioBusiness;
import br.com.soc.sistema.filter.FuncionarioFilter;
import br.com.soc.sistema.helper.FunctionsHelper;
import br.com.soc.sistema.infra.Action;
import br.com.soc.sistema.infra.OpcoesComboBuscar;
import br.com.soc.sistema.vo.FuncionarioVo;

@SuppressWarnings("serial")
public class FuncionarioAction extends Action {

	private List<FuncionarioVo> funcionarios = new ArrayList<>();
	private FuncionarioBusiness business = new FuncionarioBusiness();
	private FuncionarioFilter filtrar = new FuncionarioFilter();
	private FuncionarioVo funcionarioVo = new FuncionarioVo();
	private final String proximaAction = "todosFuncionarios";

	public String todos() {
		funcionarios.addAll(business.trazerTodosOsFuncionarios());
		return SUCCESS;
	}

	public String filtrar() {
		if(funcionarioVo.getRowid() == null) {
			return REDIRECT;
		}
		
		if (filtrar.isNullOpcoesCombo())
			return REDIRECT;
		
		funcionarios = business.filtrarFuncionarios(filtrar);

		return SUCCESS;
	}

	public String input() {
		if (FunctionsHelper.isNuloOuVazioString(funcionarioVo.getRowid()))
			return INPUT;
		funcionarioVo = business.buscarFuncionarioPor(funcionarioVo.getRowid());
		return INPUT;
	}

	public String atualizar() {
		if (FunctionsHelper.isNuloOuVazioString(funcionarioVo.getNome())) {
			addActionError("O nome não pode ser vazio.");
			return INPUT;
		}
		
		try {
			if (FunctionsHelper.isNuloOuVazioString(funcionarioVo.getRowid()))
				business.salvarFuncionario(funcionarioVo);
			else
				business.atualizarFuncionario(funcionarioVo);
		} catch (Exception e) {
			addActionError(e.getMessage());
			return INPUT;
		}
		
		return REDIRECT;
	}

	public String excluir() {
		if (funcionarioVo.getRowid() == null)
			return ERROR;

		try {
			business.excluirFuncionario(funcionarioVo.getRowid());
		} catch (Exception e) {
			addActionError(e.getMessage());
			return ERROR;
		}
		return REDIRECT;
	}

	public List<OpcoesComboBuscar> getListaOpcoesCombo() {
		return Arrays.asList(OpcoesComboBuscar.values());
	}

	public List<FuncionarioVo> getFuncionarios() {
		return funcionarios;
	}

	public void setFuncionarios(List<FuncionarioVo> funcionarios) {
		this.funcionarios = funcionarios;
	}

	public FuncionarioFilter getFiltrar() {
		return filtrar;
	}

	public void setFiltrar(FuncionarioFilter filtrar) {
		this.filtrar = filtrar;
	}

	public FuncionarioVo getFuncionarioVo() {
		return funcionarioVo;
	}

	public void setFuncionarioVo(FuncionarioVo funcionarioVo) {
		this.funcionarioVo = funcionarioVo;
	}

	public String getProximaAction() {
		return proximaAction;
	}
}
