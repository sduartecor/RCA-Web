package com.capa3Persistencia.entities;

import java.io.Serializable;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.*;
import javax.validation.constraints.Size;

import com.capa3Persistencia.entities.Ciudad.Ciudad;
import com.capa3Persistencia.entities.Estacion.Estacion;
import com.capa3Persistencia.entities.Formulario.Formulario;
import com.capa3Persistencia.entities.Usuario.Usuario;

/**
 * Entity implementation class for Entity: Registro
 *
 */
@Entity

public class Registro implements Serializable {

	
	private static final long serialVersionUID = 1L;

	public Registro() {
		super();
	}
   
	@Id
	@SequenceGenerator(name = "SEQ_REGISTRO", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_REGISTRO")
	private Long id;
	
	@ManyToOne
	private Formulario formulario; 
	
	
	@ManyToOne
	private Estacion estacion;
	
	@OneToOne
	private Ciudad ciudad;
	
	private String usuario;
	
	
	@Column(length = 40, nullable=false)
	private String metodo;
	

	@Column(length = 40)
	private java.util.Date fecha;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "DATOS_REGISTRO")
	@MapKeyColumn(name = "VALOR")
	@Column(name = "VALOR_NUMERICO")
	private Map<String, Double> campos = new HashMap<String, Double>();
	
	private boolean activo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}



	public Estacion getEstacion() {
		return estacion;
	}

	public void setEstacion(Estacion estacion) {
		this.estacion = estacion;
	}

	public String getMetodo() {
		return metodo;
	}

	public void setMetodo(String metodo) {
		this.metodo = metodo;
	}

	

	public java.util.Date getFecha() {
		return fecha;
	}

	public void setFecha(java.util.Date fecha) {
		this.fecha = fecha;
	}

	public Formulario getFormulario() {
		return formulario;
	}

	public void setFormulario(Formulario formulario) {
		this.formulario = formulario;
	}

	public Map<String, Double> getCampos() {
		return campos;
	}

	public void setCampos(Map<String, Double> campos) {
		this.campos = campos;
	}

	public boolean isActivo() {
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

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	

	
	
	
	
	
	
	
	
	
	
}
