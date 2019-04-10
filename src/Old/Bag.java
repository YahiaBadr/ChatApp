package Old;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Queue;

@SuppressWarnings("serial")
public class Bag implements Serializable{
	String seq;
	String s;
	
	HashMap<String, Integer>User_ID=new HashMap<>();
	HashMap<Integer, String>Inv=new HashMap<>();
	HashMap<Integer, String>serverClients=new HashMap<>();
	public Bag(Queue<Message> queue) {
		StringBuilder tmp=new StringBuilder();
		while(!queue.isEmpty()) {
			Message m=queue.poll();
			tmp.append(m.from+":"+m.Message+"`");
		}
		seq=tmp+"";
	}
	
	@SuppressWarnings("unchecked")
	public Bag(HashMap<String, Integer> user ,HashMap<Integer, String> inv ,HashMap<String, Integer> serverClients) {
		this.User_ID=(HashMap<String, Integer>) user.clone();
		this.Inv = (HashMap<Integer, String>) inv.clone();
		this.serverClients = (HashMap<Integer, String>) serverClients.clone();
		
	}
	public Bag(String s)
	{
		this.s=s;
	}
}