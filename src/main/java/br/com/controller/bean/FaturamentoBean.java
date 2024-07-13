package br.com.controller.bean;

import java.io.Serializable;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import br.com.controller.dao.FaturamentoDAO;
import br.com.controller.util.ArquivoUtil;

@Named (value = "MBFaturamento")
@SessionScoped
public class FaturamentoBean implements Serializable{
	
	private static final long serialVersionUID = 3611869791989372621L;
	
	private FaturamentoDAO dao = new FaturamentoDAO();
	private ArquivoUtil util = new ArquivoUtil();
	public ArquivoUtil getUtil() {
		return util;
	}

	public void setUtil(ArquivoUtil util) {
		this.util = util;
	}

	public int getAnoAnterior() {
		return anoAnterior;
	}

	public void setAnoAnterior(int anoAnterior) {
		this.anoAnterior = anoAnterior;
	}

	public String getMesAtual() {
		return mesAtual;
	}

	public void setMesAtual(String mesAtual) {
		this.mesAtual = mesAtual;
	}

	private int qtdeMes = 0;
	private int qtdeDia = 0;
	private int qtdeAno = 0;
	
	private double percentualRecebimento;
	private double percentualFaturamento;
	private double percentualFaturamentoNoMesNoAnoAnterior;
	private double percentualFaturamentoNoAnoAnterior;
	
	DecimalFormat df = new DecimalFormat("#,###.00");
	
	private int anoAnterior = 0;
	private String mesAtual = "";
	private int anoAtual = 0;
	
	
	
	
	public double getPercentualFaturamentoNoAnoAnterior() {
		return percentualFaturamentoNoAnoAnterior;
	}

	public void setPercentualFaturamentoNoAnoAnterior(double percentualFaturamentoNoAnoAnterior) {
		this.percentualFaturamentoNoAnoAnterior = percentualFaturamentoNoAnoAnterior;
	}

	public DecimalFormat getDf() {
		return df;
	}

	public void setDf(DecimalFormat df) {
		this.df = df;
	}

	public int getQtdeMes() {
		return qtdeMes;
	}

	public void setQtdeMes(int qtdeMes) {
		this.qtdeMes = qtdeMes;
	}

	public int getQtdeDia() {
		return qtdeDia;
	}

	public void setQtdeDia(int qtdeDia) {
		this.qtdeDia = qtdeDia;
	}

	public int getQtdeAno() {
		return qtdeAno;
	}

	public void setQtdeAno(int qtdeAno) {
		this.qtdeAno = qtdeAno;
	}

	public FaturamentoDAO getDao() {
		return dao;
	}

	public void setDao(FaturamentoDAO dao) {
		this.dao = dao;
	}

	public double getPercentualRecebimento() {
		return percentualRecebimento;
	}

	public void setPercentualRecebimento(double percentualRecebimento) {
		this.percentualRecebimento = percentualRecebimento;
	}

	public double getPercentualFaturamento() {
		return percentualFaturamento;
	}

	public void setPercentualFaturamento(double percentualFaturamento) {
		this.percentualFaturamento = percentualFaturamento;
	}
	
	@PostConstruct
	public void init() {
		anoAnterior = util.anoAnterior();
		anoAtual = util.anoAtual();
		mesAtual = util.mesCorrente();
		
	}
	

	public double getPercentualFaturamentoNoMesNoAnoAnterior() {
		return percentualFaturamentoNoMesNoAnoAnterior;
	}

	public void setPercentualFaturamentoNoMesNoAnoAnterior(double percentualFaturamentoNoMesNoAnoAnterior) {
		this.percentualFaturamentoNoMesNoAnoAnterior = percentualFaturamentoNoMesNoAnoAnterior;
	}

	
	public String mostrarPercentualRecebimento(String codigo) throws ClassNotFoundException, SQLException {
		double recebidos = dao.mostrarRecebidosMesAtual(codigo);
		double vencidos = dao.mostrarVencimentoMesAtual(codigo);
		
		percentualRecebimento = ((recebidos/vencidos));
		percentualRecebimento = percentualRecebimento % 10 ;
		
		return NumberFormat.getPercentInstance().format(percentualRecebimento) ;
	}
	
