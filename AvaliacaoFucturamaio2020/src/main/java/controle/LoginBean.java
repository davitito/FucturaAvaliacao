package controle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import dao.UsuarioDAO;
import dao.UsuarioDAOImpl;
import dao.VeiculoDAO;
import dao.VeiculoDAOImpl;
import dao.PecaDAO;
import dao.PecaDAOImpl;
import entidade.Usuario;
import entidade.Veiculo;
import entidade.Peca;


@ManagedBean(name = "LoginBean")
@SessionScoped
public class LoginBean {
	
	//Variáveis do login.xhtml
	private String txtEmail;
	private String txtSenha;
	
	private Boolean emailValido;

	//Variáveis do veiculos.xhtml
	private String txtNomeVeic;
	private String txtMarcaVeic;
	private String txtTipoVeic;
	
	//Variáveis de peças.xhtml
	private String txtNomePeca;
	private String txtDescricaoPeca;
	

	private List<Usuario> listaUsuarios;
	private Usuario usuario;
	private String msgCadastroUsuario;
	
	private UsuarioDAO usuarioDAO;
	
	private List<Veiculo> listaVeiculos;
	private Veiculo veiculo;
	
	private VeiculoDAO veiculoDAO;

	private List<Peca> listaPecas;
	private Peca peca;
	
	private PecaDAO pecaDAO;
	
	public LoginBean() {
		this.listaUsuarios = new ArrayList<Usuario>();
		this.usuario = new Usuario();
		this.usuarioDAO = new UsuarioDAOImpl();
		
		this.listaVeiculos = new ArrayList<Veiculo>();
		this.veiculo = new Veiculo();
		this.veiculoDAO = new VeiculoDAOImpl();
		
		this.listaPecas = new ArrayList<Peca>();
		this.peca = new Peca();
		this.pecaDAO = new PecaDAOImpl();
	}

	public void entrar() throws IOException {
		boolean achou = false;
		this.listaUsuarios = this.usuarioDAO.listarTodos();
		
		emailValido = validarEmail(this.txtEmail);
		if (emailValido) {
			for (Usuario usuarioPesquisa : listaUsuarios) {
				if (usuarioPesquisa.getEmail().equals(this.txtEmail) && usuarioPesquisa.getSenha().equals(this.txtSenha)) {
					achou = true;
				}
			}
			if (achou) {
				FacesContext.getCurrentInstance().getExternalContext().redirect("veiculos.xhtml");			
			} else {
				//FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
				FacesContext.getCurrentInstance()
				.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção!", "Usuário não cadastrado ou senha inválida!!!"));
			}
		}else {
			FacesContext.getCurrentInstance()
			.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção!", "Digite um e-mail válido!!!"));
		}
	}
	
	public static boolean validarEmail(String email) {
		boolean emailValido = false;
			if (email != null && email.length() > 0) {
		        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
		        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
		        Matcher matcher = pattern.matcher(email);
		    	if (matcher.matches()) {
		    		emailValido = true;
		    	}
			}
		return emailValido;
	}

	public void cadastrarPecas() {
		System.out.println("cadastrar pecas "+this.txtNomePeca + " "+this.txtDescricaoPeca + " "+this.txtNomeVeic);
		Peca novo = new Peca();
		novo.setNome(this.txtNomePeca);
		novo.setDescricao(this.txtDescricaoPeca);
		novo.setNome_veiculo(this.txtNomeVeic);
		boolean achou = false;
		this.listaPecas = this.pecaDAO.listarTodos();		
		for (Peca pecaPesquisa : listaPecas) {
			if (pecaPesquisa.getNome().equals(this.txtNomePeca) &&
					pecaPesquisa.getDescricao().equals(this.txtDescricaoPeca)) {
				achou = true;
			}
		}
		if(achou) {
			FacesContext.getCurrentInstance()
				.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção!", "Peça já existe!!!"));
		}else {
			this.pecaDAO.inserir(novo);
			this.txtNomePeca = "";
			this.txtDescricaoPeca = "";
		}
	}
	
	public void sairPecas() throws IOException {
		this.txtNomeVeic = "";
		FacesContext.getCurrentInstance().getExternalContext().redirect("veiculos.xhtml");
	}
	
	public void sairVeiculo() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
	}
	
	public void pesquisarVeiculo() throws IOException {
		veiculo = veiculoDAO.pesquisar(this.txtNomeVeic);
		boolean achou = false;
		if (veiculo !=null) {
			achou = true;
		}
		if (achou) {
			FacesContext.getCurrentInstance().getExternalContext().redirect("pecas.xhtml");			
		} else {
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
		if(achou) {
			FacesContext.getCurrentInstance()
				.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção!", "Veículo já existe!!!"));
		}else {
			this.veiculoDAO.inserir(novo);
			this.txtNomeVeic = "";
			this.txtMarcaVeic = "";
			this.txtTipoVeic = "";
		}
	}
	
	public void criarUsuario() throws IOException {
		emailValido = validarEmail(this.usuario.getEmail());
		if (emailValido) {
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
					.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção!", "E-mail já cadastrado, tente outro!!!"));
			}else {
				this.usuarioDAO.inserir(novo);
				this.usuario = new Usuario();
				FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
			}
		}else {
			FacesContext.getCurrentInstance()
			.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção!", "Digite um e-mail válido!!!"));
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
	
	public String getTxtNomePeca() {
		return txtNomePeca;
	}

	public void setTxtNomePeca(String txtNomePeca) {
		this.txtNomePeca = txtNomePeca;
	}

	public String getTxtDescricaoPeca() {
		return txtDescricaoPeca;
	}

	public void setTxtDescricaoPeca(String txtDescricaoPeca) {
		this.txtDescricaoPeca = txtDescricaoPeca;
	}

	public List<Peca> getListaPecas() {
		return listaPecas;
	}

	public void setListaPecas(List<Peca> listapecas) {
		this.listaPecas = listapecas;
	}

	public Peca getPeca() {
		return peca;
	}

	public void setPeca(Peca peca) {
		this.peca = peca;
	}

	public PecaDAO getPecaDAO() {
		return pecaDAO;
	}

	public void setPecaDAO(PecaDAO pecaDAO) {
		this.pecaDAO = pecaDAO;
	}
	
	public Boolean getEmailValido() {
		return emailValido;
	}

	public void setEmailValido(Boolean emailValido) {
		this.emailValido = emailValido;
	}
}
