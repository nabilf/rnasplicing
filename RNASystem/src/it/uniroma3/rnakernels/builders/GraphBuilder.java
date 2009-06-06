package it.uniroma3.rnakernels.builders;

import it.uniroma3.rnakernels.exception.RNABuilderException;
import it.uniroma3.rnakernels.models.Node;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

public class GraphBuilder extends Builder {
	public static Logger log = Logger.getLogger(TreeBuilder.class);

	public GraphBuilder(String sequence, String structure) {
		super();
		super.sequence = sequence;
		super.structure = structure;
	}
	public GraphBuilder(){
		super(); 
	}
	@Override
	public List<Node> execute() throws RNABuilderException {
		return new LinkedList<Node>(); 
	}
	
}
