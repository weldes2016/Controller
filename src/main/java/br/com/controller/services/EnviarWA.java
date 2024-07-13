package br.com.controller.services;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import br.com.controller.dao.DuprecDAO;
import br.com.controller.dao.EmpresasDAO;
import br.com.controller.domain.Duprec;
import br.com.controller.domain.Empresas;
import br.com.controller.domain.Telefones;
import br.com.controller.galaxpay.domain.Boleto;
import br.com.controller.galaxpay.services.GalaxPayServices;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;

public class EnviarWA {

	GalaxPayServices services = new GalaxPayServices();
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	DuprecDAO dao = new DuprecDAO();

	public void enviarMensagemWa(String whatsapp) {

		HttpResponse<String> response = Unirest.post("https://graph.facebook.com/v18.0/107097462492842/messages")
				.header("Content-Type", "application/json")
				.header("Authorization",
						"Bearer EAAuZAGIGkZCFgBO3SdVZCq0dohpJm5UMb4r5ldvrD2HOSZAGTInId4JFAZAVythKZBCnIbcI0yexo4nzCuYE4mopMdUmMClUnA3ShIkZAeZB8ETwZB9KxZBZB0chicFACZCk3Ad0ovHEUmEiONp2we01ckUZCZBcA9W9jRjrL40fFgNqnTF6Mzyw0GDwHFnwWW9bnZCbnpBmLSd0ZCorZAoUZBViUZD")
				.header("Cookie", "ps_l=0; ps_n=0")
				.body("{\n    \"messaging_product\": \"whatsapp\", \n    \"to\": \"+55" + whatsapp
						+ "\", \n    \"type\": \"template\", \n    \"template\": \n    { \n        \"name\": \"hello_world\", \n        \"language\": \n        { \"code\": \"en_US\" }\n                 } \n                 }")
				.asString();

		System.out.println(response.getBody());

	}

