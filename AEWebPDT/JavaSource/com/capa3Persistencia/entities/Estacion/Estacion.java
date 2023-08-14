package com.capa3Persistencia.entities.Estacion;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.capa3Persistencia.entities.Ciudad.Ciudad;
import com.capa3Persistencia.entities.Departamento.Departamento;



/**
 * Entity implementation class for Entity: Casilla
 *
 */
@Entity

public class Estacion implements Serializable {

	
	private static final long serialVersionUID = 1L;

	public Estacion() {
		super();
	}
	
	@Id
	@SequenceGenerator(name = "SEQ_ESTACION", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ESTACION")
	private Long id;
	
	@Column(nullable=false)
	@Size(max = 40, message = "Max. 40 caracteres")
	private String nombre;
	
	@Size(max = 200, message = "Max. 200 caracteres")
	private String descripcion;
	
	@OneToOne
	private Departamento departamento;
	
	@OneToOne
	private Ciudad ciudad;
	
	@Column(length = 80)
	private Double longitud;
	
	@Column(length = 80)
	private Double latitud;
	
	@NotNull
	private boolean activo;

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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Ciudad getCiudad() {
		return ciudad;
	}

	public void setCiudad(Ciudad ciudad) {
		this.ciudad = ciudad;
	}

	public boolean getActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	

	public Double getLongitud() {
		return longitud;
	}

	public void setLongitud(Double longitud) {
		this.longitud = longitud;
	}

	public Double getLatitud() {
		return latitud;
	}

	public void setLatitud(Double latitud) {
		this.latitud = latitud;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}
	
	

	
	
	

	
}
