package br.com.controller.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.controller.domain.Duppag;
import br.com.controller.factory.ConexaoFactory;

public class DuppagDAO extends GenericDAO<Duppag> implements Serializable {

	private static final long serialVersionUID = 1329866443990715082L;

	private String[] emp = { "1000", "2000", "9000", "9999" };
	private String[] socios = { "01720865159", "00964333104", "71487204191", "61900320100" };
	private Double[] valores = new Double[4];

	public String[] getEmp() {
		return emp;
	}

	public void setEmp(String[] emp) {
		this.emp = emp;
	}

	public String[] getSocios() {
		return socios;
	}

	public void setSocios(String[] socios) {
		this.socios = socios;
	}

	public Duppag pagarSocios(String codigo, String codcliente) throws ClassNotFoundException, SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"SELECT duppag.CODCLI AS CODIGO, SUM(duppag.VLRPGR) AS PAGAR, sUM(duppag.VLRPGO) AS PAGO, (SUM(duppag.VLRPGR) - Sum(duppag.VLRPGO)) AS diferenca, clifor.nom FROM duppag INNER JOIN CLIFOR ON CLIFOR.COD = duppag.CODCLI ");
		sql.append(
				"WHERE clifor.CPF = ? and (YEAR(duppag.VCTO) >= YEAR(CURDATE())) GROUP BY duppag.CODCLI ORDER BY VCTO DESC");
		Connection conexao = ConexaoFactory.conectarMySql(codigo);

		PreparedStatement comando = conexao.prepareStatement(sql.toString());

		comando.setString(1, codcliente);

		ResultSet resultado = comando.executeQuery();

		Duppag dup = new Duppag();

		if (resultado.next()) {
			dup.setCodcli(resultado.getString("codigo"));
			dup.setNom(resultado.getString("nom"));
			dup.setVlrpgo(resultado.getDouble("pago"));
			dup.setVlrpgr(resultado.getDouble("pagar"));
			dup.setDiferenca(resultado.getDouble("diferenca"));

			// System.out.println("Codigo: "+dup.getCodcli()+" Nome: "+dup.getNom()+" Valor
			// a Pagar: "+dup.getVlrpgr()+ " Valor Pago: "+dup.getVlrpgo()+ " Diferença:
			// "+dup.getDiferenca());

		}
		return dup;

	}
	
	public Duppag pgtoSocios (String codigo, String codcliente) throws ClassNotFoundException, SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT SUM(duppag.VLRPGR) AS total, SUM(DUPPAG.VLRPGO) AS pago, clifor.nom, clifor.CPF, SUM(dupPAG.VLRPGR) - SUM(dupPAG.VLRPGO) AS diferenca ");
		sql.append("FROM dupPAG INNER JOIN clifor ON clifor.COD = duppag.CODCLI WHERE (MONTH(duppag.VCTO) = MONTH(CURRENT_TIMESTAMP()) AND ");
		sql.append("YEAR(duppag.VCTO) = YEAR(CURRENT_TIMESTAMP())) AND (clifor.CPF = ?) GROUP BY clifor.cod ");
		
		Connection conexao = ConexaoFactory.conectarMySql(codigo);

		PreparedStatement comando = conexao.prepareStatement(sql.toString());

		comando.setString(1, codcliente);

		ResultSet resultado = comando.executeQuery();

		Duppag dup = new Duppag();

		if (resultado.next()) {
			dup.setCodcli(resultado.getString("cpf"));
			dup.setNom(resultado.getString("nom"));
			dup.setVlrpgo(resultado.getDouble("pago"));
			dup.setVlrpgr(resultado.getDouble("total"));
			dup.setDiferenca(resultado.getDouble("diferenca"));
			System.out.println(dup.getVlrpgr());
		}
		return dup;
	}

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		DuppagDAO dao = new DuppagDAO();

		List<String[]> rensga = dao.lerDuppagTresMeses("9000");
		for (String[] strings : rensga) {
			System.out.println("Mes: "+strings[0]+ " - Valor: "+Double.parseDouble(strings[1]));
		}
	}

	public void valorTotalSocios() throws ClassNotFoundException, SQLException {
		DuppagDAO dao = new DuppagDAO();
		
		System.out.println("inicio =========================");
		for (String string : socios) {
			double total = 0;
			System.err.println(string);

			for (String stringEmpresa : emp) {
//				total = total + (dao.pagarSocios(stringEmpresa, string).getVlrpgr());
				System.out.println(total);
			}
		}
		System.out.println("Termino =========================");
	}
	
	public List<String[]> lerDuppagTresMeses(String codigo) throws ClassNotFoundException, SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("select concat(lpad(day(duppag.vcto),2,'00'),'-',(lpad(month(duppag.vcto),2,'00'))) as vencimento, sum(duppag.vlrpgr) as valor from duppag ");
		sql.append("where date_format(vcto, '%y%m') IN ( date_format(curdate(), '%y%m'), ");
		sql.append("date_format(date_sub(curdate(), interval 1 month), '%y%m'),");
		sql.append("date_format(date_add(curdate(), interval 1 month), '%y%m')) ");
		sql.append("and (duppag.DTPG is null) ");
		sql.append("group by duppag.vcto ");
		sql.append("order by duppag.vcto;");
		
		Connection conexao = ConexaoFactory.conectarMySql(codigo);

		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		ResultSet resultado = comando.executeQuery();
		
		 List<String[]> listaDeValores = new ArrayList<>();
		 
		while(resultado.next()) {
			String[] valores = new String[2];
				
			valores[0] = resultado.getString("vencimento");
			valores[1] = resultado.getString("valor");
			
			listaDeValores.add(valores);		
			
		}
		return listaDeValores;
	}
	
}