	public void enviarMensagemBoleto(Duprec duprec, Empresas empresa, Boleto bol)
			throws ClassNotFoundException, InterruptedException, IOException, SQLException, UnirestException {
		
		if(duprec.getNumdup().contains("-")) {
			String[] partes = duprec.getNumdup().split("-");
			duprec.setNumdup(partes[0]);
			duprec.setOrdem(partes[1]);
		}

		DuprecDAO dao = new DuprecDAO();

		DecimalFormat formato = new DecimalFormat("#,##0.00");
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

		String valorConvertido = formato.format(duprec.getValor());

		Telefones telefones = new Telefones();

		telefones = dao.listarTelefonesDuprec(duprec, empresa);

		String tel = "";
		String empresaNom = empresa.getNom();
		String contato = "";
		String valor = valorConvertido;
		String duprecEmissao = sdf.format(duprec.getEmissao());
		String duprecVencimento = sdf.format(duprec.getVencimento());
		String duprecUrlPDF = bol.getPdf();

		System.out.println("Pdf antes de enviar esta na URL: " + duprecUrlPDF);

		System.out.println("Telefone 01: " +telefones.getTelefone1() + " - Telefone 02: "+telefones.getTelefone2());
		
		if (telefones.getTelefone1() != null) {
			tel = telefones.getTelefone1();
			contato = telefones.getContato1();
			JsonObject json = new JsonObject();
			json.addProperty("messaging_product", "whatsapp");
			json.addProperty("to", "+55" + tel);
			json.addProperty("type", "template");

			JsonObject template = new JsonObject();
			template.addProperty("name", "envio_de_boletos");

			JsonObject language = new JsonObject();
			language.addProperty("code", "pt_BR");
			template.add("language", language);

			JsonArray components = new JsonArray();
			JsonObject body = new JsonObject();
			body.addProperty("type", "BODY");

			JsonArray parameters = new JsonArray();

			JsonObject text1 = new JsonObject();
			text1.addProperty("type", "TEXT");
			text1.addProperty("text", contato);
			parameters.add(text1);

			JsonObject text2 = new JsonObject();
			text2.addProperty("type", "TEXT");
			text2.addProperty("text", empresaNom);
			parameters.add(text2);

			JsonObject text3 = new JsonObject();
			text3.addProperty("type", "TEXT");
			text3.addProperty("text", duprecEmissao);
			parameters.add(text3);

			JsonObject text4 = new JsonObject();
			text4.addProperty("type", "TEXT");
			text4.addProperty("text", valor);
			parameters.add(text4);

			JsonObject text5 = new JsonObject();
			text5.addProperty("type", "TEXT");
			text5.addProperty("text", duprecVencimento);
			parameters.add(text5);

			JsonObject text6 = new JsonObject();
			text6.addProperty("type", "TEXT");
			text6.addProperty("text", duprecUrlPDF);
			parameters.add(text6);

			body.add("parameters", parameters);
			components.add(body);

			template.add("components", components);
			json.add("template", template);

			// Convertendo o objeto JSON para String
			String jsonString = json.toString();

			System.out.println(jsonString);

			HttpResponse<String> response = Unirest.post("https://graph.facebook.com/v18.0/107097462492842/messages")
					.header("Content-Type", "application/json")
					.header("Authorization",
							"Bearer EAAuZAGIGkZCFgBOZBMFUfFrZAVDlcxyzKomLafdPseb3Lr7V4tT0U7ygMXfjmfcWHZBV0trU9t71VE7CV8mmBTjS3IJSH5qSXoa93ZAMhqGSIt8lzITFtwJBZCFxEpGhyHNTERsKkbksiMkNbehnXZAX6rZCCRp6GaDZCy1CZAnrge9RBlDimStrTCWXhMzw4Gg")
					.header("Cookie", "ps_l=0; ps_n=0").body(jsonString).asString();

			System.out.println(response.getBody());
			ObjectMapper mapper = new ObjectMapper();
			JsonNode rootNode = mapper.readTree(response.getBody());

			JsonNode messagesNode = rootNode.path("messages");
			if (messagesNode.isArray() && messagesNode.size() > 0) {
				Date dataAtual = new Date();

				SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				String dataFormatada = sdf2.format(dataAtual);

				duprec.setTokenjuno(dataFormatada);
				
				//System.out.println("Data a ser inserida no banco de dados: "+duprec.getTokenjuno());
				
				String obs = "\n Enviado via WhatsApp para: "+ contato + "-"+ dataFormatada;
				dao.updateWhatsAppDuprec(duprec, dataFormatada, empresa, obs, tel);
			} else {
				System.out.println("Nenhum objeto 'messages' encontrado ou está vazio.");
			}
		}

		if (telefones.getTelefone2() != null) {
			tel = telefones.getTelefone2();
			contato = telefones.getContato2();
			JsonObject json = new JsonObject();
			json.addProperty("messaging_product", "whatsapp");
			json.addProperty("to", "+55" + tel);
			json.addProperty("type", "template");

			JsonObject template = new JsonObject();
			template.addProperty("name", "envio_de_boletos");

			JsonObject language = new JsonObject();
			language.addProperty("code", "pt_BR");
			template.add("language", language);

			JsonArray components = new JsonArray();
			JsonObject body = new JsonObject();
			body.addProperty("type", "BODY");

			JsonArray parameters = new JsonArray();

			JsonObject text1 = new JsonObject();
			text1.addProperty("type", "TEXT");
			text1.addProperty("text", contato);
			parameters.add(text1);

			JsonObject text2 = new JsonObject();
			text2.addProperty("type", "TEXT");
			text2.addProperty("text", empresaNom);
			parameters.add(text2);

			JsonObject text3 = new JsonObject();
			text3.addProperty("type", "TEXT");
			text3.addProperty("text", duprecEmissao);
			parameters.add(text3);

			JsonObject text4 = new JsonObject();
			text4.addProperty("type", "TEXT");
			text4.addProperty("text", valor);
			parameters.add(text4);

			JsonObject text5 = new JsonObject();
			text5.addProperty("type", "TEXT");
			text5.addProperty("text", duprecVencimento);
			parameters.add(text5);

			JsonObject text6 = new JsonObject();
			text6.addProperty("type", "TEXT");
			text6.addProperty("text", duprecUrlPDF);
			parameters.add(text6);

			body.add("parameters", parameters);
			components.add(body);

			template.add("components", components);
			json.add("template", template);

			// Convertendo o objeto JSON para String
			String jsonString = json.toString();

			System.out.println(jsonString);

		
			HttpResponse<String> response = Unirest.post("https://graph.facebook.com/v18.0/107097462492842/messages")
					.header("Content-Type", "application/json")
					.header("Authorization",
							"Bearer EAAuZAGIGkZCFgBOZBMFUfFrZAVDlcxyzKomLafdPseb3Lr7V4tT0U7ygMXfjmfcWHZBV0trU9t71VE7CV8mmBTjS3IJSH5qSXoa93ZAMhqGSIt8lzITFtwJBZCFxEpGhyHNTERsKkbksiMkNbehnXZAX6rZCCRp6GaDZCy1CZAnrge9RBlDimStrTCWXhMzw4Gg")
					.header("Cookie", "ps_l=0; ps_n=0").body(jsonString).asString();

			System.out.println(response.getBody());
			ObjectMapper mapper = new ObjectMapper();
			JsonNode rootNode = mapper.readTree(response.getBody());

			JsonNode messagesNode = rootNode.path("messages");
						
			if (messagesNode.isArray() && messagesNode.size() > 0) {
				Date dataAtual = new Date();

				SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				String dataFormatada = sdf2.format(dataAtual);
				
				String obs = "Enviado via WhatsApp para: "+ contato + "-"+ dataFormatada;

				duprec.setTokenjuno(dataFormatada);
				dao.updateWhatsAppDuprec(duprec, dataFormatada, empresa, obs, tel);
			} else {
				System.out.println("Nenhum objeto 'messages' encontrado ou está vazio.");
			}
		}

		
	}

