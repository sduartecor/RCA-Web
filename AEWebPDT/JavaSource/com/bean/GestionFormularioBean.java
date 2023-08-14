package com.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;



import com.capa3Persistencia.exception.PersistenciaException;
import com.utils.ExceptionsTools;
import com.capa3Persistencia.DAO.FormularioEmpresaDAO;
import com.capa3Persistencia.entities.Ciudad.Ciudad;
import com.capa3Persistencia.entities.Formulario.Formulario;




@Named(value="gestionFormulario")		//JEE8
@SessionScoped				        //JEE8
public class GestionFormularioBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@EJB
	FormularioEmpresaDAO formDAO;
	

	GestionUsuarioBean usuarioBean;
	
	private Formulario formularioSeleccionado;
	
	private Long id;
	
	private String nombre;
	
	private Boolean activo;
		
	private List<Formulario> formulariosSeleccionados;
	
	private List<Formulario> formularios;

	
	private String[] fila;
	
	private boolean modoEdicion=false;
	
	private String modalidad;
	
	private boolean varNo2 =false;
	private	boolean varCo2 =false;
	private boolean varTemperatura =false;
	private boolean varPrecipitacion =false;
	private boolean varPm25 =false;
	private boolean varPm10 =false;
	
	
	
	private String no2;
	private	String co2;
	private String temperatura;
	private String precipitacion;
	private String pm25;
	private String pm10;
	
	
	public GestionFormularioBean() {
		super();
	}
	
	@PostConstruct
	public void init() {

		formularioSeleccionado = new Formulario();
		
	}
	
	

	//se ejecuta antes de desplegar la vista
	public void preRenderViewListener() {
		
		if (id!=null) {
			formularioSeleccionado = formDAO.buscarFormuario(id);
		} else {
			formularioSeleccionado=new Formulario();
		}
		if (modalidad.contentEquals("view")) {
			
Map<String, Boolean> camposSelec = formularioSeleccionado.getCampos();
			
			if (formularioSeleccionado.getCampos().get("no2")) {
				varNo2=true;
			} else {
				varNo2=false;
			}	if (formularioSeleccionado.getCampos().get("co2")) {
				varCo2=true;
			} else {
				varCo2=false;
			} if (formularioSeleccionado.getCampos().get("pm2,5")) {
				varPm25=true;
			} else {
				varPm25=false;
			}  if (formularioSeleccionado.getCampos().get("pm10")) {
				varPm10=true;
			} else {
				varPm10=false;
			} if (formularioSeleccionado.getCampos().get("temperatura")) {
				varTemperatura=true;
			} else {
				varTemperatura=false;
			}  if (formularioSeleccionado.getCampos().get("precipitacion")) {
				varPrecipitacion=true;
			} else {
				varPrecipitacion=false;
			}
			
			modoEdicion=false;
		}else if (modalidad.contentEquals("update")) {	
			//Cambio
			modoEdicion=true;
		}else if (modalidad.contentEquals("insert")) {
			//false
			varNo2=false;
			varCo2=false;
			varPm25=false;
			varPm10=false;
			varTemperatura=false;
			varPrecipitacion=false;
			
		
			
			//cambio
			modoEdicion=true;
		} else if (modalidad.contentEquals("remove")) {
			modoEdicion=false;
		} else {
			modoEdicion=false;
			modalidad="view";
			varNo2=false;
			varCo2=false;
			varPm25=false;
			varPm10=false;
			varTemperatura=false;
			varPrecipitacion=false;
	
		}
	}
	
	//acciones
		public String cambiarModalidadUpdate() throws CloneNotSupportedException {
			//this.modalidad="update";
			return "DatosFormulario?faces-redirect=true&includeViewParams=true";
			
		}
		//Pasar a modo 
	
		public String borrarFormulario() throws IOException {
			
			try {
				formDAO.borrarFormulario(formularioSeleccionado);
				

				FacesContext.getCurrentInstance().addMessage(null, 
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha borrado Formulario.",formularioSeleccionado.getNombre()));
				
				formulariosSeleccionados = formDAO.obtenerTodos();
				
				FacesContext.getCurrentInstance().getExternalContext().redirect("/AEWeb3/pages/formulario/Formularios.xhtml");
				
			} catch (PersistenciaException e) {

				Throwable rootException=ExceptionsTools.getCause(e);
				String msg1=e.getMessage();
				String msg2=ExceptionsTools.formatedMsg(e.getCause());
				//mensaje de actualizacion correcta
				FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR,msg1, msg2);
				FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			
				this.modalidad="remove";
				
			
				e.printStackTrace();
			} 
			
			return "";
		}
	
	
