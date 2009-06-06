package it.uniroma3.rnakernels.kernel;

import it.uniroma3.rnakernels.exception.RNAKernelException;
import it.uniroma3.rnasystem.model.RNASequence;

public interface IKernel {

	public double execute(RNASequence rna, RNASequence rna2, boolean normalize) throws RNAKernelException; 
}
