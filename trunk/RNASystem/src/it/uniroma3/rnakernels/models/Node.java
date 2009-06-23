package it.uniroma3.rnakernels.models;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Node extends Element implements Cloneable, Serializable{
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Edge> edges;
	private List<Element> element; 
	
	public Node(List<Edge> edges, String bases) {
		super.bases = bases; 
		this.edges = edges; 
	}
	public Node(String bases){
		super(); 
		super.bases = bases; 
		this.edges = new LinkedList<Edge>();
		this.element = new LinkedList<Element>(); 
	}
	public Node() {
		super(); 
		this.edges = new LinkedList<Edge>();
		this.element = new LinkedList<Element>(); 
	}

	public List<Edge> getEdges() {
		return edges;
	}

	public void setEdges(List<Edge> edges) {
		this.edges = edges;
	}

	public List<Element> getElement() {
		return element;
	}

	public void setElement(List<Element> element) {
		this.element = element;
	}

	public List<Edge> getChilds() {
		return edges;
	}


	public void setChilds(List<Edge> edges) {
		this.edges = edges;
	}
	public Node clone(){
		Node n = new Node(); 
		n.setI(this.getI()); 
		n.setJ(this.getJ()); 
		n.setLevel(this.getLevel()); 
		n.setBases(this.getBases()); 
		return n; 
	}
	
}
