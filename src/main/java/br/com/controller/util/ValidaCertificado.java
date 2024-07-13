package br.com.controller.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import br.com.controller.domain.Certificados;

public class ValidaCertificado {

	public static Certificados lerCertificado(InputStream inputStream, String senha) {
		Certificados certificado = new Certificados();
		try {
			Security.addProvider(new BouncyCastleProvider());
			KeyStore keyStore = KeyStore.getInstance("PKCS12");
			keyStore.load(inputStream, senha.toCharArray());

			Enumeration<String> aliases = keyStore.aliases();
			while (aliases.hasMoreElements()) {
				String alias = aliases.nextElement();

				if (keyStore.isKeyEntry(alias)) {
					Certificate cert = keyStore.getCertificate(alias);
					if (cert instanceof X509Certificate) {
						X509Certificate certif = (X509Certificate) cert;
						certificado.setAlias(alias);
						certificado.setEmissor(extrairEmissor(certif));
						certificado.setSujeito(certif.getSubjectX500Principal().getName());
						certificado.setValInicio(certif.getNotBefore());
						certificado.setValFim(certif.getNotAfter());
						// certificado.setCnpj(extrairCnpj(certif));
						// certificado.setRazaoSocial(extrairRazaoSocial(certif.getSubjectX500Principal().getName()));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return certificado;
	}

	 private static String extrairEmissor(X509Certificate cert) {
	        return extrairInformacao(cert.getIssuerX500Principal().getName(), "CN");
	    }

	private String extrairRazaoSocial(String sujeito) {
		return extrairInformacao(sujeito, "CN");
	}

	private static String extrairInformacao(String dn, String campo) {
		Pattern pattern = Pattern.compile(campo + "=([^,]+),?");
		Matcher matcher = pattern.matcher(dn);
		if (matcher.find()) {
			return matcher.group(1).trim();
		}
		return null;
	}

	private String extrairCnpj(X509Certificate cert) {
		try {
			byte[] extensionValue = cert.getExtensionValue("2.5.29.17");
			if (extensionValue != null) {
				try (ASN1InputStream asn1InputStream = new ASN1InputStream(extensionValue)) {
					ASN1OctetString octetString = (ASN1OctetString) asn1InputStream.readObject();
					String texto = new String(octetString.getOctets(), StandardCharsets.UTF_8);
					Matcher matcher = Pattern.compile("\\d{14}").matcher(texto);
					while (matcher.find()) {
						String cnpj = matcher.group();
						if (validarCnpj(cnpj)) {
							return cnpj;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private boolean validarCnpj(String cnpj) {
		return cnpj != null && cnpj.length() == 14;
	}

	public Certificados validar(String url, String password) {
		Certificados certif = new Certificados();

		try {
			KeyStore keyStore = KeyStore.getInstance("PKCS12");
			char[] passwordString = password.toCharArray();

			// Carrega o arquivo PFX usando FileInputStream e a senha
			try (InputStream inputStream = new FileInputStream(url)) {
				keyStore.load(inputStream, passwordString);
			}

			// Obtém a chave privada e o certificado do PFX
			String alias = keyStore.aliases().nextElement(); // assume que há apenas uma entrada no keystore
			PrivateKey privateKey = (PrivateKey) keyStore.getKey(alias, passwordString);
			Certificate cert = keyStore.getCertificate(alias);

			// Exemplo de uso:
			System.out.println("Chave privada: " + privateKey);
			System.out.println("Certificado: " + cert);

			// Aqui você pode usar a chave privada e o certificado conforme necessário

		} catch (Exception e) {
			e.printStackTrace();
		}

		return certif;

	}

}
