package com.capa3Persistencia.DAO;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import com.capa3Persistencia.entities.Ciudad.Ciudad;
import com.capa3Persistencia.entities.Departamento.Departamento;
import com.capa3Persistencia.entities.Usuario.Usuario;
import com.capa3Persistencia.exception.PersistenciaException;



/**
 * Session Bean implementation class CiudadDAO
 */
@Stateless
@LocalBean
public class CiudadEmpresaDAO {
	@PersistenceContext 
	private EntityManager em;

    /**
     * Default constructor. 
     */
    public CiudadEmpresaDAO() {
        // TODO Auto-generated constructor stub
    }
    
	public Ciudad crear(Ciudad ciudad) throws PersistenciaException {
		try {
			Ciudad ciudadEmpresa = em.merge(ciudad);
			em.flush();
			return ciudadEmpresa;
		}catch (PersistenceException e) {
				throw new PersistenciaException("No se pudo agregar el ciudad." + e.getMessage(), e);
			}
			finally {
				
			}
		
	}

	
	public boolean actualizar(Ciudad ciudad) {
		 try {
			 em.merge(ciudad);
			 em.flush();
			 return true;
		 } catch(PersistenceException e) {
			 return false;
		 }
		
	}

	
	public boolean borrar(Long id) {
		try {
			Ciudad ciudad = em.find(Ciudad.class, id);
			em.remove(ciudad);
			em.flush();
			return true;
		} catch(PersistenceException e) {
			return false;
		}
		
	}


	public List<Ciudad> obtenerTodos() {
		TypedQuery<Ciudad> query ;
		query = em.createQuery("SELECT c FROM Ciudad c", Ciudad.class);
		return query.getResultList();
	}
	
	
	public boolean asignarDepartamento(Long idCiudad, Long idDepartamento) {
		try {
			Ciudad c = em.find(Ciudad.class, idCiudad);
			c.setDepartamento(em.find(Departamento.class, idDepartamento));
			em.flush();
			return true;
		} catch (PersistenceException e) {
			e.printStackTrace();
			return false;
		}

		
	}
	
	public List<Ciudad> encontrarCiudadNombre(String nombre) {
		TypedQuery<Ciudad> query ;
		query = em.createQuery("SELECT u FROM Ciudad u WHERE u.nombre = :nombre", Ciudad.class)
				.setParameter("nombre", nombre);
		return query.getResultList();
	}
	
	public Ciudad ciudadNombre(String nombre) {
		TypedQuery<Ciudad> query ;
		query = em.createQuery("SELECT u FROM Ciudad u WHERE u.nombre = :nombre", Ciudad.class)
				.setParameter("nombre", nombre);
		
		return query.getSingleResult();
	}
	
	public List<Ciudad> ciudadDeparamento(String nombre) {
		TypedQuery<Ciudad> query ;
		query = em.createQuery("SELECT u FROM Ciudad u WHERE u.departamento.nombre = :nombre", Ciudad.class)
				.setParameter("nombre", nombre);
		return query.getResultList();
	}
	
	
	
	
	

}
