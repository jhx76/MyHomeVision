package com.mhv.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.mhv.bean.Pallier;
import com.mhv.dao.DAOException;
import com.mhv.dao.PallierDAO;

public class CreatePallierForm {
	public static final String FIELD_PALLIER_NAME = "pallierName";
	public static final String FIELD_PALLIER_DESCRIPTION = "pallierDescription";
	public static final String ATT_ERROR_MAP  = "errorMap";
	public static final String ATT_RESULT = "result";

	private String result;
	private Map<String, String> errorMap = new HashMap<String, String>();

	private PallierDAO pallierDAO;

	public CreatePallierForm(PallierDAO pallierDAO) {
		this.pallierDAO = pallierDAO;
	}

	public Pallier submitCreatePallier(HttpServletRequest request) {
		String pallierName = getFieldValue(request, FIELD_PALLIER_NAME);
		String pallierDescription = getFieldValue(request, FIELD_PALLIER_DESCRIPTION);
		Pallier pallier = new Pallier();
		try {
			processPallierName(pallierName, pallier);
			processPallierDescription(pallierDescription, pallier);
			if(errorMap.isEmpty()) {
				pallierDAO.create(pallier);
				result = "Pallier enregistré avec succès !";
			}
			else {
				result = "Échec de l'enregistrement du pallier !";
			}
		}
		catch(DAOException e) {
			result = "Échec de l'enregistrement : une erreur imprévue est survenue, merci de réessayer dans quelques instants.<br/>["+e.getMessage()+"]";
	    	e.printStackTrace();
		}
		return pallier;
	}

	private void processPallierDescription(String description, Pallier pallier) {
		pallier.setDescription(description);
	}

	private void processPallierName(String pallierName, Pallier pallier) {
		try { pallierNameFormatValidation(pallierName); }
		catch(FormValidationException e) { setError(FIELD_PALLIER_NAME, e.getMessage()); }
		pallier.setPallierName(pallierName);
	}

	private void pallierNameFormatValidation(String pallierName) throws FormValidationException {
		System.out.println("pallierName : "+pallierName);
		if(pallierName != null && !pallierName.isEmpty()) {
			if(pallierName.length() < 3) {
				throw new FormValidationException("Le nom d'un pallier doit au moins contenir 3 caractères !");
			}
		}
		else {
			throw new FormValidationException("Vous devez saisir un nom pour le nouveau pallier !");
		}
	}

	public void setError(String field, String message) {
		errorMap.put(field, message);
	}
	
	public String getResult() { 
		return result;
	}

	public Map<String, String> getErrorMap() {
		return errorMap;
	}
	
	private static String getFieldValue(HttpServletRequest request, String fieldName ) {
		String value = request.getParameter(fieldName);
		if (value == null || value.trim().length() == 0)
			return null;
		return value.trim();
	}

}
