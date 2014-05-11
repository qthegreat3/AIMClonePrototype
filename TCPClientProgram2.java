import java.io.*;
import java.net.*;
import java.util.LinkedList;

import javax.swing.SwingUtilities;


public class TCPClientProgram2{

	@SuppressWarnings("unchecked")
	public static void main(String args[]) throws Exception{
		TCPClient client = new TCPClient("Client2", 3007, 3008);
		try{
			Socket srvrConnect = client.connectToServer();
			client.login(srvrConnect);
			InputStream in = srvrConnect.getInputStream();
			ObjectInputStream inObj = new ObjectInputStream (in);
			client.setBuddyList((LinkedList<ClientInfo>) inObj.readObject());
		}catch (Exception e){
			System.err.println(e);
			return;
		}
		
		ClientInfo buddy = client.getBuddyInfo(0);
			
		Socket buddyConnect = client.connectToBuddy(buddy.getIp(), buddy.getConvoPort());

/*		DataOutputStream out = new DataOutputStream(buddyConnect.getOutputStream());
	    BufferedReader inFromUser =
	 	         new BufferedReader(new InputStreamReader(System.in));

		BufferedReader in = new BufferedReader(new InputStreamReader(
				buddyConnect.getInputStream()));*/
		
		//while(true){
			
			SwingUtilities.invokeLater(new Conversation(buddyConnect));
			
/*			if(inFromUser.ready()){
				String outLn = inFromUser.readLine();	
				out.writeBytes(outLn + '\n');
			}		    			
			
			if(in.ready()){
				System.out.println(in.readLine());
			}
				*/
		//}
	}
}

