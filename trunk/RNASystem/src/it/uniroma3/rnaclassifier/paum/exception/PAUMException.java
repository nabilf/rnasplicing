package it.uniroma3.rnaclassifier.paum.exception;

import it.uniroma3.rnaclassifier.exception.ClassifierException;

public  class PAUMException extends ClassifierException{
	private static final long serialVersionUID = 1L;
	
	public PAUMException() {
		super();
	}
	
	public PAUMException(String message) {
		super(message);
	}
}
