package com.capa3Persistencia.DAO;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;


import com.capa3Persistencia.entities.Formulario.Formulario;
import com.capa3Persistencia.exception.PersistenciaException;



@Stateless
@LocalBean
public class FormularioEmpresaDAO {
	@PersistenceContext 
	private EntityManager em;

    /**
     * Default constructor. 
     */
    public FormularioEmpresaDAO() {
        // TODO Auto-generated constructor stub
    }
    
    
    
    
    public Formulario modificarFormulario(Formulario formulario) throws PersistenciaException{

		try {
			Formulario formularioEmpresa = em.merge(formulario);
			em.flush();
			return formularioEmpresa;
		} catch (PersistenceException e) {
			throw new PersistenciaException("No se pudo modificar el formulario." + e.getMessage(), e);
		}
	}
    
  //Logico
  	public Formulario borrarFormulario(Formulario formulario) throws PersistenciaException {

  				
  		try {
  			Formulario formularioEmpresa = formulario;
  			formularioEmpresa.setActivo(false);
  			em.merge(formularioEmpresa);
  			em.flush();
  		return formularioEmpresa;
  		} catch(PersistenceException e) {
  			throw new PersistenciaException("No se pudo borrar el usuario. Id=" + formulario.getId());
  		}
  	}
    
    
	public boolean crear(Formulario formulario) {
		try {
			formulario.setNombre(formulario.getNombre().toUpperCase());
			em.persist(formulario);
			em.flush();
			return true;
		} catch(PersistenceException e) {
			return false;
		}
		
	}

	

	public boolean actualizar(Formulario formulario) throws PersistenciaException{
		 try {
			 formulario.setNombre(formulario.getNombre().toUpperCase()); 
			 List<Formulario> formularios = encontrarFormulario(formulario.getNombre());
			 if (formularios.size() > 0) {
				 Formulario f = formularios.get(0);
				 f.setCampos(formulario.getCampos());
				 em.persist(f);
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
			Formulario formulario = em.find(Formulario.class, id);
			em.remove(formulario);
			em.flush();
			return true;
		} catch(PersistenceException e) {
			return false;
		}
		
	}
	
	public Formulario buscarFormuario(Long id) {
		Formulario formularioEmpresa = em.find(Formulario.class, id);
		return formularioEmpresa;
	}


	
	
	
	
	public List<Formulario> encontrarFormulario(String nombre) {
		TypedQuery<Formulario> query ;
		query = em.createQuery("SELECT f FROM Formulario f WHERE f.nombre = :nombreFormulario", Formulario.class)
				.setParameter("nombreFormulario", nombre);
		return query.getResultList();
	}
	
	public List<Formulario> obtenerTodos() {
		TypedQuery<Formulario> query ;
		query = em.createQuery("SELECT f FROM Formulario f WHERE f.activo=true", Formulario.class);
		return query.getResultList();
	}
	
	
	public Formulario crearDos(Formulario formulario) throws PersistenciaException{
		try {
			
			Formulario form = em.merge(formulario);
			em.flush();
			return form;
		} catch(PersistenceException e) {
			return null;
		}
		
	}


	

}

