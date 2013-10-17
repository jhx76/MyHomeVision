package com.zigbus.device;


public class ServoDevice extends Device {

	private int optPin = -1;
	
	public ServoDevice() {
		super();
	}
	
	public ServoDevice(Module module, int command, int puissance) {
		super(module, command, ZigbusType.SERVO);
		setOptPin(puissance);
	}

	public int getOptPin() { return optPin; }

	public void setOptPin(int optPin) { this.optPin = optPin; }
	
}
