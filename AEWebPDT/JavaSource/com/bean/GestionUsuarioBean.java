package com.bean;

import javax.annotation.PostConstruct;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.ejb.EJB;

import java.io.IOException;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.NamingException;
import javax.persistence.Enumerated;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;

import com.capa2LogicaNegocio.GestionUsuarioService;
import com.capa3Persistencia.DAO.CiudadEmpresaDAO;
import com.capa3Persistencia.DAO.DepartamentoEmpresaDAO;
import com.capa3Persistencia.DAO.UsuariosEmpresaDAO;
import com.capa3Persistencia.entities.Ciudad.Ciudad;
import com.capa3Persistencia.entities.Departamento.Departamento;
import com.capa3Persistencia.entities.Rol;
import com.capa3Persistencia.entities.Usuario.Usuario;
import com.capa3Persistencia.entities.Usuario.UsuarioEmpresa;
import com.capa3Persistencia.exception.PersistenciaException;
import com.filter.ValidationCedula;
import com.filter.cedulaValidatorFront;
import com.ldap.ConexionLDAP;
import com.utils.ExceptionsTools;
import com.utils.Util;

import javax.enterprise.context.SessionScoped;	//JEE8
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

//import javax.faces.bean.ManagedBean;
//import javax.faces.bean.SessionScoped;


//@ManagedBean(name="usuario")

@Named(value="gestionUsuario")		//JEE8
@SessionScoped				        //JEE8
public class GestionUsuarioBean implements Serializable{
	
	/**
	 * 
	 */


	//@Inject
	//PersistenciaBean persistenciaBean;
	@EJB
	GestionUsuarioService gestionUsuarioService;
	
	@EJB
	UsuariosEmpresaDAO usuariosPersistenciaDAO;
	
	@EJB
	CiudadEmpresaDAO ciudadEmpresaDAO;
	
	@EJB
	DepartamentoEmpresaDAO departamentoEmpresaDAO;
	
	
    cedulaValidatorFront verificacion = new cedulaValidatorFront();

	
	private Long id;
	
	private boolean autenticado = false;

	private String modalidad;

	private String ciudadUsuario;
	
	private String departamentoUsuario;
	
	private Usuario usuarioIngresado;
	
	private Usuario usuarioLDAP;
	
	private Usuario usuarioIngresadoLDAP;
	
	private Boolean datosExtra = false;
	
	private Usuario usuarioSeleccionado;
	
	private Boolean varLDAP = false;
	
	private Boolean edicionClave = false;
	
	private Usuario usuarioMod;

	
	@Enumerated
	private Rol admin = Rol.Administrador;
	
	@Enumerated
	private Rol inv= Rol.Investigador;
	
	@Enumerated
	private Rol afic= Rol.Aficionado;
	
	HashMap<String, String> datosUsuario = new HashMap<String, String>();
	
	private String criterioNombre;
	private String criterioRol;
	private Boolean criterioActivo;
	
	private List<Usuario> usuariosSeleccionados;
	
	private List<Ciudad> ciudades;
	
	private List<Departamento> departamentos;
	
	private boolean modoEdicion=false;
	
	private String claveNueva;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public GestionUsuarioBean() {
		super();
	}
	@PostConstruct
	public void init() {

		usuarioIngresado = new Usuario();
		
		usuarioLDAP = new Usuario();
		

		
	}
	
	

