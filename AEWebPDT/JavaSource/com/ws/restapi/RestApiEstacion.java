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

import com.capa2LogicaNegocio.GestionEstacionService;
import com.capa2LogicaNegocio.GestionUsuarioService;
import com.capa3Persistencia.DAO.FormularioEmpresaDAO;
import com.capa3Persistencia.entities.Estacion.Estacion;
import com.capa3Persistencia.entities.Formulario.Formulario;
import com.capa3Persistencia.entities.Usuario.Usuario;
import com.capa3Persistencia.exception.PersistenciaException;

@Path("estaciones")
public class RestApiEstacion {
	
	

	@EJB
	GestionEstacionService gestionEstacionService;

	
	
		
	@GET
	@Path("listarEstaciones")
	@Produces("application/json")
	public List<Estacion> listarEstaciones(){
		

		try {
			 List<Estacion> listarEstaciones = gestionEstacionService.seleccionarEstaciones();
			 return listarEstaciones;
			
			
		}catch(Exception e) {
			e.printStackTrace();
			return  new ArrayList<Estacion>(); 
		}
		
	}
		
	
	

	
}