package br.com.controller.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "certificados")
public class Certificados extends GenericDomain implements Serializable {

    private static final long serialVersionUID = -6099909437912645669L;

    @Column(length = 50)
    private String alias;

    @Column(length = 80)
    private String emissor;

    @Column(length = 255)
    private String sujeito;

    @Column
    private Date valInicio;

    @Column(name = "valFim")
    private Date valFim;

    @Column(length = 20)
    private String cnpj;

    @Column(length = 80)
    private String razaoSocial;

    @Column(length = 2)
    private String tipoCertificado;

    // Getters and Setters
    
    

    public boolean isEmpty() {
        return (emissor == null || emissor.isEmpty()) &&
               (sujeito == null || sujeito.isEmpty()) &&
               valInicio == null &&
               valFim == null &&
               (cnpj == null || cnpj.isEmpty()) &&
               (alias == null || alias.isEmpty()) &&
               (razaoSocial == null || razaoSocial.isEmpty());
    }

    public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getEmissor() {
		return emissor;
	}

	public void setEmissor(String emissor) {
		this.emissor = emissor;
	}

	public String getSujeito() {
		return sujeito;
	}

	public void setSujeito(String sujeito) {
		this.sujeito = sujeito;
	}

	public Date getValInicio() {
		return valInicio;
	}

	public void setValInicio(Date valInicio) {
		this.valInicio = valInicio;
	}

	public Date getValFim() {
		return valFim;
	}

	public void setValFim(Date valFim) {
		this.valFim = valFim;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getTipoCertificado() {
		return tipoCertificado;
	}

	public void setTipoCertificado(String tipoCertificado) {
		this.tipoCertificado = tipoCertificado;
	}

	@Override
    public String toString() {
        return "Certificados{" +
               "alias='" + alias + '\'' +
               ", emissor='" + emissor + '\'' +
               ", sujeito='" + sujeito + '\'' +
               ", valInicio=" + valInicio +
               ", valFim=" + valFim +
               ", cnpj='" + cnpj + '\'' +
               ", razaoSocial='" + razaoSocial + '\'' +
               ", tipoCertificado='" + tipoCertificado + '\'' +
               '}';
    }
}
