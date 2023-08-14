package com.capa3Persistencia.entities.Formulario;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Size;

import com.capa3Persistencia.entities.Usuario.Usuario;

/**
 * Entity implementation class for Entity: Formulario
 *
 */
@Entity

public class Formulario implements Serializable {

	
	private static final long serialVersionUID = 1L;

	public Formulario() {
		super();
	}
	
	@Id
	@SequenceGenerator(name = "SEQ_FORMULARIO", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_FORMULARIO")
	private Long id;
	
	//@ManyToMany (mappedBy = "formulario")
	//private Set<Casilla> casillas;
	
	@Column(unique=false, nullable=false)
	@Size(max = 30, message = "Max. 30 caracteres")
	private String nombre;
	
	@Size(max = 300, message = "Max. 300 caracteres")
	private String resumen;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "DATO_FORMULARIO")
	@MapKeyColumn(name = "VALOR")
	@Column(name = "EXISTE")
	private Map<String, Boolean> campos = new HashMap<String, Boolean>();
	
	
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

	public Map<String, Boolean> getCampos() {
		return campos;
	}

	public void setCampos(Map<String, Boolean> campos) {
		this.campos = campos;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public String getResumen() {
		return resumen;
	}

	public void setResumen(String resumen) {
		this.resumen = resumen;
	}

	

	
	
	
	

	
	

	
	
	
	
	
   
}
