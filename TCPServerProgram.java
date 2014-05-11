	import java.io.InputStream;
	import java.io.ObjectInputStream;
	import java.net.ServerSocket;
	import java.net.Socket;
	
public class TCPServerProgram {

	public static void main(String args[]) throws Exception {
		TCPServer server = new TCPServer();

		while (true) {
			try{
			ServerSocket serverTalk = server.getServerSocket();
			Socket toClient = serverTalk.accept();
			InputStream in = toClient.getInputStream();
			ObjectInputStream objIn = new ObjectInputStream(in);
			server.RegisterClient((ClientInfo) (objIn.readObject()));
			server.SendList(toClient);
			toClient.close();
			}catch(Exception e){
				System.err.println(e);
			}
		}
	}
}


