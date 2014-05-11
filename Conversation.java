import javax.swing.*;

import java.util.Iterator;
import java.util.List;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class Conversation extends JFrame implements Runnable {
	private JTextArea receiveConvo;
	private JTextField inputConvo;
	private JScrollPane scroll;
	private ConvoInputNotifier convoListen;
	private Socket convo;
	private BufferedReader in;
	private DataOutputStream out;

	public Conversation(Socket s) {
		convo = s;

		setTitle("Conversation");

		receiveConvo = new JTextArea();
		inputConvo = new JTextField();

		// JPanel panel = new JPanel();
		TextFieldListener tfListen = new TextFieldListener();
		convoListen = new ConvoInputNotifier();
		// panel.setLayout(null);

		setLayout(null);

		inputConvo.addActionListener(tfListen);
		

		inputConvo.setEditable(true);
		receiveConvo.setEditable(false);

		receiveConvo.setBounds(5, 5, 400, 50);
		inputConvo.setBounds(10, 200, 450, 50);

		scroll = new JScrollPane(receiveConvo);

		scroll.setBounds(10, 20, 450, 70);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		add(scroll);
		add(inputConvo);

		// add(panel);

		convoListen.execute();
		pack();
		setSize(500, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	private class TextFieldListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			String text = inputConvo.getText();
			receiveConvo.append(text + '\n');
			try {
				out.writeBytes(text + '\n');
			} catch (Exception e) {
				System.err.println(e);
			}
			inputConvo.setText("");
		}

	}

	private class ConvoInputNotifier extends SwingWorker<Void, String> {
		
		@Override
		protected Void doInBackground() {
			
			while (true) {
				//System.out.println("In While Loop");
				
				if (in != null) {
					try {
						if (in.ready()) {
							String text = in.readLine();
							publish(text);
						}
					} catch (Exception e) {
						System.err.println("Error in doInBackGround " + e);
						// return null;
					}
				}else {
					try {
						in = new BufferedReader(new InputStreamReader(
								convo.getInputStream()));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//System.out.println("In is null right now");
				}
			}
		}

		//@Override
		protected void process(List<String> inputStr){
			if (inputStr.isEmpty()){
				return;
			}
			Iterator<String> listIt = inputStr.iterator();
			while(listIt.hasNext()){
				receiveConvo.append(listIt.next() + '\n');				
			}
		}
	}

	@Override
	public void run() {

		try {
			in = new BufferedReader(new InputStreamReader(
					convo.getInputStream()));

			out = new DataOutputStream(convo.getOutputStream());
			
			if (in.ready()) {
				receiveConvo.setText(in.readLine() + '\n');
			}
		} catch (Exception e) {
			System.err.println(e);
		}
	}
}
