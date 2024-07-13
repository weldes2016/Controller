package br.com.controller.dao;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mashape.unirest.http.exceptions.UnirestException;

import br.com.controller.domain.Banco;
import br.com.controller.domain.Clifor;
import br.com.controller.domain.Duprec;
import br.com.controller.domain.Empresas;
import br.com.controller.domain.HistoricoWA;
import br.com.controller.domain.Telefones;
import br.com.controller.factory.ConexaoFactory;
import br.com.controller.galaxpay.domain.Boleto;
import br.com.controller.galaxpay.services.GalaxPayServices;
import br.com.controller.util.ArquivoUtil;

public class DuprecDAO extends GenericDAO<Duprec> implements Serializable {

	private static final long serialVersionUID = -3797119798626563970L;
	SimpleDateFormat sdfSaida = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat sdfEntrada = new SimpleDateFormat("dd/MM/yyyy");

	public List<Duprec> listarDupRec(String codigo) throws SQLException, ClassNotFoundException {

		StringBuilder sql = new StringBuilder();
		sql.append(
				"select clifor.cpf, clifor.cgc, clifor.nom, clifor.endcob, clifor.complcob, clifor.baicob, clifor.cepcob, clifor.cidcob, clifor.estcob, clifor.email, clifor.numender,\r\n"
						+ "		duprec.id_duprec, duprec.percjuros, duprec.percmulta, duprec.numdup, duprec.ordem, duprec.nf, duprec.NumNFSe, duprec.emis, duprec.vcto, duprec.vlrpgr, duprec.banco, duprec.codfil,\r\n"
						+ "		itfser.descrser, banco.tokenJuno from clifor inner join duprec on clifor.cod = duprec.codcli inner join itfser on itfser.nfser = duprec.nf\r\n"
						+ "		inner join banco on banco.Codbco = duprec.BANCO WHERE (duprec.banco='0020') AND (duprec.nossonum = '') AND (itfser.codfil = duprec.codfil) AND (duprec.vlrpgo is NULL)");

		Connection conexao = ConexaoFactory.conectarMySql(codigo);

		PreparedStatement comando = conexao.prepareStatement(sql.toString());

		ResultSet resultado = comando.executeQuery();

		List<Duprec> duprecs = new ArrayList<Duprec>();

		while (resultado.next()) {
			Duprec duprec = new Duprec();
			duprec.setId_duprec(resultado.getInt("id_duprec"));
			duprec.setNumdup(resultado.getString("numdup"));
			duprec.setOrdem(resultado.getString("ordem"));
			duprec.setNf(resultado.getString("nf"));
			duprec.setNumNFSe(resultado.getString("NumNFSe"));
			duprec.setDescricao(resultado.getString("nom"));

			if (resultado.getString("cpf") != "") {
				duprec.setCpfCnpj(resultado.getString("CPF"));
			} else if (resultado.getString("CGC") != "") {
				duprec.setCpfCnpj(resultado.getString("CGC"));
			}
			duprec.setEndereco(resultado.getString("endcob"));
			duprec.setComplemento(resultado.getString("complcob"));
			duprec.setBairro(resultado.getString("baicob"));
			duprec.setCidade(resultado.getString("cidcob"));
			duprec.setUf(resultado.getString("estcob"));
			duprec.setCep(resultado.getString("cepcob"));
			duprec.setEmail(resultado.getString("email"));
			duprec.setValor(resultado.getFloat("VLRPGR"));
			duprec.setVencimento(resultado.getDate("vcto"));
			duprec.setEmissao(resultado.getDate("emis"));
			duprec.setDescricaoNfse(resultado.getString("DESCRSER"));

			Banco banco = new Banco();
			banco.setCodBco(resultado.getString("BANCO"));

			duprec.setBanco(banco);
			duprec.setTokenjuno(resultado.getString("tokenJuno"));
			duprec.setJuros(resultado.getBigDecimal("percjuros"));
			duprec.setMulta(resultado.getBigDecimal("percmulta"));
			duprec.setFilial(resultado.getString("codfil"));
			duprec.setNumender(resultado.getInt("numender"));

			duprecs.add(duprec);

		}
		return duprecs;
	}

	public List<Duprec> listarTitulosEmAtraso(String codigo) throws SQLException, ClassNotFoundException {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"select clifor.cpf, clifor.cgc, clifor.nom, clifor.endcob, clifor.complcob, clifor.baicob, clifor.cepcob, clifor.cidcob, clifor.estcob, clifor.email, ");
		sql.append(
				"duprec.id_duprec, duprec.percjuros, duprec.percmulta, duprec.numdup, duprec.ordem, duprec.nf, duprec.NumNFSe, duprec.emis, duprec.vcto, duprec.vlrpgr, ");
		sql.append(
				"duprec.banco, duprec.codfil, duprec.nossonum, DATEDIFF(CURDATE(), duprec.VCTO)as atraso, itfser.descrser, banco.tokenJuno from clifor inner join duprec on clifor.cod = duprec.codcli inner join ");
		sql.append(
				"itfser on itfser.nfser = duprec.nf inner join banco on banco.Codbco = duprec.BANCO where duprec.banco='0020' and duprec.nossonum <> '' AND duprec.DTPG IS null and duprec.vcto < CURDATE()");

