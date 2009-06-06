package it.uniroma3.dia.tknn.classifier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.StringTokenizer;
import java.util.Vector;

public class ClassifierHelper {
	
	public static String matrixToCSV(double[][] matrix) {
		String cvs = new String();
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				cvs += matrix[i][j] + ";";
			}			
			cvs += "\n";
		}
		return cvs;
	}
	
	public static double[][] CSVToMatrix(String csv) {
		StringReader sr = new StringReader(csv);
		BufferedReader br = new BufferedReader(sr);
		
		Vector app = new Vector();
		int rows = 0;
		int cols = 0;
		
		double[][] ret = null;
		
		try {
			
			String line = null;
			
			while((line = br.readLine()) != null) {
				rows++;
				
				String ts = line.replace('\n',' ').trim();
				
				StringTokenizer st = new StringTokenizer(ts, ";");
				while (st.hasMoreTokens()) {
					String token = st.nextToken();
					app.add(token);
					if (rows == 1) {
						cols++;
					}
				}
			}
			
			ret = new double[rows][cols];
			
			int k = 0;
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					ret[i][j] = Double.valueOf((String)app.get(k)).doubleValue();
					k++;
				}
			}
						
			
		} catch (IOException ex) {
			
		}
		
		return ret;
		
	}

	public  static double sumRow(double[] row) {
		double sum = 0;
		for (int i = 0; i < row.length; i++)
			sum += row[i];
		return sum;
	}

	public  static double sumMatrix(double[][] matrix) {
		double sum = 0;
		for (int i = 0; i < matrix.length; i++)
			for (int j = 0; j < matrix.length; j++)
				sum += matrix[i][j];
		return sum;
	}
	
	public static int  sumMatrix(int[][] matrix) {
		int sum = 0;
		for (int i = 0; i < matrix.length; i++)
			for (int j = 0; j < matrix.length; j++)
				sum += matrix[i][j];
		return sum;
	}
	
	public static double[][] calculateOddsMatrix(double[][] matrix) {

		int rows = matrix.length;
		int cols = matrix[0].length;

		double[][] ret = new double[rows][cols];

		for (int i = 0; i < rows; i++) {
			double sum = sumRow(matrix[i]);
			for (int j = 0; j < cols; j++) {
				if (matrix[i][j] != 0 && sum != 0) 
					ret[i][j] = matrix[i][j] / sum;
				else 
					ret[i][j] = 0;
			}
		}

		return ret;

	}
	
	public static double[][] calculateSupportsMatrix(double[][] matrix) {

		int rows = matrix.length;
		int cols = matrix[0].length;

		double[][] ret = new double[rows][cols];

		for (int i = 0; i < rows; i++) {
			double sum = sumMatrix(matrix);
			for (int j = 0; j < cols; j++) {
				ret[i][j] = matrix[i][j] / sum;
			}
		}

		return ret;

	}

	public static double[][] calculateGapRatioMatrix(double[][] matrix1,
			double[][] matrix2, double[][] matrix3) {

		int rows = matrix1.length;
		int cols = matrix1[0].length;

		double[][] ret = new double[rows][cols];

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				ret[i][j] = matrix1[i][j] / (1 + matrix2[i][j] + matrix3[i][j]);
			}
		}

		return ret;

	}

	public static void setKZero(double[][] matrix, int k) {

		int rows = matrix.length;
		int cols = matrix[0].length;

		for (int i = rows - k; i < rows; i++) {
			for (int j = cols - k; j < cols; j++) {
				if (i == j) {
					matrix[i][j] = 0;
				}
			}
		}

	}
}