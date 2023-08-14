package com.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import com.capa2LogicaNegocio.GestionEstacionService;
import com.capa2LogicaNegocio.GestionUsuarioService;
import com.capa3Persistencia.entities.Usuario.Usuario;
import com.capa3Persistencia.exception.PersistenciaException;
import com.utils.ExceptionsTools;

import org.primefaces.event.map.PointSelectEvent;
import org.primefaces.model.map.LatLng;

import com.capa3Persistencia.DAO.CiudadEmpresaDAO;
import com.capa3Persistencia.DAO.DepartamentoEmpresaDAO;
import com.capa3Persistencia.entities.Ciudad.Ciudad;
import com.capa3Persistencia.entities.Departamento.Departamento;
import com.capa3Persistencia.entities.Estacion.Estacion;



@Named(value="gestionEstacion")		//JEE8
@SessionScoped				        //JEE8
public class GestionEstacionBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EJB
	GestionEstacionService gestionEstacionService;
	
	@EJB
	GestionUsuarioService gestionUsuarioService;
	
	@EJB
	CiudadEmpresaDAO ciudadEmpresaDAO;
	
	@EJB
	DepartamentoEmpresaDAO departamentoEmpresaDAO;
	
	private Double longitud;
	private Double latitud;
	
	private Usuario usuarioSeleccionado;
	
	private Estacion estacionSeleccionado;
	
	private Long id;
	
	private String ciudadEstacion;
	
	private String departamentoEstacion;

	private List<Estacion> estacionesSeleccionadas;
	
	private List<Ciudad> ciudades;
	
	private boolean edicionMap=false;
	
	private boolean modoEdicion=false;
	
	private String modalidad;
	
	public GestionEstacionBean() {
		super();
	}
	
	@PostConstruct
	public void init() {

		estacionSeleccionado = new Estacion();
		
	}
	
	

	//se ejecuta antes de desplegar la vista
	public void preRenderViewListener() {
		
		if (id!=null) {
			estacionSeleccionado=gestionEstacionService.buscarEstacion(id);
		} else {
			estacionSeleccionado=new Estacion();	
		}
		if (modalidad.contentEquals("view")) {
			ciudadEstacion = estacionSeleccionado.getCiudad().getNombre();
			departamentoEstacion = estacionSeleccionado.getDepartamento().getNombre();
			//
			latitud = estacionSeleccionado.getLatitud();
			longitud = estacionSeleccionado.getLongitud();
			edicionMap=false;
			modoEdicion=false;
		}else if (modalidad.contentEquals("update")) {
			edicionMap=false;
			modoEdicion=true;
		}else if (modalidad.contentEquals("insert")) {
			
			edicionMap=true;
			modoEdicion=true;
		} else if (modalidad.contentEquals("remove")) {
			modoEdicion=false;
			edicionMap=false;
		} else {
			edicionMap=false;
			modoEdicion=false;
			modalidad="view";
	
		}
	}
	
	//acciones
		public String cambiarModalidadUpdate() throws CloneNotSupportedException {
			//this.modalidad="update";
			return "DatosEstacion?faces-redirect=true&includeViewParams=true";
			
		}
		//Pasar a modo 
	
	
