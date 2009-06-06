package it.uniroma3.rnakernels.models;

public class BasePair {

	private String bases; 
	private Double prob; 
	
	private String conversion;

	public BasePair(String bases, String conversion, Double prob) {
		super();
		this.bases = bases;
		this.conversion = conversion;
		this.prob = prob;
	}

	public BasePair() {
		this.bases = "";
		this.conversion = "";
		this.prob = 0.0;
	}

	public String getBases() {
		return bases;
	}

	public void setBases(String bases) {
		this.bases = bases;
	}

	public Double getProb() {
		return prob;
	}

	public void setProb(Double prob) {
		this.prob = prob;
	}

	public String getConversion() {
		return conversion;
	}

	public void setConversion(String conversion) {
		this.conversion = conversion;
	} 
	
}
