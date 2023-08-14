package com.ws.restapi;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bean.GestionUsuarioBean;
import com.capa2LogicaNegocio.GestionUsuarioService;
import com.capa3Persistencia.DAO.CiudadEmpresaDAO;
import com.capa3Persistencia.DAO.DepartamentoEmpresaDAO;
import com.capa3Persistencia.DAO.EstacionEmpresaDAO;
import com.capa3Persistencia.DAO.FormularioEmpresaDAO;
import com.capa3Persistencia.DAO.RegistroEmpresaDAO;
import com.capa3Persistencia.DAO.UsuariosEmpresaDAO;
import com.capa3Persistencia.entities.Registro;
import com.capa3Persistencia.entities.Rol;
import com.capa3Persistencia.entities.Ciudad.Ciudad;
import com.capa3Persistencia.entities.Departamento.Departamento;
import com.capa3Persistencia.entities.Estacion.EstacionEmpresa;
import com.capa3Persistencia.entities.Formulario.Formulario;
import com.capa3Persistencia.entities.Usuario.UsuarioEmpresa;




/**
 * Servlet implementation class CargarDatos
 */
@WebServlet("/CargarDatos")
public class CargarDatos extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@EJB
	UsuariosEmpresaDAO usuariosEmpresaDAO;
	
	@EJB
	DepartamentoEmpresaDAO departamentoEmpresaDAO;
	
	@EJB
	CiudadEmpresaDAO ciudadEmpresaDAO;
	
	@EJB
	EstacionEmpresaDAO estacionEmpresaDAO;
	
	@EJB
	GestionUsuarioService gestionUsuarioService;
	
	@EJB
	FormularioEmpresaDAO formularioEmpresaDAO;
	
	@EJB
	RegistroEmpresaDAO registroDao;
	

	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CargarDatos() {
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
			if (gestionUsuarioService.seleccionarUsuarios().isEmpty()) {
				
			// creo el departamento 1 (Soriano)
			Departamento departamento1 = new Departamento();
			departamento1.setNombre("SORIANO");
			departamentoEmpresaDAO.crear(departamento1);
			
			// creo el departamento 2 (Montevideo)
			Departamento departamento2 = new Departamento();
			departamento2.setNombre("MONTEVIDEO");
			departamentoEmpresaDAO.crear(departamento2);
			
			// creo el departamento 3 (Durazno)
			Departamento departamento3 = new Departamento();
			departamento3.setNombre("DURAZNO");
			departamentoEmpresaDAO.crear(departamento3);
			
			// creo el departamento 4 (Canelones)
			Departamento departamento4 = new Departamento();
			departamento4.setNombre("CANELONES");
			departamentoEmpresaDAO.crear(departamento4);
			
			// creo el departamento 4 (Canelones)
			Departamento departamento5 = new Departamento();
			departamento5.setNombre("ARTIGAS");
			departamentoEmpresaDAO.crear(departamento5);
			
			// creo el departamento 4 (Canelones)
			Departamento departamento6 = new Departamento();
			departamento6.setNombre("CERRO LARGO");
			departamentoEmpresaDAO.crear(departamento6);
			
			// creo el departamento 4 (Canelones)
			Departamento departamento7 = new Departamento();
			departamento7.setNombre("COLONIA");
			departamentoEmpresaDAO.crear(departamento7);
			
			// creo el departamento 4 (Canelones)
			Departamento departamento8 = new Departamento();
			departamento8.setNombre("FLORES");
			departamentoEmpresaDAO.crear(departamento8);
			
			// creo el departamento 4 (Canelones)
			Departamento departamento9 = new Departamento();
			departamento9.setNombre("FLORIDA");
			departamentoEmpresaDAO.crear(departamento9);
			
			// creo el departamento 4 (Canelones)
			Departamento departamento10 = new Departamento();
			departamento10.setNombre("LAVALLEJA");
			departamentoEmpresaDAO.crear(departamento10);
			
			// creo el departamento 4 (Canelones)
			Departamento departamento11 = new Departamento();
			departamento11.setNombre("MALDONADO");
			departamentoEmpresaDAO.crear(departamento11);
			
			// creo el departamento 4 (Canelones)
			Departamento departamento12 = new Departamento();
			departamento12.setNombre("PAYSANDU");
			departamentoEmpresaDAO.crear(departamento12);
			
			// creo el departamento 4 (Canelones)
			Departamento departamento13 = new Departamento();
			departamento13.setNombre("RIO NEGRO");
			departamentoEmpresaDAO.crear(departamento13);
			
			// creo el departamento 4 (Canelones)
			Departamento departamento14 = new Departamento();
			departamento14.setNombre("RIVERA");
			departamentoEmpresaDAO.crear(departamento14);
			
			// creo el departamento 4 (Canelones)
			Departamento departamento15 = new Departamento();
			departamento15.setNombre("ROCHA");
			departamentoEmpresaDAO.crear(departamento15);
			
			// creo el departamento 4 (Canelones)
			Departamento departamento16 = new Departamento();
			departamento16.setNombre("SALTO");
			departamentoEmpresaDAO.crear(departamento16);
			
			// creo el departamento 4 (Canelones)
			Departamento departamento17 = new Departamento();
			departamento17.setNombre("SAN JOSE");
			departamentoEmpresaDAO.crear(departamento17);
			
			// creo el departamento 4 (Canelones)
			Departamento departamento18 = new Departamento();
			departamento18.setNombre("TACUAREMBO");
			departamentoEmpresaDAO.crear(departamento18);
			
			// creo el departamento 4 (Canelones)
			Departamento departamento19 = new Departamento();
			departamento19.setNombre("TREINTA Y TRES");
			departamentoEmpresaDAO.crear(departamento19);

			
		
			
			// creo una ciudad 1 (Mercedes) y la asigno al departamento 1 (Soriano)
			Ciudad ciudad1 = new Ciudad();
			ciudad1.setNombre("MERCEDES");
			ciudadEmpresaDAO.crear(ciudad1);
			ciudadEmpresaDAO.asignarDepartamento(1l, 1l);
			
			
			// creo una ciudad 3 (Montevideo) y la asigno al departamento 2 (Montevideo)
			Ciudad ciudad3 = new Ciudad();
			ciudad3.setNombre("MONTEVIDEO");
			ciudadEmpresaDAO.crear(ciudad3);
			ciudadEmpresaDAO.asignarDepartamento(2l, 2l);
			
			// creo una ciudad 4 (Villa del Carmen) y la asigno al departamento 3 (Durazno)
			Ciudad ciudad4 = new Ciudad();
			ciudad4.setNombre("DURAZNO");
			ciudadEmpresaDAO.crear(ciudad4);
			ciudadEmpresaDAO.asignarDepartamento(3l, 3l);
			
			
			// creo una ciudad 5 (Las Piedras) y la asigno al departamento 4 (Canelones)
			Ciudad ciudad5 = new Ciudad();
			ciudad5.setNombre("CANELONES");
			ciudadEmpresaDAO.crear(ciudad5);
			ciudadEmpresaDAO.asignarDepartamento(4l, 4l);
			
			// creo una ciudad 5 (Las Piedras) y la asigno al departamento 4 (Canelones)
			Ciudad ciudad6 = new Ciudad();
			ciudad6.setNombre("ARTIGAS");
			ciudadEmpresaDAO.crear(ciudad6);
			ciudadEmpresaDAO.asignarDepartamento(5l, 5l);
			
			// creo una ciudad 5 (Las Piedras) y la asigno al departamento 4 (Canelones)
			Ciudad ciudad7 = new Ciudad();
			ciudad7.setNombre("MELO");
			ciudadEmpresaDAO.crear(ciudad7);
			ciudadEmpresaDAO.asignarDepartamento(6l, 6l);
			
			// creo una ciudad 5 (Las Piedras) y la asigno al departamento 4 (Canelones)
			Ciudad ciudad8 = new Ciudad();
			ciudad8.setNombre("COLONIA");
			ciudadEmpresaDAO.crear(ciudad8);
			ciudadEmpresaDAO.asignarDepartamento(7l, 7l);
			
			// creo una ciudad 5 (Las Piedras) y la asigno al departamento 4 (Canelones)
			Ciudad ciudad9 = new Ciudad();
			ciudad9.setNombre("TRINIDAD");
			ciudadEmpresaDAO.crear(ciudad9);
			ciudadEmpresaDAO.asignarDepartamento(8l, 8l);
			
			// creo una ciudad 5 (Las Piedras) y la asigno al departamento 4 (Canelones)
			Ciudad ciudad10 = new Ciudad();
			ciudad10.setNombre("FLORIDA");
			ciudadEmpresaDAO.crear(ciudad10);
			ciudadEmpresaDAO.asignarDepartamento(9l, 9l);
			
			// creo una ciudad 5 (Las Piedras) y la asigno al departamento 4 (Canelones)
			Ciudad ciudad11 = new Ciudad();
			ciudad11.setNombre("MINAS");
			ciudadEmpresaDAO.crear(ciudad11);
			ciudadEmpresaDAO.asignarDepartamento(10l, 10l);
			
			// creo una ciudad 5 (Las Piedras) y la asigno al departamento 4 (Canelones)
			Ciudad ciudad12 = new Ciudad();
			ciudad12.setNombre("MALDONADO");
			ciudadEmpresaDAO.crear(ciudad12);
			ciudadEmpresaDAO.asignarDepartamento(11l, 11l);
			
			// creo una ciudad 5 (Las Piedras) y la asigno al departamento 4 (Canelones)
			Ciudad ciudad13 = new Ciudad();
			ciudad13.setNombre("PAYSANDU");
			ciudadEmpresaDAO.crear(ciudad13);
			ciudadEmpresaDAO.asignarDepartamento(12l, 12l);
			
			// creo una ciudad 5 (Las Piedras) y la asigno al departamento 4 (Canelones)
			Ciudad ciudad14 = new Ciudad();
			ciudad14.setNombre("FRAY BENTOS");
			ciudadEmpresaDAO.crear(ciudad14);
			ciudadEmpresaDAO.asignarDepartamento(13l, 13l);
			
			// creo una ciudad 5 (Las Piedras) y la asigno al departamento 4 (Canelones)
			Ciudad ciudad15 = new Ciudad();
			ciudad15.setNombre("RIVERA");
			ciudadEmpresaDAO.crear(ciudad15);
			ciudadEmpresaDAO.asignarDepartamento(14l, 14l);
			
			// creo una ciudad 5 (Las Piedras) y la asigno al departamento 4 (Canelones)
			Ciudad ciudad16 = new Ciudad();
			ciudad16.setNombre("ROCHA");
			ciudadEmpresaDAO.crear(ciudad16);
			ciudadEmpresaDAO.asignarDepartamento(15l, 15l);
			
			// creo una ciudad 5 (Las Piedras) y la asigno al departamento 4 (Canelones)
			Ciudad ciudad17 = new Ciudad();
			ciudad17.setNombre("SALTO");
			ciudadEmpresaDAO.crear(ciudad17);
			ciudadEmpresaDAO.asignarDepartamento(16l, 16l);
			
			// creo una ciudad 5 (Las Piedras) y la asigno al departamento 4 (Canelones)
			Ciudad ciudad18 = new Ciudad();
			ciudad18.setNombre("SAN JOSE");
			ciudadEmpresaDAO.crear(ciudad18);
			ciudadEmpresaDAO.asignarDepartamento(17l, 17l);
			
			// creo una ciudad 5 (Las Piedras) y la asigno al departamento 4 (Canelones)
			Ciudad ciudad19 = new Ciudad();
			ciudad19.setNombre("TACUAREMBO");
			ciudadEmpresaDAO.crear(ciudad19);
			ciudadEmpresaDAO.asignarDepartamento(18l, 18l);
			
			// creo una ciudad 5 (Las Piedras) y la asigno al departamento 4 (Canelones)
			Ciudad ciudad20 = new Ciudad();
			ciudad20.setNombre("TREINTA Y TRES");
			ciudadEmpresaDAO.crear(ciudad20);
			ciudadEmpresaDAO.asignarDepartamento(19l, 19l);
			
			//
			Ciudad ciudad21 = new Ciudad();
			ciudad21.setNombre("SARANDI DEL YI");
			ciudadEmpresaDAO.crear(ciudad21);
			ciudadEmpresaDAO.asignarDepartamento(20l, 3l);
			
			//
			Ciudad ciudad22 = new Ciudad();
			ciudad22.setNombre("PASO DE LOS TOROS");
			ciudadEmpresaDAO.crear(ciudad21);
			ciudadEmpresaDAO.asignarDepartamento(21l, 18l);
			
			// creo una ciudad 1 (Mercedes) y la asigno al departamento 1 (Soriano)
			Ciudad ciudad23 = new Ciudad();
			ciudad23.setNombre("DOLORES");
			ciudadEmpresaDAO.crear(ciudad23);
			ciudadEmpresaDAO.asignarDepartamento(22l, 1l);
			
			// creo una ciudad 5 (Las Piedras) y la asigno al departamento 4 (Canelones)
			Ciudad ciudad24 = new Ciudad();
			ciudad24.setNombre("VERGARA");
			ciudadEmpresaDAO.crear(ciudad24);
			ciudadEmpresaDAO.asignarDepartamento(23l, 19l);
			
			//
			Ciudad ciudad25 = new Ciudad();
			ciudad25.setNombre("BLANQUILLO");
			ciudadEmpresaDAO.crear(ciudad25);
			ciudadEmpresaDAO.asignarDepartamento(24l, 3l);
			
			
			List<Departamento> listaDepartamento = departamentoEmpresaDAO.obtenerTodos();
			
			EstacionEmpresa e;
			e=new EstacionEmpresa("SOR_MERCEDES01","ESTACION MERCEDES 01",0.0,0.0);
			e.setActivo(true);
			e.setDepartamento(listaDepartamento.get(0));
			estacionEmpresaDAO.agregarEstacion(e);
			estacionEmpresaDAO.asignarCiudad(1l, 1l);
			
			e=new EstacionEmpresa("SOR_DOLORES01","ESTACION DOLORES 01",0.0,0.0);
			e.setActivo(true);
			e.setDepartamento(listaDepartamento.get(1));
			estacionEmpresaDAO.agregarEstacion(e);
			estacionEmpresaDAO.asignarCiudad(2l, 1l);
			
			e=new EstacionEmpresa("MONTEVIDEO01","ESTACION MONTEVIDEO 01",0.0,0.0);
			e.setActivo(true);
			e.setDepartamento(listaDepartamento.get(2));
			estacionEmpresaDAO.agregarEstacion(e);
			estacionEmpresaDAO.asignarCiudad(3l, 2l);

			e=new EstacionEmpresa("DUR_VILLAC01","ESTACION VILLA DEL CARMEN 01",0.0,0.0);
			e.setActivo(true);
			e.setDepartamento(listaDepartamento.get(3));
			estacionEmpresaDAO.agregarEstacion(e);
			estacionEmpresaDAO.asignarCiudad(4l, 3l);
			
			e=new EstacionEmpresa("CAN_LASPIEDRAS01","ESTACION LAS PIEDRAS 01",0.0,0.0);
			e.setActivo(true);
			e.setDepartamento(listaDepartamento.get(4));
			estacionEmpresaDAO.agregarEstacion(e);
			estacionEmpresaDAO.asignarCiudad(5l, 4l);	
			
			
			
			UsuarioEmpresa u;
			
			List<Ciudad> listaCiudades = ciudadEmpresaDAO.obtenerTodos();
			
		
					
			u=new UsuarioEmpresa("Joaquin","Garcia","joacojoaco",null,Rol.Aficionado,null, "joaco@gmail.com",null);
			u.setClave(gestionUsuarioService.encryptString("joacojoaco"));
			u.setActivo(true);
			u.setCiudad(listaCiudades.get(0));
			UsuarioEmpresa usuarioCreado = usuariosEmpresaDAO.agregarUsuario(u);
			out.println("Se creo el usuario:"+ usuarioCreado.getId()+" Nombre"+usuarioCreado.getNombre());
			
			u=new UsuarioEmpresa("Diego", "Machado","diegodiego","09299999",Rol.Investigador,"Calle 1234", "diego@gmail.com","2222222-2");
			u.setClave(gestionUsuarioService.encryptString("diegodiego"));
			u.setActivo(true);
			u.setCiudad(listaCiudades.get(1));
			usuarioCreado = usuariosEmpresaDAO.agregarUsuario(u);
			out.println("Se creo el usuario:"+ usuarioCreado.getId()+" Nombre"+usuarioCreado.getNombre());
			
			u=new UsuarioEmpresa("Ramiro", "Perez","ramiroramiro","09299999",Rol.Administrador,"Calle 1234", "ramiro@gmail.com", "3333333-3");
			u.setClave(gestionUsuarioService.encryptString("ramiroramiro"));
			u.setActivo(true);
			u.setCiudad(listaCiudades.get(2));
			usuarioCreado = usuariosEmpresaDAO.agregarUsuario(u);
			out.println("Se creo el usuario:"+ usuarioCreado.getId()+" Nombre"+usuarioCreado.getNombre());
		
			
			// creo un formulario con algunas casillas (formulario1)
			Formulario f1 = new Formulario();
			f1.setActivo(true);
			f1.setNombre("FORM1");
			Map<String, Boolean> camposf1 = new HashMap<String, Boolean>();
			camposf1.put("co2", true);
			camposf1.put("no2", false);
			camposf1.put("pm2,5", true);
			camposf1.put("pm10", true);
			camposf1.put("temperatura", false);
			camposf1.put("precipitacion", false);
			f1.setCampos(camposf1);
			formularioEmpresaDAO.crear(f1);
			
			// creo un formulario con otras casillas (formulario2)
			Formulario f2 = new Formulario();
			f2.setActivo(true);
			f2.setNombre("FORM2");
			Map<String, Boolean> camposf2 = new HashMap<String, Boolean>();
			camposf2.put("co2", false);
			camposf2.put("no2", true);
			camposf2.put("pm2,5", true);
			camposf2.put("pm10", false);
			camposf2.put("temperatura", true);
			camposf2.put("precipitacion", false);
			f2.setCampos(camposf2);
			formularioEmpresaDAO.crear(f2);
			
			// creo un formulario con todas las casillas (formulario3)
			Formulario f3 = new Formulario();
			f3.setActivo(true);
			f3.setNombre("FORM3");
			Map<String, Boolean> camposf3 = new HashMap<String, Boolean>();
			camposf3.put("co2", true);
			camposf3.put("no2", true);
			camposf3.put("pm2,5", true);
			camposf3.put("pm10", true);
			camposf3.put("temperatura", true);
			camposf3.put("precipitacion", true);
			f3.setCampos(camposf3);
			formularioEmpresaDAO.crear(f3);
			
			Map<String, Double> camposR1 = new HashMap<String, Double>();
			camposR1.put("co2", 45d);
			camposR1.put("no2", 25d);
			camposR1.put("pm2,5", 9d);
			camposR1.put("pm10", 1d);
			camposR1.put("temperatura", 1d);
			camposR1.put("precipitacion", 1d);

						
			
			Registro r1 = new Registro();
			r1.setActivo(true);
			String fechaStrR1="2021-11-5";  
			Date fechaR1 = Date.valueOf(fechaStrR1);
			r1.setUsuario("ramiroramiro");
			r1.setFecha(fechaR1);
			r1.setFormulario(formularioEmpresaDAO.encontrarFormulario("FORM1").get(0));
			r1.setMetodo("Manual");
			r1.setEstacion(estacionEmpresaDAO.obtenerTodoDos().get(0));
			r1.setCampos(camposR1);
			
			registroDao.crear(r1);
			
			
			
			
			RequestDispatcher rd = request.getRequestDispatcher("/login.xhtml");
			rd.forward(request, response);
			
			} else {
				
				RequestDispatcher rd = request.getRequestDispatcher("/login.xhtml");
				rd.forward(request, response);
			}
			
		}catch(Exception e) {
			out.println("No se creo el dato:"+ e.getClass().getSimpleName()+"-"+e.getMessage() + e.getStackTrace());
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
