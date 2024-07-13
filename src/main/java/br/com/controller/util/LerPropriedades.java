package br.com.controller.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import br.com.controller.domain.Parametros;


public class LerPropriedades {

	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HHmmss");
	
	public Parametros levarPropriedadesReserva() throws IOException {
		Properties prop = LerPropriedades.getProp();
		
		Parametros parametros = new Parametros();
		parametros.setDriver(prop.getProperty("prop.server.driver"));
		parametros.setBanco(prop.getProperty("prop.server.banco"));
		parametros.setHost(prop.getProperty("prop.server.host"));
		parametros.setInstacia(prop.getProperty("prop.server.instance"));
		parametros.setPorta(prop.getProperty("prop.server.porta"));
		parametros.setModo(Integer.parseInt(prop.getProperty("prop.server.modo")));
		parametros.setWhatsApp(prop.getProperty("prop.server.whatsApp"));
		parametros.setEmail(prop.getProperty("prop.server.email"));

		//System.out.println("Ultima Nota: " + parametros.getUltimaNota());
		//System.err.println("Ultma Gravação: " + parametros.getGravacao());

		Date grav = new Date();
		grav.getTime();
		System.out.println(sdf.format(grav));

		/*
		 * if (!arquivo.exists()) { arquivo.mkdirs(); }
		 */
		return parametros;

	}

	public void gravarProperties() throws IOException {

		Properties prop = LerPropriedades.getProp();
		File file = new File("\\factory\\dados.properties");
		
		try (InputStream in = new FileInputStream(file)) {
			if (in == null) {
				throw new FileNotFoundException();
			}
			
			Long grav = new Date().getTime();
			
			prop.load(in);
			prop.setProperty("prop.server.gravacao", sdf.format(grav));
			
		//	System.out.println("Até aqui deu certo");

			OutputStream out = new FileOutputStream(file);
			prop.store(out, "Gravacao do Arquivo Bayer");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

		
	public static Properties getProp() {
		Properties props = new Properties();
		InputStream file = (InputStream) Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("br/com/controller/factory/dados.properties");

		try {
			props.load(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return props;
	}
	
	public static Parametros lerPropriedades() {
		Properties prop;

		prop = getProp();

		Parametros parametros = new Parametros();

		parametros.setModo(Integer.parseInt(prop.getProperty("prop.server.modo")));// 0
		parametros.setWhatsApp(prop.getProperty("prop.server.whatsApp"));// 1
		parametros.setEmail(prop.getProperty("prop.server.email"));// 2
		parametros.setBanco(prop.getProperty("prop.server.sbanco"));// 3
		parametros.setDriver(prop.getProperty("prop.server.sdriver"));// 4
		parametros.setHost(prop.getProperty("prop.server.shost"));// 5
		parametros.setLogin(prop.getProperty("prop.server.slogin"));// 6
		parametros.setPassword(prop.getProperty("prop.server.spassword"));// 7
		parametros.setPorta(prop.getProperty("prop.server.sporta"));// 8

		// -------------------- CADUSU ----------------------//

		parametros.setUbanco(prop.getProperty("prop.server.ubanco"));
		parametros.setUdriver(prop.getProperty("prop.server.udriver"));
		parametros.setUhost(prop.getProperty("prop.server.uhost"));
		parametros.setUlogin(prop.getProperty("prop.server.ulogin"));
		parametros.setUpassword(prop.getProperty("prop.server.upassword"));
		parametros.setUporta(prop.getProperty("prop.server.uporta"));

		// --------------------- MSSQL -------------------------//
		
		parametros.setMinstance(prop.getProperty("prop.server.minstance"));
		parametros.setMdriver(prop.getProperty("prop.server.mdriver"));
		parametros.setMhost(prop.getProperty("prop.server.mhost"));
		parametros.setMloginBanco(prop.getProperty("prop.server.mloginBanco"));
		parametros.setMpassword(prop.getProperty("prop.server.mpassword"));
		parametros.setMporta(prop.getProperty("prop.server.mporta"));
		parametros.setMbanco(prop.getProperty("prop.server.mbanco"));
		
		
		return parametros;
	}
}
