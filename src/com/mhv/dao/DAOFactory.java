package com.mhv.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

public class DAOFactory {
	private static final String FILE_PROPERTIES = "/com/mhv/dao/dao.properties";

	private static final String PROPERTY_URL = "url";
	private static final String PROPERTY_DRIVER = "driver";
	private static final String PROPERTY_USER_NAME = "username";
	private static final String PROPERTY_PASSWORD = "password";

	/*package*/ BoneCP connectionPool = null;

	/* package */DAOFactory( BoneCP connectionPool ) {
        this.connectionPool = connectionPool;
    }

	/*
	 * Méthode chargée de récupérer les informations de connexion à la base de
	 * données, charger le driver JDBC et retourner une instance de la Factory
	 */
	public static DAOFactory getInstance() throws DAOConfigurationException {
		Properties properties = new Properties();
		String url;
		String driver;
		String nomUtilisateur;
		String motDePasse;
		BoneCP connectionPool = null;

		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream fichierProperties = classLoader.getResourceAsStream(FILE_PROPERTIES);

		if ( fichierProperties == null ) {
			throw new DAOConfigurationException( "Le fichier properties " + FILE_PROPERTIES + " est introuvable." );
		}

		try {
			properties.load( fichierProperties );
			url = properties.getProperty( PROPERTY_URL );
			driver = properties.getProperty( PROPERTY_DRIVER );
			nomUtilisateur = properties.getProperty( PROPERTY_USER_NAME );
			motDePasse = properties.getProperty( PROPERTY_PASSWORD);
		} catch ( IOException e ) {
			throw new DAOConfigurationException( "Impossible de charger le fichier properties " + FILE_PROPERTIES, e );
		}

		try {
			Class.forName( driver );
		} catch ( ClassNotFoundException e ) {
			throw new DAOConfigurationException( "Le driver est introuvable dans le classpath.", e );
		}

		try {
            BoneCPConfig config = new BoneCPConfig();
            config.setJdbcUrl(url);
            config.setUsername(nomUtilisateur);
            config.setPassword(motDePasse);
            config.setMinConnectionsPerPartition(5);
            config.setMaxConnectionsPerPartition(10);
            config.setPartitionCount(2);
            connectionPool = new BoneCP(config);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOConfigurationException("Erreur de configuration du pool de connexions.", e);
        }
        DAOFactory instance = new DAOFactory(connectionPool);
		return instance;
	}

	/* Méthode chargée de fournir une connexion à la base de données */
    /* package */Connection getConnection() throws SQLException {
        return connectionPool.getConnection();
    }

	/*
	 * Méthodes de récupération de l'implémentation des différents DAO (un seul
	 * pour le moment)
	 */
	public UserDAO getUserDAO() {
		return new UserDAOImpl(this);
	}
	
	public PallierDAO getPallierDAO() {
		return new PallierDAOImpl(this);
	}

}
