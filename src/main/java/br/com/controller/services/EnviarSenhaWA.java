package br.com.controller.services;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class EnviarSenhaWA {
	public native void meuMetodo(int parametro);

	public static void main(String[] args) {
		// Criar um objeto JSON para representar o template de mensagem
		JsonObject templateJson = new JsonObject();
		templateJson.addProperty("name", "template_nome");

		// Configurar o idioma
		JsonObject language = new JsonObject();
		language.addProperty("code", "pt_BR");
		language.addProperty("policy", "deterministic");
		templateJson.add("language", language);

		// Configurar os componentes da mensagem
		JsonArray components = new JsonArray();
		JsonObject bodyComponent = new JsonObject();
		bodyComponent.addProperty("type", "BODY");

		// Configurar os parâmetros do componente BODY
		JsonArray parameters = new JsonArray();
		JsonObject textParameter = new JsonObject();
		textParameter.addProperty("type", "TEXT");
		textParameter.addProperty("text", "Olá {{nome_do_usuario}}, sua senha temporária é: {{senha_temporaria}}.");

		parameters.add(textParameter);
		bodyComponent.add("parameters", parameters);

		components.add(bodyComponent);
		templateJson.add("components", components);

		// Converter o objeto JSON para uma string JSON formatada
		Gson gson = new Gson();
		String jsonString = gson.toJson(templateJson);

		// Imprimir a string JSON
		System.out.println(jsonString);
	}
}
