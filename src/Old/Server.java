package Old;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;



public class Server
{
	static HashMap<String, Integer>User_ID=new HashMap<>();
	static HashMap<String, String>Pass=new HashMap<>();
	static HashMap<Integer, String>Inv=new HashMap<>();
	static ArrayList<Queue<Message>>history=new ArrayList<>();
	static int id=0;
	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException
	{
//		ServerSocket listener=new ServerSocket(6666);
		ExecutorService pool = Executors.newFixedThreadPool(20);
		int i=1;
//		while(true)
//		{
//			Server_Thread thread1=new Server_Thread(listener.accept());
//			pool.execute(thread1);
//			System.out.println("done thread "+i++);
//		}
//		Scanner sc=new Scanner(System.in);
		
		Slave s1=new Slave(6666);
		pool.execute(s1);
		Slave s2=new Slave(6667);
		pool.execute(s2);
		System.out.println("done");
	}
}

class Slave implements Runnable
{
	ServerSocket listener;
	ExecutorService pool;
	int MyPort;
	int i=1;
	HashMap<String, Integer>server_clients;
	public Slave(int port) throws IOException 
	{
		// TODO Auto-generated constructor stub
		server_clients=new HashMap<>();
		MyPort=port;
		listener=new ServerSocket(port);
		pool = Executors.newFixedThreadPool(20);
	}
	public void Open() throws IOException
	{
		while(true)
		{
			Server_Thread thread1=new Server_Thread(listener.accept(),this);
			pool.execute(thread1);
			System.out.println("done thread "+i++);
		}
		
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			Open();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
class Server_Thread implements Runnable
{
	Socket socket;
	ObjectOutputStream outToClient;
	ObjectInputStream inFromClient;
	Slave slave;
	public Server_Thread(Socket s, Slave slave) throws IOException{
	// TODO Auto-generated constructor stub
		socket=s;
		this.slave=slave;
		outToClient = new ObjectOutputStream(socket.getOutputStream());
		inFromClient = new ObjectInputStream(socket.getInputStream());
	}
	public void run() 
    { 
        try
        {
        	Open_Server();
        } 
        catch (Exception e) 
        { 
            // Throwing an exception 
            System.out.println ("Exception is caught :"+e.getMessage()); 
        } 
    }
	public void Open_Server() throws IOException
	{
		Message clientSentence;
		
		while (true) {
			Object o;
			try
			{
				o=inFromClient.readObject();
			}
			catch (Exception e)
			{
				break;
			}
			if(o instanceof Message)
			{
				clientSentence =(Message)o;
				int id=clientSentence.to;
				if(id==-1)
				{
					String user=Server.Inv.get(((Message) o).from);
					Server.Pass.remove(user);
					Server.User_ID.remove(user);
					Server.Inv.remove(id);
					slave.server_clients.remove(user);
				}
				else
					Server.history.get(id).add(clientSentence);
			}
			else if(o instanceof GetMessage)
			{
				outToClient.writeObject(new Bag(Server.User_ID,Server.Inv,slave.server_clients));
				GetMessage c=(GetMessage)o;
				int id=c.id;
				Bag b=new Bag(Server.history.get(id));
				outToClient.writeObject(b);
				Server.history.get(id).clear();
			}
			else
			{
				Connection c=(Connection)o;
				int id=Server.User_ID.size();
				if(Server.User_ID.containsKey(c.Username))
				{
					if(c.Pass.equals(Server.Pass.get(c.Username)))
					{
						outToClient.writeObject(new Bag("Connected!"));
						continue;
					}
					else
						outToClient.writeObject(new Bag("Wrong Pass!, User Already Exist"));
				}
				else
				{
					Server.Pass.put(c.Username, c.Pass);
					Server.User_ID.put(c.Username, id);
					Server.Inv.put(id, c.Username);
					slave.server_clients.put(c.Username,id );
					System.out.println(slave.server_clients);
				}
				System.out.println(Server.User_ID);
				while(id>=Server.history.size())
					Server.history.add(new LinkedList<Message>());
				outToClient.writeObject(new Bag("Connected!"));
				
			}
		}
	}
}