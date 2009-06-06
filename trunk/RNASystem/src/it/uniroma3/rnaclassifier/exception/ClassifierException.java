package it.uniroma3.rnaclassifier.exception;

public class ClassifierException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public ClassifierException() {
		super();
	}
	
	public ClassifierException(String message) {
		super(message);
	}
}
