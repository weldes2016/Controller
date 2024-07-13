package br.com.controller.bean;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import com.mashape.unirest.http.exceptions.UnirestException;

import br.com.controller.dao.DuprecDAO;
import br.com.controller.domain.Duprec;
import br.com.controller.domain.Empresas;
import br.com.controller.domain.Parametros;
import br.com.controller.galaxpay.domain.Balance;
import br.com.controller.galaxpay.domain.BalanceItem;
import br.com.controller.galaxpay.services.GalaxPayServices;
import br.com.controller.galaxpay.services.ObterToken;
import br.com.controller.galaxpay.util.EnviarEmailConfirmacao;
import br.com.controller.util.ArquivoUtil;
import br.com.controller.util.LerPropriedades;

@Named(value = "MBGalaxPay")
@SessionScoped
public class GalaxPayBean implements Serializable {

	private static final long serialVersionUID = 1363592521755783564L;
	List<BalanceItem> itens = new ArrayList<>();
	List<Balance> balances = new ArrayList<>();
	Balance balan = new Balance();
	Balance balanResult = new Balance();
	Boolean habilitarDatas = false;
	String dtInicial = "";
	String dtFinal = "";
	GalaxPayServices servicesGalaxPay = new GalaxPayServices();
	
	

	public GalaxPayServices getServicesGalaxPay() {
		return servicesGalaxPay;
	}

	public void setServicesGalaxPay(GalaxPayServices servicesGalaxPay) {
		this.servicesGalaxPay = servicesGalaxPay;
	}

	public Balance getBalanResult() {
		return balanResult;
	}

	public void setBalanResult(Balance balanResult) {
		this.balanResult = balanResult;
	}

	public List<BalanceItem> getItens() {
		return itens;
	}

	public void setItens(List<BalanceItem> itens) {
		this.itens = itens;
	}

	public List<Balance> getBalances() {
		return balances;
	}

	public void setBalances(List<Balance> balances) {
		this.balances = balances;
	}

	public Balance getBalan() {
		return balan;
	}

	public void setBalan(Balance balan) {
		this.balan = balan;
	}

	public String getDtInicial() {
		return dtInicial;
	}

	public void setDtInicial(String dtInicial) {
		this.dtInicial = dtInicial;
	}

	public String getDtFinal() {
		return dtFinal;
	}

	public void setDtFinal(String dtFinal) {
		this.dtFinal = dtFinal;
	}

	public Boolean getHabilitarDatas() {
		return habilitarDatas;
	}

	public void setHabilitarDatas(Boolean habilitarDatas) {
		this.habilitarDatas = habilitarDatas;
	}

