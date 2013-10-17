package com.xpl;

public class xPL_Address {
	private String vendorId = "";
	private String deviceId = "";
	private String instanceId = "";

	public xPL_Address() {

	}

	public xPL_Address(String vendorId, String deviceId, String instanceId) {
		this.setVendorId(vendorId);
		this.setDeviceId(deviceId);
		this.setInstanceId(instanceId);
	}

	public xPL_Address(xPL_Address other) {
		this.vendorId = other.vendorId;
		this.deviceId = other.deviceId;
		this.instanceId = other.instanceId;
	}

	public xPL_Address(String text) {
		if(text == "*") {
			vendorId = "*";
		}
		else {
			vendorId = text.substring(0, text.indexOf("-"));
			deviceId = text.substring(text.indexOf("-")+1, text.indexOf("."));
			instanceId = text.substring(text.indexOf(".")+1);
		}
	}

	public static xPL_Address broadcast() {
		return new xPL_Address("*", "", "");
	}

	public boolean isBroadcast() {
		if(vendorId == "*")
			return true;
		return false;
	}

	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String toString() {
		if(this.isBroadcast())
			return "*";
		return (vendorId+"-"+deviceId+"."+instanceId);
	}

	public boolean equals(xPL_Address other) {
		if(this.vendorId.equals(other.vendorId) 
				&& this.deviceId.equals(other.deviceId) 
				&& this.instanceId.equals(other.instanceId))
			return true;
		return false;
	}

	public boolean equalsIgnoreCase(xPL_Address other) {
		if(this.vendorId.equalsIgnoreCase(other.vendorId)
				&& this.deviceId.equalsIgnoreCase(other.deviceId)
				&& this.instanceId.equalsIgnoreCase(other.instanceId))
			return true;
		return false;
	}
}
