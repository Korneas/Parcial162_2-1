package parcial2_cliente;

import java.util.Observable;
import java.util.Observer;

import processing.core.PApplet;
import serial.Iniciar;
import serial.Mensaje;

public class Logica implements Observer {

	private PApplet app;
	private Elemento elem;
	private Cliente c;
	private boolean online;

	private float[] col;
	private int id;
	private boolean actual;
	private int suma;

	public Logica(PApplet app) {
		super();
		this.app = app;

		c = new Cliente();
		new Thread(c).start();
		c.addObserver(this);

		if (col == null) {
			col = new float[3];
		}
	}

	public void ejecutar() {
		app.background(255);
		if (online) {
			if (actual) {
				elem.pintar();
				elem.mover(suma);
				if (suma <= 450) {
					suma++;
				} else {

				}
			}
		}
	}

	@Override
	public void update(Observable o, Object arg) {

		if (!online) {
			online = true;
		}

		if (arg instanceof Iniciar) {
			Iniciar in = (Iniciar) arg;
			col = in.getColor();
			id = in.getId();
			System.out.println("Mi id es: " + id);
			elem = new Elemento(app, col, id);
		} else if (arg instanceof Mensaje) {
			Mensaje m = (Mensaje) arg;

			if (m.getMsg().contains("comenzar")) {
				actual = true;
			}
		}

	}
}
