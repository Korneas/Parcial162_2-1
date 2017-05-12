package serial;

import java.io.Serializable;

public class Mensaje implements Serializable{
	
	private String msg;
	private int send;

	public Mensaje(String msg, int send) {
		super();
		this.msg = msg;
		this.send = send;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getSend() {
		return send;
	}

	public void setSend(int send) {
		this.send = send;
	}	

}
