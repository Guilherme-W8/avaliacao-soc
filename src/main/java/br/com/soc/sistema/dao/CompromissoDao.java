package br.com.soc.sistema.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.soc.sistema.exception.TechnicalException;
import br.com.soc.sistema.helper.FunctionsHelper;
import br.com.soc.sistema.vo.CompromissoVo;

public class CompromissoDao extends Dao {
    
    private enum ParametrosCompromisso {
        ROWID,
        ID_FUNCIONARIO;
    }
    
    public int getQtdCompromissoPorCodigoAgenda(int id) throws SQLException {
        int qtdCompromissoPorCodigoAgenda = 0;
        StringBuilder query = new StringBuilder()
            .append("SELECT COUNT(c.rowid) qtdCompromisso FROM compromisso c ")
            .append("WHERE c.id_agenda = ?");

        try (Connection conn = getConexao(); 
             PreparedStatement ps = conn.prepareStatement(query.toString())) {
            
            ps.setInt(1, id);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    qtdCompromissoPorCodigoAgenda = rs.getInt("qtdCompromisso");
                }
            }
        } catch (TechnicalException e) {
            throw new TechnicalException(e.getMessage());
        }
        
        return qtdCompromissoPorCodigoAgenda;
    }

    public List<CompromissoVo> listarTodos() throws SQLException, ParseException {
        List<CompromissoVo> lista = new ArrayList<>();
        StringBuilder query = new StringBuilder()
            .append("SELECT c.rowid, c.id_funcionario, c.id_agenda, c.data, c.hora, ")
            .append("f.nm_funcionario, a.nm_agenda ")
            .append("FROM compromisso c ")
            .append("JOIN funcionario f ON c.id_funcionario = f.rowid ")
            .append("JOIN agenda a ON c.id_agenda = a.rowid ")
            .append("ORDER BY rowid DESC");

        try (Connection conn = getConexao(); 
             PreparedStatement ps = conn.prepareStatement(query.toString());
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                CompromissoVo compromissoVo = new CompromissoVo();
                compromissoVo.setRowid(rs.getInt("rowid"));
                compromissoVo.setIdFuncionario(rs.getInt("id_funcionario"));
                compromissoVo.setIdAgenda(rs.getInt("id_agenda"));
                compromissoVo.setData(FunctionsHelper.retornaDataBR(rs.getString("data")));

                String hora = rs.getTime("hora").toString();
                compromissoVo.setHora(hora.substring(0, 5));
                compromissoVo.setNomeFuncionario(rs.getString("nm_funcionario"));
                compromissoVo.setNomeAgenda(rs.getString("nm_agenda"));

                lista.add(compromissoVo);
            }
        } catch (TechnicalException e) {
            throw new TechnicalException(e.getMessage());
        }

        return lista;
    }

    public void inserir(CompromissoVo compromisso) throws SQLException {
        StringBuilder query = new StringBuilder("INSERT INTO compromisso (id_funcionario, id_agenda, data, hora) VALUES (?, ?, ?, ?)");

        try (Connection conn = getConexao(); 
             PreparedStatement ps = conn.prepareStatement(query.toString())) {

            ps.setInt(1, compromisso.getIdFuncionario());
            ps.setInt(2, compromisso.getIdAgenda());
            ps.setDate(3, Date.valueOf(compromisso.getData()));
            LocalTime localTime = LocalTime.parse(compromisso.getHora(), DateTimeFormatter.ofPattern("HH:mm"));
            ps.setTime(4, Time.valueOf(localTime));

            ps.executeUpdate();
        }
    }

    public void atualizar(CompromissoVo compromisso) throws SQLException {
        StringBuilder query = new StringBuilder("UPDATE compromisso SET id_funcionario = ?, id_agenda = ?, data = ?, hora = ? WHERE rowid = ?");

        try (Connection conn = getConexao(); 
             PreparedStatement ps = conn.prepareStatement(query.toString())) {

            ps.setInt(1, compromisso.getIdFuncionario());
            ps.setInt(2, compromisso.getIdAgenda());
            ps.setDate(3, Date.valueOf(compromisso.getData()));
            LocalTime localTime = LocalTime.parse(compromisso.getHora(), DateTimeFormatter.ofPattern("HH:mm"));
            ps.setTime(4, Time.valueOf(localTime));
            ps.setInt(5, compromisso.getRowid());

            ps.executeUpdate();
        }
    }
    
    public void deleteCompromissosPorCodigoFuncionario(int id) throws SQLException {
        deleteCompromisso(id, ParametrosCompromisso.ID_FUNCIONARIO);
    } 

    public void deleteCompromissoPorRowid(Integer id) throws SQLException {    
        deleteCompromisso(id, ParametrosCompromisso.ROWID);
    }
    
    private void deleteCompromisso(int id, ParametrosCompromisso parametroCompromisso) throws SQLException {
        StringBuilder query = new StringBuilder("DELETE FROM compromisso ");
        
        switch (parametroCompromisso) {
            case ID_FUNCIONARIO:
                query.append("WHERE id_funcionario = ?");
                break;
            case ROWID:
                query.append("WHERE rowid = ?");
                break;
        }
        
        try (Connection conn = getConexao(); 
             PreparedStatement ps = conn.prepareStatement(query.toString())) {
            
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public CompromissoVo findByCodigo(Integer codigo) throws SQLException, ParseException {
        StringBuilder query = new StringBuilder()
            .append("SELECT c.rowid, c.id_funcionario, c.id_agenda, c.data, c.hora, ")
            .append("f.nm_funcionario, a.nm_agenda ")
            .append("FROM compromisso c ")
            .append("JOIN funcionario f ON c.id_funcionario = f.rowid ")
            .append("JOIN agenda a ON c.id_agenda = a.rowid ")
            .append("WHERE c.rowid = ?");

        CompromissoVo compromissoVo = new CompromissoVo();

        try (Connection conn = getConexao(); 
             PreparedStatement ps = conn.prepareStatement(query.toString())) {

            ps.setInt(1, codigo);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    compromissoVo.setRowid(rs.getInt("rowid"));
                    compromissoVo.setIdFuncionario(rs.getInt("id_funcionario"));
                    compromissoVo.setIdAgenda(rs.getInt("id_agenda"));
                    compromissoVo.setData(rs.getString("data"));

                    String hora = rs.getTime("hora").toString();
                    compromissoVo.setHora(hora.substring(0, 5));

                    compromissoVo.setNomeFuncionario(rs.getString("nm_funcionario"));
                    compromissoVo.setNomeAgenda(rs.getString("nm_agenda"));
                }
            }
        } catch (TechnicalException e) {
            throw new TechnicalException(e.getMessage());
        }

        return compromissoVo;
    }

    public List<CompromissoVo> findAllByFuncionario(String nomeFuncionario) throws ParseException {
        StringBuilder query = new StringBuilder()
            .append("SELECT c.rowid, c.id_funcionario, c.id_agenda, c.data, c.hora, ")
            .append("f.nm_funcionario, a.nm_agenda ")
            .append("FROM compromisso c ")
            .append("JOIN funcionario f ON c.id_funcionario = f.rowid ")
            .append("JOIN agenda a ON c.id_agenda = a.rowid ")
            .append("WHERE LOWER(f.nm_funcionario) LIKE LOWER(?) ")
            .append("ORDER BY c.data, c.hora");

        try (Connection con = getConexao(); 
             PreparedStatement ps = con.prepareStatement(query.toString())) {

            ps.setString(1, "%" + nomeFuncionario + "%");

            try (ResultSet rs = ps.executeQuery()) {
                List<CompromissoVo> compromissos = new ArrayList<>();

                while (rs.next()) {
                    CompromissoVo compromissoVo = new CompromissoVo();
                    compromissoVo.setRowid(rs.getInt("rowid"));
                    compromissoVo.setIdFuncionario(rs.getInt("id_funcionario"));
                    compromissoVo.setIdAgenda(rs.getInt("id_agenda"));
                    compromissoVo.setData(FunctionsHelper.retornaDataBR(rs.getString("data")));

                    String hora = rs.getTime("hora").toString();
                    compromissoVo.setHora(hora.substring(0, 5));
                    compromissoVo.setNomeFuncionario(rs.getString("nm_funcionario"));
                    compromissoVo.setNomeAgenda(rs.getString("nm_agenda"));

                    compromissos.add(compromissoVo);
                }
                return compromissos;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public List<CompromissoVo> findAllByAgenda(String nomeAgenda) throws ParseException {
        StringBuilder query = new StringBuilder()
            .append("SELECT c.rowid, c.id_funcionario, c.id_agenda, c.data, c.hora, ")
            .append("f.nm_funcionario, a.nm_agenda ")
            .append("FROM compromisso c ")
            .append("JOIN funcionario f ON c.id_funcionario = f.rowid ")
            .append("JOIN agenda a ON c.id_agenda = a.rowid ")
            .append("WHERE LOWER(a.nm_agenda) LIKE LOWER(?) ")
            .append("ORDER BY c.data, c.hora");

        try (Connection con = getConexao(); 
             PreparedStatement ps = con.prepareStatement(query.toString())) {

            ps.setString(1, "%" + nomeAgenda + "%");

            try (ResultSet rs = ps.executeQuery()) {
                List<CompromissoVo> compromissos = new ArrayList<>();

                while (rs.next()) {
                    CompromissoVo compromissoVo = new CompromissoVo();
                    compromissoVo.setRowid(rs.getInt("rowid"));
                    compromissoVo.setIdFuncionario(rs.getInt("id_funcionario"));
                    compromissoVo.setIdAgenda(rs.getInt("id_agenda"));
                    compromissoVo.setData(FunctionsHelper.retornaDataBR(rs.getString("data")));

                    String hora = rs.getTime("hora").toString();
                    compromissoVo.setHora(hora.substring(0, 5));
                    compromissoVo.setNomeFuncionario(rs.getString("nm_funcionario"));
                    compromissoVo.setNomeAgenda(rs.getString("nm_agenda"));

                    compromissos.add(compromissoVo);
                }
                return compromissos;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public List<CompromissoVo> findAllByData(String data) throws ParseException {
        StringBuilder query = new StringBuilder()
            .append("SELECT c.rowid, c.id_funcionario, c.id_agenda, c.data, c.hora, ")
            .append("f.nm_funcionario, a.nm_agenda ")
            .append("FROM compromisso c ")
            .append("JOIN funcionario f ON c.id_funcionario = f.rowid ")
            .append("JOIN agenda a ON c.id_agenda = a.rowid ")
            .append("WHERE c.data = ? ")
            .append("ORDER BY c.hora");

        try (Connection con = getConexao(); 
             PreparedStatement ps = con.prepareStatement(query.toString())) {

            ps.setDate(1, java.sql.Date.valueOf(data));

            try (ResultSet rs = ps.executeQuery()) {
                List<CompromissoVo> compromissos = new ArrayList<>();

                while (rs.next()) {
                    CompromissoVo compromissoVo = new CompromissoVo();
                    compromissoVo.setRowid(rs.getInt("rowid"));
                    compromissoVo.setIdFuncionario(rs.getInt("id_funcionario"));
                    compromissoVo.setIdAgenda(rs.getInt("id_agenda"));
                    compromissoVo.setData(FunctionsHelper.retornaDataBR(rs.getString("data")));

                    String hora = rs.getTime("hora").toString();
                    compromissoVo.setHora(hora.substring(0, 5));
                    compromissoVo.setNomeFuncionario(rs.getString("nm_funcionario"));
                    compromissoVo.setNomeAgenda(rs.getString("nm_agenda"));

                    compromissos.add(compromissoVo);
                }
                return compromissos;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}