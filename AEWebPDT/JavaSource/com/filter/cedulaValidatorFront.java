package com.filter;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.bean.GestionUsuarioBean;
import com.capa2LogicaNegocio.GestionUsuarioService;
import com.capa3Persistencia.DAO.UsuariosEmpresaDAO;
import com.capa3Persistencia.entities.Usuario.Usuario;
import com.capa3Persistencia.exception.PersistenciaException;

@ManagedBean
@SessionScoped
@FacesValidator("cedulaValidator")
public class cedulaValidatorFront implements Validator {
	
	
	private String mno;
	
	private ValidationCedula verificar = new ValidationCedula();
	

	public String getMno() {
		return mno;
	}

	public void setMno(String mno) {
		this.mno = mno;
	}


	public cedulaValidatorFront() {
	}
	


	@Override
	public void validate(FacesContext fc, UIComponent uic, Object obj)
			throws ValidatorException {

		String model = (String) obj;
		
	
		if (!verificar.validateCi(model)) {
			
			
			FacesMessage msg = new FacesMessage(
					"CEDULA INVALIDA -  Intente nuevamente");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);

			throw new ValidatorException(msg);
			
		} 
		
		
		
		

	}
	


}
