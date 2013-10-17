package com.zigbus.device;


public class PwmDevice extends Device {

	public PwmDevice() {
		super();
	}
	
	public PwmDevice(Module module, int pin) {
		super(module, pin, ZigbusType.PWM_OUTPUT);
	}
}
