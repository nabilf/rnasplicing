package it.uniroma3.dia.tknn.algorithm;
import it.uniroma3.dia.tknn.log.Logging;

import org.apache.log4j.Logger;

/**
 * Find the possible maximum number of G-C and A-U pairs in a given
 * RNA sequence. Then, display one of its optimal structure of folding.
 * @author Hyung-Joon Kim
 */
public class RNAFolding extends Algorithm{
	private static Logger log = Logging.getInstance(RNAFolding.class); 

	//---------------------------------------------------------------
	/** A string of RNA sequence */
	private String seq;
	/** The number of characters in the RNA sequence */
	private int n;   
	/** A matrix for computing max number of pairs */ 
	private int[][] B;
	/** An auxiliary matrix for traceback */
	private int[][] P;

	private String result; 
	//----------------------------------------------------------------

	/**
	 * Construct an instance with a given string of RNA sequence.
	 */
	public RNAFolding(String s) {
		// Initialize the instance variables
		this.seq = s;
		this.n = seq.length();
		this.B = new int[this.n][this.n];
		this.P = new int[this.n][this.n];        
	}

	/**
	 * Check G-C and A-U pairs forming in a RNA sequence.
	 * @param xi, xj - two characters to be checked if they are paired.
	 * @return 1 if they are paired, otherwise 0.
	 */
	public int isPaired(char xi, char xj) {
		if ((xi == 'G' && xj == 'C') || (xi == 'C' && xj == 'G'))
			return 1;
		else if ((xi == 'A' && xj == 'U') || (xi == 'U' && xj == 'A'))
			return 1;
		else if ((xi == 'G' && xj == 'U') || (xi == 'U' && xj == 'G'))
			return 1;
		else if ((xi == 'A' && xj == 'T') || (xi == 'T' && xj == 'A'))
			return 1;
		else if ((xi == 'G' && xj == 'T') || (xi == 'T' && xj == 'G'))
			return 1;
		else
			return 0;
	}

	/**
	 * With Dynamic Programming, compute the possible max number of pairs
	 * in a given RNA sequence.
	 * @return max the maximum number of valid pairs.   
	 */
	public int findMaxPairs() {
		int max, temp;     
		for (int i=this.n-1; i>=0; i--) {
			for (int j=0; j<this.n; j++) {
				max = 0;
				//--------------------------------------------------------------
				// The algorithm looks at each recursive term to see whether it
				// gives the max num of pair. If the max. pair occurs in a 
				// particular term, the max. num will be stored in a matrix with 
				// the respective position indexed by i,j, and k. While doing that,
				// the algorithm also places a mark, such as -3, -2, -1, and k,
				// on the repective entry in a traceback matrix.
				//-------------------------------------------------------------- 

				// If i >= j - 4, then B[i,j] = 0. Since the matrix B is initialized
				// in the constructor, nothing needs to be done.
				if (i >= j - 4) {
					this.P[i][j] = -3;
				}            
				else { // If i < j - 4, then it's valid for folding.
					// Case1: i,j paired together or none of i and j is paired
					temp = this.B[i+1][j-1] + isPaired(this.seq.charAt(i),this.seq.charAt(j));
					if (temp > max ) {
						max = temp;                 
						this.P[i][j] = -1 - isPaired(this.seq.charAt(i),this.seq.charAt(j));                 
					}
					// Case2: i, j paired with k such that 1 <= k < j
					for (int k=i; k<j; k++) {
						temp = this.B[i][k] + this.B[k+1][j];                   
						if (temp > max) {
							max = temp;                    
							this.P[i][j] = k;
						}
					}
					this.B[i][j] = max;               
				}      
			}
		}  
		return this.B[0][this.n-1]; // holds the maximum number of pairs
	}

	/**     
	 * Trace back the possible maximum number of pairs, then consctruct 
	 * the structure of folding using parens.
	 */
	public StringBuilder traceBack(int i, int j, StringBuilder sb) {

		if (this.P[i][j] == -3) {  // Not paired
			for (int d=1; d <= j-i+1; d++) {
				sb.append('.');
			}
		}
		else if (this.P[i][j] == -2) {  // i and j paired together
			sb.append('(');       
			traceBack(i+1,j-1, sb);
			sb.append(')');     
		}
		else if (this.P[i][j] == -1) {  // i and j are not paired together
			sb.append('.');
			traceBack(i+1,j-1, sb);
			sb.append('.');
		}
		else {  // i and j could be possibly paired with k such that i <= k < j
			int k = this.P[i][j];
			if ( i <= k && k+1 <= j ) {
				traceBack(i,k, sb);
				traceBack(k+1,j, sb);
			}        
		}  
		return sb; 
	}

	/** 
	 * Display a structure of RNA folding, having the max number of pairs.
	 */
	public void execute() {      
		log.info("RNA sequence : " + seq);
		this.findMaxPairs(); 
		StringBuilder sb = traceBack(0,n-1, new StringBuilder());  // Trace back and construct the max num folding
		this.result = sb.toString(); 
		log.info("Folding structure : "+result);

		//printMatrix();

	}

	/** 
	 * Print the entire matrix which shows how the computation of
	 * the max number of pairs is processed.
	 */
	public void printMatrix() {
		for (int i=0; i < this.B.length; i++) {
			for (int j=0; j < this.B.length; j++) {
				if (this.B[i][j] > 9) {  System.out.print(" "+B[i][j]); }
				else { System.out.print("  "+B[i][j]); }               
			}
			System.out.println();
		}
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}