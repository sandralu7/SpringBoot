package com.bolsaideas.springboot.app.view.csv;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.bolsaideas.springboot.app.models.entity.Usuario;


@Component ("listar")
public class UsuarioCSVView extends AbstractView {

	
	
	public UsuarioCSVView() {
		setContentType("text/csv");
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		response.setHeader("Content-Disposition", "attachment; filename=\"usuarios.csv\"");
		response.setContentType(getContentType());
		Page<Usuario> usuarios = (Page<Usuario>)model.get("usuarios");
		
		ICsvBeanWriter beanWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
		
		String [] header = {"id", "nombre", "apellido", "email", "createAt" };
		
		beanWriter.writeHeader(header);
		
		for(Usuario usuario : usuarios) {
			beanWriter.write(usuario, header);
		}
		
		beanWriter.close();
	}

	@Override
	protected boolean generatesDownloadContent() {
		// TODO Auto-generated method stub
		return true;
	}
	
	

}
