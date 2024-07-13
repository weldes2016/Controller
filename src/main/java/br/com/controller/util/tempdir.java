package br.com.controller.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class tempdir {

	public static void main(String[] args) {

		File arq = new File("D:/tmp/O0137.txt");
		try {
			BufferedReader in = new BufferedReader(new FileReader(arq));
			String str;
			while ((str = in.readLine()) != null) {
				StringTokenizer st = null;
				st = new StringTokenizer(str, ";");
				String Codigo = st.nextToken();
				String Descricao = st.nextToken();
				String Cpj_Cnpj = st.nextToken();
				String Uf = st.nextToken();
				String Cidade = st.nextToken();
				String Endereco = st.nextToken();
				String Bairro = st.nextToken();
				String Cep = st.nextToken();
				String Fone = st.nextToken();
				String Email = st.nextToken();
				
 			
			}
			in.close();
		} catch (IOException e) {
			System.out.println("File Read Error");
		}

	}

}
