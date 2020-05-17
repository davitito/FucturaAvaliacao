package dao;

import java.util.List;
import entidade.Veiculo;

public interface VeiculoDAO {
	
	public void inserir(Veiculo veiculo);
	
	public void alterar(Veiculo veiculo);

	public void remover(Veiculo veiculo);

	public Veiculo pesquisar(String tipo);

	public List<Veiculo> listarTodos();

}
