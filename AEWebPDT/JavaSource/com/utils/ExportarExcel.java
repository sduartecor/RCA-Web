package com.utils;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import com.capa3Persistencia.DAO.RegistroEmpresaDAO;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;

@Named(value = "exportarExcel")
@ViewScoped
public class ExportarExcel implements Serializable {
	

    /**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public void postProcessXLS(Object document) {
		//
        HSSFWorkbook wb = (HSSFWorkbook) document;
        HSSFSheet sheet = wb.getSheetAt(0);
        CellStyle style = wb.createCellStyle();
        style.setFillBackgroundColor(IndexedColors.AQUA.getIndex());
        for (Row row : sheet) {
            for (Cell cell : row) {
                switch (cell.getCellType()) {
                case STRING:
                    cell.setCellValue(cell.getStringCellValue().toUpperCase());
                    cell.setCellStyle(style);
                    break;
                }
            }
        }
    }
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}	
}

