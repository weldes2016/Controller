package br.com.controller.util;


import java.time.DayOfWeek;
import java.time.LocalDate;

public class DiaUtilChecker {
	public static void main(String[] args) {
		// Defina a data para verificar
		LocalDate data = LocalDate.of(2024, 3, 23); // Exemplo: 27 de março de 2024

		// Verifique se a data é um dia útil
		if (isDiaUtil(data)) {
			System.out.println(data + " é um dia útil.");
		} else {
			System.out.println(data + " não é um dia útil.");
		}
	}

	public static boolean isDiaUtil(LocalDate data) {
		// Obtenha o dia da semana da data
		DayOfWeek diaDaSemana = data.getDayOfWeek();

		// Verifique se o dia da semana é sábado ou domingo
		return !diaDaSemana.equals(DayOfWeek.SATURDAY) && !diaDaSemana.equals(DayOfWeek.SUNDAY);
	}
}
