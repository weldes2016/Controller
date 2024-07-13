package br.com.controller.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.com.controller.bean.UsuarioBean;
import br.com.controller.domain.Empresas;

@FacesConverter(value = "empresasConverter")
public class EmpresasConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value != null && !value.isEmpty()) {
			try {
				int codigo = Integer.parseInt(value);
				UsuarioBean usuarioBean = context.getApplication().evaluateExpressionGet(context, "#{MBUsuarios}",
						UsuarioBean.class);
				for (Empresas empresa : usuarioBean.getEmpresasDoUsuario()) {
					if (empresa.getId_empresa() == codigo) {
						return empresa;
					}
				}
			} catch (NumberFormatException e) {
				throw new ConverterException("Não foi possível converter para Empresa: " + value, e);
			}
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value instanceof Empresas) {
			Empresas empresa = (Empresas) value;
			return String.valueOf(empresa.getId_empresa());
		}
		return null;
	}
}
