package com.capa3Persistencia.DAO;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import com.capa3Persistencia.entities.Registro;
import com.capa3Persistencia.entities.Ciudad.Ciudad;
import com.capa3Persistencia.entities.Formulario.Formulario;
import com.capa3Persistencia.exception.PersistenciaException;



@Stateless
@LocalBean
public class RegistroEmpresaDAO {
	@PersistenceContext 
	private EntityManager em;

	/**
	 * Default constructor. 
	 */
	public RegistroEmpresaDAO() {
		// TODO Auto-generated constructor stub
	}

	public Registro crear(Registro registro) throws PersistenciaException{
		try {
			registro.setMetodo(registro.getMetodo().toUpperCase());
			registro.setUsuario(registro.getUsuario().toUpperCase());
			Registro registroEmpresa = em.merge(registro);
			em.flush();
			return registroEmpresa;
		} catch (PersistenceException e) {
			throw new PersistenciaException("No se pudo crear el formulario." + e.getMessage(), e);
		}
	}


	public Registro actualizar(Registro registro) throws PersistenciaException{
		try {
			registro.setMetodo(registro.getMetodo().toUpperCase());
			Registro registroEmpresa = em.merge(registro);
			em.flush();
			return registroEmpresa;
			} catch(PersistenceException e) {
				throw new PersistenciaException("No se pudo modificar el formulario." + e.getMessage(), e);
			}
	}
	
	//Logico
  	public Registro borrarRegistro(Registro registro) throws PersistenciaException {

  				
  		try {
  			Registro registroEmpresa = registro;
  			registroEmpresa.setActivo(false);
  			em.merge(registroEmpresa);
  			em.flush();
  		return registroEmpresa;
  		} catch(PersistenceException e) {
  			throw new PersistenciaException("No se pudo borrar el usuario. Id=");
  		}
  	}


	public boolean borrar(Long id) {
		try {
			Registro registro = em.find(Registro.class, id);
			registro.setActivo(false);
			em.merge(registro);
			em.flush();
			return true;
		} catch(PersistenceException e) {
			return false;
		}

	}

	
	public List<Registro> obtenerTodos() {
		TypedQuery<Registro> query ;
		query = em.createQuery("SELECT r FROM Registro r WHERE r.activo=true", Registro.class);
		return query.getResultList();
	}
	
	public Registro encontrarRegistro(Long id) {
		Registro registro = em.find(Registro.class, id);
		return registro;
	}

	public List<Registro> encontrarRegistroCiudad(String ciudad) {
		TypedQuery<Registro> query ;
		query = em.createQuery("SELECT r FROM Registro r WHERE r.ciudad.nombre = :ciudadR", Registro.class)
				.setParameter("ciudadR", ciudad);
		return query.getResultList();
	}

	public List<Registro> encontrarRegistroDepartamento(String departamento) {
		TypedQuery<Registro> query ;
		query = em.createQuery("SELECT r FROM Registro r WHERE r.ciudad.departamento.nombre = :departamentoR", Registro.class)
				.setParameter("departamentoR", departamento);
		return query.getResultList()	;
	}
	
	public List<Registro> obtenerRegistrosFiltrados(Date fechaDesde, Date fechaHasta) throws ParseException {
		
		//
		TypedQuery<Registro> query ;
		query = em.createQuery("SELECT r FROM Registro r WHERE r.fecha BETWEEN :fechaDesdeR AND :fechaHastaR" , Registro.class).setParameter("fechaDesdeR", fechaDesde).setParameter("fechaHastaR", fechaHasta);
		return query.getResultList();
	}
	

	public List<Registro> obtenerRegistrosUsuario(String usuario) {
		
		TypedQuery<Registro> query ;
		query = em.createQuery("SELECT r FROM Registro r WHERE r.usuario  = :usuarioR", Registro.class).setParameter("usuarioR", usuario.toUpperCase());
		return query.getResultList();
	}
	
}

