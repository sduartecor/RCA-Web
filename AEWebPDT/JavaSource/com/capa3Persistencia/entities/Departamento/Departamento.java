package com.capa3Persistencia.entities.Departamento;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Departamento
 *
 */

@SessionScoped
@Entity
@NamedQuery(name = "departamentos.todos", query = "SELECT d FROM Departamento d")
public class Departamento implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	

	public Departamento() {
		super();
	}
	
	@Id
	@SequenceGenerator(name = "SEQ_DEPARTAMENTO", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DEPARTAMENTO")
	private Long id;
	
	@Column(length = 40, nullable=false)
	String nombre;

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

	
	
	
	
	
	
	
   
}
