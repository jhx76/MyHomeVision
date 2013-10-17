package com.xpl;

import java.util.ArrayList;

public class xPL_Message {
	
	public enum MessageType {
		COMMAND,
		TRIGGER,
		STATUS,
		ANY,
		UNKNOWN
	}
	private MessageType messageType = MessageType.UNKNOWN;
	private int hop = 1;
	private xPL_Address sourceAddress = null;
	private xPL_Address targetAddress = null;
	private String messageSchema = "";
	private String messageClass = "";	
	private ArrayList<xPL_NameValuePair> nvlist = new ArrayList<xPL_NameValuePair>();

	public xPL_Message() {
	
	}

	public int getHop() { return hop; }

	public void setHop(int hop) { this.hop = hop; }

	public xPL_Address getTargetAddress() { return targetAddress; }

	public void setTargetAddress(xPL_Address targetAddress) { this.targetAddress = targetAddress; }

	public xPL_Address getSourceAddress() { return sourceAddress; }

	public void setSourceAddress(xPL_Address sourceAddress) { this.sourceAddress = sourceAddress; }

	public String getMessageSchema() { return messageSchema; }

	public void setMessageSchema(String messageSchema) { this.messageSchema = messageSchema; }

	public String getMessageClass() { return messageClass; }

	public void setMessageClass(String messageClass) { this.messageClass = messageClass; }

	public ArrayList<xPL_NameValuePair> getNvlist() { return nvlist; }

	public void setNvlist(ArrayList<xPL_NameValuePair> nvlist) { this.nvlist = nvlist; }

	public boolean hasNamedValue(String name) { 
		for(int i = 0; i < nvlist.size(); i++) {
			if(nvlist.get(i).getName().equals(name)) {
				return true;
			}
		}
		return false;
	}
	
	public String getNamedValue(String name) throws xPL_Exception {
		for(int i = 0; i < nvlist.size(); i++) {
			if(nvlist.get(i).getName().equals(name)) {
				return nvlist.get(i).getValue();
			}
		}
		throw new xPL_Exception("Name-Value pair named "+name+" not found in the message");
	}
	
	public String getNamedValuePluralIndex(String name, int index) throws xPL_Exception {
		for(int i = 0, nvindex = 0; i < nvlist.size(); i++) {
			if(nvlist.get(i).getName().equals(name)) {
				nvindex++;
				if(nvindex == index) {
					return nvlist.get(i).getValue();
				}
			}
		}
		throw new xPL_Exception("Name-Value pair named "+name+"(index"+index+") not found in the message");
	}
	
	public int getNamedValueInt(String name) throws xPL_Exception {
		for(int i = 0; i < nvlist.size(); i++) {
			if(nvlist.get(i).getName().equals(name)) {
				try {
					return Integer.parseInt(nvlist.get(i).getValue());
				}
				catch(NumberFormatException exception) {
					throw new xPL_Exception("Name-Value pair named "+name+" has not an integer value !");
				}
			}
		}
		throw new xPL_Exception("Name-Value pair named "+name+" not found in the message");
	}
	
	public int getNamedValueIntPluralIndex(String name, int index) throws xPL_Exception {
		for(int i = 0, nvindex = 0; i < nvlist.size(); i++) {
			if(nvlist.get(i).getName().equals(name)) {
				nvindex++;
				if(nvindex == index) {
					try {
						return Integer.parseInt(nvlist.get(i).getValue());
					}
					catch(NumberFormatException exception) {
						throw new xPL_Exception("Name-Value pair named "+name+"(index"+index+") has not an integer value !");
					}
				}
			}
		}
		throw new xPL_Exception("Name-Value pair named "+name+"(index"+index+") not found in the message");
	}
	
	public boolean getNamedValueBool(String name) throws xPL_Exception {
		for(int i = 0; i < nvlist.size(); i++) {
			if(nvlist.get(i).getName().equals(name)) {
					if(nvlist.get(i).getValue().equalsIgnoreCase("true")) return true;
					else if(nvlist.get(i).getValue().equalsIgnoreCase("false")) return false;
					else throw new xPL_Exception("Name-Value pair named "+name+" has not a boolean value !");
			}
		}
		throw new xPL_Exception("Name-Value pair named "+name+" not found in the message !");
	}
	
	public boolean getNamedValueBoolPluralIndex(String name, int index) throws xPL_Exception {
		for(int i = 0, nvindex = 0; i < nvlist.size(); i++) {
			if(nvlist.get(i).getName().equals(name)) {
				nvindex++;
				if(nvindex == index) {
					if(nvlist.get(i).getValue().equalsIgnoreCase("true")) return true;
					else if(nvlist.get(i).getValue().equalsIgnoreCase("false")) return false;
					else throw new xPL_Exception("Name-Value pair named "+name+"(index"+index+") has not a boolean value !");
				}
			}
		}
		throw new xPL_Exception("Name-Value pair named "+name+"(index"+index+") not found in the message");
	}
	
