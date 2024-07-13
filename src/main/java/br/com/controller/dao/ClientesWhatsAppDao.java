package br.com.controller.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.controller.domain.Clifor;
import br.com.controller.factory.ConexaoFactory;

public class ClientesWhatsAppDao extends GenericDAO<Clifor> implements Serializable {

	private static final long serialVersionUID = 5887269097927959878L;

	public List<Clifor> listarWhatsApp(String codigo) throws SQLException, ClassNotFoundException {

		List<Clifor> clientes = new ArrayList<Clifor>();

		StringBuilder sql = new StringBuilder();

		sql.append("select * from clifor where cliente is true");

		Connection conexao = ConexaoFactory.conectarMySql(codigo);
		PreparedStatement comando = conexao.prepareStatement(sql.toString());

		ResultSet resultado = comando.executeQuery();

		while (resultado.next()) {
			Clifor cli = new Clifor();
			cli.setFantasia(resultado.getString("fantasia"));
			cli.setNom(resultado.getString("nom"));
			cli.setCgc(resultado.getString("cgc"));
			cli.setCpf(resultado.getString("cpf"));
			clientes.add(cli);
		}
		return (clientes);

	}

	public Clifor contaCliente(String codcli, String codigo) throws SQLException, ClassNotFoundException {

		StringBuilder sql = new StringBuilder();
		sql.append("Select * from clifor WHERE cod = ?");

		Connection conexao = ConexaoFactory.conectarMySql(codigo);
		PreparedStatement comando = conexao.prepareStatement(sql.toString());

		comando.setString(1, codcli);

		ResultSet resultado = comando.executeQuery();
		Clifor cli = new Clifor();

		if (resultado.next()) {
			cli.setNom(resultado.getString("nom"));
			cli.setContacli(resultado.getString("contacli"));
			return cli;

		} else {
			return null;
		}
	}

	public Clifor clienteContratoBackup(Clifor clifor, String codigo) throws ClassNotFoundException, SQLException {
		System.out.println("String Variavel para buscar cliente: " + clifor.getCgc().substring(0, 7) + " "
				+ clifor.getCpf().substring(0, 7));
		StringBuilder sql = new StringBuilder();
		sql.append("select itcontra.codpro, itcontra.descrser, itcontra.vlrunit, contrato.inicio, contrato.codcli, ");
		sql.append("clifor.nom from itcontra inner join contrato on contrato.contrato = itcontra.contra inner ");
		sql.append(
				"join clifor on clifor.cod = contrato.codcli where (clifor.CGC like '%?%' OR clifor.CPF LIKE '%?%') and itcontra.codpro = '108'");

		Connection conexao = ConexaoFactory.conectarMySql(codigo);
		PreparedStatement comando = conexao.prepareStatement(sql.toString());

		comando.setString(1, clifor.getCgc().substring(0, 7));
		comando.setString(2, clifor.getCpf().substring(0, 7));

		ResultSet resultado = comando.executeQuery();

		Clifor cli = new Clifor();

		if (resultado.next()) {
			cli.setCgc(resultado.getString("cgc"));
			cli.setCpf(resultado.getString("cpf"));
			cli.setNom(resultado.getString("nom"));
			System.err.println("cliente: " + cli.getCgc() + " " + cli.getCpf() + " " + cli.getNom());
			return cli;
		} else {
			System.out.println("eu fiz merda aqui!");
			return null;
		}
	}

