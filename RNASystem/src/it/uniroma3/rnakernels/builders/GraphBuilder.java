package it.uniroma3.rnakernels.builders;

import it.uniroma3.rnakernels.exception.RNABuilderException;
import it.uniroma3.rnakernels.models.Edge;
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

	public GraphBuilder() {
		super();
	}

	@Override
	public List<Node> execute() throws RNABuilderException {

		String graph = this.getGraphStructure();
		graph = "E3(I2(H2)I1)M1(H8)M3(I2(I1((H2)B3)I2)I3)";

		return new LinkedList<Node>();
	}

	private void buildSubGraph(Element el, Node node) {
		System.out.println("BUILD SUB GRAPH");
		
		String structure = el.getStructure();
		String sequence = el.getBases(); 
		
		while(structure.length()>0){
			System.out.println("[Actual] "+el.getBases());
			
			int start = 0;
			int end = structure.length() - 1;
			Edge e = new Edge(start, end); 
			int stemSize = 1; 
			//Fino a che gli indici incontrano parentesi
			while( structure.charAt(start) != '.' && structure.charAt(end) != '.'){	
				start++; 
				end--; 
				stemSize++; 
			}
			e.setStemSize(stemSize); 
			System.out.println("E "+(e.getJ()-stemSize));
			e.setBases((el.getBases().substring(e.getI(), e.getI()+stemSize-1)+(el.getBases().substring(e.getJ()-stemSize+1, e.getJ())))); 
			System.out.println(e.toString()); 
			break; 
		}
	}
	
	private String getMacroStructure(String sequence,
			List<Element> macroStructure, String structure, int offSet, String node) {
		
		//System.out.println("[Actual] "+structure);
		//System.out.println("[Actual] "+sequence); 
		
		if (structure.length() > 0) {
			int start = structure.indexOf("(");
			
			if (start != -1) {
				//Se la prima parentesi non  all'inizio, allora la sequenza 
				//inizia con i punti quindi comincio a costruire il nodo
				if(start>0){
					node += sequence.substring(0, start); 
					System.out.println("found NODE "+node);
				}
				int openBracket = 1;
				int closeBracket = 0;

				int i = start + 1;

				while (i < structure.length() && openBracket != closeBracket) {
					if (structure.charAt(i) == '(') {
						openBracket++;
					} else if (structure.charAt(i) == ')') {
						closeBracket++;
					}
					i++;
				}
				
//				System.out.println();
//				System.out.println("[Actual] Start "+start);
//				System.out.println("[Actual] End "+i);
//				System.out.println("[Actual] "+structure.substring(start, i));
//				System.out.println("[Actual] "+sequence.substring(start, i));	
//				System.out.println();
//				System.out.println("[Absolute] "+this.structure.substring(offSet+start, offSet+i));
//				System.out.println("[Absolute] "+this.sequence.substring(offSet+start, offSet+i));
//				System.out.println();				

				macroStructure.add(new Element(offSet + start, offSet + i,this.sequence.substring(offSet + start, offSet + i), this.structure.substring(offSet + start, offSet + i)));
				
				node = getMacroStructure(sequence.substring(i), macroStructure, structure.substring(i), offSet, node);
			}
		}
		return node;
	}

	public static void main(String[] args) {
		String sequence = "XXCCACTGACAYYGGTACTATGCAGGAAAGAATTACCACTACCAAGAAGGGATCTATCACCTCTGTACAGGTAAGAAAAATTACATAGATGAAGATCTGATTTGTATAAAGGCAGGGTGCAGTGGTGCATCTCAGCTACT";
			String a = 	  "((.(.(.)..)))"; 
		String structure = ".."
				+ "((.(.(.)..)))"
				+ ".."
				+ "(...)"
				+ "(.)"
				+ ".........."
				+ "((.(..((..)..))(..(...(.((.(.(..).))...(........)...)..)..)..).)....)"
				+ "........" 
				+"(.)"
				+"..." 
				+ "((.).((.()..).(.))..)" 
				+ ".";
		//printSequence(sequence);
		//printSequence(structure);
		GraphBuilder b = new GraphBuilder(sequence, structure);
		List<Element> macroStructure =  new ArrayList<Element>(); 
		
		String node = 	b.getMacroStructure(sequence, macroStructure,structure, 0, "");
		Node root = new Node(node);
		b.buildSubGraph(macroStructure.get(0), root); 
//		System.out.println("SIZE "+macroStructure.size());
//		System.out.println("NODE "+node);
//		System.out.println("End");

	}

	public static void printSubSequence(String s, int i, int j){
		for(int c = i; c<j; c++){
			System.out.print(s.charAt(c));
		}
	}
	public static void printSequence(String s) {

		for (int i = 0; i < s.length(); i++) {
			System.out.print(s.charAt(i) + "\t");
		}
		System.out.println("\n");
		for (int i = 0; i < s.length(); i++) {
			System.out.print(i + "\t");
		}
		System.out.println("\n");
	}

	public String getGraphStructure() {
		String gf = this.structure;
		// Find Structure ;
		gf = RegexManager.applyAndReplace(
				RNASystemConfiguration.RE_HAIRPIN_LOOP, gf,
				RNASystemConfiguration.HAIRPIN_LOOP,
				RNASystemConfiguration.OPEN_BRACKET,
				RNASystemConfiguration.CLOSE_BRACKET);
		// gf = RegexManager.applyAndReplace(
		// RNASystemConfiguration.RE_RIGHT_BULGE_LOOP,
		// gf,
		// RNASystemConfiguration.BULGE_LOOP,
		// RNASystemConfiguration.OPEN_BRACKET,
		// RNASystemConfiguration.OPEN_BRACKET);
		// gf = RegexManager.applyAndReplace(
		// RNASystemConfiguration.RE_LEFT_BULGE_LOOP,
		// gf,
		// RNASystemConfiguration.BULGE_LOOP,
		// RNASystemConfiguration.CLOSE_BRACKET,
		// RNASystemConfiguration.CLOSE_BRACKET);
		// gf = RegexManager.applyAndReplace(
		// RNASystemConfiguration.RE_INTERNAL_LOOP,
		// gf,
		// RNASystemConfiguration.INTERNAL_LOOP,
		// RNASystemConfiguration.OPEN_BRACKET,
		// RNASystemConfiguration.CLOSE_BRACKET);
		gf = RegexManager.applyAndReplace(RNASystemConfiguration.RE_MULTI_LOOP,
				gf, RNASystemConfiguration.MULTI_LOOP,
				RNASystemConfiguration.CLOSE_BRACKET,
				RNASystemConfiguration.OPEN_BRACKET);
		System.out.println(gf);
		log.debug("Graph Structure : " + gf);
		return gf;
	}
}
