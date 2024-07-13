package br.com.controller.bean;

import java.io.Serializable;
import java.sql.SQLException;
import java.text.NumberFormat;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.controller.dao.DuppagDAO;
import br.com.controller.domain.Duppag;

@Named(value = "MBDuppag")
@ViewScoped
public class DupPagBean implements Serializable {

	private static final long serialVersionUID = -1213665297418217282L;

	private DuppagDAO dao = new DuppagDAO();
	private Duppag duppag = new Duppag();
	private Double valorPagarTotal;
	
	String vazia = "";

	public String pagarSocios(String codigo, String codcliente) throws ClassNotFoundException, SQLException {
		Double valorPagar = dao.pagarSocios(codigo, codcliente).getVlrpgr();
		if (valorPagar != null) {
			return NumberFormat.getCurrencyInstance().format(valorPagar);
		} else {
			return "Não tem";
		}
	}

	public String pagoSocios(String codigo, String codcliente) throws ClassNotFoundException, SQLException {
		Double valorPago = dao.pagarSocios(codigo, codcliente).getVlrpgo();
		if (valorPago != null) {
			return NumberFormat.getCurrencyInstance().format(valorPago);
		} else {
			return "Sem informacao";
		}
	}
	
	public String pgtoSociosMes(String codigo, String codCliente) throws ClassNotFoundException, SQLException {
		Double  vlrPago = dao.pgtoSocios(codigo, codCliente).getVlrpgo();
		System.out.println("Valor Pago: "+vlrPago);
		if (vlrPago != null) {
			return NumberFormat.getCurrencyInstance().format(vlrPago);			
		}else {
			return "Sem Informação";
		}
		
	}
	
	public String aPagarSocioMes(String codigo, String codCliente) throws ClassNotFoundException, SQLException {
		Double  vlrPagar = dao.pgtoSocios(codigo, codCliente).getVlrpgr();
		if (vlrPagar != null) {
			return NumberFormat.getCurrencyInstance().format(vlrPagar);			
		}else {
			return "Sem Informação";
		}
		
	}

	public String restante(String codigo, String codCliente) throws ClassNotFoundException, SQLException {
		Double  vlrRestante = dao.pgtoSocios(codigo, codCliente).getDiferenca();
		if (vlrRestante != null) {
			return NumberFormat.getCurrencyInstance().format(vlrRestante);			
		}else {
			return "Sem Informação";
		}
		
	}
	
	public String diferencaSocios(String codigo, String codcliente) throws ClassNotFoundException, SQLException {
		Double diferenca = dao.pagarSocios(codigo, codcliente).getDiferenca();
		if (diferenca != null) {
			return NumberFormat.getCurrencyInstance().format(diferenca);
		} else {
			return "Sem informacao";
		}
	}

	public String nomeSocios(String codigo, String codcliente) throws ClassNotFoundException, SQLException {
		String nome = dao.pagarSocios(codigo, codcliente).getNom();

		if (nome != null) {
			return nome;
		} else {
			return "Ana Paula Alves Pimenta";
		}
	}

	public DuppagDAO getDao() {
		return dao;
	}

	public void setDao(DuppagDAO dao) {
		this.dao = dao;
	}

	public Duppag getDuppag() {
		return duppag;
	}

	public void setDuppag(Duppag duppag) {
		this.duppag = duppag;
	}
	

	public String getVazia() {
		return vazia;
	}

	public void setVazia(String vazia) {
		this.vazia = vazia;
	}

	public String somaSocioAnaPaula() throws ClassNotFoundException, SQLException {
		
		valorPagarTotal = dao.pagarSocios("1000", "01720865159").getVlrpgr();
		
		if (valorPagarTotal != null) {
			return NumberFormat.getCurrencyInstance().format(valorPagarTotal);
		} else {
			return "Não tem";
		}
		
	}
}