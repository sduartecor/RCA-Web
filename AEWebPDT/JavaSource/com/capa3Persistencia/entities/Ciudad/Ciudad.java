package com.capa3Persistencia.entities.Ciudad;

import java.io.Serializable;


import javax.persistence.*;

import com.capa3Persistencia.entities.Departamento.Departamento;

/**
 * Entity implementation class for Entity: Ciudad
 *
 */
@Entity

public class Ciudad implements Serializable {

	
	private static final long serialVersionUID = 1L;

	public Ciudad() {
		super();
	}
   
	@Id
	@SequenceGenerator(name = "SEQ_CIUDAD", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CIUDAD")
	private Long id;

	@Column(length = 40, nullable=false)
	private String nombre;
	
	@OneToOne
	private Departamento departamento;

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

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	@Override
	public String toString() {
		return nombre+" ("+departamento+")";
	}
	
	
	
	
	
	
}
