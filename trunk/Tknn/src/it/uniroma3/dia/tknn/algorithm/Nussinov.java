package it.uniroma3.dia.tknn.algorithm;

import it.uniroma3.dia.tknn.log.Logging;
import it.uniroma3.dia.tknn.model.Element;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import org.apache.log4j.Logger;
/**
 * Nussinov Algorithm 
 *       
 * @author Luana Rinaldi
 *
 */
public class Nussinov extends Algorithm {
	public static Logger log = Logging.getInstance(Nussinov.class); 

	private double[][] matrix;
	private int sequenceLenght;
	private String sequence;

	private String result; 

	private Map<String, Double> basePairs; 

	private Stack<Element> stack; 
	private Stack<Element> basePairStack; 

	public Nussinov(String sequence) {
		super();
		this.result = ""; 
		this.sequence = sequence; 
		this.sequenceLenght = sequence.length(); 
		this.matrix =new double[this.sequenceLenght][this.sequenceLenght] ;
		this.stack = new Stack<Element>(); 
		this.basePairStack = new Stack<Element>();
		this.createBasePairMap(); 
	} 

	public Nussinov() {
		// TODO Auto-generated constructor stub
	}

	public void execute(){
		log.info("[Nussinov Algorithm - Execute]"); 
		this.fill(); 
		this.tradeback(); 
		this.conversion();
	}


	/**
	 * @Method : Fill 
	 * Inizializzazione della matrice con diagonali a zero
	 * Restituisce una matrice che memorizza il numero massimo di coppie che possono
	 * essere create con quella coppia di basi 
	 * PSEUDOCODE 	
    for i = 2 to L do s(i,i-1) = 0; 
	for i = 1 to L do s(i,i) = 0; 
	for j = 2 to L do 
    	for i = 1 to j-1 do 
        	s(i,j) = max { 
        		s(i+1,j), 
            	s(i,j-1), 
            	s(i+1,j-1) + d(i,j), 
            	max i<k<j [s(i,k) + s(k+1,j)] 
            } 
	 */
	private void fill(){
		log.debug("Filling Matrix"); 
		this.fillMatrix();
		this.fillRecursion(); 
	}

	private void fillMatrix(){	
		for(int i = 1; i< this.sequenceLenght; i++){	
			this.matrix[i][i] = 0;
			this.matrix[i][i-1] = 0; 
		}
	}
	private void fillRecursion(){
		for (int j = 1; j < this.sequenceLenght; j++){ 
			int i = 0; 
			for (int jDiagWalker = j; jDiagWalker < this.sequenceLenght; jDiagWalker++){ 
				this.matrix[i][jDiagWalker] = max(i,jDiagWalker); 
				i++; 
			}
		}
	}  

	private double max(int i, int j){ 
		double a = this.matrix[i+1][j]; 
		double b = this.matrix[i][j-1]; 
		double c = this.matrix[i+1][j-1]+delta(i,j); 


		double d = 0; 
		for (int k = i+1; k < j; k++){ 
			double newValue = this.matrix[i][k]+this.matrix[k+1][j]; 
			if (newValue > d) d = newValue; 
		} 

		return Math.max(Math.max(a,b),Math.max(c,d)); 

	} 
	/*TODO: Da verificare!!! */
	/**
	 * Create The Base Pair Map
	 */
	private void createBasePairMap(){
		this.basePairs = new HashMap<String, Double>(); 

		String gu = "GU"; 
		String ug = "UG"; 
		Double guP = 1.0; 
		Double ugP = 1.0; 
		this.basePairs.put(gu, guP); 
		this.basePairs.put(ug, ugP); 

		String au = "AU";
		String ua = "UA"; 
		Double auP = 1.0; 
		Double uaP = 1.0; 
		this.basePairs.put(au, auP); 
		this.basePairs.put(ua, uaP); 

		String cg = "CG"; 
		String gc = "GC"; 
		Double cgP = 1.0; 
		Double gcP = 1.0; 
		this.basePairs.put(cg, cgP); 
		this.basePairs.put(gc, gcP); 
	}

