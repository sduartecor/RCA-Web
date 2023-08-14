package com.capa2LogicaNegocio;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;

import com.capa3Persistencia.DAO.CiudadEmpresaDAO;
import com.capa3Persistencia.DAO.UsuariosEmpresaDAO;
import com.capa3Persistencia.entities.Rol;
import com.capa3Persistencia.entities.Ciudad.Ciudad;
import com.capa3Persistencia.entities.Usuario.Usuario;
import com.capa3Persistencia.entities.Usuario.UsuarioEmpresa;
import com.capa3Persistencia.exception.PersistenciaException;
import com.ldap.ConexionLDAP;
import com.utils.Util;




@Stateless
@LocalBean

public class GestionUsuarioService implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EJB
	UsuariosEmpresaDAO usuariosPersistenciaDAO;
	
	private String nicknameUser;
	private Long idUser;
	

	
	

	public Usuario fromUsuarioEmpresa(UsuarioEmpresa u) {
		Usuario usuario=new Usuario();
		usuario.setId(u.getId());
		usuario.setActivo(u.getActivo());
		usuario.setDocumento(u.getDocumento());
		usuario.setApellido(u.getApellido());
		usuario.setNombre(u.getNombre());
		usuario.setRol(u.getRol());
		usuario.setNickname(u.getNickname());
		usuario.setClave(u.getClave());
		usuario.setMail(u.getMail());
		usuario.setDomicilio(u.getDomicilio());
		usuario.setCiudad(u.getCiudad());
		usuario.setTelefono(u.getTelefono());
		
		return usuario;
	}
	public UsuarioEmpresa toUsuarioEmpresa(Usuario u) {
		UsuarioEmpresa usuarioEmpresa=new UsuarioEmpresa();
		usuarioEmpresa.setId(u.getId()!=null?u.getId().longValue():null);
		usuarioEmpresa.setActivo(u.getActivo());
		usuarioEmpresa.setDocumento(u.getDocumento());
		usuarioEmpresa.setApellido(u.getApellido());
		usuarioEmpresa.setNombre(u.getNombre());
		usuarioEmpresa.setRol(u.getRol());
		usuarioEmpresa.setNickname(u.getNickname());
		usuarioEmpresa.setClave(u.getClave());
		usuarioEmpresa.setMail(u.getMail());
		usuarioEmpresa.setDomicilio(u.getDomicilio());
		usuarioEmpresa.setCiudad(u.getCiudad());
		usuarioEmpresa.setTelefono(u.getTelefono());
		
		return usuarioEmpresa;
	}


	
	// servicios para capa de Presentacion

	

	

	public List<Usuario> seleccionarUsuarios() throws PersistenciaException {
		//buscamos todos los  objetos UsuarioEmpresa
		List<UsuarioEmpresa> listaUsuariosEmpresa = usuariosPersistenciaDAO.buscarUsuarios();
		
		List<Usuario> listaUsuarios=new ArrayList<Usuario>();
		//recorremos listaEmpleadosEmpresa y vamos populando listaEmpleado (haciendo la conversion requerida)
		for (UsuarioEmpresa usuarioEmpresa : listaUsuariosEmpresa) {
			listaUsuarios.add(fromUsuarioEmpresa(usuarioEmpresa));
		}
		return listaUsuarios;
	}
	
	


	public List<Usuario> seleccionarUsuarios(String criterioNombre,String criterioRol,Boolean criterioActivo) throws PersistenciaException {
		//buscamos empleados segun criterio indicado
		List<UsuarioEmpresa> listaUsuariosEmpresa = usuariosPersistenciaDAO.seleccionarUsuarios(criterioNombre,criterioRol,criterioActivo);
		//lista para devolver la seleccion de empleados
		List<Usuario> listaUsuarios=new ArrayList<Usuario>();
		//recorremos listaEmpleadosEmpresa y vamos populando listaEmpleado (haciendo la conversion requerida)
		for (UsuarioEmpresa usuarioEmpresa : listaUsuariosEmpresa) {
			listaUsuarios.add(fromUsuarioEmpresa(usuarioEmpresa));
		}
		return listaUsuarios;
		
	}
	
	
	public boolean verificarLogin(Usuario usuarioSeleccionado) throws PersistenciaException, NoSuchAlgorithmException {
		
		boolean autenticado = false;
		List<Usuario> listaUsuarios = seleccionarUsuarios();
		for (Usuario u : listaUsuarios) {
			if (u.getNickname().equals(usuarioSeleccionado.getNickname()) &&  u.getClave().equals(encryptString(usuarioSeleccionado.getClave())) && u.getActivo() == true ) {
				autenticado = true;
			}			
		}
		return autenticado;
	}
	
	public Usuario usuarioLogin (String nickname) throws PersistenciaException{
		Usuario usuarioEmpresa = null;
		List<Usuario> listaUsuarios = seleccionarUsuarios();
		for (Usuario user : listaUsuarios) {
			if (user.getNickname().equals(nickname)) {
				usuarioEmpresa = user;
			}
		}
		
		return usuarioEmpresa;
	}
	
	public Usuario usuarioLdap (String nickname, String clave) throws PersistenciaException {
		//
		HashMap<String, String> datosUsuario = new HashMap<String, String>();
		Usuario usuarioEmpresa = new Usuario();
		//
		datosUsuario.clear();
		//
		ConexionLDAP.setLdapCorreoUsuario(nickname);
		ConexionLDAP.setLdapPasswordUsuario(clave);
		//
		try {
			
			datosUsuario = ConexionLDAP.autenticar();
			// Si lo logro, guardo los datos
			if(datosUsuario.get("usuario") != null) {
				
				usuarioEmpresa.setNickname(nickname);
				usuarioEmpresa.setClave(clave);
				
				// segun el grupo que viene de AD, seteo el rol
				if(datosUsuario.get("grupo").equals("AppAdministradores")){
					usuarioEmpresa.setRol(Rol.Administrador);
				} else if (datosUsuario.get("grupo").equals("AppInvestigadores")) {
					usuarioEmpresa.setRol(Rol.Investigador);
				}
				
				return usuarioEmpresa;
			}
			
		} catch (NamingException e1) {
			
			// TODO Auto-generated catch block
			e1.printStackTrace();
		
		}
		return usuarioEmpresa; 
		
		
		
		
	}
	
	
	public String encryptString(String input) throws NoSuchAlgorithmException {
			
			//SHA-384 and SHA-512
			MessageDigest md = MessageDigest.getInstance("MD5");
			
			byte[] messageDigest = md.digest(input.getBytes());
			
			BigInteger bigInt = new BigInteger(1,messageDigest);
			
			return bigInt.toString(16);
			
	}
	


	
	
	
	public String obtenerRolUsuario(Usuario usuarioSeleccionado) throws PersistenciaException, NoSuchAlgorithmException {
    	String rol = null;
    	List<Usuario> listaUsuarios = seleccionarUsuarios();
		for (Usuario u : listaUsuarios) {
    	
			if (u.getNickname().equals(usuarioSeleccionado.getNickname()) && u.getClave().equals(encryptString(usuarioSeleccionado.getClave()))) {
				rol = u.getRol().name();
;			}	
		}
		
		return rol;
    }
	
	public Long obtenerId(Usuario usuario) throws PersistenciaException {
		Long id = null;
		List<Usuario> listaUsuarios = seleccionarUsuarios();
		
		for (Usuario u : listaUsuarios) {
	    	
			if (u.getNickname().equals(usuario.getNickname())) {
				id = u.getId();
			}	
		}
		
		return id;
		
	}
	
	public Usuario buscarUsuarioEmpresa(Long id) {
		UsuarioEmpresa u = usuariosPersistenciaDAO.buscarUsuario(id);
		return fromUsuarioEmpresa(u);
	}

	public Usuario buscarUsuario(Long i) {
		UsuarioEmpresa u = usuariosPersistenciaDAO.buscarUsuario(i);
		return fromUsuarioEmpresa(u);
	}
	
	public Usuario agregarUsuario(Usuario usuarioSeleccionado) throws PersistenciaException   {
		UsuarioEmpresa u = usuariosPersistenciaDAO.agregarUsuario(toUsuarioEmpresa(usuarioSeleccionado));
		return fromUsuarioEmpresa(u);

	}
	
	public Usuario borrarUsuario(Usuario u) throws PersistenciaException   {
		UsuarioEmpresa a = usuariosPersistenciaDAO.borrarUsuario(toUsuarioEmpresa(u));
		return fromUsuarioEmpresa(a);


	}
	
	
	
	

	public void actualizarEmpleado(Usuario usuarioSeleccionado) throws PersistenciaException   {
		UsuarioEmpresa u = usuariosPersistenciaDAO.modificarUsuario(toUsuarioEmpresa(usuarioSeleccionado));
		
	}
	public String getNicknameUser() {
		return nicknameUser;
	}
	public void setNicknameUser(String nicknameUser) {
		this.nicknameUser = nicknameUser;
	}
	public Long getIdUser() {
		return idUser;
	}
	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}
	
	
}