	//se ejecuta antes de desplegar la vista
	public void preRenderViewListener() {
		
		if (id!=null) {
			usuarioSeleccionado=gestionUsuarioService.buscarUsuario(id);
			usuarioMod = gestionUsuarioService.buscarUsuario(id);
			gestionUsuarioService.setIdUser(id);
			
			if (usuarioSeleccionado.getRol().equals(admin) || usuarioSeleccionado.getRol().equals(inv)) {
				datosExtra = true;
			} else {
				datosExtra = false;
			}
			
		
		} else {
			usuarioSeleccionado=new Usuario();
		}
		if (modalidad.contentEquals("view")) {
			if (usuarioSeleccionado.getRol().equals(admin) || usuarioSeleccionado.getRol().equals(inv)) {
			ciudadUsuario = usuarioSeleccionado.getCiudad().getNombre();
			departamentoUsuario = usuarioSeleccionado.getCiudad().getDepartamento().getNombre();
			
			}
		
			
			edicionClave=false;
			modoEdicion=false;
		}else if (modalidad.contentEquals("update")) {
			edicionClave=false;
			modoEdicion=true;
		}else if (modalidad.contentEquals("insert")) {
			edicionClave=true;
			modoEdicion=true;
		} else if (modalidad.contentEquals("remove")) {
			edicionClave=false;
			modoEdicion=false;
		} else {
			edicionClave=false;
			modoEdicion=false;
			modalidad="view";
	
		}
	}
	//acciones
	public String cambiarModalidadUpdate() throws CloneNotSupportedException {
		//this.modalidad="update";
		return "DatosUsuario?faces-redirect=true&includeViewParams=true";
		
	}
	//Pasar a modo 
	
public String borrarUsuario() throws IOException {
		
		try {
			gestionUsuarioService.borrarUsuario(usuarioSeleccionado);
			

			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha borrado Usuario.",usuarioSeleccionado.getNombre()));
			
			usuariosSeleccionados=gestionUsuarioService.seleccionarUsuarios(criterioNombre, criterioRol, criterioActivo);
			
			FacesContext.getCurrentInstance().getExternalContext().redirect("/AEWeb3/pages/usuario/Usuarios.xhtml");
			
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

	
	public String salvarCambios() throws NoSuchAlgorithmException {
		
		if (usuarioSeleccionado.getId()==null) {
			usuarioSeleccionado.setActivo(true);
			
			Usuario usuarioNuevo;			
			
			ciudades = ciudadEmpresaDAO.obtenerTodos();
			
			
			try {
				
				
				
				for (Ciudad c : ciudades) {
					if(c.getNombre().equals(ciudadUsuario)) {
						usuarioSeleccionado.setCiudad(c);
						break;
						}
					}
				
				if (usuarioSeleccionado.getRol().equals(afic)) {
					usuarioSeleccionado.setCiudad(null);
					usuarioSeleccionado.setDocumento(null);
					usuarioSeleccionado.setTelefono(null);
				}
				
				
				verificacion.setMno(usuarioSeleccionado.getDocumento());
				
				
				
				usuarioSeleccionado.setClave(gestionUsuarioService.encryptString(usuarioSeleccionado.getClave()));
				
				if (usuariosPersistenciaDAO.verificarNicknameExistente(usuarioSeleccionado.getNickname())) {
					FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "El usuario ya existe en el sistema", usuarioSeleccionado.getNickname());
					FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			
				} else if (usuariosPersistenciaDAO.verificarCorreoExistente(usuarioSeleccionado.getMail())) {
					FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "El correo electronico ya existe en el sistema", usuarioSeleccionado.getMail());
					FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			
				} else if (usuariosPersistenciaDAO.verificarCedulaExistente(usuarioSeleccionado.getDocumento())) {
					FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "La cedula ya existe en el sistema", usuarioSeleccionado.getDocumento());
					FacesContext.getCurrentInstance().addMessage(null, facesMsg);
					
				} else {
				
					
				usuarioNuevo = (Usuario) gestionUsuarioService.agregarUsuario(usuarioSeleccionado);
				this.id=usuarioNuevo.getId();
				
				//mensaje de actualizacion correcta
				FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha agregado un nuevo Usuario", usuarioSeleccionado.getNombre());
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
			
			ciudades = ciudadEmpresaDAO.obtenerTodos();
			
			try {
					
				verificacion.setMno(usuarioSeleccionado.getDocumento());
				
				for (Ciudad c : ciudades) {
					if(c.getNombre().equals(ciudadUsuario)) {
						usuarioSeleccionado.setCiudad(c);
						break;
						}
					}
				
				if (usuarioSeleccionado.getRol().equals(afic)) {
					usuarioSeleccionado.setCiudad(null);
					usuarioSeleccionado.setDocumento(null);
					usuarioSeleccionado.setTelefono(null);
				}
				
				
		
				if (usuariosPersistenciaDAO.verificarNicknameExistente(usuarioSeleccionado.getNickname()) && !usuarioMod.getNickname().equals(usuarioSeleccionado.getNickname())) {
						FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "El usuario ya existe en el sistema", usuarioSeleccionado.getNickname());
						FacesContext.getCurrentInstance().addMessage(null, facesMsg);
							System.out.println(usuarioMod.getNickname().toString() == usuarioSeleccionado.getNickname().toString());
				} else if (usuariosPersistenciaDAO.verificarCorreoExistente(usuarioSeleccionado.getMail()) && !usuarioMod.getMail().equals(usuarioSeleccionado.getMail())) {
					FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "El correo electronico ya existe en el sistema", usuarioSeleccionado.getMail());
					FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			
				} else if (usuariosPersistenciaDAO.verificarCedulaExistente(usuarioSeleccionado.getDocumento()) && !usuarioMod.getDocumento().equals(usuarioSeleccionado.getDocumento())) {
					FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "La cedula ya existe en el sistema", usuarioSeleccionado.getDocumento());
					FacesContext.getCurrentInstance().addMessage(null, facesMsg);
					
				}  else {
				
				gestionUsuarioService.actualizarEmpleado(usuarioSeleccionado);

				FacesContext.getCurrentInstance().addMessage(null, 
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha modificado Usuario.",usuarioSeleccionado.getNombre()));
				
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
	

	
	
	public String verificarLogin() throws NoSuchAlgorithmException {
				
		if (varLDAP.equals(true)) {
		
		this.datosUsuario.clear();
		 FacesContext facesContext = FacesContext.getCurrentInstance();
				// tomo los datos ingresados por el usuario
		ConexionLDAP.setLdapCorreoUsuario(usuarioLDAP.getNickname());
		ConexionLDAP.setLdapPasswordUsuario(usuarioLDAP.getClave());
				
				try {
					
					this.datosUsuario = ConexionLDAP.autenticar();
					// Si lo logro, guardo los datos
					if(datosUsuario.get("usuario") != null) {
					
						this.autenticado = true;
						HttpSession hs = Util.getSession();
						gestionUsuarioService.setNicknameUser(usuarioLDAP.getNickname());
						
						// segun el grupo que viene de AD, seteo el rol
						if(datosUsuario.get("grupo").equals("AppAdministradores")){
							usuarioLDAP.setRol(Rol.Administrador);
						} else if (datosUsuario.get("grupo").equals("AppInvestigadores")) {
							usuarioLDAP.setRol(Rol.Investigador);
						}
						
						hs.setAttribute("gestionUsuario", usuarioLDAP);
						
						return "index";
					// si falla autenticacion contra AD, sigo el viejo esquema de autenticacion
					} else {
						  facesContext.addMessage(null, new FacesMessage("Usuario o contraseña incorrecto"));
					}
					
				} catch (NamingException e1) {
					
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
				
		} else if (varLDAP.equals(false)) {
			
			 FacesContext facesContext = FacesContext.getCurrentInstance();
			
			try {
				this.autenticado = gestionUsuarioService.verificarLogin(usuarioIngresado);
				
				
				if (autenticado == true) {
					
					gestionUsuarioService.setNicknameUser(usuarioIngresado.getNickname());
					//
					HttpSession hs = Util.getSession();
					hs.setAttribute("gestionUsuario", usuarioIngresado);
					
					return "index";			
					
				} else {
					  facesContext.addMessage(null, new FacesMessage("Usuario o contraseña incorrecto"));
					 
				}
				
				
			} catch (PersistenciaException e) {
				// TODO Auto-generated catch block
			e.printStackTrace();
			}
			
		}

		
			return "";
			
		}
	
	
		
	
	public Usuario obtenerUser(){
		Usuario userIngresado=null;
		try {
		
		
		usuariosSeleccionados=gestionUsuarioService.seleccionarUsuarios();
		
		for (Usuario u : usuariosSeleccionados ) {
			if (u.getId().equals(idUsuario())) {
				usuarioIngresado = u;
				break;
			}
		}
		} catch  (Exception e) {
			e.printStackTrace();
		}
		
		return userIngresado;
	}
	
	


	
	public String rolUsuario() throws PersistenciaException, NoSuchAlgorithmException {
		String rol = null;
		
		if (varLDAP.equals(true)) {
			
			//	rol = gestionUsuarioService.obtenerRolUsuario(usuarioLDAP);
			rol = usuarioLDAP.getRol().toString();
			
		} else if(varLDAP.equals(false)) {
		
		try {
			rol = gestionUsuarioService.obtenerRolUsuario(usuarioIngresado);
			
		} catch (PersistenciaException e){
			e.printStackTrace();
		}
		
		}
		return rol;
	}
	

	public Long idUsuario() throws PersistenciaException {
		Long id = null;
		if (varLDAP.equals(true)) {
			
			id = usuarioLDAP.getId();
			
		} else if(varLDAP.equals(false)) {
				
		try {
			id = gestionUsuarioService.obtenerId(usuarioIngresado);
		} catch (PersistenciaException e) {
			e.printStackTrace();
		}
		
		}
	
	return id;
	}

	public String modificarContraseña() throws PersistenciaException, NoSuchAlgorithmException, IOException {
		try {
		
		usuarioSeleccionado.setClave(gestionUsuarioService.encryptString(claveNueva));
		
		claveNueva= "";
		
		gestionUsuarioService.actualizarEmpleado(usuarioSeleccionado);
		
		usuariosSeleccionados=gestionUsuarioService.seleccionarUsuarios(criterioNombre, criterioRol, criterioActivo);
		
		
		FacesContext.getCurrentInstance().getExternalContext().redirect("/AEWeb3/pages/usuario/Usuarios.xhtml");
	
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "";
		
	}

	public void cerrarSesion() throws IOException {
		this.datosUsuario.clear();
		
		HttpSession hs = Util.getSession();
		hs.invalidate();
		FacesContext.getCurrentInstance().getExternalContext().redirect("/AEWeb3/login.xhtml");
		
		
		
	}
	

	public void cargarDatos() throws IOException, PersistenciaException {
		
		if (gestionUsuarioService.seleccionarUsuarios().isEmpty()) {
					
		FacesContext.getCurrentInstance().getExternalContext().redirect("/AEWeb3/CargarDatos");
		
		} 
		
			
		}
	
	
	public String seleccionarUsuarios() throws PersistenciaException {
		usuariosSeleccionados=gestionUsuarioService.seleccionarUsuarios(criterioNombre, criterioRol, criterioActivo);
	
		return "";
	}
	
	public void viewDatosExtra() {
		
		if ( usuarioSeleccionado.getRol().equals(admin) || usuarioSeleccionado.getRol().equals(inv) ) {
				this.datosExtra = true;
                                   	
        } else  {
        	this.datosExtra = false;
        	
        }
		
	
	}
	
	
	
	public boolean uniqueCedula(String cedula) throws PersistenciaException {
		
		List<Usuario> usuarios = usuariosPersistenciaDAO.buscarUsuarioValidar();
		boolean verificar = false;
		
		
		for (Usuario user : usuarios) {
			if (user.getDocumento() != null && user.getDocumento().equals(cedula) && user.getId() != gestionUsuarioService.getIdUser() && user.getActivo() == true) {
				verificar = true;
				break;
			}
		}
		
		return verificar;
	}

	
	public boolean uniqueUsuario(String usuario) throws PersistenciaException {
		
		List<Usuario> usuarios = usuariosPersistenciaDAO.buscarUsuarioValidar();
		boolean verificar = false;
		
		for (Usuario user : usuarios) {
			if (user.getNickname().equals(usuario) && user.getId() != gestionUsuarioService.getIdUser() && user.getActivo() == true) {
				verificar = true;
				break;
			}
		}
		
		return verificar;
	}
	
public boolean uniqueMail(String mail) throws PersistenciaException {
		
		List<Usuario> usuarios = usuariosPersistenciaDAO.buscarUsuarioValidar();
		boolean verificar = false;
		
		for (Usuario user : usuarios) {
			if (user.getMail().equals(mail) && user.getId() != gestionUsuarioService.getIdUser() && user.getActivo() == true) {
				verificar = true;
				break;
			}
		}
		
		return verificar;
	}
	

	
	
	
public List<Ciudad> listaCiudades() {
	List<Ciudad> listaCiudades = new ArrayList<>();
	List<Ciudad> listaC = ciudadEmpresaDAO.ciudadDeparamento(departamentoUsuario);
	
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
	
	
	
		
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getModalidad() {
		return modalidad;
	}
	public void setModalidad(String modalidad) {
		this.modalidad = modalidad;
	}
	public Usuario getUsuarioSeleccionado() {
		return usuarioSeleccionado;
	}
	public void setUsuarioSeleccionado(Usuario usuarioSeleccionado) {
		this.usuarioSeleccionado = usuarioSeleccionado;
	}
	
	
	public boolean getAutenticado() {
		return autenticado;
	}
	
	public void setAutenticado(boolean autenticado) {
		this.autenticado = autenticado;
	}
	
	public Usuario getUsuarioIngresado() {
		return usuarioIngresado;
	}
	public void setUsuarioIngresado(Usuario usuarioIngresado) {
		this.usuarioIngresado = usuarioIngresado;
	}
	
	public boolean getModoEdicion() {
		return modoEdicion;
	}
	public boolean isModoEdicion() {
		return modoEdicion;
	}
	public void setModoEdicion(boolean modoEdicion) {
		this.modoEdicion = modoEdicion;
	}
	public String getCriterioNombre() {
		return criterioNombre;
	}
	public void setCriterioNombre(String criterioNombre) {
		this.criterioNombre = criterioNombre;
	}
	
	
	public String getCriterioRol() {
		return criterioRol;
	}
	public void setCriterioRol(String criterioRol) {
		this.criterioRol = criterioRol;
	}
	public Boolean getCriterioActivo() {
		return criterioActivo;
	}
	public void setCriterioActivo(Boolean criterioActivo) {
		this.criterioActivo = criterioActivo;
	}
	public List<Usuario> getUsuariosSeleccionados() {
		return usuariosSeleccionados;
	}
	public void setUsuariosSeleccionados(List<Usuario> usuariosSeleccionados) {
		this.usuariosSeleccionados = usuariosSeleccionados;
	}
	
	public Boolean getDatosExtra() {
		return datosExtra;
	}
	public void setDatosExtra(Boolean datosExtra) {
		this.datosExtra = datosExtra;
	}
	public String getCiudadUsuario() {
		return ciudadUsuario;
	}
	public void setCiudadUsuario(String ciudadUsuario) {
		this.ciudadUsuario = ciudadUsuario;
	}
	public Rol getAdmin() {
		return admin;
	}
	public void setAdmin(Rol admin) {
		this.admin = admin;
	}
	public Rol getInv() {
		return inv;
	}
	public void setInv(Rol inv) {
		this.inv = inv;
	}
	public Rol getAfic() {
		return afic;
	}
	public void setAfic(Rol afic) {
		this.afic = afic;
	}
	public Usuario getUsuarioIngresadoLDAP() {
		return usuarioIngresadoLDAP;
	}
	public void setUsuarioIngresadoLDAP(Usuario usuarioIngresadoLDAP) {
		this.usuarioIngresadoLDAP = usuarioIngresadoLDAP;
	}
	public Boolean getVarLDAP() {
		return varLDAP;
	}
	public void setVarLDAP(Boolean varLDAP) {
		this.varLDAP = varLDAP;
	}
	public Usuario getUsuarioLDAP() {
		return usuarioLDAP;
	}
	public void setUsuarioLDAP(Usuario usuarioLDAP) {
		this.usuarioLDAP = usuarioLDAP;
	}
	public String getDepartamentoUsuario() {
		return departamentoUsuario;
	}
	public void setDepartamentoUsuario(String departamentoUsuario) {
		this.departamentoUsuario = departamentoUsuario;
	}
	public Boolean getEdicionClave() {
		return edicionClave;
	}
	public void setEdicionClave(Boolean edicionClave) {
		this.edicionClave = edicionClave;
	}
	public String getClaveNueva() {
		return claveNueva;
	}
	public void setClaveNueva(String claveNueva) {
		this.claveNueva = claveNueva;
	}
		
	
	
	
	
	
	
		
	
	
	
}
