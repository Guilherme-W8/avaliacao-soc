package br.com.soc.sistema.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.ServletActionContext;

import br.com.soc.sistema.business.CompromissoBusiness;
import br.com.soc.sistema.filter.CompromissoFilter;
import br.com.soc.sistema.helper.FunctionsHelper;
import br.com.soc.sistema.infra.Action;
import br.com.soc.sistema.vo.CompromissoVo;

@SuppressWarnings("serial")
public class RelatorioCompromissoExcelAction extends Action{
	private List<CompromissoVo> compromissosList = new ArrayList<>();
	private CompromissoFilter filter = new CompromissoFilter();
	private final String proximaAction = "filtrarRelatorios";
	
	public String gerar() {
		if (!validarPedidoDeProcessamento()) {
			return INPUT;
		}
		
		try {
			CompromissoBusiness compromissoBusiness = new CompromissoBusiness();
			setCompromissosList(compromissoBusiness.buscarCompromissosPorRangeData(filter));
			processarArquivoXlsx();
		} catch (Exception e) {
			addActionError(e.getLocalizedMessage());
			return INPUT;
		}
		
		return SUCCESS;
	}
	
	private boolean validarPedidoDeProcessamento() {
		boolean valido = true;

		if (FunctionsHelper.isNuloOuVazioString(filter.getTipoImpressao())) {
			addActionError("Tipo de impressão inválido.");
			valido = false;
		}

		if (FunctionsHelper.isNuloOuVazioString(filter.getDataInicial())) {
			addActionError("Não foi definido uma data inicial.");
			valido = false;
		}

		return valido;
	}
	
	private void processarArquivoXlsx() {
		try {
			ByteArrayInputStream fileInputStream = gerarExcelRelatorios();
			gerarDownloadDoArquivo(fileInputStream);
		} catch (Exception e) {
			e.printStackTrace();
			addActionError("Houve um problema ao tentar processar o documento");
		}
	}
	
	private String pegarNomeArquivo() {
		LocalDateTime agora = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
		String dataFormatada = agora.format(formatter);
		return String.format("compromissos-%s.xlsx", dataFormatada);
	}
	
	public void gerarDownloadDoArquivo(ByteArrayInputStream fileInputStream) throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-Disposition", "attachment; filename=" + pegarNomeArquivo());

		ServletOutputStream outputStream = response.getOutputStream();
		byte[] buffer = new byte[1024];
		int bytesRead;

		while ((bytesRead = fileInputStream.read(buffer)) != -1) {
			outputStream.write(buffer, 0, bytesRead);
		}

		outputStream.flush();
		outputStream.close();
		fileInputStream.close();
	}

	public ByteArrayInputStream gerarExcelRelatorios() throws Exception {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Compromissos");

		sheet.setDefaultColumnWidth(15);
		sheet.setDefaultRowHeight((short) 400);

		Row headerRow = sheet.createRow(0);
		String[] headers = { "ID Funcionário", "Funcionário", "ID Agenda", "Agenda", "Data", "Hora"};
		for (int i = 0; i < headers.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(headers[i]);
		}

		int rownum = 1;
		for (CompromissoVo compromissoVo : compromissosList) {
			Row row = sheet.createRow(rownum++);
			row.createCell(0).setCellValue(compromissoVo.getIdFuncionario());
			row.createCell(1).setCellValue(compromissoVo.getNomeFuncionario());
			row.createCell(2).setCellValue(compromissoVo.getIdAgenda());
			row.createCell(3).setCellValue(compromissoVo.getNomeAgenda());
			row.createCell(4).setCellValue(compromissoVo.getData());
			row.createCell(5).setCellValue(compromissoVo.getHora());
		}

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		workbook.write(out);
		return new ByteArrayInputStream(out.toByteArray());
	}

	public String getProximaAction() {
		return proximaAction;
	}

	public List<CompromissoVo> getCompromissosList() {
		return compromissosList;
	}

	public void setCompromissosList(List<CompromissoVo> compromissosList) {
		this.compromissosList = compromissosList;
	}

	public CompromissoFilter getFilter() {
		return filter;
	}

	public void setFilter(CompromissoFilter filter) {
		this.filter = filter;
	}
	
}
