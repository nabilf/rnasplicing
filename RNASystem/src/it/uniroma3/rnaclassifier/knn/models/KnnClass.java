package it.uniroma3.rnaclassifier.knn.models;

import it.uniroma3.rnasystem.model.RNAClass;


public class KnnClass implements Comparable<KnnClass>{


	private RNAClass className; 
	private int frequency;
	
	public KnnClass() {
		super();
	}

	public KnnClass(RNAClass className, int frequency) {
		super();
		this.className = className;
		this.frequency = frequency;
	}

	public KnnClass(RNAClass n) {
		super();
		this.className = n;
		this.frequency = 0;
	}


	public RNAClass getClassName() {
		return className;
	}

	public void setClassName(RNAClass className) {
		this.className = className;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public int compareTo(KnnClass o) {
		return (new Integer (o.getFrequency()).compareTo(this.frequency));
	}

	public String toString() {
		return this.className.toString();
	}

}
