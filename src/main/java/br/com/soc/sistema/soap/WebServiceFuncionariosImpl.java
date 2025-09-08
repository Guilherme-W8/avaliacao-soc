package br.com.soc.sistema.soap;

import javax.jws.WebService;

import br.com.soc.sistema.business.FuncionarioBusiness;
import br.com.soc.sistema.exception.BusinessException;

@WebService(endpointInterface = "br.com.soc.sistema.soap.WebServiceFuncionarios" )
public class WebServiceFuncionariosImpl implements WebServiceFuncionarios {

	private FuncionarioBusiness business;
	
	public WebServiceFuncionariosImpl() {
		this.business = new FuncionarioBusiness();
	}
	
	@Override
	public String buscarFuncionario(String codigo) throws BusinessException {		
		return business.buscarFuncionarioPor(codigo).toString();
	}
}
