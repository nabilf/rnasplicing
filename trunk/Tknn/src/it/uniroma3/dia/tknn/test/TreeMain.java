package it.uniroma3.dia.tknn.test;

import it.uniroma3.dia.tknn.algorithm.TreeBuilder;
import it.uniroma3.dia.tknn.log.Logging;
import it.uniroma3.dia.tknn.model.Node;
import it.uniroma3.dia.tknn.setup.SysSetup;

public class TreeMain {
	protected static org.apache.log4j.Logger log = Logging.getInstance(TreeMain.class); 
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SysSetup.init();
		String sequence =	"TGGCTCTGTCCTCCCTATCCTTCCTGTTGAAGGTATTATCCTGTCTTACTAATTTCTCTCTCCTACCTAGGATGGCGGTGGTGGCAGCAGTGATCCTCTGAACCTGCAGAGGCCCCCTCCCCCAGCCTGGCCTGGCTC"; 
		String s = 			"((.((((((((((((((((((..(.....)..))))))))))(((...((((((.(((....)))))).)))(((((((....)..)))).)))))))(.(..(.(((((..((....))))))))))))))).).))";
//		System.out.println(s.length());
//		System.out.println(sequence.length());
//		for(int i = 0; i<s.length(); i++){
//			System.out.print(i+"   ");
//		}
//		System.out.println();
//		buildCsv(sequence); 
//		System.out.println();
//		buildCsv(s); 
//		String se = "ABCDEFGHI"; 
//		System.out.println(se.substring(0, 2));
//		System.out.println(se);
		TreeBuilder tb = new TreeBuilder(sequence, s);
		//tb.execute(); 
//		//getRoot(s); 

	}	
	private static Node getRootNode(String structure){
		int i=0; 
		int start = i; 
		int end = i; 
		int firstBacket = structure.indexOf("("); 
		//Se inizia con una parentesi la root sta alla fine
		if(firstBacket == 0 ){
			end = structure.length()-1; 
			i = end; 
			while(i>=0 && structure.charAt(i) == '.'){
				i--; 
			}
			start = i; 
		}else{
			
			while (i<structure.length() && structure.charAt(i) == '.' ){
				i++; 
			}
			end = i; 
		}
		Node root = new Node(); 
		root.setI(start); 
		root.setJ(end); 
		System.out.println("start "+start+" end "+end);
		return root; 
		
	}
	public static void buildCsv(String s ){
		//String s = ".(((.(.(....)((...((..((.(..(..(()).(.....)......).(.(.(())..))))).)).))(.((.(.(..(....)..)).)).).))))(((.((....)...))...)).....";
		String t = ""; 
		for (int i=0;i<s.length(); i++ ){
			System.out.print(s.charAt(i)+","); 
		}
	}
	public static void getRoot(String structure){
		int i = 0;
		
		int startRoot = 0; 
		int endRoot = 0; 
		// Nel caso in cui il pimo elemento della struttura  un ., 
		// alloras  sicuramente la root
		if(structure.charAt(i) == '.'){
			startRoot = i; 
			while (structure.charAt(i) == '.'){
				i++; 
			}
			endRoot = i-1; 
		}
		//Se invece l'ultimo elemento  un punto
		//allora la root sta alla fine
		else if(structure.charAt(structure.length()-1) == '.'){
			endRoot = structure.length()-1; 
			i = endRoot; 
			while ( structure.charAt(i) == '.' ){
				i--; 
			}
			startRoot = i+1; 
		}
		log.debug("ROOT "+startRoot+" - "+endRoot); 
	}

	


}
