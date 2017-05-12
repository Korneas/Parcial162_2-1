package parcial2_cliente;

import java.util.Observable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;


public class Cliente extends Observable implements Runnable {

	private Socket s;
	private static final String ADDRESS = "127.0.0.1";
	private static final int PORT = 5000;
	private boolean life;

	public Cliente() {
		s = null;
		life = true;
	}

	@Override
	public void run() {
		while (life) {

			try {
				if (s == null) {
					s = new Socket(InetAddress.getByName(ADDRESS), PORT);
				} else {
					recibir();
				}

				Thread.sleep(500);

			} catch (IOException e) {
				life = false;
				System.out.println("No hay conexion");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void recibir() throws IOException, ClassNotFoundException {
		ObjectInputStream in = new ObjectInputStream(s.getInputStream());
		Object o = in.readObject();
		setChanged();
		notifyObservers(o);
		clearChanged();
	}

	public void enviar(Object o) throws IOException {
		ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
		out.writeObject(o);
		out.flush();
	}
}