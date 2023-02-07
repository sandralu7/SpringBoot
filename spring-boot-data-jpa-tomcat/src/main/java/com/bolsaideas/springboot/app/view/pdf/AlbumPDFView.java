package com.bolsaideas.springboot.app.view.pdf;

import java.awt.Color;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.bolsaideas.springboot.app.models.entity.Album;
import com.bolsaideas.springboot.app.models.entity.AlbumUsuario;
import com.bolsaideas.springboot.app.models.entity.FichaAlbumUsuario;
import com.lowagie.text.Document;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Component ("album/ver")
public class AlbumPDFView extends AbstractPdfView {

	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private LocaleResolver localeResolver;
	
	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		AlbumUsuario albumUsuario = (AlbumUsuario) model.get("albumUsuario");
		Locale locale = localeResolver.resolveLocale(request);
		
		MessageSourceAccessor mensajes = getMessageSourceAccessor();
		
		PdfPTable tabla = new PdfPTable(1);
		tabla.setSpacingAfter(20);
		
		PdfPCell cell = null;
		cell = new PdfPCell(new Phrase(messageSource.getMessage("text.usuario.datos", null, locale)));
		cell.setBackgroundColor(new Color(184, 218, 255));
		cell.setPadding(8f);
		
		tabla.addCell(cell);
		tabla.addCell(albumUsuario.getUsuario().getNombre() + " " + albumUsuario.getUsuario().getApellido());
		tabla.addCell(albumUsuario.getUsuario().getEmail());
		
		
		PdfPTable tabla2 = new PdfPTable(1);
		tabla2.setSpacingAfter(20);
		
		cell = new PdfPCell(new Phrase(messageSource.getMessage("text.album.datos", null, locale)));
		cell.setBackgroundColor(new Color(195, 230, 203));
		cell.setPadding(8f);
		
		tabla.addCell(cell);
		tabla.addCell(mensajes.getMessage("text.album.nombre") + ": " + albumUsuario.getAlbum().getNombre());
		tabla.addCell(mensajes.getMessage("text.album.cantidadFichas") + ": " + albumUsuario.getAlbum().getCantidadFichas());
		tabla.addCell(mensajes.getMessage("text.album.createAt") + ": " +  albumUsuario.getAlbum().getCreateAt());
		
		document.add(tabla);
		document.add(tabla2);
		
		PdfPTable tabla3 = new PdfPTable(5);
		//tamañ de las columnas
		tabla3.setWidths(new float [] {2.5f, 1, 1, 1});
		
		tabla3.addCell(mensajes.getMessage("text.ficha.nombre") + ": ");
		tabla3.addCell(mensajes.getMessage("text.ficha.descripcion") + ": ");
		tabla3.addCell(mensajes.getMessage("text.ficha.dificultad") + ": ");
		tabla3.addCell(mensajes.getMessage("text.ficha.cantidad") + ": ");
		tabla3.addCell(mensajes.getMessage("text.ficha.repetidas") + ": ");
		
		for (FichaAlbumUsuario fichaAlbumUsuario : albumUsuario.getListaFichasAlbumPorUsuario()) {
			tabla3.addCell(fichaAlbumUsuario.getFicha().getNombre());
			tabla3.addCell(fichaAlbumUsuario.getFicha().getDescripcion());
			tabla3.addCell(fichaAlbumUsuario.getFicha().getDificultad());
			
			cell = new PdfPCell (new Phrase(fichaAlbumUsuario.calcularTotal().toString()));
			cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			
			tabla3.addCell(cell);
			
			cell = new PdfPCell (new Phrase(fichaAlbumUsuario.getRepetidas().toString()));
			cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			
			tabla3.addCell(cell);
		}
		
		cell = new PdfPCell(new Phrase());
		cell.setColspan(5);
		cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		tabla3.addCell(cell);
		tabla3.addCell(albumUsuario.getTotalLlenado().toString());
		
		document.add(tabla3);
		
	}

}
