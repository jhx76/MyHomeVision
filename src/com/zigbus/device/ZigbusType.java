package com.zigbus.device;

/**
 * Undef				0	00000
 * Sortie Num			1	00001
 * Entree Num			2	00010
 * Sortie PWM			3	00011
 * Entree Analogique	4	00100
 * Lampe				5	00101
 * Chauffage			6	00110
 * Temperature			7	00111
 * Servo				8	01000
 * Token				9	01001
 * Remote				10	01010
 * Serie				11	01011
 */
public enum ZigbusType {
	UNDEF			( 0x00, "UNDEF" ),
	NUMERIC_OUTPUT	( 0x01, "NUMERIC_OUTPUT" ),
	NUMERIC_INPUT	( 0x02, "NUMERIC_INPUT" ),
	PWM_OUTPUT		( 0x03, "PWM_OUTPUT" ),
	ANALOGIC_INPUT	( 0x04, "ANALOGIC_INPUT" ),
	LIGHT			( 0x05, "LIGHT" ),
	HEATER			( 0x06, "HEATER" ),
	METEO		 	( 0x07, "METEO" ),
	SERVO			( 0x08, "SERVO" ),
	TOKEN			( 0x09, "TOKEN" ),
	REMOTE			( 0x0A, "REMOTE" ),
	SERIAL			( 0x0B, "SERIAL" ),
	GATE			( 0x0C, "GATE" ),
	MOTOR			( 0x0D, "MOTOR" ),
	
	//Ne pas utiliser ce type comme type d'appareil. Il est juste utilisé dans les 
	//trames de configuration d'adresse...
	ADDRESS			( 0xFF, "ADDRESS");
	private int value;
	
	private String stringValue = "";
	
	ZigbusType(int value, String stringValue) {
		this.value = value;
		this.stringValue = stringValue;
	}
	
	public int value() { return value; }
	
	public String toString() { return stringValue; }
	
	public static ZigbusType fromString(String str) {
		for (ZigbusType type : ZigbusType.values()) {
		    if(type.toString().equals(str))
		    	return type;
		}
		return null;
	}
	
	public String toBinaryString() {
		String rslt = Integer.toString(value, 2);
		while(rslt.length() < 5) rslt = "0"+rslt;
		return rslt;
	}
}
