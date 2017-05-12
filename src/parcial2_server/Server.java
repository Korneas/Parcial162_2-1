package parcial2_server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import serial.Mensaje;

public class Server implements Observer, Runnable {

	// Puerto en el que se archivaran los elementos de red
	private static final int PORT = 5000;

	// ServerSocket para definir al servidor
	private ServerSocket sS;

	// Vida del servidor para el while este en constante ejecucion
	private boolean life;

	// Lista de controles de recibiran y enviaran datos a el usuario
	// correspondiente
	private ArrayList<Control> users;

	// Entero que determina al usuario que esta en accion en el momento
	private int currentUser;

	public Server() {

		life = true;
		users = new ArrayList<Control>();

		// Se crea el servidor con el respectivo puerto
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
				// Si se conecta alguien agregarlo a un control para tener la
				// comunicacion con el cliente desde esa clase
				System.out.println("Esperando...");
				users.add(new Control(sS.accept(), this, users.size() + 1));

				// Cada vez que se conecte alguien se le enviara a todos los
				// usuarios los usuarios conectados
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

			// Si un control se desconecta envia este String lo cual lo elimina
			// de la lista y actualiza a los usuarios conectados
			if (msg.contains("finConexion")) {
				users.remove(o);
				System.out.println("Clientes restantes: " + users.size());
				for (int i = 0; i < users.size(); i++) {
					Control cont = users.get(i);
					try {
						cont.enviar(new Mensaje("total", users.size()));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}

		if (arg instanceof Mensaje) {
			Mensaje m = (Mensaje) arg;

			// Si un usuario llego se recibe el mensaje para que comience el
			// siguiente
			if (m.getMsg().contains("finish")) {
				System.out.println("Acabo el jugador " + m.getSend());
				if (currentUser < users.size() - 1) {
					currentUser++;
				} else {
					currentUser = 0;
				}

				try {
					users.get(currentUser).enviar(new Mensaje("comenzar", 0));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
