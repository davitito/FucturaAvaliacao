package dao;

import java.util.List;
import entidade.Peca;

public interface PecaDAO {
	
	public void inserir(Peca peca);
	
	public void alterar(Peca peca);

	public void remover(Peca peca);

	public Peca pesquisar(String nome);

	public List<Peca> listarTodos();

}
