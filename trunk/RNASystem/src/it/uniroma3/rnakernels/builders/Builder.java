package it.uniroma3.rnakernels.builders;

import it.uniroma3.rnakernels.exception.RNABuilderException;
import it.uniroma3.rnakernels.models.Node;

import java.util.List;

public abstract class Builder {
	protected String sequence; 
	protected String structure; 
	
	public Builder(String sequence, String structure){
		this.sequence = sequence; 
		this.structure = structure; 
	}
	public Builder() {
		this.sequence=""; 
		this.structure=""; 
	}
	public abstract List<Node>  execute() throws RNABuilderException;
	
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

	
	
	
}
