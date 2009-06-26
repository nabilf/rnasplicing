package it.uniroma3.rnakernels.builders;

import it.uniroma3.rnakernels.exception.RNABuilderException;
import it.uniroma3.rnakernels.models.Edge;
import it.uniroma3.rnakernels.models.Element;
import it.uniroma3.rnakernels.models.Node;

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
		
		//this.getMacroStructure(sequence, new ArrayList<Element>(), structure, ""); 
		Node root = this.buildRecursive(this.structure.toString(), this.sequence.toString()); 
		
		return new LinkedList<Node>();
	}
	private Node buildRecursive(String structure, String sequence){
		System.out.println("**** NEW NODE ****");
		List<Element> macroStructure =  new ArrayList<Element>(); 
		
		Node n = new Node(this.getMacroStructure(sequence, macroStructure, structure, null)); 
		System.out.println("NODE "+n.getBases());
		for (Element macro : macroStructure) {
			System.out.println("Macro Structure "+macro.getStructure());
			System.out.println("Macro Sequence "+macro.getBases());
			Edge e = this.findEdge(macro); 
			n.getEdges().add(e);
			String nextStructure = macro.getStructure().substring(e.getI(),e.getJ()+1);
			
			String nextSequence = macro.getBases().substring(e.getI(),e.getJ()+1);
			e.setDestination(this.buildRecursive(nextStructure, nextSequence));  
		}
		return new Node(); 
	}
	private Edge findEdge(Element el) {			
		int start = 0;
		int end = el.getStructure().length() - 1;
		System.out.println(el.getStructure());
		Edge e = new Edge(); 
		
		int stemSize = 0; 
		char s = el.getStructure().charAt(start); 
		char en = el.getStructure().charAt(end);
		
		while(start != end)
		{
			if(el.getStructure().charAt(start) == '('){
				if(el.getStructure().charAt(end) ==')'){
					stemSize++; 
					start++; 
					end--; 
				}else{
					start = end; 
					//stemSize--; 
				}
			}else if(el.getStructure().charAt(start) == ')') {
				stemSize--; 
				start = end; 
			}else if(el.getStructure().charAt(end) == '('){
				stemSize--; 
				start = end; 
			}else if((el.getStructure().charAt(start) == '.') || (el.getStructure().charAt(end) == '.')){
				start = end; 
				stemSize--; 
			}
		}
		System.out.println("Stem size "+(stemSize));
		e.setStemSize(stemSize-1); 
		e.setI(start-1); 
		e.setJ(end+1); 
		
	//	e.setBases((el.getBases().substring(0,stemSize)+(el.getBases().substring(el.getStructure().length()-stemSize, el.getStructure().length())))); 
		e.setStructure((el.getStructure().substring(0,stemSize)+(el.getStructure().substring(el.getStructure().length()-stemSize, el.getStructure().length())))); 
		System.out.println("new EDGE "+e.toString());
		return e;  
	}
	
	private String getMacroStructure(String sequence,
			List<Element> macroStructure, String structure, String node) {
		
		if (structure.length() > 0) {
			int start = structure.indexOf("(");
			
			if (start != -1) {
				//Se la prima parentesi non  all'inizio, allora la sequenza 
				//inizia con i punti quindi comincio a costruire il nodo
				if(start>0){
					if(node ==null)
						node = ""; 
					node += sequence.substring(0, start); 
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
//				System.out.println("[Result] Start "+start);
//				System.out.println("[Result] End "+i);
//				System.out.println("[Result] "+structure.substring(start, i));
//				System.out.println("[Result] "+sequence.substring(start, i));	
//				System.out.println();				

				macroStructure.add(new Element(sequence.substring(start, i), structure.substring(start, i)));
				
				node = getMacroStructure(sequence.substring(i), macroStructure, structure.substring(i), node);
			}else{
				if(node == null)
					node = ""; 
				for(int i=0; i<sequence.length(); i++){
					node += sequence.charAt(i); 
				}
			}
		}
		return node;
	}

	public static void main(String[] args) {
		String sequence = "XXCCACTGACAYYGGTACTATGCAGGAAAGAATTACCACTACCAAGAAGGGATCTATCACCTCTGTACAGGTAAGAAAAATTACATAGATGAAGATCTGATTTGTATAAAGGCAGGGTGCAGTGGTGCATCTCAGCTACT";
			//String a = 	  "((.(.(.)..)))"; 
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
		
		//try {
			Element e = new Element("AAAAAAAAAAAAAAAAAAAA","((.(.)))"); 
			b.findEdge(e); 
//			b.execute();
//		} catch (RNABuilderException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} 

	}
}
