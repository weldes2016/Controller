package br.com.controller.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class EmailTracker {
	public static void main(String[] args) {
		// URL do pixel de rastreamento
		String pixelUrl = "https://miklus.com.br/images/Logo.png";

		// Simular abertura de e-mail
		boolean emailOpened = true;

		// Se o e-mail foi aberto, faça uma solicitação HTTP para o pixel de
		// rastreamento
		if (emailOpened) {
			try {
				URL url = new URL(pixelUrl);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod("GET");
				int responseCode = connection.getResponseCode();

				// Verifique se a solicitação foi bem-sucedida (código 200)
				if (responseCode == HttpURLConnection.HTTP_OK) {
					System.out.println("Pixel de rastreamento baixado com sucesso.");
				} else {
					System.out.println("Erro ao baixar o pixel de rastreamento. Código de resposta: " + responseCode);
				}
				connection.disconnect();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
