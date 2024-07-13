package br.com.controller.bean;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.joda.time.DateTime;

import br.com.controller.dao.BancoDAO;
import br.com.controller.dao.CliforDAO;
import br.com.controller.dao.DuprecDAO;
import br.com.controller.dao.RecebimentosJunoDAO;
import br.com.controller.domain.Banco;
import br.com.controller.domain.Clifor;
import br.com.controller.domain.Duprec;
import br.com.controller.domain.RecebimentosJuno;

@ManagedBean(name = "MBBoletos")
@SessionScoped
public class BoletosBean<Balance> implements Serializable {
	private static final long serialVersionUID = -5976983295877749036L;
	public String parametro;
	private double[] balance = new double[3];
	private float[] percentuais = new float[3];
	private DuprecDAO dao = new DuprecDAO();
	private RecebimentosJunoDAO daoRecebimentosJuno = new RecebimentosJunoDAO();
	private Banco banco = new Banco();
	private Duprec boleto = new Duprec();
	private Duprec boletoSelecionado = new Duprec();
	private List<Duprec> boletos = new ArrayList<Duprec>();
	private List<Duprec> boletosSelecionados = new ArrayList<Duprec>();
	private List<Clifor> clientesSelecionados = new ArrayList<Clifor>();
	private List<Duprec> boletosEmAtrasoSelecionados = new ArrayList<Duprec>();
	private List<Duprec> boletosEmAtraso = new ArrayList<Duprec>();
	private List<RecebimentosJuno> recebimentos = new ArrayList<RecebimentosJuno>();
	private List<RecebimentosJuno> recebimentosSelecionados = new ArrayList<RecebimentosJuno>();
	private List<Duprec> boletosRecebidos = new ArrayList<Duprec>();
	private float valorTransferencia = 0;
	private Transfer transfer = new Transfer();
	private ChargeList charges = new ChargeList();
	private List<Charge> lista = new ArrayList<Charge>();
	public String token;
	public String tokenGalaxPay;
	public List<Clifor> clientes = new ArrayList<Clifor>();

	public List<Clifor> getClientes() {
		return clientes;
	}

