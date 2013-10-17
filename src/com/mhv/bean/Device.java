package com.mhv.bean;

import com.xpl.xPL_Device;

public class Device {

	private Long id;
	private xPL_Device xplDevice = null;
	private com.zigbus.device.Device zigbusDevice = null;
	private String configurationStatus;
	private boolean registered;
	private String description;
	private int posX;
	private int posY;
	private String iconFilePath;
	
	/**
	 * @return the configurationStatus
	 */
	public String getConfigurationStatus() {
		return configurationStatus;
	}

	/**
	 * @param configurationStatus the configurationStatus to set
	 */
	public void setConfigurationStatus(String configurationStatus) {
		this.configurationStatus = configurationStatus;
	}

	/**
	 * @return the registered
	 */
	public boolean isRegistered() {
		return registered;
	}

	/**
	 * @param registered the registered to set
	 */
	public void setRegistered(boolean registered) {
		this.registered = registered;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

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

	/**
	 * @return the xplDevice
	 */
	public xPL_Device getXplDevice() {
		return xplDevice;
	}

	/**
	 * @param xplDevice the xplDevice to set
	 */
	public void setXplDevice(xPL_Device xplDevice) {
		this.xplDevice = xplDevice;
	}
	
	public String toString() {
		return "";
	}

	/**
	 * @return the zigbusDevice
	 */
	public com.zigbus.device.Device getZigbusDevice() {
		return zigbusDevice;
	}

	/**
	 * @param zigbusDevice the zigbusDevice to set
	 */
	public void setZigbusDevice(com.zigbus.device.Device zigbusDevice) {
		this.zigbusDevice = zigbusDevice;
	}

	/**
	 * @return the posY
	 */
	public int getPosY() {
		return posY;
	}

	/**
	 * @param posY the posY to set
	 */
	public void setPosY(int posY) {
		this.posY = posY;
	}

	/**
	 * @return the posX
	 */
	public int getPosX() {
		return posX;
	}

	/**
	 * @param posX the posX to set
	 */
	public void setPosX(int posX) {
		this.posX = posX;
	}

	/**
	 * @return the iconFilePath
	 */
	public String getIconFilePath() {
		return iconFilePath;
	}

	/**
	 * @param iconFilePath the iconFilePath to set
	 */
	public void setIconFilePath(String iconFilePath) {
		this.iconFilePath = iconFilePath;
	}
	
}