	public void enviarBoletosGalaxPay(List<Duprec> dups, String codigo)
			throws ClassNotFoundException, InterruptedException, IOException, SQLException, UnirestException {
		ObterToken obter = new ObterToken();
		List<String> galax = new ArrayList<String>();
		galax = obter.obterToken(codigo);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Parametros parametro = new Parametros();
		LerPropriedades ler = new LerPropriedades();
		parametro = ler.levarPropriedadesReserva();

		for (Duprec duprec : dups) {
			// System.out.println(duprec.getDescricao() + " - " +
			// duprec.getDescricaoNfse());
			String vcto = sdf.format(duprec.getVencimento());

			DecimalFormat df = new DecimalFormat("0.00");
			String valor = df.format(duprec.getValor()).replace(".", "").replace(",", "");

			String descricao = "Referente a NFS: " + duprec.getNumNFSe() + ". Emitida em: " + duprec.getEmissao() + " "
					+ duprec.getDescricaoNfse();

			String body = "{\"myId\": \"" + duprec.getNumdup() + "-" + duprec.getOrdem() + "\",\r\n" + "    \"value\": "
					+ valor + ",\r\n" + "    \"additionalInfo\": \"" + descricao + "\",\r\n" + "    \"payday\": \""
					+ vcto + "\",\r\n" + "    \"payedOutsideGalaxPay\": false,\r\n"
					+ "    \"mainPaymentMethodId\": \"boleto\",\r\n" + "    \"Customer\": {\r\n"
					+ "        \"myId\": \"" + duprec.getId_galaxpay() + "\",\r\n" + "        \"name\": \""
					+ duprec.getDescricao() + "\",\r\n" + "        \"document\": " + duprec.getCpfCnpj() + ",\n"
					+ "        \"emails\": [\r\n" + "            \"" + duprec.getEmail() + "\"\r\n" + "        ],\r\n"
					+ "        \"phones\": [\r\n" + "          \"" + duprec.getTelefone() + "\"\r\n" + "        ],\r\n"
					+ "        \"Address\": {\r\n" + "            \"zipCode\": \"" + duprec.getCep() + "\",\r\n"
					+ "            \"street\": \"" + duprec.getEndereco() + "\",\r\n" + "            \"number\": "
					+ duprec.getNumender() + ",\r\n" + "            \"complement\": \"" + duprec.getComplemento()
					+ "\",\r\n" + "            \"neighborhood\": \"" + duprec.getBairro() + "\",\r\n"
					+ "            \"city\": \"" + duprec.getCidade() + "\",\r\n" + "            \"state\": \""
					+ duprec.getUf() + "\"\r\n" + "        }\r\n" + "    },\r\n" + "    \"PaymentMethodBoleto\": {\r\n"
					+ "        \"fine\": 200,\r\n" + "        \"interest\": 450,\r\n" + "        \"instructions\": \""
					+ descricao + "\",\r\n" + "        \"deadlineDays\": 59\r\n" + "    },\r\n"
					+ "    \"PaymentMethodPix\": {\r\n" + "        \"fine\": 100,\r\n"
					+ "        \"interest\": 200,\r\n"
					+ "        \"instructions\": \"Pagar prefencialmente no PIX\",\r\n" + "        \"Deadline\": {\r\n"
					+ "            \"type\": \"days\",\r\n" + "            \"value\": 60\r\n" + "        }\r\n"
					+ "    }\r\n" + "}";

			if (servicesGalaxPay.enviarBoletos(body, galax, parametro).getStatus() == 200) {

				float total = 0;
				int qtde = 0;
				EnviarEmailConfirmacao enviar = new EnviarEmailConfirmacao();
				// enviar.enviarConfirmacao(parametro.getEmail(), parametro.getWhatsApp(),
				// total, qtde);

			} else {
				FacesMessage msg = new FacesMessage("Cadastro: " + "nao foi enviado");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
		}
	}

	public Balance extratoCelcoin(String codigo, String dtInicial, String dtFinal) throws ClassNotFoundException,
			InterruptedException, IOException, SQLException, UnirestException, ParseException {
		ArquivoUtil util = new ArquivoUtil();

		itens.clear();

		DuprecDAO dao = new DuprecDAO();
		GalaxPayServices services = new GalaxPayServices();
		Balance balanResult = services.listarExtrato(codigo, dtInicial, dtFinal);

		double saldo = balanResult.totals.sdinitial;

		System.out.println(balanResult.totals.sdinitial); // Corrigi para acessar o campo 'initial' de 'totals'

		// Iterar sobre a lista de balances dentro de balanResult
		BalanceItem balanceItemSInicial = new BalanceItem();

		balanceItemSInicial.setFriendlyDescription("Saldo Anterior a: " + dtInicial);
		balanceItemSInicial.setValorAcumulado(saldo);

		itens.add(balanceItemSInicial);
		for (BalanceItem item : balanResult.balances) {
			BalanceItem balanceItem = new BalanceItem();
			balanceItem.setCreatedAt(util.formataData(item.createdAt));
			if (item.getPaymentType().contains("credit")) {
				String cliente = dao.selecionaClienteExtrato(item.getTransactionGalaxPayId(), codigo);
				if (cliente != null) {
					balanceItem.setFriendlyDescription(item.getFriendlyDescription() + " - " + cliente);
				} else {
					balanceItem.setFriendlyDescription("GalaxPayId: " + item.getGalaxPayId() + " - Descricao: "
							+ item.getFriendlyDescription() + " - Cliente nao identificado");
				}
			} else {
				balanceItem.setFriendlyDescription(item.getFriendlyDescription());
			}
			balanceItem.setGalaxPayId(item.galaxPayId);
			balanceItem.setGroupPaymentType(item.getGroupPaymentType());
			balanceItem.setValue(item.getValue());
			balanceItem.setPaymentType(item.getPaymentType());
			balanceItem.setTransactionGalaxPayId(item.getTransactionGalaxPayId());
			saldo = calcularSaldoAcumulado(saldo, balanceItem);
			balanceItem.setValorAcumulado(saldo);

			System.out.println(balanceItem.getGalaxPayId() + " - " + balanceItem.getFriendlyDescription() + " - Valor: "
					+ balanceItem.getValue() + " Valor Acumulado: " + balanceItem.getValorAcumulado());
			itens.add(balanceItem);
		}

		return balanResult;
	}

	public double calcularSaldoAcumulado(double saldo, BalanceItem item) {
		saldo = item.getValue() + saldo;
		return saldo;
	}

	public void datasMesAtual() {
		ArquivoUtil util = new ArquivoUtil();
		List<String> data = util.datasMesAtual();
		dtInicial = data.get(0);
		dtFinal = data.get(1);
	}

	public void enviarBoletosWhatsApp(List<Duprec> duprec, Empresas empresa)
			throws ClassNotFoundException, InterruptedException, IOException, SQLException, UnirestException {

		for (Duprec duprec2 : duprec) {
			duprec2.setUrlPDF(servicesGalaxPay.gerarPDFCobranca(duprec2.getId_galaxpay(), empresa.getUsu()));
			// enviarWA.enviarMensagemBoleto(duprec2, empresa);
		}
	}

	public static void main(String[] args) throws ClassNotFoundException, InterruptedException, IOException,
			SQLException, UnirestException, ParseException {
		GalaxPayBean services = new GalaxPayBean();
		String dtInicial = "01/12/2023";
		String dtFinal = "31/12/2023";
		// balances = services.listarExtrato("2000", dtInicial, dtFinal );

		services.extratoCelcoin("2000", dtInicial, dtFinal);

	}
}
