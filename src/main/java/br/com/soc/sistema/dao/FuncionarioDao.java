package br.com.soc.sistema.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.soc.sistema.vo.FuncionarioVo;

public class FuncionarioDao extends Dao {
    
    public void insert(FuncionarioVo funcionarioVo){
        StringBuilder query = new StringBuilder("INSERT INTO funcionario (nm_funcionario) VALUES (?)");
        
        try(Connection con = getConexao();
            PreparedStatement ps = con.prepareStatement(query.toString())){
            
            ps.setString(1, funcionarioVo.getNome());
            ps.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public List<FuncionarioVo> listAll(){
        StringBuilder query = new StringBuilder("SELECT rowid id, nm_funcionario nome FROM funcionario");
        
        try(Connection con = getConexao();
            PreparedStatement ps = con.prepareStatement(query.toString());
            ResultSet rs = ps.executeQuery()){
            
            FuncionarioVo vo = null;
            List<FuncionarioVo> funcionarios = new ArrayList<>();
            while (rs.next()) {
                vo = new FuncionarioVo();
                vo.setRowid(rs.getString("id"));
                vo.setNome(rs.getString("nome"));    
                
                funcionarios.add(vo);
            }
            return funcionarios;
        }catch (SQLException e) {
            e.printStackTrace();
        }
        
        return Collections.emptyList();
    }
    
    public List<FuncionarioVo> findAllByNome(String nome){
        StringBuilder query = new StringBuilder("SELECT rowid id, nm_funcionario nome FROM funcionario ")
                                .append("WHERE lower(nm_funcionario) like lower(?)");
        
        try(Connection con = getConexao();
            PreparedStatement ps = con.prepareStatement(query.toString())){
            
            ps.setString(1, "%" + nome + "%");
            
            try(ResultSet rs = ps.executeQuery()){
                FuncionarioVo vo = null;
                List<FuncionarioVo> funcionarios = new ArrayList<>();
                
                while (rs.next()) {
                    vo = new FuncionarioVo();
                    vo.setRowid(rs.getString("id"));
                    vo.setNome(rs.getString("nome"));    
                    
                    funcionarios.add(vo);
                }
                return funcionarios;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }        
        return Collections.emptyList();
    }
    
    public FuncionarioVo findByCodigo(Integer codigo){
        StringBuilder query = new StringBuilder("SELECT rowid id, nm_funcionario nome FROM funcionario ")
                                .append("WHERE rowid = ?");
        FuncionarioVo funcionarioVo = new FuncionarioVo();
        
        try(Connection con = getConexao();
            PreparedStatement ps = con.prepareStatement(query.toString())){
            
            ps.setInt(1, codigo);
            
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
            	funcionarioVo = new FuncionarioVo();
            	funcionarioVo.setRowid(rs.getString("id"));
            	funcionarioVo.setNome(rs.getString("nome"));   
            	return funcionarioVo;
            }
            
        }catch (SQLException e) {
            e.printStackTrace();
        }        
        return null;
    }
    
    public void update(FuncionarioVo funcionarioVo) {
        StringBuilder query = new StringBuilder("UPDATE funcionario SET nm_funcionario = ? WHERE rowid = ?");

        try (Connection con = getConexao();
             PreparedStatement ps = con.prepareStatement(query.toString())) {

            ps.setString(1, funcionarioVo.getNome());
            ps.setInt(2, Integer.parseInt(funcionarioVo.getRowid()));
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void delete(Integer codigo) {
        StringBuilder query = new StringBuilder("DELETE FROM funcionario WHERE rowid = ?");
        
        try (Connection con = getConexao();
             PreparedStatement ps = con.prepareStatement(query.toString())) {
            
            ps.setInt(1, codigo);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}