	private double delta(int i, int j){ 
		String base = ""+this.sequence.charAt(i)+this.sequence.charAt(j); 
		return this.basePairs.get(base)!= null ? this.basePairs.get(base).doubleValue() : 0; 
	} 

	/**
	 * @Method : tradeback Algorithm 
		push(S,(1,L)); 
		while notEmpty(S) do 
    		(i,j) = pop(S); 
    		if i>=j  
    			then continue; 
    		else if s(i+1,j) = s(i,j) 
    			then push(S,(i+1,j)); 
            else if s(i,j-1) = s(i,j) 
            	then push(S,(i,j-1)); 
            else if  s(i+1,j-1) + d(i,j) = s(i,j) 
                then Òmemorizza la coppia i,jÓ 
                push(S,(i,j-1)); 
            else  
            	for k = i+1 to j-1 do 
                	if s(i,k) + s(k+1,j) = s(i,j) 
                    	then push(S,(k+1),j); 
                        push(S,(i.k)); 
                        break;  
	 */
	private void tradeback(){ 
		Element se =new Element(0, (this.sequenceLenght-1));  
		log.debug("Stack Element se: I="+se.getI()+" ; J="+se.getJ());
		this.stack.push(se); 

		while(!this.stack.empty()){
			Element e = stack.pop(); 
			log.debug("Stack Element e: I="+e.getI()+" ; J="+e.getJ()); 
			if (e.getI() >= e.getJ()) continue; 
			/*
			 * Case 1: Adding unpaired base i 
			 * Add unpaired position i onto best structure 
			 * for subsequence i+1, j 
			 */
			else if (this.matrix[e.getI()+1][e.getJ()] == this.matrix[e.getI()][e.getJ()])
				this.stack.push(new Element(e.getI()+1, e.getJ()));

			/*
			 *  Case 2: Adding unpaired base j 
			 *  Add unpaired position i onto best structure 
			 *  for subsequence i, j-1 
			 */  
			else if (this.matrix[e.getI()][e.getJ()-1] == this.matrix[e.getI()][e.getJ()])
				this.stack.push(new Element(e.getI(), e.getJ()-1));

			/*
			 * Case 3: Adding (i, j) pair 
			 * Add base pair (i, j) onto best structure 
			 * found for subsequence i+1, j-1 
			 */
			else if (this.matrix[e.getI()+1][e.getJ()-1] + delta(e.getI(),e.getJ()) == this.matrix[e.getI()][e.getJ()]){
				basePairStack.push(new Element(e.getI(), e.getJ()));
				stack.push(new Element(e.getI()+1, e.getJ()-1)); 
			}else{
				/*
				 * Case 4: Bifurcation 
				 * combining two optimal substructures i, k
				 * and k+1, j 
				 */		    	 
				for (int k = e.getI()+1; k < e.getJ(); k++){
					if (this.matrix[e.getI()][k] + this.matrix[k+1][e.getJ()] == this.matrix[e.getI()][e.getJ()]){
						stack.push(new Element(k+1, e.getJ()));
						stack.push(new Element(e.getI(), k)); 
						break;
					} 
				} 
			} 
		}
	}
	private void conversion(){
		char[] chars = new  char[this.sequenceLenght]; 
		for(int i=0; i<chars.length; i++){
			chars[i]='.'; 
		}
		while(!this.basePairStack.isEmpty()){
			Element e = (Element)basePairStack.pop(); 		  
			chars[e.getI()] = '(';
			chars[e.getJ() ] = ')';
		}

		for (int i = 0; i < chars.length; i++) {
			this.result += chars[i]; 
		} 
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public Stack<Element> getStack() {
		return stack;
	}

	public void setStack(Stack<Element> stack) {
		this.stack = stack;
	}

	public Stack<Element> getBasePairStack() {
		return basePairStack;
	}

	public void setBasePairStack(Stack<Element> basePairStack) {
		this.basePairStack = basePairStack;
	}
	public double[][] getMatrix() {
		return matrix;
	}

	public void setMatrix(double[][] matrix) {
		this.matrix = matrix;
	}

	public int getSequenceLenght() {
		return sequenceLenght;
	}

	public void setSequenceLenght(int sequenceLenght) {
		this.sequenceLenght = sequenceLenght;
	}


}
