package it.uniroma3.rnaclassifier.model;

public abstract class ClassifierInput {

	protected ClassifierSetup setup;

	public ClassifierSetup getSetup() {
		return setup;
	}

	public void setSetup(ClassifierSetup setup) {
		this.setup = setup;
	} 
	
	
}
