import java.io.*;
import java.net.*;
import java.util.LinkedList;

import javax.swing.SwingUtilities;


public class TcpClientProgram {

	@SuppressWarnings("unchecked")
	public static void main(String args[]) throws Exception{
		TCPClient client = new TCPClient("Client1", 3005, 3006);
		try{
			Socket srvrConnect = client.connectToServer();
			System.out.println("connected to server");
			client.login(srvrConnect);
			InputStream in = srvrConnect.getInputStream();
			ObjectInputStream inObj = new ObjectInputStream (in);
			System.out.println("Logged In");
			client.setBuddyList((LinkedList<ClientInfo>) inObj.readObject());
		}catch (Exception e){
			System.err.println(e);
			return;
		}
		
		System.out.println("Cleared Try Statement");
		while(true){
		ServerSocket buddyListen = client.getServerSocket();
		Socket buddyConnect = buddyListen.accept();			
		SwingUtilities.invokeLater(new Conversation(buddyConnect));
		//new Thread(new Conversation(buddyConnect)); 
		}
	}
}
