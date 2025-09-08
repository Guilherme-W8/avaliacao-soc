package br.com.soc.sistema.business;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.soc.sistema.dao.AgendaDao;
import br.com.soc.sistema.exception.BusinessException;
import br.com.soc.sistema.filter.AgendaFilter;
import br.com.soc.sistema.helper.FunctionsHelper;
import br.com.soc.sistema.vo.AgendaVo;

public class AgendaBusiness {
	private static final String FOI_INFORMADO_CARACTER_NO_LUGAR_DE_UM_NUMERO = "Foi informado um caracter no lugar de um numero";
	private AgendaDao agendaDao;

	public AgendaBusiness() {
		this.agendaDao = new AgendaDao();
	}

	public void salvarAgenda(AgendaVo agenda) throws SQLException, BusinessException {
		try {
			if (FunctionsHelper.isNuloOuVazioString(agenda.getNome()))
				throw new IllegalArgumentException("Nome não pode ser em branco");

			agendaDao.insert(agenda);
		} catch (Exception e) {
			throw new BusinessException("Não foi possível realizar a inclusão do registro");
		}
	}

	public List<AgendaVo> trazerTodasAsAgendas() throws SQLException {
		return agendaDao.listAll();
	}

	public List<AgendaVo> filtrarAgendas(AgendaFilter filter) throws SQLException, BusinessException {
		List<AgendaVo> agendasVo = new ArrayList<>();

 		switch (filter.getOpcoesCombo()) {
		case ID:
			try {
				if(FunctionsHelper.isNuloOuVazioString(filter.getValorBusca())) throw new BusinessException("Preencha o campo ID para buscar.");
				
				AgendaVo agendaVo = agendaDao.findByCodigo(Integer.parseInt(filter.getValorBusca()));

				if(agendaVo != null) agendasVo.add(agendaDao.findByCodigo(Integer.parseInt(filter.getValorBusca())));
				
			} catch (NumberFormatException e) {
				throw new BusinessException(FOI_INFORMADO_CARACTER_NO_LUGAR_DE_UM_NUMERO);
			}
			break;

		case NOME:
			
			if(FunctionsHelper.isNuloOuVazioString(filter.getValorBusca())) throw new BusinessException("Preencha o campo nome para buscar.");
			
			agendasVo.addAll(agendaDao.findAllByNome(filter.getValorBusca()));
			break;

		case PERIODO:
			try {
				
				if (filter.isNullOpcoesPeriodoDisponivel()) throw new BusinessException("Escolha uma opção de período válida.");

				agendasVo.addAll(agendaDao.findAllByPeriodo(filter.getOpcoesPeriodoDisponivel().getCodigo()));
			} catch (IllegalArgumentException e) {
				throw new BusinessException("Não há código selecionado para essa opção");
			}
			break;
		}
		return agendasVo;
	}

	public AgendaVo buscarAgendaPor(Integer codigo) throws SQLException, BusinessException {
		try {
			return agendaDao.findByCodigo(codigo);
		} catch (NumberFormatException e) {
			throw new BusinessException(FOI_INFORMADO_CARACTER_NO_LUGAR_DE_UM_NUMERO);
		}
	}

	public void atualizarAgenda(AgendaVo agendaVo) throws Exception {
		try {
			CompromissoBusiness compromissoBusiness = new CompromissoBusiness();

			if (compromissoBusiness.buscarQuantidadeCompromissoPorCodigoAgenda(agendaVo.getRowid()) > 0)
				throw new BusinessException("Agenda possui vínculo com compromissos");

			if (FunctionsHelper.isNuloOuVazioString(agendaVo.getNome()))
				throw new BusinessException("Nome não pode ser em branco");

			agendaDao.update(agendaVo);
		} catch (IllegalArgumentException e) {
			throw new BusinessException("Não foi possível atualizar o registro");
		}
	}

	public void excluirAgenda(Integer codigo) throws SQLException, BusinessException {
		try {
			CompromissoBusiness compromissoBusiness = new CompromissoBusiness();

			if (compromissoBusiness.buscarQuantidadeCompromissoPorCodigoAgenda(codigo) > 0)
				throw new BusinessException("Agenda possui vínculo com compromissos");

			agendaDao.delete(codigo);
		} catch (Exception e) {
			throw new BusinessException("Não foi possível excluir o registro");
		}
	}
}