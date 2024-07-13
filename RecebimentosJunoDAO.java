package br.com.controller.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import br.com.controller.domain.Banco;
import br.com.controller.domain.Clifor;
import br.com.controller.domain.Duprec;
import br.com.controller.domain.Empresas;
import br.com.controller.domain.MovBco;
import br.com.controller.domain.RecebimentosJuno;
import br.com.controller.factory.ConexaoFactory;

public class RecebimentosJunoDAO extends GenericDirectusDAO<RecebimentosJuno> implements Serializable {

	private static final long serialVersionUID = 1L;
	public RecebimentosJuno recebimentoJuno = new RecebimentosJuno();
	DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	public Clifor clifor = new Clifor();
	double registroBanco = 0;
	public int dia = 0;
	public int mes = 0;
	public int ano = 0;
	
	public Clifor getClifor() {
		return clifor;
	}

	public void setClifor(Clifor clifor) {
		this.clifor = clifor;
	}

	public double getRegistroBanco() {
		return registroBanco;
	}

	public void setRegistroBanco(double registroBanco) {
		this.registroBanco = registroBanco;
	}

	public DateFormat getSdf() {
		return sdf;
	}

	public void setSdf(DateFormat sdf) {
		this.sdf = sdf;
	}

	public int getDia() {
		return dia;
	}

	public void setDia(int dia) {
		this.dia = dia;
	}

