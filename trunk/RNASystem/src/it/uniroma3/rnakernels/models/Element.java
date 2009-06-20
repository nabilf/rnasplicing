package it.uniroma3.rnakernels.models;

import java.io.Serializable;

public class Element implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Integer I; 
	protected Integer J;
	protected Integer level; 
	protected String bases;	 
	
	public Element(int i, int j, String bases) {
		this.I = i;
		this.J = j;
		this.level = 0; 
		this.bases = bases; 
	}
	public Element() {
		this.I = 0;
		this.J = 0;
		this.level = 0; 
		this.bases = ""; 
	}
	
	public Element(int i2, int j2) {
		this.I = i2;
		this.J = j2;
	}
	public String getBases() {
		return bases;
	}
	public void setBases(String bases) {
		this.bases = bases;
	}
	public int getI() {
		return I;
	}
	public void setI(int i) {
		I = i;
	}
	public int getJ() {
		return J;
	}
	public void setJ(int j) {
		J = j;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	
}
