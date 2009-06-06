package it.uniroma3.dia.tknn.model;

public class RNAClass implements Comparable<RNAClass>{

	private RNAClassEnum className; 
	private int frequency;
	
	public RNAClass() {
		super();
	}

	public RNAClass(RNAClassEnum className, int frequency) {
		super();
		this.className = className;
		this.frequency = frequency;
	}

	public RNAClass(RNAClassEnum n) {
		super();
		this.className = n;
		this.frequency = 0;
	}


	public RNAClassEnum getClassName() {
		return className;
	}

	public void setClassName(RNAClassEnum className) {
		this.className = className;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public int compareTo(RNAClass o) {
		return (new Integer (o.getFrequency()).compareTo(this.frequency));
	}

	
}
