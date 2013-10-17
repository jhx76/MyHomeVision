package com.xpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Properties;

import com.mhv.dao.DAOConfigurationException;

public class xPL_HalServer_Proxy {
	private static final String FILE_PROPERTIES = "/com/mhv/dao/mhv.properties";
	private static final String PROPERTY_HALSERVER_IP = "halserver_xhcp_ip";
	private static final String PROPERTY_HALSERVER_PORT = "halserver_xhcp_port";
	private String ipAddress = "";
	private int port = -1;
	private String serverIdentifier = "";
	private String version = "";
	private String xhcpVersion = "";
	private String welcomeMessage = "";
	private boolean connected = false;
	private Socket socket = null;
	private BufferedReader in = null;
	private PrintWriter out = null;
	private ArrayList<xPL_Device> deviceList = new ArrayList<xPL_Device>();

	/**
	 * Constructor
	 * @param ipAddress
	 * @param port
	 */
	public xPL_HalServer_Proxy(String ipAddress, int port) {
		this.ipAddress = ipAddress;
		this.port = port; 
	}

	/**
	 * Build an xPL_HalServer_Proxy whith the configuration values found in the file specify by FILE_PROPERTIES
	 * @return a preconfigured object with values found in the appropriate property file(s) 
	 * @throws DAOConfigurationException
	 */
	public static xPL_HalServer_Proxy getInstance() throws DAOConfigurationException {
		Properties properties = new Properties();	
		String ipAddress;
		int port;
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream fichierProperties = classLoader.getResourceAsStream(FILE_PROPERTIES);
		if ( fichierProperties == null ) {
			throw new DAOConfigurationException( "Le fichier properties " + FILE_PROPERTIES + " est introuvable." );
		}
		try {
			properties.load( fichierProperties );
			ipAddress = properties.getProperty( PROPERTY_HALSERVER_IP );
			port = Integer.parseInt(properties.getProperty( PROPERTY_HALSERVER_PORT ));
		} catch ( IOException e ) {
			throw new DAOConfigurationException( "Impossible de charger le fichier properties " + FILE_PROPERTIES, e );
		}
		xPL_HalServer_Proxy proxy = new xPL_HalServer_Proxy(ipAddress, port);
		return proxy;
	}

	/**
	 * Try to open the client socket.
	 * @return true if success, otherwise return false;
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public boolean connect() throws UnknownHostException, IOException {
		System.out.println("trying to connect to host at "+ipAddress+" port "+port);
		System.out.println("Demande de connection...");
		socket = new Socket(Inet4Address.getByName(ipAddress), port);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream(), true);
		String connectionMessage = in.readLine();
		System.out.println(connectionMessage);
		if(connectionMessage.substring(0, 3).equals("200")) {
			System.out.println("CONNECTION OK");
			setServerIdentifier(connectionMessage.substring(4, connectionMessage.indexOf("Version")));
			setVersion(connectionMessage.substring(connectionMessage.indexOf("Version"), connectionMessage.indexOf("XHCP")));
			setXhcpVersion(connectionMessage.substring(connectionMessage.indexOf("XHCP"), connectionMessage.indexOf("Welcome")));
			setWelcomeMessage(connectionMessage.substring(connectionMessage.indexOf("Welcome")));
			connected = true;
		}
		return connected;
	}

	/**
	 * Close the client socket
	 */
	public void disconect() {
		try {
			if(socket != null && socket.isConnected()) {
				System.out.print("xPLHal socket closure...");
				socket.close();
				System.out.println("OK");
			}
		} catch (IOException e) {
			System.out.println("");
			System.out.println("["+e.getClass().toString()+"] "+e.getMessage());
		}
	}

