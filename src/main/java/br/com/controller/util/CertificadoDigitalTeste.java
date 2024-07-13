package br.com.controller.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.jce.provider.BouncyCastleProvider;


public class CertificadoDigitalTeste {
    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    private static String getValorOID(X509Certificate certificado, String oid) {
        try {
            byte[] extensionValue = certificado.getExtensionValue(oid);
            if (extensionValue != null) {
                try (ASN1InputStream asn1InputStream = new ASN1InputStream(extensionValue)) {
                    ASN1Primitive derOctetString = asn1InputStream.readObject();
                    if (derOctetString instanceof DEROctetString) {
                        byte[] octets = ((DEROctetString) derOctetString).getOctets();
                        try (ASN1InputStream asn1InputStream2 = new ASN1InputStream(octets)) {
                            ASN1Primitive asn1Primitive = asn1InputStream2.readObject();
                            // Aqui você precisa ajustar como extrair a string correta
                            return asn1Primitive.toString();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    
    private static void extrairInformacoes(X509Certificate certif) {
        // OIDs especificados
        String oidNomeResponsavel = "2.16.76.1.3.2";
        String oidEmail = "1.2.840.113549.1.9.1"; // OID comum para email em certificados
        String oidTelefone = "2.16.76.1.3.8"; // Ajuste este OID conforme a necessidade

        // Extração dos valores
        String nomeResponsavel = getValorOID(certif, oidNomeResponsavel);
        String email = getValorOID(certif, oidEmail);
        String telefone = getValorOID(certif, oidTelefone);

        // Exibindo as informações extraídas
        System.out.println("Nome do Responsável: " + nomeResponsavel);
        System.out.println("Email: " + email);
        System.out.println("Telefone: " + telefone);
    }
    
    public static void main(String[] args) {
        String pfxFilePath = "C:/Certificados/MIKLUS_CONSULTORIA.pfx";
        String pfxPassword = "304620";

        try {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            char[] password = pfxPassword.toCharArray();

            // Carrega o arquivo PFX usando FileInputStream e a senha
            try (InputStream inputStream = new FileInputStream(pfxFilePath)) {
                keyStore.load(inputStream, password);
            }

            // Obtém a chave privada e o certificado do PFX
            String alias = keyStore.aliases().nextElement(); // assume que há apenas uma entrada no keystore
            PrivateKey privateKey = (PrivateKey) keyStore.getKey(alias, password);
            Certificate cert = keyStore.getCertificate(alias);

            // Exemplo de uso:
            System.out.println("Chave privada: " + privateKey);
            System.out.println("Certificado: " + cert);
            
            // Aqui você pode usar a chave privada e o certificado conforme necessário

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}