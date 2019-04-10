package Old;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.JTextPane;

public class GUI_Signin extends JFrame {

	private JPanel contentPane;
	private JTextField textChat;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client c=new Client("yahia", "yahia", "123", 6666);
					GUI_Signin frame = new GUI_Signin(c);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	JComboBox Users;
	JLabel Recieve;
	JTextField textTTL;
	public GUI_Signin(Client c) {
		setTitle(c.Username);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 510, 716);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		textChat = new JTextField();
		textChat.setBounds(26, 538, 284, 37);
		contentPane.add(textChat);
		textChat.setColumns(10);
		textChat.setText("");
		JButton btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String friend=(String)Users.getSelectedItem();
				if(friend.equals(""))
				{
					JOptionPane.showMessageDialog(null, "Select a Username");
				}
				else
				{
					try {
						if(c.Chat(friend,textChat.getText(),Integer.parseInt(textTTL.getText())))
							c.online.all+="You to "+friend+" : "+textChat.getText()+"<br/>";
						
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
					
			}
		});
		btnSend.setBounds(26, 610, 284, 37);
		contentPane.add(btnSend);
		
		Users = new JComboBox();
		Users.setBounds(355, 54, 122, 29);
		contentPane.add(Users);
		
		JButton btnGetMembers = new JButton("Get Members");
		btnGetMembers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HashMap<String, Integer> users=c.GetMembers();
				Users.removeAllItems();
				Users.addItem("");
				for(String s:users.keySet())
				{
					System.out.println(s);
					Users.addItem(s);
				}
				Users.setVisible(true);
				
			}
		});
		btnGetMembers.setBounds(347, 542, 106, 29);
		contentPane.add(btnGetMembers);
		
		Recieve = new JLabel("");
		Recieve.setBackground(Color.BLACK);
		Recieve.setBounds(26, 34, 284, 476);
		contentPane.add(Recieve);
		
		textTTL = new JTextField();
		textTTL.setBounds(110, 585, 96, 19);
		contentPane.add(textTTL);
		textTTL.setColumns(10);
		
		JButton btnQuit = new JButton("Quit");
		btnQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					c.Quit();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				JOptionPane.showMessageDialog(null, "Bye Bye Mariem a7ssan TA <3 !!!");
				dispose();
			}
		});
		btnQuit.setBounds(347, 618, 106, 29);
		contentPane.add(btnQuit);
		
		
	}
}
