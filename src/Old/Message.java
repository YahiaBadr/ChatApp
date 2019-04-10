package Old;

import java.io.Serializable;
import java.util.Queue;

public class Message implements Serializable
{

	private static final long serialVersionUID = 1L;
	int from,to,flag;
	String Message;
	public Message(int from,int to,String M) {
		// TODO Auto-generated constructor stub
		this.from=from;this.to=to;
		Message=M;
	}
	public Message(int from,int to,Queue<Message> queue) {
		// TODO Auto-generated constructor stub
		StringBuilder tmp=new StringBuilder();
		while(!queue.isEmpty()) {
			tmp.append(queue.poll().Message+" ");
		}
		Message=tmp+"";
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return Message;
	}
}
