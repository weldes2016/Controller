package br.com.controller.bean;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.primefaces.extensions.component.gchart.model.GChartModel;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.line.LineChartDataSet;
import org.primefaces.model.charts.line.LineChartModel;
import org.primefaces.model.charts.line.LineChartOptions;
import org.primefaces.model.charts.optionconfig.title.Title;

import br.com.controller.dao.EmpresasDAO;
import br.com.controller.dao.FaturamentoDAO;
import br.com.controller.domain.Empresas;
import br.com.controller.domain.Fatser;
import br.com.controller.util.ArquivoUtil;

@Named
@RequestScoped
public class GChartController implements Serializable {
	private static final long serialVersionUID = 253762400419864192L;
	private FaturamentoDAO dao = new FaturamentoDAO();

	private GChartModel chartModel = null;

	private LineChartModel cartesianLinerModel;

	private EmpresasDAO daoEmpresas = new EmpresasDAO();

	private Empresas empresa = new Empresas();
	private List<Empresas> empresas = new ArrayList<>();
	private ArquivoUtil arquivoUtil = new ArquivoUtil();

	public GChartModel getChart() {
		return chartModel;
	}

	@PostConstruct
	public void generateModel() {

		createCartesianLinerModel();

		final Map<String, Object> colorAxis = new HashMap<String, Object>();

		colorAxis.put("colors", new String[] { "yellow", "red" });
		// List<Fatser> fatserTotal = new ArrayList<Fatser>();

		List<Fatser> fat9999 = dao.mostrarFaturamentoPorEstadoAnoCorrente("9999");

		for (Fatser fatser : fat9999) {
			if (fatser.getStringField().contains("PA")) {
				System.out.println(
						fatser.getDoubleField() + " String: " + fatser.getStringField() + " Empresa 9999 - GO");
			}
		}

		List<Fatser> fat9000 = dao.mostrarFaturamentoPorEstadoAnoCorrente("9000");

		for (Fatser fatser : fat9000) {

			if (fatser.getStringField().contains("PA")) {

			}
			// fatserTotal.add(fatser);
		}
	}

	public void createCartesianLinerModel() {
		cartesianLinerModel = new LineChartModel();
		ChartData data = new ChartData();
		List<String> labels = new ArrayList<>();

		try {
			empresas = daoEmpresas.listarEmpresasPorUsuario(arquivoUtil.obterUsuarioDaSessao().getCodigo());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Map<String, LineChartDataSet> dataSetMap = new HashMap<>();

		for (Empresas empresa : empresas) {
			String codigoEmpresa = empresa.getUsu(); // Suponhamos que existe um método getUsu() em Empresas

			List<Object> values = new ArrayList<>();
			List<Fatser> fatsers = null;

			try {
				try {
					fatsers = dao.totalFaturamentoCincoAnos(codigoEmpresa);
				} catch (org.json.simple.parser.ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
				System.err.println("Erro ao obter dados para a empresa " + empresa.getNom() + ": " + e.getMessage());
			}

			for (Fatser fatser : fatsers) {
				values.add(fatser.getDoubleField());
			}

			LineChartDataSet dataSet = new LineChartDataSet();
			dataSet.setData(values);
			dataSet.setLabel(empresa.getNom());
			dataSet.setYaxisID("left-y-axis");
			dataSet.setFill(true);
			dataSet.setTension(0.5);

			dataSetMap.put(codigoEmpresa, dataSet);
		}

		for (LineChartDataSet dataSet : dataSetMap.values()) {
			data.addChartDataSet(dataSet);
		}

		// Labels não dependem das empresas, então mova isso para fora do loop
		if (!empresas.isEmpty()) {
			List<Fatser> fatsers = null;
			try {
				try {
					fatsers = dao.totalFaturamentoCincoAnos(empresas.get(0).getUsu());
				} catch (org.json.simple.parser.ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace(); // Trate as exceções de acordo com suas necessidades
			}
			for (Fatser fatser : fatsers) {
				labels.add(fatser.getStringField());
			}
		}

		data.setLabels(labels);
		cartesianLinerModel.setData(data);

		LineChartOptions options = new LineChartOptions();
		CartesianScales cScales = new CartesianScales();

		for (int i = 1; i <= dataSetMap.size(); i++) {
			CartesianLinearAxes linearAxes = new CartesianLinearAxes();
			linearAxes.setId("left-y-axis-" + i);
			linearAxes.setPosition("left");
			cScales.addYAxesData(linearAxes);
		}

		options.setScales(cScales);

		Title title = new Title();
		title.setDisplay(true);
		title.setText("Faturamento do Grupo dos últimos 5 anos");
		options.setTitle(title);

		cartesianLinerModel.setOptions(options);
	}

	public static void main(String[] args) {
		GChartController teste = new GChartController();
		teste.generateModel();
	}

	public LineChartModel getCartesianLinerModel() {
		return cartesianLinerModel;
	}

	public void setCartesianLinerModel(LineChartModel cartesianLinerModel) {
		this.cartesianLinerModel = cartesianLinerModel;
	}

	public Empresas getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresas empresa) {
		this.empresa = empresa;
	}
}