public String salvarCambios()  {
		
		if (formularioSeleccionado.getId()==null) {
		formularioSeleccionado.setActivo(true);
			
			Formulario formularioNuevo;
			
			Map<String, Boolean> campos = new HashMap<String, Boolean>();
			campos.put("co2", varCo2);
			campos.put("no2", varNo2);
			campos.put("pm2,5", varPm25);
			campos.put("pm10", varPm10);
			campos.put("temperatura", varTemperatura);
			campos.put("precipitacion", varPrecipitacion);
			
			formularioSeleccionado.setCampos(campos);
			
			formularioSeleccionado.setNombre(formularioSeleccionado.getNombre().toUpperCase());
			
		try {
			
			if (varCo2 == false && varNo2 == false && varPm25 == false && varPm10 == false && varTemperatura == false && varPrecipitacion == false ) {
				FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe seleccionar una casilla como minimo", null);
				FacesContext.getCurrentInstance().addMessage(null, facesMsg);
				
			} else {
			formularioNuevo = (Formulario) formDAO.crearDos(formularioSeleccionado);
			this.id=formularioNuevo.getId();
				
				
			
				//mensaje de actualizacion correcta
				FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha agregado un nuevo Formulario", formularioSeleccionado.getNombre());
				FacesContext.getCurrentInstance().addMessage(null, facesMsg);
				
				this.modalidad="view";
				
			}
			
			
				
			} catch (PersistenciaException e) {
				
				Throwable rootException=ExceptionsTools.getCause(e);
				String msg1=e.getMessage();
				String msg2=ExceptionsTools.formatedMsg(rootException);
				//mensaje de actualizacion correcta
				FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR,msg1, msg2);
				FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			
				this.modalidad="update";
				
				
				e.printStackTrace();
			}
		} else if (modalidad.equals("update")) {
			

			try {
				
				Map<String, Boolean> campos = new HashMap<String, Boolean>();
				campos.put("co2", varCo2);
				campos.put("no2", varNo2);
				campos.put("pm2,5", varPm25);
				campos.put("pm10", varPm10);
				campos.put("temperatura", varTemperatura);
				campos.put("precipitacion", varPrecipitacion);
				
				formularioSeleccionado.setCampos(campos);
				
				if (varCo2 == false && varNo2 == false && varPm25 == false && varPm10 == false && varTemperatura == false && varPrecipitacion == false ) {
					FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe seleccionar almenos una casilla como minimo", null);
					FacesContext.getCurrentInstance().addMessage(null, facesMsg);
					
				} else {
				
							
				formDAO.modificarFormulario(formularioSeleccionado);

				FacesContext.getCurrentInstance().addMessage(null, 
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha modificado Formulario.",formularioSeleccionado.getNombre()));
				
				this.modalidad="view";
				}
				
			} catch (PersistenciaException e) {

				Throwable rootException=ExceptionsTools.getCause(e);
				String msg1=e.getMessage();
				String msg2=ExceptionsTools.formatedMsg(e.getCause());
				//mensaje de actualizacion correcta
				FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR,msg1, msg2);
				FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			
				this.modalidad="update";
				
			
				e.printStackTrace();
			}
		 
		
		
		}
		return "";
		
}
			
	
	public String seleccionarFormularios() throws PersistenciaException {
		
		
		
		//formulariosSeleccionados = gestionFormularioService.seleccionarFormularios();
		
		formulariosSeleccionados = formDAO.obtenerTodos();
		
		
		return "";
	
	}
	

	
	
	
	
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isModoEdicion() {
		return modoEdicion;
	}

	public void setModoEdicion(boolean modoEdicion) {
		this.modoEdicion = modoEdicion;
	}

	public String getModalidad() {
		return modalidad;
	}

	public void setModalidad(String modalidad) {
		this.modalidad = modalidad;
	}

	public Formulario getFormularioSeleccionado() {
		return formularioSeleccionado;
	}

	public void setFormularioSeleccionado(Formulario formularioSeleccionado) {
		this.formularioSeleccionado = formularioSeleccionado;
	}

	public List<Formulario> getFormulariosSeleccionados() {
		return formulariosSeleccionados;
	}

	public void setFormulariosSeleccionados(List<Formulario> formulariosSeleccionados) {
		this.formulariosSeleccionados = formulariosSeleccionados;
	}

	public String[] getFila() {
		return fila;
	}

	public void setFila(String[] fila) {
		this.fila = fila;
	}

	public String getNo2() {
		return no2;
	}

	public void setNo2(String no2) {
		this.no2 = no2;
	}

	public String getCo2() {
		return co2;
	}

	public void setCo2(String co2) {
		this.co2 = co2;
	}

	public String getTemperatura() {
		return temperatura;
	}

	public void setTemperatura(String temperatura) {
		this.temperatura = temperatura;
	}

	public String getPrecipitacion() {
		return precipitacion;
	}

	public void setPrecipitacion(String precipitacion) {
		this.precipitacion = precipitacion;
	}

	public String getPm25() {
		return pm25;
	}

	public void setPm25(String pm25) {
		this.pm25 = pm25;
	}

	public String getPm10() {
		return pm10;
	}

	public void setPm10(String pm10) {
		this.pm10 = pm10;
	}

	public boolean isVarNo2() {
		return varNo2;
	}

	public void setVarNo2(boolean varNo2) {
		this.varNo2 = varNo2;
	}

	public boolean isVarCo2() {
		return varCo2;
	}

	public void setVarCo2(boolean varCo2) {
		this.varCo2 = varCo2;
	}

	public boolean isVarTemperatura() {
		return varTemperatura;
	}

	public void setVarTemperatura(boolean varTemperatura) {
		this.varTemperatura = varTemperatura;
	}

	public boolean isVarPrecipitacion() {
		return varPrecipitacion;
	}

	public void setVarPrecipitacion(boolean varPrecipitacion) {
		this.varPrecipitacion = varPrecipitacion;
	}

	public boolean isVarPm25() {
		return varPm25;
	}

	public void setVarPm25(boolean varPm25) {
		this.varPm25 = varPm25;
	}

	public boolean isVarPm10() {
		return varPm10;
	}

	public void setVarPm10(boolean varPm10) {
		this.varPm10 = varPm10;
	}
	

	



	
	
	
	

}
