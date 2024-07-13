package br.com.controller.dao;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.controller.domain.Empresas;
import br.com.controller.factory.ConexaoFactory;
import br.com.controller.util.ImageConverter;

public class EmpresasDAO extends GenericDAO<Empresas> implements Serializable {

	private static final long serialVersionUID = -5324342429927927148L;

	private static ArrayList<Empresas> empresasDirectus = new ArrayList<Empresas>();
	Empresas empresa = new Empresas();
	ImageConverter converter = new ImageConverter();

	public String selecionaInscricaoMunicipal(String codigo) throws ClassNotFoundException, SQLException {
		StringBuilder sql = new StringBuilder();
		String inscMun = "";

		sql.append("SELECT cadusu.inscmun FROM cadusu WHERE cadusu.usu = ?");

		Connection conexao = ConexaoFactory.conectarMySqlUsuariosDirectus();

		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		comando.setString(1, codigo);

		ResultSet resultado = comando.executeQuery();

		if (resultado.next()) {
			inscMun = resultado.getString("inscmun");

		}
		return inscMun;
	}

	public Empresas trocarEmpresa(String codigo) throws ClassNotFoundException, SQLException, IOException {
	    StringBuilder sql = new StringBuilder();
	    sql.append("SELECT * FROM cadusu WHERE usu = ?");

	    Connection conexao = ConexaoFactory.conectarMySqlUsuariosDirectus();

	    PreparedStatement comando = conexao.prepareStatement(sql.toString());
	    comando.setString(1, codigo);

	    ResultSet resultado = comando.executeQuery();

	    if (resultado.next()) {
	        Empresas empresa = new Empresas();
	        
	        String base64Img = ImageConverter.convertImageToBase64(resultado.getBlob("imglogo"));

	        empresa.setImgLogo(base64Img);
	        empresa.setUsu(resultado.getString("usu"));
	        empresa.setNom(resultado.getString("nom"));
	        empresa.setCa(resultado.getString("ca")); 

	        return empresa;
	    }

	    return null;
	}

	public List<Empresas> listarEmpresasPorUsuario(int i) throws ClassNotFoundException, SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"select useremp.nom, useremp.codusu, usuario.nome, usuario.idusuario, cadusu.id_empresa from useremp \r\n"
						+ "inner join usuario on useremp.nome = usuario.nome\r\n"
						+ "inner join cadusu on useremp.codusu = cadusu.usu\r\n" + "where usuario.idusuario = ?");
		Connection conexao = ConexaoFactory.conectarMySqlUsuariosDirectus();

		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		comando.setInt(1, i);

		ResultSet resultado = comando.executeQuery();

		List<Empresas> empresas = new ArrayList<Empresas>();

		while (resultado.next()) {
			Empresas emp = new Empresas();
			emp.setUsu(resultado.getString("codusu"));
			emp.setNom(resultado.getString("nom"));
			emp.setId_empresa(resultado.getInt("id_empresa"));
			empresas.add(emp);

		}
		return empresas;
	}

	public List<Empresas> listarEmpresas() throws ClassNotFoundException, SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * from cadusu");

		Connection conexao = ConexaoFactory.conectarMySqlUsuariosDirectus();

		PreparedStatement comando = conexao.prepareStatement(sql.toString());

		ResultSet resultado = comando.executeQuery();

		List<Empresas> empresas = new ArrayList<Empresas>();

		while (resultado.next()) {
			Empresas emp = new Empresas();
			emp.setUsu(resultado.getString("codusu"));
			emp.setNom(resultado.getString("nom"));
			emp.setInscriMunicipal("inscmun");
			empresas.add(emp);
		}
		return empresas;
	}

	public Empresas buscarEmpresaPorId(String codigo) throws ClassNotFoundException, SQLException, IOException {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM cadusu WHERE usu = ?");

		try (Connection conexao = ConexaoFactory.conectarMySqlUsuariosDirectus();
				PreparedStatement comando = conexao.prepareStatement(sql.toString())) {

			comando.setString(1, codigo);
			try (ResultSet resultado = comando.executeQuery()) {
				if (resultado.next()) {
					Empresas empresa = new Empresas();
					empresa.setUsu(resultado.getString("usu"));
					empresa.setNom(resultado.getString("nom"));
					empresa.setInscriMunicipal(resultado.getString("inscmun"));
					Blob imgBlob = resultado.getBlob("imglogo");

					String base64Img = ImageConverter.convertImageToBase64(resultado.getBlob("imglogo"));
					empresa.setImgLogo(base64Img);

					System.out.println("Epresa: " + empresa.getImgLogo());

					return empresa;
				} else {
					System.err.println("Empresa não encontrada");
					return null;
				}
			}
		}
	}

	public static ArrayList<Empresas> getEmpresasDirectus() {
		return empresasDirectus;
	}

	public static void setEmpresasDirectus(ArrayList<Empresas> empresasDirectus) {
		EmpresasDAO.empresasDirectus = empresasDirectus;
	}

	public Empresas getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresas empresa) {
		this.empresa = empresa;
	}

}
