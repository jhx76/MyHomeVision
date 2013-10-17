package com.mhv.dao;

import java.util.List;

import com.mhv.bean.Device;
import com.xpl.xPL_Address;

public interface DeviceDAO {
	public void create(Device device) throws DAOException, IllegalArgumentException;
	public void deleteById(Long id) throws DAOException;
	public void modify(Device device) throws DAOException;
	public List<Device> getDeviceList() throws DAOException;
	public Device getDeviceById(Long id) throws DAOException;
	public Device getDeviceByxPLId(xPL_Address address) throws DAOException;
}
