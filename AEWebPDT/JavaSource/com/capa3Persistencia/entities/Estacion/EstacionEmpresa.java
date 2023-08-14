package com.capa3Persistencia.entities.Estacion;


	
	import java.io.Serializable;
	import java.lang.Long;
	import java.lang.String;

	import javax.enterprise.context.SessionScoped;
	import javax.persistence.*;

import com.capa3Persistencia.entities.Ciudad.Ciudad;
import com.capa3Persistencia.entities.Departamento.Departamento;





	/**
	 * Entity implementation class for Entity: Usuario
	 *
	 */
	@SessionScoped
	@Entity
	@Table(name =  "ESTACION")
	public class EstacionEmpresa implements Serializable {	   

		private static final long serialVersionUID = 1L;

		
		@Id
		@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_ESTACION")
		private Long id;
		private String nombre;
		private String descripcion;
		private Double longitud;
		private Double latitud;
		
		@OneToOne
		private Ciudad ciudad;
		
		@OneToOne
		private Departamento departamento;
		
		private boolean activo;
		
		public EstacionEmpresa() {
			super();
		}

		public EstacionEmpresa(String nombre, String descripcion, Double longitud, Double latitud) {
			super();
			this.nombre = nombre;
			this.descripcion = descripcion;
			this.longitud = longitud;
			this.latitud = latitud;
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

		public void setActivo(boolean activo) {
			this.activo = activo;
		}

		public Boolean getActivo() {
			return activo;
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
