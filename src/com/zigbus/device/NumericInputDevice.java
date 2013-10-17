package com.zigbus.device;


public class NumericInputDevice extends Device {

	public NumericInputDevice() {
		super();
	}
	
	public NumericInputDevice(Module module, int pin) {
		super(module, pin, ZigbusType.NUMERIC_INPUT);
	}
	
}
