package it.uniroma3.dia.tknn.algorithm;

import it.uniroma3.dia.tknn.log.Logging;

import org.apache.log4j.Logger;

public class LevenshteinDistance extends Algorithm{

	public static Logger log = Logging.getInstance(LevenshteinDistance.class); 

	private String s; 
	private String t; 
	private double result; 


	public LevenshteinDistance( String s, String t) {
		super();
		this.result = 0;
		this.s = s;
		this.t = t;
	}

	@Override
	public void execute() {
		log.debug("String A : "+this.s); 
		log.debug("String B : "+this.t); 
		this.getDistance(); 
		log.debug("Result Not Normalized "+this.result); 
		//this.normalize(); 
		log.debug("Result Normalized "+this.result); 
	}
	//*****************************
	// Compute Levenshtein distance
	//*****************************

	private void getDistance () {

		this.s= this.s.toUpperCase(); 
		this.t= this.t.toUpperCase(); 
		int matrix[][]; // matrix
		int s_size; // length of s
		int t_size; // length of t
		int i; // iterates through s
		int j; // iterates through t
		char s_i; // ith character of s
		char t_j; // jth character of t
		int cost; // cost

		// Step 1

		s_size= this.s.length ();
		t_size = this.t.length ();
		if (s_size == 0) {
			this.result =  t_size;
		}
		if (t_size == 0) {
			this.result = s_size;
		}
		matrix = new int[s_size+1][t_size+1];

		// Step 2

		for (i = 0; i <= s_size; i++) {
			matrix[i][0] = i;
		}

		for (j = 0; j <= t_size; j++) {
			matrix[0][j] = j;
		}

		// Step 3

		for (i = 1; i <= s_size; i++) {

			s_i = this.s.charAt (i - 1);

			// Step 4

			for (j = 1; j <= t_size; j++) {

				t_j = this.t.charAt (j - 1);

				// Step 5

				if (s_i == t_j) {
					cost = 0;
				}
				else {
					cost = 1;
				}

				// Step 6

				matrix[i][j] = Minimum (matrix[i-1][j]+1, matrix[i][j-1]+1, matrix[i-1][j-1] + cost);

			}

		}

		// Step 7

		this.result = matrix[s_size][t_size];

	}


	//****************************
	// Get minimum of three values
	//****************************

	private static int Minimum (int a, int b, int c) {
		int mi;

		mi = a;
		if (b < mi) {
			mi = b;
		}
		if (c < mi) {
			mi = c;
		}
		return mi;

	}
	//****************************
	// Normalizzazione
	//****************************
	
	private void normalize(){
		int maxLength=0; 
		int s_size= this.s.length ();
		int t_size = this.t.length ();
		if(s_size>t_size)
			maxLength = s_size; 
		else
			maxLength = t_size;
		//log.debug("MaxLength "+maxLength); 
		
		/*Math.exp(- ( (x-L) ^2) / (2*L) ) 
		sostituisci ad L la distanza di levenstein 
		e ad x 1
		*/
		double l = this.result/maxLength; 
	
		this.result = Math.exp(-(Math.pow((1-l), 2))/(2*l)); 
		
		
	}
	public String getS() {
		return s;
	}

	public void setS(String s) {
		this.s = s;
	}

	public String getT() {
		return t;
	}

	public void setT(String t) {
		this.t = t;
	}

	public double getResult() {
		return result;
	}

	public void setResult(double result) {
		this.result = result;
	}


}
