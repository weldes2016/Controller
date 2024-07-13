package br.com.controller.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.StringTokenizer;

import org.primefaces.model.file.UploadedFile;


public class lerArquivo {
	private UploadedFile uploadedFile;

	public void ler(File arquivo) throws FileNotFoundException {

		try {
			FileReader arq = new FileReader(arquivo.getAbsoluteFile());
			BufferedReader leitor = new BufferedReader(arq);
			StringTokenizer st = null;
			String linha = null;

			while ((linha = leitor.readLine()) != null) {
				st = new StringTokenizer(linha, ";");
				/*
				 * String codigo = st.nextToken(); String Descricao = st.nextToken(); String
				 * Cpj_Cnpj = st.nextToken(); String Uf = st.nextToken(); String Cidade =
				 * st.nextToken(); String Endereco = st.nextToken(); String Bairro =
				 * st.nextToken(); String Cep = st.nextToken(); String Fone = st.nextToken();
				 * String email = st.nextToken(); System.out.println(codigo + " " + email);
				 */
				System.out.println(st);
			}
			leitor.close();
			arq.close();

		} catch (

		Exception e) {
			e.printStackTrace();

		}

	}

	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

}