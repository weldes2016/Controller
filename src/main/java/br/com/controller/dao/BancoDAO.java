package br.com.controller.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.controller.domain.Banco;
import br.com.controller.factory.ConexaoFactory;
import br.com.controller.galaxpay.domain.GalaxPayBank;

public class BancoDAO implements Serializable {

	private static final long serialVersionUID = 1L;

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
		}
		return banco;
	}
	
	public GalaxPayBank selecionaBancoGalax(String codbanco, String codigo) throws SQLException, ClassNotFoundException {

		StringBuilder sql = new StringBuilder();
		sql.append("Select * from banco where codbco = ?");

		Connection conexao = ConexaoFactory.conectarMySql(codigo);

		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		comando.setString(1, codbanco);

		ResultSet resultado = comando.executeQuery();

		GalaxPayBank galax = new GalaxPayBank();

		if (resultado.next()) {
			galax.setIdGalaxyPay(resultado.getString("idGalaxPay"));
			galax.setHashGalaxPay(resultado.getString("hashGalaxPay"));
		}
		return galax;
	}


	public Banco bancoJuno(String codigo) throws SQLException, ClassNotFoundException {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT CodBco, nombco, tokenjuno FROM banco WHERE LENGTH(banco.tokenjuno) > 1");

		Connection conexao = ConexaoFactory.conectarMySql(codigo);

		PreparedStatement comando = conexao.prepareStatement(sql.toString());
	
		
		ResultSet resultado = comando.executeQuery();

		Banco banco = new Banco();

		
		if (resultado.next()) {
		
			banco.setTokenJuno(resultado.getString("tokenjuno"));
			banco.setNomBco(resultado.getString("nombco"));
			banco.setCodBco(resultado.getString("CodBco"));
		//	System.out.println(banco.getTokenJuno());
			return banco;
		}else return null;
		
	}
	
	public GalaxPayBank bancoGalax(String codigo) throws SQLException, ClassNotFoundException {
		StringBuilder sql = new StringBuilder();
		sql.append("select banco.hashgalaxpay, banco.IdGalaxPay from banco where hashgalaxpay  <> ''");

		Connection conexao = ConexaoFactory.conectarMySql(codigo);

		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		
		ResultSet resultado = comando.executeQuery();

		GalaxPayBank banco = new GalaxPayBank();

		
		if (resultado.next()) {
			
			banco.setHashGalaxPay(resultado.getString("HashGalaxPay"));
			banco.setIdGalaxyPay(resultado.getString("IdGalaxPay"));
			System.out.println(banco.getIdGalaxyPay());
		}
		return banco;
	}

	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		String codigo = "2000";
		BancoDAO dao = new BancoDAO();
		System.out.println(dao.selecionaBancoGalax("0050",codigo).getIdGalaxyPay() +" "+dao.selecionaBancoGalax("0050",codigo).getHashGalaxPay());
	}

}
