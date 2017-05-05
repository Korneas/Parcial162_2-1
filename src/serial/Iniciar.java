package serial;

public class Iniciar {

	private float[] color;
	private int id;

	public Iniciar(float[] color, int id) {
		super();
		this.color = color;
		this.id = id;
	}

	public float[] getColor() {
		return color;
	}

	public void setColor(float[] color) {
		this.color = color;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
