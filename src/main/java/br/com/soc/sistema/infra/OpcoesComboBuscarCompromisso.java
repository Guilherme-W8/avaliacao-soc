package br.com.soc.sistema.infra;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import br.com.soc.sistema.exception.BusinessException;

public enum OpcoesComboBuscarCompromisso {
    ID("1", "ID"),
    FUNCIONARIO("2", "Funcionário"),
    AGENDA("3", "Agenda"),
    DATA("4", "Data");

    private String codigo;
    private String descricao;
    private static final Map<String, OpcoesComboBuscarCompromisso> opcoes = new HashMap<>();

    static {
        Arrays.asList(OpcoesComboBuscarCompromisso.values())
              .forEach(opcao -> opcoes.put(opcao.getCodigo(), opcao));
    }

    private OpcoesComboBuscarCompromisso(String codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public static OpcoesComboBuscarCompromisso buscarPor(String codigo) throws BusinessException {
        if(codigo == null)
            throw new IllegalArgumentException("Informe um código válido");

        return Optional.ofNullable(opcoes.get(codigo))
                       .orElseThrow(() -> new BusinessException("Código informado não existe"));
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }
}