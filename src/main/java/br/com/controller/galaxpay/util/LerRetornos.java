package br.com.controller.galaxpay.util;

import java.io.IOException;
import java.sql.SQLException;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import br.com.controller.dao.CliforDAO;
import br.com.controller.domain.Clifor;
import br.com.controller.domain.Duprec;
import br.com.controller.galaxpay.domain.Boleto;


public class LerRetornos {
	
	CliforDAO daoClifor = new CliforDAO();
	

	public CliforDAO getDaoClifor() {
		return daoClifor;
	}

	public void setDaoClifor(CliforDAO daoClifor) {
		this.daoClifor = daoClifor;
	}

	public Boleto lerRetornos(String response) {
		
		Boleto boleto = new Boleto();
		
		JsonObject obj = JsonParser.parseString(response).getAsJsonObject();

		JsonObject chargeObject = obj.getAsJsonObject("Charge");
		String myId = chargeObject.get("myId").getAsString();
		System.out.println("myId: " + myId);
		
		JsonObject customerObject = chargeObject.getAsJsonObject("Customer");
		
		 String customerId = customerObject.get("myId").getAsString();
		 int galaxPayId = customerObject.get("galaxPayId").getAsInt();
		 String name = customerObject.get("name").getAsString();
		 String documento = customerObject.get("document").getAsString();
		 //System.out.println("MyId: "+customerId+  "GalaxPayId: "+galaxPayId + "Nome: "+name);
		// daoClifor.updateClientesGalaxPay(documento, customerId, codigo);
		    
		JsonArray transactions = obj.getAsJsonObject("Charge").getAsJsonArray("Transactions");

		for (JsonElement transaction : transactions) {
			JsonObject transactionObject = transaction.getAsJsonObject();  
		
			String galaxPayIdTransaction = chargeObject.get("galaxPayId").getAsString();

			JsonObject boletoObject = transactionObject.getAsJsonObject("Boleto");

			if (boletoObject != null) {
				String numberBank = boletoObject.getAsJsonPrimitive("bankNumber").getAsString();
				String pdf = boletoObject.getAsJsonPrimitive("pdf").toString();
				System.out.println("NumberBank: " + numberBank);
				boleto.setMyId(myId);
				boleto.setBankNumber(numberBank);
				boleto.setGalaxPayId(galaxPayIdTransaction);
				boleto.setCustomerGalaxPayId(customerId);
				boleto.setPdf(pdf);
				//System.out.println(boleto.getPdf());
			} else {
				System.out.println("GalaxPayId: " + galaxPayId);
				// Restante do código...
			}
			
		}
		return boleto;
	}

	public void lerRetornosClientes(String response, String codigo) throws ClassNotFoundException, SQLException, IOException {
		System.out.println("Codigo da empresa: "+codigo);
		EnviarEmailConfirmacao enviar = new EnviarEmailConfirmacao();	
		Duprec duprec = new Duprec();
		
		System.out.println(response.toString());
		JsonObject obj = JsonParser.parseString(response).getAsJsonObject();
		
		JsonObject chargeObject = obj.getAsJsonObject("Customer");
		String document = chargeObject.get("document").getAsString();
		Clifor clifor = new Clifor();
		clifor.setCod(chargeObject.get("myId").getAsString());
		duprec.setCodcli(clifor);
		
		String idGalaxPay = chargeObject.get("galaxPayId").getAsString();	
		
		System.out.println("MyId: " + duprec.getCod()+ " id GalaxPay: "+idGalaxPay);
		
		daoClifor.updateClientesGalaxPay(duprec, idGalaxPay, codigo);
		

	}

	public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
		LerRetornos ler = new LerRetornos();
		String clientes = "";

