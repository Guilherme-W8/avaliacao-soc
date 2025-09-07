package br.com.soc.sistema.exception;

@SuppressWarnings("serial")
public class TechnicalException extends RuntimeException{
	
	public TechnicalException(String mensagem, Throwable throwable) {
		super(mensagem, throwable);
	}
	
	public TechnicalException(String mensagem) {
		super(mensagem);
	}
}
