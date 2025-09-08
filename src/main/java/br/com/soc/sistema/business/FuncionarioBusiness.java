package br.com.soc.sistema.business;

import java.util.ArrayList;
import java.util.List;

import br.com.soc.sistema.dao.FuncionarioDao;
import br.com.soc.sistema.exception.BusinessException;
import br.com.soc.sistema.filter.FuncionarioFilter;
import br.com.soc.sistema.helper.FunctionsHelper;
import br.com.soc.sistema.vo.FuncionarioVo;

public class FuncionarioBusiness {

	private static final String FOI_INFORMADO_CARACTER_NO_LUGAR_DE_UM_NUMERO = "Foi informado um caracter no lugar de um numero";
	private FuncionarioDao funcionarioDao;

	public FuncionarioBusiness() {
		this.funcionarioDao = new FuncionarioDao();
	}

	public void salvarFuncionario(FuncionarioVo funcionarioVo) throws BusinessException {
		try {
			if (funcionarioVo.getNome().isEmpty())
				throw new IllegalArgumentException("Nome nao pode ser em branco");

			funcionarioDao.insert(funcionarioVo);
		} catch (Exception e) {
			throw new BusinessException("Não foi possível realizar a inclusão do registro");
		}
	}

	public List<FuncionarioVo> trazerTodosOsFuncionarios() {
		return funcionarioDao.listAll();
	}

	public List<FuncionarioVo> filtrarFuncionarios(FuncionarioFilter filter) throws BusinessException {
		List<FuncionarioVo> funcionarios = new ArrayList<>();

		switch (filter.getOpcoesCombo()) {
		case ID:
			try {
				if(FunctionsHelper.isNuloOuVazioString(filter.getValorBusca())) throw new BusinessException("Preencha o campo ID para buscar.");
				
				FuncionarioVo funcionarioVo = funcionarioDao.findByCodigo(Integer.parseInt(filter.getValorBusca()));
				
				if(funcionarioVo != null) funcionarios.add(funcionarioDao.findByCodigo(Integer.parseInt(filter.getValorBusca())));

			} catch (NumberFormatException e) {
				throw new BusinessException(FOI_INFORMADO_CARACTER_NO_LUGAR_DE_UM_NUMERO);
			}
			break;

		case NOME:
			funcionarios.addAll(funcionarioDao.findAllByNome(filter.getValorBusca()));
			break;
		}

		return funcionarios;
	}

	public FuncionarioVo buscarFuncionarioPor(String codigo) throws BusinessException {
		try {
			return funcionarioDao.findByCodigo(Integer.parseInt(codigo));
		} catch (NumberFormatException e) {
			throw new BusinessException(FOI_INFORMADO_CARACTER_NO_LUGAR_DE_UM_NUMERO);
		}
	}

	public void atualizarFuncionario(FuncionarioVo funcionarioVo) throws BusinessException {
		try {
			if (FunctionsHelper.isNuloOuVazioString(funcionarioVo.getNome()))
				throw new IllegalArgumentException("Nome não pode ser em branco");

			funcionarioDao.update(funcionarioVo);
		} catch (Exception e) {
			throw new BusinessException("Não foi possível atualizar o registro");
		}
	}

	public void excluirFuncionario(String codigo) throws BusinessException {
		try {
			CompromissoBusiness compromissoBusiness = new CompromissoBusiness();
			compromissoBusiness.excluirTodosCompromissosPorCodigoFuncionario(Integer.parseInt(codigo));
			funcionarioDao.delete(Integer.parseInt(codigo));
		} catch (NumberFormatException e) {
			throw new BusinessException(FOI_INFORMADO_CARACTER_NO_LUGAR_DE_UM_NUMERO);
		} catch (Exception e) {
			throw new BusinessException("Não foi possível excluir o registro");
		}
	}
}
