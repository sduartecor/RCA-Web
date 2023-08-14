package com.ldap;

import java.util.HashMap;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

public class ConexionLDAP {

	private static String ldapServerUrl = "ldap://26.137.187.98:389";
	private static String ldapUsuario;
	private static String ldapPasswordUsuario;
	private static String ldapDominio = "@utec.edu.uy";
	private static HashMap<String, String> datosUsuario = new HashMap<>();


	public static HashMap<String, String> getDatosUsuario() {
		return datosUsuario;
	}

	public static void setDatosUsuario(HashMap<String, String> datosUsuario) {
		ConexionLDAP.datosUsuario = datosUsuario;
	}

	public static String getLdapUsuario() {
		return ldapUsuario;
	}

	public static void setLdapCorreoUsuario(String ldapCorreoUsuario) {
		ConexionLDAP.ldapUsuario = ldapCorreoUsuario;
	}

	public static String getLdapPasswordUsuario() {
		return ldapPasswordUsuario;
	}

	public static void setLdapPasswordUsuario(String ldapPasswordUsuario) {
		ConexionLDAP.ldapPasswordUsuario = ldapPasswordUsuario;
	}

	static DirContext ldapContext;

	public static HashMap<String, String> autenticar() throws NamingException
	{
		Hashtable<String, String> ldapEnv = new Hashtable<String, String>(11);
		ldapEnv.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		ldapEnv.put(Context.PROVIDER_URL,  ldapServerUrl);
		ldapEnv.put(Context.SECURITY_AUTHENTICATION, "simple");
		ldapEnv.put(Context.SECURITY_PRINCIPAL, ldapUsuario+ldapDominio);
		ldapEnv.put(Context.SECURITY_CREDENTIALS, ldapPasswordUsuario);

		try
		{
			ldapContext = new InitialDirContext(ldapEnv);
			System.out.println("Intentando conexion contra AD..");
			// --- DEVUELVE LISTA DE TODOS LOS USUARIOS ---
			// creo el objeto searchcontols        
			SearchControls searchCtls = new SearchControls();
			//Especifico los atributos que quiero recibir
			String returnedAtts[]={"sn","givenName", "samAccountName", "memberOf"};
			searchCtls.setReturningAttributes(returnedAtts);
			//Especifico el scope de la busqueda
			searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
			//Especifico el filtro 
			String searchFilter = "(&(objectClass=user))";
			//Especifico la base para la busqueda (En este caso busco en la OU definida para la App)
			//String searchBase = "ou=UTEC,dc=utec,dc=edu,dc=uy";
			String searchBase = "ou=Analisis,dc=utec,dc=edu,dc=uy";
			// Hago la busqueda
			NamingEnumeration<SearchResult> answer = ldapContext.search(searchBase, searchFilter, searchCtls);
			// Obtengo los resultados y los recorro hasta encontrar al usuario que ha intentado autenticarse
			while (answer.hasMoreElements())
			{
				SearchResult sr = (SearchResult)answer.next();
				Attributes attrs = sr.getAttributes();
				// parseo el usuario y el grupo de cada resultado
				String usuario = attrs.get("samAccountName").toString().split(":")[1].trim();
				//String grupo = attrs.get("memberOf").toString().split("memberOf: CN=")[1].split(",")[0];
				String grupo = attrs.get("memberOf").toString().split(", CN=")[1].split(",")[0];
				// busco al usuario ingresado
				if(usuario.equals(ldapUsuario)) {
					// si el usuario forma parte de alguno de los grupos de la app, guardo los datos y corto el loop
					if (grupo.equals("AppAdministradores") || grupo.equals("AppInvestigadores")) {
						System.out.println("Autenticado!");
						datosUsuario.put("usuario", usuario);
						datosUsuario.put("grupo", grupo);
						break;
					}

				}
			}
			
			ldapContext.close();
		}
		catch (Exception e)
		{
			System.out.println("error de autenticacion para: "+ldapUsuario);
		
			//System.out.println(" Search error: " + e);
			//e.printStackTrace();
			//System.exit(-1);
		}
		
		return datosUsuario;
	}
}
