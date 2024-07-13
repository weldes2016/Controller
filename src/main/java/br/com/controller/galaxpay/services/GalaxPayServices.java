package br.com.controller.galaxpay.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.mashape.unirest.http.exceptions.UnirestException;

import br.com.controller.domain.Clifor;
import br.com.controller.domain.Duprec;
import br.com.controller.domain.Parametros;
import br.com.controller.galaxpay.domain.Balance;
import br.com.controller.galaxpay.domain.BalanceItem;
import br.com.controller.galaxpay.domain.Boleto;
import br.com.controller.galaxpay.domain.Customers;
import br.com.controller.galaxpay.domain.GalaxPayBank;
import br.com.controller.galaxpay.domain.GalaxPayClifor;
import br.com.controller.galaxpay.domain.Root;
import br.com.controller.galaxpay.util.LerRetornos;
import br.com.controller.services.EnviarWA;
import br.com.controller.util.JacksonUtil;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

public class GalaxPayServices {
	// data no formato 2021-11-30
	// public HttpResponse<String> listarClientes(GalaxPayBank galax) {

	DecimalFormat df = new DecimalFormat("#.##");
	List<Balance> listBalances = new ArrayList<Balance>();

	public DecimalFormat getDf() {
		return df;
	}

	public void setDf(DecimalFormat df) {
		this.df = df;
	}

	public List<Balance> getListBalances() {
		return listBalances;
	}

	public void setListBalances(List<Balance> listBalances) {
		this.listBalances = listBalances;
	}

