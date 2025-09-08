package br.com.soc.sistema.soap;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import br.com.soc.sistema.exception.BusinessException;

@WebService
@SOAPBinding(style = Style.RPC)
public interface WebServiceFuncionarios {
	@WebMethod
	public String buscarFuncionario(String codigo) throws BusinessException;
	
}
