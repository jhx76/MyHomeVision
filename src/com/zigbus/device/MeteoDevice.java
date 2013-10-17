package com.zigbus.device;


public class MeteoDevice extends Device {

	private ZigbusMeteoType meteoType = null;
	
	public MeteoDevice() {
		super();
	}
	
	public MeteoDevice(Module module, int pin, ZigbusMeteoType meteoType) {
		super(module, pin, ZigbusType.METEO);
		this.meteoType = meteoType;
	}
	
	public ZigbusMeteoType getMeteoType() { return meteoType; }
	
	public void setMeteoType(ZigbusMeteoType meteoType) { this.meteoType = meteoType; }	
}
