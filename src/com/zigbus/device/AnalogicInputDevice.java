package com.zigbus.device;


public class AnalogicInputDevice extends Device {

	public AnalogicInputDevice() {
		super();
	}
	
	public AnalogicInputDevice(Module module, int pin) {
		super(module, pin, ZigbusType.ANALOGIC_INPUT);
	}
	
}
