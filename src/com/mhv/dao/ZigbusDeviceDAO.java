package com.mhv.dao;

import java.util.List;

import com.zigbus.device.Device;

public interface ZigbusDeviceDAO {

	public void create(Device device) throws IllegalArgumentException, DAOException;
	public void deleteById(Long id) throws DAOException;
	public void modify(Device device) throws DAOException;
	public void getDeviceById(Long id) throws DAOException;
	public List<Device> getDeviceListByModuleId() throws DAOException;
	public List<Device> getDeviceList() throws DAOException;
	
}
