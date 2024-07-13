package br.com.controller.bean;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import br.com.controller.dao.EmpresasDAO;
import br.com.controller.domain.Empresas;

@Named(value = "MBEmpresas")
@SessionScoped
public class EmpresasBean implements Serializable {

	private static final long serialVersionUID = 4980918402613572560L;

	private List<Empresas> empresas;
	private List<Empresas> empresasPorUsuario;
	private Empresas empresaSelecionada = new Empresas();
	Empresas empresa = new Empresas();
	private String base64Image;
	private EmpresasDAO dao = new EmpresasDAO();

	

	@PostConstruct
	public void init() {
		try {
			empresas = dao.listarEmpresas();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			addMessage(FacesMessage.SEVERITY_ERROR, "Error", "Unable to load companies");
		}
	}

	public List<Empresas> getEmpresas() {
		return empresas;
	}

	public void setEmpresaSelecionada(Empresas empresaSelecionada) {
		this.empresaSelecionada = empresaSelecionada;
		
	}

	public Empresas getEmpresaSelecionada() {
		return empresaSelecionada;
	}

	public List<Empresas> getEmpresasPorUsuario() {
		return empresasPorUsuario;
	}

	public void setEmpresasPorUsuario(List<Empresas> empresasPorUsuario) {
		this.empresasPorUsuario = empresasPorUsuario;
	}

	public void selecionarEmpresa(Empresas empresa) {
		this.empresaSelecionada = empresa;
		if (empresa != null) {
			this.base64Image = empresa.getImgLogo();
			System.out.println("Seleciona Empresa:" + base64Image);
		}
	}

	public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
	}

	public Empresas trocarEmpresa(Empresas empresa) throws SQLException, ClassNotFoundException, IOException {
		if (empresa != null) {
			empresa = dao.buscarEmpresaPorId(empresa.getUsu());
			if (empresa != null) {
			
				System.out.println(empresa.getImgLogo());
				
			}
		}
		return empresa;
	}

	public List<Empresas> listarEmpresasPorUsuario(int id_codusuario) throws ClassNotFoundException, SQLException {
		empresas = dao.listarEmpresasPorUsuario(id_codusuario);
		return empresas;
	}

	public String getBase64Image() {
		return base64Image;
	}

	public void setBase64Image(String base64Image) {
		this.base64Image = base64Image;
	}

	public byte[] getBytes() {
		return Base64.getDecoder().decode(base64Image);
	}

}