		Connection conexao = ConexaoFactory.conectarMySql(codigo);

		PreparedStatement comando = conexao.prepareStatement(sql.toString());

		ResultSet resultado = comando.executeQuery();

		List<Duprec> duprecs = new ArrayList<Duprec>();

		while (resultado.next()) {
			Duprec duprec = new Duprec();
			duprec.setId_duprec(resultado.getInt("id_duprec"));
			duprec.setNumdup(resultado.getString("numdup"));
			duprec.setOrdem(resultado.getString("ordem"));
			duprec.setNf(resultado.getString("nf"));
			duprec.setNumNFSe(resultado.getString("NumNFSe"));
			duprec.setDescricao(resultado.getString("nom"));

			if (resultado.getString("cpf") != "") {
				duprec.setCpfCnpj(resultado.getString("CPF"));
			} else if (resultado.getString("CGC") != "") {
				duprec.setCpfCnpj(resultado.getString("CGC"));
			}
			duprec.setEndereco(resultado.getString("endcob"));
			duprec.setComplemento(resultado.getString("complcob"));
			duprec.setBairro(resultado.getString("baicob"));
			duprec.setCidade(resultado.getString("cidcob"));
			duprec.setUf(resultado.getString("estcob"));
			duprec.setCep(resultado.getString("cepcob"));
			duprec.setEmail(resultado.getString("email"));
			duprec.setValor(resultado.getFloat("VLRPGR"));
			duprec.setVencimento(resultado.getDate("vcto"));
			duprec.setEmissao(resultado.getDate("emis"));
			duprec.setDescricaoNfse(resultado.getString("DESCRSER"));

			Banco banco = new Banco();
			banco.setCodBco(resultado.getString("BANCO"));

			duprec.setBanco(banco);
			duprec.setTokenjuno(resultado.getString("tokenJuno"));
			duprec.setJuros(resultado.getBigDecimal("percjuros"));
			duprec.setMulta(resultado.getBigDecimal("percmulta"));
			duprec.setAtraso(resultado.getInt("atraso"));
			duprec.setFilial(resultado.getString("codfil"));
			float multa = ((resultado.getBigDecimal("vlrpgr").floatValue() * 2) / 100);
			float juros = (float) ((resultado.getBigDecimal("vlrpgr").floatValue() * (duprec.getAtraso() * (4.5 / 30)))
					/ 100);
			float vlrAtualizado = (float) ((resultado.getBigDecimal("vlrpgr").floatValue() + juros + multa));
			duprec.setNossonum(resultado.getString("nossonum"));
			duprec.setMulta(BigDecimal.valueOf(multa));
			duprec.setJuros(BigDecimal.valueOf(juros));
			duprec.setVlrAtualizado(BigDecimal.valueOf(vlrAtualizado));

			duprecs.add(duprec);
		}
		return duprecs;

	}

	public Double mostrarSaldoInadimplentePorMes(LocalDateTime mes, String codigo)
			throws SQLException, ClassNotFoundException {

		StringBuilder sql = new StringBuilder();
		sql.append("select sum(duprec.vlrpgr) as total from duprec where duprec.vlrpgo <= duprec.vlrpgr ");
		sql.append("and month(duprec.emis) = ? and year(duprec.emis) = ?");

		Connection conexao = ConexaoFactory.conectarMySql(codigo);

		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		comando.setLong(1, mes.getMonthValue());
		comando.setLong(2, mes.getYear());

		ResultSet resultado = comando.executeQuery();
		Double saldo = null;

		if (resultado.next()) {
			saldo = resultado.getDouble("total");
		}

		return saldo;
	}

	public List<Duprec> listarSaldosEmAberto(String codigo) throws SQLException, ClassNotFoundException {
		StringBuilder sql = new StringBuilder();

		sql.append(
				"select sum(duprec.VLRPGR), duprec.VLRPGR, duprec.banco from duprec where duprec.vlrpgo is null group by duprec.banco");

		Connection conexao = ConexaoFactory.conectarMySql(codigo);

		PreparedStatement comando = conexao.prepareStatement(sql.toString());

		ResultSet resultado = comando.executeQuery();

		List<Duprec> saldos = new ArrayList<Duprec>();

		while (resultado.next()) {
			Duprec saldo = new Duprec();
			saldo.setValor(resultado.getFloat("VLRPGR"));
			saldos.add(saldo);
		}

		return saldos;
	}

	public void atualizarDupRec(String code, int id_duprec, String codigo) throws SQLException, ClassNotFoundException {
		StringBuilder sql = new StringBuilder();
		sql.append("update duprec set duprec.nossonum = ? where duprec.id_duprec = ?");

		Connection conexao = ConexaoFactory.conectarMySql(codigo);
		PreparedStatement comando = conexao.prepareStatement(sql.toString());

		comando.setString(1, code);
		comando.setInt(2, id_duprec);
		comando.execute();
	}

	public void atualizarDupRecGalaxPay(Boleto bol, String codigo) throws SQLException, ClassNotFoundException {

		CliforDAO daoClifor = new CliforDAO();

		ArquivoUtil util = new ArquivoUtil();
		String[] string = util.extrairVariaveis(bol.getMyId());
		// System.err.println("String vindo do extrair: "+string);

		StringBuilder sql = new StringBuilder();
		String nossoNumero = bol.getBankNumber();
		String galaxPayId = String.valueOf(bol.getGalaxPayId());
		System.out.println("Antes de pegar a String");
		String numDup = string[0];
		String ordem = string[1];
		System.out.println("Nosso Numero: " + nossoNumero + " NumCheque: " + galaxPayId + " NumDup: " + string[0] + "-"
				+ string[1]);

		sql.append(
				"UPDATE duprec SET duprec.nossonum = ?, duprec.numchq = ? where duprec.numdup = ? and duprec.ordem = ? and duprec.banco = ?");

		Connection conexao = ConexaoFactory.conectarMySql(codigo);
		PreparedStatement comando = conexao.prepareStatement(sql.toString());

		comando.setString(1, nossoNumero);
		comando.setString(2, galaxPayId);
		comando.setString(3, numDup);
		comando.setString(4, ordem);
		comando.setString(5, "0050");
		try {
			comando.execute();
		} catch (SQLException e) {
			System.err.println("Erro ao executar a consulta SQL: " + e.getMessage());

			e.printStackTrace();
		}

	}

	public void atualizarDupRecAtualizacao(String code, int id_duprec, String codigo, String obs)
			throws SQLException, ClassNotFoundException {
		StringBuilder sql = new StringBuilder();
		sql.append("update duprec SET duprec.nossonum = ?, duprec.OBS = ? where duprec.id_duprec = ?");

		Connection conexao = ConexaoFactory.conectarMySql(codigo);
		PreparedStatement comando = conexao.prepareStatement(sql.toString());

		comando.setString(1, code);
		comando.setString(2, obs);
		comando.setInt(3, id_duprec);
		comando.execute();
	}

	public Duprec buscarToken(Duprec boleto) {
		return boleto;
	}

	public List<Duprec> listarDuplicatasEmAberto(String codigo) throws SQLException, ClassNotFoundException {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"SELECT duprec.VLRPGR, duprec.EMIS, duprec.CODCLI, duprec.numdup, duprec.ordem, duprec.VCTO, clifor.nom, banco.Codbco, banco.Nombco ");
		sql.append("FROM duprec INNER JOIN clifor ON clifor.COD = duprec.CODCLI INNER JOIN banco ON banco.Codbco ");
		sql.append("= duprec.BANCO WHERE duprec.VLRPGO IS null or duprec.VLRPGO = 0 ORDER BY VCTO");

		Connection conexao = ConexaoFactory.conectarMySql(codigo);

		PreparedStatement comando = conexao.prepareStatement(sql.toString());

		ResultSet resultado = comando.executeQuery();

		List<Duprec> duprecs = new ArrayList<Duprec>();

		while (resultado.next()) {
			Clifor cli = new Clifor();
			Banco banco = new Banco();
			Duprec duprec = new Duprec();
			duprec.setValor(resultado.getFloat("VLRPGR"));
			duprec.setNumdup(resultado.getString("numdup"));
			cli.setCod(resultado.getString("CodCli"));
			cli.setNom(resultado.getString("nom"));
			duprec.setCodcli(cli);
			duprec.setOrdem(resultado.getString("ordem"));
			banco.setCodBco(resultado.getString("CodBco"));
			banco.setNomBco(resultado.getString("nomBco"));
			duprec.setBanco(banco);
			duprec.setVencimento(resultado.getDate("VCTO"));
			duprecs.add(duprec);
		}

		return duprecs;
	}

	public Duprec buscaDuplicata(String codigo, String nossonum) throws SQLException, ClassNotFoundException {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM duprec WHERE duprec.nossonum = ?");

		Connection conexao = ConexaoFactory.conectarMySql(codigo);

		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		comando.setString(1, nossonum);

		ResultSet resultado = comando.executeQuery();
		Duprec duprec = new Duprec();
		Clifor cli = new Clifor();

		if (resultado.next()) {
			duprec.setValor(resultado.getFloat("VLRPGR"));
			duprec.setVencimento(resultado.getDate("VCTO"));
			cli.setCod(resultado.getString("codcli"));
			duprec.setCodcli(cli);
			duprec.setEmissao(resultado.getDate("Emis"));
			Banco banco = new Banco();
			banco.setCodBco(resultado.getString("banco"));
			duprec.setBanco(banco);
			duprec.setDescricao(resultado.getString("nom"));
			duprec.setNumdup(resultado.getString("numdup"));
			duprec.setOrdem(resultado.getString("ordem"));
			duprec.setId_galaxpay(resultado.getString("numchq"));
		}

		return duprec;
	}

	public float totalEmAberto(String codigo) throws SQLException, ClassNotFoundException {
		float total = 0;

		StringBuilder sql = new StringBuilder();
		sql.append("select sum(vlrpgr) as total from duprec where duprec.vlrpgo is null");

		Connection conexao = ConexaoFactory.conectarMySql(codigo);

		PreparedStatement comando = conexao.prepareStatement(sql.toString());

		ResultSet resultado = comando.executeQuery();
		if (resultado.next()) {
			total = resultado.getFloat("total");
		}
		return total;
	}

	public List<Duprec> maioresClientesDoAno(String codigo) throws SQLException, ClassNotFoundException {
		StringBuilder sql = new StringBuilder();
		sql.append("select sum(fatser.totnf) as total, clifor.nom from fatser ");
		sql.append(
				"inner join clifor on clifor.cod = fatser.codcli where (year(fatser.datemi) = year(current_date())) ");
		sql.append("group by clifor.cod order by total desc limit 5");

		Connection conexao = ConexaoFactory.conectarMySql(codigo);

		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		// comando.setLong(1, limite);

		ResultSet resultado = comando.executeQuery();

		List<Duprec> duprecs = new ArrayList<Duprec>();

		if (resultado.next()) {
			Duprec duprec = new Duprec();
			duprec.setDescricao(resultado.getString("nom"));
			duprec.setValor(resultado.getFloat("total"));

			duprecs.add(duprec);
		}
		return duprecs;
	}

	public float totalFaturamentoNoMesDoGrupo(String codigo) throws SQLException, ClassNotFoundException {
		StringBuilder sql = new StringBuilder();
		sql.append("select sum(fatser.totnf) as total from fatser where month(fatser.datemi) = month(current_date()) ");
		sql.append("and year(fatser.datemi) = year(current_date()) and fatser.flag = '' order by fatser.datemi");

		Connection conexao = ConexaoFactory.conectarMySql(codigo);
		PreparedStatement comando = conexao.prepareStatement(sql.toString());

		ResultSet resultado = comando.executeQuery();

		float VlrTotal = 0;

		if (resultado.next()) {
			VlrTotal = resultado.getFloat("total");
		}

		return VlrTotal;
	}

	public float totalFaturamentoNoMesAnteriorDoGrupo(String codigo) throws SQLException, ClassNotFoundException {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"select sum(fatser.totnf) as total from fatser where month(fatser.datemi) = month(current_date())-1 ");
		sql.append("and year(fatser.datemi) = year(current_date()) and fatser.flag = '' order by fatser.datemi");

		Connection conexao = ConexaoFactory.conectarMySql(codigo);
		PreparedStatement comando = conexao.prepareStatement(sql.toString());

		ResultSet resultado = comando.executeQuery();

		float VlrTotal = 0;

		if (resultado.next()) {
			VlrTotal = resultado.getFloat("total");
		}

		return VlrTotal;
	}

	public float totalFaturamentoNoAnoDoGrupo(String codigo) throws SQLException, ClassNotFoundException {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"select sum(fatser.totnf) as total from fatser where year(fatser.datemi) = year(current_date()) and fatser.flag = '' order by fatser.datemi");

		Connection conexao = ConexaoFactory.conectarMySql(codigo);
		PreparedStatement comando = conexao.prepareStatement(sql.toString());

		ResultSet resultado = comando.executeQuery();

		float VlrTotal = 0;

		if (resultado.next()) {
			VlrTotal = resultado.getFloat("total");
		}

		return VlrTotal;
	}

	public float totalFaturamentoNoAnoDoGrupoAnoAnetrior(String codigo) throws SQLException, ClassNotFoundException {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"select sum(fatser.totnf) as total from fatser where year(fatser.datemi) = year(current_date()) -1  and fatser.flag = '' order by fatser.datemi");

		Connection conexao = ConexaoFactory.conectarMySql(codigo);
		PreparedStatement comando = conexao.prepareStatement(sql.toString());

		ResultSet resultado = comando.executeQuery();

		float VlrTotal = 0;

		if (resultado.next()) {
			VlrTotal = resultado.getFloat("total");
		}

		return VlrTotal;
	}

	public float totalFaturamentoNoMesDoGrupoAnoAnetrior(String codigo) throws SQLException, ClassNotFoundException {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"select sum(fatser.totnf) as total from fatser where MONTH(fatser.datemi) = MONTH(CURRENT_DATE) AND YEAR(fatser.datemi) = YEAR(CURRENT_DATE)-1 ");
		sql.append("and fatser.flag = ''order by fatser.datemi");

		Connection conexao = ConexaoFactory.conectarMySql(codigo);
		PreparedStatement comando = conexao.prepareStatement(sql.toString());

		ResultSet resultado = comando.executeQuery();

		float VlrTotal = 0;

		if (resultado.next()) {
			VlrTotal = resultado.getFloat("total");
		}

		return VlrTotal;
	}

	public double saldoEmAberto(String codigo) {

		return 0;

	}

	public void baixarDupRec(Duprec duprec, String codigo, double registroBanco)
			throws SQLException, ClassNotFoundException {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"update duprec set duprec.VLRPGO = ?, duprec.DTPG = ?, duprec.APRESENTADO = ?, duprec.credmov = ?, ");
		sql.append("duprec.REGBCO = ? WHERE duprec.NUMDUP = ? AND duprec.ORDEM = ?");

		Connection conexao = ConexaoFactory.conectarMySql(codigo);
		PreparedStatement comando = conexao.prepareStatement(sql.toString());

		comando.setBigDecimal(1, duprec.getValorPago());
		comando.setDate(2, duprec.getDataPagamento());
		comando.setDate(3, duprec.getDataPagamento());
		comando.setDate(4, duprec.getDataPagamento());
		comando.setDouble(5, registroBanco);
		comando.setString(6, duprec.getNumdup());
		comando.setString(7, duprec.getOrdem());

		comando.execute();
	}

	public List<Duprec> buscarBoletoABaixar(String nossonum, Date pagamento, String codigo)
			throws SQLException, ClassNotFoundException {

		StringBuilder sql = new StringBuilder();
		sql.append(
				"select duprec.vlrpgr, duprec.emis, duprec.codcli, duprec.numdup, duprec.nf, duprec.ordem, duprec.vcto, clifor.nom, banco.codbco, banco.nombco ");
		sql.append(
				"from duprec inner join clifor on clifor.cod = duprec.codcli inner join banco on banco.codbco = duprec.banco where duprec.nossonum = ? ");
		sql.append("and (duprec.vlrpgo is null or duprec.vlrpgo = 0) order by vcto");

		Connection conexao = ConexaoFactory.conectarMySql(codigo);
		PreparedStatement comando = conexao.prepareStatement(sql.toString());

		comando.setString(1, nossonum);

		ResultSet resultado = comando.executeQuery();

		Duprec dup = new Duprec();

		if (resultado.next()) {
			dup.setValor(resultado.getFloat("vlrpgr"));
			dup.setDescricao(resultado.getString("nom"));
			dup.setEmissao(resultado.getDate("emissao"));
			dup.setDataPagamento(pagamento);
		}

		return (List<Duprec>) dup;

	}

	public static void main(String[] args) throws SQLException, ClassNotFoundException, ParseException {
		DuprecDAO dao = new DuprecDAO();
		dao.listarHistoricoWA(3425, "1000");

	}

	public List<Duprec> listarDuplicatasGalaxPay(String codigo) throws SQLException, ClassNotFoundException {

		StringBuilder sql = new StringBuilder();
		sql.append("select duprec.numdup, duprec.ordem, duprec.id_duprec, duprec.percjuros, duprec.percmulta,\r\n"
				+ "duprec.nf, duprec.numnfse, duprec.emis, duprec.vcto, duprec.vlrpgr, duprec.banco, duprec.codfil,\r\n"
				+ "concat('ref a servicos prestados no mes de: ', date_format(duprec.emis, '%m/%y')) as descrser, \r\n"
				+ "replace(concat(clifor.dddcob1, replace(clifor.telecob1, '-', ''), ' ', ''), ' ', '') as fone, \r\n"
				+ "clifor.numender, clifor.id_galaxpay, clifor.cod,  clifor.cpf, clifor.cgc, clifor.nom, clifor.endcob,\r\n"
				+ "clifor.complcob, clifor.baicob, clifor.cepcob, clifor.cidcob, clifor.estcob, clifor.email, \r\n"
				+ "clifor.numender from duprec inner join clifor on clifor.cod = duprec.codcli inner join itfser on itfser.nfser = duprec.nf \r\n"
				+ "inner join banco on banco.codbco = duprec.banco where duprec.banco = '0050' and (duprec.nossonum = '' or duprec.nossonum is null)\r\n"
				+ "and (duprec.vlrpgo = 0 or duprec.vlrpgo is null) and duprec.vcto >= curdate() and clifor.id_galaxpay is not null\r\n"
				+ "group by duprec.numdup,\r\n" + "duprec.ordem;");

		Connection conexao = ConexaoFactory.conectarMySql(codigo);
		PreparedStatement comando = conexao.prepareStatement(sql.toString());

		ResultSet resultado = comando.executeQuery();

		List<Duprec> duprecs = new ArrayList<Duprec>();

		while (resultado.next()) {
			Duprec dup = new Duprec();
			dup.setId_duprec(resultado.getInt("id_duprec"));
			dup.setValor(resultado.getFloat("vlrpgr"));
			dup.setDescricao(resultado.getString("nom"));
			dup.setEmissao(resultado.getDate("emis"));
			dup.setDescricaoNfse(resultado.getString("descrser"));
			dup.setVencimento(resultado.getDate("vcto"));
			dup.setEmail(resultado.getString("email"));
			dup.setTelefone(resultado.getString("fone"));
			dup.setCep(resultado.getString("cepcob"));
			dup.setEndereco(resultado.getString("endcob"));
			dup.setNumender(Integer.parseInt(resultado.getString("numender")));
			dup.setComplemento(resultado.getString("complcob"));
			dup.setBairro(resultado.getString("baicob"));
			dup.setCidade(resultado.getString("cidcob"));
			dup.setUf(resultado.getString("estcob"));
			dup.setNumdup(resultado.getString("numdup"));
			dup.setOrdem(resultado.getString("ordem"));
			dup.setNumNFSe(resultado.getString("numnfse"));
			dup.setFilial(resultado.getString("codfil"));
			dup.setId_galaxpay(resultado.getString("id_galaxpay"));
			dup.setCod(resultado.getString("cod"));

			if (resultado.getString("id_galaxpay") != null) {
				dup.setDisable(true);
				System.out.println("Cliente cadastrado no GalaxPay" + dup.getId_galaxpay());
			} else {
				dup.setDisable(false);
				System.err.println("Cliente sem GalaxPay");
			}

			if (resultado.getString("cpf").length() > 0) {
				dup.setCpfCnpj(resultado.getString("CPF"));
			} else {
				dup.setCpfCnpj(resultado.getString("CGC"));
			}
			duprecs.add(dup);
		}
		return duprecs;
	}

	public float mostrarVencimentoNoMesCorrente(String codigo) throws ClassNotFoundException, SQLException {
		StringBuilder sql = new StringBuilder();

		sql.append(
				"select sum(duprec.vlrpgr) as total from duprec where month(duprec.vcto) = month(current_date()) and year(duprec.vcto) = year(current_date())");
		Connection conexao = ConexaoFactory.conectarMySql(codigo);

		PreparedStatement comando = conexao.prepareStatement(sql.toString());

		ResultSet resultado = comando.executeQuery();

		Float totalReceberMesCorrenteFloat = null;

		if (resultado.next()) {
			totalReceberMesCorrenteFloat = resultado.getFloat("total");
		}
		return totalReceberMesCorrenteFloat;
	}

	public String selecionaClienteExtrato(double numcheq, String codigo) throws SQLException, ClassNotFoundException {
		StringBuilder sql = new StringBuilder();
		sql.append("select clifor.nom as cliente from duprec inner join clifor ");
		sql.append("on duprec.codcli = clifor.cod where duprec.numchq = ?");

		Connection conexao = ConexaoFactory.conectarMySql(codigo);

		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		// comando.setLong(1, limite);

		comando.setDouble(1, numcheq);

		ResultSet resultado = comando.executeQuery();

		String cliente = "";

		if (resultado.next()) {
			cliente = resultado.getString("cliente");
		}
		return cliente;
	}

	public List<String[]> lerDuprecTresMeses(String codigo) throws ClassNotFoundException, SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"SELECT Concat(LPAD(Day(duprec.VCTO),2,'00'),'-',(LPAD(MONTH(duprec.VCTO),2,'00'))) AS vencimento, SUM(duprec.VLRPGR) AS valor FROM duprec\r\n"
						+ "WHERE DATE_FORMAT(vcto, '%Y%m') IN (\r\n"
						+ "    DATE_FORMAT(CURDATE(), '%Y%m'), -- Mês atual\r\n"
						+ "    DATE_FORMAT(DATE_SUB(CURDATE(), INTERVAL 1 MONTH), '%Y%m'), -- Mês anterior\r\n"
						+ "    DATE_FORMAT(DATE_ADD(CURDATE(), INTERVAL 1 MONTH), '%Y%m') -- Próximo mês\r\n"
						+ ") AND (duprec.dtpg is null)\r\n" + "\r\n" + "GROUP BY duprec.VCTO\r\n"
						+ "ORDER BY duprec.VCTO");

		Connection conexao = ConexaoFactory.conectarMySql(codigo);

		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		ResultSet resultado = comando.executeQuery();

		List<String[]> listaDeValores = new ArrayList<>();

		while (resultado.next()) {
			String[] valores = new String[2];

			valores[0] = resultado.getString("vencimento");
			valores[1] = resultado.getString("valor");

			listaDeValores.add(valores);

		}
		return listaDeValores;
	}

	public List<Duprec> listarDuprecBoletosAEnviarWA(String dtInicial, String dtFinal, String codigo, String enviados)
			throws ClassNotFoundException, SQLException, ParseException {

		System.out.println("Dentro do DAO> " + enviados);
		StringBuilder sql = new StringBuilder();
		Date dInicial = sdfEntrada.parse(dtInicial);
		Date dFinal = sdfEntrada.parse(dtFinal);

		String dFInicial = sdfSaida.format(dInicial);
		String dFFinal = sdfSaida.format(dFinal);

		sql.append(
				"select duprec.id_duprec, concat(duprec.numdup,\"-\",duprec.ordem) as duplicata, duprec.numchq, duprec.vcto, duprec.emis, duprec.vlrpgr, duprec.tokenjuno, concat(clifor.dddcob1, clifor.TELECOB1) AS telefone, clifor.nom, clifor.cod from duprec\r\n"
						+ "inner join clifor on duprec.codcli = clifor.cod\r\n"
						+ "where banco = '0050' and dtpg is null and numchq is not null AND (duprec.EMIS BETWEEN ? AND ?)");

		if (enviados.equals("IS NULL")) {
			sql.append(" AND duprec.tokenjuno IS NULL");
		} else if (enviados.equals("IS NOT NULL")) {
			sql.append(" AND duprec.tokenjuno IS NOT NULL");
		}

		Connection conexao = ConexaoFactory.conectarMySql(codigo);

		PreparedStatement comando = conexao.prepareStatement(sql.toString());

		comando.setString(1, dFInicial);
		comando.setString(2, dFFinal);

		ResultSet resultado = comando.executeQuery();

		List<Duprec> duprecs = new ArrayList<>();

		while (resultado.next()) {
			Duprec duprec = new Duprec();
			duprec.setNumdup(resultado.getString("duplicata"));
			duprec.setId_galaxpay(resultado.getString("numchq"));
			duprec.setVencimento(resultado.getDate("vcto"));
			duprec.setEmissao(resultado.getDate("emis"));
			duprec.setCliente(resultado.getString("nom"));
			duprec.setTelefone(resultado.getString("telefone"));
			duprec.setValor(resultado.getFloat("vlrpgr"));
			duprec.setTokenjuno(resultado.getString("tokenjuno"));
			duprec.setId_duprec(resultado.getInt("id_duprec"));

			System.out.println(duprec.getTokenjuno());

			duprecs.add(duprec);
		}
		return duprecs;
	}

	public void updateWhatsAppDuprec(Duprec duprec, String data, Empresas empresa, String obs, String tel)
			throws ClassNotFoundException, SQLException {

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		java.util.Date dateUtil = null;
		try {
			dateUtil = sdf.parse(data);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		java.sql.Date sqlDate = new java.sql.Date(dateUtil.getTime());

		System.out.println("Data: " + data + " - Numcheq: " + duprec.getId_galaxpay() + " - " + duprec.getFilial());
		System.out.println();

		StringBuilder sqlUpdate = new StringBuilder();
		sqlUpdate.append("UPDATE duprec SET tokenjuno = ?, obs = ? WHERE id_duprec = ?");

		StringBuilder sqlInsert = new StringBuilder();
		sqlInsert.append("INSERT INTO historicowa (id_duprec, data_historico, historico, whatsapp) ");
		sqlInsert.append("VALUES (?, ?, ?, ?)");

		Connection conexao = ConexaoFactory.conectarMySql(empresa.getUsu());

		// Executando a atualização dos dados na tabela duprec
		PreparedStatement comandoUpdate = conexao.prepareStatement(sqlUpdate.toString());
		comandoUpdate.setString(1, data);
		comandoUpdate.setString(2, obs);
		comandoUpdate.setInt(3, duprec.getId_duprec());
		comandoUpdate.executeUpdate(); // Use executeUpdate() para operações de atualização

		// Executando a inserção de dados na tabela historicowa
		PreparedStatement comandoInsert = conexao.prepareStatement(sqlInsert.toString());
		comandoInsert.setInt(1, duprec.getId_duprec());
		comandoInsert.setDate(2, sqlDate);
		comandoInsert.setString(3, obs);
		comandoInsert.setString(4, tel);
		comandoInsert.execute(); // Use executeUpdate() para operações de atualização

		conexao.close(); // Feche a conexão após a conclusão das operações
	}

	public List<Duprec> listarDuprecCincoDiasAtraso(Empresas empresa)
			throws ClassNotFoundException, SQLException, InterruptedException, IOException, UnirestException {
		StringBuilder sql = new StringBuilder();
		GalaxPayServices servicesGalaxPay = new GalaxPayServices();

		sql.append(
				"select concat(duprec.numdup,\"-\",duprec.ordem) as dup, duprec.vcto, duprec.vlrpgr, clifor.nom, \r\n"
						+ "concat(clifor.dddcob1, clifor.telecob1) as telefone, clifor.contcob1 as contato1, \r\n"
						+ "concat(clifor.dddcob2, clifor.telecob2) as telefone2, clifor.contcob2 as contato2\r\n"
						+ "from duprec inner join clifor on clifor.cod = duprec.codcli\r\n"
						+ "where (vcto = date_sub(curdate(), interval 7 day) or vcto = date_sub(curdate(), interval 5 day)) \r\n"
						+ "and duprec.dtpg is null and duprec.banco = '0050'");

		Connection conexao = ConexaoFactory.conectarMySql(empresa.getUsu());

		PreparedStatement comando = conexao.prepareStatement(sql.toString());

		ResultSet resultado = comando.executeQuery();

		List<Duprec> duprecs = new ArrayList<>();

		while (resultado.next()) {
			Duprec duprec = new Duprec();
			duprec.setVencimento(resultado.getDate("vcto"));
			duprec.setValor(resultado.getFloat("vlrpgr"));
			duprec.setNumdup(resultado.getString("dup"));
			duprec.setCliente(resultado.getString("nom"));
			// duprec.setTelefone(resultado.getString("telefone"));
			duprec.setTelefone("62982592220");
			duprec.setContato1(resultado.getString("contato1"));
			duprec.setTelefone2("62982592220");
			// duprec.setTelefone2(resultado.getString("telefone2"));
			duprec.setContato2(resultado.getString("contato2"));
			duprec.setUrlPDF(servicesGalaxPay.gerarPDFCobranca(duprec.getId_galaxpay(), empresa.getUsu()));
			duprecs.add(duprec);
		}

		return duprecs;

	}

	public Telefones listarTelefonesDuprec(Duprec duprec, Empresas empresa)
			throws ClassNotFoundException, SQLException {

		StringBuilder sql = new StringBuilder();
		sql.append(
				"SELECT concat(clifor.dddcob1, REPLACE(clifor.TELECOB1,'-',''))  AS telefone1, clifor.CONTCOB1 AS contato1, concat(clifor.dddcob2, REPLACE(clifor.TELECOB2,'-',''))  AS telefone2, clifor.CONTCOB2 AS contato2, clifor.nom, clifor.COD, duprec.NUMDUP, duprec.ORDEM, duprec.codfil ");
		sql.append(
				"FROM clifor INNER JOIN duprec ON duprec.CODCLI = clifor.COD WHERE (duprec.NUMDUP = ? AND duprec.ORDEM = ? ) and duprec.id_duprec = ?");

		Connection conexao = ConexaoFactory.conectarMySql(empresa.getUsu());

		PreparedStatement comando = conexao.prepareStatement(sql.toString());

		comando.setString(1, duprec.getNumdup());
		comando.setString(2, duprec.getOrdem());
		comando.setInt(3, duprec.getId_duprec());

		ResultSet resultado = comando.executeQuery();

		Telefones telefones = new Telefones();

		if (resultado.next()) {
			telefones.setContato1(resultado.getString("contato1"));
			telefones.setTelefone1(resultado.getString("telefone1"));
			telefones.setContato2(resultado.getString("contato2"));
			telefones.setTelefone2(resultado.getString("telefone2"));
		}
		return telefones;
	}

	public List<HistoricoWA> listarHistoricoWA(int id, String codigo) throws ClassNotFoundException, SQLException {

		StringBuilder sql = new StringBuilder();
		sql.append("select concat(duprec.numdup,'-',duprec.ordem) as dup, historicowa.historico, historicowa.data_historico, historicowa.whatsapp  from historicowa \r\n"
				+ "inner join duprec on historicowa.id_duprec = duprec.id_duprec\r\n"
				+ "where historicowa.id_duprec = ?");

		Connection conexao = ConexaoFactory.conectarMySql(codigo);

		PreparedStatement comando = conexao.prepareStatement(sql.toString());

		comando.setInt(1, id);

		ResultSet resultado = comando.executeQuery();

		List<HistoricoWA> historicos = new ArrayList<>();

		while (resultado.next()) {
			HistoricoWA hist = new HistoricoWA();
			hist.setData_historico(resultado.getDate("data_historico"));
			hist.setHistorico(resultado.getString("historico"));
			hist.setWhastApp(resultado.getString("whatsapp"));
			hist.setDuplicata(resultado.getString("dup"));
			historicos.add(hist);

			System.out.println(hist.getHistorico());

		}
		return historicos;

	}

}