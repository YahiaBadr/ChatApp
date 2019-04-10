package Old;

import java.io.Serializable;

public class Connection implements Serializable {

	private static final long serialVersionUID = 1L;
	String Username,Pass;
	public Connection(String user,String pass) {
		// TODO Auto-generated constructor stub
		Username=user;
		Pass=pass;
	}
}