	/**
	 * Refresh the device list. The actual list will be cleared before to add
	 * all device found accross the server response. 
	 * XHCP command(s) used: 
	 * LISTDEVICES
	 * Server response: 
	 * <vdi><tab><expires><tab><interval><tab><configtype><tab><configdone><tab><waitingconfig><tab><suspended>
	 * Where :
     * <vdi> specifies the vendor-device.instance name of the device.
     * <expires> contains the date/time at which the device will be considered "expired" if no further hbeat messages are received.
     * <interval> The interval (in minutes) between heartbeat messages
     * <configtype> Y = awaiting configuration, N = already configured
     * <configdone> Y = sent/not required, N = waiting
     * <waitingconfig>
     * <suspended> Y = the device is suspended - i.e. no heartbeat received within timeout period, N = not suspended 
	 * @throws XHCPException
	 */
	public void refreshDeviceList() throws XHCPException {
		try {
			out.println("LISTDEVICES");
			String message = "";
			message = in.readLine();
			if(message.substring(0, 3).equals("216")) {
				//clear the device list
				deviceList.clear();
				//add each device found accross the server response
				while(message != null) {
					message = in.readLine();
					System.out.println(message);
					if(message.equals("."))
						break;
					xPL_Address vdi = new xPL_Address(message.substring(0, message.indexOf("\t")));
					message = message.substring(message.indexOf("\t")+1);
					String expires = message.substring(0, message.indexOf("\t"));
					message = message.substring(message.indexOf("\t")+1);
					int interval = Integer.parseInt(message.substring(0, message.indexOf("\t")));
					message = message.substring(message.indexOf("\t")+1);
					boolean configType = message.substring(0, message.indexOf("\t")).equals("Y");
					message = message.substring(message.indexOf("\t")+1);
					boolean configDone = message.substring(0, message.indexOf("\t")).equals("Y");
					message = message.substring(message.indexOf("\t")+1);
					boolean waitingConfig = message.substring(0, message.indexOf("\t")).equals("Y");
					message = message.substring(message.indexOf("\t")+1);
					boolean suspended = message.substring(0).equals("Y");
					xPL_Device device = new xPL_Device(vdi, expires, interval, configType, configDone, waitingConfig, suspended);
					deviceList.add(device);
				}	
				for(int i = 0; i < getDeviceList().size(); i++) {
					xPL_Device device = getDeviceList().get(i);
					refreshDeviceConfigItems(device);
				}
			}
			else {
				throw new XHCPException("Unknown server response...");
			}
		}
		catch(IOException e) {
			throw new XHCPException("Server not connected...", e);
		}
	}

	/**
	 * Get the configuration-item list from the xPLHal server via the XHCP connection.
	 * XHCP provides GETDEVCONFIG <vdi>
	 * server response :
	 * 416 No config available for specified XPL device
	 * 417 No such device
	 * 217 List of config items follows
	 * <name>\t<type>\t<number>
	 * @param device The device we need to get the configItems
	 * @throws XHCPException
	 */
	public void refreshDeviceConfigItems(xPL_Device device) throws XHCPException {
		System.out.println("GETDEVCONFIG "+device.getAddress());
		out.println("GETDEVCONFIG "+device.getAddress());
		String message = "";
		try {
			message = in.readLine();
			if(message.substring(0, 3).equals("217")) {
				System.out.println(message.substring(3));
				device.getConfigItemList().clear();
				while(message != null) {
					message = in.readLine();
					if(message.equals("."))
						break;
					String name = message.substring(0, message.indexOf("\t"));
					message = message.substring(message.indexOf("\t")+1);
					String type = message.substring(0, message.indexOf("\t"));
					message = message.substring(message.indexOf("\t")+1);
					int number = Integer.parseInt(message);
					ConfigurableItem item = new ConfigurableItem(name, type, number);
					System.out.println(item);
					device.getConfigItemList().add(item);
				}
			}
			else if(message.substring(0, 3).equals("417")) {
				System.out.println(message.substring(3));
				throw new XHCPException(message.substring(3));
			}
			else if(message.substring(0, 3).equals("416")) {
				System.out.println(message.substring(3));
				throw new XHCPException(message.substring(3));
			}
			else {
				throw new XHCPException("Unknown server response ...");
			}
		}
		catch(IOException e) {
			throw new XHCPException("Server not connected ...", e);
		}
	}

	/**
	 * @return the serverIdentifier
	 */
	public String getServerIdentifier() {
		return serverIdentifier;
	}

	/**
	 * @param serverIdentifier the serverIdentifier to set
	 */
	public void setServerIdentifier(String serverIdentifier) {
		this.serverIdentifier = serverIdentifier;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * @return the xhcpVersion
	 */
	public String getXhcpVersion() {
		return xhcpVersion;
	}

	/**
	 * @param xhcpVersion the xhcpVersion to set
	 */
	public void setXhcpVersion(String xhcpVersion) {
		this.xhcpVersion = xhcpVersion;
	}

	/**
	 * @return the welcomeMessage
	 */
	public String getWelcomeMessage() {
		return welcomeMessage;
	}

	/**
	 * @param welcomeMessage the welcomeMessage to set
	 */
	public void setWelcomeMessage(String welcomeMessage) {
		this.welcomeMessage = welcomeMessage;
	}

	/**
	 * @return the deviceList
	 */
	public ArrayList<xPL_Device> getDeviceList() { 
		return deviceList;
	}


}
