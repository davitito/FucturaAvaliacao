package controle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import dao.UsuarioDAO;
import dao.UsuarioDAOImpl;
import dao.VeiculoDAO;
import dao.VeiculoDAOImpl;
import entidade.Usuario;
import entidade.Veiculo;

@ManagedBean(name = "LoginBean")
@SessionScoped
public class LoginBean {
	
	//Variáveis do login.xhtml
	private String txtEmail;
	private String txtSenha;
	

	//Variáveis do veiculos.xhtml
	private String txtNomeVeic;
	private String txtMarcaVeic;
	private String txtTipoVeic;

	private List<Usuario> listaUsuarios;
	private Usuario usuario;
	private String msgCadastroUsuario;
	
	private UsuarioDAO usuarioDAO;
	
	private List<Veiculo> listaVeiculos;
	private Veiculo veiculo;
	
	private VeiculoDAO veiculoDAO;

	
	public LoginBean() {
		this.listaUsuarios = new ArrayList<Usuario>();
		this.usuario = new Usuario();
		this.usuarioDAO = new UsuarioDAOImpl();
		
		this.listaVeiculos = new ArrayList<Veiculo>();
		this.veiculo = new Veiculo();
		this.veiculoDAO = new VeiculoDAOImpl();
	}

	public void entrar() throws IOException {
		boolean achou = false;
		this.listaUsuarios = this.usuarioDAO.listarTodos();
		for (Usuario usuarioPesquisa : listaUsuarios) {
			if (usuarioPesquisa.getEmail().equals(this.txtEmail) && usuarioPesquisa.getSenha().equals(this.txtSenha)) {
				achou = true;
			}
		}
		System.out.println(achou);
		if (achou) {
			FacesContext.getCurrentInstance().getExternalContext().redirect("veiculos.xhtml");			
		} else {
			FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
		}
	}
	
	public void pesquisarVeiculo() throws IOException {
		System.out.println("pesquisar");
		Veiculo novo = new Veiculo();
		novo.setNome(this.txtNomeVeic);
		novo.setMarca(this.txtMarcaVeic);
		novo.setTipo(this.txtTipoVeic);
		boolean achou = false;
		this.listaVeiculos = this.veiculoDAO.listarTodos();
		for (Veiculo veiculoPesquisa : listaVeiculos) {
			if (veiculoPesquisa.getNome().equalsIgnoreCase ((this.txtNomeVeic)) &&
					veiculoPesquisa.getMarca().equalsIgnoreCase ((this.txtMarcaVeic)) &&
					veiculoPesquisa.getTipo().equalsIgnoreCase ((this.txtTipoVeic))) {
				achou = true;
			}
		}
		if(achou) {
			System.out.println("Veículo encontrado");
			FacesContext.getCurrentInstance()
			.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "OK!", "Veículo encontrado!!!"));
			FacesContext.getCurrentInstance().getExternalContext().redirect("veiculos.xhtml");
			this.txtNomeVeic = "";
			this.txtMarcaVeic = "";
			this.txtTipoVeic = "";
		}else {
			System.out.println("não encontrado");
			FacesContext.getCurrentInstance()
			.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Veículo não existe!!!"));	
		}
		
		
	}
	public void criarVeiculo(){
		Veiculo novo = new Veiculo();
		novo.setNome(this.txtNomeVeic);
		novo.setMarca(this.txtMarcaVeic);
		novo.setTipo(this.txtTipoVeic);
		boolean achou = false;
		this.listaVeiculos = this.veiculoDAO.listarTodos();
		for (Veiculo veiculoPesquisa : listaVeiculos) {
			if (veiculoPesquisa.getNome().equals(this.txtNomeVeic) &&
					veiculoPesquisa.getMarca().equals(this.txtMarcaVeic) &&
					veiculoPesquisa.getTipo().equals(this.txtTipoVeic)) {
				achou = true;
			}
		}
		for (Veiculo veiculoPesquisa : listaVeiculos) {
			if (veiculoPesquisa.getNome().equals(this.veiculo.getNome())) {
				achou = true;
			}
		}
		if(achou) {
			FacesContext.getCurrentInstance()
				.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Veículo já existe!!!"));
		}else {
			this.veiculoDAO.inserir(novo);
			this.txtNomeVeic = "";
			this.txtMarcaVeic = "";
			this.txtTipoVeic = "";
			//FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
		}
	}
	
	public void criarUsuario() {
		Usuario novo = new Usuario();
		novo.setNome(this.usuario.getNome());
		novo.setEmail(this.usuario.getEmail());
		novo.setSenha(this.usuario.getSenha());
		boolean achou = false;
		this.listaUsuarios = this.usuarioDAO.listarTodos();
		for (Usuario usuarioPesquisa : listaUsuarios) {
			if (usuarioPesquisa.getEmail().equals(this.usuario.getEmail())) {
				achou = true;
			}
		}
		if(achou) {
			FacesContext.getCurrentInstance()
				.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Usuário já existe!!!"));
		}else {
			this.usuarioDAO.inserir(novo);
			this.usuario = new Usuario();	
		}
	}


	public List<Veiculo> getListaVeiculos() {
		return listaVeiculos;
	}

	public void setListaVeiculos(List<Veiculo> listaVeiculos) {
		this.listaVeiculos = listaVeiculos;
	}

	public Veiculo getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}

	public VeiculoDAO getVeiculoDAO() {
		return veiculoDAO;
	}

	public void setVeiculoDAO(VeiculoDAO veiculoDAO) {
		this.veiculoDAO = veiculoDAO;
	}

	public String getTxtEmail() {
		return txtEmail;
	}

	public void setTxtEmail(String txtEmail) {
		this.txtEmail = txtEmail;
	}

	public UsuarioDAO getUsuarioDAO() {
		return usuarioDAO;
	}

	public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}

	public String getTxtSenha() {
		return txtSenha;
	}

	public void setTxtSenha(String txtSenha) {
		this.txtSenha = txtSenha;
	}

	public List<Usuario> getListaUsuarios() {
		return listaUsuarios;
	}

	public void setListaUsuarios(List<Usuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getMsgCadastroUsuario() {
		return msgCadastroUsuario;
	}

	public void setMsgCadastroUsuario(String msgCadastroUsuario) {
		this.msgCadastroUsuario = msgCadastroUsuario;
	}
	

	public String getTxtNomeVeic() {
		return txtNomeVeic;
	}

	public void setTxtNomeVeic(String txtNomeVeic) {
		this.txtNomeVeic = txtNomeVeic;
	}

	public String getTxtMarcaVeic() {
		return txtMarcaVeic;
	}

	public void setTxtMarcaVeic(String txtMarcaVeic) {
		this.txtMarcaVeic = txtMarcaVeic;
	}

	public String getTxtTipoVeic() {
		return txtTipoVeic;
	}

	public void setTxtTipoVeic(String txtTipoVeic) {
		this.txtTipoVeic = txtTipoVeic;
	}
}