	public String mostrarFaturamentoMesAtual(String codigo) throws ClassNotFoundException, SQLException{
		return NumberFormat.getCurrencyInstance().format(dao.listarFaturamentoMesAtual(codigo));
		
	}
	public String mostrarPercentualFaturamento(String codigo) throws ClassNotFoundException, SQLException {
		double faturadosMes = dao.listarFaturamentoMesAtual(codigo);
		double faturadosMesAnterior = dao.mostrarFaturadosNoMesAnterior(codigo);
		
		percentualFaturamento = ((faturadosMes/faturadosMesAnterior));
		percentualFaturamento = percentualFaturamento % 10 ;
		
		return NumberFormat.getPercentInstance().format(percentualFaturamento) ;
	}
	
	public String mostrarRecebidosMesAtual(String codigo) throws ClassNotFoundException, SQLException {
		return NumberFormat.getCurrencyInstance().format(dao.mostrarRecebidosMesAtual(codigo));
		
	}
	
	public String mostrarVencimentoMesAtual(String codigo) throws ClassNotFoundException, SQLException {
		return NumberFormat.getCurrencyInstance().format(dao.mostrarVencimentoMesAtual(codigo));
		
	}
	
	
	public String mostrarFaturamentoMesAnterior(String codigo) throws ClassNotFoundException, SQLException {
		return NumberFormat.getCurrencyInstance().format(dao.mostrarFaturadosNoMesAnterior(codigo));
		
	}
	
	public String mostrarPercentualFaturamentoNoMesNoAnoAnterior(String codigo) throws ClassNotFoundException, SQLException {
		double faturadosMes = dao.listarFaturamentoMesAtual(codigo);
		double faturadosMesNoAnoAnterior = dao.mostrarFaturadosNoMesNoAnoAnterior(codigo);
		
		percentualFaturamentoNoMesNoAnoAnterior = ((faturadosMes/faturadosMesNoAnoAnterior));
		percentualFaturamentoNoMesNoAnoAnterior = percentualFaturamentoNoMesNoAnoAnterior % 10 ;
		
		return NumberFormat.getPercentInstance().format(percentualFaturamentoNoMesNoAnoAnterior) ;
	}
	
	public String mostrarFaturamentoAnoAnterior(String codigo) throws ClassNotFoundException, SQLException{
		return NumberFormat.getCurrencyInstance().format(dao.mostrarFaturamentoAnoAnterior(codigo));
	}
	
	public String mostrarFaturamentoAnoAtual(String codigo) throws ClassNotFoundException, SQLException{
		return NumberFormat.getCurrencyInstance().format(dao.listarFaturamentoAnoAtual(codigo));
	}
	
	public String mostrarPercentualFaturamentoNoAnoAnterior(String codigo) throws ClassNotFoundException, SQLException {
		double faturadosAno = dao.listarFaturamentoAnoAtual(codigo);
		double faturadosAnoAnterior = dao.mostrarFaturamentoAnoAnterior(codigo);
		
		percentualFaturamentoNoAnoAnterior = (faturadosAno/faturadosAnoAnterior);
		percentualFaturamentoNoAnoAnterior = percentualFaturamentoNoAnoAnterior % 10 ;
		
		return NumberFormat.getPercentInstance().format(percentualFaturamentoNoAnoAnterior) ;
	}
	
	public String mostrarFaturamentoNoMesAnoAnterior(String codigo) throws ClassNotFoundException, SQLException {
		return NumberFormat.getCurrencyInstance().format(dao.mostrarFaturadosNoMesNoAnoAnterior(codigo));
	}
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		FaturamentoBean bean = new FaturamentoBean();
		System.err.println(bean.mostrarFaturamentoAnoAnterior("9000"));
		System.out.println(bean.mostrarFaturamentoMesAtual("9000"));
	}

	public int getAnoAtual() {
		return anoAtual;
	}

	public void setAnoAtual(int anoAtual) {
		this.anoAtual = anoAtual;
	}
}
	