package com.zigbus.device;


public class NumericOutputDevice extends Device {
	
	public NumericOutputDevice() {
		super();
	}
	
	public NumericOutputDevice(Module module, int mainModulePin) {
		super(module, mainModulePin, ZigbusType.NUMERIC_OUTPUT);
	}
	
}