	public void setClientes(List<Clifor> clientes) {
		this.clientes = clientes;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public RecebimentosJunoDAO getDaoRecebimentosJuno() {
		return daoRecebimentosJuno;
	}

	public void setDaoRecebimentosJuno(RecebimentosJunoDAO daoRecebimentosJuno) {
		this.daoRecebimentosJuno = daoRecebimentosJuno;
	}

	public List<RecebimentosJuno> getRecebimentosSelecionados() {
		return recebimentosSelecionados;
	}

	public void setRecebimentosSelecionados(List<RecebimentosJuno> recebimentosSelecionados) {
		this.recebimentosSelecionados = recebimentosSelecionados;
	}

	public float[] getPercentuais() {
		return percentuais;
	}

	public void setPercentuais(float[] percentuais) {
		this.percentuais = percentuais;
	}

	public DuprecDAO getDao() {
		return dao;
	}

	public void setDao(DuprecDAO dao) {
		this.dao = dao;
	}

	public List<Duprec> getBoletosRecebidos() {
		return boletosRecebidos;
	}

	public void setBoletosRecebidos(List<Duprec> boletosRecebidos) {
		this.boletosRecebidos = boletosRecebidos;
	}

	public static Properties getProp() throws IOException {

		Properties props = new Properties();
		Reader file = new FileReader(
				"d:/Projetos Miklus/WEBController/src/main/java/br/com/webcontroller/factory/dados.properties");

		props.load(file);

		return props;
	}

	public List<Duprec> listarBoletos(String codigo) throws SQLException, ClassNotFoundException {
		DuprecDAO dao = new DuprecDAO();
		List<Duprec> boletos = dao.listarDupRec(codigo);
		return boletos;
	}

	public List<Clifor> listarClientesGalaxPay(String codigo) throws SQLException, ClassNotFoundException {
		CliforDAO daoClientes = new CliforDAO();
		List<Clifor> clientes = daoClientes.listarClientesGalaxPay(codigo);

		return clientes;
	}

	public List<Duprec> listarBoletosEmAtraso(String codigo) throws SQLException, ClassNotFoundException {
		boletosEmAtraso = dao.listarTitulosEmAtraso(codigo);
		return boletosEmAtraso;
	}

	public List<RecebimentosJuno> listarRecebimentosJuno(String codigo) throws SQLException, ClassNotFoundException {
		RecebimentosJunoDAO dao = new RecebimentosJunoDAO();
		recebimentos = dao.listarBoletosRecebidos(codigo);
		return recebimentos;
	}

	public List<Duprec> ble(RecebimentosJuno juno, String Codigo) {
		System.out.println(juno.getCode());
		return null;

	}

	public void importarBoletosPagos(String codigo) throws SQLException, ClassNotFoundException {
		BancoDAO dao = new BancoDAO();
		Banco banco = dao.bancoJuno(codigo);

		RecebimentosJunoDAO daojuno = new RecebimentosJunoDAO();

		ListChargesDates dates = new ListChargesDates();

		Calendar calendar = Calendar.getInstance(); // this would default to now
		calendar.add(Calendar.DAY_OF_MONTH, -4);
		dates.setBeginDueDate(calendar);
		dates.setEndDueDate(Calendar.getInstance());

		BoletoFacil boletoFacil = new BoletoFacil(BoletoFacilEnvironment.PRODUCTION, banco.getTokenJuno());

		if (banco != null) {
			banco.setTokenJuno(dao.selecionaBanco(banco.getCodBco(), codigo).getTokenJuno());

			try {
				ListChargesResponse response = boletoFacil.listCharges(dates);

				for (Charge c : response.getData().getCharges()) {

					RecebimentosJuno juno = new RecebimentosJuno();
					Boolean temRegisro = daojuno.buscaRecebimento(c.getCode(), codigo);
					System.out.println(
							"Importando recebimentos da empresa: " + codigo + " Token: " + banco.getTokenJuno());
					System.out.println("Juno: " + juno.getEmpresa() + " Tem registro: " + temRegisro);

					if (temRegisro == false) {
						juno.setAmount(c.getAmount());

						DateTime now = new DateTime(c.getDueDate());
						DateTime liberacao = new DateTime(c.getPayments().get(0).getDate());
						java.sql.Date now2 = new java.sql.Date(now.getMillis());
						java.sql.Date liberacao2 = new java.sql.Date(liberacao.getMillis());

						juno.setDueDate(now2);
						juno.setPagamentoDate(liberacao2);
						juno.setCode(c.getCode());
						juno.setPayNumber(c.getReference());
						juno.setLink(c.getLink());
						juno.setPayNumber(c.getPayNumber());
						juno.setEmpresa(codigo);
						juno.setReference(c.getReference());

						System.out.println(juno.getFee());
						daoRecebimentosJuno.salvarRecebimentos(juno, codigo);

						System.out.println("Salvo sucesso no Bean");

					} else {

						System.out.println("Registro nao foi salvo, pois ja existe no banco: " + juno.getCode());

					}
					System.out.println(c);
				}

			} catch (BoletoFacilException e) {
				// handleException(e);
			} finally {
				// doneMessage();
			}

		}
		System.out.println("Nao tem titulos");
	}

	public Duprec getRecebimentoJuno(String codigo, String code) throws SQLException, ClassNotFoundException {
		return this.daoRecebimentosJuno.getRecebimentoJuno(codigo, code);
	}

	public List<Duprec> getBoletos() {
		return boletos;
	}

	public void setBoletos(List<Duprec> boletos) {
		this.boletos = boletos;
	}

	public List<Duprec> getBoletosSelecionados() {
		return boletosSelecionados;
	}

	public void setBoletosSelecionados(List<Duprec> boletosSelecionados) {
		this.boletosSelecionados = boletosSelecionados;
	}

	public void onItemSelect(SelectEvent event) throws SQLException {
		Duprec boleto = (Duprec) event.getObject();
		boletoSelecionado = boleto;

	}

	public void onItemSelectJuno(SelectEvent event) throws SQLException {
		RecebimentosJuno juno = (RecebimentosJuno) event.getObject();

		FacesMessage msg = new FacesMessage("Boleto selecionado", juno.getCode() + " - " + juno.getDueDate());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	private Calendar dateToCalendar(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}

	public void enviarBoletos(List<Duprec> duplicatas, String codigo)
			throws SQLException, IOException, ClassNotFoundException {
		Banco banco = new Banco();
		BancoDAO dao = new BancoDAO();
		banco = dao.bancoJuno(codigo);
		if (banco.getTokenJuno() != null) {
			BoletoFacil boletoFacil = new BoletoFacil(BoletoFacilEnvironment.PRODUCTION, banco.getTokenJuno());
			for (Duprec dupRec : duplicatas) {
				// System.out.println(dupRec.getTokenjuno());
				novoBoleto(dupRec, boletoFacil, codigo);
			}
		}
	}

	public void baixarBoletos(List<RecebimentosJuno> juno, String codigo, String usuario)
			throws SQLException, ParseException, ClassNotFoundException {
		RecebimentosJunoDAO dao = new RecebimentosJunoDAO();

		for (RecebimentosJuno recebimentosJuno : juno) {

			System.out.println("Aqui esta dentro do Bean:" + recebimentosJuno.getCode() + " Data de pagamento: "
					+ recebimentosJuno.getPagamentoDate());

			dao.gerarMovBanco(recebimentosJuno, codigo, usuario);
		}
	}

	public void viewBoletos() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("resizable", false);
		PrimeFaces.current().dialog().openDynamic("viewBoletos", options, null);
	}

	public Map<String, Object> getDialogOptions() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("resizable", false);
		options.put("draggable", true);
		options.put("modal", true);
		options.put("height", 400);
		options.put("contentHeight", "100%");

		return options;
	}

