package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entidade.Veiculo;
import util.JpaUtil;

public class VeiculoDAOImpl implements VeiculoDAO {

	public void inserir(Veiculo veiculo) {

		String sql = "insert into VEICULOS (NOME, MARCA, TIPO) values (?, ?, ?)";
		
		Connection conexao;
		try {
			conexao = JpaUtil.getConexao();
			
			PreparedStatement ps = conexao.prepareStatement(sql);
			
			ps.setString(1, veiculo.getNome());
			ps.setString(2, veiculo.getMarca());
			ps.setString(3,  veiculo.getTipo());

			ps.execute();
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}


	public void alterar(Veiculo veiculo) {

		String sql = "UPDATE VEICULOS SET MARCA = ?, TIPO = ? where NOME = ?";

		Connection conexao;
		try {
			conexao = JpaUtil.getConexao();
			
			PreparedStatement ps = conexao.prepareStatement(sql);
			
			ps.setString(1, veiculo.getMarca());
			ps.setString(2, veiculo.getTipo());
			ps.setString(3, veiculo.getNome());
			
			ps.execute();
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void remover(Veiculo veiculo) {

		String sql = "DELETE FROM VEICULOS WHERE NOME = ?";

		Connection conexao;
		try {
			conexao = JpaUtil.getConexao();
			
			PreparedStatement ps = conexao.prepareStatement(sql);
			
			ps.setString(1, veiculo.getNome());

			ps.execute();
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public Veiculo pesquisar(String nome) {
		
		String sql = "select V.NOME, V.MARCA, V.TIPO from VEICULOS V where NOME = ?";
		
		Veiculo veiculo = null;
		
		
		Connection conexao;
		try {
			conexao = JpaUtil.getConexao();
			
			PreparedStatement ps = conexao.prepareStatement(sql);
			
			ps.setString(1, nome);

			ResultSet res = ps.executeQuery();

			while (res.next()) {
				veiculo = new Veiculo();
				veiculo.setNome(res.getString("NOME"));
				veiculo.setMarca(res.getString("MARCA"));
				veiculo.setTipo(res.getString("TIPO"));
			 }
			
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return veiculo;
	}

	public List<Veiculo> listarTodos() {

		String sql = "select V.NOME, V.MARCA, V.TIPO from VEICULOS V";
		
		List<Veiculo> listaVeiculos = new ArrayList<Veiculo>();
		
		Connection conexao;
		try {
			conexao = JpaUtil.getConexao();
			
			PreparedStatement ps = conexao.prepareStatement(sql);

			ResultSet res = ps.executeQuery();

			while (res.next()) {
				
				Veiculo veiculo = new Veiculo();
				veiculo.setNome(res.getString("NOME"));
				veiculo.setMarca(res.getString("MARCA"));
				veiculo.setTipo(res.getString("TIPO"));
				listaVeiculos.add(veiculo);
			 }
			
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return listaVeiculos;

	}

}
