package br.com.controller.bean;

import java.io.IOException;
import java.io.Serializable;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;

import com.mashape.unirest.http.exceptions.UnirestException;

import br.com.controller.dao.BancoDAO;
import br.com.controller.dao.CliforDAO;
import br.com.controller.dao.DuprecDAO;
import br.com.controller.dao.FaturamentoDAO;
import br.com.controller.domain.Duprec;
import br.com.controller.domain.Empresas;
import br.com.controller.domain.Fatser;
import br.com.controller.domain.HistoricoWA;
import br.com.controller.domain.Parametros;
import br.com.controller.galaxpay.domain.Boleto;
import br.com.controller.galaxpay.domain.GalaxPayBank;
import br.com.controller.galaxpay.domain.GalaxPayClifor;
import br.com.controller.galaxpay.services.GalaxPayServices;
import br.com.controller.galaxpay.services.ObterToken;
import br.com.controller.galaxpay.util.EnviarEmailConfirmacao;
import br.com.controller.galaxpay.util.LerRetornos;
import br.com.controller.services.EnviarWA;
import br.com.controller.util.ArquivoUtil;
import br.com.controller.util.LerPropriedades;
import kong.unirest.HttpResponse;

@Named(value = "MBDuplicatas")
@SessionScoped
public class DupRecBean implements Serializable {
	private static final long serialVersionUID = 8131061594289771167L;
	private DuprecDAO dao = new DuprecDAO();
	private List<Duprec> boletos = new ArrayList<Duprec>();
	private List<Boleto> boletosGP = new ArrayList<Boleto>();
	private List<Duprec> boletosPortal = new ArrayList<>();
	private List<Duprec> boletosSelecionados = new ArrayList<Duprec>();
	private List<Duprec> boletosSelecionadosWA = new ArrayList<Duprec>();
	private List<Duprec> boletosAEnviarWA = new ArrayList<Duprec>();
	private List<Duprec> boletosFiltrados = new ArrayList<Duprec>();
	private Duprec boleto = new Duprec();
	private Parametros par = new Parametros();
	private LerPropriedades ler = new LerPropriedades();
	private float total = 0;
	private float percentual = 0;
	private EnviarEmailConfirmacao enviar = new EnviarEmailConfirmacao();
	private BancoDAO daoBanco = new BancoDAO();
	private Boleto bolGalax = new Boleto();
	private GalaxPayBank galax = new GalaxPayBank();
	private List<String> tokens = new ArrayList<>();
	private ObterToken obter = new ObterToken();	
	private String enviados;
	private List<HistoricoWA> historicosWA = new ArrayList<>();
	private HistoricoWA historicoWA = new HistoricoWA();
	private ArquivoUtil util = new ArquivoUtil();
	private String dtInicial;
	private String dtFinal;
	
	
	public ArquivoUtil getUtil() {
		return util;
	}

	public void setUtil(ArquivoUtil util) {
		this.util = util;
	}

	public SimpleDateFormat getSdf() {
		return sdf;
	}

	public void setSdf(SimpleDateFormat sdf) {
		this.sdf = sdf;
	}

	GalaxPayServices servicesGalaxPay = new GalaxPayServices();
	
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	public GalaxPayServices getServicesGalaxPay() {
		return servicesGalaxPay;
	}

	public void setServicesGalaxPay(GalaxPayServices servicesGalaxPay) {
		this.servicesGalaxPay = servicesGalaxPay;
	}

	

	public List<Boleto> getBoletosGP() {
		return boletosGP;
	}

	public void setBoletosGP(List<Boleto> boletosGP) {
		this.boletosGP = boletosGP;
	}

	public String getDtInicial() {
		return dtInicial;
	}

	public void setDtInicial(String dtInicial) {
		this.dtInicial = dtInicial;
	}

	public String getDtFinal() {
		return dtFinal;
	}

	public void setDtFinal(String dtFinal) {
		this.dtFinal = dtFinal;
	}

	public Duprec getBoleto() {
		return boleto;
	}

	public void setBoleto(Duprec boleto) {
		this.boleto = boleto;
	}

	public DuprecDAO getDao() {
		return dao;
	}

	public void setDao(DuprecDAO dao) {
		this.dao = dao;
	}

	public List<Duprec> getBoletos() {
		return boletos;
	}

