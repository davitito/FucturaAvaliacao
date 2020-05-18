package entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="VEICULOS")
public class Veiculo {

	@Id
	@Column(name="NOME", nullable = false)
	private String nome;
	
	@Column(name="MARCA", nullable = false)
	private String marca;
	
	
	@Column(name="TIPO", nullable = false)
	private String tipo;

	@ManyToOne
	private Peca peca;
	

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Peca getPeca() {
		return peca;
	}

	public void setPeca(Peca peca) {
		this.peca = peca;
	}


}
