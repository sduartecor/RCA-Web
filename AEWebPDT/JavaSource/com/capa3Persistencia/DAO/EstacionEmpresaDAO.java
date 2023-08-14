package com.capa3Persistencia.DAO;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import com.capa3Persistencia.entities.Ciudad.Ciudad;
import com.capa3Persistencia.entities.Estacion.Estacion;
import com.capa3Persistencia.entities.Estacion.EstacionEmpresa;
import com.capa3Persistencia.entities.Usuario.UsuarioEmpresa;
import com.capa3Persistencia.exception.PersistenciaException;


/**
 * Session Bean implementation class EstacionDAO
 */
@Stateless
@LocalBean
public class EstacionEmpresaDAO {
	@PersistenceContext 
	private EntityManager em;

	/**
	 * Default constructor. 
	 */
	public EstacionEmpresaDAO() {
		// TODO Auto-generated constructor stub
	}

	public boolean crear(EstacionEmpresa estacion) {
		try {
			estacion.setNombre(estacion.getNombre().toUpperCase());
			estacion.setDescripcion(estacion.getDescripcion().toUpperCase());
			em.persist(estacion);
			em.flush();
			return true;
		} catch(PersistenceException e) {
			return false;
		}

	}
	
	public EstacionEmpresa agregarEstacion(EstacionEmpresa estacion) throws PersistenciaException {

		try {
			estacion.setNombre(estacion.getNombre().toUpperCase());
			estacion.setDescripcion(estacion.getDescripcion().toUpperCase());
			EstacionEmpresa estacionEmpresa = em.merge(estacion);
			em.flush();
			return estacionEmpresa;
		} 
		catch (PersistenceException e) {
			throw new PersistenciaException("No se pudo agregar el estacion." + e.getMessage(), e);
		}
		finally {
			
		}
	}
	
	public EstacionEmpresa modificarEstacion(EstacionEmpresa estacion) throws PersistenciaException {

		try {
			estacion.setNombre(estacion.getNombre().toUpperCase());
			estacion.setDescripcion(estacion.getDescripcion().toUpperCase());
			EstacionEmpresa estacionEmpresa = em.merge(estacion);
			em.flush();
			return estacionEmpresa;
		} catch (PersistenceException e) {
			throw new PersistenciaException("No se pudo modificar la estacion." + e.getMessage(), e);
		}
	}

	
	//Logico
		public EstacionEmpresa borrarEstacion(EstacionEmpresa estacion) throws PersistenciaException {

					
			try {
				EstacionEmpresa estacionEmpresa = estacion;
				estacionEmpresa.setActivo(false);
				em.merge(estacionEmpresa);
				em.flush();
			return estacionEmpresa;
			} catch(PersistenceException e) {
				throw new PersistenciaException("No se pudo borrar la estacion. Id=" + estacion.getId());
			}
		}

	public boolean actualizar(EstacionEmpresa estacion) {
		try {
			estacion.setNombre(estacion.getNombre().toUpperCase());
			estacion.setDescripcion(estacion.getDescripcion().toUpperCase());
			
			List<EstacionEmpresa> resultado = encontrarEstacion(estacion.getNombre());
			if (resultado.size() > 0) {
				EstacionEmpresa e = resultado.get(0);
				
				e.setNombre(estacion.getNombre());
				e.setDescripcion(estacion.getDescripcion());
				e.setCiudad(estacion.getCiudad());
				em.merge(e);
				em.flush();
				return true;
			} else {
				return false;
			}
			
		} catch(PersistenceException e) {
			return false;
		}

	}


	public boolean borrar(Long id) {
		try {
			EstacionEmpresa estacion = em.find(EstacionEmpresa.class, id);
			em.remove(estacion);
			em.flush();
			return true;
		} catch(PersistenceException e) {
			return false;
		}

	}
	
	public EstacionEmpresa buscarEstacion(Long id) {
		EstacionEmpresa estacionEmpresa = em.find(EstacionEmpresa.class, id);
		return estacionEmpresa;
	}


	public List<EstacionEmpresa> obtenerTodos() {
		TypedQuery<EstacionEmpresa> query ;
		query = em.createQuery("SELECT e FROM Estacion e", EstacionEmpresa.class);
		return query.getResultList();
	}
	
	public List<Estacion> obtenerTodoDos() {
		TypedQuery<Estacion> query ;
		query = em.createQuery("SELECT e FROM Estacion e", Estacion.class);
		return query.getResultList();
	}
	
	
	public List<EstacionEmpresa> buscarUsuarios() throws PersistenciaException {
		try {
		
		String query= 	"Select e from EstacionEmpresa e WHERE e.activo=true ";
		List<EstacionEmpresa> resultList = (List<EstacionEmpresa>) em.createQuery(query,EstacionEmpresa.class)
							 .getResultList();
		return  resultList;
		}catch(PersistenceException e) {
			throw new PersistenciaException("No se pudo hacer la consulta." + e.getMessage(),e);
		}
		
	}


	public List<EstacionEmpresa> encontrarEstacion(String nombre) {
		TypedQuery<EstacionEmpresa> query ;
		query = em.createQuery("SELECT e FROM Estacion e WHERE e.nombre = :nombreEstacion", EstacionEmpresa.class)
				.setParameter("nombreEstacion", nombre);
		return query.getResultList();
	}
	
	
	public boolean asignarCiudad(Long idEstacion, Long idCiudad) {
		try {
			EstacionEmpresa e = em.find(EstacionEmpresa.class, idEstacion);
			e.setCiudad(em.find(Ciudad.class, idCiudad));
			em.flush();
			return true;
		} catch (PersistenceException e) {
			return false;
		}

	}
	


}