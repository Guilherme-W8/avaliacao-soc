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
    private AgendaDao dao;
    
    public AgendaBusiness() {
        this.dao = new AgendaDao();
    }
    
    public void salvarAgenda(AgendaVo agenda) throws SQLException {
        try {
            if (FunctionsHelper.isNuloOuVazioString(agenda.getNome()))
                throw new IllegalArgumentException("Nome não pode ser em branco");
            
            dao.inserir(agenda);
        } catch (Exception e) {
            throw new BusinessException("Não foi possível realizar a inclusão do registro");
        }
    }

    public List<AgendaVo> trazerTodasAsAgendas() throws SQLException {
        return dao.listarTodos();
    }

    public List<AgendaVo> filtrarAgendas(AgendaFilter filter) throws SQLException {
        List<AgendaVo> agendas = new ArrayList<>();

        switch (filter.getOpcoesCombo()) {
        case ID:
            try {
                agendas.add(dao.findByCodigo(Integer.parseInt(filter.getValorBusca())));
            } catch (NumberFormatException e) {
                throw new BusinessException(FOI_INFORMADO_CARACTER_NO_LUGAR_DE_UM_NUMERO);
            }
            break;

        case NOME:
            agendas.addAll(dao.findAllByNome(filter.getValorBusca()));
            break;

        case PERIODO:
            try {
                
                if (FunctionsHelper.isNuloOuVazioString(filter.getOpcoesPeriodoDisponivel().getCodigo())) {
                    throw new BusinessException("Não há código selecionado para essa opção");
                }
                
                agendas.addAll(dao.findAllByPeriodo(filter.getOpcoesPeriodoDisponivel().getCodigo()));
            } catch (IllegalArgumentException | BusinessException e) {
                throw new BusinessException("Não há código selecionado para essa opção");
            }
            break;
        }
        return agendas;
    }

    public AgendaVo buscarAgendaPor(Integer codigo) throws SQLException {
        try {
            return dao.findByCodigo(codigo);
        } catch (NumberFormatException e) {
            throw new BusinessException(FOI_INFORMADO_CARACTER_NO_LUGAR_DE_UM_NUMERO);
        }
    }

    public void atualizarAgenda(AgendaVo agendaVo) throws SQLException {
        try {
            if (FunctionsHelper.isNuloOuVazioString(agendaVo.getNome()))
                throw new IllegalArgumentException("Nome não pode ser em branco");

            dao.atualizar(agendaVo);
        } catch (Exception e) {
            throw new BusinessException("Não foi possível atualizar o registro");
        }
    }

    public void excluirAgenda(Integer codigo) throws SQLException {
        try {
            CompromissoBusiness compromissoBusiness = new CompromissoBusiness();
            
            if(compromissoBusiness.buscarQuantidadeCompromissoPorCodigoAgenda(codigo) > 0) 
                throw new BusinessException("Agenda possui vínculo com compromissos");

            dao.excluir(codigo);
        } catch (Exception e) {
            throw new BusinessException("Não foi possível excluir o registro");
        }
    }
}