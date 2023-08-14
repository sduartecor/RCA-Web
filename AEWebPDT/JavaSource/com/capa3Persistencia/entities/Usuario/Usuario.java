package com.capa3Persistencia.entities.Usuario;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.capa3Persistencia.entities.Rol;
import com.capa3Persistencia.entities.Ciudad.Ciudad;




/**
 * Entity implementation class for Entity: Usuario
 *
 */
@SessionScoped
@Entity
@NamedQuery(name = "usuarios.todos", query = "SELECT u FROM Usuario u")
public class Usuario implements Serializable {

	
	private static final long serialVersionUID = 1L;

	public Usuario() {
		super();
	}
	
	@Id
	@SequenceGenerator(name = "SEQ_USUARIO", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_USUARIO")
	private Long id;
	
	
	@NotNull(message="Debe ingresar una cedula")
	private String documento;
	
	@NotNull(message="Debe ingresar un nombre")
	@Size(max = 40, message = "El nombre tiene que ser maximo 30 de caracteres")
	private String nombre;
	
	@NotNull(message="Debe ingresar un apellido")
	@Size(max = 40, message = "El apellido tiene que ser maximo 30 de caracteres")
	private String apellido;
	
	
	@Pattern(regexp="[^@]+@\\w+\\.com", message="El correo debe contener: @ y .com")
	private String mail;
	
	@NotNull(message="Debe ingresar una usuario")
	@Size(min = 8, max = 30, message = "Min. 8 caracteres / Max. 25 caracteres")
	private String nickname;
	
	@NotNull(message="Debe ingresar una contraseña")
	@Size(min = 8, max = 30, message = "Min. 8 caracteres / Max. 25 caracteres")
	@Pattern(regexp = "^[a-zA-Z0-9]+$", message = "La contraseña solo puede contener caracteres y números")
	private String clave;
	
	@NotNull(message= "Debe ingresar un docimicilio")
	private String domicilio;
	
	@NotNull(message= "Debe seleccionar una ciudad")
	@OneToOne
	private Ciudad ciudad;
	
	
	@NotNull(message="Debe ingresar un telefono")
	private String telefono;
	
	
	@NotNull(message="Debe seleccionar un rol")
	private Rol rol;
	

	
	@NotNull
	private boolean activo;
	


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}
	


	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}
	

	public String getNickname() {
		return nickname;
	}

	

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}


	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public boolean getActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public Ciudad getCiudad() {
		return ciudad;
	}

	public void setCiudad(Ciudad ciudad) {
		this.ciudad = ciudad;
	}

	
	
	
	

	
	
	
	
	
	
   
}
