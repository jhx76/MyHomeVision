package com.xpl;

public class xPL_NameValuePair  {
	private String name = "";
	private String value = "";
	
	public xPL_NameValuePair() {
		
	}
	
	public xPL_NameValuePair(String name, String value) {
		this.name = name;
		this.value = value;
	}
	
	public xPL_NameValuePair(xPL_NameValuePair other) {
		this.name = other.name;
		this.value = other.value;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public boolean equalsIgnoreCase(xPL_NameValuePair other) {
		if(this.name.equalsIgnoreCase(other.name) && this.value.equalsIgnoreCase(other.value))
			return true;
		return false;
	}
	
	public boolean equals(xPL_NameValuePair other) {
		if(this.name.equals(other.name) && this.value.equals(other.value))
			return true;
		return false;
	}
	
	public String toString() {
		return (name+"="+value);
	}
	

}