	public List<GalaxPayClifor> listarClientes(String codigo)
			throws IOException, ClassNotFoundException, SQLException, InterruptedException {

		ObterToken obter = new ObterToken();
		List<String> tokens = null;
		try {
			tokens = obter.obterToken(codigo);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		HttpResponse<String> response = Unirest
				.get(tokens.get(0) + "/v2/customers?startAt=0&limit=100&order=createdAt.asc")
				.header("Authorization", "Bearer " + tokens.get(2)).asString();
		System.out.println(response.getBody());

		Gson gson = new Gson();

		Root root = gson.fromJson(response.getBody(), Root.class);

		List<Customers> customers = root.getCustomers();

		List<GalaxPayClifor> cliforGalaxPay = new ArrayList<GalaxPayClifor>();

		for (Customers customers2 : customers) {
			GalaxPayClifor cli = new GalaxPayClifor();
			cli.setDocument(customers2.getDocument());
			cli.setName(customers2.getName());
			cli.setMyId(customers2.getMyId());
			// System.err.println(customers2.getStatus());
			if (customers2.getStatus().contains("active")) {
				cli.setStatus("Ativo");
			} else if (customers2.getStatus().contains("delayed")) {
				cli.setStatus("Atrasado");
			} else {
				if (customers2.getStatus().contains("withoutSubscriptionOrCharge")) {
					cli.setStatus("Sem registro de cobrancas");
				} else {
					if (customers2.getStatus().contains("inactive")) {
						cli.setStatus("Inativo");

					}
				}
			}

			System.out.println("Name " + cli.getName() + " Document:" + cli.getDocument() + " Status:" + cli.getStatus()
					+ " - Codigo: " + cli.getMyId());
			cliforGalaxPay.add(cli);

		}
		return cliforGalaxPay;

	}

	public void cadastrarClienteGalaxPay(List<Clifor> clientes, String codigo)
			throws ClassNotFoundException, SQLException, InterruptedException, IOException, UnirestException {

		ObterToken obter = new ObterToken();
		List<String> tokens = obter.obterToken(codigo);

		for (Clifor clifor : clientes) {
			System.out.println("Antes de enviar o cliente: " + clifor.getId_clifor());

			HttpResponse<String> response = Unirest.post(tokens.get(0) + "/v2/customers")
					.header("Authorization", "Bearer " + tokens.get(2)).header("Content-Type", "application/json")
					.body("{\n    \"myId\": \"" + clifor.getId_clifor() + "\",\n    \"name\": \"" + clifor.getNom()
							+ "\",\n    \"document\": \"" + clifor.getCpfcgc() + "\",\n    \"emails\": [\r\n        \""
							+ clifor.getEmail() + "\"\r\n" + "],\r\n    \"phones\": [\r\n" + "\"" + clifor.getFone()
							+ "\"\r\n" + "],\n    \"Address\": {\n        \"zipCode\": \"" + clifor.getCepcob()
							+ "\",\n        \"street\": \"" + clifor.getEndcob() + "\",\n        \"number\": \""
							+ clifor.getNumendercob() + "\",\n        \"complement\": \"" + clifor.getComplcob()
							+ "\",\n        \"neighborhood\": \"" + clifor.getBaicob() + "\",\n        \"city\": \""
							+ clifor.getCidcob() + "\",\n        \"state\": \"" + clifor.getEstcob() + "\"\n    }\n}")
					.asString();
			System.out.println("Retorno Cadastro Clientes:");
			System.out.println(response.getBody());

			LerRetornos lerRetorno = new LerRetornos();
			lerRetorno.lerRetornosClientes(response.getBody(), codigo);
		}

	}

	public void atualizarClientesGalaxPay(GalaxPayBank galax, Clifor clifor) {

		System.out.println("Document: " + clifor.getCpfcgc());

		HttpResponse<String> response = null;
		response = Unirest.put(galax.getUrl() + "/v2/customers/1/myId").header("Authorization", "Bearer " + galax.token)
				.header("Content-Type", "application/json UTF-8")
				.body("{\n    \"myId\": \"" + clifor.getId_galaxPay() + "\",\n    \"name\": \"" + clifor.getNom()
						+ "\",\n    \"document\": \"" + clifor.getCpfcgc() + "\",\n    \"emails\": [\n        \""
						+ clifor.getEmail() + "\"\n    ],\n    \"phones\": [\n" + clifor.getFone()
						+ "],\n    \"Address\": {\n        \"zipCode\": \"" + clifor.getCepcob()
						+ "\",\n        \"street\": \"" + clifor.getEndcob() + "\",\n        \"number\": \""
						+ clifor.getNumendercob() + "\",\n        \"complement\": \"" + clifor.getComplcob()
						+ "\",\n        \"neighborhood\": \"" + clifor.getBaicob() + "\",\n        \"city\": \""
						+ clifor.getCidcob() + "\",\n        \"state\": \"" + clifor.getEstcob() + "\"\n    }\n}")
				.asString();

		System.out.println(response.getBody());
	}

	public HttpResponse<String> salvarClientes(String body, GalaxPayBank galax) {
		HttpResponse<String> response = null;
		response = Unirest.post(galax.url + "/v2/customers").header("Authorization", "Bearer " + galax.token)
				.asString();

		// System.out.println(response.getBody());

		return response;
	}

	public HttpResponse<String> enviarBoletos(String body, List<String> tokens, Parametros par) {
		System.out.println(body);

		HttpResponse<String> response = Unirest.post(tokens.get(0) + "/v2/charges")
				.header("Authorization", "Bearer " + tokens.get(2)).header("Content-Type", "application/json")
				.body(body).asString();

		System.out.println("Retorno:" + response.getBody());
		return response;
	}

	public String buscarIDClienteGalaxPay(String body) {
		Gson gson = new Gson();
		String jsonInString = body.toString();
		GalaxPayClifor clifor = gson.fromJson(jsonInString, GalaxPayClifor.class);
		String id = clifor.getMyId();

		System.out.println("Id: " + id);
		return null;

	}

	public List<Boleto> listarBoletosGalaxPay(String codigo)
			throws IOException, ClassNotFoundException, InterruptedException, SQLException, UnirestException {

		ObterToken obter = new ObterToken();
		List<String> tokens = obter.obterToken(codigo);

		EnviarWA enviar = new EnviarWA();
		HttpResponse<String> response = Unirest.get(tokens.get(0)
				+ "/v2/charges?myIds=&galaxPayIds=&customerMyIds=&customerGalaxPayIds=&createdAtFrom=&createdAtTo=&createdOrUpdatedAtTo=&status=active&startAt=0&limit=100&order=createdAt.desc")
				.header("Authorization", "Bearer " + tokens.get(2)).asString();

		List<Boleto> boletos = new ArrayList<Boleto>();

		JSONObject obj = new JSONObject(response.getBody());
		JSONArray charges = obj.getJSONArray("Charges");
		for (Object object : charges) {
			Boleto bol = new Boleto();
			JSONObject obj2 = new JSONObject(object.toString());
			Float flValor = Float.parseFloat(obj2.get("value").toString());

			bol.setValor(flValor / 100);

			bol.setPaymentLink(obj2.get("paymentLink").toString());
			String customer = obj2.get("Customer").toString();
			JSONObject cust = new JSONObject(customer.toString());
			bol.setCliente(cust.get("name").toString());
			JSONArray emailsArray = cust.getJSONArray("emails");

			for (int j = 0; j < emailsArray.length(); j++) {
				String email = emailsArray.getString(j);
				bol.setEmail(email);
			}

			JSONArray transactions = obj2.getJSONArray("Transactions");
			for (Object object2 : transactions) {
				JSONObject obj3 = new JSONObject(object2.toString());
				// System.out.println(obj3.toString());
				bol.setPayDay(obj3.get("payday").toString());
				// System.out.println("GalaxPayId: "+obj3.get("galaxPayId") + " Valor:
				// "+Integer.toString((int) bol.getValor())+ " Vencimento: " +bol.getPayDay());
				bol.setGalaxPayId(obj3.get("galaxPayId").toString());
				String strBoleto = obj3.get("Boleto").toString();
				JSONObject obj4 = new JSONObject(strBoleto.toString());
				bol.setBankNumber(obj4.get("bankNumber").toString());
				bol.setPdf(obj4.getString("pdf"));
				// enviar.enviarBoletoWhatsApp(bol.getPaymentLink(),
				// bol.getCliente(),"62982592220");
				boletos.add(bol);
			}

		}
		return boletos;
	}

	public Balance listarExtrato(String codigo, String dtInicial, String dtFinal) throws ClassNotFoundException,
			InterruptedException, IOException, SQLException, UnirestException, java.text.ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		SimpleDateFormat sdforiginal = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat sdfdesejado = new SimpleDateFormat("yyyy-MM-dd");

		Date dtOInicial = sdforiginal.parse(dtInicial);
		Date dtOFinal = sdforiginal.parse(dtFinal);

		String dfInicial = sdfdesejado.format(dtOInicial);
		String dfFinal = sdfdesejado.format(dtOFinal);
		System.out.println();

		System.out.println("dtInicial dentro do Service:" + dtInicial);

		ObterToken obter = new ObterToken();
		List<String> tokens = obter.obterToken(codigo);
		System.out.println("Url  : " + tokens.get(0));
		System.out.println("Token: " + tokens.get(2));

		HttpResponse<String> response = Unirest
				.get(tokens.get(0) + "/v2/company/balance/movements?initialDate=" + dfInicial + "&finalDate=" + dfFinal)
				.header("Authorization", "Bearer " + tokens.get(2)).asString();

		System.out.println(response.getBody());
		String json = response.getBody();

		Balance balance = JacksonUtil.fromJson(json, Balance.class);
		balance.setBalances(deserializeBalanceItems(json));

		for (BalanceItem balances : balance.balances) {
			System.out.println(
					balances.getFriendlyDescription() + " Transaction: " + balances.getTransactionGalaxPayId());

		}

		return balance;
	}

