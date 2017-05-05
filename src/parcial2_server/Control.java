package parcial2_server;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observer;

import serial.Iniciar;
import serial.Mensaje;

public class Control implements Runnable {
	private Socket s;
	private Observer boss;
	private int id;

	public Control(Socket s, Observer boss, int id) {
		this.s = s;
		this.boss = boss;
		this.id = id;
		new Thread(this).start();

		try {
			float[] col = new float[3];
			for (int i = 0; i < col.length; i++) {
				col[i] = (float) (Math.random() * (float) (255));
			}
			enviar(new Iniciar(col, id));
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (id == 1) {
			try {
				enviar(new Mensaje("comenzar", "server"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void run() {
		while (true) {
			try {
				recibir();
				Thread.sleep(100);
			} catch (IOException e) {
				e.printStackTrace();
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

	private void clasificar(Object o) {
		if (o instanceof Mensaje) {
			Mensaje m = (Mensaje) o;

		}
	}

	public void recibir() throws IOException, ClassNotFoundException {
		ObjectInputStream in = new ObjectInputStream(s.getInputStream());
		Object recibido = in.readObject();
		clasificar(recibido);
		boss.update(null, recibido);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}