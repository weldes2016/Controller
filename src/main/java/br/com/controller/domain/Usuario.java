package br.com.controller.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Usuario")
public class Usuario implements Serializable {

	private static final long serialVersionUID = -1474786549015163108L;

	@Id
	@GeneratedValue
	private int codigo;

	@Column
	private String nome;

	@Column
	private String celular;

	@Column
	private String email;

	@Column
	private String login;

	@Column
	private String senha;

	private String depto;

	@Column
	private String ultimaempresa;
	
	@Column
	private Date ultimoAcesso;
	
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
	private List<UserEmp> empresas;
	
	private Empresas empresa;

	public List<UserEmp> getEmpresas() {
		return empresas;
	}

	public void setEmpresas(List<UserEmp> empresas) {
		this.empresas = empresas;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getDepto() {
		return depto;
	}

	public void setDepto(String depto) {
		this.depto = depto;
	}

	public String getUltimaempresa() {
		return ultimaempresa;
	}

	public void setUltimaempresa(String ultimaempresa) {
		this.ultimaempresa = ultimaempresa;
	}
	
	

	public Date getUltimoAcesso() {
		return ultimoAcesso;
	}

	public void setUltimoAcesso(Date ultimoAcesso) {
		this.ultimoAcesso = ultimoAcesso;
	}

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((celular == null) ? 0 : celular.hashCode());
		result = prime * result + codigo;
		result = prime * result + ((depto == null) ? 0 : depto.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((empresas == null) ? 0 : empresas.hashCode());
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((senha == null) ? 0 : senha.hashCode());
		result = prime * result + ((ultimaempresa == null) ? 0 : ultimaempresa.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (celular == null) {
			if (other.celular != null)
				return false;
		} else if (!celular.equals(other.celular))
			return false;
		if (codigo != other.codigo)
			return false;
		if (depto == null) {
			if (other.depto != null)
				return false;
		} else if (!depto.equals(other.depto))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (empresas == null) {
			if (other.empresas != null)
				return false;
		} else if (!empresas.equals(other.empresas))
			return false;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (senha == null) {
			if (other.senha != null)
				return false;
		} else if (!senha.equals(other.senha))
			return false;
		if (ultimaempresa == null) {
			if (other.ultimaempresa != null)
				return false;
		} else if (!ultimaempresa.equals(other.ultimaempresa))
			return false;
		return true;
	}

	public Empresas getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresas empresa) {
		this.empresa = empresa;
	}

	
}
