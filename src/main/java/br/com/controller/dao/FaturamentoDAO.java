package br.com.controller.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.parser.ParseException;

import br.com.controller.domain.Duprec;
import br.com.controller.domain.Empresas;
import br.com.controller.domain.Fatser;
import br.com.controller.factory.ConexaoFactory;

public class FaturamentoDAO extends GenericDAO<Fatser> implements Serializable {

	private static final long serialVersionUID = 6137284457482053682L;

	public List<Fatser> totalFaturamentoCincoAnos(String codigo)
			throws ClassNotFoundException, SQLException, ParseException {
		StringBuilder sql = new StringBuilder();
		sql.append("select sum(fatser.totnf) as totnf, date_format(fatser.datemi, '%Y') as ano from fatser ");
		sql.append("where fatser.datemi between date_add(now(), interval -4 year) and now() ");
		sql.append("group by year(fatser.datemi) order by fatser.datemi");

		Connection conexao = ConexaoFactory.conectarMySql(codigo);
		PreparedStatement comando = conexao.prepareStatement(sql.toString());

		ResultSet resultado = comando.executeQuery();

		List<Fatser> fatsers = new ArrayList<Fatser>();

		while (resultado.next()) {
			Fatser fat = new Fatser(resultado.getString("ano"), resultado.getDouble("totnf"));
			fatsers.add(fat);
		}
		return fatsers;
	}

	public List<Fatser> totalFaturamentoDozeMeses(String codigo)
			throws ClassNotFoundException, SQLException, ParseException {

		StringBuilder sql = new StringBuilder();
		sql.append("select sum(fatser.totnf) as total, date_format(fatser.datemi, '%m-%y') as mes from fatser\r\n"
				+ "where fatser.datemi between date_add(now(), interval -12 month) and NOW() group by month(fatser.datemi) order by fatser.datemi");

		Connection conexao = ConexaoFactory.conectarMySql(codigo);
		PreparedStatement comando = conexao.prepareStatement(sql.toString());

		ResultSet resultado = comando.executeQuery();

		List<Fatser> fatsers = new ArrayList<Fatser>();

		while (resultado.next()) {
			Fatser fat = new Fatser(resultado.getString("mes"), resultado.getDouble("total"));
			// System.out.println("Dentro do DAO "+fat.getDoubleField()+ " Mes:
			// "+fat.getStringField());
			fatsers.add(fat);
		}
		return fatsers;
	}

	public List<Duprec> totalRecebimentoDozeMeses(String codigo)
			throws ClassNotFoundException, SQLException, ParseException {
		StringBuilder sql = new StringBuilder();
		sql.append("select sum(duprec.vlrpgo) as total, date_format(duprec.emis, '%m-%y') as mes from duprec \r\n"
				+ "where duprec.emis between date_add(now(), interval -12 month) and now() group by month(duprec.emis) order by duprec.emis");

		Connection conexao = ConexaoFactory.conectarMySql(codigo);
		PreparedStatement comando = conexao.prepareStatement(sql.toString());

		ResultSet resultado = comando.executeQuery();

		List<Duprec> duprecs = new ArrayList<Duprec>();

		while (resultado.next()) {
			Duprec duprec = new Duprec(resultado.getString("mes"), resultado.getDouble("total"));
			duprecs.add(duprec);
		}
		return duprecs;
	}

	public Double listarFaturamentoMesAtual(String codigo) throws ClassNotFoundException, SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("select sum(fatser.totnf) as total from fatser ");
		sql.append("where (month(fatser.datemi) = month(now())) and (year(fatser.datemi) = year(now()))");

		Connection conexao = ConexaoFactory.conectarMySql(codigo);
		PreparedStatement comando = conexao.prepareStatement(sql.toString());

		ResultSet resultado = comando.executeQuery();

		Double vlrTotal = null;

		if (resultado.next()) {
			vlrTotal = resultado.getDouble("total");
		} else {
			vlrTotal = 0.0;
		}

