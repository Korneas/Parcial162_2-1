package parcial2_server;

public class MainServer {
	public static void main(String[] args) {
		Server s = new Server();
		new Thread(s).start();
	}
}
