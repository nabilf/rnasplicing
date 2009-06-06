package it.uniroma3.rnaclassifier.paum.models;

import it.uniroma3.rnaclassifier.model.ClassifierOutput;

public class PAUMOutput extends ClassifierOutput{
	private float[] w; 
	private float b;
	
	
	
	public PAUMOutput(float b, float[] w) {
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
