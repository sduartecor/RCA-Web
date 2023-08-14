	package com.ws.restapi;


import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.GET;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.capa2LogicaNegocio.GestionUsuarioService;
import com.capa3Persistencia.entities.Usuario.Usuario;
import com.capa3Persistencia.exception.PersistenciaException;

@Path("usuarios")
public class RestApiService {
	
	

	@EJB
	GestionUsuarioService gestionUsuarioService;
	
	
	@GET
	@Path("obtenerUsuario/{id}")
	@Produces("application/json")
	public Usuario obtenerUsuario(@PathParam("id") Long id){
		try {
			 Usuario usuario = gestionUsuarioService.buscarUsuario(id);
			 if (usuario==null) {
				 return new Usuario();
			 }
			 return usuario;
		}catch(Exception e) {
			e.printStackTrace();
			return new Usuario(); 
		}
	
		
	}
	
	@GET
	@Path("listarUsuarios")
	@Produces("application/json")
	public List<Usuario> listarUsuarios(){
		

		try {
			 List<Usuario> listaUsuarios = gestionUsuarioService.seleccionarUsuarios();
			 return listaUsuarios;
			
			
		}catch(Exception e) {
			e.printStackTrace();
			return  new ArrayList<Usuario>(); 
		}
		
	}
	
	
	@GET
	@Path("/login{usuario}-{password}")
	@Produces("application/json")
	public Usuario login(@PathParam("usuario") String usuario, @PathParam("password") String password) throws PersistenciaException {
		try {
			Usuario usuarioEmpresa = gestionUsuarioService.usuarioLogin(usuario);
			if ( usuarioEmpresa.getClave().equals(gestionUsuarioService.encryptString(password)) == false || usuarioEmpresa == null) {
				return null;
			} else {
				return usuarioEmpresa;
			}
			
		} catch (Exception e) {
			return null;
		}
	}
	
	
	@GET
	@Path("/ldap{usuario}-{password}")
	@Produces("application/json")
	public Usuario loginLdap(@PathParam("usuario") String usuario, @PathParam("password") String password) throws PersistenciaException {
		try {
			Usuario usuarioEmpresa = gestionUsuarioService.usuarioLdap(usuario, password);
			if (usuarioEmpresa == null) {
				return null;
			} else {
				return usuarioEmpresa;
			}
			
		} catch (Exception e) {
			return null;
		}
	}
	
	
	
	

	
}