	public int getMes() {
		return mes;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public RecebimentosJuno getRecebimentoJuno() {
		return recebimentoJuno;
	}

	public RecebimentosJuno getRecebimentoJuno(String codigo) {
		return recebimentoJuno;
	}

	public void setRecebimentoJuno(RecebimentosJuno recebimentoJuno) {
		this.recebimentoJuno = recebimentoJuno;
	}

	public Banco selecionaBanco(String codbanco, String codigo) throws SQLException, ClassNotFoundException {

		StringBuilder sql = new StringBuilder();
		sql.append("Select * from banco where codbco = ?");

		Connection conexao = ConexaoFactory.conectarMySql(codigo);

		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		comando.setString(1, codbanco);

		ResultSet resultado = comando.executeQuery();

		Banco banco = new Banco();

		if (resultado.next()) {
			banco.setCodBco(resultado.getString("codbco"));
			banco.setTokenJuno(resultado.getString("tokenjuno"));
			banco.setCtaPlano(resultado.getString("CtaPlano"));
		}
		return banco;
	}

	public Boolean buscaRecebimento(String code, String codigo) throws SQLException, ClassNotFoundException {

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM recebimentosjuno WHERE CODE = ?");

		Connection conexao = ConexaoFactory.conectarMySql(codigo);

		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		comando.setString(1, code);

		ResultSet resultado = comando.executeQuery();

		RecebimentosJuno rec = new RecebimentosJuno();

		if (resultado.next()) {
			return true;
		}else {
		return false;
		}
	}

	public List<RecebimentosJuno> listarBoletosRecebidos(String codigo) throws SQLException, ClassNotFoundException {
		List<RecebimentosJuno> recebimentos = new ArrayList<RecebimentosJuno>();

		StringBuilder sql = new StringBuilder();

		sql.append("select * from recebimentosjuno where empresa  = ? and baixado is null");

		Connection conexao = ConexaoFactory.conectarMySqlUsuariosDirectus();
		PreparedStatement comando = conexao.prepareStatement(sql.toString());

		comando.setString(1, codigo);

		ResultSet resultado = comando.executeQuery();

		while (resultado.next()) {
			RecebimentosJuno juno = new RecebimentosJuno();
			// juno.setBarCodeNumber(resultado.getString("barCodeNumber"));
			juno.setAmount(resultado.getBigDecimal("amount"));
			juno.setCode(resultado.getString("code"));
			juno.setDueDate(resultado.getDate("dueDate"));
			juno.setId(Integer.parseInt(resultado.getString("code")));
			juno.setPagamentoDate(resultado.getDate("pagamentoDate"));
			juno.setPayNumber(resultado.getString("payNumber"));
			juno.setDate(resultado.getDate("date"));
			recebimentos.add(juno);
		}
		return recebimentos;
	}

	public Duprec getRecebimentoJuno(String codigo, String id) throws SQLException, ClassNotFoundException {

		System.out.println(id);

		StringBuilder sql = new StringBuilder();
		sql.append("select * from duprec where nossonum = ?");

		Connection conexao = ConexaoFactory.conectarMySql(codigo);
		PreparedStatement comando = conexao.prepareStatement(sql.toString());

		comando.setString(1, id);

		ResultSet resultado = comando.executeQuery();

		if (resultado.next()) {
			Duprec dup = new Duprec();
			Banco banco = new Banco();
			banco.setCodBco(resultado.getString("banco"));
			dup.setBanco(banco);
			dup.setNf(resultado.getString("nf"));
			dup.setNumdup(resultado.getString("numdup"));
			dup.setOrdem(resultado.getString("ordem"));
			clifor.setCod(resultado.getString("codcli"));
			dup.setCodcli(clifor);
			return dup;
		}
		return null;
	}

	/*
	public void gerarMovBanco(RecebimentosJuno juno, String codigo, String usuario) throws SQLException, ParseException, ClassNotFoundException {
		System.out.println("Aqui esta dentro do DAO:" +juno.getCode() + " Data de pagamento: "+juno.getDueDate());
		DuprecDAO duprecDAO = new DuprecDAO();
		Duprec duprec = new Duprec();
		int registroJuros = 0;
				
		duprec = duprecDAO.buscaDuplicata(codigo, juno.getCode());
		
		if (juno.getAmount().compareTo(duprec.getValor()) == 0) {
			duprec.setValorPago(juno.getAmount());
			duprec.setDataPagamento(juno.getPagamentoDate());
			
			preparaMovBancoBaixaTitulo(juno, codigo, usuario);
			System.out.println(registroBanco + " este é o Registro do Banco");
			baixarDuplicatas(juno, duprec, codigo, registroBanco);
			
		}else {
			System.out.println("Valor Pago: "+juno.getAmount() + " Valor a Receber: "+duprec.getValor() + " 1");
			duprec.setValorPago(juno.getAmount());
			duprec.setJuros(juno.getAmount().subtract(duprec.getValor()));
			
			preparaMovBancoBaixaTitulo(juno, codigo, usuario);
			preparaMovBancoJuros(juno, codigo, usuario);
			baixarDuplicatas(juno, duprec, codigo, registroBanco);
		}
		
	}
	*/


	private void baixarDuplicatas(RecebimentosJuno juno, Duprec duprec, String codigo, double registroBanco) throws SQLException, ClassNotFoundException {
		DuprecDAO duprecDao = new DuprecDAO();
		System.out.println("Baixando Duplicata com o Registro Banco: "+registroBanco);
		System.out.println("Estamos baixando duplicatas: "+duprec.getNumdup() + " Valor: "+juno.getAmount());
		
		duprecDao.baixarDupRec(duprec, codigo, registroBanco);
		
				
	}

	private double preparaMovBancoBaixaTitulo(RecebimentosJuno juno, String codigo, String usuario) throws SQLException, ClassNotFoundException {		
		DuprecDAO duprecDAO = new DuprecDAO();
		CliforDAO daoClientes = new CliforDAO();
		EmpresasDAO daoEmpresas = new EmpresasDAO();
		Empresas emp = new Empresas();
		Duprec duprec = new Duprec();
		MovBco movBco = new MovBco();
		Banco banco = new Banco();
		String codBanco = "0020";
		banco = selecionaBanco(codBanco,codigo);
		
		duprec = duprecDAO.buscaDuplicata(codigo, juno.getCode());
		//duprec.setDataPagamento(java.sql.Date.valueOf(juno.getDueDate().toLocalDate()));
		dia = juno.getDueDate().getDate();
		mes = juno.getDueDate().getMonth()+1;
		ano = juno.getDueDate().getYear()+1900;
			
		
		movBco.setDuplc(duprec.getNumdup());
		movBco.setLetra(duprec.getOrdem());
		movBco.setVLR(juno.getAmount());
		movBco.setDia(dia);
		movBco.setMes(mes);
		movBco.setAno(ano);
		movBco.setDuplc(duprec.getNumdup());
		movBco.setLetra(duprec.getOrdem());
		movBco.setCRE(daoClientes.contaCliente(duprec.getCodcli().getCod(), codigo).getContacli());
		movBco.setDEV(banco.getCtaPlano());
		movBco.setUsuario(usuario);
		movBco.setGravacao(DateTime.now());
		movBco.setSIM("RC");
		movBco.setOpe("01");
		movBco.setCodCli(duprec.getCodcli().getCod());
		movBco.setBanco(banco.getCodBco());
		movBco.setHis("NFS-e "+movBco.getDuplc()+"-"+movBco.getLetra()+" - "+daoClientes.contaCliente(duprec.getCodcli().getCod(), codigo).getNom());
					
		salvarMovBanco(movBco, codigo);
		
		registroBanco = pegarRegistro(movBco.getAno(), codigo);
		
		return registroBanco;	
	}
	
	public double preparaMovBancoJuros(RecebimentosJuno juno, String codigo, String usuario) throws SQLException, ClassNotFoundException {

		DuprecDAO duprecDAO = new DuprecDAO();
		CliforDAO daoClientes = new CliforDAO();
		EmpresasDAO daoEmpresas = new EmpresasDAO();
		Empresas emp = new Empresas();
		Duprec duprec = new Duprec();
		MovBco movBco = new MovBco();
		Banco banco = new Banco();
		String codBanco = "0020";
		banco = selecionaBanco(codBanco,codigo);
		
		duprec = duprecDAO.buscaDuplicata(codigo, juno.getCode());
		//duprec.setDataPagamento(java.sql.Date.valueOf(juno.getDueDate().toLocalDate()));
		dia = juno.getPagamentoDate().getDate();
		mes = juno.getPagamentoDate().getMonth()+1;
		ano = juno.getPagamentoDate().getYear()+1900;
			
		
		movBco.setDuplc(duprec.getNumdup());
		movBco.setLetra(duprec.getOrdem());
		movBco.setVLR(juno.getAmount());
		movBco.setDia(dia);
		movBco.setMes(mes);
		movBco.setAno(ano);
		movBco.setDuplc(duprec.getNumdup());
		movBco.setLetra(duprec.getOrdem());
		movBco.setCRE(daoEmpresas.trocarEmpresa(codigo).getConjurrc());
		movBco.setDEV(daoClientes.contaCliente(duprec.getCodcli().getCod(), codigo).getContacli());
		movBco.setUsuario(usuario);
		movBco.setGravacao(DateTime.now());
		movBco.setSIM("AL");
		movBco.setOpe("01");
		movBco.setCodCli(duprec.getCodcli().getCod());
		movBco.setBanco(banco.getCodBco());
		movBco.setHis("JUROS "+movBco.getDuplc()+"-"+movBco.getLetra()+" - "+daoClientes.contaCliente(duprec.getCodcli().getCod(), codigo).getNom());
					
		salvarMovBanco(movBco, codigo);
		double registroBanco = pegarRegistro(movBco.getAno(), codigo);
		
		return registroBanco;	
		
		
	}

	public double pegarRegistro(int ano, String codigo) throws SQLException, ClassNotFoundException {
		double registro = 0;

		StringBuilder sql1 = new StringBuilder();

		sql1.append("select (max(regauto) + 1) as reg from mbco?");

		Connection conexao = ConexaoFactory.conectarMySql(codigo);
		PreparedStatement comando1 = conexao.prepareStatement(sql1.toString());

		comando1.setInt(1, ano);

		ResultSet resultado1 = comando1.executeQuery();

		if (resultado1.next()) {
			registro = resultado1.getInt("reg");
			System.err.println("registro: " + registro);
		} else {
			System.out.println("Nao achou nada");
		}
		return registro;
	}

	public void salvarMovBanco(MovBco movBco, String codigo) throws SQLException, ClassNotFoundException {
		StringBuilder sql = new StringBuilder();

		sql.append("INSERT INTO mbco?(reg, dev, cre, SIM, VLR, DIA, MES, ANO, banco, DUPLC, LETRA, CODCLI, CODHIS, OPE, USUARIO, HIS, GRAVACAO) ");
		sql.append("VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, '0','01', ?, ?, CURDATE())");

		Connection conexao = ConexaoFactory.conectarMySql(codigo);
		PreparedStatement comando = conexao.prepareStatement(sql.toString());

		comando.setInt(1, movBco.getAno());
		comando.setDouble(2, pegarRegistro(movBco.getAno(), codigo));
		comando.setString(3, movBco.getDEV());
		comando.setString(4, movBco.getCRE());
		comando.setString(5, movBco.getSIM());
		comando.setBigDecimal(6, movBco.getVLR());
		comando.setInt(7, movBco.getDia());
		comando.setInt(8, movBco.getMes());
		comando.setInt(9, movBco.getAno());
		comando.setString(10, movBco.getBanco());
		comando.setString(11, movBco.getDuplc());
		comando.setString(12, movBco.getLetra());
		comando.setString(13, movBco.getCodCli());
		comando.setString(14, movBco.getUsuario());
		comando.setString(15, movBco.getHis());

		comando.execute();
		
	}
	
	public void salvarRecebimentos(RecebimentosJuno juno, String codigo) throws SQLException, ClassNotFoundException {
		StringBuilder sql = new StringBuilder();
		sql.append("insert into recebimentosjuno (code, dueDate, link, amount, empresa, payNumber, pagamentoDate, fee) ");
		sql.append("select * from (select ?, ?, ?, ?, ?, ?, ?, ?) as temp where not exists (select code from recebimentosjuno where code= ? )");
		
		Connection conexao = ConexaoFactory.conectarMySql(codigo);
		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		
		comando.setString(1, juno.getCode());
		comando.setDate(2, (Date) juno.getDueDate());
		comando.setString(3, juno.getLink());
		comando.setBigDecimal(4, juno.getAmount());
		comando.setString(5, codigo);
		comando.setString(6, juno.getPayNumber());
		comando.setDate(7, (Date) juno.getPagamentoDate());
		comando.setFloat(8, juno.getFee());
		comando.setString(9, juno.getCode());
		
		comando.execute();
		
	}

}
