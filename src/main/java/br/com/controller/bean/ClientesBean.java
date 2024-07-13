package br.com.controller.bean;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;

import com.mashape.unirest.http.exceptions.UnirestException;

import br.com.controller.dao.CliforDAO;
import br.com.controller.domain.Clifor;
import br.com.controller.galaxpay.services.GalaxPayServices;

@Named(value = "MBClientes")
@SessionScoped
public class ClientesBean implements Serializable {
	private static final long serialVersionUID = 200944315848910914L;
	private List<Clifor> clientesSelecionados = new ArrayList<Clifor>();
	private CliforDAO daoClifor = new CliforDAO();
	private Clifor clifor = new Clifor();
	private List<Clifor> clientesNaoCadastrados = new ArrayList<Clifor>();

	public List<Clifor> getClientesSelecionados() {
		return clientesSelecionados;
	}

	public void setClientesSelecionados(List<Clifor> clientesSelecionados) {
		this.clientesSelecionados = clientesSelecionados;
	}

	public CliforDAO getDaoClifor() {
		return daoClifor;
	}

	public void setDaoClifor(CliforDAO daoClifor) {
		this.daoClifor = daoClifor;
	}

	public Clifor getClifor() {
		return clifor;
	}

	public void setClifor(Clifor clifor) {
		this.clifor = clifor;
	}

	public void onRowSelect(SelectEvent<Clifor> event) {
		clifor = event.getObject();
		clientesSelecionados.add(clifor);
	}
	

	public List<Clifor> getClientesNaoCadastrados() {
		return clientesNaoCadastrados;
	}

	public void setClientesNaoCadastrados(List<Clifor> clientesNaoCadastrados) {
		this.clientesNaoCadastrados = clientesNaoCadastrados;
	}

	public void cadastrarClientesGalaxPay(List<Clifor> clientesSelecionados, String codigo)	throws ClassNotFoundException, SQLException, InterruptedException, IOException, UnirestException {
		System.out.println("Cadastrar Clientes Dentro do Bean: "+codigo);
		GalaxPayServices services = new GalaxPayServices();
		services.cadastrarClienteGalaxPay(clientesSelecionados, codigo);
	}
	
	public List<Clifor> listarClientesNaoCadastradosGalaxPay(String codigo) throws ClassNotFoundException, SQLException{
		CliforDAO daoClifor = new CliforDAO();
		return daoClifor.listarClientesNaoCadastrados(codigo);		
	}
	
	public void listarClientesSelecionados() {
		for (Clifor clifor : clientesNaoCadastrados) {
			System.out.println(clifor.getNom());
			
		}
	}
	

}
