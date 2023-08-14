package com.capa3Persistencia.DAO;


import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import com.capa2LogicaNegocio.GestionUsuarioService;
import com.capa3Persistencia.entities.Usuario.Usuario;
import com.capa3Persistencia.entities.Usuario.UsuarioEmpresa;
import com.capa3Persistencia.exception.PersistenciaException;


/**
 * Session Bean implementation class UsuariosEJBBean
 */
@Stateless
@LocalBean


public class UsuariosEmpresaDAO {

	@PersistenceContext
	private EntityManager em;
	
	@EJB
	GestionUsuarioService gestionUsuarioService;

	/**
	 * Default constructor.
	 */
	public UsuariosEmpresaDAO() {
		super();
	}

	
	public UsuarioEmpresa agregarUsuario(UsuarioEmpresa usuario) throws PersistenciaException {

		try {
			UsuarioEmpresa usuarioEmpresa = em.merge(usuario);
			em.flush();
			return usuarioEmpresa;
		} 
		catch (PersistenceException e) {
			throw new PersistenciaException("No se pudo agregar el usuario." + e.getMessage(), e);
		}
		finally {
			
		}
	}
	
	public boolean crear(Usuario usuario) {
		try {
			//em.merge(usuario);
			em.persist(usuario);
			em.flush();
			return true;
		} catch(PersistenceException e) {
			return false;
		}

	}
		//Logico
	public UsuarioEmpresa borrarUsuario(UsuarioEmpresa usuario) throws PersistenciaException {

				
		try {
			UsuarioEmpresa usuarioEmpresa = usuario;
			usuarioEmpresa.setActivo(false);
			em.merge(usuarioEmpresa);
			em.flush();
		return usuarioEmpresa;
		} catch(PersistenceException e) {
			throw new PersistenciaException("No se pudo borrar el usuario. Id=" + usuario.getId());
		}
	}
	
	public UsuarioEmpresa modificarUsuario(UsuarioEmpresa usuario) throws PersistenciaException {

		try {
			UsuarioEmpresa usuarioEmpresa = em.merge(usuario);
			em.flush();
			return usuarioEmpresa;
		} catch (PersistenceException e) {
			throw new PersistenciaException("No se pudo modificar el usuario." + e.getMessage(), e);
		}
	}

	public UsuarioEmpresa buscarUsuario(Long id) {
		UsuarioEmpresa usuarioEmpresa = em.find(UsuarioEmpresa.class, id);
		return usuarioEmpresa;
	}
	
	

	public List<UsuarioEmpresa> buscarUsuarios() throws PersistenciaException {
		try {
		
		String query= 	"Select u from UsuarioEmpresa u ";
		List<UsuarioEmpresa> resultList = (List<UsuarioEmpresa>) em.createQuery(query,UsuarioEmpresa.class)
							 .getResultList();
		return  resultList;
		}catch(PersistenceException e) {
			throw new PersistenciaException("No se pudo hacer la consulta." + e.getMessage(),e);
		}
		
	}
	
	public List<Usuario> buscarUsuarioValidar() throws PersistenciaException {
		try {
		
		String query= 	"Select u from Usuario u ";
		List<Usuario> resultList = (List<Usuario>) em.createQuery(query,Usuario.class)
							 .getResultList();
		return  resultList;
		}catch(PersistenceException e) {
			throw new PersistenciaException("No se pudo hacer la consulta." + e.getMessage(),e);
		}
		
	}


	public List<UsuarioEmpresa> seleccionarUsuarios(String criterioNombre,
			String criterioRol, Boolean criterioActivo) throws PersistenciaException {
		try {
			
			String query= 	"Select u from UsuarioEmpresa u WHERE u.activo=true  ";
			String queryCriterio="";
			if (criterioNombre!=null && ! criterioNombre.contentEquals("")) {
				queryCriterio+=(!queryCriterio.isEmpty()?" and ":"")+ " u.nombre like '%"+criterioNombre +"%' ";
			} 
			if (criterioRol!=null && ! criterioRol.equals(null)) {
				queryCriterio+=(!queryCriterio.isEmpty()?" and ":"")+" u.rol='"+criterioRol+"'  " ;
			}
			
			/*if (criterioActivo!=null) {
				queryCriterio+=(!queryCriterio.isEmpty()?" and ":"")+" e.activo  " ;
			}*/
			if (!queryCriterio.contentEquals("")) {
				query+=" AND "+queryCriterio;
			}
			List<UsuarioEmpresa> resultList = (List<UsuarioEmpresa>) em.createQuery(query,UsuarioEmpresa.class)
								 .getResultList();
			return  resultList;
			}catch(PersistenceException e) {
				throw new PersistenciaException("No se pudo hacer la consulta." + e.getMessage(),e);
			}
	}
	
	
	
	public List<Usuario> encontrarUsuarioPorNick(String nickname) {
		TypedQuery<Usuario> query ;
		query = em.createQuery("SELECT u FROM Usuario u WHERE u.nickname = :nombreUsuario", Usuario.class)
				.setParameter("nombreUsuario", nickname);
		return query.getResultList();
	}
	
	public boolean verificarCorreoExistente(String correo) {
	    TypedQuery<Long> query = em.createQuery("SELECT COUNT(u) FROM Usuario u WHERE u.mail = :correoUsuario", Long.class)
	        .setParameter("correoUsuario", correo);
	    Long count = query.getSingleResult();
	    return count > 0;
	}

	public boolean verificarCedulaExistente(String cedula) {
	    TypedQuery<Long> query = em.createQuery("SELECT COUNT(u) FROM Usuario u WHERE u.documento = :cedulaUsuario", Long.class)
	        .setParameter("cedulaUsuario", cedula);
	    Long count = query.getSingleResult();
	    return count > 0;
	}
	
	public boolean verificarNicknameExistente(String nickname) {
	    TypedQuery<Long> query = em.createQuery("SELECT COUNT(u) FROM Usuario u WHERE u.nickname = :nicknameUsuario", Long.class)
	        .setParameter("nicknameUsuario", nickname);
	    Long count = query.getSingleResult();
	    return count > 0;
	}


	
	
	
}
