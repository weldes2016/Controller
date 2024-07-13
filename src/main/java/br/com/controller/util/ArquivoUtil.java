package br.com.controller.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.text.MessageFormat;

import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.controller.dao.DuprecDAO;
import br.com.controller.domain.Empresas;
import br.com.controller.domain.Usuario;
import br.com.controller.galaxpay.domain.Boleto;

public class ArquivoUtil {

	Empresas empresa = new Empresas();

	public Empresas getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresas empresa) {
		this.empresa = empresa;
	}

	public static File escrever(String name, byte[] contents) throws IOException {
		File file = new File(diretorioRaizParaArquivos(), name);

		OutputStream out = new FileOutputStream(file);
		out.write(contents);
		out.close();

		return file;
	}

	public static List<File> listar() {
		File dir = diretorioRaizParaArquivos();

		return Arrays.asList(dir.listFiles());
	}

	public static java.io.File diretorioRaizParaArquivos() {
		File dir = new File(diretorioRaiz(), "arquivos");

		if (!dir.exists()) {
			dir.mkdirs();
		}

		return dir;
	}

	public static File diretorioRaiz() {
		// Estamos utilizando um diretório dentro da pasta temporária.
		// No seu projeto, imagino que queira mudar isso para algo como:
		// File dir = new File(System.getProperty("user.home"), "algaworks");
		File dir = new File(System.getProperty("java.io.tmpdir"), "controller");

		if (!dir.exists()) {
			dir.mkdirs();
		}

		return dir;
	}

	public String mesCorrente() {
		Date dataAtual = new Date();

		// Criar um formato para o nome do mês por extenso em português
		SimpleDateFormat formato = new SimpleDateFormat("MMMM");

		// Formatando a data e obtendo o nome do mês por extenso
		String mesAtualExtenso = formato.format(dataAtual);

		// Exibindo o resultado
		// System.out.println("Mês corrente por extenso: " + mesAtualExtenso);
		return mesAtualExtenso;
	}

	public int anoAnterior() {
		Date dataAtual = new Date();

		// Criar um formato para o nome do mês por extenso em português
		SimpleDateFormat formato = new SimpleDateFormat("YYYY");

		// Formatando a data e obtendo o nome do mês por extenso
		String anoAnteriorExtenso = formato.format(dataAtual);

		// Obter o Ano Anterior
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dataAtual);
		calendar.add(Calendar.YEAR, -1);
		int anoAnterior = calendar.get(Calendar.YEAR);

		// Exibindo o resultado
		// System.out.println("Mês corrente por extenso: " + mesAtualExtenso);
		return anoAnterior;

	}

	public int anoAtual() {
		Date dataAtual = new Date();

		// Criar um formato para o nome do mês por extenso em português
		SimpleDateFormat formato = new SimpleDateFormat("YYYY");

		// Formatando a data e obtendo o nome do mês por extenso
		String anoAtualExtenso = formato.format(dataAtual);

		// Obter o Ano Anterior
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dataAtual);

		int anoAnterior = calendar.get(Calendar.YEAR);

		// Exibindo o resultado
		// System.out.println("Mês corrente por extenso: " + mesAtualExtenso);
		return anoAnterior;

	}

	public List<String> datasMesAtual() {
		LocalDate dataAtual = LocalDate.now();
		// Define o dia como 1 para obter o primeiro dia do mês
		String primeiroDiaDoMes = LocalDate.of(dataAtual.getYear(), dataAtual.getMonth(), 1).toString();
		String diaAtual = LocalDate.now().toString();
		List<String> datas = new ArrayList<>();
		datas.add(primeiroDiaDoMes);
		datas.add(diaAtual);

		return datas;
	}
	
	public List<String> datasMesAtualFormatada() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		LocalDate dataAtual = LocalDate.now();
		
		LocalDate primeiroDiaDoMes = LocalDate.of(dataAtual.getYear(), dataAtual.getMonth(), 1);
		Date primeiroDiaDoMesDate = java.sql.Date.valueOf(primeiroDiaDoMes);
		
		Date diaAtualDate = java.sql.Date.valueOf(LocalDate.now());
	
		String diaAtual = sdf.format(diaAtualDate);
		String primeiroDia = sdf.format(primeiroDiaDoMesDate);
		
		List<String> datas = new ArrayList<>();
		datas.add(primeiroDia);
		datas.add(diaAtual);
		return datas;
	}
	

	public String formataData(String dataOriginal) throws ParseException {

		SimpleDateFormat formatoOriginal = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date data = formatoOriginal.parse(dataOriginal);

		// Formato desejado
		SimpleDateFormat formatoDesejado = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		String dataFormatada = formatoDesejado.format(data);

		return dataFormatada;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();

		empresa = (Empresas) session.getAttribute("empresaSelecionada");

		System.out.println("Empresa dento do doGet: " + empresa.getNom());
	}

	public Empresas obterEmpresaDaSessao() {
		Usuario usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get("usuarioLogado");
		Empresas empresa = usuario.getEmpresa();
		return empresa;
	}

	public Usuario obterUsuarioDaSessao() {
		Usuario usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get("usuarioLogado");
		return usuario;
	}

	public String[] extrairVariaveis(String input) {

		System.out.println("Input: " + input);
		String[] partes = null;
		String myId = "";

		myId = input.replaceAll("[\\s()]+", "");
		System.out.println("MyId: " + myId);
		String[] values = input.split("-");
		for (String string : values) {
			System.out.println(string);
		}

		return values;
	}

	protected HttpServletRequest sesssao(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();

		Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
		Empresas empresa = (Empresas) session.getAttribute("empresaSelecionada");
		return request;
	}

	public String formatarTelefone(String numeroTelefone) {
			return MessageFormat.format("({0}) {1}-{2}", numeroTelefone.substring(0, 2), numeroTelefone.substring(2, 7),
				numeroTelefone.substring(7));
	}
	
	public boolean isDiaUtil(LocalDate data) {
		// Obtenha o dia da semana da data
		DayOfWeek diaDaSemana = data.getDayOfWeek();
		return !diaDaSemana.equals(DayOfWeek.SATURDAY) && !diaDaSemana.equals(DayOfWeek.SUNDAY);
	}

	
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		String var = "19525-012";
		ArquivoUtil ut = new ArquivoUtil();
		DuprecDAO dao = new DuprecDAO();
		Boleto bol = new Boleto();
		bol.setBankNumber("1544114");
		bol.setGalaxPayId("222222");
		bol.setMyId("14690314-11");
		LocalDate dataAtual = LocalDate.now();
	

        System.out.println(ut.datasMesAtualFormatada().get(0) + " - "+ut.datasMesAtualFormatada().get(1));

	}

}