	//// EMISSAO DE NOVO BOLETO

	public void novoBoleto(Duprec duprec, BoletoFacil boletoFacil, String codigoEmp)
			throws SQLException, ClassNotFoundException {

		DuprecDAO daoDupRec = new DuprecDAO();

		Payer payer = new Payer();
		payer.setName(duprec.getDescricao());
		payer.setCpfCnpj(duprec.getCpfCnpj());
		payer.setEmail(duprec.getEmail());
		payer.setPhone(duprec.getTelefone());

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		Charge charge = new Charge();
		charge.setDescription(duprec.getDescricaoNfse() + " Referente a NFSe: " + duprec.getNumNFSe() + " emitida dia: "
				+ sdf.format(duprec.getEmissao()));
		charge.setAmount(duprec.getValor());
		// System.out.println("Aqui entrou normal");
		charge.setPayer(payer);
		charge.setDueDate(dateToCalendar(duprec.getVencimento()));
		charge.setMaxOverdueDays(90);

		charge.setFine(BigDecimal.valueOf(2.00));
		charge.setInterest(BigDecimal.valueOf(4.5));

		ChargeResponse response = boletoFacil.issueCharge(charge);

		if (response.isSuccess()) {
			for (Charge c : response.getData().getCharges()) {
				System.out.println(c);
				daoDupRec.atualizarDupRec(c.getCode(), duprec.getId_duprec(), codigoEmp);

			}
		}

	}

	/// atualizacao de boletos/////-*****

