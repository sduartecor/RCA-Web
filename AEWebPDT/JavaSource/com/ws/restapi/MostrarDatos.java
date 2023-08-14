package com.ws.restapi;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.capa3Persistencia.DAO.DepartamentoEmpresaDAO;
import com.capa3Persistencia.DAO.UsuariosEmpresaDAO;
import com.capa3Persistencia.entities.Departamento.Departamento;
import com.capa3Persistencia.entities.Usuario.UsuarioEmpresa;

/**
 * Servlet implementation class CargarDatos
 */
@WebServlet("/MostrarDatos")
public class MostrarDatos extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@EJB
	UsuariosEmpresaDAO usuariosEmpresaDAO;
	
	@EJB
	DepartamentoEmpresaDAO departamentoEmpresaDAO;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MostrarDatos() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath()+"\n");
		PrintWriter out = response.getWriter();
		
		try {
			 List<UsuarioEmpresa> listaUsuarios = usuariosEmpresaDAO.buscarUsuarios();
			 for(UsuarioEmpresa e: listaUsuarios ) {
				 out.println("Usuario:"+ e.getId()+ " Nombre:" + e.getNombre() + "Activo: " + e.getActivo() + "Rol: " + e.getRol());
			 } 
			
			
		}catch(Exception e) {
			out.println("No se creo el usuario:"+ e.getClass().getSimpleName()+"-"+e.getMessage());
		}
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
