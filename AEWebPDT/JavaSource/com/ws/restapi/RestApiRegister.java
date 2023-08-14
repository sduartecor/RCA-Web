package com.ws.restapi;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.capa3Persistencia.entities.Registro;
import com.capa3Persistencia.entities.Formulario.Formulario;
import com.capa3Persistencia.DAO.CiudadEmpresaDAO;
import com.capa3Persistencia.DAO.EstacionEmpresaDAO;
import com.capa3Persistencia.DAO.FormularioEmpresaDAO;
import com.capa3Persistencia.DAO.RegistroEmpresaDAO;
import com.capa3Persistencia.DAO.UsuariosEmpresaDAO;



@Path("registros")
public class RestApiRegister {
	
	@EJB
	RegistroEmpresaDAO registroDAO;
	
	@EJB
	FormularioEmpresaDAO formularioEmpresaDAO;
	
	@EJB
	CiudadEmpresaDAO ciudadEmpresaDAO;
	
	@EJB
	UsuariosEmpresaDAO usuarioEmpresaDAO;
	
	@EJB
	EstacionEmpresaDAO estacionEmpresaDAO;
	
	@EJB
	RegistroEmpresaDAO registroEmpresaDAO;

	@GET
	@Path("listarRegistros")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Registro> listaRegistro(){

		try {
			 List<Registro> listaRegistro = registroDAO.obtenerTodos();
			 return listaRegistro;
			
			
		}catch(Exception e) {
			e.printStackTrace();
			return  new ArrayList<Registro>(); 
		}
	}
	
	@POST
	@Path("altaRegistro{fecha}")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean altaRegistro(Registro registro,@PathParam("fecha") String fecha ) {
		
		try {
			
			Date fechaNew = Date.valueOf(fecha.toString()); 
			registro.setFecha(fechaNew);
			Registro altaCorrecta = registroEmpresaDAO.crear(registro);
			 
			 if (altaCorrecta!=null) {
				 return true;
			 } else {
				 return false;
			 }
			
		} catch (Exception e) {
			e.printStackTrace();
			
			return false;
			
		}
		

		
	}
	
	@POST
	@Path("modificarRegistro{fecha}")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean modificarRegistro(Registro registro,@PathParam("fecha") String fecha ) {
		
		try {

			Date fechaNew = Date.valueOf(fecha); 
			registro.setFecha(fechaNew);
			Registro modificarCorrecta = registroEmpresaDAO.actualizar(registro);
			 
			 if (modificarCorrecta != null) {
				 return true;
			 } else {
				 return false;
			 }
			
		} catch (Exception e) {
			e.printStackTrace();
			
			return false;
			
		}
		

		
	}
	
	@POST
	@Path("bajaRegistro{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean bajaRegistro(@PathParam("id") Long id) {
		
		
		try {

			boolean bajaCorrecta = registroEmpresaDAO.borrar(id);
			 
			 if (bajaCorrecta == false) {
				 return true;
			 } else {
				 return false;
			 }
			
		} catch (Exception e) {
			e.printStackTrace();
			
			return false;
			
		}
		

		
	}

	
	
	
	
	
	
	
	
}
