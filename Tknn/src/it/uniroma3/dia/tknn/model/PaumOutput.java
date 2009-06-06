package it.uniroma3.dia.tknn.model;

public class PaumOutput {

	private float[] w; 
	private float b;
	public PaumOutput(float b, float[] w) {
		super();
		this.b = b;
		this.w = w;
	}
	public float[] getW() {
		return w;
	}
	public void setW(float[] w) {
		this.w = w;
	}
	public float getB() {
		return b;
	}
	public void setB(float b) {
		this.b = b;
	} 
	
	
}
