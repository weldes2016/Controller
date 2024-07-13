package br.com.controller.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class somarHora {

	public static void main(String[] args) {
		Date hoje = new Date();
		String formato = "dd/MM/yyyy";
		SimpleDateFormat formatter = new SimpleDateFormat(formato);
		System.out.println("A data formatada é: " + formatter.format(hoje));

		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(new Date());

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		System.out.println("HORA ATUAL: " + sdf.format(gc.getTime()));
		/// AQUI EU SETO A HORA QUE QUERO SOMAR E MAIS OS MINUTOS, SE QUISER SOMAR OS
		/// SEGUNDO É SÓ COLOCAR O SEGUNDOS PARA SOMAR
		gc.add(Calendar.HOUR, 24);
		gc.add(Calendar.MINUTE, 30);
		System.out.println("HORA SOMADA: " + sdf2.format(gc.getTime()));
		gc.add(Calendar.SECOND, 15);
		///
		System.out.println("HORA SOMADA: " + sdf.format(gc.getTime()));
	
		System.out.println("HORA SOMADA: " + sdf2.format(gc.getTime()));

	}

}
