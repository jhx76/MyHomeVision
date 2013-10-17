package com.mhv.dao;

import java.util.List;

import com.xpl.xPL_Address;
import com.xpl.xPL_Device;

public interface xPLDeviceDAO {
	
	public void create(xPL_Device device) throws IllegalArgumentException, DAOException;
	public void deleteById(Long id) throws DAOException;
	public void modify(xPL_Device device) throws DAOException;
	public xPL_Device getDeviceById(Long id) throws DAOException;
	public xPL_Device getDeviceByAddress(xPL_Address address) throws DAOException;
	public List<xPL_Device> getDeviceList() throws DAOException;
	
}
