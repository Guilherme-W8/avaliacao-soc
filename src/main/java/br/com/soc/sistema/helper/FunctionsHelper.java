package br.com.soc.sistema.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FunctionsHelper {
	public static boolean isNuloOuVazioString(String value) {
		return value == null || value.isEmpty();
	}
	
	public static String retornaDataBR(String data) throws ParseException {
		SimpleDateFormat formatoOrigem = new SimpleDateFormat("yyyy-MM-dd");
		Date dataOrigem = formatoOrigem.parse(data);

		SimpleDateFormat formatoDestino = new SimpleDateFormat("dd/MM/yyyy");
		return formatoDestino.format(dataOrigem);
	}
}
