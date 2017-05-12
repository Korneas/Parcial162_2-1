package parcial2_server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import serial.Mensaje;

public class Server extends Observable implements Observer, Runnable {

	private static final int PORT = 5000;
	private ServerSocket sS;
	private boolean life;
	private ArrayList<Control> users;

	public Server() {

		life = true;
		users = new ArrayList<Control>();

		try {
			sS = new ServerSocket(PORT);
			System.out.println("Servidor iniciado");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (life) {
			try {
				System.out.println("Esperando...");
				users.add(new Control(sS.accept(), this, users.size() + 1));
				for (int i = 0; i < users.size(); i++) {
					Control cont = users.get(i);
					cont.enviar(new Mensaje("total", users.size()));
				}
				System.out.println("Nuevo usuario es: " + users.size());
				Thread.sleep(100);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof String) {
			String msg = (String) arg;

			if (msg.contains("finConexion")) {
				users.remove(o);
				System.out.println("Clientes restantes: " + users.size());
			}
		}
	}

}
