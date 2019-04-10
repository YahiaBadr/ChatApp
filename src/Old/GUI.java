package Old;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class GUI extends JFrame {

	private JPanel contentPane;
	private JTextField txtUsername;
	private JTextField txtPassword;
	private JTextField txtPort;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
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
	public GUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnSignIn = new JButton("Sign In");
		btnSignIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = txtUsername.getText();
				String password = txtPassword.getText();
				int port=Integer.parseInt(txtPort.getText());
				if(port!=6666 && port!=6667)
				{
					JOptionPane.showMessageDialog(null, "Enter right Port");
				}
				try {
					Client c=new Client(username, username, password, port);
					if(c.connected)
					{
						dispose();
						GUI_Signin g=new GUI_Signin(c);
						c.g=g;
						g.setVisible(true);
//						GUI g1=new GUI()
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Wrong Pass!, User Already Exist");
					}
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnSignIn.setBounds(183, 176, 85, 21);
		contentPane.add(btnSignIn);
		
		txtUsername = new JTextField();
		txtUsername.setToolTipText("");
		txtUsername.setBounds(172, 88, 96, 19);
		contentPane.add(txtUsername);
		txtUsername.setColumns(10);
		
		txtPassword = new JTextField();
		txtPassword.setToolTipText("Password");
		txtPassword.setBounds(172, 114, 96, 19);
		contentPane.add(txtPassword);
		txtPassword.setColumns(10);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(101, 91, 61, 13);
		contentPane.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(101, 117, 61, 13);
		contentPane.add(lblPassword);
		
		txtPort = new JTextField();
		txtPort.setBounds(172, 147, 96, 19);
		contentPane.add(txtPort);
		txtPort.setColumns(10);
		
		JLabel lblPort = new JLabel("Port");
		lblPort.setBounds(101, 150, 46, 13);
		contentPane.add(lblPort);
	}
}
