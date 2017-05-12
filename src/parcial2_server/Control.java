package parcial2_server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

import serial.Iniciar;
import serial.Mensaje;

public class Control extends Observable implements Runnable {

	// Socket para la comunicacion con el cliente
	private Socket s;

	// Jefe observador del cliente
	private Observer boss;

	// Identificador del usuario
	private int id;
	private boolean life = true;

	public Control(Socket s, Observer boss, int id) {
		this.s = s;
		this.boss = boss;
		this.id = id;
		new Thread(this).start();

		// Se crea el color random para el cliente
		try {
			float[] col = new float[3];
			for (int i = 0; i < col.length; i++) {
				col[i] = (float) (Math.random() * (float) (255));
			}
			enviar(new Iniciar(col, id));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Si el cliente es el primero en conectarse entonces este comienza
		if (id == 1) {
			try {
				enviar(new Mensaje("comenzar", 0));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void run() {
		while (life) {
			try {
				recibir();
				Thread.sleep(100);
			} catch (IOException e) {
				System.out.println("Problema con cliente " + id);
				setChanged();
				boss.update(this, "finConexion");
				clearChanged();
				life = false;
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	public void enviar(Object o) throws IOException {
		ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
		out.writeObject(o);
		System.out.println("Cliente: Se envio: " + o.getClass());
	}

	private void recibir() throws IOException, ClassNotFoundException {
		ObjectInputStream in = new ObjectInputStream(s.getInputStream());
		Object recibido = in.readObject();
		boss.update(this, recibido);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}