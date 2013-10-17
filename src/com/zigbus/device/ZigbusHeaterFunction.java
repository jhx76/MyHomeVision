package com.zigbus.device;

public enum ZigbusHeaterFunction {

	/*
000	   HG      	0x00
001	   Arret   	0x01
010	   Eco     	0x02
011	  reserved 	0x03
100	  reserved 	0x04
101	 Confort-2 	0x05
110	 Confort-1 	0x06
111	 Confort   	0x07
	 */

	//[hg|stop|eco|comfort|comfort1|comfort2]
	HG			(0x00, "HG"),
	STOP		(0x01, "STOP"),
	ECO			(0x02, "ECO"),
	//values reserved for future use
	COMFORT2	(0x05, "COMFORT2"),
	COMFORT1	(0x06, "COMFORT1"),
	COMFORT		(0x07, "COMFORT");

	private String strValue;
	private int value;

	private ZigbusHeaterFunction(int value, String strValue) {
		this.value = value;
		this.strValue = strValue;
	}

	public static ZigbusHeaterFunction fromString(String str) {
		for (ZigbusHeaterFunction func : ZigbusHeaterFunction.values()) {
			if(func.toString().equals(str))
				return func;
		}
		return null;
	}

	public static ZigbusHeaterFunction fromInteger(int i) {
		for (ZigbusHeaterFunction func : ZigbusHeaterFunction.values()) {
			if(func.value() == i)
				return func;
		}
		return null;
	}

	public String toString() {
		return strValue;
	}

	public int value() {
		return value;
	}
}
