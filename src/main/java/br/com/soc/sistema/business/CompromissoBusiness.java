package br.com.soc.sistema.business;

import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import br.com.soc.sistema.dao.CompromissoDao;
import br.com.soc.sistema.exception.BusinessException;
import br.com.soc.sistema.exception.TechnicalException;
import br.com.soc.sistema.filter.CompromissoFilter;
import br.com.soc.sistema.helper.FunctionsHelper;
import br.com.soc.sistema.vo.AgendaVo;
import br.com.soc.sistema.vo.CompromissoVo;

public class CompromissoBusiness {
    private CompromissoDao compromissoDao;
    private static final String FOI_INFORMADO_CARACTER_NO_LUGAR_DE_UM_NUMERO = "Foi informado um caracter no lugar de um numero";
    private static final String DATA_INVALIDA = "Data informada está em formato inválido. Use o formato YYYY-MM-DD";
    
    public CompromissoBusiness() {
        this.compromissoDao = new CompromissoDao();
    }
    
    public void salvarCompromisso(CompromissoVo compromissoVo) throws SQLException {
        try {
        	if (!validarHoraComPeriodoAgenda(compromissoVo))
                throw new BusinessException("Horário fora do periodo disponível.");

            compromissoDao.insert(compromissoVo);
        } catch (Exception e) {
            throw new TechnicalException("Não foi possível realizar a inclusão do registro", e);
        }
    }

    public List<CompromissoVo> trazerTodosOsCompromissos() throws SQLException, ParseException {
        return compromissoDao.listAll();
    }

    public List<CompromissoVo> filtrarCompromissos(CompromissoFilter filter) throws SQLException, ParseException, BusinessException {
        List<CompromissoVo> compromissosVo = new ArrayList<>();

        switch (filter.getOpcoesCombo()) {
            case ID:
                try {
                	if(FunctionsHelper.isNuloOuVazioString(filter.getValorBusca())) throw new BusinessException("Preencha o campo ID para buscar.");
                	
                	CompromissoVo compromissoVo = compromissoDao.findByCodigo(Integer.parseInt(filter.getValorBusca()));
                
                	if(compromissoVo != null) compromissosVo.add(compromissoDao.findByCodigo(Integer.parseInt(filter.getValorBusca())));
                } catch (NumberFormatException e) {
                    throw new BusinessException(FOI_INFORMADO_CARACTER_NO_LUGAR_DE_UM_NUMERO);
                }
                break;

            case FUNCIONARIO:
            	compromissosVo.addAll(compromissoDao.findAllByFuncionario(filter.getValorBusca()));
                break;

            case AGENDA:
            	compromissosVo.addAll(compromissoDao.findAllByAgenda(filter.getValorBusca()));
                break;

            case DATA:
            	try {
            		if(FunctionsHelper.isNuloOuVazioString(filter.getValorBusca())) throw new BusinessException("Preencha o campo data para buscar.");
                	
            		compromissosVo.addAll(compromissoDao.findAllByData(filter.getValorBusca()));
                } catch (TechnicalException e) {
                    throw new BusinessException(DATA_INVALIDA);
                }
                break;
        }
        
        return compromissosVo;
    }
    
    public List<CompromissoVo> buscarCompromissosPorRangeData(CompromissoFilter filtro) throws ParseException {
    	return compromissoDao.findAllByRangeData(filtro);
    }

    public CompromissoVo buscarCompromissoPor(Integer codigo) throws SQLException, ParseException, BusinessException {
        try {
            return compromissoDao.findByCodigo(codigo);
        } catch (NumberFormatException e) {
            throw new BusinessException(FOI_INFORMADO_CARACTER_NO_LUGAR_DE_UM_NUMERO);
        }
    }

    public void atualizarCompromisso(CompromissoVo compromissoVo) throws SQLException {
        try {
            if (!validarHoraComPeriodoAgenda(compromissoVo))
                throw new BusinessException("Horário fora do periodo disponível.");

            compromissoDao.update(compromissoVo);
        } catch (Exception e) {
            throw new TechnicalException("Não foi possível atualizar o registro", e);
        }
    }
    
    private boolean validarHoraComPeriodoAgenda(CompromissoVo compromissoVo) throws SQLException, BusinessException {
    	AgendaVo agendaVo = new AgendaBusiness().buscarAgendaPor(compromissoVo.getIdAgenda());
    	
    	if(agendaVo == null) throw new BusinessException("Não foi possível encontrar a agenda");
    	
    	String horaInicioString = retornaHoraInicioDisponivel(agendaVo);
    	String horaFinalString = retornaHoraFinalDisponivel(agendaVo);
    	
    	LocalTime horaDefinida = LocalTime.parse(compromissoVo.getHora());
    	LocalTime horaInicio = LocalTime.parse(horaInicioString);
    	LocalTime horaFinal = LocalTime.parse(horaFinalString);
    	
    	return (horaDefinida.equals(horaInicio) || horaDefinida.isAfter(horaInicio)) && (horaDefinida.equals(horaFinal) || horaDefinida.isBefore(horaFinal));
    }
    
    private String retornaHoraInicioDisponivel(AgendaVo agendaVo) throws BusinessException {
    	String horaInicio = "";
    
    	switch (agendaVo.getCodigoPeriodoDisponivel()) {
		case "2":
			horaInicio = "12:00";	
			break;
		case "1":
		case "3": 
			horaInicio = "06:00";
			break;
		default:
			throw new BusinessException("Código indisponivel para disponibilizar o horário");
		}
    	
    	return horaInicio;
    }
    
    private String retornaHoraFinalDisponivel(AgendaVo agendaVo) throws BusinessException {
    	String horaFinal = "";
    	
    	switch (agendaVo.getCodigoPeriodoDisponivel()) {
		case "1":
			horaFinal = "11:59";	
			break;
		case "2":
		case "3": 
			horaFinal = "17:59";
			break;
		default:
			throw new BusinessException("Código indisponivel para disponibilizar o horário");
		}
    	
    	return horaFinal;
    }
    
    public void excluirCompromisso(Integer codigo) throws SQLException, BusinessException {
        try {
            compromissoDao.deleteCompromissoPorRowid(codigo);
        } catch (SQLException e) {
            throw new BusinessException("Não foi possível excluir o registro");
        }
    }

    public int buscarQuantidadeCompromissoPorCodigoAgenda(int id) throws SQLException {
        return compromissoDao.getQtdCompromissoPorCodigoAgenda(id);
    }
    
    public void excluirTodosCompromissosPorCodigoFuncionario(int id) throws SQLException, BusinessException {
        try {
            compromissoDao.deleteCompromissosPorCodigoFuncionario(id);
        } catch (SQLException e) {
            throw new BusinessException("Não foi possível excluir os compromissos");
        }
    }
}