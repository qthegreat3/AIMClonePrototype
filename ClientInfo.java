import java.net.*;
import java.io.*;

public class ClientInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	private String      screenName;
	private int         updatePort;
	private int         convoPort;
	private InetAddress ip;

	public ClientInfo(){
		screenName = " ";
		updatePort = 0;
		convoPort = 0;
		try{
		ip = InetAddress.getLocalHost();
		}
		catch (IOException e){
			System.err.println("Unknown Host Error: " + e.getMessage());
		}
	}
	
	public ClientInfo(String snName, int convoPort_in, int updatePort_in, InetAddress ip_in){
		screenName = snName;
		convoPort = convoPort_in;
		updatePort = updatePort_in;
		ip = ip_in;
	}
	
	public void setScreenName(String snName){
		screenName = snName;
	}
	
	public void setPort(int port_in){
		convoPort = port_in;
	}	
	
	public void setIp(InetAddress ip_in){
		ip = ip_in;
	}	
	
	public String getScreenName(){
		return screenName;
	}
	
	public int getConvoPort(){
		return convoPort;
	}
	
	public int getUpdatePort(){
		return updatePort;
	}
	public InetAddress getIp(){
		return ip;
	}
}
