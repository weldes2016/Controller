package br.com.controller.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.controller.domain.Clifor;


@Named
@FacesConverter(value = "themeConverter")
public class CliforConverter implements Converter {

	@Inject
	private Clifor clifor;

	@Override
	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
		if (value != null && value.trim().length() > 0) {
			try {
				return clifor.getNom();
			} catch (NumberFormatException e) {
				throw new ConverterException();
			}
		} else {
			return null;
		}
	}

	@Override
	public String getAsString(FacesContext fc, UIComponent uic, Object object) {
		if (object != null) {
			return String.valueOf(clifor.getCod());
		} else {
			return null;
		}
	}
}