	public void novoBoletoAtualizacao(Duprec duprec, BoletoFacil boletoFacil, String codigoEmp)
			throws SQLException, ClassNotFoundException {

		DuprecDAO daoDupRec = new DuprecDAO();

		Payer payer = new Payer();
		payer.setName(duprec.getDescricao());
		payer.setCpfCnpj(duprec.getCpfCnpj());
		payer.setEmail(duprec.getEmail());
		payer.setPhone(duprec.getTelefone());

		Charge charge = new Charge();
		charge.setDescription(duprec.getDescricaoNfse() + " Referente a NFSe: " + duprec.getNumNFSe() + " emitida dia: "
				+ duprec.getEmissao() + " - " + duprec.getOBS());
		charge.setAmount(duprec.getVlrAtualizado());
		charge.setPayer(payer);
		charge.setDueDate(dateToCalendar(duprec.getVencimento()));
		charge.setMaxOverdueDays(90);

		charge.setFine(BigDecimal.valueOf(2.00));
		charge.setInterest(BigDecimal.valueOf(4.5));

		ChargeResponse response = boletoFacil.issueCharge(charge);
		if (response.isSuccess()) {
			for (Charge c : response.getData().getCharges()) {
				System.out.println(c);
				daoDupRec.atualizarDupRecAtualizacao(c.getCode(), duprec.getId_duprec(), codigoEmp, duprec.getOBS());

			}
		}

	}

	public List<Duprec> listarSaldosPorBanco(String codigo) throws SQLException, ClassNotFoundException {
		DuprecDAO dao = new DuprecDAO();

		return dao.listarSaldosEmAberto(codigo);
	}

	public List<Duprec> listarBoletosABaixar(List<RecebimentosJuno> juno, String codigo)
			throws SQLException, ClassNotFoundException {
		DuprecDAO dao = new DuprecDAO();
		for (RecebimentosJuno recebimentosJuno : juno) {
			boletosRecebidos = dao.buscarBoletoABaixar(recebimentosJuno.getCode(), recebimentosJuno.getPagamentoDate(),
					codigo);
		}
		System.out.println(boletosRecebidos.get(0).getNossonum());
		return boletosRecebidos;

	}

	public void onRowSelect(SelectEvent selectEvent) {

		boletoSelecionado = (Duprec) selectEvent.getObject();

		System.out.println(boletoSelecionado.getValor());
	}

	public Duprec getBoleto() {
		return boleto;
	}

	public void setBoleto(Duprec boleto) {
		this.boleto = boleto;
	}

	public Double saldoInadimplente(LocalDateTime mes, String codigo) throws SQLException, ClassNotFoundException {
		return dao.mostrarSaldoInadimplentePorMes(mes, codigo);

	}

	public BigDecimal[] saldoDisponivelJuno(String codigoEmp) throws SQLException, IOException, ClassNotFoundException {
		BigDecimal saldo[] = new BigDecimal[3];

		BancoDAO dao = new BancoDAO();
		Banco banco = dao.bancoJuno(codigoEmp);

		if (banco != null) {
			BoletoFacil boletoFacil = new BoletoFacil(BoletoFacilEnvironment.PRODUCTION, banco.getTokenJuno());

			FetchBalanceResponse response = boletoFacil.fetchBalance(ResponseType.XML);

			saldo[0] = response.getData().getBalance();
			saldo[1] = response.getData().getWithheldBalance();
			saldo[2] = response.getData().getTransferableBalance();
		}

		return saldo;

	}

	public Banco getBanco() {
		return banco;
	}

	public void setBanco(Banco banco) {
		this.banco = banco;
	}

	public double[] getBalance() {
		return balance;
	}

	public void setBalance(double[] balance) {
		this.balance = balance;
	}

	public List<Duprec> listarDuplicatasEmAberto(String codigo) throws SQLException, ClassNotFoundException {
		DuprecDAO dao = new DuprecDAO();

		boletos = dao.listarDuplicatasEmAberto(codigo);
		return boletos;

	}

	public Double saldoInadimplente(String codigo) throws SQLException, ClassNotFoundException {
		LocalDateTime mes = LocalDateTime.now();
		return dao.mostrarSaldoInadimplentePorMes(mes, codigo);

	}

	public float totalEmAberto(String codigo) throws SQLException, ClassNotFoundException {
		DuprecDAO dao = new DuprecDAO();
		return dao.totalEmAberto(codigo);
	}

