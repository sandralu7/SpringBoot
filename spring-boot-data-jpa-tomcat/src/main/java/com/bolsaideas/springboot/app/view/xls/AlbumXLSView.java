package com.bolsaideas.springboot.app.view.xls;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.bolsaideas.springboot.app.models.entity.AlbumUsuario;
import com.bolsaideas.springboot.app.models.entity.FichaAlbumUsuario;

@Component ("album/ver.xlsx")
public class AlbumXLSView extends AbstractXlsxView{

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		AlbumUsuario albumUsuario = (AlbumUsuario) model.get("albumUsuario");
		Sheet sheet = workbook.createSheet("Album");
		
		Row row = sheet.createRow(1);
		Cell cell = row.createCell(0);
		
		cell.setCellValue("Datos del Usuario");
		row = sheet.createRow(2);
		cell = row.createCell(0);
		cell.setCellValue(albumUsuario.getUsuario().getNombre() + " " + albumUsuario.getUsuario().getApellido());
		
		row = sheet.createRow(3);
		cell = row.createCell(0);
		cell.setCellValue(albumUsuario.getUsuario().getEmail());
		
		sheet.createRow(5).createCell(0).setCellValue("Datos del Album");
		sheet.createRow(6).createCell(0).setCellValue("Nombre: " + albumUsuario.getAlbum().getNombre());
		sheet.createRow(7).createCell(0).setCellValue("Cantidad Fichas: " + albumUsuario.getAlbum().getCantidadFichas());
		sheet.createRow(8).createCell(0).setCellValue("Fecha: " + albumUsuario.getAlbum().getCreateAt());
		
		CellStyle theaderStyle = workbook.createCellStyle();
		theaderStyle.setBorderBottom(BorderStyle.MEDIUM);
		theaderStyle.setBorderTop(BorderStyle.MEDIUM);
		theaderStyle.setBorderRight(BorderStyle.MEDIUM);
		theaderStyle.setBorderLeft(BorderStyle.MEDIUM);
		theaderStyle.setFillForegroundColor(IndexedColors.GOLD.index);
		theaderStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		CellStyle tbodyStyle = workbook.createCellStyle();
		
		tbodyStyle.setBorderBottom(BorderStyle.THIN);
		tbodyStyle.setBorderTop(BorderStyle.THIN);
		tbodyStyle.setBorderRight(BorderStyle.THIN);
		tbodyStyle.setBorderLeft(BorderStyle.THIN);
		
		Row header = sheet.createRow(10);
		header.createCell(0).setCellValue("Ficha");
		header.createCell(1).setCellValue("Descripcion");
		header.createCell(2).setCellValue("Dificultad");
		header.createCell(3).setCellValue("Cantidad");
		header.createCell(4).setCellValue("Repetidas");
		
		header.getCell(0).setCellStyle(theaderStyle);
		header.getCell(1).setCellStyle(theaderStyle);
		header.getCell(2).setCellStyle(theaderStyle);
		header.getCell(3).setCellStyle(theaderStyle);
		header.getCell(4).setCellStyle(theaderStyle);
		
		int rownum = 11;
		for (FichaAlbumUsuario fichaAlbumUsuario : albumUsuario.getListaFichasAlbumPorUsuario()) {
			Row fila = sheet.createRow(rownum ++);
			cell = 	fila.createCell(0);
			cell.setCellValue(fichaAlbumUsuario.getFicha().getNombre());
			cell.setCellStyle(tbodyStyle);
		
			cell  = fila.createCell(1);
			cell.setCellValue(fichaAlbumUsuario.getFicha().getDescripcion());
			cell.setCellStyle(tbodyStyle);
			
			cell  = fila.createCell(2);
			cell.setCellValue(fichaAlbumUsuario.getFicha().getDificultad());
			cell.setCellStyle(tbodyStyle);
			
			cell  = fila.createCell(3);
			cell.setCellValue(fichaAlbumUsuario.calcularTotal().toString());
			cell.setCellStyle(tbodyStyle);
			
			
			cell  = fila.createCell(4);
			cell.setCellValue(fichaAlbumUsuario.getRepetidas().toString());
			cell.setCellStyle(tbodyStyle);
			
			
		}
		
		
		Row filaTotal = sheet.createRow(rownum);
		filaTotal.createCell(3).setCellValue("Gran total:  ");
		filaTotal.createCell(4).setCellValue(albumUsuario.getTotalLlenado().toString());
		
	}

}
