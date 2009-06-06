package it.uniroma3.rnaclassifier.paum.exception;


public class PAUMInputException extends PAUMException{
	private static final long serialVersionUID = 1L;
	
	public PAUMInputException() {
		super();
	}
	
	public PAUMInputException(String variable) {
		super("[Object PAUMInput] Variabile "+variable+" non impostata correttamente");
	}
	public PAUMInputException(String variable, String value){
		super("[Object PAUMInput] Valore "+value+" per variabile "+variable+" non ammesso");
	}
	
}
