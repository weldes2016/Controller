package br.com.controller.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import br.com.controller.domain.Certificados;
import br.com.controller.factory.ConexaoFactory;


public class CertificadoDAO extends GenericDAO<Certificados> implements Serializable {

	private static final long serialVersionUID = 5887269097927959878L;

	public List<Certificados> listarCertificados() throws SQLException, ClassNotFoundException {

		List<Certificados> certificados = new ArrayList<Certificados>();

		StringBuilder sql = new StringBuilder();

		sql.append("select * from certificados");

		Connection conexao = ConexaoFactory.conectarMySqlUsuariosDirectus();
		PreparedStatement comando = conexao.prepareStatement(sql.toString());

		ResultSet resultado = comando.executeQuery();

		while (resultado.next()) {
			Certificados cert = new Certificados();
			// cert.setLong(resultado.getLong("codigo"));
			cert.setAlias(resultado.getString("alias"));
			cert.setCnpj(resultado.getString("cnpj"));
			cert.setEmissor(resultado.getString("emissor"));
			cert.setRazaoSocial(resultado.getString("razaqSocial"));
			cert.setSujeito(resultado.getString("sujeito"));
			cert.setValFim(resultado.getDate("valFim"));
			cert.setValInicio(resultado.getDate("valInicio"));
			certificados.add(cert);
		}
		return (certificados);
	}

	public static void main(String[] args) {
		CertificadoDAO dao = new CertificadoDAO();
		List<Certificados> certs = new ArrayList<>();
		certs = dao.listarCertificadosVencendo();
		for (Certificados certificados : certs) {
			System.out.println(certificados.getCodigo());
		}
	}

	public List<Certificados> listarCertificadosVencendo() {

		List<Certificados> certificados = null;

		try {
			// Data atual + 5 dias
			LocalDate dataFutura = LocalDate.now().plusDays(10);

			// Converte LocalDate para Date
			Date dataFuturaDate = java.sql.Date.valueOf(dataFutura);

			// HQL query
			String hql = "FROM Certificados c WHERE c.valFim <= :dataFutura";
			Query query = emf.createQuery(hql, Certificados.class);
			query.setParameter("dataFutura", dataFuturaDate);

			// Execute the query and get the results
			certificados = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (emf != null && emf.isOpen()) {
				emf.close();
			}
		}

		return certificados;
	}
}
