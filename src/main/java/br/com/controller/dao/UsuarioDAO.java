package br.com.controller.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import br.com.controller.domain.Usuario;
import br.com.controller.factory.ConexaoFactory;

public class UsuarioDAO extends GenericDAO<Usuario> implements Serializable {

	private static final long serialVersionUID = 8155595807694005029L;

	private Usuario usuario = new Usuario();

	public Usuario validarUsuarioDirectus(Usuario us) throws SQLException, ClassNotFoundException {
		
		StringBuilder sql = new StringBuilder();
		sql.append("select * from usuario where nome = ? and pass = ?");

		Connection conexao = ConexaoFactory.conectarMySqlUsuariosDirectus();

		PreparedStatement comando = conexao.prepareStatement(sql.toString());

		comando.setString(1, us.getLogin());
		comando.setString(2, us.getSenha());

		ResultSet resultado = comando.executeQuery();

		if (resultado.next()) {
			usuario.setCodigo(resultado.getInt("idUsuario"));
			usuario.setNome(resultado.getString("nome"));
			usuario.setSenha(resultado.getString("pass"));
			usuario.setDepto(resultado.getString("depto"));
			usuario.setCelular(resultado.getString("telefone"));
			usuario.setUltimaempresa(resultado.getString("ultimaempresa"));
			
			System.out.println("Usuario Encontrado!");
		}else {
			System.out.println("nao sai nada");
		}

		return usuario;
	}

	public List<Usuario> listarUsuario() {

		return UsuarioDAO.findAll(Usuario.class);
	}

	public void updateLogin(Usuario us) {
		GenericDAO.update(us);

	}

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Usuario us = new Usuario();
		us.setNome("Weldes");
		us.setSenha("123");

		UsuarioDAO dao = new UsuarioDAO();

		dao.validarUsuarioDirectus(us);
	}

}
