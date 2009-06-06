package it.uniroma3.rnaclassifier.knn.exception;

import it.uniroma3.rnaclassifier.exception.ClassifierException;

public  class KNNException extends ClassifierException{
	private static final long serialVersionUID = 1L;
	
	public KNNException() {
		super();
	}
	
	public KNNException(String message) {
		super(message);
	}
}
