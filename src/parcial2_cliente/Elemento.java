package parcial2_cliente;

import processing.core.PApplet;

public class Elemento {

	private PApplet app;
	private int x, y, diam;
	private float[] col;

	public Elemento(float[] col) {
		this.col = col;
	}

	public void pintar() {
		app.fill(col[0], col[1], col[2]);
		app.ellipse(x, y, diam, diam);
	}

	public void mover(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
