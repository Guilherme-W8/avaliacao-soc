package br.com.soc.sistema.exception;

@SuppressWarnings("serial")
public class BusinessException extends Exception{
	public BusinessException(String mensagem) {
		super(mensagem);
	}
}