	public void setBoletos(List<Duprec> boletos) {
		this.boletos = boletos;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public float getPercentual() {
		return percentual;
	}

	public void setPercentual(float percentual) {
		this.percentual = percentual;
	}

	public Parametros getPar() {
		return par;
	}

	public void setPar(Parametros par) {
		this.par = par;
	}

	public LerPropriedades getLer() {
		return ler;
	}

	public void setLer(LerPropriedades ler) {
		this.ler = ler;
	}

	public EnviarEmailConfirmacao getEnviar() {
		return enviar;
	}

	public void setEnviar(EnviarEmailConfirmacao enviar) {
		this.enviar = enviar;
	}

	public BancoDAO getDaoBanco() {
		return daoBanco;
	}

	public void setDaoBanco(BancoDAO daoBanco) {
		this.daoBanco = daoBanco;
	}

	public Boleto getBolGalax() {
		return bolGalax;
	}

	public void setBolGalax(Boleto bolGalax) {
		this.bolGalax = bolGalax;
	}

	public List<Duprec> listarDuplicatas(String codigo) throws SQLException, ClassNotFoundException {
		return dao.listarDupRec(codigo);
	}

	public List<Duprec> listarBoletosGalaxPay(String codigo) throws SQLException, ClassNotFoundException {
		boletos = dao.listarDuplicatasGalaxPay(codigo);
		return boletos;
	}

	public float totalEmAberto(String codigo) throws SQLException, ClassNotFoundException {
		total = dao.totalEmAberto(codigo);
		return total;
	}

	public GalaxPayBank getGalax() {
		return galax;
	}

	public void setGalax(GalaxPayBank galax) {
		this.galax = galax;
	}

	public List<String> getTokens() {
		return tokens;
	}

	public void setTokens(List<String> tokens) {
		this.tokens = tokens;
	}

	public ObterToken getObter() {
		return obter;
	}

	public void setObter(ObterToken obter) {
		this.obter = obter;
	}

	public List<Duprec> getBoletosPortal() {
		return boletosPortal;
	}

	public void setBoletosPortal(List<Duprec> boletosPortal) {
		this.boletosPortal = boletosPortal;
	}
	
	

	public List<HistoricoWA> getHistoricosWA() {
		return historicosWA;
	}

	public void setHistoricosWA(List<HistoricoWA> historicosWA) {
		this.historicosWA = historicosWA;
	}

	public HistoricoWA getHistoricoWA() {
		return historicoWA;
	}

	public void setHistoricoWA(HistoricoWA historicoWA) {
		this.historicoWA = historicoWA;
	}

	@PostConstruct
	public void init() {
		boletos = getBoletos();
		dtInicial = util.datasMesAtualFormatada().get(0);
		dtFinal = util.datasMesAtualFormatada().get(1);
		
		
	}

	public float percentualEmpresa(String codigo) throws SQLException, ClassNotFoundException {
		percentual = ((dao.totalEmAberto(codigo) / (dao.totalEmAberto("1000") + dao.totalEmAberto("2000")
				+ dao.totalEmAberto("9000") + dao.totalEmAberto("9999")) * 100));

		float fPercencial = (float) Math.floor(percentual);
		return fPercencial;
	}

	public List<Duprec> getBoletosSelecionados() {
		return boletosSelecionados;
	}

	public void setBoletosSelecionados(List<Duprec> boletosSelecionados) {
		this.boletosSelecionados = boletosSelecionados;
	}

	public double totalAbertoGeral() throws SQLException, ClassNotFoundException {
		double totalgeral = dao.totalEmAberto("1000") + dao.totalEmAberto("2000") + dao.totalEmAberto("9000")
				+ dao.totalEmAberto("9999");
		return totalgeral;

	}

	public void onRowSelect(SelectEvent<Duprec> event) {
		boleto = event.getObject();
		boletos.add(boleto);
		FacesMessage msg = new FacesMessage("Boleto Selecionado",
				String.valueOf(event.getObject().getCliente() + " - " + event.getObject().getValor()));
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void listarBoletosSelecionados() {
		for (Duprec duprec : boletosSelecionados) {
			System.out.println("Boleto: " + duprec.getId_duprec() + " Valor:" + duprec.getValor());
		}
	}

	public void enviarBoletosGalaxPay(List<Duprec> dups, Empresas empresa)
			throws ClassNotFoundException, InterruptedException, IOException, SQLException, UnirestException {

		Parametros par = new Parametros();
		par = LerPropriedades.lerPropriedades();

		System.out.println("Empresa Selecionada no Bean: " + empresa.getUsu());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
		float totalBoletos = 0;
		int qtdeBoletos = 0;

		GalaxPayServices services = new GalaxPayServices();

		FaturamentoDAO faturamentoDAO = new FaturamentoDAO();
		CliforDAO daoClifor = new CliforDAO();

		ObterToken obter = new ObterToken();
		List<String> tokens = obter.obterToken(empresa.getUsu());

		for (Duprec duprec : dups) {
			Fatser fatser = new Fatser();
			String vcto = sdf.format(duprec.getVencimento());
			String descricao = "";
			String numeroDocto = "";

			DecimalFormat df = new DecimalFormat("0.00");

			String valor = df.format(duprec.getValor()).replace(".", "").replace(",", "");

			System.out.println("Veio até aqui, Antes de verificar se tem nota e o valor é: " + valor);
			fatser = faturamentoDAO.verificaSeTemNfse(duprec, empresa.getUsu());

			System.out.println("Nota: " + fatser.getNumNfSer() + " - " + fatser.getCodVerificacao());

			// System.out.println("O que tem aqui? "+fatser.getNumNfSer() + " -
			// FilialobjBoleto.getInt(\"galaxPayId\"): " + fatser.getFilial());

			if (fatser.getNumNfSer() != null) {
				System.out.println("encontrou nota");
				descricao = "Referente a NFS: " + duprec.getNumNFSe() + ". Emitida em: "
						+ sdf2.format(duprec.getEmissao()) + " Ref: " + duprec.getDescricaoNfse() + "\\n"
						+ " Validação NFS-e: http://www2.goiania.go.gov.br/sistemas/snfse/asp/snfse00200w0.asp?inscricao="
						//+ URLEncoder.encode(fatser.getInscMunicipal(), "UTF-8") + "&" + "nota="
						+ URLEncoder.encode(fatser.getInscMunicipal(), "UTF-8") + "&nota="
						+ URLEncoder.encode(fatser.getNumNfSer(), "UTF-8") + "&verificador="
						+ URLEncoder.encode(fatser.getCodVerificacao(), "UTF-8");

				numeroDocto = duprec.getNumdup() + "-" + duprec.getOrdem();
				System.err.println(descricao + " - " + numeroDocto + "-" + duprec.getFilial() + " Nao deve ter filial");
			} else {
				descricao = "Fatura: " + duprec.getNumdup() + ". Emitida em: " + sdf2.format(duprec.getEmissao()) + ", "
						+ duprec.getDescricaoNfse();
				// numeroDocto = duprec.getNumdup() + "-" + duprec.getOrdem() + "-(" +
				// duprec.getFilial() + ")";
				numeroDocto = duprec.getNumdup() + "-" + duprec.getOrdem();
				System.err.println(descricao + " - " + numeroDocto + "-Filial:" + duprec.getFilial());
			}

			String body = "{\"myId\": \"" + numeroDocto + "\",\r\n" + "    \"value\": " + valor + ",\r\n"
					+ "    \"additionalInfo\": \"" + descricao + "\",\r\n" + "    \"payday\": \"" + vcto + "\",\r\n"
					+ "    \"expirationAfterPayment\": 59,\r\n"
					+ "    \"payedOutsideGalaxPay\": false,\r\n" + "    \"mainPaymentMethodId\": \"boleto\",\r\n"
					+ "    \"Customer\": {\r\n" + "        \"myId\": \"" + duprec.getId_galaxpay() + "\",\r\n"
					+ "        \"name\": \"" + duprec.getDescricao() + "\",\r\n" + "\"document\": \""
					+ duprec.getCpfCnpj() + "\",\r\n" + "        \"emails\": [\r\n" + "            \""
					+ duprec.getEmail() + "\"\r\n" + "        ],\r\n" + "        \"phones\": [\r\n" + "          \""
					+ duprec.getTelefone() + "\"\r\n" + "        ],\r\n" + "        \"Address\": {\r\n"
					+ "            \"zipCode\": \"" + duprec.getCep() + "\",\r\n" + "            \"street\": \""
					+ duprec.getEndereco() + "\",\r\n" + "            \"number\": " + duprec.getNumender() + ",\r\n"
					+ "            \"complement\": \"" + duprec.getComplemento() + "\",\r\n"
					+ "            \"neighborhood\": \"" + duprec.getBairro() + "\",\r\n" + "            \"city\": \""
					+ duprec.getCidade() + "\",\r\n" + "            \"state\": \"" + duprec.getUf() + "\"\r\n"
					+ "        }\r\n" + "    },\r\n" + "    \"PaymentMethodBoleto\": {\r\n"
					+ "        \"fine\": 450,\r\n" + "        \"interest\": 200,\r\n" + "        \"instructions\": \""
					+ descricao + "\",\r\n" + "        \"deadlineDays\": 59\r\n" + "    },\r\n"
					+ "    \"PaymentMethodPix\": {\r\n" + "        \"fine\": 100,\r\n"
					+ "        \"interest\": 200,\r\n"
					+ "        \"instructions\": \"Pagar prefencialmente no PIX\",\r\n" + "        \"Deadline\": {\r\n"
					+ "            \"type\": \"days\",\r\n" + "            \"value\": 60\r\n" + "        }\r\n"
					+ "    }\r\n" + "}";

			HttpResponse<String> response = services.enviarBoletos(body, tokens, par);
			System.out.println("Response dentro do Bean, voltando do retorno do Services: " + response.getBody());
			System.out.println("Status do Response: " + response.getStatus());

			if (response.getStatus() != 200) {
				System.out.println("Entrou aqui, no enviar e-mail");

				enviar.enviarConfirmacaoDeErro(duprec, par.getEmail(), par.getWhatsApp(),
						response.getBody().toString());

			} else {
				totalBoletos = totalBoletos + duprec.getValor();
				qtdeBoletos = qtdeBoletos + 1;
				System.out.println("Entrou aqui! agora vai salvar a alteracao no direcuts:");

				LerRetornos lerRetorno = new LerRetornos();
				bolGalax = lerRetorno.lerRetornos(response.getBody());
				System.err.println("GalxPayID, vai para o numero do cheque " + bolGalax.getGalaxPayId());
				System.err.println("NumBank, vai para o nosso numero " + bolGalax.getBankNumber());

				dao.atualizarDupRecGalaxPay(bolGalax, empresa.getUsu());
							
				EnviarWA enviar = new EnviarWA();
				enviar.enviarMensagemBoleto(duprec, empresa, bolGalax);

			}

		}
		enviar.enviarConfirmacao(par.getEmail(), par.getWhatsApp(), totalBoletos, qtdeBoletos);
	}

	public List<GalaxPayClifor> listarClientesGalaxPayCadastrados(String codigo)
			throws ClassNotFoundException, IOException, SQLException, InterruptedException, UnirestException {
		GalaxPayServices services = new GalaxPayServices();
		List<GalaxPayClifor> clientesGalaxPay = (List<GalaxPayClifor>) services.listarClientes(codigo);
		return clientesGalaxPay;
	}

	public List<Boleto> listarBoletosGalaxPayPortal(String codigo)
			throws ClassNotFoundException, InterruptedException, IOException, SQLException, UnirestException {
		GalaxPayServices service = new GalaxPayServices();
		boletosGP = service.listarBoletosGalaxPay(codigo);
		for (Boleto boleto : boletosGP) {
			System.out.println("GalaxPayId: " + boleto.getGalaxPayId() + " - " + boleto.getCliente());

		}
		return boletosGP;
	}

	public List<Duprec> listarDuprecBoletosAEnviarWA(String codigo, String dtInicial, String dtFinal, String enviados)
			throws ClassNotFoundException, SQLException, InterruptedException, IOException, UnirestException,
			ParseException {
		
		System.out.println("dentro do Bean: "+enviados);
		boletosAEnviarWA.clear();
		boletosAEnviarWA = dao.listarDuprecBoletosAEnviarWA(dtInicial, dtFinal, codigo, enviados);

		return boletosAEnviarWA;
	}

	public String formatarTelefone(String numeroTelefone) {

		return MessageFormat.format("({0}) {1}-{2}", numeroTelefone.substring(0, 2), numeroTelefone.substring(2, 7),
				numeroTelefone.substring(7));
	}

	public List<Duprec> getBoletosSelecionadosWA() {
		return boletosSelecionadosWA;
	}

	public void setBoletosSelecionadosWA(List<Duprec> boletosSelecionadosWA) {
		this.boletosSelecionadosWA = boletosSelecionadosWA;
	}

	public List<Duprec> getBoletosAEnviarWA() {
		return boletosAEnviarWA;
	}

	public void setBoletosAEnviarWA(List<Duprec> boletosAEnviarWA) {
		this.boletosAEnviarWA = boletosAEnviarWA;
	}

	public List<Duprec> getBoletosFiltrados() {
		return boletosFiltrados;
	}

	public void setBoletosFiltrados(List<Duprec> boletosFiltrados) {
		this.boletosFiltrados = boletosFiltrados;
	}

	public void enviarBoletosWhatsApp(List<Duprec> duprec, Empresas empresa)
			throws ClassNotFoundException, InterruptedException, IOException, SQLException, UnirestException {
		EnviarWA enviarWA = new EnviarWA();
		
		for (Duprec duprec2 : duprec) {
			Boleto bol = new Boleto();
			
			System.out.println("ID:" +duprec2.getId_duprec()+  " Duprec "+duprec2.getNumdup() + " Duprec.Filial: "+duprec2.getFilial());
			bol.setPdf(servicesGalaxPay.gerarPDFCobranca(duprec2.getId_galaxpay(), empresa.getUsu()));
			
			enviarWA.enviarMensagemBoleto(duprec2, empresa, bol);
		}
	}
	
	public void listarHistoricosWA(int id, String codigo) throws ClassNotFoundException, SQLException{
		historicosWA = dao.listarHistoricoWA(id, codigo);
	}

	public String getEnviados() {
		return enviados;
	}

	public void setEnviados(String enviados) {
		this.enviados = enviados;
	}
}