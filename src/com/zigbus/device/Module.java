package com.zigbus.device;

import java.util.ArrayList;

import com.xpl.xPL_Address;

public class Module {

	private String moduleId = ""; //Adresse littérale, exprimée en octal !
	private int nbNumericPin = -1;
	private int nbAnalogicPin = -1;
	private int zigbusNetworkId = 0; //default value
	private boolean alive = false;
	
	private ArrayList<Device> deviceList = new ArrayList<Device>();
	
	public Module() {
		
	}
	
	public Module(String moduleId, int nbNumericPin, int nbAnalogicPin) {
		this.moduleId = moduleId;
		this.nbNumericPin = nbNumericPin;
		this.nbAnalogicPin = nbAnalogicPin;
	}
	
	public Module(String moduleId, int nbNumericPin, int nbAnalogicPin, int zigbusNetworkId) {
		this.moduleId = moduleId;
		this.nbNumericPin = nbNumericPin;
		this.nbAnalogicPin = nbAnalogicPin;
		this.zigbusNetworkId = zigbusNetworkId;
	}
	
	public String getModuleId() { return moduleId; }
	
	public void setModuleId(String moduleId) { this.moduleId = moduleId; }
	
	public xPL_Address getXplAddress() { 
		String strNetwork = Integer.toString(this.zigbusNetworkId, 10);
		while(strNetwork.length() < 2) strNetwork = "0"+strNetwork;
		return new xPL_Address("zigbus", "module", strNetwork+":"+moduleId); 
	}
	
	public int getNbNumericPin() { return nbNumericPin; }
	
	public void setNbNumericPin(int nbNumericPin) { this.nbNumericPin = nbNumericPin; }
	
	public int getNbAnalogicPin() { return nbAnalogicPin; }
	
	public void setNbAnalogicPin(int nbAnalogicPin) { this.nbAnalogicPin = nbAnalogicPin; }
	
	public int getZigbusNetworkId() { return zigbusNetworkId; }
	
	public void setZigbusNetworkId(int zigbusNetworkId) { this.zigbusNetworkId = zigbusNetworkId; }

	public boolean isAlive() { return alive; }

	public void setAlive(boolean alive) { this.alive = alive; }

	public ArrayList<Device> getDeviceList() { return deviceList; }

	public void setDeviceList(ArrayList<Device> deviceList) { this.deviceList = deviceList; }	
	
	public void addDevice(Device device) {
		this.deviceList.add(device);
	}
	
	public void removeDevice(Device device) {
		for(int index = 0; index < deviceList.size(); index++) {
			if(deviceList.get(index) == device) {
				deviceList.remove(index);
				return;
			}
		}
	}
}
