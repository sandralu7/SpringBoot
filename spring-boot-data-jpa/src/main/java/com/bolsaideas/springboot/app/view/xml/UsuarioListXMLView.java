package com.bolsaideas.springboot.app.view.xml;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.xml.MarshallingView;

import com.bolsaideas.springboot.app.models.entity.Usuario;

//El view result es el component
@Component ("listar.xml")
public class UsuarioListXMLView extends MarshallingView{

	
	//inyectamos el bean del MvcConfig
	@Autowired
	public UsuarioListXMLView(Jaxb2Marshaller marshaller) {
		super(marshaller);
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		//Remover los atributos que no aplican y que estan dentro del listar de UsuarioController
		model.remove("titulo");
		model.remove("page");
		
		Page<Usuario> usuarios = (Page<Usuario>) model.get("usuarios");
		
		
		model.remove("usuarios");
		//se obtendrian los clientes por pagina
		model.put("usuarioList", new UsuarioList(usuarios.getContent()));
		super.renderMergedOutputModel(model, request, response);
	}

	
	
}
