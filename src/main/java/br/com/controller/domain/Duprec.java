package br.com.controller.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

public class Duprec implements Serializable {

	private static final long serialVersionUID = 8089677699952272861L;
	private int Id_duprec;
	private String Numdup;
	private String Nf;
	private String CpfCnpj;
	private String Descricao;
	private String Endereco;
	private String Complemento;
	private String Bairro;
	private String Cidade;
	private String Uf;
	private String Cep;
	private String Telefone;
	private String Email;
	private Date Emissao;
	private Date Vencimento;
	private float  Valor;
	private Date DataNascimento;
	private String Ordem;
	private String DescricaoNfse;
	private String nossonum;
	private Banco banco;
	private Clifor codcli;
	private String NumNFSe;
	private String OBS;
	private String filial;
	private BigDecimal ValorPago;
	private Date DataPagamento;
	private String tokenjuno;
	private BigDecimal multa;
	private BigDecimal juros;
	private String cliente;
	private int atraso;
	private BigDecimal vlrAtualizado;
	private int numender;	
	private String id_galaxpay;
	private String cod;
	private String stringField;
	private double doubleField;
	private Boolean disable;
	private String urlPDF;
	private String telefone2;
	private String contato1;
	private String contato2;
	
	public String getContato1() {
		return contato1;
	}

	public void setContato1(String contato1) {
		this.contato1 = contato1;
	}

	public String getContato2() {
		return contato2;
	}

	public void setContato2(String contato2) {
		this.contato2 = contato2;
	}

	public Duprec(String stringField, double doubleField) {
		this.stringField = stringField;
		this.doubleField = doubleField;
		}

	public Duprec() {
	}

	public String getStringField() {
		return stringField;
	}




	public void setStringField(String stringField) {
		this.stringField = stringField;
	}




	public double getDoubleField() {
		return doubleField;
	}




	public void setDoubleField(double doubleField) {
		this.doubleField = doubleField;
	}

	public int getId_duprec() {
		return Id_duprec;
	}

	public void setId_duprec(int id_duprec) {
		Id_duprec = id_duprec;
	}

	public String getCpfCnpj() {
		return CpfCnpj;
	}

	public void setCpfCnpj(String cpfCnpj) {
		CpfCnpj = cpfCnpj;
	}

	public String getDescricao() {
		return Descricao;
	}

	public void setDescricao(String descricao) {
		Descricao = descricao;
	}

	public String getEndereco() {
		return Endereco;
	}

	public void setEndereco(String endereco) {
		Endereco = endereco;
	}

	public String getBairro() {
		return Bairro;
	}

	public void setBairro(String bairro) {
		Bairro = bairro;
	}

	public String getCidade() {
		return Cidade;
	}

	public void setCidade(String cidade) {
		Cidade = cidade;
	}

	public String getUf() {
		return Uf;
	}

	public void setUf(String uf) {
		Uf = uf;
	}

	public String getCep() {
		return Cep;
	}

	public void setCep(String cep) {
		Cep = cep;
	}

	public String getTelefone() {
		return Telefone;
	}

	public void setTelefone(String telefone) {
		Telefone = telefone;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public Date getEmissao() {
		return Emissao;
	}

	public void setEmissao(Date emissao) {
		this.Emissao = emissao;
	}

	public Date getVencimento() {
		return Vencimento;
	}

	public void setVencimento(Date date) {
		Vencimento = date;
	}



	public float getValor() {
		return Valor;
	}

	public void setValor(float valor) {
		Valor = valor;
	}

	public String getComplemento() {
		return Complemento;
	}

	public void setComplemento(String complemento) {
		Complemento = complemento;
	}

	public String getNumdup() {
		return Numdup;
	}

	public void setNumdup(String numdup) {
		Numdup = numdup;
	}

	public String getNf() {
		return Nf;
	}

	public void setNf(String nf) {
		Nf = nf;
	}

	public Date getDataNascimento() {
		return DataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		DataNascimento = dataNascimento;
	}

	public String getOrdem() {
		return Ordem;
	}

	public void setOrdem(String ordem) {
		Ordem = ordem;
	}

	public String getDescricaoNfse() {
		return DescricaoNfse;
	}

	public void setDescricaoNfse(String descricaoNfse) {
		DescricaoNfse = descricaoNfse;
	}

	public Banco getBanco() {
		return banco;
	}

	public void setBanco(Banco banco) {
		this.banco = banco;
	}

	public String getTokenjuno() {
		return tokenjuno;
	}

	public void setTokenjuno(String tokenjuno) {
		this.tokenjuno = tokenjuno;
	}

	public BigDecimal getMulta() {
		return multa;
	}

	public void setMulta(BigDecimal multa) {
		this.multa = multa;
	}

	public BigDecimal getJuros() {
		return juros;
	}

	public void setJuros(BigDecimal juros) {
		this.juros = juros;
	}

	public String getFilial() {
		return filial;
	}

	public void setFilial(String filial) {
		this.filial = filial;
	}

	public BigDecimal getValorPago() {
		return ValorPago;
	}

	public void setValorPago(BigDecimal bigDecimal) {
		ValorPago = bigDecimal;
	}

	public Date getDataPagamento() {
		return DataPagamento;
	}

	public void setDataPagamento(java.util.Date date) {
		DataPagamento = (Date) date;
	}

	public String getNossonum() {
		return nossonum;
	}

	public void setNossonum(String nossonum) {
		this.nossonum = nossonum;
	}

	public Clifor getCodcli() {
		return codcli;
	}

	public void setCodcli(Clifor codcli) {
		this.codcli = codcli;
	}

	public String getNumNFSe() {
		return NumNFSe;
	}

	public void setNumNFSe(String numNFSe) {
		NumNFSe = numNFSe;
	}

	public String getOBS() {
		return OBS;
	}

	public void setOBS(String oBS) {
		OBS = oBS;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public BigDecimal getVlrAtualizado() {
		return vlrAtualizado;
	}

	public void setVlrAtualizado(BigDecimal vlrAtualizado) {
		this.vlrAtualizado = vlrAtualizado;
	}

	public int getAtraso() {
		return atraso;
	}

	public void setAtraso(int atraso) {
		this.atraso = atraso;
	}

	public int getNumender() {
		return numender;
	}

	public void setNumender(int numender) {
		this.numender = numender;
	}

	

	public String getId_galaxpay() {
		return id_galaxpay;
	}

	public void setId_galaxpay(String id_galaxpay) {
		this.id_galaxpay = id_galaxpay;
	}

	public String getCod() {
		return cod;
	}

	public void setCod(String cod) {
		this.cod = cod;
	}

	public Boolean getDisable() {
		return disable;
	}

	public void setDisable(Boolean disable) {
		this.disable = disable;
	}

	public String getUrlPDF() {
		return urlPDF;
	}

	public void setUrlPDF(String urlPDF) {
		this.urlPDF = urlPDF;
	}

	public String getTelefone2() {
		return telefone2;
	}

	public void setTelefone2(String telefone2) {
		this.telefone2 = telefone2;
	}

	
}