	public void setNamedValue(String name, String value) {
		for(int i = 0; i < nvlist.size(); i++) {
			if(nvlist.get(i).getName().equals(name)) {
				nvlist.get(i).setValue(value);
				return; 
			}
		}
		nvlist.add(new xPL_NameValuePair(name, value));
	}
	
	public void setNamedValuePluralIndex(String name, String value, int index) {
		for(int i = 0, nvindex = 0; i < nvlist.size(); i++) {
			if(nvlist.get(i).getName().equals(name)) {
				nvindex++;
				if(nvindex == index) {
					nvlist.get(i).setValue(value);
					return;
				}
			}
		}
		nvlist.add(new xPL_NameValuePair(name, value));
	}
	
	public void setNamedValueInt(String name, int value) {
		for(int i = 0; i < nvlist.size(); i++) {
			if(nvlist.get(i).getName().equals(name)) {
				nvlist.get(i).setValue(""+value);
			}
		}
		nvlist.add(new xPL_NameValuePair(name, ""+value));
	}
	
	public void setNamedValueIntPluralIndex(String name, int value, int index) {
		for(int i = 0, nvindex = 0; i < nvlist.size(); i++) {
			if(nvlist.get(i).getName().equals(name)) {
				nvindex++;
				if(nvindex == index) {
					nvlist.get(i).setValue(Integer.toString(value));
					return;
				}
			}
		}
		nvlist.add(new xPL_NameValuePair(name, Integer.toString(value)));
	}
	
	public void setNamedValueBool(String name, boolean value) {
		for(int i = 0; i < nvlist.size(); i++) {
			if(nvlist.get(i).getName().equals(name)) {
				nvlist.get(i).setValue(value ? "true" : "false");
			}
		}
		nvlist.add(new xPL_NameValuePair(name, value ? "true" : "false"));
	}
	
	public void setNamedValueBoolPluralIndex(String name, boolean value, int index) {
		for(int i = 0, nvindex = 0; i < nvlist.size(); i++) {
			if(nvlist.get(i).getName().equals(name)) {
				nvindex++;
				if(nvindex == index) {
					nvlist.get(i).setValue(value ? "true" : "false");
					return;
				}
			}
		}
		nvlist.add(new xPL_NameValuePair(name, value ? "true" : "false"));
	}
	
	public int count(String name) {
		int count = 0;
		for(int i = 0; i < nvlist.size(); i++) {
			if(nvlist.get(i).getName().equals(name))
				count++;
		}
		return count;
	}
	
	public int count() {
		return nvlist.size();
	}
	
	public void removeNamedValue(String name) throws xPL_Exception {
		for(int i = 0; i < nvlist.size(); i++) {
			if(nvlist.get(i).getName().equals(name)) {
				nvlist.remove(i);
				return;
			}
		}
		throw new xPL_Exception("Name-Value pair named "+name+" not found in the message !");
	}
	
	public void removeNamedValuePluralIndex(String name, int index) throws xPL_Exception {
		for(int i = 0, nvindex = 0; i < nvlist.size(); i++) {
			if(nvlist.get(i).getName().equals(name)) {
				nvindex++;
				if(nvindex == index) {
					nvlist.remove(i);
					return;
				}
			}
		}
		throw new xPL_Exception("Name-Value pair named "+name+"(index"+index+") not found in the message !");
	}
	
	public void clearNamedValues() { nvlist.clear(); }
	
	public MessageType getMessageType() { return this.messageType; }
	
	public void setMessageType(MessageType messageType) { this.messageType = messageType; }
	
	public String toString() {
		String rslt = "xpl-";
		switch(messageType) {
		case COMMAND:
			rslt += "cmnd\n{\n";
			break;
		case STATUS:
			rslt += "stat\n{\n";
			break;
		case TRIGGER:
			rslt += "trig\n{\n";
			break;
		default:
			return "";
		}
		rslt += "hop="+hop+"\n";
		rslt += "source="+sourceAddress+"\n";
		rslt += "target="+targetAddress+"\n";
		rslt += "}\n";
		rslt += messageSchema+"."+messageClass+"\n";
		rslt += "{\n";
		for(int i = 0; i < this.nvlist.size(); i++) {
			rslt += nvlist.get(i)+"\n";
		}
		rslt += "}\n";
		
		return rslt;
	}
}
