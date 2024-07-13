package br.com.controller.bean;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;

import br.com.controller.dao.CertificadoDAO;
import br.com.controller.domain.Certificados;
import br.com.controller.util.ValidaCertificado;

@Named(value = "MBCertificados")
@SessionScoped
public class CertificadosBean implements Serializable {

	private static final long serialVersionUID = -4368999307954685513L;

	private Certificados certificado = new Certificados();
	private List<Certificados> certificados = new ArrayList<>();

	private List<Certificados> certificadosSelecionados = new ArrayList<>();
	
	
	private UploadedFile file;
	private boolean certificadoValido = false;
	private String senha;
	private boolean rendered = false;
	private boolean arquivoEnviado = false;
	
	public boolean isArquivoEnviado() {
	    return arquivoEnviado;
	}

	public void setArquivoEnviado(boolean arquivoEnviado) {
	    this.arquivoEnviado = arquivoEnviado;
	}

	public Certificados getCertificado() {
		return certificado;
	}

	public void setCertificado(Certificados certificado) {
		this.certificado = certificado;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public boolean isCertificadoValido() {
		return certificadoValido;
	}

	
	public void validarCertificado() {
	    FacesContext context = FacesContext.getCurrentInstance();
	    try {
	        if (file != null) {
	            InputStream inputStream = file.getInputStream();
	            certificado = ValidaCertificado.lerCertificado(inputStream, senha);
	            if (certificado != null) {
	                rendered = true;
	            } else {
	                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Certificado inválido ou vazio"));
	                rendered = false;
	            }
	        } else {
	            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Arquivo não fornecido"));
	            rendered = false;
	        }
	    } catch (Exception e) {
	        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Ocorreu um erro ao validar o certificado"));
	        e.printStackTrace();
	        rendered = false;
	    }
	}
	
	public List<Certificados> listarCertificados(){
		return CertificadoDAO.findAll(Certificados.class);
	}


	
	public void handleFileUpload(FileUploadEvent event) {
	    this.file = event.getFile();
	    if (file != null) {
	        arquivoEnviado = true;
	    }
	    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", file.getFileName() + " foi enviado com sucesso.");
	    FacesContext.getCurrentInstance().addMessage(null, message);
	}
	
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public boolean isRendered() {
		return rendered;
	}

	public void setRendered(boolean rendered) {
		this.rendered = rendered;
	}

	public List<Certificados> getCertificados() {
		return certificados;
	}

	public void setCertificados(List<Certificados> certificados) {
		this.certificados = certificados;
	}

	public List<Certificados> getCertificadosSelecionados() {
		return certificadosSelecionados;
	}

	public void setCertificadosSelecionados(List<Certificados> certificadosSelecionados) {
		this.certificadosSelecionados = certificadosSelecionados;
	}
}
