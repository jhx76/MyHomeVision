package com.xpl;

import java.util.ArrayList;

/**
 * 
 * @author jhx
 *
 */
public class ConfigurableItem {

	private String name = "";
	private String type = "";
	private int number = 0;
	private ArrayList<String> values = new ArrayList<String>();
	
	/**
	 * Constructor
	 * @param name
	 * @param type
	 * @param number
	 */
	public ConfigurableItem(String name, String type, int number) {
		this.name = name;
		this.type = type;
		this.number = number;
	}

	/**
	 * Add a value to the value list
	 * @param value
	 */
	public void addValue(String value) {
		values.add(value);
	}
	
	/**
	 * @param index
	 * @return the value stored at the specified index
	 */
	public String getValueAt(int index) {
		return values.get(index);
	}
	
	/**
	 * @return the number of values stored
	 */
	public int getCountValues() {
		return values.size();
	}
	
	/**
	 * Remove the value of the value list at the specified index
	 * @param index
	 */
	public void removeValueAt(int index) {
		values.remove(index);
	}	

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}


	/**
	 * @return the number
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * @param number the number to set
	 */
	public void setNumber(int number) {
		this.number = number;
	}


	/**
	 * @return the values
	 */
	public ArrayList<String> getValues() {
		return values;
	}	
}
