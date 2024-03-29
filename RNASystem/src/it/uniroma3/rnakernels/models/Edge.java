package it.uniroma3.rnakernels.models;

import java.io.Serializable;


public class Edge extends Element implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int stemSize; //Numero di parentesi che compongono l'arco ( size dello Stem)
	private Node destination;
	
	private boolean removed; //Flag di rimozione in caso di arco consecutivo
	
	public Edge(int i, int j, int stemSize, String bases) {
		super(i, j); 
		this.stemSize =stemSize;
		this.removed = false; 
		super.bases = bases; 
	}

	public Edge(int i, int j) {
		super(i, j); 
		this.stemSize =0;
		this.removed = false; 
	}
	public Edge() {
		super(0, 0); 
		this.stemSize =0;
		this.removed = false; 
		super.bases = ""; 
	}

	public boolean isRemoved() {
		return removed;
	}

	public void setRemoved(boolean removed) {
		this.removed = removed;
	}
	public int getStemSize() {
		return stemSize;
	}

	public void setStemSize(int stemSize) {
		this.stemSize = stemSize;
	}

	public Node getDestination() {
		return destination;
	}

	public void setDestination(Node destination) {
		this.destination = destination;
	}

	public String toString() {
		return super.toString();
	}

}
