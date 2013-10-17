package com.mhv.dao;

import java.util.List;

import com.mhv.bean.User;

public interface UserDAO {
	public void create(User utilisateur) throws DAOException;
	public void modify(User modifiedUser) throws DAOException;
	public void deleteUser(User userToDelete) throws DAOException;
	public void deleteUserById(Long id) throws DAOException;
	public User findByLogin(String login) throws DAOException;
	public User findByEmail(String email) throws DAOException;
	public User findById(Long id) throws DAOException;
	public List<User> getUserList() throws DAOException;
}