	public List<Charge> listarBoletosEmAbertoJuno(String codEmp) throws SQLException, ClassNotFoundException {
		Calendar calendar = Calendar.getInstance();
		BancoDAO dao = new BancoDAO();
		Banco banco = dao.bancoJuno(codEmp);
		System.out.println(banco.getTokenJuno());
		if (banco != null) {

			banco.setTokenJuno(dao.selecionaBanco(banco.getCodBco(), codEmp).getTokenJuno());
			BoletoFacil boletoFacil = new BoletoFacil(BoletoFacilEnvironment.PRODUCTION, banco.getTokenJuno());

			Charge charge = new Charge();

			ListChargesDates dates = new ListChargesDates();
		}
		return null;
	}

	public void atualizarBoletoJuno(Duprec boletoSelecionado, String codEmp)
			throws SQLException, IOException, ClassNotFoundException {
		BancoDAO dao = new BancoDAO();
		banco = dao.bancoJuno(codEmp);

		System.out.println("Atraso " + boletoSelecionado.getAtraso());
		System.out.println("Valor Atualizado: " + boletoSelecionado.getVlrAtualizado().floatValue());

		enviarBoletosAtualizacao(boletoSelecionado, codEmp);
	}

	public void enviarBoletosAtualizacao(Duprec boleto, String codigo)
			throws SQLException, IOException, ClassNotFoundException {
		Banco banco = new Banco();
		BancoDAO dao = new BancoDAO();
		banco = dao.bancoJuno(codigo);

		// System.out.println("Estou aqui: com o codigo do banco: " +
		// banco.getTokenJuno());

		if (banco.getTokenJuno() != null) {

			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

			BoletoFacil boletoFacil = new BoletoFacil(BoletoFacilEnvironment.PRODUCTION, banco.getTokenJuno());
			Date vencimento = new Date();
			String strVencimento = formato.format(vencimento);

			System.out.println("Novo vencimento: " + strVencimento);

			String strEmissao = sdf.format(boleto.getEmissao());

			NumberFormat nf = NumberFormat.getCurrencyInstance();

			String strValor = nf.format(boleto.getValor());
			boleto.setOBS("{Atualizado em: " + strVencimento + " ref: ao boleto anterior " + "emitido em: " + strEmissao
					+ " Valor original: " + strValor + "}");
			java.sql.Date sd = new java.sql.Date(vencimento.getTime());
			boleto.setVencimento(sd);
			novoBoletoAtualizacao(boleto, boletoFacil, codigo);
		}

	}

	public void gerarLink(Duprec boleto, String codigo) throws SQLException, IOException, ClassNotFoundException {
		Banco banco = new Banco();
		BancoDAO dao = new BancoDAO();
		banco = dao.bancoJuno(codigo);

		// System.out.println("Estou aqui: com o codigo do banco: " +
		// banco.getTokenJuno());

		if (banco.getTokenJuno() != null) {

			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

			BoletoFacil boletoFacil = new BoletoFacil(BoletoFacilEnvironment.PRODUCTION, banco.getTokenJuno());
			Date vencimento = new Date();
			String strVencimento = formato.format(vencimento);

			Charge charge = new Charge();
			charge.setDescription("Teste");

			System.out.println(boletoFacil.getBoletoFacilEnvironment().toString());

			// System.out.println("Novo vencimento: " + strVencimento);

			String strEmissao = sdf.format(boleto.getEmissao());

			NumberFormat nf = NumberFormat.getCurrencyInstance();

			String strValor = nf.format(boleto.getValor());
			boleto.setOBS("{Atualizado em: " + strVencimento + " ref: ao boleto anterior " + "emitido em: " + strEmissao
					+ " Valor original: " + strValor + "}");
			java.sql.Date sd = new java.sql.Date(vencimento.getTime());
			boleto.setVencimento(sd);

			charge.setCode(boleto.getNossonum());
			System.out.println(boletoFacil.issueCharge(charge).getData());
		}

	}

	public void transferencia(String codEmp) throws SQLException, ClassNotFoundException {
		BancoDAO dao = new BancoDAO();
		banco = dao.bancoJuno(codEmp);

		if (banco != null) {
			banco.setTokenJuno(dao.selecionaBanco(banco.getCodBco(), codEmp).getTokenJuno());
			BoletoFacil boletoFacil = new BoletoFacil(BoletoFacilEnvironment.PRODUCTION, banco.getTokenJuno());
			boletoFacil.requestTransfer(transfer);
		}
	}

