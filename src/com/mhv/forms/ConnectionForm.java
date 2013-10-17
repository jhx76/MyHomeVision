package com.mhv.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.mhv.bean.User;

public class ConnectionForm {
	private static final String FIELD_LOGIN = "login";
	private static final String FIELD_PASSWORD = "password";
	private String result;
	private Map<String, String> errorMap = new HashMap<String, String>();
	
	public User userConnection(HttpServletRequest request) {
		String login = getFieldValue(request, FIELD_LOGIN);
		String password = getFieldValue(request, FIELD_PASSWORD);
		
		User user = new User();
		try {
			loginValidation(login);
		}
		catch(Exception e) {
			setError(FIELD_LOGIN, e.getMessage());
		}
		user.setLogin(login);
		
		try {
			passwordValidation(password);
		}
		catch(Exception e) {
			setError(FIELD_PASSWORD, e.getMessage());
		}
		user.setPassword(password);
		
		if(errorMap.isEmpty())
			result = "Bienvenue à vous, "+user.getLogin();
		else
			result = "Échec de la connexion !";
		return user;
	}
	
	private void passwordValidation(String password) throws Exception {
		if (password != null) {
            if (password.length() < 3)
                throw new Exception( "Le mot de passe doit contenir au moins 3 caractères." );
        } 
		else
            throw new Exception( "Merci de saisir votre mot de passe." );
	}
	
	private void loginValidation(String login) throws Exception {
		
	}
	
	private void emailValidation(String email) throws Exception {
		if ( email != null && !email.matches( "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)" ) ) {
            throw new Exception( "Merci de saisir une adresse mail valide." );
        }
	}
	
	private void setError(String fieldName, String message) {
		errorMap.put(fieldName, message);
	}
	
	private static String getFieldValue(HttpServletRequest request, String fieldName) {
		String value = request.getParameter(fieldName);
        if (value == null || value.trim().length() == 0)
            return null;
        return value;
	}
	
	public String getResult() { return result; }
	public Map<String, String> getErrorMap() { return errorMap; }
}
