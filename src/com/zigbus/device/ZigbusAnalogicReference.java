package com.zigbus.device;

public enum ZigbusAnalogicReference {

	DEFAULT			(0x00, "DEFAULT"),
	INTERNAL		(0x01, "INTERNAL"),
	INTERNAL_1v1	(0x02, "INTERNAL_1V1"),
	INTERNAL_2v56	(0x03, "INTERNAL_2V56"),
	EXTERNAL		(0x04, "EXTERNAL");
	
	private String string;
	private int value;
	
	private ZigbusAnalogicReference(int value, String string) {
		this.value = value;
		this.string = string;
	}
	
	public static ZigbusAnalogicReference fromString(String str) {
		for (ZigbusAnalogicReference ref : ZigbusAnalogicReference.values()) {
			if(ref.toString().equals(str))
				return ref;
		}
		return null;
	}

	public static ZigbusAnalogicReference fromInteger(int i) {
		for (ZigbusAnalogicReference ref : ZigbusAnalogicReference.values()) {
			if(ref.value() == i)
				return ref;
		}
		return null;
	}
	
	public String toString() { return string; }
	public int value() { return value; }
	public String toBinaryString() {
		String rslt = Integer.toString(value, 2); 
		while(rslt.length() < 3) rslt = "0"+rslt;
		return rslt;
	}	
}