	public float getValorTransferencia() {
		return valorTransferencia;
	}

	public void setValorTransferencia(float valorTransferencia) {
		this.valorTransferencia = valorTransferencia;
	}

	public Transfer getTransfer() {
		return transfer;
	}

	public void setTransfer(Transfer transfer) {
		this.transfer = transfer;
	}

	public void addMessage(String summary, String detail) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void destroyWorld() {
		addMessage("Algo deu Errado", "Tente Novamente.");
	}

	public void showMessage(Duprec boleto) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Boleto selecionado",
				boleto.getDescricao() + " - " + boleto.getValor().doubleValue());
		FacesContext.getCurrentInstance().addMessage(null, msg);

		PrimeFaces.current().dialog().showMessageDynamic(msg);
	}

	public void findSelectedDuprec(ActionEvent event) {
		if (event.getComponent().getAttributes().get("id_duprec") != null) {
			Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext()
					.getRequestParameterMap();
			int id_duprec = (params.get("id_duprec") != null) ? Integer.parseInt(params.get("id_duprec")) : -1;
			// Loop through the persons array
			for (Duprec d : boletosEmAtraso) {
				if (d.getId_duprec() == id_duprec) {
					boletoSelecionado = d;
					int dias = LocalDate.now().compareTo(boletoSelecionado.getVencimento().toLocalDate());
					boletoSelecionado.setAtraso(dias);

					boletoSelecionado.setVlrAtualizado(boletoSelecionado.getValor().add(boletoSelecionado.getJuros())
							.add(boletoSelecionado.getMulta()));

					break;
				}

			}
		}
		// System.out.println(boletoSelecionado.getValor());
	}

	public ChargeList getCharges() {
		return charges;
	}

	public void setCharges(ChargeList charges) {
		this.charges = charges;
	}

	public List<RecebimentosJuno> getRecebimentos() {
		return recebimentos;
	}

	public void setRecebimentos(List<RecebimentosJuno> recebimentos) {
		this.recebimentos = recebimentos;
	}

	public List<Charge> getLista() {
		return lista;
	}

	public void setLista(List<Charge> lista) {
		this.lista = lista;
	}

	public List<Duprec> getBoletosEmAtrasoSelecionados() {
		return boletosEmAtrasoSelecionados;
	}

	public void setBoletosEmAtrasoSelecionados(List<Duprec> boletosEmAtrasoSelecionados) {
		this.boletosEmAtrasoSelecionados = boletosEmAtrasoSelecionados;
	}

	public Duprec getBoletoSelecionado() {
		return boletoSelecionado;
	}

	public void setBoletoSelecionado(Duprec boletoSelecionado) {
		this.boletoSelecionado = boletoSelecionado;
	}

	public void testar() {
		System.out.println(parametro);
	}

	public void setParametro(Duprec boleto) {
		this.boleto = boleto;
	}

	public String getParametro() {
		return parametro;
	}

	public void setParametro(String parametro) {
		this.parametro = parametro;
	}

	public List<Duprec> getBoletosEmAtraso() {
		return boletosEmAtraso;
	}

	public void setBoletosEmAtraso(List<Duprec> boletosEmAtraso) {
		this.boletosEmAtraso = boletosEmAtraso;
	}

	public void cadastrarClientesGalaxPay(List<Clifor> clientes, String codigo)
			throws ClassNotFoundException, InterruptedException, IOException, SQLException {

		ObterToken obter = new ObterToken();
		GalaxPayBank galax = new GalaxPayBank();
		galax = obter.obterToken(codigo);

		GalaxPayServices servicesGalax = new GalaxPayServices();

		for (Clifor cli : clientes) {
			
			String myId = String.valueOf(cli.getCod());
			String name = cli.getNom();
			String document = cli.getCpfcgc();
			String email = cli.getEmail();
			String phone = cli.getFone();
			String cep = cli.getCepcob();
			String address = cli.getEndcob();
			String number = String.valueOf(cli.getNumendercob());
			String complement = cli.getComplcob();
			String neighborhood = cli.getBaicob();
			String city = cli.getCidcob().replace("â", "a");
			String stat = cli.getEstcob();		

			String body = ("{\r\n" + "    \"myId\": \"" + myId + "\",\r\n" + "    \"name\": \"" + name + "\",\r\n"
					+ "    \"document\": \"" + document + "\",\r\n" + "    \"emails\": [\r\n" + "        \"" + email
					+ "\"\r\n" + "    ],\r\n" + "    \"phones\": [\r\n" + "        " + phone + "\r\n" + "    ],\r\n"
					+ "    \"Address\": {\r\n" + "        \"zipCode\": \"" + cep + "\",\r\n" + "        \"street\": \""
					+ address + "\",\r\n" + "        \"number\": \"" + number + "\",\r\n" + "        \"complement\": \""
					+ complement + "\",\r\n" + "        \"neighborhood\": \"" + neighborhood + "\",\r\n"
					+ "        \"city\": \"" + city + "\",\r\n" + "        \"state\": \"" + stat + "\"\r\n"
					+ "    }\r\n" + "}");

		//	String mensagem = servicesGalax.salvarClientes(body, galax).getBody();
			

			
			System.out.println(body);
		}
	}

	public void listarClientesGalaxPayCadastrados(String codigo)
			throws ClassNotFoundException, InterruptedException, IOException, SQLException {
		GalaxPayServices serviceGalaxPay = new GalaxPayServices();
		Clifor clientes = new Clifor();
		ObterToken obter = new ObterToken();
		GalaxPayBank galax = new GalaxPayBank();
		galax = obter.obterToken(codigo);

		serviceGalaxPay.listarClientes(galax);
	}

	public static void main(String[] args)
			throws SQLException, IOException, ClassNotFoundException, InterruptedException {
		BoletosBean<Object> bean = new BoletosBean<Object>();
		List<Clifor> clifor = new ArrayList<Clifor>();
		CliforDAO daoClifor = new CliforDAO();
		String codigo = "2000";
		clifor = daoClifor.listarClientesGalaxPay(codigo);
		DuprecDAO duprecDAO = new DuprecDAO();

		bean.cadastrarClientesGalaxPay(clifor, codigo);
		//bean.listarClientesRegistradosGalaxPay(codigo);
	}

	public List<Clifor> getClientesSelecionados() {
		return clientesSelecionados;
	}

	public void setClientesSelecionados(List<Clifor> clientesSelecionados) {
		this.clientesSelecionados = clientesSelecionados;
	}

	public String getTokenGalaxPay() {
		return tokenGalaxPay;
	}

	public void setTokenGalaxPay(String tokenGalaxPay) {
		this.tokenGalaxPay = tokenGalaxPay;
	}

	public void enviarBoletosGalaxPay(List<Duprec> dups, String codigo)
			throws ClassNotFoundException, InterruptedException, IOException, SQLException {
		ObterToken obter = new ObterToken();
		GalaxPayBank galax = new GalaxPayBank();
		galax = obter.obterToken(codigo);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		GalaxPayServices servicesGalaxPay = new GalaxPayServices();
						
		for (Duprec duprec : dups) {
			System.out.println(duprec.getDescricao() + " - "+duprec.getDescricaoNfse());
			String vcto = sdf.format(duprec.getVencimento());

			DecimalFormat df = new DecimalFormat("0.00");					
			String valor = df.format(duprec.getValor()).replace(".", "").replace(",", "");
					
			String descricao = "Referente a NFS: "+duprec.getNumNFSe() + ". Emitida em: "+duprec.getEmissao()+ " Ref: "+duprec.getDescricaoNfse();
			
			String body = "{\"myId\": \""+duprec.getNumdup() + "-"+duprec.getOrdem()+"\",\r\n"
					+ "    \"value\": "+valor+",\r\n"
					+ "    \"additionalInfo\": \""+descricao+"\",\r\n"
					+ "    \"payday\": \""+vcto+"\",\r\n"
					+ "    \"payedOutsideGalaxPay\": false,\r\n"
					+ "    \"mainPaymentMethodId\": \"boleto\",\r\n"
					+ "    \"Customer\": {\r\n"
					+ "        \"myId\": \""+duprec.getId_galaxpay()+"\",\r\n"
					+ "        \"name\": \""+duprec.getDescricao()+"\",\r\n"
					+ "        \"document\": "+duprec.getCpfCnpj()+",\n"
					+ "        \"emails\": [\r\n"
					+ "            \""+duprec.getEmail()+"\"\r\n"
					+ "        ],\r\n"
					+ "        \"phones\": [\r\n"
					+ "          \""+duprec.getTelefone()+"\"\r\n"
					+ "        ],\r\n"
					+ "        \"Address\": {\r\n"
					+ "            \"zipCode\": \""+duprec.getCep()+"\",\r\n"
					+ "            \"street\": \""+duprec.getEndereco()+"\",\r\n"
					+ "            \"number\": "+duprec.getNumender()+",\r\n"
					+ "            \"complement\": \""+duprec.getComplemento()+"\",\r\n"
					+ "            \"neighborhood\": \""+duprec.getBairro()+"\",\r\n"
					+ "            \"city\": \""+duprec.getCidade()+"\",\r\n"
					+ "            \"state\": \""+duprec.getUf()+"\"\r\n"
					+ "        }\r\n"
					+ "    },\r\n"
					+ "    \"PaymentMethodBoleto\": {\r\n"
					+ "        \"fine\": 100,\r\n"
					+ "        \"interest\": 200,\r\n"
					+ "        \"instructions\": \""+descricao+"\",\r\n"
					+ "        \"deadlineDays\": 1\r\n"
					+ "    },\r\n"
					+ "    \"PaymentMethodPix\": {\r\n"
					+ "        \"fine\": 100,\r\n"
					+ "        \"interest\": 200,\r\n"
					+ "        \"instructions\": \"Pagar prefencialmente no PIX\",\r\n"
					+ "        \"Deadline\": {\r\n"
					+ "            \"type\": \"days\",\r\n"
					+ "            \"value\": 60\r\n"
					+ "        }\r\n"
					+ "    }\r\n"
					+ "}";
			
			
			
			
			if(servicesGalaxPay.enviarBoletos(body, galax).getStatus() == 200) {
				
				
				EnviarEmailConfirmacao enviar = new EnviarEmailConfirmacao();
				enviar.enviarConfirmacao();
				
			}else {
				FacesMessage msg = new FacesMessage("Cadastro: " + "nao foi enviado");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				
			}
			
			
		}
	}

	public List<Duprec> listarBoletosNaoRegistradosGalaxyPay(String codigo)
			throws ClassNotFoundException, SQLException {
		boletos = dao.listarDuplicatasGalaxPay(codigo);
		return boletos;
	}
	
	public void listarClientesRegistradosGalaxPay(String codigo) throws ClassNotFoundException, InterruptedException, IOException, SQLException {
		
		ObterToken obter = new ObterToken();
		GalaxPayBank galax = new GalaxPayBank();
		galax = obter.obterToken(codigo);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		GalaxPayServices servicesGalaxPay = new GalaxPayServices();
	
		servicesGalaxPay.listarClientes(galax);
	}

	public List<Clifor> listarClientesNaoCadastradosGalaxPay(String codigo) throws ClassNotFoundException, SQLException{
		CliforDAO daoClientes = new CliforDAO();
	
		return daoClientes.listarClientesGalaxPay(codigo);
		
	}
	
	public void onItemSelectJunoGalaxPay(SelectEvent event) throws SQLException {
		List<Duprec> boletosGalaxPay = (List<Duprec>) event.getObject();

		FacesMessage msg = new FacesMessage("Boleto selecionado", boletosGalaxPay.get(0).getCliente());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	

}