		return vlrTotal;
	}

	public Double listarFaturamentoAnoAtual(String codigo) throws ClassNotFoundException, SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("select sum(fatser.totnf) as total from fatser where year(fatser.datemi) = year(now()) ");
		sql.append("group by year(fatser.datemi) ");

		Connection conexao = ConexaoFactory.conectarMySql(codigo);
		PreparedStatement comando = conexao.prepareStatement(sql.toString());

		ResultSet resultado = comando.executeQuery();

		Double vlrTotal = null;

		if (resultado.next()) {
			vlrTotal = resultado.getDouble("total");
		} else {
			vlrTotal = 0.0;
		}

		return vlrTotal;
	}

	public Double mostrarFaturadosNoMesAnterior(String codigo) throws SQLException, ClassNotFoundException {

		StringBuilder sql = new StringBuilder();
		sql.append("select sum(fatser.totnf) as total \r\n"
				+ "from fatser \r\n"
				+ "where (\r\n"
				+ "    (year(fatser.datemi) = if(month(curdate()) = 1, year(curdate()) - 1, year(curdate())) \r\n"
				+ "     and month(fatser.datemi) = if(month(curdate()) = 1, 12, month(curdate()) - 1))\r\n"
				+ ")\r\n"
				+ "");

		Connection conexao = ConexaoFactory.conectarMySql(codigo);

		PreparedStatement comando = conexao.prepareStatement(sql.toString());

		ResultSet resultado = comando.executeQuery();
		Double saldo = null;

		if (resultado.next()) {
			saldo = resultado.getDouble("total");
		} else {
			saldo = 0.0;
		}

		return saldo;
	}

	public Double mostrarFaturadosNoMesAnteriorAJaneiro(String codigo) throws SQLException, ClassNotFoundException {

		StringBuilder sql = new StringBuilder();
		sql.append(
				"select sum(fatser.totnf) as total from fatser where month(fatser.datemi) = 12 and year(fatser.datemi) = year(current_date()) -1");

		Connection conexao = ConexaoFactory.conectarMySql(codigo);

		PreparedStatement comando = conexao.prepareStatement(sql.toString());

		ResultSet resultado = comando.executeQuery();
		Double saldo = null;

		if (resultado.next()) {
			saldo = resultado.getDouble("total");
		} else {
			saldo = 0.0;
		}

		return saldo;
	}

	public List<Fatser> mostrarFaturamentoPorEstadoAnoCorrente(String codigo) {
		StringBuilder sql = new StringBuilder();

		sql.append("select sum(fatser.totnf) as total, concat('BR-',clifor.est) as estado from fatser\r\n"
				+ "inner join clifor on clifor.cod = fatser.codcli where fatser.datemi between date_add(now(), interval -12 month) and now() \r\n"
				+ "group by clifor.est ");

		Connection conexao = null;
		try {
			conexao = ConexaoFactory.conectarMySql(codigo);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PreparedStatement comando = null;
		try {
			comando = conexao.prepareStatement(sql.toString());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ResultSet resultado = null;
		try {
			resultado = comando.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<Fatser> fatsers = new ArrayList<Fatser>();

		try {
			while (resultado.next()) {
				Fatser fat = new Fatser();
				fat.setStringField(resultado.getString("estado"));
				fat.setDoubleField(resultado.getDouble("total"));
				fatsers.add(fat);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fatsers;
	}

	public Double mostrarRecebidosMesAtual(String codigo) throws SQLException, ClassNotFoundException {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"select sum(duprec.vlrpgr) as total from duprec where (month(duprec.dtpg) = month(current_date()) and year (duprec.dtpg) = year(current_date()))");

		Connection conexao = ConexaoFactory.conectarMySql(codigo);

		PreparedStatement comando = conexao.prepareStatement(sql.toString());

		ResultSet resultado = comando.executeQuery();
		Double saldo = null;

		if (resultado.next()) {
			saldo = resultado.getDouble("total");
		} else {
			saldo = 0.0;
		}

		return saldo;
	}

	public Double mostrarVencimentoMesAtual(String codigo) throws SQLException, ClassNotFoundException {
		StringBuilder sql = new StringBuilder();
		sql.append("select sum(duprec.vlrpgr) as total from duprec where month(duprec.vcto) =  month(current_date()) ");
		sql.append("and year(duprec.vcto) = year(current_date())");

		Connection conexao = ConexaoFactory.conectarMySql(codigo);

		PreparedStatement comando = conexao.prepareStatement(sql.toString());

		ResultSet resultado = comando.executeQuery();
		Double saldo = null;

		if (resultado.next()) {
			saldo = resultado.getDouble("total");
		} else {
			saldo = 0.0;
		}

		return saldo;
	}

	public Double mostrarFaturadosNoMesNoAnoAnterior(String codigo) throws SQLException, ClassNotFoundException {

		StringBuilder sql = new StringBuilder();
		sql.append(
				"select sum(fatser.totnf) as total from fatser where month(fatser.datemi) = MONTH(CURRENT_DATE()) and year(fatser.datemi) = year(CURRENT_DATE() - INTERVAL 1 year) and fatser.flag = ''");

		Connection conexao = ConexaoFactory.conectarMySql(codigo);

		PreparedStatement comando = conexao.prepareStatement(sql.toString());

		ResultSet resultado = comando.executeQuery();
		Double saldo = null;

		if (resultado.next()) {
			saldo = resultado.getDouble("total");
		} else {
			saldo = 0.0;
		}

		return saldo;
	}

	public Double mostrarFaturamentoAnoAnterior(String codigo) throws ClassNotFoundException, SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"select sum(fatser.totnf) as total from fatser where year(fatser.datemi) = year(current_date())-1 and fatser.flag = ''");

		Connection conexao = ConexaoFactory.conectarMySql(codigo);

		PreparedStatement comando = conexao.prepareStatement(sql.toString());

		ResultSet resultado = comando.executeQuery();
		Double saldo = null;

		if (resultado.next()) {
			saldo = resultado.getDouble("total");
		}
		return saldo;
	}

	public int faturadosRecebidos(String codigo) throws SQLException, ClassNotFoundException {
		int total1 = 0;
		int total2 = 0;

		StringBuilder sql = new StringBuilder();
		StringBuilder sql2 = new StringBuilder();

		sql.append(
				"SELECT COUNT(duprec.CODCLI) AS recebidos  FROM duprec WHERE DUPREC.DTPG  BETWEEN LAST_DAY(DATE(DATE_SUB(NOW(), INTERVAL 1 MONTH))+1) AND LAST_DAY(DATE(NOW()))");
		sql2.append(
				"SELECT COUNT(fatser.CODCLI) as faturados FROM fatser WHERE fatser.DATEMI BETWEEN  LAST_DAY(DATE(DATE_SUB(NOW(), INTERVAL 2 MONTH))+1) AND LAST_DAY(DATE(DATE_SUB(NOW(), INTERVAL 1 MONTH)));");

		Connection conexao = ConexaoFactory.conectarMySql(codigo);

		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		PreparedStatement comando2 = conexao.prepareStatement(sql2.toString());

		ResultSet resultado = comando.executeQuery();
		ResultSet resultado2 = comando2.executeQuery();

		if (resultado.next()) {
			total1 = resultado.getInt("recebidos");
		}
		if (resultado2.next()) {
			total2 = resultado2.getInt("faturados");
		}

		System.out.println(total1 + "-" + total2);
		return 0;
	}

	public Fatser verificaSeTemNfse(Duprec dup, String codigo) throws SQLException, ClassNotFoundException {
		StringBuilder sql = new StringBuilder();

		System.out.println("Entrou para verificar");
		System.out.println(dup.getFilial() + " - " + codigo);
		sql.append("SELECT * FROM nfsdig WHERE nfsdig.numnf = ?");

		Connection conexao = ConexaoFactory.conectarMySql(codigo);

		PreparedStatement comando = conexao.prepareStatement(sql.toString());

		comando.setString(1, dup.getNumNFSe());

		ResultSet resultado = comando.executeQuery();

		Fatser fatser = new Fatser();
		if (resultado.next()) {
			fatser.setCodVerificacao(resultado.getString("codVerificacao"));
			fatser.setNumNfSer(resultado.getString("numeronfse"));
			EmpresasDAO daoEmpresas = new EmpresasDAO();
			System.out.println("Encontrou a Nota, agora vai buscar a inscricao");
			fatser.setInscMunicipal(daoEmpresas.selecionaInscricaoMunicipal(codigo));
		}

		return fatser;
	}

	public Fatser buscarFatserItem(Fatser fatser, String codigo) throws ClassNotFoundException, SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"select duprec.numdup, duprec.vcto, duprec.nf, itfser.descrser, itfser.nfser, itfser.seq, nfsdig.codverificacao from duprec ");
		sql.append(
				"inner join itfser on itfser.nfser = duprec.numnfse  inner join nfsdig on nfsdig.numeronfse = duprec.numnfse ");
		sql.append("where duprec.numnfse = ? and itfser.seq = 1");

		Connection conexao = ConexaoFactory.conectarMySql(codigo);

		PreparedStatement comando = conexao.prepareStatement(sql.toString());

		comando.setString(1, fatser.getNumNfSer());
		// comando.setString(2, fatser.getFilial());

		ResultSet resultado = comando.executeQuery();

		if (resultado.next()) {
			fatser.setCodVerificacao(resultado.getString("codVerificacao"));
			fatser.setDescricao(resultado.getString("descrser"));
			fatser.setNumDup(resultado.getString("numdup"));
			EmpresasDAO daoEmpresas = new EmpresasDAO();
			fatser.setInscMunicipal(daoEmpresas.selecionaInscricaoMunicipal(codigo));
		}

		return fatser;
	}

	public List<Fatser> listarFaturamentoPorProdutoDozeMeses(Empresas empresaSelecionada)
			throws ClassNotFoundException, SQLException {

		StringBuilder sql = new StringBuilder();
		sql.append("select itfser.codser, prod.obs, sum(itfser.qtdser * itfser.vunit) as total ");
		sql.append("from itfser inner join prod on itfser.codser = prod.codpro ");
		sql.append(
				"inner join fatser on itfser.nfser = fatser.nfser where fatser.datemi between date_add(now(), interval -60 month) and now() ");
		sql.append("group by itfser.codser, prod.despro ");
		sql.append("order by total desc; ");

		Connection conexao = ConexaoFactory.conectarMySql(empresaSelecionada.getUsu());

		PreparedStatement comando = conexao.prepareStatement(sql.toString());

		ResultSet resultado = comando.executeQuery();

		List<Fatser> fatsers = new ArrayList<>();

		while (resultado.next()) {
			Fatser fatser = new Fatser();
			fatser.setDescricao(resultado.getString("obs"));
			fatser.setCoditemfatser(resultado.getString("codser"));
			fatser.setDoubleField(resultado.getDouble("total"));
			System.out.println(fatser.getDescricao());
			fatsers.add(fatser);
		}

		return fatsers;
	}
	
	public Double mostrarTotalFaturamentoExactusDozeMeses(String codigo) throws ClassNotFoundException, SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT SUM(fatser.TOTNF) AS total FROM fatser WHERE (fatser.datemi between date_add(now(), INTERVAL -12 month) and NOW()) ");
		sql.append("AND (fatser.CODCLI = 81760878000127)" );
		
		Connection conexao = ConexaoFactory.conectarMySql(codigo);

		PreparedStatement comando = conexao.prepareStatement(sql.toString());

		ResultSet resultado = comando.executeQuery();
		
		Double total = null;
		
		if (resultado.next()) {
			total = resultado.getDouble("total");
		}
		
		return total;		
	}

	public Double mostrarTotalFaturamentoNaoExactusDozeMeses(String codigo) throws ClassNotFoundException, SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT SUM(fatser.TOTNF) AS total FROM fatser WHERE (fatser.datemi between date_add(now(), INTERVAL -12 month) and NOW()) ");
		sql.append("AND (fatser.CODCLI <> 81760878000127)" );
		
		Connection conexao = ConexaoFactory.conectarMySql(codigo);

		PreparedStatement comando = conexao.prepareStatement(sql.toString());

		ResultSet resultado = comando.executeQuery();
		
		Double total = null;
		
		if (resultado.next()) {
			total = resultado.getDouble("total");
		}
		
		System.out.println("Total Exactus: "+total);
		return total;		
	}

	public static void main(String[] args) throws ClassNotFoundException, SQLException, ParseException {
		FaturamentoDAO dao = new FaturamentoDAO();
		Empresas empresa = new Empresas();
		empresa.setUsu("9000");
		dao.mostrarTotalFaturamentoExactusDozeMeses(empresa.getUsu());

	}
}
