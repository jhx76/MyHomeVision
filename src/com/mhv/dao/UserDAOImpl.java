package com.mhv.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mhv.bean.User;

public class UserDAOImpl implements UserDAO {

	private DAOFactory daoFactory;

	private static final String SQL_SELECT_BY_LOGIN = "SELECT user.id as id" +
													  ", user.login as login" +
													  ", user.password as password" +
													  ", r.profile as role" +
													  ", user.email as email" +
													  ", user.date_inscription as date_inscription" +
													  " FROM T_USER user" +
													  " INNER JOIN T_ROLE r ON user.et_profile=r.id" +
													  " WHERE login = ?";
	
	private static final String SQL_SELECT_BY_EMAIL = "SELECT user.id as id" +
													  ", user.login as login" +
													  ", user.password as password" +
													  ", r.profile as role" +
													  ", user.email as email" +
													  ", user.date_inscription as date_inscription" +
													  " FROM T_USER user" +
													  " INNER JOIN T_ROLE r ON user.et_profile=r.id" +
													  " WHERE email = ?";
	
	private static final String SQL_SELECT_BY_ID = "SELECT user.id as id" +
												  ", user.login as login" +
												  ", user.password as password" +
												  ", r.profile as role" +
												  ", user.email as email" +
												  ", user.date_inscription as date_inscription" +
												  " FROM T_USER user" +
												  " INNER JOIN T_ROLE r ON user.et_profile=r.id" +
												  " WHERE user.id = ?";
	
	private static final String SQL_INSERT = "INSERT INTO T_USER (login, password, email, et_profile, date_inscription) " +
											 "VALUES(" +
											 " ?" +
											 ", ?" +
											 ", ?" +
											 ", (SELECT id FROM T_ROLE WHERE profile=?)" +
											 ", NOW()" +
											 " )";
	
	private static final String SQL_SELECT_ALL = "SELECT user.id as id" +
											  ", user.login as login" +
											  ", user.password as password" +
											  ", r.profile as role" +
											  ", user.email as email" +
											  ", user.date_inscription as date_inscription" +
											  " FROM T_USER user" +
											  " INNER JOIN T_ROLE r ON user.et_profile=r.id";
	
	private static final String SQL_DELETE_BY_ID = "DELETE FROM T_USER" +
												   " WHERE id=?";
	
	UserDAOImpl(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	private static User map(ResultSet resultSet) throws SQLException {
	    User user = new User();
	    user.setId(resultSet.getLong("id"));
	    user.setEmail( resultSet.getString("email"));
	    user.setPassword( resultSet.getString("password"));
	    user.setLogin(resultSet.getString("login"));
	    user.setDateInscription(resultSet.getTimestamp("date_inscription"));
	    user.setRole(resultSet.getString("role"));
	    return user;
	}
	
	@Override
	public void create(User user) throws DAOException {
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet generatedValues = null;
		
		try {
			conn = daoFactory.getConnection();
			preparedStatement = DAOUtils.initPreparedQuery(conn, SQL_INSERT, true, user.getLogin(), user.getPassword(), user.getEmail(), user.getRole());
			int status = preparedStatement.executeUpdate();
			if(status == 0) {
				throw new DAOException("Échec de la création de l'utilisateur, aucune ligne ajoutée dans la table.");
			}
			generatedValues = preparedStatement.getGeneratedKeys();
			if(generatedValues.next()) {
				user.setId(generatedValues.getLong(1));
			}
			else {
				throw new DAOException("Échec de la création de l'utilisateur en base, aucun ID auto-généré retourné.");
			}
		}
		catch(SQLException e) {
			throw new DAOException(e);
		}
		finally {
			DAOUtils.release(generatedValues, preparedStatement, conn);
		}
	}

	@Override
	public void modify(User modifiedUser) throws IllegalArgumentException, DAOException {

	}

	@Override
	public void deleteUser(User userToDelete) throws IllegalArgumentException, DAOException {

	}
	
	@Override
	public void deleteUserById(Long id) throws DAOException {
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		try {
			conn = daoFactory.getConnection();
			preparedStatement = DAOUtils.initPreparedQuery(conn, SQL_DELETE_BY_ID, false, id);
			int status = preparedStatement.executeUpdate();
			if(status == 0) {
				throw new DAOException("Impossible de supprimer l'utilisateur... (corrupted ID)");
			}
		}
		catch(SQLException e) {
			throw new DAOException(e);
		}
		finally {
			DAOUtils.release(preparedStatement, conn);
		}
	}

	@Override
	public User findByLogin(String login) throws DAOException {
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    User user = null;
	 
	    try {
	        connexion = daoFactory.getConnection();
	        preparedStatement = DAOUtils.initPreparedQuery(connexion, SQL_SELECT_BY_LOGIN, false, login);
	        resultSet = preparedStatement.executeQuery();
	        if (resultSet.next()) {
	            user = map(resultSet);
	        }
	    } 
	    catch (SQLException e) {
	        throw new DAOException(e);
	    } 
	    finally {
	        DAOUtils.release(resultSet, preparedStatement, connexion);
	    }
	    return user;
	}

	public User findByEmail(String email) throws DAOException {
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		User user = null;
		try {
			conn = daoFactory.getConnection();
			preparedStatement = DAOUtils.initPreparedQuery(conn, SQL_SELECT_BY_EMAIL, false, email);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next())
				user = map(resultSet);
		}
		catch(SQLException e) {
			throw new DAOException(e);
		}
		finally {
			DAOUtils.release(resultSet, preparedStatement, conn);
		}
		return user;
	}
	
	@Override
	public User findById(Long id) throws DAOException {
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		User user = null;
		try {
			conn = daoFactory.getConnection();
			preparedStatement = DAOUtils.initPreparedQuery(conn, SQL_SELECT_BY_ID, false, id);
			result = preparedStatement.executeQuery();
			if(result.next())
				user = map(result);
		}
		catch(SQLException e) {
			throw new DAOException(e);
		}
		finally {
			DAOUtils.release(result, preparedStatement, conn);
		}
		return user;
	}

	@Override
	public List<User> getUserList() throws DAOException {
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		List<User> userList = new ArrayList<User>();
		try {
			conn = daoFactory.getConnection();
			preparedStatement = DAOUtils.initPreparedQuery(conn, SQL_SELECT_ALL, false);
			result = preparedStatement.executeQuery();
			while(result.next()) {
				userList.add(map(result));
			}
		}
		catch(SQLException e) {
			throw new DAOException(e);
		}
		finally {
			DAOUtils.release(result, preparedStatement, conn);
		}
		return userList;
	}

}
