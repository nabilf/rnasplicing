package it.uniroma3.rnafolding.interfaces;

import it.uniroma3.rnafolding.exception.RNAFoldingException;

public interface FoldingAlgorithm {

	public abstract String execute(String sequence) throws RNAFoldingException; 
}