	private List<BalanceItem> deserializeBalanceItems(String json) {
		List<BalanceItem> balanceItems = new ArrayList<>();

		try {
			// Desserializa a parte relevante do JSON para uma lista de objetos BalanceItem
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode rootNode = objectMapper.readTree(json);
			JsonNode balancesNode = rootNode.path("Balances");

			if (balancesNode.isArray()) {
				for (JsonNode balanceNode : balancesNode) {
					BalanceItem balanceItem = objectMapper.treeToValue(balanceNode, BalanceItem.class);
					balanceItems.add(balanceItem);
				}
			}
		} catch (IOException e) {
			// Trate a exceção, se necessário
			e.printStackTrace();
		}

		return balanceItems;
	}

	public void atualizarWebHook(String urlWebHook, String codigo)
			throws ClassNotFoundException, InterruptedException, IOException, SQLException, UnirestException {
		ObterToken token = new ObterToken();
		List<String> strings = token.obterToken(codigo);

		HttpResponse<String> response = Unirest.put(strings.get(0) + "/v2/webhooks")
				.header("Content-Type", "application/json").header("Authorization", "Bearer " + strings.get(2))
				.body("{\n    \"url\": \"https:\\/\\/" + urlWebHook
						+ "\\/webhook-galax-pay\",\n    \"events\": [\n        \"company.verifyDocuments\"\n    ]\n}")
				.asString();

		System.out.println(response.getBody());
	}

	public String gerarPDFCobranca(String id_galaxPay, String codigo) throws ClassNotFoundException, InterruptedException, IOException, SQLException, UnirestException {
		ObterToken obter = new ObterToken();
		List<String> tokens = obter.obterToken(codigo);

		System.out.println("URL: " + tokens.get(0));

		HttpResponse<String> response = Unirest.post(tokens.get(0) + "/v2/boletos/charges")
				.header("Content-Type", "application/json").header("Authorization", "Bearer " + tokens.get(2))
				.header("Cookie",
						"__cf_bm=2mCUZuLjeLL4dMwaD6YB6e0shPZM.bnt1B6gKnVV_xg-1710875300-1.0.1.1-eadAn4dD510srz_H4FQ32ELt05VibPA2RSb0zKV3WAttWDllZJsDJc0LS15_ayzkll4gm7SPxYnnjPnIqCHPhQ")
				.body("{\n    \"galaxPayIds\": [\n        " + id_galaxPay
						+ "\n    ],\n    \"myIds\": [\n        \"2A\",\n        \"RFA-22\"\n    ],\n    \"order\": \"transactionPayday.asc\"\n}")
				.asString();

		System.out.println(response.getBody());

		JSONObject obj = new JSONObject(response.getBody());
		JSONObject boleto = obj.getJSONObject("Boleto");
		
		String urglPdf =  boleto.getString("pdf").toString();
		return urglPdf;
	}

	

	public static void main(String[] args) throws FileNotFoundException, IOException, ParseException,
			ClassNotFoundException, InterruptedException, SQLException, UnirestException, java.text.ParseException {

		GalaxPayServices services = new GalaxPayServices();
		ObterToken obter = new ObterToken();
		GalaxPayBank galax = new GalaxPayBank();
		List<Boleto> boletos = new ArrayList<>();
		List<GalaxPayClifor> clientes = new ArrayList<>();
		List<Balance> balances = new ArrayList<>();
		String dtInicial = "01/02/2024";
		String dtFinal = "14/02/2024";
		Duprec dup = new Duprec();
		dup.setId_galaxpay("281");
		List<Duprec> lDup = new ArrayList<>();
		lDup.add(dup);
		// services.listarExtrato("2000", dtInicial, dtFinal );

		// services.listarClientes("1000");
		services.gerarPDFCobranca("281", "2000");
	}

}
