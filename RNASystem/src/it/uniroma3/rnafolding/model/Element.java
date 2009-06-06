package it.uniroma3.rnafolding.model;

public class Element {
	protected int I; 
	protected int J;
	protected Integer level; 
	protected String bases;	 
	
	public Element(int i, int j) {
		this.I = i;
		this.J = j;
		this.level = 0; 
		this.bases = ""; 
	}
	public Element() {
		this.I = 0;
		this.J = 0;
		this.level = 0; 
		this.bases = ""; 
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
