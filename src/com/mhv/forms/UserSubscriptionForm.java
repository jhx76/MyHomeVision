package com.mhv.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jasypt.util.password.ConfigurablePasswordEncryptor;

import com.mhv.bean.User;
import com.mhv.dao.DAOException;
import com.mhv.dao.UserDAO;

public final class UserSubscriptionForm {
	private static final String ALGO_CHIFFREMENT = "SHA-256";
	public static final String FIELD_EMAIL = "email";
	public static final String FIELD_PASSWORD = "password";
	public static final String FIELD_CONFIRMATION = "confirmation";
	public static final String FIELD_LOGIN = "login";
	public static final String FIELD_ROLE = "role";
	public static final String ATT_ERRORS  = "errors";
	public static final String ATT_RESULT = "result";
	
	private String result;
    private Map<String, String> errorMap = new HashMap<String, String>();
	
    private UserDAO userDAO;
    
    public UserSubscriptionForm(UserDAO userDAO) {
    	this.userDAO = userDAO;
    }

    public User userSubscribe( HttpServletRequest request ) {
	    String email = getFieldValue(request, FIELD_EMAIL);
	    String password = getFieldValue(request, FIELD_PASSWORD);
	    String confirmation = getFieldValue(request, FIELD_CONFIRMATION);
	    String login = getFieldValue(request, FIELD_LOGIN);
	    String role = getFieldValue(request, FIELD_ROLE);
	    User user = new User();
	    try {
	    	processLogin(login, user);
	    	processEmail(email, user);
	    	processPassword(password, confirmation, user);
	    	processRole(role, user);
	    	if(errorMap.isEmpty()) {
	    		userDAO.create(user);
	    		result = "Utilisateur enregistré avec succès !";
	    	}
	    	else {
	    		result = "Échec de l'inscritpion.";
	    	}
	    }
	    catch(DAOException e) {
	    	result = "Échec de l'inscription : une erreur imprévue est survenue, merci de réessayer dans quelques instants.<br/>["+e.getMessage()+"]";
	    	e.printStackTrace();
	    }
	    return user;
	}
    
	public String getResult() { return result; }
	
	public Map<String, String> getErrorMap() { return errorMap; }	

	private void processEmail(String email, User user) {
	    try { emailFormatValidation(email); } 
	    catch(FormValidationException e) { setError(FIELD_EMAIL, e.getMessage()); }
	    user.setEmail(email);
	}
	
	private void emailFormatValidation(String email) throws FormValidationException {
		if ( email != null && email.trim().length() > 0 ) {
			if ( !email.matches( "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)" ) ) {
				throw new FormValidationException( "Merci de saisir une adresse mail valide." );
			}
			else if(userDAO.findByEmail(email) != null) {
				throw new FormValidationException("Cet adresse mail est déjà utilisée ! Choisissez en une autre !");
			}
		} 
		else {
			throw new FormValidationException( "Merci de saisir une adresse mail.");
		}
	}
	
	private void processPassword(String password, String confirmation, User user) {
		try { passwordFormatValidation(password, confirmation); }
		catch(FormValidationException e) { 
			setError(FIELD_PASSWORD, e.getMessage());
			setError(FIELD_CONFIRMATION, null);
		}
		/*
	     * Utilisation de la bibliothèque Jasypt pour chiffrer le mot de passe efficacement.
	     * L'algorithme SHA-256 est ici utilisé, avec par défaut un salage aléatoire et un grand nombre d'itérations de la fonction de hashage.
	     * La String retournée est de longueur 56 et contient le hash en Base64.
	     */
	    ConfigurablePasswordEncryptor passwordEncryptor = new ConfigurablePasswordEncryptor();
	    passwordEncryptor.setAlgorithm(ALGO_CHIFFREMENT);
	    passwordEncryptor.setPlainDigest( false );
	    String encryptedPassword = passwordEncryptor.encryptPassword(password);
	    user.setPassword(encryptedPassword);
	}

	private void passwordFormatValidation(String password, String confirmation) throws FormValidationException {
		if (password != null && password.trim().length() > 0 && confirmation != null && confirmation.trim().length() > 0) {
			if (!password.equals(confirmation)) {
				throw new FormValidationException("Les mots de passe entrés sont différents, merci de les saisir à nouveau.");
			} 
			else if (password.trim().length() < 6) {
				throw new FormValidationException("Les mots de passe doivent contenir au moins 3 caractères.");
			}
		} 
		else {
			throw new FormValidationException("Merci de saisir et confirmer votre mot de passe.");
		}
	}

	private void processLogin(String login, User user) {
		try { loginFormatValidation(login); }
		catch(FormValidationException e) { setError(FIELD_LOGIN, e.getMessage()); }
		user.setLogin(login);
	}
	
	private void loginFormatValidation(String userName) throws FormValidationException {
		if(userName != null && userName.trim().length() > 0) {
			if(userName.trim().length() < 3) {
				throw new FormValidationException("Le nom d'utilisateur doit contenir au minimum 3 caractères");
			}
			else if(userDAO.findByLogin(userName) != null) {
				throw new FormValidationException("Ce login est déjà utilisé. Choisissez en un autre !");
			}
		}
		else {
			throw new FormValidationException("Vous n'avez pas saisi de nom d'utilisateur");
		}
	}
	
	private void processRole(String role, User user) {
		try { roleFormatValidation(role); }
		catch(FormValidationException e) { setError(FIELD_ROLE, e.getMessage()); }
		user.setRole(role);
	}
	
	private void roleFormatValidation(String role) throws FormValidationException {
		
	}
	
	private void setError( String champ, String message ) {
	    errorMap.put( champ, message );
	}
	 
	private static String getFieldValue(HttpServletRequest request, String fieldName ) {
	    String value = request.getParameter( fieldName );
	    if ( value == null || value.trim().length() == 0 )
	        return null;
	    return value.trim();
	}
}
