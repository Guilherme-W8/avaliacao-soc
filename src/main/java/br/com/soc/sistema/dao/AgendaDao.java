package br.com.soc.sistema.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.soc.sistema.exception.TechnicalException;
import br.com.soc.sistema.vo.AgendaVo;

public class AgendaDao extends Dao {
    
    public List<AgendaVo> listAll() throws SQLException {
        StringBuilder query = new StringBuilder("SELECT rowid, nm_agenda, cd_periodo_disponivel FROM agenda");
        List<AgendaVo> lista = new ArrayList<>();

        try (Connection conn = getConexao(); 
             PreparedStatement ps = conn.prepareStatement(query.toString());
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                AgendaVo agendaVo = new AgendaVo();
                agendaVo.setRowid(rs.getInt("rowid"));
                agendaVo.setNome(rs.getString("nm_agenda"));
                agendaVo.setCodigoPeriodoDisponivel(rs.getString("cd_periodo_disponivel"));
                lista.add(agendaVo);
            }
        } catch (TechnicalException e) {
            throw new TechnicalException(e.getMessage());
        }

        return lista;
    }

    public void insert(AgendaVo agenda) throws SQLException {
        StringBuilder query = new StringBuilder("INSERT INTO agenda (nm_agenda, cd_periodo_disponivel) VALUES (?, ?)");
        
        try (Connection conn = getConexao(); 
             PreparedStatement ps = conn.prepareStatement(query.toString())) {
            
            ps.setString(1, agenda.getNome());
            
            if (agenda.getCodigoPeriodoDisponivel() != null && !agenda.getCodigoPeriodoDisponivel().trim().isEmpty()) {
                ps.setInt(2, Integer.valueOf(agenda.getCodigoPeriodoDisponivel()));
            } else {
                ps.setNull(2, java.sql.Types.INTEGER);
            }
            
            ps.executeUpdate();
        } catch (NumberFormatException e) {
            throw new SQLException("Código do período inválido: " + agenda.getCodigoPeriodoDisponivel());
        }
    }

    public void update(AgendaVo agendaVo) throws SQLException {
        StringBuilder query = new StringBuilder("UPDATE agenda SET nm_agenda = ?, cd_periodo_disponivel = ? WHERE rowid = ?");
        
        try (Connection conn = getConexao(); 
             PreparedStatement ps = conn.prepareStatement(query.toString())) {
            
            ps.setString(1, agendaVo.getNome());
            ps.setInt(2, Integer.valueOf(agendaVo.getCodigoPeriodoDisponivel()));
            ps.setLong(3, agendaVo.getRowid());
            ps.executeUpdate();
        }
    }

    public void delete(Integer id) throws SQLException {
        StringBuilder query = new StringBuilder("DELETE FROM agenda WHERE rowid = ?");
        
        try (Connection conn = getConexao(); 
             PreparedStatement ps = conn.prepareStatement(query.toString())) {
            
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public AgendaVo findByCodigo(Integer codigo) throws SQLException {
        StringBuilder query = new StringBuilder("SELECT rowid, nm_agenda, cd_periodo_disponivel FROM agenda WHERE rowid = ?");
        AgendaVo agendaVo = new AgendaVo();

        try (Connection conn = getConexao(); 
             PreparedStatement ps = conn.prepareStatement(query.toString())) {
            
            ps.setInt(1, codigo);
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                agendaVo.setRowid(rs.getInt("rowid"));
                agendaVo.setNome(rs.getString("nm_agenda"));
                agendaVo.setCodigoPeriodoDisponivel(rs.getString("cd_periodo_disponivel"));
                return agendaVo;
            }
         
        } catch (TechnicalException e) {
            throw new TechnicalException(e.getMessage());
        }
        return null;
    }

    public List<AgendaVo> findAllByNome(String nome) {
        StringBuilder query = new StringBuilder("SELECT rowid id, nm_agenda nome, cd_periodo_disponivel FROM agenda ")
                .append("WHERE lower(nm_agenda) LIKE lower(?)");

        try (Connection con = getConexao(); 
             PreparedStatement ps = con.prepareStatement(query.toString())) {

            ps.setString(1, "%" + nome + "%");

            try (ResultSet rs = ps.executeQuery()) {
                List<AgendaVo> agendas = new ArrayList<>();

                while (rs.next()) {
                    AgendaVo agendaVo = new AgendaVo();
                    agendaVo.setRowid(rs.getInt("id"));
                    agendaVo.setNome(rs.getString("nome"));
                    agendaVo.setCodigoPeriodoDisponivel(rs.getString("cd_periodo_disponivel"));
                    agendas.add(agendaVo);
                }
                return agendas;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public List<AgendaVo> findAllByPeriodo(String periodo) {
        StringBuilder query = new StringBuilder("SELECT rowid, nm_agenda, cd_periodo_disponivel FROM agenda WHERE cd_periodo_disponivel = ?");

        try (Connection con = getConexao(); 
             PreparedStatement ps = con.prepareStatement(query.toString())) {

            ps.setString(1, periodo);
            
            try (ResultSet rs = ps.executeQuery()) {
                List<AgendaVo> agendas = new ArrayList<>();

                while (rs.next()) {
                    AgendaVo agendaVo = new AgendaVo();
                    agendaVo.setRowid(rs.getInt("rowid"));
                    agendaVo.setNome(rs.getString("nm_agenda"));
                    agendaVo.setCodigoPeriodoDisponivel(rs.getString("cd_periodo_disponivel"));
                    agendas.add(agendaVo);
                }
                return agendas;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}