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
import com.capa3Persistencia.DAO.FormularioEmpresaDAO;
import com.capa3Persistencia.entities.Formulario.Formulario;
import com.capa3Persistencia.entities.Usuario.Usuario;
import com.capa3Persistencia.exception.PersistenciaException;

@Path("formularios")
public class RestApiFormulario {
	
	

	@EJB
	FormularioEmpresaDAO formDAO;
	
	
		
	@GET
	@Path("listarFormularios")
	@Produces("application/json")
	public List<Formulario> listaFormularios(){
		

		try {
			 List<Formulario> listaFormularios = formDAO.obtenerTodos();
			 return listaFormularios;
			
			
		}catch(Exception e) {
			e.printStackTrace();
			return  new ArrayList<Formulario>(); 
		}
		
	}
		
	
	

	
}
