package it.uniroma3.dia.tknn.model;

import java.util.LinkedList;
import java.util.List;

public class Record implements Comparable<Record>, Cloneable{

	private String data; 
	
	private String structure; 
	private String className; 
	private double k;
	private String predictedClass; 
	private List<Node> tree; 
	
	private SparseFeatureVector paumVector; 
	
	public List<Node> getTree() {
		return tree;
	}
	public void setTree(List<Node> tree) {
		this.tree = tree;
	}
	public Record() {
		super();
		this.className = "";
		this.data = "";
		this.k = 0.0;
		this.predictedClass = ""; 
		this.tree = new LinkedList<Node>(); 
		this.paumVector = new SparseFeatureVector(); 
	}
	public Record(String className, String data, double k) {
		super();
		this.className = className;
		this.data = data;
		this.k = k;
		this.tree = new LinkedList<Node>();
		this.paumVector = new SparseFeatureVector();
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public double getK() {
		return k;
	}
	public void setK(double k) {
		this.k = k;
	}
	public int compareTo(Record arg0) {
		return (new Double(this.getK())).compareTo(new Double(arg0.getK()));
	}
	public String getPredictedClass() {
		return predictedClass;
	}
	public void setPredictedClass(String predictedClass) {
		this.predictedClass = predictedClass;
	} 
	public Record clone(){
		Record r = new Record(); 
		r.setClassName(this.getClassName()); 
		r.setData(this.getData()); 
		r.setK(this.getK()); 
		r.setPredictedClass(this.getPredictedClass()); 
		r.setStructure(this.getStructure()); 
		
		List<Node> tree = new LinkedList<Node>(); 
		
		for (Node node : this.tree) {
			tree.add(node.clone()); 
		}
		r.setTree(tree); 
		
		r.setPaumVector(this.paumVector.clone()); 
		return r;
		
	}
	public String getStructure() {
		return structure;
	}
	public void setStructure(String structure) {
		this.structure = structure;
	}
	public SparseFeatureVector getPaumVector() {
		return paumVector;
	}
	public void setPaumVector(SparseFeatureVector paumVector) {
		this.paumVector = paumVector;
	}
	
	
}
