package com.bolsaideas.springboot.app.view.json;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.bolsaideas.springboot.app.models.entity.Usuario;

@Component("listar.json")
public class UsuarioListJsonView extends MappingJackson2JsonView{

	@Override
	protected Object filterModel(Map<String, Object> model) {
		
		model.remove("titulo");
		model.remove("page");
		Page<Usuario> usuarios = (Page<Usuario>) model.get("usuarios");
		model.remove("page");
		model.put("usuarios", usuarios.getContent());
		
		return super.filterModel(model);
	}

}
