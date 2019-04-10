package Old;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.HashMap;

public class Online implements Runnable
{

	Client client;
	String all="";
	public Online(Client c) {
		// TODO Auto-generated constructor stub
		client=c;
	}
	@Override
	public void run()
	{
		// TODO Auto-generated method stub
		while(true)
		{
			try {
				if(!GetMessages())break;
			} 
			catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	@SuppressWarnings("unchecked")
	public boolean GetMessages() throws UnknownHostException, IOException, ClassNotFoundException
	{
		try
		{
			client.outToServer.writeObject(new GetMessage(client.My_ID));
			Bag b = (Bag)client.inFromServer.readObject();
			client.Users=(HashMap<String, Integer>) b.User_ID.clone();
			client.Inv=(HashMap<Integer, String>) b.Inv.clone();
			client.sameServer=(HashMap<String, Integer>) b.serverClients.clone();
			client.My_ID=client.Users.get(client.Username);
			String[] M_Server=((Bag)client.inFromServer.readObject()).seq.split("`");
			for(int i=0;i<M_Server.length;i++) {
				if(M_Server[i].length()!=0 && M_Server[i].split(":")[1].length() !=0)
				{
					all+="From your Friend "+client.Inv.get(Integer.parseInt(M_Server[i].split(":")[0])) + ":" + M_Server[i].split(":")[1]+"<br/>";
					System.out.println("From your Friend "+client.Inv.get(Integer.parseInt(M_Server[i].split(":")[0])) + ":" + M_Server[i].split(":")[1]);
				}
			}
			if(this.client.g!=null)
				this.client.g.Recieve.setText("<html>"+all+"</html>");
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}

}
