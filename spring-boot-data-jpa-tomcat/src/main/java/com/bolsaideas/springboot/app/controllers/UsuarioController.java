package com.bolsaideas.springboot.app.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bolsaideas.springboot.app.models.dao.IUsuarioDAO;
import com.bolsaideas.springboot.app.models.entity.Usuario;
import com.bolsaideas.springboot.app.models.service.IUsuarioService;
import com.bolsaideas.springboot.app.util.paginator.PageRender;
import com.bolsaideas.springboot.app.view.xml.UsuarioList;

@Controller
@SessionAttributes ("usuario")
public class UsuarioController {
	
	@Autowired
	@Qualifier("crudRepository")
	private IUsuarioService usuarioService;
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private MessageSource messageSource;
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping(value = "/ver/{id}")
	public String ver(@PathVariable (value="id") Long id, Model model, RedirectAttributes flash) {
		
		Usuario usuario = usuarioService.fetchByIdTodaInformacion(id);
		if(usuario==null) {
			flash.addAttribute("error", "El cliente no existe en la base de datos");
			return ("redirect :/listar");
			
		}
		model.addAttribute("usuario", usuario);
		model.addAttribute("titulo", "Detalle usuario: "+ usuario.getNombre());
		return "ver";
	}
	
	//Metodo que responde en formato Json
	@GetMapping(value = "/listar-rest")
	public @ResponseBody UsuarioList listarRest() {
		
		return new UsuarioList(usuarioService.findAll());
	}

	@RequestMapping(value = {"/","/listar"}, method = RequestMethod.GET)
	public String listar(@RequestParam(name="page", defaultValue = "0") int page, Model model,
				Authentication authentication,
				HttpServletRequest request,
				Locale locale) {
		if(authentication != null) {
			logger.info("Hola usuario autenticado, tu username es: " + authentication.getName());
		}
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if(auth != null) {
			logger.info("Utilizando la forma estatica. Hola usuario autenticado, tu username es: " + auth.getName());
		}
		
		if(hasRole("ROLE_ADMIN")) {
			logger.info("Hola ".concat(auth.getName()).concat(" tienes acceso"));
		}else {
			logger.info("Hola ".concat(auth.getName()).concat(" NO tienes acceso"));
		}
		
		//otra forma
		SecurityContextHolderAwareRequestWrapper securityContext = new SecurityContextHolderAwareRequestWrapper(request, "ROLE_");
		
		if(securityContext.isUserInRole("ADMIN")) {
			logger.info("Hola forma usando SecurityContextHolderAwareRequestWrapper ".concat(auth.getName()).concat(" tienes acceso"));
		}
		else {
			logger.info("Hola forma usando SecurityContextHolderAwareRequestWrapper ".concat(auth.getName()).concat(" NO tienes acceso"));
		}
		
		//otra forma con request
		if(request.isUserInRole("ROLE_ADMIN")) {
			logger.info("Hola forma usando SecurityContextHolderAwareRequestWrapper ".concat(auth.getName()).concat(" tienes acceso"));
		}
		else {
			logger.info("Hola forma usando SecurityContextHolderAwareRequestWrapper ".concat(auth.getName()).concat(" NO tienes acceso"));
		}
		
		
		Pageable pageRequest = PageRequest.of(page, 4);
		Page<Usuario> usuarios = usuarioService.findAll(pageRequest);
		
		PageRender<Usuario> pageRender = new PageRender<>("/listar", usuarios);
		model.addAttribute("titulo", messageSource.getMessage("text.usuario.listar", null, locale));
		model.addAttribute("usuarios", usuarios);
		model.addAttribute("page", pageRender);
		return "listar";
		
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping(value = "/form")
	public String crear(Model model) {
		
		Usuario usuario = new Usuario();
		model.addAttribute("usuario", usuario);
		model.addAttribute("titulo","Formulario de Usuario");
		
		return "form";
	}
	
	
	@Secured("ROLE_ADMIN")
	//No olvidar que Binding result va junto al objeto que se va a validar
	@RequestMapping(value = "/form", method = RequestMethod.POST)
	public String guardar(@Valid @ModelAttribute("usuario") Usuario usuario, BindingResult result, @RequestParam("file") MultipartFile foto, Model model, RedirectAttributes flash, SessionStatus status) {
		if(result.hasErrors()) {
			model.addAttribute("titulo", "Formulario del usuario");
			return "form";
		}
		if(!foto.isEmpty()) {
			
			if(usuario.getId()!=null
					&& usuario.getId()> 0 
					&& usuario.getFoto() !=null
					&& usuario.getFoto().length()>0) {
				String rootPath = "/opt/upload";
				Path rutaCompleta = Paths.get(rootPath + "//" + usuario.getFoto());
				File archivo = rutaCompleta.toFile();
				
				if(archivo.exists() && archivo.canRead()) {
					if(archivo.delete()) {
						flash.addAttribute("info", "Foto eliminada con exito");
					}
				}
			}
		
			String rootPath = "/opt/upload";
			try {
				byte [] bytes = foto.getBytes();
				Path rutaCompleta = Paths.get(rootPath + "//" + foto.getOriginalFilename());
				Files.write(rutaCompleta, bytes);
				flash.addAttribute("info", "Se ha subido correctamente '" + foto.getOriginalFilename() + "'");
				usuario.setFoto(foto.getOriginalFilename());
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		String mensajeFlash = (usuario.getId() !=null ) ? "Usuario Editado con exito":"Usuario creado con Exito!";
		
		usuarioService.save(usuario);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:listar";
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value="/form/{id}")
	public String editar (@PathVariable(value="id")Long id, Map<String, Object> model, RedirectAttributes flash) {
		
		Usuario usuario= null;
		if(id>0) {
			usuario = usuarioService.findOne(id);
			if(usuario==null) {
				flash.addFlashAttribute("error", "El cliente no puede ser nulo");
				return "redirect:/listar";
			}
			
		}else {
			flash.addFlashAttribute("error", "El id del cliente no puede ser 0");
			return "redirect:/listar";
		}
		model.put("usuario", usuario);
		model.put("titulo", "Editar Usuario");
		return "form";
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value="/eliminar/{id}")
	public String eliminar(@PathVariable(value="id")Long id, RedirectAttributes flash) {
		if(id>0) {
			
			Usuario usuario = usuarioService.findOne(id);
			usuarioService.delete(id);
			flash.addFlashAttribute("success", "Usuario Eliminado con exito");
			
			String rootPath = "/opt/upload";
			Path rutaCompleta = Paths.get(rootPath + "//" + usuario.getFoto());
			File archivo = rutaCompleta.toFile();
			
			if(archivo.exists() && archivo.canRead()) {
				if(archivo.delete()) {
					flash.addAttribute("info", "Foto eliminada con exito");
				}
			}
		}	
		
			return "redirect:/listar";
	}
	
	private boolean hasRole(String role) {
		SecurityContext context = SecurityContextHolder.getContext();
		
		if(context==null) {
			return false;
		}
		
		Authentication auth = context.getAuthentication();
		if(auth==null) {
			
			return false;
		}
		
		Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
		
		return authorities.contains(new SimpleGrantedAuthority(role));
		
		/*for(GrantedAuthority authority: authorities) {
			if(role.equals(authority.getAuthority())) {
				return true;
			}
		}
		
		return false;*/
	}
	}
