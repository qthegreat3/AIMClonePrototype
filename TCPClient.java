import java.io.*;
import java.net.*;
import java.util.*;

public class TCPClient {
	
	private LinkedList<ClientInfo> totalBuddyList;
	private ServerSocket server;
	private ServerSocket updateServer;
	private ClientInfo myInfo;
	
	public TCPClient(String mySnName) throws IOException{
		totalBuddyList = new LinkedList<ClientInfo>();
		server = new ServerSocket(3000);
		updateServer = new ServerSocket(3001);
		myInfo = new ClientInfo(mySnName, server.getLocalPort(), updateServer.getLocalPort(),server.getInetAddress());
	}
	
	public TCPClient(String mySnName, int port, int updatePort) throws IOException{
		totalBuddyList = new LinkedList<ClientInfo>();
		server = new ServerSocket(port);
		updateServer = new ServerSocket(updatePort);
		myInfo = new ClientInfo(mySnName, server.getLocalPort(), updateServer.getLocalPort(), server.getInetAddress());
	}	
	
	public int getBuddiesOnline(){
		return totalBuddyList.size();
	}
	
	public ClientInfo getMyInfo(){
		return myInfo;
	}
	
	public ClientInfo getBuddyInfo(int numOfBuddy){
		return totalBuddyList.get(numOfBuddy);
	}
	
	public ServerSocket getServerSocket(){
		return server;
	}
	
	public ServerSocket getUpdateServerSocket(){
		return updateServer;
	}
	
	public void setBuddyList(LinkedList<ClientInfo> buddyList){
		totalBuddyList = buddyList;
	}
	
	public void setServerSocket(ServerSocket server_in){
		server = server_in;
	}
	
	public void setMyInfo(String newSnName){
		myInfo.setScreenName(newSnName);
	}
	public  Socket connectToServer() throws IOException{
		Socket s = new Socket(InetAddress.getLocalHost(), 3000);
		return s;
	}
	
	public Socket connectToBuddy(InetAddress ip, int port) throws IOException{
		Socket s = new  Socket(ip, port);
		return s;
	}
	
	public void login(Socket toSrvr) throws IOException{
		OutputStream out = toSrvr.getOutputStream();
		ObjectOutputStream objOut = new ObjectOutputStream(out);
		objOut.writeObject(myInfo);
		//toSrvr.close();
	}
	
	public void logOff(){
		//TODO Write logic To Tell Server To Remove Client From MasterBuddy List
	}
}
