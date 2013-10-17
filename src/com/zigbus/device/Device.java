package com.zigbus.device;


public class Device {
	
	private Long id;
	private int mainPin = -1;
	private Module module = null;
	private ZigbusType zigbusType = null;
	
	public Device() { }
	
	public Device(Module module, int pin, ZigbusType zigbusType) {
		this.module = module;
		this.mainPin = pin;
		this.zigbusType = zigbusType;
	}

	public int getMainModulePin() { return mainPin; }

	public void setMainModulePin(int mainModulePin) { this.mainPin = mainModulePin; }

	public Module getModule() { return module; }

	public void setModule(Module module) { this.module = module; }

	public ZigbusType getZigbusType() { return zigbusType; }

	public void setZigbusType(ZigbusType zigbusType) { this.zigbusType = zigbusType; }

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
}
