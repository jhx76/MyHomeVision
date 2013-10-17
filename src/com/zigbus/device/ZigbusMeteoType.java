package com.zigbus.device;

public enum ZigbusMeteoType {

	DHT11	( 0x01, "DHT11" ),
	LM35DZ	( 0x02, "LM35DZ" );

	private int value;
	private String stringValue;

	ZigbusMeteoType(int value, String stringValue) {
		this.value = value;
		this.stringValue = stringValue;
	}

	public int value() { return value; }
	public String toString() { return stringValue; }
	
	public static ZigbusMeteoType fromString(String str) {
		for (ZigbusMeteoType type : ZigbusMeteoType.values()) {
			if(type.toString().equals(str))
				return type;
		}
		return null;
	}

	public static ZigbusMeteoType fromInteger(int i) {
		for (ZigbusMeteoType type : ZigbusMeteoType.values()) {
			if(type.value() == i)
				return type;
		}
		return null;
	}
	
	public String toBinaryString() {
		String rslt = Integer.toString(value, 2);
		while(rslt.length() < 3) rslt = "0"+rslt;
		return rslt;
	}

}
