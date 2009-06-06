package it.uniroma3.rnaclassifier.knn.models;

import it.uniroma3.rnaclassifier.model.ClassifierSetup;

public class KNNSetup extends ClassifierSetup {

	private int k;

	public KNNSetup(int k) {
		super();
		this.k = k;
	}

	public int getK() {
		return k;
	}

	public void setK(int k) {
		this.k = k;
	} 
	
	
}
