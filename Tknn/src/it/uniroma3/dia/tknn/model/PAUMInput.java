package it.uniroma3.dia.tknn.model;

public class PAUMInput {

	//SparseFeatureVector [] dataLearning, int totalNumFeatures, short [] classLabels, int numTraining
	private SparseFeatureVector[] vector; 
	private int totNumFeatures; 
	private short[] classLabels; 
	private int numTraining;
	
	public PAUMInput(short[] classLabels, int numTraining, int totNumFeatures,
			SparseFeatureVector[] vector) {
		super();
		this.classLabels = classLabels;
		this.numTraining = numTraining;
		this.totNumFeatures = totNumFeatures;
		this.vector = vector;
	}

	public SparseFeatureVector[] getVector() {
		return vector;
	}

	public void setVector(SparseFeatureVector[] vector) {
		this.vector = vector;
	}

	public int getTotNumFeatures() {
		return totNumFeatures;
	}

	public void setTotNumFeatures(int totNumFeatures) {
		this.totNumFeatures = totNumFeatures;
	}

	public short[] getClassLabels() {
		return classLabels;
	}

	public void setClassLabels(short[] classLabels) {
		this.classLabels = classLabels;
	}

	public int getNumTraining() {
		return numTraining;
	}

	public void setNumTraining(int numTraining) {
		this.numTraining = numTraining;
	} 
	
	
}
