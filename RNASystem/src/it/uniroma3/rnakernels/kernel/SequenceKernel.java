package it.uniroma3.rnakernels.kernel;

import it.uniroma3.rnakernels.exception.RNAKernelException;
import it.uniroma3.rnakernels.metrics.LevenshteinDistance;
import it.uniroma3.rnasystem.model.RNASequence;

public class SequenceKernel implements IKernel{
	
	public double execute(RNASequence a, RNASequence b,boolean normalize) throws RNAKernelException {
	
		LevenshteinDistance ld = new LevenshteinDistance(a.getSequence(),b.getSequence(), normalize); 
		ld.execute(); 
		return ld.getResult(); 	
		
	}
}
