package com.capa2LogicaNegocio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.capa3Persistencia.DAO.EstacionEmpresaDAO;
import com.capa3Persistencia.DAO.UsuariosEmpresaDAO;
import com.capa3Persistencia.entities.Estacion.Estacion;
import com.capa3Persistencia.entities.Estacion.EstacionEmpresa;
import com.capa3Persistencia.entities.Usuario.Usuario;
import com.capa3Persistencia.entities.Usuario.UsuarioEmpresa;
import com.capa3Persistencia.exception.PersistenciaException;

@Stateless
@LocalBean

public class GestionEstacionService implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EJB
	EstacionEmpresaDAO estacionPersistenciaDAO;
	
	

	public Estacion fromEstacionEmpresa(EstacionEmpresa e) {
		Estacion estacion=new Estacion();
		estacion.setId(e.getId());
		estacion.setNombre(e.getNombre());
		estacion.setDescripcion(e.getDescripcion());
		estacion.setCiudad(e.getCiudad());
		estacion.setDepartamento(e.getDepartamento());
		estacion.setActivo(e.getActivo());
		estacion.setLatitud(e.getLatitud());
		estacion.setLongitud(e.getLongitud());
		
		return estacion;
	}
	public EstacionEmpresa toEstacionEmpresa(Estacion e) {
		EstacionEmpresa estacionEmpresa=new EstacionEmpresa();
		estacionEmpresa.setId(e.getId()!=null?e.getId().longValue():null);
		estacionEmpresa.setNombre(e.getNombre());
		estacionEmpresa.setDescripcion(e.getDescripcion());
		estacionEmpresa.setCiudad(e.getCiudad());
		estacionEmpresa.setDepartamento(e.getDepartamento());
		estacionEmpresa.setActivo(e.getActivo());
		estacionEmpresa.setLatitud(e.getLatitud());
		estacionEmpresa.setLongitud(e.getLongitud());
		
		return estacionEmpresa;
	}
	
	public List<Estacion> seleccionarEstaciones() throws PersistenciaException {
		//buscamos todos los  objetos UsuarioEmpresa
		List<EstacionEmpresa> listaEstacionEmpresa = estacionPersistenciaDAO.buscarUsuarios();
		
		List<Estacion> listaEstacion=new ArrayList<Estacion>();
		//recorremos listaEmpleadosEmpresa y vamos populando listaEmpleado (haciendo la conversion requerida)
		for (EstacionEmpresa estacionEmpresa : listaEstacionEmpresa) {
			listaEstacion.add(fromEstacionEmpresa(estacionEmpresa));
		}
		
		return listaEstacion;
	}
	
	public Estacion buscarEstacion(Long i) {
		EstacionEmpresa e = estacionPersistenciaDAO.buscarEstacion(i);
		return fromEstacionEmpresa(e);
	}
	
	public Estacion agregarEstacion(Estacion estacionSeleccionado) throws PersistenciaException   {
		EstacionEmpresa e = estacionPersistenciaDAO.agregarEstacion(toEstacionEmpresa(estacionSeleccionado));       
		return fromEstacionEmpresa(e);

	}
	
	public void actualizarEstacion(Estacion estacionSeleccionado) throws PersistenciaException   {
		EstacionEmpresa e = estacionPersistenciaDAO.modificarEstacion(toEstacionEmpresa(estacionSeleccionado)); 
	}
	
	public Estacion borrarEstacion(Estacion estacionSeleccionado) throws PersistenciaException   {
		EstacionEmpresa e = estacionPersistenciaDAO.borrarEstacion(toEstacionEmpresa(estacionSeleccionado)); //borrarUsuario(toUsuarioEmpresa(u));
		return fromEstacionEmpresa(e);


	}


}
