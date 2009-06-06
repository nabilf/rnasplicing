package it.uniroma3.dia.tknn.kernel;

import it.uniroma3.dia.tknn.algorithm.Algorithm;
import it.uniroma3.dia.tknn.algorithm.LevenshteinDistance;
import it.uniroma3.dia.tknn.exception.KernelException;

public class SequenceKernel implements IKernel{
	private String a; 
	private String b; 
	
	private double k; 
	private double c; 
	
	public SequenceKernel(String a, String b){
		this.a = a; 
		this.b = b; 
	}

	public double getK() {
		return k;
	}

	public void setK(double k) {
		this.k = k;
	}

	public void execute() throws KernelException {
	
		LevenshteinDistance ld = new LevenshteinDistance(a,b); 
		ld.execute(); 
		this.k = ld.getResult(); 	
		
	}
}