		ler.lerRetornos("{\r\n"
				+ "    \"type\": true,\r\n"
				+ "    \"Charge\": {\r\n"
				+ "        \"myId\": \"2324-43\",\r\n"
				+ "        \"galaxPayId\": 72957,\r\n"
				+ "        \"value\": 38550,\r\n"
				+ "        \"paymentLink\": \"https:\\/\\/panel.sandbox.cel.cash\\/c\\/5473\\/be371a07\\/b\",\r\n"
				+ "        \"mainPaymentMethodId\": \"boleto\",\r\n"
				+ "        \"status\": \"active\",\r\n"
				+ "        \"additionalInfo\": \"Fatura: 2324. Emitida em: 06\\/03\\/2024, Ref a servicos prestados no mes de: 03\\/2024\",\r\n"
				+ "        \"createdAt\": \"2024-03-18 11:33:56\",\r\n"
				+ "        \"updatedAt\": \"2024-03-18 11:33:56\",\r\n"
				+ "        \"payedOutsideGalaxPay\": false,\r\n"
				+ "        \"Customer\": {\r\n"
				+ "            \"myId\": \"null\",\r\n"
				+ "            \"galaxPayId\": 31773,\r\n"
				+ "            \"name\": \"ACOS MAANAIM INDUSTRIA E COMERCIO EIRELI - ME\",\r\n"
				+ "            \"document\": \"24523443000125\",\r\n"
				+ "            \"createdAt\": \"2024-03-07 11:28:07\",\r\n"
				+ "            \"updatedAt\": \"2024-03-18 11:33:56\",\r\n"
				+ "            \"emails\": [\r\n"
				+ "                \"rensga@rensga.com.br\"\r\n"
				+ "            ],\r\n"
				+ "            \"phones\": [\r\n"
				+ "                62988845449\r\n"
				+ "            ],\r\n"
				+ "            \"Address\": {\r\n"
				+ "                \"zipCode\": \"74357270\",\r\n"
				+ "                \"street\": \"RUA VICINAL EGITO\",\r\n"
				+ "                \"number\": \"0\",\r\n"
				+ "                \"complement\": \"QUADRA15 LOTE 5\\/5A\",\r\n"
				+ "                \"neighborhood\": \"CONDOM\\u00cdNIO PRIV\\u00ca DAS OLIVEIRAS\",\r\n"
				+ "                \"city\": \"Goi\\u00e2nia\",\r\n"
				+ "                \"state\": \"GO\"\r\n"
				+ "            },\r\n"
				+ "            \"ExtraFields\": []\r\n"
				+ "        },\r\n"
				+ "        \"Transactions\": [\r\n"
				+ "            {\r\n"
				+ "                \"galaxPayId\": 110329,\r\n"
				+ "                \"value\": 38550,\r\n"
				+ "                \"payday\": \"2024-05-20\",\r\n"
				+ "                \"paydayDate\": null,\r\n"
				+ "                \"installment\": 1,\r\n"
				+ "                \"status\": \"pendingBoleto\",\r\n"
				+ "                \"statusDescription\": \"Em aberto\",\r\n"
				+ "                \"statusDate\": \"2024-03-18 11:33:57\",\r\n"
				+ "                \"createdAt\": \"2024-03-18 11:33:56\",\r\n"
				+ "                \"chargeGalaxPayId\": 72957,\r\n"
				+ "                \"chargeMyId\": \"2324-43\",\r\n"
				+ "                \"ConciliationOccurrences\": [],\r\n"
				+ "                \"Boleto\": {\r\n"
				+ "                    \"pdf\": \"https:\\/\\/data.sandbox.cel.cash\\/saromfuneraria\\/boleto\\/202403HWDF958HVNVEPHH4WM8AA85RH18113356\",\r\n"
				+ "                    \"bankLine\": \"03399022070600004000356002501015997220000038550\",\r\n"
				+ "                    \"bankNumber\": 4000560025,\r\n"
				+ "                    \"barCode\": \"03399972200000385509022006000040005600250101\",\r\n"
				+ "                    \"bankEmissor\": \"santander\",\r\n"
				+ "                    \"bankAgency\": \"1004\",\r\n"
				+ "                    \"bankAccount\": \"0220060\"\r\n"
				+ "                },\r\n"
				+ "                \"Antifraud\": {\r\n"
				+ "                    \"ip\": null,\r\n"
				+ "                    \"sessionId\": null,\r\n"
				+ "                    \"sent\": false,\r\n"
				+ "                    \"approved\": null\r\n"
				+ "                },\r\n"
				+ "                \"Pix\": {\r\n"
				+ "                    \"reference\": \"80F3104E822D41328C1FA0C64F4105FA\",\r\n"
				+ "                    \"qrCode\": \"MBC6W4D7NT4GQAS7FUBP9RDQD54KNWK2XZF5E849E41VVCEK2QMDECMR9YYG\",\r\n"
				+ "                    \"image\": \"https:\\/\\/data.sandbox.cel.cash\\/saromfuneraria\\/pix\\/80F3104E822D41328C1FA0C64F4105FA\",\r\n"
				+ "                    \"page\": \"https:\\/\\/panel.sandbox.cel.cash\\/q\\/5473\\/83dcefb7\"\r\n"
				+ "                }\r\n"
				+ "            }\r\n"
				+ "        ],\r\n"
				+ "        \"ExtraFields\": [],\r\n"
				+ "        \"PaymentMethodBoleto\": {\r\n"
				+ "            \"fine\": 450,\r\n"
				+ "            \"interest\": 200,\r\n"
				+ "            \"instructions\": \"Fatura: 2324. Emitida em: 06\\/03\\/2024, Ref a servicos prestados no mes de: 03\\/2024\",\r\n"
				+ "            \"deadlineDays\": 1,\r\n"
				+ "            \"documentNumber\": null\r\n"
				+ "        }\r\n"
				+ "    }\r\n"
				+ "}");
	}
}
