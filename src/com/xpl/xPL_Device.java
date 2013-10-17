package com.xpl;

import java.util.ArrayList;

public class xPL_Device {
	
	private Long id;
	private xPL_Address address = null;
	private String expires = ""; //timestamp
	private int interval = 0; //interval (minutes)
	private boolean configType = false;
	private boolean configDone = false;
	private boolean waitingConfig = false;
	private boolean suspended = false;
	private ArrayList<ConfigurableItem> configItemList = new ArrayList<ConfigurableItem>();
	
//	public xPL_Device(xPL_Address address, String expires, int interval, boolean configType, 
//			boolean configDone, boolean waitingConfig, boolean suspended) {
//		this.setAddress(address);
//		this.setExpires(expires); 
//		this.setInterval(interval);
//		this.setConfigType(configType);
//		this.setConfigDone(configDone);
//		this.setWaitingConfig(waitingConfig);
//		this.setSuspended(suspended);
//	}

	/**
	 * @return the suspended
	 */
	public boolean isSuspended() {
		return suspended;
	}

	/**
	 * @param suspended the suspended to set
	 */
	public void setSuspended(boolean suspended) {
		this.suspended = suspended;
	}

	/**
	 * @return the waitingConfig
	 */
	public boolean isWaitingConfig() {
		return waitingConfig;
	}

	/**
	 * @param waitingConfig the waitingConfig to set
	 */
	public void setWaitingConfig(boolean waitingConfig) {
		this.waitingConfig = waitingConfig;
	}

	/**
	 * @return the configDone
	 */
	public boolean isConfigDone() {
		return configDone;
	}

	/**
	 * @param configDone the configDone to set
	 */
	public void setConfigDone(boolean configDone) {
		this.configDone = configDone;
	}

	/**
	 * @return the configType
	 */
	public boolean isConfigType() {
		return configType;
	}

	/**
	 * @param configType the configType to set
	 */
	public void setConfigType(boolean configType) {
		this.configType = configType;
	}

	/**
	 * @return the interval
	 */
	public int getInterval() {
		return interval;
	}

	/**
	 * @param interval the interval to set
	 */
	public void setInterval(int interval) {
		this.interval = interval;
	}

	/**
	 * @return the expires
	 */
	public String getExpires() {
		return expires;
	}

	/**
	 * @param expires the expires to set
	 */
	public void setExpires(String expires) {
		this.expires = expires;
	}

	/**
	 * @return the address
	 */
	public xPL_Address getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(xPL_Address address) {
		this.address = address;
	}

	/**
	 * @return the configItemList
	 */
	public ArrayList<ConfigurableItem> getConfigItemList() {
		return configItemList;
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
}
