package br.com.controller.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table (name = "Chamados")
public class Chamados extends GenericDomain{

	private static final long serialVersionUID = -8709799634562520489L;
	
	@Column
	private String descricao;
	private Date dataChamado;
	private Date dataSolucao;
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Date getDataChamado() {
		return dataChamado;
	}
	public void setDataChamado(Date dataChamado) {
		this.dataChamado = dataChamado;
	}
	public Date getDataSolucao() {
		return dataSolucao;
	}
	public void setDataSolucao(Date dataSolucao) {
		this.dataSolucao = dataSolucao;
	}
	
	
	
	

}
