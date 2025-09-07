package br.com.soc.sistema.vo;


public class CompromissoVo {
    private Integer rowid;
    private Integer idFuncionario;
    private Integer idAgenda;
    private String data;
    private String hora;
    
    // Campos auxiliares para exibição
    private String nomeFuncionario;
    private String nomeAgenda;

    // Construtores
    public CompromissoVo() {}

    public Integer getRowid() {
        return rowid;
    }

    public void setRowid(Integer id) {
        this.rowid = id;
    }

    public Integer getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(Integer idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public Integer getIdAgenda() {
        return idAgenda;
    }

    public void setIdAgenda(Integer idAgenda) {
        this.idAgenda = idAgenda;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getNomeFuncionario() {
        return nomeFuncionario;
    }

    public void setNomeFuncionario(String nomeFuncionario) {
        this.nomeFuncionario = nomeFuncionario;
    }

    public String getNomeAgenda() {
        return nomeAgenda;
    }

    public void setNomeAgenda(String nomeAgenda) {
        this.nomeAgenda = nomeAgenda;
    }
}