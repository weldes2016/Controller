package br.com.controller.bean;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import br.com.controller.dao.EmpresasDAO;
import br.com.controller.dao.UsuarioDAO;
import br.com.controller.domain.Empresas;
import br.com.controller.domain.Usuario;
import br.com.controller.util.Message;


@Named(value = "MBUsuarios")
@SessionScoped
public class UsuarioBean implements Serializable {

	private static final long serialVersionUID = 4821336997974538276L;
	private Usuario usuario = new Usuario();
	private UsuarioDAO dao = new UsuarioDAO();
	private Usuario usuarioLogado = new Usuario();
	private EmpresasDAO daoEmpresas = new EmpresasDAO();
	private Empresas empresaSelecionada = new Empresas();
	private Message mensagem = new Message();
	private List<Empresas> empresas = new ArrayList<Empresas>();
	private List<Empresas> empresasDoUsuario = new ArrayList<Empresas>();
	private String color;
	private String color2;
	Boolean renderizarEmpresas = false;
	Boolean renderizarUsuarios = true;
	
		
	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	@PostConstruct
	public void init() {
		usuario = new Usuario(); // Inicialize aqui
		empresaSelecionada = null; // ou defina um valor padrão válido
		try {
			empresas = daoEmpresas.listarEmpresas();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getColor2() {
		return color2;
	}

	public void setColor2(String color2) {
		this.color2 = color2;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public UsuarioDAO getDao() {
		return dao;
	}

	public void setDao(UsuarioDAO dao) {
		this.dao = dao;
	}

	public EmpresasDAO getDaoEmpresas() {
		return daoEmpresas;
	}

	public void setDaoEmpresas(EmpresasDAO daoEmpresas) {
		this.daoEmpresas = daoEmpresas;
	}

	public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
	}

	public String validarUsuario(Usuario us) throws ClassNotFoundException, SQLException {
		System.out.println(us.getLogin() + " - " + us.getSenha());
		usuario = dao.validarUsuarioDirectus(us);

		if (usuario != null) {
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuarioLogado", usuario);
			usuarioLogado = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("usuarioLogado");
			System.out.println("Codigo do usuario: " + usuarioLogado.getCodigo());

			empresasDoUsuario = daoEmpresas.listarEmpresasPorUsuario(usuarioLogado.getCodigo());

			if (renderizarEmpresas != true) {
				renderizarEmpresas = true;
				renderizarUsuarios = false;
			}

			return "login.xhtml";

		} else {
			return "access.xhtml";
		}
	}

	public String entrar(Usuario usuarioLogado, Empresas empresaSelecionada) {
		usuarioLogado.setEmpresa(empresaSelecionada);
		
		System.out.println(usuarioLogado.getNome() + ", Empresa Selecionada de dentro do usuario: "
				+ usuarioLogado.getEmpresa().getImgLogo());
		return "dashboard.xhtml";
	}

	
	public void trocarEmpresa(Empresas empresaSelecionada) {
        try {
            Empresas empresa = daoEmpresas.trocarEmpresa(empresaSelecionada.getUsu());
            System.err.println("Nome da Empresa: "+empresa.getNom());
            usuarioLogado.setEmpresa(empresa);
            System.out.println("Código Empresa Selecionada: " + usuarioLogado.getEmpresa().getUsu());
            System.err.println("Logo em String: "+ usuarioLogado.getEmpresa().getImgLogo());
         
        } catch (ClassNotFoundException | SQLException | IOException e) {
            e.printStackTrace();
            // Adicionar tratamento de erro adequado
        }
    }

	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();

		try {
			// Redireciona o usuário para a página de login.xhtml após o logout
			FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
		} catch (IOException e) {
			e.printStackTrace(); // Trate exceções de redirecionamento, se necessário
		}

		// Retorna null ou uma página de redirecionamento (caso deseje)
		return null;
	}

	public Empresas getEmpresaSelecionada() {
		return empresaSelecionada;
	}

	public void setEmpresaSelecionada(Empresas empresaSelecionada) {
		this.empresaSelecionada = empresaSelecionada;
	}

	public Message getMensagem() {
		return mensagem;
	}

	public void setMensagem(Message mensagem) {
		this.mensagem = mensagem;
	}

	public List<Empresas> getEmpresas() {
		return empresas;
	}

	public void setEmpresas(List<Empresas> empresas) {
		this.empresas = empresas;
	}

	public Empresas selecionaEmpresa(Empresas empresa) throws SQLException, ClassNotFoundException, IOException {
		empresaSelecionada = daoEmpresas.trocarEmpresa(empresa.getUsu());

		return empresaSelecionada;
	}

	public Boolean getRenderizarEmpresas() {
		return renderizarEmpresas;
	}

	public void setRenderizarEmpresas(Boolean renderizarEmpresas) {
		this.renderizarEmpresas = renderizarEmpresas;
	}

	public Boolean getRenderizarUsuarios() {
		return renderizarUsuarios;
	}

	public void setRenderizarUsuarios(Boolean renderizarUsuarios) {
		this.renderizarUsuarios = renderizarUsuarios;
	}

	public List<Empresas> getEmpresasDoUsuario() {
		return empresasDoUsuario;
	}

	public void setEmpresasDoUsuario(List<Empresas> empresasDoUsuario) {
		this.empresasDoUsuario = empresasDoUsuario;
	}
}
