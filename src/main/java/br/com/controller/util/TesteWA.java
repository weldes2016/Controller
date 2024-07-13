package br.com.controller.util;

import java.io.*;
import java.net.URL;

public class TesteWA {
	public static void main(String[] args) {
		String urlString = "https://data.cel.cash/landingpage5063767/boletos-personalizados/735e27023f9bba1c918a826e8f3c9c44";
        String destino = "boleto.pdf";

        try {
            // Cria uma URL com o endereço da página
            URL url = new URL(urlString);

            
            System.out.println(url.toString());
            
            // Abre uma conexão com a URL
            InputStream inputStream = url.openStream();

            // Cria um arquivo local para armazenar o PDF
            FileOutputStream outputStream = new FileOutputStream(destino);

            // Lê os dados da URL e os escreve no arquivo local
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            // Fecha os fluxos de entrada e saída
            inputStream.close();
            outputStream.close();

            System.out.println("Download concluído com sucesso!");
        } catch (IOException e) {
            e.printStackTrace();
        }
   
	}
	

}