	/*
	 * 
	 * String template = "{\n" + "    \"messaging_product\": \"whatsapp\", \n" +
	 * "    \"to\": \"+5562982592220" + "\", \n" + "    \"type\": \"template\", \n"
	 * + "    \"template\": \n" + "    { \n" +
	 * "        \"name\": \"envio_de_boletos\", \n" + "        \"language\": \n" +
	 * "        { \"code\": \"pt_BR\" },\n" + "        \"components\": \n" +
	 * "        [\n" + "            {\n" + "                \"type\": \"BODY\",\n" +
	 * "                \"parameters\": \n" + "                [\n" +
	 * "{ \"type\": \"TEXT\", \"text\": \"" + "*" + empresa.getNom() + "*" +
	 * "\" },\n" + "                    { \"type\": \"TEXT\", \"text\": \"" +
	 * sdf.format(duprec.getEmissao()) + "\" },\n" +
	 * "{ \"type\": \"TEXT\", \"text\": },\n" + "{ \"type\": \"TEXT\", \"text\": \""
	 * + valor + "\" },\n" +
	 * "                    { \"type\": \"TEXT\", \"text\": \"" +
	 * sdf.format(duprec.getVencimento()) + "\" },\n" +
	 * "                    { \"type\": \"TEXT\", \"text\": \"" + duprec.getUrlPDF()
	 * + "\" }\n" + "                ]\n" + "            }\n" + "        ]\n" +
	 * "    }\n" + "}";
	 * 
	 * HttpResponse<String> response =
	 * Unirest.post("https://graph.facebook.com/v18.0/107097462492842/messages")
	 * .header("Content-Type", "application/json") .header("Authorization",
	 * "Bearer EAAuZAGIGkZCFgBOZBMFUfFrZAVDlcxyzKomLafdPseb3Lr7V4tT0U7ygMXfjmfcWHZBV0trU9t71VE7CV8mmBTjS3IJSH5qSXoa93ZAMhqGSIt8lzITFtwJBZCFxEpGhyHNTERsKkbksiMkNbehnXZAX6rZCCRp6GaDZCy1CZAnrge9RBlDimStrTCWXhMzw4Gg"
	 * ) .header("Cookie", "ps_l=0; ps_n=0").body(template).asString();
	 * 
	 * System.out.println(response.getBody()); ObjectMapper mapper = new
	 * ObjectMapper(); JsonNode rootNode = mapper.readTree(response.getBody());
	 * 
	 * JsonNode messagesNode = rootNode.path("messages"); if (messagesNode.isArray()
	 * && messagesNode.size() > 0) { Date dataAtual = new Date();
	 * 
	 * SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); String
	 * dataFormatada = sdf2.format(dataAtual);
	 * 
	 * duprec.setTokenjuno(dataFormatada); // dao.updateWhatsAppDuprec(duprec,
	 * dataFormatada, empresa); } else {
	 * System.out.println("Nenhum objeto 'messages' encontrado ou está vazio."); } }
	 */

