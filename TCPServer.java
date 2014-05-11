import java.io.*;
import java.net.*;
import java.util.*;

public class TCPServer {
	
	private LinkedList<ClientInfo> totalBuddyList;
	//private HashMap<String, Socket> clientConnection;
	private ServerSocket server;
	
	public TCPServer() throws IOException{
		totalBuddyList = new LinkedList<ClientInfo>();
		server = new ServerSocket(3000);
	}
	
	public void RegisterClient(ClientInfo newClient){
		totalBuddyList.add(newClient);
	}
	
	public void setServerSocket(ServerSocket server_in){
		server = server_in;
	}
	
	public ServerSocket getServerSocket(){
		return server;
	}
	
	public void SendList(Socket toClient) throws IOException{
		OutputStream out = toClient.getOutputStream();
		ObjectOutputStream objOut = new ObjectOutputStream(out);
		objOut.writeObject(totalBuddyList);	
		objOut.close();
		toClient.close();
	}
	
	public void NotifyList(){
		for (int i = 0; i < totalBuddyList.size(); i++){
			try{
				Socket s = new Socket(totalBuddyList.get(i).getIp(), totalBuddyList.get(i).getUpdatePort());
				ObjectOutputStream objOut = new ObjectOutputStream(s.getOutputStream());
				objOut.writeObject(totalBuddyList);
				objOut.close();
				s.close();
			}catch (Exception e){
				System.err.println(e);
			}
		}

	}
	
	public void RemoveClient(ClientInfo exClient){
		totalBuddyList.remove(exClient);		
	}

}
