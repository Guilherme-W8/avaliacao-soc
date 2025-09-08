package br.com.soc.sistema.infra;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import br.com.soc.sistema.exception.BusinessException;

public enum PeriodoDisponivelEnum {
	MANHA("1", "Manhã"),
    TARDE("2", "Tarde"),
    AMBOS("3", "Ambos");

    private String codigo;
    private String descricao;
    private static final Map<String, PeriodoDisponivelEnum> opcoes = new HashMap<>();

    static {
        Arrays.asList(PeriodoDisponivelEnum.values())
        	.forEach(opcao -> opcoes.put(opcao.getCodigo(), opcao));
    }
    
    public static PeriodoDisponivelEnum buscarPor(String codigo) throws IllegalArgumentException, BusinessException {
		if(codigo == null)
			throw new IllegalArgumentException("Informe um código válido");
		
		PeriodoDisponivelEnum opcao = getOpcao(codigo)
				.orElseThrow(() -> new BusinessException("Código informado não existe"));
		
		return opcao;
	}
    
    private static Optional<PeriodoDisponivelEnum> getOpcao(String codigo){
		return Optional.ofNullable(opcoes.get(codigo));
	}
    
    private PeriodoDisponivelEnum(String codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public static Map<String, PeriodoDisponivelEnum> getOpcoes() {
		return opcoes;
	}
}
