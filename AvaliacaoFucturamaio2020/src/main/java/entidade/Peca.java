package entidade;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="PECAS")
public class Peca {

	@Id
	@Column(name="NOME", nullable = false)
	private String nome;
	
	@Column(name="DESCRICAO", nullable = false)
	private String descricao;
	
	@OneToMany
	@JoinColumn(name = "NOME_VEICULO", referencedColumnName = "NOME", nullable = false)
	private String nome_veiculo;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getNome_veiculo() {
		return nome_veiculo;
	}

	public void setNome_veiculo(String nome_veiculo) {
		this.nome_veiculo = nome_veiculo;
	}

	
	
}
	