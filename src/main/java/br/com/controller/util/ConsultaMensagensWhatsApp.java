package br.com.controller.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConsultaMensagensWhatsApp {
	public static void main(String[] args) {
		try {
			URL url = new URL(
					"https://graph.facebook.com/v19.0/3264555277155416/template_analytics?start=1689379200&end=1689552000&granularity=DAILY&metric_types=[%27SENT%27%2C%27DELIVERED%27%2C%27READ%27%2C%27CLICKED%27]&template_ids=[1924084211297547%2C954638012257287]");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Authorization",
					"Bearer EAAuZAGIGkZCFgBO3SdVZCq0dohpJm5UMb4r5ldvrD2HOSZAGTInId4JFAZAVythKZBCnIbcI0yexo4nzCuYE4mopMdUmMClUnA3ShIkZAeZB8ETwZB9KxZBZB0chicFACZCk3Ad0ovHEUmEiONp2we01ckUZCZBcA9W9jRjrL40fFgNqnTF6Mzyw0GDwHFnwWW9bnZCbnpBmLSd0ZCorZAoUZBViUZD");

			int responseCode = connection.getResponseCode();
			System.out.println("Response Code: " + responseCode);

			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			System.out.println("Response: " + response.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
