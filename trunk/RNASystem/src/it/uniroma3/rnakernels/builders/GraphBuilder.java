package it.uniroma3.rnakernels.builders;

import it.uniroma3.rnakernels.exception.RNABuilderException;
import it.uniroma3.rnakernels.models.Element;
import it.uniroma3.rnakernels.models.Node;
import it.uniroma3.rnakernels.regex.RegexManager;
import it.uniroma3.rnasystem.setup.RNASystemConfiguration;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;


public class GraphBuilder extends Builder {
	private static Logger log = Logger.getLogger(TreeBuilder.class);

	private String graphStructure; 
	
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
		
		String graph = this.getGraphStructure(); 
		graph = "E3(I2(H2)I1)M1(H8)M3(I2(I1((H2)B3)I2)I3)"; 
		
		return new LinkedList<Node>(); 
	}
	
	private static void buildSubGraph(Element e){
		
	}
	
	private static List<Element> getMacroStructure(String sequence, List<Element> macroStructure,String structure, int offSet){
		
		if(macroStructure == null){
			macroStructure = new ArrayList<Element>(); 
		}
		if(structure.length()> 0){
			int start=structure.indexOf("("); 
			
			if(start!=-1){
				int openBracket = 1; 
				int closeBracket = 0; 
				
				int i=start+1; 
				
				while(i<structure.length() && openBracket!= closeBracket){
					if(structure.charAt(i) == '('){
						openBracket++;  
					}else if(structure.charAt(i) == ')'){
						closeBracket++; 
					}
					i++; 
				}
				
				macroStructure.add(new Element(start, i, sequence.substring(start, i))); 
				log.debug(structure.substring(start,i));
				log.debug(sequence.substring(start, i));
				getMacroStructure(sequence, macroStructure, structure.substring(i), i-1); 
			}
		}
		return macroStructure; 
	}
	
	
	public static void main(String[] args) {
		String sequence = "GGCCACTGACATGGGTACTATGCAGGAAAGAATTACCACTACCAAGAAGGGATCTATCACCTCTGTACAGGTAAGAAAAATTACATAGATGAAGATCTGATTTGTATAAAGGCAGGGTGCAGTGGTGCATCTCAGCTACT"; 
	 
		String structure = ".." +
		"((.(.(.)..)))" +
		".." +
		"(...)" +
		"(.)" +
		".........." +
		"((.(..((..)..))(..(...(.((.(.(..).))...(........)...)..)..)..).)....)" +
		"........" +
		"(.)" +
		"..." +
		"((.).((.()..).(.))..)"+
		".";
		printSequence(sequence); 
		printSequence(structure); 
		GraphBuilder b = new GraphBuilder(sequence,structure); 
		getMacroStructure(sequence, null, structure, 0); 
		//String s = b.getGraphStructure(); 
	}
	public static void printSequence(String s ){
		
		for (int i =0; i<s.length(); i++){
			System.out.print(s.charAt(i)+"\t");
		}
		System.out.println("\n");
		for (int i =0; i<s.length(); i++){
			System.out.print(i+"\t");
		}
		System.out.println("\n");
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public String getGraphStructure(){
		String gf = this.structure;
		//Find Structure ; 
		gf = RegexManager.applyAndReplace(
									RNASystemConfiguration.RE_HAIRPIN_LOOP, 
									gf, 
									RNASystemConfiguration.HAIRPIN_LOOP,
									RNASystemConfiguration.OPEN_BRACKET, 
									RNASystemConfiguration.CLOSE_BRACKET); 
//		gf = RegexManager.applyAndReplace(	RNASystemConfiguration.RE_RIGHT_BULGE_LOOP, 
//									gf, 
//									RNASystemConfiguration.BULGE_LOOP,
//									RNASystemConfiguration.OPEN_BRACKET, 
//									RNASystemConfiguration.OPEN_BRACKET);
//		gf = RegexManager.applyAndReplace(	RNASystemConfiguration.RE_LEFT_BULGE_LOOP, 
//									gf, 
//									RNASystemConfiguration.BULGE_LOOP,
//									RNASystemConfiguration.CLOSE_BRACKET, 
//									RNASystemConfiguration.CLOSE_BRACKET);
//		gf = RegexManager.applyAndReplace(	RNASystemConfiguration.RE_INTERNAL_LOOP, 
//									gf, 
//									RNASystemConfiguration.INTERNAL_LOOP, 
//									RNASystemConfiguration.OPEN_BRACKET, 
//									RNASystemConfiguration.CLOSE_BRACKET);
		gf = RegexManager.applyAndReplace(	RNASystemConfiguration.RE_MULTI_LOOP, 
									gf, 
									RNASystemConfiguration.MULTI_LOOP, 
									RNASystemConfiguration.CLOSE_BRACKET, 
									RNASystemConfiguration.OPEN_BRACKET);
		System.out.println(gf);
		log.debug("Graph Structure : "+gf); 
		return gf; 
	}
}
