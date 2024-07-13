package br.com.controller.domain;

import java.io.Serializable;
import java.util.Date;

public class Duppag implements Serializable {

	private static final long serialVersionUID = 7551224253147642534L;
	private int id_duppag;
	private String Numdup;
	private String Ordem;
	private String Codcli;
	private String Nom;
	private Date Emis;
	private Date Vcto;
	private Double Vlrpgr;
	private Double Vlrpgo;
	private Double Diferenca;
	private String vcto;
	private Double totalPagar;
	
	public int getId_duppag() {
		return id_duppag;
	}
	public void setId_duppag(int id_duppag) {
		this.id_duppag = id_duppag;
	}
	public String getNumdup() {
		return Numdup;
	}
	public void setNumdup(String numdup) {
		Numdup = numdup;
	}
	public String getOrdem() {
		return Ordem;
	}
	public void setOrdem(String ordem) {
		Ordem = ordem;
	}
	public String getCodcli() {
		return Codcli;
	}
	public void setCodcli(String codcli) {
		Codcli = codcli;
	}
	
	public String getNom() {
		return Nom;
	}
	public void setNom(String nom) {
		Nom = nom;
	}
	public Date getEmis() {
		return Emis;
	}
	public void setEmis(Date emis) {
		Emis = emis;
	}
	public Date getVcto() {
		return Vcto;
	}
	public void setVcto(Date vcto) {
		Vcto = vcto;
	}
	public Double getVlrpgr() {
		return Vlrpgr;
	}
	public void setVlrpgr(Double vlrpgr) {
		Vlrpgr = vlrpgr;
	}
	public Double getVlrpgo() {
		return Vlrpgo;
	}
	public void setVlrpgo(Double vlrpgo) {
		Vlrpgo = vlrpgo;
	}
	public Double getDiferenca() {
		return Diferenca;
	}
	public void setDiferenca(Double diferenca) {
		Diferenca = diferenca;
	}
	public Double getTotalPagar() {
		return totalPagar;
	}
	public void setTotalPagar(Double totalPagar) {
		this.totalPagar = totalPagar;
	}
	public void setVcto(String vcto) {
		this.vcto = vcto;
	}	

}
