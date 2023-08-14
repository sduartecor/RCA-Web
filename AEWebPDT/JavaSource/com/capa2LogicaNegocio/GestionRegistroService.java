package com.capa2LogicaNegocio;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.capa3Persistencia.DAO.FormularioEmpresaDAO;
import com.capa3Persistencia.DAO.RegistroEmpresaDAO;
import com.capa3Persistencia.entities.Registro;
import com.capa3Persistencia.entities.Estacion.Estacion;
import com.capa3Persistencia.entities.Formulario.Formulario;
import com.capa3Persistencia.exception.PersistenciaException;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

@Stateless
@LocalBean
public class GestionRegistroService implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	
	@EJB
	RegistroEmpresaDAO registroDao;
	
	@EJB
	GestionEstacionService gestionEstacionService;
	
	@EJB
	GestionUsuarioService gestionUsuarioService;
	
	@EJB
	FormularioEmpresaDAO gestionFormularioService;
	
	
	public boolean importarDatos() throws IOException, PersistenciaException  {
		File directoryPath = new File("C:\\data");
		File filesList[] = directoryPath.listFiles();
		File archivoFinal = filesList[0];
		
		FileInputStream fis = new FileInputStream(archivoFinal);
		HSSFWorkbook wb = new HSSFWorkbook(fis);
		HSSFSheet sheet = wb.getSheetAt(0);
		//
		//
		List<Estacion> listaEstacion = gestionEstacionService.seleccionarEstaciones();
		List<Formulario> listaFormulario = gestionFormularioService.obtenerTodos();
		//
		try {
			for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
				Registro registro = new Registro();
				
				registro.setActivo(true);
				registro.setMetodo(sheet.getRow(i).getCell(0).getStringCellValue());

		
	            
				Date fecha = Date.valueOf(sheet.getRow(i).getCell(1).getStringCellValue());
				registro.setFecha(fecha);
				//
				for (Estacion e : listaEstacion) {
					if(e.getNombre().equals(sheet.getRow(i).getCell(2).getStringCellValue())) {
						registro.setEstacion(e);
						registro.setCiudad(e.getCiudad());
						break;
						}
					}
				//
				for (Formulario f : listaFormulario) {
					if(f.getNombre().equals(sheet.getRow(i).getCell(3).getStringCellValue())) {
						registro.setFormulario(f);
						break;
						}
					}
				//
				String no2 = sheet.getRow(i).getCell(4).getStringCellValue();
				String co2 = sheet.getRow(i).getCell(5).getStringCellValue();
				String pm25 = sheet.getRow(i).getCell(6).getStringCellValue();
				String pm10 = sheet.getRow(i).getCell(7).getStringCellValue();
				String temp = sheet.getRow(i).getCell(8).getStringCellValue();
				String prep = sheet.getRow(i).getCell(9).getStringCellValue();
				//
				Map<String, Double> campos = new HashMap<String, Double>();
				if (!no2.isEmpty()) {
					campos.put("no2", Double.parseDouble(sheet.getRow(i).getCell(4).getStringCellValue()));
				}
				if (!co2.isEmpty()) {
					campos.put("co2", Double.parseDouble(sheet.getRow(i).getCell(5).getStringCellValue()));
				}
				if (!pm25.isEmpty()) {
					campos.put("pm2,5", Double.parseDouble(sheet.getRow(i).getCell(6).getStringCellValue()));
				}
				if (!pm10.isEmpty()) {
					campos.put("pm10", Double.parseDouble(sheet.getRow(i).getCell(7).getStringCellValue()));
				}
				if (!temp.isEmpty()) {
					campos.put("temperatura", Double.parseDouble(sheet.getRow(i).getCell(8).getStringCellValue()));
				}
				if (!prep.isEmpty()) {
					campos.put("precipitacion", Double.parseDouble(sheet.getRow(i).getCell(9).getStringCellValue()));
				}
				
				registro.setCampos(campos);
				registro.setUsuario(sheet.getRow(i).getCell(10).getStringCellValue());
				
				registroDao.crear(registro);
				
			}
			
			archivoFinal.delete();
			

			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Los datos se cargaron con exito", null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			
			return true;
		} catch (Exception e) {
			
				FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR: El archivo no es compatible", null);
				FacesContext.getCurrentInstance().addMessage(null, facesMsg);
				
				archivoFinal.delete();
				
				
		
			
		}
		
		return false;
	}

}
