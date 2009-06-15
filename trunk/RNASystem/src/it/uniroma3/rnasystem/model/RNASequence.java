package it.uniroma3.rnasystem.model;

import it.uniroma3.rnakernels.models.Node;

import java.io.Serializable;
import java.util.List;

public class RNASequence implements Serializable, Comparable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String md5ID; 
	private RNAClass realClass;
	private String sequence; 
	
	private String structure;
	private String extendedStructure; 
	
	private Double distance; 
	private RNAClass predictedClass; 
	
	private List<Node> graph; 

	public RNASequence(RNAClass realClass, String sequence, String structure) {
		super();
		this.md5ID = ""; 
		this.realClass = realClass;
		this.sequence = sequence;
		this.structure = structure;
		this.distance = 0D; 
		this.predictedClass = RNAClass.NONE; 
	}
	
	public RNASequence() {
		super();
		this.md5ID=""; 
		this.realClass = RNAClass.NONE;
		this.sequence = "";
		this.structure = "";
		this.distance = 0D; 
		this.predictedClass = RNAClass.NONE; 
	}
	
	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public List<Node> getGraph() {
		return graph;
	}

	public void setGraph(List<Node> graph) {
		this.graph = graph;
	}

	public String getMd5ID() {
		return md5ID;
	}

	public void setMd5ID(String md5ID) {
		this.md5ID = md5ID;
	}

	public String getExtendedStructure() {
		return extendedStructure;
	}

	public void setExtendedStructure(String extendedStructure) {
		this.extendedStructure = extendedStructure;
	}

	public RNAClass getPredictedClass() {
		return predictedClass;
	}

	public void setPredictedClass(RNAClass predictedClass) {
		this.predictedClass = predictedClass;
	}

	public RNAClass getRealClass() {
		return realClass;
	}

	public void setRealClass(RNAClass realClass) {
		this.realClass = realClass;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public String getStructure() {
		return structure;
	}

	public void setStructure(String structure) {
		this.structure = structure;
	}

	public String toString() {
		return "\n REAL Class: "+ this.realClass+"; \n SEQUENCE: "+this.sequence+"; \n STRUCTURE: "+this.structure;
	}

	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	} 
	
}
