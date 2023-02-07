package com.bolsaideas.springboot.app.controllers;

import java.awt.LinearGradientPaint;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bolsaideas.springboot.app.models.entity.Album;
import com.bolsaideas.springboot.app.models.entity.AlbumUsuario;
import com.bolsaideas.springboot.app.models.entity.Ficha;
import com.bolsaideas.springboot.app.models.entity.FichaAlbumUsuario;
import com.bolsaideas.springboot.app.models.entity.Usuario;
import com.bolsaideas.springboot.app.models.service.IAlbumService;
import com.bolsaideas.springboot.app.models.service.IUsuarioService;



@Controller
@RequestMapping("/album")
@SessionAttributes({"albumUsuario", "usuario2", "album"})
@Secured("ROLE_ADMIN")
public class AlbumController {
	
	@Autowired
	@Qualifier ("crudRepository")
	private IUsuarioService usuarioService;
	
	@Autowired
	private IAlbumService albumService;
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@GetMapping("/form/{usuarioId}")
	public String crear(@PathVariable(value = "usuarioId") Long usuarioId, 
			Model model, 
			RedirectAttributes flash) {
		
		Usuario usuario = usuarioService.findOne(usuarioId);
		List<Album> albumes = new ArrayList<Album>();
		if(usuario==null) {
			flash.addFlashAttribute("error", "El cliente no existe en la base de datos");
			return "redirect:/listar";
		}
		
		if(usuario.getAlbumesUsuario().size()==0) {
			flash.addFlashAttribute("error", "El usuario no tiene datos");
			return "redirect:/album/asociar/" + usuario.getId();
		}
		else{
			for (AlbumUsuario albumUsuario : usuario.getAlbumesUsuario()) {
				albumes.add(albumUsuario.getAlbum());
			}
		}
		Album album = new Album();
		
		model.addAttribute("albumesUsuario", albumes);
		model.addAttribute("album",album);
		model.addAttribute("usuario2", usuario);
		model.addAttribute("titulo", "Asociar Album");
		
		return "album/form";
	}
	
	@ModelAttribute("albums")
	public List<Album> albums(){
		return albumService.findAll();
	}

	//ResponseBody se usa para no retornar una vista sino datos que se pasan directamente al body
	@GetMapping(value="/cargar-fichas/{term}", produces = {"application/json"})
	public @ResponseBody List<Ficha> cargarFichas (@PathVariable String term){
		System.out.println("HOLA ENTRE " + albumService.findByNombre(term).size());
	
		return 	albumService.findByNombre(term);
	}
	
	
	@GetMapping("/asociar/{usuarioId}")
	public String asociarAlbum(@PathVariable(value = "usuarioId") Long usuarioId, 
			Model model, 
			RedirectAttributes flash) {
		
		Usuario usuario = usuarioService.findOne(usuarioId);
		if(usuario==null) {
			flash.addFlashAttribute("error", "El cliente no existe en la base de datos");
			return "redirect:/listar";
		}
		
		AlbumUsuario albumUsuario = new AlbumUsuario();
		albumUsuario.setNivel("Principiante");
		albumUsuario.setUsuario(usuario);
		
		model.addAttribute("albumUsuario", albumUsuario);
		model.addAttribute("usuario2", usuario);
		model.addAttribute("titulo", "Asociar Album");
		
		return "album/asociarAlbum";
	}
	
	@PostMapping(value = "/asociar/")
	public String guardarAlbum (@Valid @ModelAttribute("albumUsuario") AlbumUsuario albumUsuario, 
			BindingResult result, Model model, RedirectAttributes flash, SessionStatus status) {
		log.info("Albbum:" + albumUsuario.getAlbum());
		
		if(result.hasErrors()) {
			model.addAttribute("titulo", "Asociar Album");
			return "album/asociarAlbum";
			
		}
		
		Usuario usuario = usuarioService.findOne(albumUsuario.getUsuario().getId());
		List<AlbumUsuario> albumUsuarios = new ArrayList<AlbumUsuario>();
		albumUsuarios.add(albumUsuario);
		usuario.setAlbumesUsuario(albumUsuarios);
		
		
		usuarioService.save(usuario);
		flash.addFlashAttribute("success", "El album se ha asociado al usuario");
	
		return "redirect:/listar";
		
	}
	
