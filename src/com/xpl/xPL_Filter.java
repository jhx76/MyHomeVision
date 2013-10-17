package com.xpl;

public class xPL_Filter extends xPL_Message {
	public xPL_Filter() { 
		this.setMessageType(MessageType.ANY);
		this.setMessageSchema("*");
		this.setMessageClass("*");
	}
	
	public boolean match(xPL_Message message) { 
		
		if(this.getMessageType() != MessageType.ANY 
				&& this.getMessageType() != message.getMessageType()) 
			return false;
		
		if(!this.getMessageSchema().equals("*") 
				&& !this.getMessageSchema().equals(message.getMessageSchema())) 
			return false;
		
		if(this.getMessageSchema().equals(message.getMessageSchema()) 
				&& !this.getMessageClass().equals("*") 
				&& !this.getMessageClass().equals(message.getMessageClass()))
			return false;
		
		//TODO Source and target addresses match management ?
		
		return true;
	}	
}
