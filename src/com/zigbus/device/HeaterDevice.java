package com.zigbus.device;


public class HeaterDevice extends Device {

	private int optPin = -1;

	public HeaterDevice() {
		super();
	}
	
	public HeaterDevice(Module module, int alterp, int alterm) {
		super(module, alterp, ZigbusType.HEATER);
		optPin = alterm;
	}
	
	
	public int getOptPin() {
		return optPin;
	}

	public void setOptPin(int optPin) {
		this.optPin = optPin;
	}
}
