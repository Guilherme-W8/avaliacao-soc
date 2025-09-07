package br.com.soc.sistema.infra;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import br.com.soc.sistema.exception.BusinessException;

public enum OpcoesComboBuscarAgenda {
    ID("1", "ID"),
    NOME("2", "Nome"),
    PERIODO("3", "Período Disponivel");

    private String codigo;
    private String descricao;
    private static final Map<String, OpcoesComboBuscarAgenda> opcoes = new HashMap<>();

    static {
        Arrays.asList(OpcoesComboBuscarAgenda.values())
              .forEach(opcao -> opcoes.put(opcao.getCodigo(), opcao));
    }

    private OpcoesComboBuscarAgenda(String codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public static OpcoesComboBuscarAgenda buscarPor(String codigo) {
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
