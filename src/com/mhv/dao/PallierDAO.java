package com.mhv.dao;

import java.util.List;

import com.mhv.bean.Pallier;

public interface PallierDAO {

	public void create(Pallier pallier) throws DAOException, IllegalArgumentException;
	public void deleteById(Long id) throws DAOException;
	public void modify(Pallier pallier) throws DAOException;
	public Pallier getPallierById(Long id) throws DAOException;
	public Pallier getPallierByName(String name) throws DAOException;
	public List<Pallier> getPallierList() throws DAOException;
	
}