	/*
	 * public static void main(String[] args) throws ClassNotFoundException {
	 * CliforDAO dao = new CliforDAO(); Clifor clifor = new Clifor();
	 * clifor.setCgc("61900320100"); clifor.setCpf("13333990000121");
	 * System.out.println("bosta");
	 * 
	 * try { clifor = dao.clienteContratoBackup(clifor, "2000"); } catch
	 * (SQLException e) { // TODO Auto-generated catch block e.printStackTrace(); }
	 * }
	 */
	public Clifor listarContratoBackup(Clifor clifor, String codigo) throws ClassNotFoundException, SQLException {
		System.out.println("String Variavel para buscar cliente: " + clifor.getCgc().substring(0, 7) + " "
				+ clifor.getCpf().substring(0, 7));
		StringBuilder sql = new StringBuilder();
		sql.append("select itcontra.codpro, itcontra.descrser, itcontra.vlrunit, contrato.inicio, contrato.codcli, ");
		sql.append("clifor.nom from itcontra inner join contrato on contrato.contrato = itcontra.contra inner ");
		sql.append(
				"join clifor on clifor.cod = contrato.codcli where (clifor.CGC like '%?%' OR clifor.CPF LIKE '%?%') and itcontra.codpro = '108'");

		Connection conexao = ConexaoFactory.conectarMySql(codigo);
		PreparedStatement comando = conexao.prepareStatement(sql.toString());

		comando.setString(1, clifor.getCgc().substring(0, 7));
		comando.setString(2, clifor.getCpf().substring(0, 7));

		ResultSet resultado = comando.executeQuery();

		Clifor cli = new Clifor();

		if (resultado.next()) {
			cli.setCgc(resultado.getString("cgc"));
			cli.setCpf(resultado.getString("cpf"));
			cli.setNom(resultado.getString("nom"));
			System.err.println("cliente: " + cli.getCgc() + " " + cli.getCpf() + " " + cli.getNom());
			return cli;
		} else {
			System.out.println("eu fiz merda aqui!");
			return null;
		}
	}

	public List<Clifor> listarClientesGalaxPay(String codigo) throws SQLException, ClassNotFoundException {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"select clifor.cpf, clifor.cgc, clifor.nom, clifor.endcob, clifor.complcob, clifor.baicob, clifor.cepcob, clifor.cidcob, clifor.estcob, clifor.email, clifor.numender,\r\n"
						+ "replace(concat(clifor.dddcob1, replace(clifor.telecob1, '-',''), ' ',''),' ','') as fone, clifor.id_galaxpay, clifor.cod \r\n"
						+ "from clifor inner join duprec on clifor.cod = duprec.codcli \r\n"
						+ "inner join itfser on itfser.nfser = duprec.nf inner join banco on banco.codbco = duprec.banco \r\n"
						+ "where duprec.banco='0050' and duprec.nossonum = '' and itfser.codfil = duprec.codfil  and duprec.vlrpgo  = ''");

		Connection conexao = ConexaoFactory.conectarMySql(codigo);
		PreparedStatement comando = conexao.prepareStatement(sql.toString());

		ResultSet resultado = comando.executeQuery();

		List<Clifor> clientes = new ArrayList<Clifor>();

