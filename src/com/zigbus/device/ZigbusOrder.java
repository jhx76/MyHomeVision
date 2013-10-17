package com.zigbus.device;

/**
 *	00000	OFF(0%)
 *	00001	ON(100%)
 *	00010	Toogle
 *	00011	Servo
 *	00100	Text
 *	00101	PWM
 *	00110	Heater
 *	00111	State
 *	01000	Serial
 *	01001	Remote
 *	01010	Ping
 *	01011	Temperature
 *	...values reserved for future use
 *	11110	Configure
 *	11111	Init
 */
public enum ZigbusOrder {
	OFF			( 0x00, "OFF", 6, -1),
	ON			( 0x01, "ON", 6, -1), 
	TOOGLE		( 0x02, "TOOGLE", 6, -1), 
	SERVO		( 0x03, "SERVO", 13, 20),
	TEXT		( 0x04, "TEXT", -1, -1),
	PWM			( 0x05, "PWM", 5, -1),
	HEATER		( 0x06, "HEATER", 8, 15),
	STATE		( 0x07, "STATE", -1, -1 ),
	SERIAL		( 0x08, "SERIAL", -1, -1 ),
	REMOTE		( 0x09, "REMOTE", -1, -1 ),
	PING		( 0x0A, "PING", -1, -1 ),
	METEO		( 0x0B, "METEO", 9, -1),
	ACK			( 0x1D, "ACK", -1, -1 ),
	CONFIGURE	( 0x1E, "CONFIGURE", -1, -1),
	INIT		( 0x1F, "INIT", -1, -1);

	private int value;
	private String stringValue = "";
	private int mainPinIndex = -1;
	private int optPinIndex = -1;	

	ZigbusOrder(int value, String stringValue, int mainPinIndex, int optPinIndex) {
		this.value = value;
		this.stringValue = stringValue;
		this.mainPinIndex = mainPinIndex;
		this.optPinIndex = optPinIndex;
	}
	
	public int mainPinIndex() { return mainPinIndex; }

	public int optPinIndex() { return optPinIndex; }
	
	public int value() { return value; }

	public String toString() { return stringValue; }

	public static ZigbusOrder fromString(String str) {
		for (ZigbusOrder order : ZigbusOrder.values()) {
			if(order.toString().equals(str))
				return order;
		}
		return null;
	}
	
	public static ZigbusOrder fromInteger(int i) {
		for (ZigbusOrder order : ZigbusOrder.values()) {
			if(order.value() == i)
				return order;
		}
		return null;
	}
}
