package com.bean;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Paths;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.Part;

import com.capa2LogicaNegocio.GestionEstacionService;
import com.capa2LogicaNegocio.GestionRegistroService;
import com.capa2LogicaNegocio.GestionUsuarioService;
import com.capa3Persistencia.entities.Usuario.Usuario;
import com.capa3Persistencia.exception.PersistenciaException;
import com.utils.ExceptionsTools;


import com.capa3Persistencia.DAO.CiudadEmpresaDAO;
import com.capa3Persistencia.DAO.DepartamentoEmpresaDAO;
import com.capa3Persistencia.DAO.FormularioEmpresaDAO;
import com.capa3Persistencia.DAO.RegistroEmpresaDAO;
import com.capa3Persistencia.entities.Registro;
import com.capa3Persistencia.entities.Ciudad.Ciudad;
import com.capa3Persistencia.entities.Departamento.Departamento;
import com.capa3Persistencia.entities.Estacion.Estacion;
import com.capa3Persistencia.entities.Formulario.Formulario;



@Named(value="gestionRegistro")		//JEE8
@SessionScoped				        //JEE8
public class GestionRegistroCA implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EJB
	GestionEstacionService gestionEstacionService;
	
	@EJB
	GestionUsuarioService gestionUsuarioService;
	
	@EJB
	FormularioEmpresaDAO gestionFormularioService;
	
	@EJB
	RegistroEmpresaDAO gestionRegistroService;
	
	@EJB
	GestionRegistroService gestionRegistro;
	
	@EJB
	CiudadEmpresaDAO ciudadEmpresaDAO;
	
	@EJB
	DepartamentoEmpresaDAO departamentoEmpresaDAO;
	
	GestionUsuarioBean gestionUsuarioBean;
	


	private Part archivoSubido;
	
	private Registro registroSeleccionado;
	
	private Estacion estacionSeleccionado;
	
	private Long id;
	
	private String ciudadNombre;
	
	private String estacionNombre;
	
	private String formularioNombre;
	
	
	private java.util.Date fechaRegistro;
	
	//
	private String tipoFiltro;
	
	private boolean filtroUbicacion;
	private boolean filtroFecha;
	private boolean filtroUsuario;
	private boolean filtroTodos = true;
	
	
	//Datos campos
	private Double datoNo2;
	private Double datoCo2;
	private Double datoTemperatura;
	private Double datoPrecipitacion;
	private Double datoPm25;
	private Double datoPm10;
	

	
	private Formulario formulario;
	
	private List<Estacion> estacionesSeleccionadas;
	
	private List<Ciudad> ciudades;
	
	private List<Registro> registrosSeleccionados;
	
	private String ciudadRegistro;
	
	private String departamentoRegistro;
	
	private java.util.Date fechaDesde;
	
	private java.util.Date fechaHasta;
	
	private boolean varNo2 =false;
	private	boolean varCo2 =false;
	private boolean varTemperatura =false;
	private boolean varPrecipitacion =false;
	private boolean varPm25 =false;
	private boolean varPm10 =false;
	
	private boolean modoEdicion=false;
	private boolean edicionForm= false;
	private boolean edicionEstacion = false;
	private boolean edicionFecha = false;
	
	private String modalidad;
	
	public GestionRegistroCA() {
		super();
	}
	
	@PostConstruct
	public void init() {

		registroSeleccionado = new Registro();
		

		
	}
	
	

	//se ejecuta antes de desplegar la vista
	public void preRenderViewListener() {
		
		if (id!=null) {
			registroSeleccionado = gestionRegistroService.encontrarRegistro(id);
		} else {
			registroSeleccionado=new Registro();
			
		}
		if (modalidad.contentEquals("view")) {
			//
			formularioNombre =registroSeleccionado.getFormulario().getNombre();
			estacionNombre = registroSeleccionado.getEstacion().getNombre();
			//
			varNo2=false;
			varCo2=false;
			varPm25=false;
			varPm10=false;
			varTemperatura=false;
			varPrecipitacion=false;
			//
			Map<String, Double> camposSelec = registroSeleccionado.getCampos();
			
			if (camposSelec.get("no2")!=null) {
				datoNo2=camposSelec.get("no2");
			} else {
				datoNo2=0.0;
			}	if (camposSelec.get("co2")!=null) {
				datoCo2=camposSelec.get("co2");
			} else {
				datoCo2=0.0;
			} if (camposSelec.get("pm2,5")!=null) {
				datoPm25=camposSelec.get("pm2,5");
			} else {
				datoPm25=0.0;
			}  if (camposSelec.get("pm10")!=null) {
				datoPm10=camposSelec.get("pm10");
			} else {
				datoPm10=0.0;
			} if (camposSelec.get("temperatura")!=null) {
				datoTemperatura=camposSelec.get("temperatura");
			} else {
				datoTemperatura=0.0;
			}  if (camposSelec.get("precipitacion")!=null) {
				datoPrecipitacion=camposSelec.get("precipitacion");
			} else {
				datoPrecipitacion=0.0;
			}
					
			//cambio
			modoEdicion=false;
			edicionForm=false;
			edicionEstacion=false;
			edicionFecha=false;
		}else if (modalidad.contentEquals("update")) {
			//
			Map<String, Double> camposSelec = registroSeleccionado.getCampos();
			
			if (camposSelec.get("no2")!=null) {
				varNo2=true;
			} else {
				varNo2=false;
			}	if (camposSelec.get("co2")!=null) {
				varCo2=true;
			} else {
				varCo2=false;
			} if (camposSelec.get("pm2,5")!=null) {
				varPm25=true;
			} else {
				varPm25=false;
			}  if (camposSelec.get("pm10")!=null) {
				varPm10=true;
			} else {
				varPm10=false;
			} if (camposSelec.get("temperatura")!=null) {
				varTemperatura=true;
			} else {
				varTemperatura=false;
			}  if (camposSelec.get("precipitacion")!=null) {
				varPrecipitacion=true;
			} else {
				varPrecipitacion=false;
			}
			//
		
	
			
			
			modoEdicion=true;
			edicionForm=false;
			edicionEstacion=false;
			edicionFecha=false;
		}else if (modalidad.contentEquals("insert")) {
			//Datos reset
			varNo2=false;
			varCo2=false;
			varPm25=false;
			varPm10=false;
			varTemperatura=false;
			varPrecipitacion=false;
			//
			datoNo2= null;
			datoCo2= null;
			datoPm25= null;
			datoPm10= null;
			datoTemperatura= null;
			datoPrecipitacion= null;
			
			//nombres
			formularioNombre=null;
			ciudadNombre=null;
			estacionNombre=null;
			//
				
			viewCasillas();
			//
		
		
			
			edicionEstacion=true;
			edicionFecha=true;
			modoEdicion=true;
			edicionForm=true;
		} else if (modalidad.contentEquals("remove")) {
			modoEdicion=false;
			edicionForm=false;
			edicionEstacion=false;
			edicionFecha=false;
		} else {
			modoEdicion=false;
			edicionForm=false;
			edicionEstacion=false;
			edicionFecha=false;
			modalidad="view";
			//
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
			return "DatosRegsitro?faces-redirect=true&includeViewParams=true";
			
		}
		//Pasar a modo 
	
	
public String borrarRegistro() throws IOException {
		
		try {
			gestionRegistroService.borrarRegistro(registroSeleccionado);
			

			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha borrado el registro.",null));
			
			registrosSeleccionados = gestionRegistroService.obtenerTodos();
			
			FacesContext.getCurrentInstance().getExternalContext().redirect("/AEWeb3/pages/registroca/Registros.xhtml");
			
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
	
	
public String salvarCambios() throws PersistenciaException {
		
		if (registroSeleccionado.getId()==null) {
		registroSeleccionado.setActivo(true);
			
		//Registro
			Registro registroNuevo;
			
		//Entidades
			List<Formulario> listaFormulario = gestionFormularioService.obtenerTodos();
			List<Estacion> listaEstacion = gestionEstacionService.seleccionarEstaciones();
			
			//Seteo formulario
			for (Formulario f : listaFormulario) {
				if(f.getNombre().equals(formularioNombre)) {
					registroSeleccionado.setFormulario(f);
					break;
					}
				}
			
			//Seteo estacion
			for (Estacion e : listaEstacion) {
				if(e.getNombre().equals(estacionNombre)) {
					registroSeleccionado.setEstacion(e);
					registroSeleccionado.setCiudad(e.getCiudad());
					break;
					}
				}
			
			
			
			
			//Agrego datos al Formulario

			Map<String, Double> campos = new HashMap<String, Double>();
			if (registroSeleccionado.getFormulario().getCampos().get("no2")) {
				campos.put("no2", datoNo2);
			} 
			
			if (registroSeleccionado.getFormulario().getCampos().get("co2")) {
				
				campos.put("co2", datoCo2);
			} 
			
			if (registroSeleccionado.getFormulario().getCampos().get("pm2,5")) {
				campos.put("pm2,5", datoPm25);
			} 
			
			if (registroSeleccionado.getFormulario().getCampos().get("pm10")) {
				campos.put("pm10", datoPm10);
			} 
			
			if (registroSeleccionado.getFormulario().getCampos().get("temperatura")) {
				campos.put("temperatura", datoTemperatura);
			} 
			
			if (registroSeleccionado.getFormulario().getCampos().get("precipitacion")) {
				campos.put("precipitacion", datoPrecipitacion);
			} 
			
			registroSeleccionado.setCampos(campos);
			
			registroSeleccionado.setUsuario(gestionUsuarioService.getNicknameUser());
		
		
			
			try {
				
				System.out.println(registroSeleccionado.getFecha());
				registroNuevo = (Registro) gestionRegistroService.crear(registroSeleccionado); //agregarUsuario(usuarioSeleccionado);
				this.id=registroNuevo.getId();
			
			
				//mensaje de actualizacion correcta
				FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha agregado un nuevo Registro", null);
				FacesContext.getCurrentInstance().addMessage(null, facesMsg);
				
				this.modalidad="view";
				
			
				
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
			
	
			
			
			List<Estacion> listaEstacion = gestionEstacionService.seleccionarEstaciones();
			
		
			
			//Seteo estacion
			for (Estacion e : listaEstacion) {
				if(e.getNombre().equals(estacionNombre)) {
					registroSeleccionado.setEstacion(e);
					registroSeleccionado.setCiudad(e.getCiudad());
					break;
					}
				}
			
			
			
			try {
				
			
				
				
				Map<String, Double> campos = new HashMap<String, Double>();
				
				if (registroSeleccionado.getFormulario().getCampos().get("no2")) {
					campos.put("no2", datoNo2);
				} 
				
				if (registroSeleccionado.getFormulario().getCampos().get("co2")) {
					
					campos.put("co2", datoCo2);
				} 
				
				if (registroSeleccionado.getFormulario().getCampos().get("pm2,5")) {
					campos.put("pm2,5", datoPm25);
				} 
				
				if (registroSeleccionado.getFormulario().getCampos().get("pm10")) {
					campos.put("pm10", datoPm10);
				} 
				
				if (registroSeleccionado.getFormulario().getCampos().get("temperatura")) {
					campos.put("temperatura", datoTemperatura);
				} 
				
				if (registroSeleccionado.getFormulario().getCampos().get("precipitacion")) {
					campos.put("precipitacion", datoPrecipitacion);
				} 
				
				registroSeleccionado.setCampos(campos);		
				
							
				gestionRegistroService.actualizar(registroSeleccionado);
				
				FacesContext.getCurrentInstance().addMessage(null, 
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha modificado el Registro.", null));
				
				this.modalidad="view";
				
				
				
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
			
	
	
	public List<Ciudad> listaCiudades() {
		List<Ciudad> listaCiudades = new ArrayList<>();
		List<Ciudad> listaC = ciudadEmpresaDAO.ciudadDeparamento(departamentoRegistro);
		
		for (Ciudad ciudad : listaC) {
			listaCiudades.add(ciudad);
		}
		
		return listaCiudades;
		
	}
	
	public List<Estacion> listaEstaciones() throws PersistenciaException{
		List<Estacion> listaEstaciones = new ArrayList<>();
		List<Estacion> listaE = gestionEstacionService.seleccionarEstaciones();
		
		for (Estacion estacion : listaE) {
			listaEstaciones.add(estacion);
		}
		
		return listaEstaciones;
	}
	
	public List<Formulario> listaFormularios() throws PersistenciaException{
		List<Formulario> listaFormularios = new ArrayList<>();
		List<Formulario> listaF = gestionFormularioService.obtenerTodos();
		
		for (Formulario formulario : listaF) {
			listaFormularios.add(formulario);
		}
		
		return listaFormularios;
	}
	
	
	
	public void viewCasillas() {
		
		List<Formulario> listaForm = gestionFormularioService.obtenerTodos();
		
		try {
			
		for (Formulario form : listaForm) {
			if (form.getNombre().equals(formularioNombre)) {
				formulario = form;
				break;
			}
		}
		
		if (formulario!=null) {
		
		Map<String, Boolean> camposSelec = formulario.getCampos();
		
		if (camposSelec.get("no2")) {
			varNo2=true;
		} else {
			varNo2=false;
		}	if (camposSelec.get("co2")) {
			varCo2=true;
		} else {
			varCo2=false;
		} if (camposSelec.get("pm2,5")) {
			varPm25=true;
		} else {
			varPm25=false;
		}  if (camposSelec.get("pm10")) {
			varPm10=true;
		} else {
			varPm10=false;
		} if (camposSelec.get("temperatura")) {
			varTemperatura=true;
		} else {
			varTemperatura=false;
		}  if (camposSelec.get("precipitacion")) {
			varPrecipitacion=true;
		} else {
			varPrecipitacion=false;
		}
		
		}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	
	public String listaRegistros() {
		registrosSeleccionados = gestionRegistroService.obtenerTodos();
			
		return "";
	}
	
	public String listaRegistroUsuario() {
		registrosSeleccionados = gestionRegistroService.obtenerRegistrosUsuario(gestionUsuarioService.getNicknameUser());
		
		return "";
	}
	 
public void seleccionarFiltros() {
		
		
		if (tipoFiltro.equals("ubicacion")) {
			this.filtroUbicacion = true;
			this.filtroFecha = false;
			this.filtroUsuario = false;
			this.filtroTodos = false;
			
		} else if (tipoFiltro.equals("fechas")) {
			this.filtroUbicacion = false;
			this.filtroFecha = true;
			this.filtroUsuario = false;
			this.filtroTodos = false;
		} else if (tipoFiltro.equals("usuario")) {
			this.filtroUbicacion = false;
			this.filtroFecha = false;
			this.filtroUsuario = true;
			this.filtroTodos = false;
		} else if (tipoFiltro.equals("todos")) {
			this.filtroUbicacion = false;
			this.filtroFecha = false;
			this.filtroUsuario = false;
			this.filtroTodos = true;
		}
	
	}
	
	
	public String filtrarRegistros() throws ParseException {
		
		if (tipoFiltro.equals("ubicacion")) {
			
			if (departamentoRegistro != null && ciudadRegistro == null ) {
				
				registrosSeleccionados = gestionRegistroService.encontrarRegistroDepartamento(departamentoRegistro);
			
		
	} else if (departamentoRegistro != null && ciudadRegistro != null ) {
		
				registrosSeleccionados = gestionRegistroService.encontrarRegistroCiudad(ciudadRegistro);
				
			
}
			
		}
		//
		else if (tipoFiltro.equals("fechas")) {
			
			if (fechaDesde != null && fechaHasta != null ) {
				
				 java.sql.Date fechaDesdeNew = new java.sql.Date(fechaDesde.getTime()); 
				 java.sql.Date fechaHastaNew = new java.sql.Date(fechaHasta.getTime()); 
				 
				 if (fechaHastaNew.before(fechaDesdeNew)) {
						FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "FECHA DESDE debe ser anterior a FECHA HASTA.", null);
						FacesContext.getCurrentInstance().addMessage(null, facesMsg);
				 } else {

				registrosSeleccionados = gestionRegistroService.obtenerRegistrosFiltrados(fechaDesdeNew, fechaHastaNew);
				
			}
			
		}
			
		}  else if (tipoFiltro.equals("usuario")) {
		registrosSeleccionados = gestionRegistroService.obtenerRegistrosUsuario(gestionUsuarioService.getNicknameUser());
		
		} else if (tipoFiltro.equals("todos")) {
			registrosSeleccionados = gestionRegistroService.obtenerTodos();
		}
		
		return "";
	}
	
	public List<Departamento> listaDepartamentos() {
		List<Departamento> listaDepartamentos = new ArrayList<>();
		List<Departamento> listaD = departamentoEmpresaDAO.obtenerTodos();
		
		for (Departamento d : listaD) {
			listaDepartamentos.add(d);
		}
		
		return listaDepartamentos;
		
	}
	
	protected String getFileName(Part p){

		String GUIDwithext = Paths.get(p.getSubmittedFileName()).getFileName().toString();

		String GUID = GUIDwithext.substring(0, GUIDwithext.lastIndexOf('.'));

		return GUID;
		    }
	
	public void importarExcel()  {
		try {
			
			if (archivoSubido == null) {
	            FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, "ADVERTENCIA: No se ha seleccionado ningún archivo", null);
	            FacesContext.getCurrentInstance().addMessage(null, facesMsg);
	            return;
	        }
			
			archivoSubido.write("C:\\data\\" + getFileName(archivoSubido));
			gestionRegistro.importarDatos();
			registrosSeleccionados = gestionRegistroService.obtenerTodos();
		} catch (Exception e) {
			File directoryPath = new File("C:\\data");
			File filesList[] = directoryPath.listFiles();
			File archivoFinal = filesList[0];
			archivoFinal.delete();
			//
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR: El archivo no es compatible", null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			
		}
	}
	
	
	
	public Registro getRegistroSeleccionado() {
		return registroSeleccionado;
	}

	public void setRegistroSeleccionado(Registro registroSeleccionado) {
		this.registroSeleccionado = registroSeleccionado;
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

	public String getCiudadNombre() {
		return ciudadNombre;
	}

	public void setCiudadNombre(String ciudadNombre) {
		this.ciudadNombre = ciudadNombre;
	}

	public String getEstacionNombre() {
		return estacionNombre;
	}

	public void setEstacionNombre(String estacionNombre) {
		this.estacionNombre = estacionNombre;
	}
	
	public String getFormularioNombre() {
		return formularioNombre;
	}

	public void setFormularioNombre(String formularioNombre) {
		this.formularioNombre = formularioNombre;
	}
	
	public List<Registro> getRegistrosSeleccionados() {
		return registrosSeleccionados;
	}

	public void setRegistrosSeleccionados(List<Registro> registrosSeleccionados) {
		this.registrosSeleccionados = registrosSeleccionados;
	}

	

	//Casillas
	
	

	

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
//Datos
	

	public java.util.Date getFechaRegistro() {
		return fechaRegistro;
	}

	public Double getDatoNo2() {
		return datoNo2;
	}

	public void setDatoNo2(Double datoNo2) {
		this.datoNo2 = datoNo2;
	}

	public Double getDatoCo2() {
		return datoCo2;
	}

	public void setDatoCo2(Double datoCo2) {
		this.datoCo2 = datoCo2;
	}

	public Double getDatoTemperatura() {
		return datoTemperatura;
	}

	public void setDatoTemperatura(Double datoTemperatura) {
		this.datoTemperatura = datoTemperatura;
	}

	public Double getDatoPrecipitacion() {
		return datoPrecipitacion;
	}

	public void setDatoPrecipitacion(Double datoPrecipitacion) {
		this.datoPrecipitacion = datoPrecipitacion;
	}

	public Double getDatoPm25() {
		return datoPm25;
	}

	public void setDatoPm25(Double datoPm25) {
		this.datoPm25 = datoPm25;
	}

	public Double getDatoPm10() {
		return datoPm10;
	}

	public void setDatoPm10(Double datoPm10) {
		this.datoPm10 = datoPm10;
	}

	public void setFechaRegistro(java.util.Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public boolean isEdicionForm() {
		return edicionForm;
	}

	public void setEdicionForm(boolean edicionForm) {
		this.edicionForm = edicionForm;
	}

	public String getCiudadRegistro() {
		return ciudadRegistro;
	}

	public void setCiudadRegistro(String ciudadRegistro) {
		this.ciudadRegistro = ciudadRegistro;
	}

	public String getDepartamentoRegistro() {
		return departamentoRegistro;
	}

	public void setDepartamentoRegistro(String departamentoRegistro) {
		this.departamentoRegistro = departamentoRegistro;
	}

	public java.util.Date getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(java.util.Date fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	public java.util.Date getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(java.util.Date fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

	public String getTipoFiltro() {
		return tipoFiltro;
	}

	public void setTipoFiltro(String tipoFiltro) {
		this.tipoFiltro = tipoFiltro;
	}

	public boolean isFiltroUbicacion() {
		return filtroUbicacion;
	}

	public void setFiltroUbicacion(boolean filtroUbicacion) {
		this.filtroUbicacion = filtroUbicacion;
	}

	public boolean isFiltroFecha() {
		return filtroFecha;
	}

	public void setFiltroFecha(boolean filtroFecha) {
		this.filtroFecha = filtroFecha;
	}

	public boolean isFiltroUsuario() {
		return filtroUsuario;
	}

	public void setFiltroUsuario(boolean filtroUsuario) {
		this.filtroUsuario = filtroUsuario;
	}

	public boolean isFiltroTodos() {
		return filtroTodos;
	}

	public void setFiltroTodos(boolean filtroTodos) {
		this.filtroTodos = filtroTodos;
	}

	public Part getArchivoSubido() {
		return archivoSubido;
	}

	public void setArchivoSubido(Part archivoSubido) {
		this.archivoSubido = archivoSubido;
	}

	public boolean isEdicionEstacion() {
		return edicionEstacion;
	}

	public void setEdicionEstacion(boolean edicionEstacion) {
		this.edicionEstacion = edicionEstacion;
	}

	public boolean isEdicionFecha() {
		return edicionFecha;
	}

	public void setEdicionFecha(boolean edicionFecha) {
		this.edicionFecha = edicionFecha;
	}


	
	
	

//
	
	

	
	
	
	
	
	

	
	
	
	
	
	
	
	
	

}
