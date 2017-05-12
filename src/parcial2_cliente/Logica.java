package parcial2_cliente;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import processing.core.PApplet;
import serial.Iniciar;
import serial.Mensaje;

public class Logica implements Observer {

	// Referencia a PApplet
	private PApplet app;

	// Elemento en movimiento del lienzo
	private Elemento elem;

	// Comunicacion del cliente
	private Cliente c;

	// Si el cliente si esta conectado con el servidor
	private boolean online;

	// Lista de colores RGB que tendra la interfaz
	private float[] col;

	// Indetificador del cliente
	private int id;

	// Si actualmente el usuario esta en accion
	private boolean actual;

	// Valor a sumar para su movimiento
	private int suma;

	// Numero de conectados actualmente al servidor
	private int totalConectados;

	public Logica(PApplet app) {
		super();
		this.app = app;

		// Se crea el cliente, se corre el hilo y se agrega logica como
		// observador
		c = new Cliente();
		new Thread(c).start();
		c.addObserver(this);

		// Si no hay color se crea un color negro
		if (col == null) {
			col = new float[3];
		}
	}

	public void ejecutar() {
		app.background(255);

		// Si esta conectado
		if (online) {

			// Si actualmente esta en accion
			if (actual) {

				// Se pinta y se mueve el elemento deseado
				elem.pintar();
				elem.mover(suma);
				if (suma <= 450) {
					suma++;
				} else {

					// Si ya llego al final se envia un mensaje al servidor
					try {
						c.enviar(new Mensaje("finish", id));
					} catch (IOException e) {
						e.printStackTrace();
					}

					// Ya deja de ser el usuario en accion
					actual = false;

					// Se resetea la suma actual del movimiento
					suma = 0;
				}
			} else {
				// Si el usuario no esta en accion se coloca en espera
				app.textAlign(PApplet.CENTER);
				app.text("En espera", app.width / 2, app.height / 2);
			}

			// Total de usuarios
			app.textAlign(PApplet.RIGHT);
			app.text("Total de usuarios: " + totalConectados, 290, 480);
		}
	}

	@Override
	public void update(Observable o, Object arg) {

		if (!online) {
			// Si llega cualquier tipo de Object se sabe que esta conectado por
			// lo que se vuelve true
			online = true;
		}

		if (arg instanceof Iniciar) {
			// El objeto Iniciar da la lista RGB de color de la interfaz y el
			// id, y crea el objeto
			Iniciar in = (Iniciar) arg;
			col = in.getColor();
			id = in.getId();
			System.out.println("Mi id es: " + id);
			elem = new Elemento(app, col, id);
		} else if (arg instanceof Mensaje) {
			Mensaje m = (Mensaje) arg;

			if (m.getMsg().contains("comenzar")) {
				// Si recibe comenzar quiere decir que es el usuario activo
				// actualmente
				actual = true;
				System.out.println("Estoy activo " + id);
				elem.mover(0);
			} else if (m.getMsg().contains("total")) {
				// Recibe el total de usuarios conectados
				totalConectados = m.getSend();
			}
		}

	}
}
