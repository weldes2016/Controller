package br.com.controller.factory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import br.com.controller.domain.Parametros;
import br.com.controller.util.LerPropriedades;

public class ConexaoFactory {

	public static Connection conectarMySqlUsuariosDirectus() throws SQLException, ClassNotFoundException {

		Parametros prop = LerPropriedades.lerPropriedades();

		String URLMySqlCadUsu = prop.getUdriver() + prop.getUhost() + prop.getUbanco();
		System.out.println(URLMySqlCadUsu);
		Class.forName("com.mysql.jdbc.Driver");
		Connection conexao = DriverManager.getConnection(URLMySqlCadUsu, prop.getUlogin(), prop.getUpassword());

		return conexao;

	}

	public static Connection conectarMySql(String codigo) throws SQLException, ClassNotFoundException {

		Parametros prop = LerPropriedades.lerPropriedades();

		String URLMySqlDirectus = prop.getDriver() + prop.getHost() + "/direc" + codigo;

		//System.out.println(URLMySqlDirectus);
		Class.forName("com.mysql.jdbc.Driver");
		Connection conexao = DriverManager.getConnection(URLMySqlDirectus, prop.getLogin(), prop.getPassword());

		return conexao;

	}

	

	public static void main(String[] args) throws IOException, ClassNotFoundException {

		String codigo = "9999";

		try {
			Connection conexaoMySql = ConexaoFactory.conectarMySql(codigo);

			//System.out.println("Conexao realizada com Sucesso! " + conexaoMySql.getCatalog() + " é o Banco de dados");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			Connection conexaomysqlServidor = ConexaoFactory.conectarMySqlUsuariosDirectus();
	//		System.out.println("Conexao realizada com Sucesso! " + conexaomysqlServidor.getCatalog() + " é o Banco de dados");

		} catch (SQLException ex) {
			ex.printStackTrace();
		//	System.out.println("Nao foi possivel conectar ao banco de dados!" + ex.getMessage());

		}
		
	}
}