public String borrarEstacion() throws IOException {
		
		try {
			gestionEstacionService.borrarEstacion(estacionSeleccionado); 
			

			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha borrado Estacion.",estacionSeleccionado.getNombre()));
			
			estacionesSeleccionadas=gestionEstacionService.seleccionarEstaciones();
			
			FacesContext.getCurrentInstance().getExternalContext().redirect("/AEWeb3/pages/estacion/Estaciones.xhtml");
			
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
	
	
public String salvarCambios() {
		
		if (estacionSeleccionado.getId()==null) {
		estacionSeleccionado.setActivo(true);
			
			Estacion estacionNuevo;
			
			ciudades = ciudadEmpresaDAO.obtenerTodos();
			
			try {
						
				for (Ciudad c : ciudades) {
					if(c.getNombre().equals(ciudadEstacion)) {
						estacionSeleccionado.setCiudad(c);
						estacionSeleccionado.setDepartamento(c.getDepartamento());
						break;
						}
					}
//				
			estacionSeleccionado.setLatitud(latitud);
			estacionSeleccionado.setLongitud(longitud);
				
			estacionNuevo = (Estacion) gestionEstacionService.agregarEstacion(estacionSeleccionado);  //agregarUsuario(usuarioSeleccionado);
			this.id=estacionNuevo.getId();
				
				
			
				//mensaje de actualizacion correcta
				FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha agregado una nueva Estacion", estacionSeleccionado.getNombre());
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
			
			ciudades = ciudadEmpresaDAO.obtenerTodos();
			
			try {
				
				for (Ciudad c : ciudades) {
					if(c.getNombre().equals(ciudadEstacion)) {
						estacionSeleccionado.setCiudad(c);
						estacionSeleccionado.setDepartamento(c.getDepartamento());
						break;
						}
					}
				
				estacionSeleccionado.setLatitud(latitud);
				estacionSeleccionado.setLongitud(longitud);
							
				gestionEstacionService.actualizarEstacion(estacionSeleccionado);

				FacesContext.getCurrentInstance().addMessage(null, 
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha modificado Estacion.",estacionSeleccionado.getNombre()));
				
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
	List<Ciudad> listaC = ciudadEmpresaDAO.ciudadDeparamento(departamentoEstacion);
	
	for (Ciudad ciudad : listaC) {
		listaCiudades.add(ciudad);
	}
	
	return listaCiudades;
	
}



public List<Departamento> listaDepartamentos() {
	List<Departamento> listaDepartamentos = new ArrayList<>();
	List<Departamento> listaD = departamentoEmpresaDAO.obtenerTodos();
	
	for (Departamento d : listaD) {
		listaDepartamentos.add(d);
	}
	
	return listaDepartamentos;
	
}
			
	
	public String seleccionarEstaciones() throws PersistenciaException {
		estacionesSeleccionadas=gestionEstacionService.seleccionarEstaciones();
	
		return "";
	}
	
	public void onPointSelect(PointSelectEvent event) {
		 try {
	        LatLng latlng = event.getLatLng();
	        latitud = latlng.getLat();
	        longitud = latlng.getLng();
	        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Ubicación seleccionada", "Lat:" + latlng.getLat() + ", Lng:" + latlng.getLng());
	        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		 } catch (Exception e) {
			 e.printStackTrace();
		 }
		 
	    }
	
	
	
	
	
	
	
	public List<Estacion> getEstacionesSeleccionadas() {
		return estacionesSeleccionadas;
	}
	public void setEstacionesSeleccionadas(List<Estacion> estacionesSeleccionadas) {
		this.estacionesSeleccionadas = estacionesSeleccionadas;
	}

	public Estacion getEstacionSeleccionado() {
		return estacionSeleccionado;
	}

	public void setEstacionSeleccionado(Estacion estacionSeleccionado) {
		this.estacionSeleccionado = estacionSeleccionado;
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
	

	public Double getLongitud() {
		return longitud;
	}

	public void setLongitud(Double longitud) {
		this.longitud = longitud;
	}

	public Double getLatitud() {
		return latitud;
	}

	public void setLatitud(Double latitud) {
		this.latitud = latitud;
	}

	public String getCiudadEstacion() {
		return ciudadEstacion;
	}

	public void setCiudadEstacion(String ciudadEstacion) {
		this.ciudadEstacion = ciudadEstacion;
	}

	public String getDepartamentoEstacion() {
		return departamentoEstacion;
	}

	public void setDepartamentoEstacion(String departamentoEstacion) {
		this.departamentoEstacion = departamentoEstacion;
	}

	public boolean isEdicionMap() {
		return edicionMap;
	}

	public void setEdicionMap(boolean edicionMap) {
		this.edicionMap = edicionMap;
	}


	
	
	

}
