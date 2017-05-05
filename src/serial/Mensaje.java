package serial;

import java.io.Serializable;

public class Mensaje implements Serializable{
	
	private String msg,send;

	public Mensaje(String msg, String send) {
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

	public String getSend() {
		return send;
	}

	public void setSend(String send) {
		this.send = send;
	}	

}
