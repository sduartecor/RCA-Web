package com.capa3Persistencia.DAO;

import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import com.capa3Persistencia.entities.Registro;
import com.capa3Persistencia.entities.Departamento.Departamento;
import com.capa3Persistencia.exception.PersistenciaException;




/**
 * Session Bean implementation class DepartamentoDAO
 */
@Stateless
@LocalBean


public class DepartamentoEmpresaDAO {
	
	@PersistenceContext 
	private EntityManager em;

    /**
     * Default constructor. 
     */
    public DepartamentoEmpresaDAO() {
        // TODO Auto-generated constructor stub
    }
    
   
    
    public boolean crear(Departamento departamento) {
		try {
			em.persist(departamento);
			em.flush();
			return true;
		} catch(PersistenceException e) {
			return false;
		}
		
	}
    
	
	public boolean actualizar(Departamento departamento) {
		 try {
			 em.merge(departamento);
			 em.flush();
			 return true;
		 } catch(PersistenceException e) {
			 return false;
		 }
		
	}

	
	public boolean borrar(Long id) {
		try {
			Departamento departamento = em.find(Departamento.class, id);
			em.remove(departamento);
			em.flush();
			return true;
		} catch(PersistenceException e) {
			return false;
		}
		
	}

	
	public List<Departamento> buscarDepartamento() throws PersistenciaException {
		try {
		
		String query= 	"Select d from Departamento d ";
		List<Departamento> resultList = (List<Departamento>) em.createQuery(query,Departamento.class)
							 .getResultList();
		return  resultList;
		}catch(PersistenceException e) {
			throw new PersistenciaException("No se pudo hacer la consulta." + e.getMessage(),e);
		}
		
	}

	public List<Departamento> obtenerTodos() {
		TypedQuery<Departamento> query ;
		query = em.createQuery("SELECT d FROM Departamento d", Departamento.class);
		return query.getResultList();
	}
	
	

}

