package parcial2_cliente;

import processing.core.PApplet;

public class Elemento {

	private PApplet app;
	private int x, y, diam;
	private float[] col;

	public Elemento(PApplet app, float[] col, int pos) {
		this.app = app;
		this.col = col;
		this.x = 1;
		this.x *= (pos * 20);
		this.diam = 50;
	}

	public void pintar() {
		app.noStroke();
		app.fill(col[0], col[1], col[2]);
		app.ellipse(x, y, diam, diam);
	}

	public void mover(int y) {
		this.y = y;
	}
}
