package br.com.controller.galaxpay.services;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import br.com.controller.dao.BancoDAO;
import br.com.controller.domain.Parametros;
import br.com.controller.galaxpay.domain.GalaxPayBank;
import br.com.controller.util.LerPropriedades;


public class ObterToken {
		
	public List<String> obterToken(String codigo)
			throws InterruptedException, IOException, ClassNotFoundException, SQLException, UnirestException {
		BancoDAO dao = new BancoDAO();

		System.out.println("Dentro do ObterToken: "+codigo);
		Parametros param = new Parametros();
		param = LerPropriedades.lerPropriedades();
		
		String parceiro = "";
		String url = "";
		String id = "";
		String hash = "";
		String converter = "";
		String token = "";
		
		GalaxPayBank conta = new GalaxPayBank();
		
		List<String> strings = new ArrayList<>();
		
		System.out.println("Modo:" +param.getModo());

		if (param.getModo() == 0) {
			System.out.println("entrou aqui");
			parceiro = "5473:83Mw5u8988Qj6fZqS4Z8K7LzOo1j28S706R0BeFe";
			url = "https://api.sandbox.cel.cash";
			strings.add(url); // lista de String value 0
			strings.add(parceiro); // lista de String value 1
			id = "5473";//banco.getIdGalaxyPay();
			hash = "83Mw5u8988Qj6fZqS4Z8K7LzOo1j28S706R0BeFe";//banco.getHashGalaxPay();
			converter = id + ":" + hash;

		} else if (param.getModo() == 1){
			System.out.println("Ele veio pra cá");
			parceiro = "22852:6pDs424uYh81G4IjKu2xOsOm8pX4X8Mn4iV83rY1";
			url = "https://api-celcash.celcoin.com.br";
			strings.add(url); // lista de String value 0
			strings.add(parceiro); // lista de String value 1
			conta = dao.selecionaBancoGalax("0050", codigo);
			System.out.println(conta.getIdGalaxyPay() + " - "+conta.getHashGalaxPay());
			id = conta.getIdGalaxyPay();
			hash = conta.getHashGalaxPay();
			converter = id + ":" + hash;
			//System.out.println("Converter: "+converter);
		} else {
			System.out.println("Deve ser informado 0 para SandBox ou 1 para producao");
		}
		
		String tokenAcess = Base64.getEncoder().encodeToString(converter.getBytes());
		String tokenParceiro = Base64.getEncoder().encodeToString(parceiro.getBytes());

		System.out.println("URL: "+url);
		HttpResponse<String> response = Unirest.post(strings.get(0) +"/v2/token").header("Authorization", "Basic " + tokenAcess)
				.header("AuthorizationPartner", tokenParceiro).header("Content-Type", "application/json")
				.body("{\n    \"grant_type\": \"authorization_code\",\n    \"scope\": \"customers.read customers.write plans.read plans.write transactions.read transactions.write webhooks.write cards.read cards.write card-brands.read charges.read charges.write boletos.read balance.read\"\n}")
				.asString();

		System.out.println("headers: "+response.getHeaders());
		System.out.println(response.getBody());
		
		JSONObject json = new JSONObject(response.getBody());

		System.out.println(json);
		token = (String) json.get("access_token");
		System.out.println("Token String: "+token);
		
		strings.add(token);// Lista de Strings value 2

		return strings;
	}
	
	public static void main(String[] args) throws ClassNotFoundException, InterruptedException, IOException, SQLException, UnirestException {
		ObterToken obter = new ObterToken();
		List<String> lista = obter.obterToken("9000");
		
		System.out.println(lista);
		
	}
}
