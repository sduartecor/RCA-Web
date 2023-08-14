package com.capa3Persistencia.entities.Usuario;

import java.io.Serializable;
import java.lang.Long;
import java.lang.String;

import javax.enterprise.context.SessionScoped;
import javax.persistence.*;
import javax.validation.constraints.*;

import com.capa3Persistencia.entities.Rol;
import com.capa3Persistencia.entities.Ciudad.Ciudad;


/**
 * Entity implementation class for Entity: Usuario
 *
 */
@SessionScoped
@Entity
@Table(name =  "USUARIO")
public class UsuarioEmpresa implements Serializable {	   

	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_USUARIO")
	private Long id;
	private String nombre;
	private String documento;
	private String apellido;
	private Rol rol;
	private String telefono;
	private String domicilio;
	@OneToOne
	private Ciudad ciudad;
	

	private String mail;

	private String nickname;
	private String clave;
	private boolean activo;


	public UsuarioEmpresa() {
		super();
	}
	
	public UsuarioEmpresa(String nombre, String apellido, String nickname, String telefono,Rol rol, String domicilio, String mail, String documento) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.nickname = nickname;
		this.telefono = telefono;
		this.domicilio = domicilio;
		this.mail = mail;
		this.rol = rol;
		this.documento = documento;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public Boolean getActivo() {
		return activo;
	}

	

	

	public Ciudad getCiudad() {
		return ciudad;
	}

	public void setCiudad(Ciudad ciudad) {
		this.ciudad = ciudad;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getTelefono() {
		return telefono;
		
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
		
	}

	public String getDomicilio() {
		return domicilio;
		
	}

	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
		
	}

	public String getMail() {
		return mail;
		
	}

	public void setMail(String mail) {
		this.mail = mail;
		
	}

	


	
	
		
}
