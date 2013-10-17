package com.xpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Timer;
import java.util.TimerTask;

import com.xpl.xPL_Message.MessageType;

public class xPL_Service implements Runnable, xPL_MessageListener {

	public static final int BASE_XPL_PORT = 3865;

	public static final int MINIMUM_XPL_PORT = 35352;

	public static final int MAXIMUM_XPL_PORT = 35420;

	public enum ConnectionStatus {
		STAND_ALONE,
		VIA_HUB,
		DISCONNECTED
	}

	private Timer hbeatTimer = new Timer();

	private int hbeatInterval = 5; //5 minutes par defaut

	private ConnectionStatus connectionStatus = ConnectionStatus.DISCONNECTED;

	private boolean running = false;

	private DatagramSocket socket = null;

	private int listeningPort = -1;

	private xPL_Address xPLAddress = null;

	private boolean hubConfirmed = false;

	private String networkInterfaceName = "";

	private String networkIPAddress = "";

	private final Collection<xPL_MessageListener> listeners = new ArrayList<xPL_MessageListener>();

	private static String getLine(String source, int numline) {
		String str;
		BufferedReader reader = new BufferedReader(new StringReader(source));
		try {
			for(int i = 0; i <= numline && (str = reader.readLine()) != null; i++) {
				if(i == numline)
					return str;
			}
		} 
		catch(IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	public xPL_Service(xPL_Address address) {
		this.xPLAddress = address;
		this.addMessageListener(this);
	}

	public void connect() throws xPL_Exception {
		try {
			if(networkInterfaceName.isEmpty())
				throw new xPL_Exception("No interface name has been set... You should set it to \"eth0\" or \"wlan0\" before start the service !");
			this.setNetworkIPAddress(getUsedIPAddress(networkInterfaceName).toString().substring(1));
		
			socket = new DatagramSocket(BASE_XPL_PORT);
			listeningPort = BASE_XPL_PORT;
			socket.setBroadcast(true);
			this.connectionStatus = ConnectionStatus.STAND_ALONE;
			System.out.println("Connection success ! listening port : "+this.listeningPort+" !");	
			return;
		} catch (SocketException e) {  
			System.out.println("Unable to bind port "+BASE_XPL_PORT+"... Trying hub ports area...");
		}

		for(int tmpPort = MINIMUM_XPL_PORT; tmpPort <= MAXIMUM_XPL_PORT; tmpPort++) {
			try {
				socket = new DatagramSocket(tmpPort);
				listeningPort = tmpPort;
				this.connectionStatus = ConnectionStatus.VIA_HUB;
				socket.setBroadcast(true);
				System.out.println("Connection success ! listening port : "+this.listeningPort+" !");	
				return;
			} catch (SocketException e) {  
				System.out.println("Unable to bind port "+tmpPort+"... Trying another port...");
			}
		}
		throw new xPL_Exception("Cannot bind any UDP port to reach the xPL network... Connection aborted !");
	}

	@Override
	public void run() {
		setRunning(true);
		byte[] buf = new byte[512];
		DatagramPacket packet = new DatagramPacket(buf, buf.length);
		try {
			this.sendHeartbeatMessage();
		} 
		catch (SocketException e1) {
			// e1.printStackTrace();
			System.out.println(e1.getMessage());
		}
		while (isRunning()) {
			try {
				startHeartbeatTimerTask();
				socket.receive(packet);
				String receivedStringPacket = new String(packet.getData());
				this.fireMessageReceived(buildMessage(receivedStringPacket));
			} 
			catch (IOException | xPL_Exception e) {
				System.out.println(e.getMessage());
			}
		}

		try {
			this.sendEndHeartbeatMessage();
			this.socket.close();
		} 
		catch (SocketException e) {
			System.out.println(e.getMessage());
		}
	}

	public void stopService() {
		if(!isRunning()) return;
		socket.close();
		this.setRunning(false);
	}
	
	private static InetAddress getUsedIPAddress(String networkInterfaceName) throws SocketException {
		Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
		for (NetworkInterface netint : Collections.list(nets)) {
			Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
			if(netint.getName().equals(networkInterfaceName)) {
				for (InetAddress inetAddress : Collections.list(inetAddresses)) {
					if(inetAddress.getHostAddress().contains("."))
						return inetAddress;
				}
			}
		}
		return null;
	}

	public static xPL_Message buildMessage(String source) throws xPL_Exception {
		String errorMessage = "unable to build a xPL message from the specified string due to ";
		xPL_Message resultMessage = new xPL_Message();
		//MESSAGE TYPE
		try {
			String strType = getLine(source, 0).substring(4, 8);
			if(strType.equals("cmnd")) {
				resultMessage.setMessageType(MessageType.COMMAND);
			}
			else if(strType.equals("stat")) {
				resultMessage.setMessageType(MessageType.STATUS);
			}
			else if(strType.equals("trig")) {
				resultMessage.setMessageType(MessageType.TRIGGER);
			}
			else 
				throw new xPL_Exception(errorMessage+"bad message type value");		
		}
		catch(StringIndexOutOfBoundsException e) {	
			System.out.println(e.getMessage());
			throw new xPL_Exception(errorMessage+"bad message type value");
		}
		//HOP COUNT
		try {
			String nvhop = getLine(source, 2);
			resultMessage.setHop(Integer.parseInt(nvhop.substring(nvhop.indexOf("=")+1)));
		}
		catch(StringIndexOutOfBoundsException | NumberFormatException e) {	
			System.out.println(e.getMessage());
			throw new xPL_Exception(errorMessage+"bad hop count value");
		}
		//SOURCE ADDRESS
		try {
			String nvsource = getLine(source, 3);
			String strSourceAddress = nvsource.substring(nvsource.indexOf("=")+1);
			xPL_Address sourceAddress = new xPL_Address(
					strSourceAddress.substring(0, strSourceAddress.indexOf("-")),
					strSourceAddress.substring(strSourceAddress.indexOf("-")+1, strSourceAddress.indexOf(".")),
					strSourceAddress.substring(strSourceAddress.indexOf(".")+1));
			resultMessage.setSourceAddress(sourceAddress);
		}
		catch(StringIndexOutOfBoundsException e) {	
			System.out.println(e.getMessage());
			throw new xPL_Exception(errorMessage+"bad source address value");
		}
		//TARGET ADDRESS
		try {
			String nvtarget= getLine(source, 4);

			String strTargetAddress = nvtarget.substring(nvtarget.indexOf("=")+1);
			if(strTargetAddress.equals("*"))
				resultMessage.setTargetAddress(xPL_Address.broadcast());
			else {
				xPL_Address targetAddress = new xPL_Address(
						strTargetAddress.substring(0, strTargetAddress.indexOf("-")),
						strTargetAddress.substring(strTargetAddress.indexOf("-")+1, strTargetAddress.indexOf(".")),
						strTargetAddress.substring(strTargetAddress.indexOf(".")+1));
				resultMessage.setTargetAddress(targetAddress);
			}
		}
		catch(StringIndexOutOfBoundsException e) {	
			System.out.println(e.getMessage());
			throw new xPL_Exception(errorMessage+"bad target address value");
		}
		//SCHEMA
		try {
			String schema = getLine(source, 6);
			String messageSchema = schema.substring(0, schema.indexOf("."));
			String messageClass = schema.substring(schema.indexOf(".")+1);
			resultMessage.setMessageSchema(messageSchema);
			resultMessage.setMessageClass(messageClass);
		}
		catch(StringIndexOutOfBoundsException e) {	
			System.out.println(e.getMessage());
			throw new xPL_Exception(errorMessage+"bad schema value");
		}
		//LOOP FOR BODY
		try {
			String tmp = "";
			for(int i = 7; !tmp.equals("}"); i++) {
				tmp = getLine(source, i);
				if(!tmp.contains("=")) continue;
				//resultMessage.setNamedValue(tmp.substring(0, tmp.indexOf("=")), tmp.substring(tmp.indexOf("=")+1));
				resultMessage.getNvlist().add(new xPL_NameValuePair(tmp.substring(0, tmp.indexOf("=")), tmp.substring(tmp.indexOf("=")+1)));
			}
		}
		catch(StringIndexOutOfBoundsException e) {	
			System.out.println(e.getMessage());
			throw new xPL_Exception(errorMessage+"an error during the body parsing");
		}
		return resultMessage;
	}

	public void addMessageListener(xPL_MessageListener listener) {
		listeners.add(listener);
	}

	public void removeListener(xPL_MessageListener listener) {
		listeners.remove(listener);
	}

	public xPL_MessageListener[] getListeners() {
		return listeners.toArray(new xPL_MessageListener[0]);
	}

	public void fireMessageReceived(xPL_Message message) {
		for(xPL_MessageListener listener : listeners) {
			listener.processMessage(message);
		}
	}

	public void sendMessage(xPL_Message message) {
		try {
			byte[] b = new byte[512];
			b = message.toString().getBytes();
			DatagramPacket p = new DatagramPacket(b, b.length);
			p.setAddress(InetAddress.getByAddress(new byte[] { (byte) 255,
					(byte) 255, (byte) 255, (byte) 255 }));
			p.setPort(BASE_XPL_PORT);
			socket.send(p);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public void startHeartbeatTimerTask() { hbeatTimer.schedule(new HeartbeatTimerTask(), 0); }

	public void sendHeartbeatMessage() throws SocketException {
		xPL_Message heartbeatMessage = new xPL_Message();
		heartbeatMessage.setMessageType(MessageType.STATUS);
		heartbeatMessage.setHop(1);
		heartbeatMessage.setSourceAddress(this.xPLAddress);
		heartbeatMessage.setTargetAddress(xPL_Address.broadcast());
		heartbeatMessage.setMessageSchema("hbeat");
		heartbeatMessage.setMessageClass("app");
		heartbeatMessage.setNamedValueInt("interval", this.hbeatInterval);
		heartbeatMessage.setNamedValueInt("port", this.listeningPort);
		heartbeatMessage.setNamedValue("remote-ip", getUsedIPAddress(this.networkInterfaceName).getHostAddress());	
		this.sendMessage(heartbeatMessage);
	}

	public void sendEndHeartbeatMessage() throws SocketException {
		xPL_Message endHeartbeatMessage = new xPL_Message();
		endHeartbeatMessage.setMessageType(MessageType.STATUS);
		endHeartbeatMessage.setHop(1);
		endHeartbeatMessage.setSourceAddress(this.xPLAddress);
		endHeartbeatMessage.setTargetAddress(xPL_Address.broadcast());
		endHeartbeatMessage.setMessageSchema("hbeat");
		endHeartbeatMessage.setMessageClass("end");
		this.sendMessage(endHeartbeatMessage);
	}

	public int getHbeatInterval() { return this.hbeatInterval; }

	public void setHbeatInterval(int interval) { this.hbeatInterval = interval; }

	public boolean isRunning() { return running; }

	public void setRunning(boolean running) { this.running = running; }

	public int getListeningPort() { return listeningPort; }

	public void setListeningPort(int listeningPort) { this.listeningPort = listeningPort; }

	public ConnectionStatus getConnectionStatus() { return connectionStatus; }

	public xPL_Address getxPLAddress() { return xPLAddress; }

	public void setxPLAddress(xPL_Address xPLAddress) { this.xPLAddress = xPLAddress; }

	public boolean isHubConfirmed() { return hubConfirmed; }

	public void setHubConfirmed(boolean hubConfirmed) { this.hubConfirmed = hubConfirmed; }

	public String getNetworkInterfaceName() { return this.networkInterfaceName; }

	public void setNetworkInterfaceName(String interfaceName) { this.networkInterfaceName = interfaceName; }

	public String getNetworkIPAddress() { return networkIPAddress; }

	public void setNetworkIPAddress(String networkIPAddress) { this.networkIPAddress = networkIPAddress; }

	private class HeartbeatTimerTask extends TimerTask {
		@Override
		public void run() {
			try {
				Thread.sleep(hbeatInterval*60*1000);
				sendHeartbeatMessage();
				hbeatTimer.schedule(new HeartbeatTimerTask(), hbeatInterval*60*1000);
			} 
			catch(InterruptedException | SocketException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void processMessage(xPL_Message message) {
		try {
			if(message.getMessageType() == MessageType.STATUS
					&& message.getMessageSchema().equals("hbeat") 
					&& message.getMessageClass().equals("app")
					&& message.getSourceAddress().equals(this.xPLAddress)
					&& message.getNamedValue("remote-ip").equals(this.getNetworkIPAddress())
					&& message.getNamedValueInt("port") == this.getListeningPort()) 
			{
				this.removeListener(this);
				this.setHubConfirmed(true);
			}
		}
		catch(xPL_Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
