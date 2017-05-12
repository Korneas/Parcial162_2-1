package parcial2_cliente;

import processing.core.PApplet;

public class MainCliente extends PApplet {

	private Logica log;

	public static void main(String[] args) {
		PApplet.main("parcial2_cliente.MainCliente");
	}

	@Override
	public void settings() {
		size(300, 500);
	}

	@Override
	public void setup() {
		log = new Logica(this);
	}

	@Override
	public void draw() {
		log.ejecutar();
	}

}