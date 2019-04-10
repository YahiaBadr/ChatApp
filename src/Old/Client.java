package Old;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Client
{

	public String getName() {
		return Username;
	}
	public String Fullname;
	public String Username;
	GUI_Signin g;
	String Password;
	String HostName;
	Socket Socket;
	ArrayList<ArrayList<Message>>Message;
	HashMap<String,Integer>Users;
	HashMap<String,Integer>sameServer;
	HashMap<Integer, String>Inv;
	int ServerPort,My_ID;
	ObjectOutputStream outToServer;
	ObjectInputStream inFromServer;
	Online online;
	ExecutorService pool;
	boolean connected;
	public Client(String name,String user,String pass,int server) throws IOException, ClassNotFoundException
	{
		// TODO Auto-generated constructor stub
		HostName=InetAddress.getLocalHost().getHostName();
		Fullname=name;
		Username=user;
		Password=pass;
		ServerPort=server;
		Message=new ArrayList<>();
		Socket=new Socket(HostName, ServerPort);
		outToServer = new ObjectOutputStream(Socket.getOutputStream());
		inFromServer = new ObjectInputStream(Socket.getInputStream());
		pool = Executors.newFixedThreadPool(2);
		Connect();
		online=new Online(this);
		pool.execute(online);
	}
	public void Connect() throws IOException, ClassNotFoundException
	{
		outToServer.writeObject(new Connection(Username,Password));
		Bag b=(Bag)inFromServer.readObject();
		System.out.println(b.s);
		if(b.s.equals("Connected!"))
		{
			connected=true;
		}
	}
	public boolean Chat(String username, String M_User,int TTL)throws IOException, ClassNotFoundException, InterruptedException
	{
		System.out.println(sameServer);
		if(TTL == 0)
			return false;
		if(TTL == 1 && !sameServer.containsKey(username))
			return false;
		int id=Users.get(username);
		while(id>=Message.size())
			Message.add(new ArrayList<>());
		ArrayList<Message>arr=Message.get(id);
//		while(true)
//		{
//			BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
//
//			String M_User=inFromUser.readLine();
//			System.out.println("next");
			if( M_User.equals("quit") )
				return false;
			Message sent=new Message(My_ID, id, M_User);
			arr.add(sent);
			outToServer.writeObject(sent);
			return true;
//		}
	}
	HashMap<String, Integer> GetMembers()
	{
		System.out.println(Users);
		return Users;
	}
	void Quit() throws IOException
	{
		Message sent=new Message(My_ID, -1, "");
		outToServer.writeObject(sent);
		Socket.close();
	}
	public static void main(String[] args) throws ClassNotFoundException, IOException, InterruptedException
	{
		System.out.println("start");
		Scanner sc=new Scanner(System.in);
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		// DO NOT FORGET THE PASSWORD HASHMAP!!!!
		Client user;
		while(true)
		{
			System.out.println("Enter Username, Password and Port");
			String username=br.readLine();
			String pass=br.readLine();
			int port=Integer.parseInt(br.readLine());
			user=new Client(username, username, pass, port);
			if(user.connected)break;
		}
		while(true)
		{	
			System.out.println("enter the id of the chatting friend or -2 for memberlist or enter -1 to close the app");
			int chat=Integer.parseInt(br.readLine());
			String msg=br.readLine();
			int ttl=Integer.parseInt(br.readLine());
			if(chat==-2)
				user.GetMembers();
			else if(chat>=0)
				user.Chat(user.Inv.get(chat),msg,ttl);
			else if(chat==-1)
			{
				user.Socket.close();
				user.pool.shutdown();
				System.out.print("Your are offline. Bye!");
				break;
			}
		}
		sc.close();
	}
}
