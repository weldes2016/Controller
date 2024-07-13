package br.com.controller.util;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;

public class TesteWhatsApp {

    public static void main(String[] args) {
        
    	try {
            JSONObject messageContent = new JSONObject();
            messageContent.put("text", "Olá, mundo! Esta é uma mensagem de teste.");
            
            JSONObject requestBody = new JSONObject();
            requestBody.put("messaging_product", "whatsapp");
            requestBody.put("recipient_type", "individual");
            requestBody.put("to", "5562982592220");
            requestBody.put("type", "TEXT");
            requestBody.put("text", "res");
           

            HttpResponse<String> response = Unirest.post("https://graph.facebook.com/v17.0/107097462492842/messages")
                    .header("Authorization", "Bearer EAAuZAGIGkZCFgBOzuEM44QhyiJkZAZABZBbhtqZBZA4KQeZAsjq2I5etm2FDG4BnTwiZAwFrgaeB4gEOe02oFonBtGWnXpPuYIpKr1QPyRk89RVhP2pBgao7LPuNzyIAfENMrfIMrDKIiVIpyRWZBZA9m9dEEncW2nA6aY6HT1xVVJ99DkyYYI7mxGmxfPXzRD4jSXG5RJM6ZClHEqFdD1IZD")
                    .header("Content-Type", "application/json")
                    .body(requestBody)
                    .asString();

            System.out.println("Código de resposta: " + response.getStatus());
            System.out.println("Resposta: " + response.getBody());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