		while (resultado.next()) {
			Clifor cli = new Clifor();
			cli.setCod(resultado.getString("cod"));
			cli.setNom(resultado.getString("nom"));

			if (resultado.getString("cpf") != "") {
				cli.setCpfcgc(resultado.getString("CPF"));
			}
			if (resultado.getString("CGC") != "") {
				cli.setCpfcgc(resultado.getString("cgc"));
			}

			cli.setEmail(resultado.getString("email"));
			cli.setFone(resultado.getString("fone"));
			cli.setCepcob(resultado.getString("cepcob"));
			cli.setEndcob(resultado.getString("endcob"));
			cli.setNumendercob(resultado.getString("numender"));
			cli.setComplcob(resultado.getString("complcob"));
			cli.setBaicob(resultado.getString("baicob"));
			cli.setCidcob(resultado.getString("cidcob"));
			cli.setEstcob(resultado.getString("estcob"));
			cli.setNumendercob(resultado.getString("numender"));
			cli.setId_galaxPay(resultado.getString("id_galaxpay"));

			System.out.println("Dentro do DAO : " + cli.getNom() + "CGC/CPF: " + cli.getCpfcgc());

			clientes.add(cli);
		}
		return clientes;
	}

	/*
	 * public String criarHashClientes(String codcli, String codigo) throws
	 * ClassNotFoundException, SQLException { StringBuilder sql = new
	 * StringBuilder(); sql.
	 * append("select concat(clifor.endcob, clifor.complcob, clifor.numendercob, clifor.cidcob, clifor.estcob, clifor.cepcob) as text from clifor\r\n"
	 * + "where clifor.cod = ?");
	 * 
	 * Connection conexao = ConexaoFactory.conectarMySqlDirectus(codigo);
	 * PreparedStatement comando = conexao.prepareStatement(sql.toString());
	 * comando.setString(1, codcli);
	 * 
	 * ResultSet resultado = comando.executeQuery();
	 * 
	 * if(resultado.next()) { String hash = resultado.getString("text");
	 * System.out.println(hash); hash = Base64.encodeBytes(hash.getBytes());
	 * System.out.println(hash); return hash; } return null;
	 * 
	 * }
	 */
	public Clifor buscarCliente(String documento, String codigo) throws ClassNotFoundException, SQLException {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from clifor where clifor.cgc = ?");

		Connection conexao = ConexaoFactory.conectarMySql(codigo);
		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		comando.setString(1, documento);

		ResultSet resultado = comando.executeQuery();

		Clifor cli = new Clifor();

		if (resultado.next()) {

			cli.setCod(resultado.getString("cod"));
			cli.setNom(resultado.getString("nom"));

			if (resultado.getString("cpf") != "") {
				cli.setCpfcgc(resultado.getString("CPF"));
			}
			if (resultado.getString("CGC") != "") {
				cli.setCpfcgc(resultado.getString("cgc"));
			}

			cli.setEmail(resultado.getString("email"));
			cli.setFone(resultado.getString("telecob1"));
			cli.setCepcob(resultado.getString("cepcob"));
			cli.setEndcob(resultado.getString("endcob"));
			cli.setNumendercob(resultado.getString("numender"));
			cli.setComplcob(resultado.getString("complcob"));
			cli.setBaicob(resultado.getString("baicob"));
			cli.setCidcob(resultado.getString("cidcob"));
			cli.setEstcob(resultado.getString("estcob"));
			cli.setId_galaxPay(resultado.getString("id_galaxpay"));

			System.out.println("Documento:? " + cli.getCpfcgc());
		}

		return cli;
	}

	public void updateClientesGalaxPay(Clifor clifor, String codigo) throws SQLException, ClassNotFoundException {
		StringBuffer sql = new StringBuffer();
		sql.append("update clifor set clifor.id_galaxpay = ? where clifor.id_clifor = ?");

		Connection conexao = ConexaoFactory.conectarMySql(codigo);
		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		comando.setString(1, clifor.getId_galaxPay());
		comando.setInt(2, clifor.getId_clifor());

		comando.execute();
	}
	public List<Clifor> listarClientesNaoCadastrados(String codigo) throws ClassNotFoundException, SQLException{
		StringBuilder sql = new StringBuilder();
		sql.append("select distinct(clifor.id_clifor), clifor.nom, concat(clifor.cgc,clifor.cpf) as cnpjcpf, clifor.cidcob, clifor.estcob, clifor.email, ");
		sql.append("concat(clifor.dddcob1,clifor.telecob1) as fone, clifor.cepcob, clifor.id_galaxpay from clifor inner join duprec on duprec.codcli =  clifor.cod ");
		sql.append("inner join banco on banco.codbco = duprec.banco  where (clifor.id_galaxpay is null and duprec.banco = '0050') and ");
		sql.append("(duprec.vlrpgo is null or duprec.vlrpgo = 0)");
		
		Connection conexao = ConexaoFactory.conectarMySql(codigo);
		PreparedStatement comando = conexao.prepareStatement(sql.toString());
				
		ResultSet resultado = comando.executeQuery();
		
		List<Clifor> clientes = new ArrayList<Clifor>();
		while(resultado.next()) {
			Clifor cli = new Clifor();
			cli.setId_clifor(resultado.getInt("id_clifor"));
			cli.setNom(resultado.getString("nom"));
			cli.setEmail(resultado.getString("email"));
			cli.setCpfcgc(resultado.getString("CnpjCpf"));
			cli.setCidcob(resultado.getString("cidcob"));
			cli.setEstcob(resultado.getString("estcob"));
			cli.setFone(resultado.getString("fone"));
			cli.setCepcob(resultado.getString("cepcob"));
			clientes.add(cli);
			System.out.println(cli.getNom());
		}
		return clientes;
		
	}
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		ClientesWhatsAppDao dao = new ClientesWhatsAppDao();
		dao.listarClientesNaoCadastrados("2000");
	}
}
