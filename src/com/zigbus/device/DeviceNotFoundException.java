package com.zigbus.device;

public class DeviceNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;
	public DeviceNotFoundException() { super(); }
	public DeviceNotFoundException(String message) { super(message); }
}
