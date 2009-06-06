package it.uniroma3.rnaclassifier.knn.exception;

public class KNNNullException extends KNNException{

	private static final long serialVersionUID = 1L;
	
	public KNNNullException() {
		super();
	}
	
	public KNNNullException(String variable) {
		super("Parameter "+variable+" is NULL");
	}
	
}
