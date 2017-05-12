package parcial2_server;

public class MainServer {
	public static void main(String[] args) {
		/**
		 * Se crea instancia del servidor y se corre el hilo
		 */
		Server s = new Server();
		new Thread(s).start();
	}
}
