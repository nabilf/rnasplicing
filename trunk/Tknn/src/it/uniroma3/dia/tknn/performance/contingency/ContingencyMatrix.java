package it.uniroma3.dia.tknn.performance.contingency;

import it.uniroma3.dia.tknn.classifier.ClassifierHelper;


public class ContingencyMatrix {

	private int[][] matrix;
	
	public ContingencyMatrix() {
		this.matrix = new int[2][2];		
	}
	public void print(String clazz){
		System.out.println("ContingecyMatrix class "+clazz);
		System.out.println("[0][0]"+this.matrix[0][0]+"\t [0][1]"+this.matrix[0][1]);
		System.out.println("[1][0]"+this.matrix[1][0]+"\t [1][1]"+this.matrix[1][1]);
		System.out.println();
	}
	public void addMatrix(ContingencyMatrix matrix)
	{
		this.matrix[0][0] = this.matrix[0][0] + matrix.matrix[0][0];
		this.matrix[1][0] = this.matrix[1][0] + matrix.matrix[1][0];
		this.matrix[0][1] = this.matrix[0][1] + matrix.matrix[0][1];
		this.matrix[1][1] = this.matrix[1][1] + matrix.matrix[1][1];
	}
	
	public void add(boolean system, boolean truth) {
		int i = 0;
		int j = 0;
		if (!system) 
			i = 1;
		if (!truth)
			j = 1;
		this.matrix[i][j] += 1;
	}
	
	public double get(boolean system, boolean truth) {
		int i = 0;
		int j = 0;
		if (!system) 
			i = 1;
		if (!truth)
			j = 1;
		
		return (double)this.matrix[i][j];
	}
	
	public double getAll() {
		return (double)ClassifierHelper.sumMatrix(this.matrix);
	}
		
}
