package br.com.controller.galaxpay.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class testeExtratoCelcoin {

	public static void main(String[] args) {
		try {
			// URL da requisição
			String urlString = "https://sandbox.openfinance.celcoin.dev/baas-walletreports/v1/wallet/movement?Account=300539137798&DateFrom=2022-10-26&DateTo=2022-10-28&DocumentNumber&Limit&Page";
			URL url = new URL(urlString);

			// Abre uma conexão HTTP
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			// Configura o método e os cabeçalhos da requisição
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Accept", "application/json");
			connection.setRequestProperty("Authorization", "Bearer {{token}}"); // Substitua {{token}} pelo seu token
																				// real

			// Lê a resposta da requisição
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = reader.readLine()) != null) {
				response.append(inputLine);
			}
			reader.close();

			// Imprime a resposta
			System.out.println(response.toString());

			// Fecha a conexão
			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
