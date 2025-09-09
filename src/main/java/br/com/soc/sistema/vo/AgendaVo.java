package br.com.soc.sistema.vo;

import br.com.soc.sistema.exception.BusinessException;
import br.com.soc.sistema.infra.PeriodoDisponivelEnum;

public class AgendaVo {
	private Integer rowid;
	private String nome;
	private String codigoPeriodoDisponivel;

	public Integer getRowid() {
		return rowid;
	}

	public void setRowid(Integer id) {
		this.rowid = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCodigoPeriodoDisponivel() {
		return codigoPeriodoDisponivel;
	}

	public void setCodigoPeriodoDisponivel(String codigoPeriodoDisponivel) {
		this.codigoPeriodoDisponivel = codigoPeriodoDisponivel;
	}

	public String getPeriodoDisponivel() throws IllegalArgumentException, BusinessException {
		return PeriodoDisponivelEnum.buscarPor(codigoPeriodoDisponivel).getDescricao();
	}
}