	public void enviarMensagemBoletoAgendado(Duprec duprec, Empresas empresa)
			throws ClassNotFoundException, InterruptedException, IOException, SQLException, UnirestException {

		DuprecDAO dao = new DuprecDAO();

		DecimalFormat formato = new DecimalFormat("#,##0.00");
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

		String valor = formato.format(duprec.getValor());

		if (duprec.getTelefone() != null) {
			String template = "{\r\n" + "    \"messaging_product\": \"whatsapp\",\n" + "    \"to\": \"+55"
					+ duprec.getTelefone() + "\",\n" + "    \"type\": \"template\",\n" + "    \"template\": {\n"
					+ "        \"name\": \"reaviso_de_vencimento\",\n" + "        \"language\": {\n"
					+ "            \"code\": \"pt_BR\"\n" + "        },\n" + "        \"components\": [\n"
					+ "            {\n" + "                \"type\": \"BODY\",\n"
					+ "                \"parameters\": [\n" + "		    { \"type\": \"TEXT\", \"text\": \"*"
					+ duprec.getContato1() + "*\" },\n" + "                    { \"type\": \"TEXT\", \"text\": \"*"
					+ empresa.getNom() + "*\" },\n" + "                    { \"type\": \"TEXT\", \"text\": \"*"
					+ duprec.getCliente() + "*\" },\n" + "                    { \"type\": \"TEXT\", \"text\": \"*"
					+ duprec.getNumdup() + "*\" },\n" + "                    { \"type\": \"TEXT\", \"text\": \"*"
					+ sdf.format(duprec.getVencimento()) + "*\"},\n"
					+ "                    { \"type\": \"TEXT\", \"text\": \"*" + valor + "*\" },\n"
					+ "                    { \"type\": \"TEXT\", \"text\": \"*" + duprec.getUrlPDF() + "*\"}\n"
					+ "                ]\n" + "            }\n" + "        ]\n" + "    }\n" + "}";

			HttpResponse<String> response = Unirest.post("https://graph.facebook.com/v18.0/107097462492842/messages")
					.header("Content-Type", "application/json")
					.header("Authorization",
							"Bearer EAAuZAGIGkZCFgBOZBMFUfFrZAVDlcxyzKomLafdPseb3Lr7V4tT0U7ygMXfjmfcWHZBV0trU9t71VE7CV8mmBTjS3IJSH5qSXoa93ZAMhqGSIt8lzITFtwJBZCFxEpGhyHNTERsKkbksiMkNbehnXZAX6rZCCRp6GaDZCy1CZAnrge9RBlDimStrTCWXhMzw4Gg")
					.header("Cookie", "ps_l=0; ps_n=0").body(template).asString();

			// System.out.println(response.getBody());
			ObjectMapper mapper = new ObjectMapper();
			JsonNode rootNode = mapper.readTree(response.getBody());

			JsonNode messagesNode = rootNode.path("messages");
			if (messagesNode.isArray() && messagesNode.size() > 0) {
				Date dataAtual = new Date();

				SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				String dataFormatada = sdf2.format(dataAtual);

			}

			if (duprec.getTelefone() != null) {
				String template2 = "{\r\n" + "    \"messaging_product\": \"whatsapp\",\n" + "    \"to\": \"+55"
						+ duprec.getTelefone() + "\",\n" + "    \"type\": \"template\",\n" + "    \"template\": {\n"
						+ "        \"name\": \"reaviso_de_vencimento\",\n" + "        \"language\": {\n"
						+ "            \"code\": \"pt_BR\"\n" + "        },\n" + "        \"components\": [\n"
						+ "            {\n" + "                \"type\": \"BODY\",\n"
						+ "                \"parameters\": [\n" + "		    { \"type\": \"TEXT\", \"text\": \"*"
						+ duprec.getContato1() + "*\" },\n" + "                    { \"type\": \"TEXT\", \"text\": \"*"
						+ empresa.getNom() + "*\" },\n" + "                    { \"type\": \"TEXT\", \"text\": \"*"
						+ duprec.getCliente() + "*\" },\n" + "                    { \"type\": \"TEXT\", \"text\": \"*"
						+ duprec.getNumdup() + "*\" },\n" + "                    { \"type\": \"TEXT\", \"text\": \"*"
						+ sdf.format(duprec.getVencimento()) + "*\"},\n"
						+ "                    { \"type\": \"TEXT\", \"text\": \"*" + valor + "*\" },\n"
						+ "                    { \"type\": \"TEXT\", \"text\": \"*" + duprec.getUrlPDF() + "*\"}\n"
						+ "                ]\n" + "            }\n" + "        ]\n" + "    }\n" + "}";

				HttpResponse<String> response2 = Unirest
						.post("https://graph.facebook.com/v18.0/107097462492842/messages")
						.header("Content-Type", "application/json")
						.header("Authorization",
								"Bearer EAAuZAGIGkZCFgBOZBMFUfFrZAVDlcxyzKomLafdPseb3Lr7V4tT0U7ygMXfjmfcWHZBV0trU9t71VE7CV8mmBTjS3IJSH5qSXoa93ZAMhqGSIt8lzITFtwJBZCFxEpGhyHNTERsKkbksiMkNbehnXZAX6rZCCRp6GaDZCy1CZAnrge9RBlDimStrTCWXhMzw4Gg")
						.header("Cookie", "ps_l=0; ps_n=0").body(template2).asString();

				// System.out.println(response.getBody());
				ObjectMapper mapper2 = new ObjectMapper();
				JsonNode rootNode2 = mapper.readTree(response2.getBody());

				JsonNode messagesNode2 = rootNode2.path("messages");
				if (messagesNode2.isArray() && messagesNode2.size() > 0) {
					Date dataAtual = new Date();

					SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
					String dataFormatada = sdf2.format(dataAtual);

				}
			}
		}

	}

	public static void main(String[] args) throws ClassNotFoundException, SQLException, UnirestException, InterruptedException, IOException, com.mashape.unirest.http.exceptions.UnirestException {
		EnviarWA enviar = new EnviarWA();
		DuprecDAO dao = new DuprecDAO();
		EmpresasDAO daoEmpresas = new EmpresasDAO();
		Duprec duprec = new Duprec();
		duprec = dao.buscaDuplicata("1000", "194644327");
		GalaxPayServices service = new GalaxPayServices();
		Boleto bol = new Boleto();
		bol.setPdf(service.gerarPDFCobranca("822", "1000"));
		Empresas empresa = new Empresas();
		empresa = daoEmpresas.buscarEmpresaPorId("1000");
		
		enviar.enviarMensagemBoleto(duprec, empresa, bol);
	}
}
