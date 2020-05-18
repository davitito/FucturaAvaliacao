package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entidade.Peca;
import util.JpaUtil;

public class PecaDAOImpl implements PecaDAO {

	public void inserir(Peca peca) {

		String sql = "insert into PECAS (NOME, DESCRICAO) values (?, ?)";
		System.out.println(sql);
		System.out.println(peca.getNome());
		System.out.println(peca.getDescricao());
		
		Connection conexao;
		try {
			conexao = JpaUtil.getConexao();
			
			PreparedStatement ps = conexao.prepareStatement(sql);
			
			ps.setString(1, peca.getNome());
			ps.setString(2, peca.getDescricao());

			ps.execute();
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}


	public void alterar(Peca peca) {

		String sql = "UPDATE PECAS SET DESCRICAO = ? where NOME = ?";

		Connection conexao;
		try {
			conexao = JpaUtil.getConexao();
			
			PreparedStatement ps = conexao.prepareStatement(sql);
			
		
			ps.setString(1, peca.getDescricao());
			ps.setString(2, peca.getNome());

			ps.execute();
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void remover(Peca peca) {

		String sql = "DELETE FROM PECAS WHERE NOME = ?";

		Connection conexao;
		try {
			conexao = JpaUtil.getConexao();
			
			PreparedStatement ps = conexao.prepareStatement(sql);
			
			ps.setString(1, peca.getNome());

			ps.execute();
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public Peca pesquisar(String nome) {
		
		String sql = "select P.NOME, P.DESCRICAO from PECAS P where NOME = ?";
		
		Peca peca = null;
		
		
		Connection conexao;
		try {
			conexao = JpaUtil.getConexao();
			
			PreparedStatement ps = conexao.prepareStatement(sql);
			
			ps.setString(1, nome);

			ResultSet res = ps.executeQuery();

			while (res.next()) {
				peca = new Peca();
				peca.setNome(res.getString("NOME"));
				peca.setDescricao(res.getString("DESCRICAO"));
				//veiculo.setNome_pecas(res.getString("NOME_PECAS"));
			 }
			
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return peca;
	}

	public List<Peca> listarTodos() {

		String sql = "select P.NOME, P.DESCRICAO from PECAS P";
		
		List<Peca> listaPecas = new ArrayList<Peca>();
		
		Connection conexao;
		try {
			conexao = JpaUtil.getConexao();
			
			PreparedStatement ps = conexao.prepareStatement(sql);

			ResultSet res = ps.executeQuery();

			while (res.next()) {
				
				Peca peca = new Peca();
				peca.setNome(res.getString("NOME"));
				peca.setDescricao(res.getString("DESCRICAO"));
				//Peca.setNome_pecas(res.getString("NOME_PECAS"));
				
				listaPecas.add(peca);
			 }
			
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return listaPecas;

	}

}