	@PostMapping("/form")
	public String guardar( @Valid @ModelAttribute("usuario2") Usuario usuario, BindingResult result,
			Model model,
			@RequestParam(name= "ficha_id[]", required = false) Long[] fichaID,
			@RequestParam(name= "cantidad[]", required = false) Integer[] cantidad,
			RedirectAttributes flash,
			SessionStatus status) {
		
		if(fichaID==null || fichaID.length == 0) {
			model.addAttribute("titulo", "Asociar fichas");
			model.addAttribute("error", "El album debe tener fichas");
			return "album/form";
		}
		
		AlbumUsuario albumUsuario = new AlbumUsuario();
		Ficha ficha1 = albumService.findFichaById(fichaID[0]);
		if(ficha1 != null){
			log.info("ID Album: " + ficha1.getSeccion().getAlbum().getId() +", usuario: " + usuario.getId());
			albumUsuario = albumService.findAlbumUsuarioByAlbumUsuario(usuario.getId(), ficha1.getSeccion().getAlbum().getId());
		}
		for(int i=0; i<fichaID.length; i++) {
			Ficha ficha = albumService.findFichaById(fichaID[i]);
			
			FichaAlbumUsuario fichaAlbumUsuario = new FichaAlbumUsuario();
			fichaAlbumUsuario.setCreateAt(new Date());
			fichaAlbumUsuario.setFicha(ficha);
			fichaAlbumUsuario.setRepetidas(cantidad[i]-1);
			albumUsuario.getListaFichasAlbumPorUsuario().add(fichaAlbumUsuario);		
			
			log.info("ID: " + fichaID[i].toString() +", cantidad: " + cantidad[i].toString());
		}
		
		albumService.saveAlbumUsuario(albumUsuario);
		status.setComplete();
		
		flash.addFlashAttribute("success", "Fichas registradas con exito");		
		return "redirect:/ver/" + albumUsuario.getUsuario().getId();
	}
	
	@GetMapping("/ver/{idAlbumUsuario}")
	public String ver(@PathVariable(value = "idAlbumUsuario") Long idAlbumUsuario,
			Model model,
			RedirectAttributes flash) {
		
		System.out.println("Hola");
		AlbumUsuario albumUsuario =albumService.fetchByIdTodaInformacion(idAlbumUsuario);
		
		if(albumUsuario == null) {
			flash.addFlashAttribute("error", "El album no existe");
			return "redirect:/listar";
		}
		model.addAttribute("albumUsuario", albumUsuario);
		model.addAttribute("titulo", "Album: " + albumUsuario.getAlbum().getNombre());
		
		return "album/ver";
		
	}
	
	@GetMapping ("/eliminar/{id}")
	public String eliminar (@PathVariable(value="id") Long id,
			RedirectAttributes flash) {
		AlbumUsuario albumUsuario = albumService.findAlbumUsuarioById(id);
		if(albumUsuario != null) {
			albumService.deleteAlbumUsuario(id);
			flash.addAttribute("info", "Album eliminado con exito");
			return "redirect:/ver/" + albumUsuario.getUsuario().getId();
		}
		flash.addAttribute("error", "El album no existe y no se pudo eliminar");
		return "redirect:/listar";
	}
	
	@GetMapping ("/eliminarf/{id}")
	public String eliminarFicha (@PathVariable (value="id") Long id,
			RedirectAttributes flash) {
		FichaAlbumUsuario fichaAlbumUsuario = albumService.findFichaAlbumUsuarioById(id);
		if(fichaAlbumUsuario != null) {
			if(fichaAlbumUsuario.getRepetidas()==0){
				albumService.deleteFichaAlbumUsuario(id);
				flash.addAttribute("success", "Ficha Eliminada con exito");
				return "redirect:/listar";
				
			}
			else {
				fichaAlbumUsuario.setRepetidas(fichaAlbumUsuario.getRepetidas() - 1);
				albumService.modificarFichaAlbumUsuarioByID(fichaAlbumUsuario);
				flash.addAttribute("success", "Ficha Eliminada con exito");
				return "redirect:/listar";
			}
		}
		flash.addAttribute("error", "El album no existe y no se pudo eliminar");
		return "redirect:/listar";
		
	}
	
}
